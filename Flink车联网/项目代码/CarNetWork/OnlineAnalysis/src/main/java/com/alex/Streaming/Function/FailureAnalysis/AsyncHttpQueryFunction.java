package com.alex.Streaming.Function.FailureAnalysis;

import com.alex.Streaming.Bean.FailureAnalysis.FailureAnalysisDataObj;
import com.alex.Streaming.Bean.FailureAnalysis.FailureAnalysisLocationInfo;
import com.alex.Streaming.Utils.GaoDeMapUtils;
import com.alex.Streaming.Utils.GeoHashUtil;
import com.alex.Streaming.Utils.RedisUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Supplier;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.async.ResultFuture;
import org.apache.flink.streaming.api.functions.async.RichAsyncFunction;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.util.EntityUtils;

import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * @author Alex_liu
 * @create 2023-01-13 14:33
 * @Description 自定义异步IO操作
 */
public class AsyncHttpQueryFunction extends RichAsyncFunction<FailureAnalysisDataObj,FailureAnalysisDataObj> {

    CloseableHttpAsyncClient httpclient = null;

    /**
     * 创建http连接
     * @param parameters
     * @throws Exception
     */
    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        //创建一个异步的HttpClient连接池
        //初始化异步的HttpClient
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(3000)
                .setConnectTimeout(3000) //设置HttpClient连接池超时时间
                .build();

        httpclient = HttpAsyncClients.custom()
                .setMaxConnTotal(20) //连接池最大连接数
                .setDefaultRequestConfig(requestConfig)
                .build();

        httpclient.start();
    }

    /**
     * 异步函数的核心方法
     * @param failureAnalysisDataObj
     * @param resultFuture
     * @throws Exception
     */
    @Override
    public void asyncInvoke(FailureAnalysisDataObj failureAnalysisDataObj, ResultFuture<FailureAnalysisDataObj> resultFuture) throws Exception {
// try 处理异常的json解析
        try {
            // 1、解析JSON，拿到经纬度信息
            double longitude = failureAnalysisDataObj.getLng();
            double latitude =failureAnalysisDataObj.getLat();

            // 创建httpGet请求
            String gaoDeAddressUrl = GaoDeMapUtils.getUrlByLonLat(longitude, latitude);
            System.out.println(gaoDeAddressUrl);
            HttpPost httpPost = new HttpPost(gaoDeAddressUrl);
            // 2、提交httpclient异步请求，获取异步请求的future对象
            Future<HttpResponse> future = httpclient.execute(httpPost, null);//callback是回调函数（也可通过回调函数拿结果）

            // 3、从成功的Future中取数据，返回的是orderBean
            CompletableFuture<FailureAnalysisDataObj> orderBeanAndPCD =
                    CompletableFuture.supplyAsync((Supplier<FailureAnalysisDataObj>) () -> {

                        // 用try包住，处理get不到值时的报错程序
                        try {
                            HttpResponse response = future.get();

                            String province = null;
                            String city = null;
                            String district = null;

                            if (response.getStatusLine().getStatusCode() == 200) {
                                //拿出响应的实例对象
                                HttpEntity entity = response.getEntity();
                                //将对象toString
                                String result = EntityUtils.toString(entity);

                                JSONObject jsonObject1 = JSON.parseObject(result);

                                JSONObject regeocodeObject = jsonObject1.getJSONObject("regeocode");

                                if (regeocodeObject != null && !regeocodeObject.isEmpty()) {
                                    JSONObject addObject = regeocodeObject.getJSONObject("addressComponent");
                                    district = addObject.getString("district");
                                    city = addObject.getString("city");
                                    province = addObject.getString("province");

                                    FailureAnalysisLocationInfo failureAnalysisLocationInfo = new FailureAnalysisLocationInfo();
                                    failureAnalysisLocationInfo.setProvince(province);
                                    failureAnalysisLocationInfo.setCity(city);
                                    failureAnalysisLocationInfo.setCountry(district);
                                    failureAnalysisLocationInfo.setLongitude(longitude);
                                    failureAnalysisLocationInfo.setLatitude(latitude);
                                    String geoHash = GeoHashUtil.encode(latitude, longitude);
                                    failureAnalysisLocationInfo.setAddress(geoHash);
                                    RedisUtil.set(geoHash.getBytes(), JSON.toJSONString(failureAnalysisLocationInfo).getBytes());
                                }
                            }
                            failureAnalysisDataObj.setProvince(province);
                            failureAnalysisDataObj.setCity(city);
                            failureAnalysisDataObj.setCounty(district);

                            return failureAnalysisDataObj;

                        } catch (Exception e) {
                            // 拿不到的返回null(还没有查询到结果，就从future取了)
                            return null;
                        }
                    });

            // 4、将取出的数据，存入ResultFuture，返回给方法
            orderBeanAndPCD.thenAccept(resultBean -> {
                //complete()里面需要的是Collection集合，但是一次执行只返回一个结果
                //所以集合使用singleton单例模式，集合中只装一个对象
                resultFuture.complete(Collections.singleton(resultBean));
            });

        } catch (Exception e) {
            resultFuture.complete(Collections.singleton(null));
        }
    }


    /**
     * 关闭连接
     * @throws Exception
     */
    @Override
    public void close() throws Exception {
        super.close();
        httpclient.close();
    }
}
