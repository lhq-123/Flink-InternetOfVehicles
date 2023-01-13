package com.alex.Streaming.Utils;

import java.util.BitSet;
import java.util.HashMap;

/**
 * @author Alex_liu
 * @create 2023-01-13 13:15
 * @Description  获得地理位置信息，根据经纬度获取地理位置信息，创建基于geoHash编码的地理位置计算工具类
 */
public class GeoHashUtil {
    //经纬度编码长度
    private static int numbits =  6 * 5;
    //数字字母编码数组
    final static char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'b', 'c', 'd', 'e', 'f', 'g',
            'h', 'j', 'k', 'm', 'n', 'p', 'q', 'r',
            's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    //存储编码字符循环存储的HashMap对象
    final static HashMap<Character, Integer> lookup = new HashMap<>();

    //静态代码块，执行设置HashMap的key，value
    static {
        int i = 0;
        for (char c : digits)
            lookup.put(c, i++);
    }

    /**
     * @desc 根据编码后的geohash字符串值，进行解码，得到经纬度数组
     * @param geoHash
     * @return [lat, lon]
     */
    public static double[] decode(String geoHash) {
        StringBuilder buffer = new StringBuilder();
        for (char c : geoHash.toCharArray()) {
            int i = lookup.get(c) + 32;
            buffer.append(Integer.toString(i, 2).substring(1));
        }

        BitSet lonset = new BitSet();
        BitSet latset = new BitSet();

        //经度，偶数位
        int j = 0;
        for (int i = 0; i < numbits * 2; i += 2) {
            boolean isSet = false;
            if (i < buffer.length())
                isSet = buffer.charAt(i) == '1';
            lonset.set(j++, isSet);
        }

        //纬度，奇数位
        j = 0;
        for (int i = 1; i < numbits * 2; i += 2) {
            boolean isSet = false;
            if (i < buffer.length())
                isSet = buffer.charAt(i) == '1';
            latset.set(j++, isSet);
        }

        //根据位编码、经度最小值、经度最大值计算出经度
        double lon = decode(lonset, -180, 180);
        //根据位编码、纬度最小值、纬度最大值计算出经度
        double lat = decode(latset, -90, 90);
        //返回纬度、经度数组
        return new double[]{lat, lon};
    }

    /**
     * @desc 编码方法，根据编码、维度[-90,90],经度[-180,180]
     * @param bs
     * @param floor
     * @param ceiling
     * @return
     */
    private static double decode(BitSet bs, double floor, double ceiling) {
        double mid = 0;
        for (int i = 0; i < bs.length(); i++) {
            mid = (floor + ceiling) / 2;
            if (bs.get(i))
                floor = mid;
            else
                ceiling = mid;
        }
        return mid;
    }

    /**
     * @desc 解码方法，根据纬度、经度，返回32编码字符串
     * @param lat 纬度
     * @param lon 经度
     * @return base32的字符串
     */
    public static String encode(double lat, double lon) {
        BitSet latbits = getBits(lat, -90, 90);
        BitSet lonbits = getBits(lon, -180, 180);
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < numbits; i++) {
            buffer.append(lonbits.get(i) ? '1' : '0');
            buffer.append(latbits.get(i) ? '1' : '0');
        }
        return base32(Long.parseLong(buffer.toString(), 2));
    }

    /**
     * @desc 根据经纬度和范围，获取对应的二进制值
     * @param d 经度 | 纬度
     * @param floor 最小值
     * @param ceiling 最大值
     * @return 返回BitSet，java.util工具类，用于位移操作工具类
     */
    private static BitSet getBits(double d, double floor, double ceiling) {
        BitSet buffer = new BitSet(numbits);
        for (int i = 0; i < numbits; i++) {
            double mid = (floor + ceiling) / 2;
            if (d >= mid) {
                buffer.set(i);
                floor = mid;
            } else {
                ceiling = mid;
            }
        }
        return buffer;
    }

    /**
     * @desc 将经纬度合并后二二进制进行指定32位编码
     * @param i 被32编码的long值
     * @return 32编码字符串
     */
    private static String base32(long i) {
        char[] buf = new char[65];
        int charPos = 64;
        boolean negative = (i < 0);
        if (!negative)
            i = -i;
        while (i <= -32) {
            buf[charPos--] = digits[(int) (-(i % 32))];
            i /= 32;
        }
        buf[charPos] = digits[(int) (-i)];

        if (negative)
            buf[--charPos] = '-';
        return new String(buf, charPos, (65 - charPos));
    }
}
