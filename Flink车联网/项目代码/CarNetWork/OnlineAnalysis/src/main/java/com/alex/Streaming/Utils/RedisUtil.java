package com.alex.Streaming.Utils;

import javolution.util.ReentrantLock;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.util.Pool;

/**
 * @author Alex_liu
 * @create 2023-01-13 13:18
 * @Description 为了方便操作redis，定义redis操作工具类
 */
public class RedisUtil {
    private static Pool<Jedis> jedisPool = null;
    private static ReentrantLock lock = new ReentrantLock();
    private static String HOST = ConfigLoader.get("redis.host");
    private static int PORT = Integer.valueOf(ConfigLoader.get("redis.port"));
    private static int TIMEOUT = Integer.valueOf(ConfigLoader.get("redis.session.timeout"));
    private static int DATABASE = Integer.valueOf(ConfigLoader.get("redis.database"));
    private static String PASSWORD = ConfigLoader.get("redis.password");

    /**
     * 初始化连接池
     */
    static {
        if ("null".equals(PASSWORD)) PASSWORD = null;
        if (jedisPool == null) {
            jedisPool = new JedisPool(new GenericObjectPoolConfig(), HOST, PORT, TIMEOUT, PASSWORD, DATABASE, "");
        }
    }

    /**
     * @desc: 获得jedis客户端
     * @return Jedis客户端
     */
    public static Jedis getJedis() {
        if (jedisPool == null) {
            lock.lock(); //防止吃初始化时多线程竞争问题
            jedisPool = new JedisPool(new GenericObjectPoolConfig(), HOST, PORT, TIMEOUT, PASSWORD, DATABASE, "");
            lock.unlock();
        }
        return jedisPool.getResource();
    }

    /**
     * @desc: 根据redis中存在的key获得value
     * @param key
     * @return value的字节数组
     */
    public static byte[] get(byte[] key) {
        Jedis jedis = getJedis();
        byte[] result = "".getBytes();
        if (jedis.exists(key)) {
            result = jedis.get(key);
        }
        jedis.close();
        return result;
    }

    /**
     * @desc: 插入数据到redis中，并设置key的存活时间（seconds）
     * @param key
     * @param value
     * @param keyTimeout
     * @return
     */
    public static Boolean set(byte[] key, byte[] value, int keyTimeout) {
        try {
            Jedis jedis = getJedis();
            jedis.setex(key, keyTimeout, value);
            jedis.close();
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static void set(byte[] key, byte[] value) {
        try {
            Jedis jedis = getJedis();
            jedis.set(key, value);
            jedis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @desc:释放资源，关闭连接池
     */
    public static void releaseSource() {
        if (jedisPool != null) jedisPool.close();
    }
}
