package com.alex.utils;


import com.alex.constants.Constants;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.lf5.viewer.configure.ConfigurationManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Alex_liu
 * @create 2023-01-28 17:24
 * @Description 参数解析工具类
 */
public class ParamUtil {

    private final static Logger logger = LoggerFactory.getLogger(ParamUtil.class);
    /**
     *  从命令行参数中提取任务id
     * @param args 命令行参数
     * @param taskType 参数类型(任务id对应的值是Long类型才可以)，对应my.properties中的key
     * @return 任务id （spark.local.taskId.monitorFlow）
     */
    public static  Long getTaskIdFromArgs(String[] args, String taskType){
        boolean local = ConfigUtil.getBoolean(Constants.SPARK_LOCAL);
        if (local){
            return ConfigUtil.getLong(taskType);
        }else {
            try {
                if (args !=null && args.length > 0){
                    return Long.valueOf(args[0]);
                }else {
                    logger.info("集群提交任务，需要参数");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0L;
    }

    /**
     * 从JSON对象中提取参数
     * @param jsonObject
     * @param field
     * @return 参数
     */
    public static String getParam(JSONObject jsonObject, String field) {
        JSONArray jsonArray = jsonObject.getJSONArray(field);
        if(jsonArray != null && jsonArray.size() > 0) {
            return jsonArray.getString(0);
        }
        return null;
    }

}
