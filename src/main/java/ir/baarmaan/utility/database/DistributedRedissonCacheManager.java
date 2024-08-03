package ir.baarmaan.utility.database;

import org.redisson.Redisson;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class DistributedRedissonCacheManager {

    private static String password;
    private static Integer dataBaseId;
    private static String address;
    private static RedissonClient redissonClient = null;
    private static String userName;
    private static Long expirationTime;
    private static String cacheName;
    private static final Logger LOGGER = LoggerFactory.getLogger(DistributedRedissonCacheManager.class);

    static {
        updateConfig();
    }

    private DistributedRedissonCacheManager() {
    }

    public static void updateConfig() {
        try {
            address = "";
            cacheName = "";
            userName = "";
            password = "";
            dataBaseId = 1;
            expirationTime = 1L;
            redissonClient = getRedissCon();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private static RedissonClient getRedissCon() {
        Config config = new Config();
        config.useSingleServer().setUsername(userName).setDatabase(dataBaseId).setAddress(address).setPassword(password);
        return Redisson.create(config);
    }

    public static void add(String enteredKey, Object enteredValue, TimeUnit timeUnit, long expTime) {

        RMapCache mapCache = redissonClient.getMapCache(cacheName);
        mapCache.putIfAbsent(enteredKey, enteredValue, expTime, timeUnit);

    }

    public static void add(String enteredKey, Object enteredValue) {
        RMapCache mapCache = redissonClient.getMapCache(cacheName);
        mapCache.putIfAbsent(enteredKey, enteredValue, expirationTime, TimeUnit.MINUTES);
    }

    public static void add(String enteredKey, Object enteredValue, String redisCacheName) {
        RMapCache mapCache = redissonClient.getMapCache(redisCacheName);
        mapCache.putIfAbsent(enteredKey, enteredValue, expirationTime, TimeUnit.MINUTES);
    }

    public static void add(String enteredKey, Object enteredValue, String redisCacheName, TimeUnit timeUnit, long expTime) {

        RMapCache mapCache = redissonClient.getMapCache(redisCacheName);
        mapCache.putIfAbsent(enteredKey, enteredValue, expTime, timeUnit);
    }

    public static void addAndUpdate(String enteredKey, Object enteredValue, String redisCacheName, TimeUnit timeUnit, long expTime) {
        RMapCache mapCache = redissonClient.getMapCache(redisCacheName);
        mapCache.put(enteredKey, enteredValue, expTime, timeUnit);
    }

    public static void addAndUpdate(String enteredKey, Object enteredValue, TimeUnit timeUnit, long expTime) {
        RMapCache mapCache = redissonClient.getMapCache(cacheName);
        mapCache.put(enteredKey, enteredValue, expTime, timeUnit);
    }

    public static void addAndUpdate(String enteredKey, Object enteredValue) {
        RMapCache mapCache = redissonClient.getMapCache(cacheName);
        mapCache.put(enteredKey, enteredValue, expirationTime, TimeUnit.MINUTES);
    }

    public static void addAndUpdate(String enteredKey, Object enteredValue, String redisCacheName) {
        RMapCache mapCache = redissonClient.getMapCache(redisCacheName);
        mapCache.put(enteredKey, enteredValue, expirationTime, TimeUnit.MINUTES);
    }

    public static Object get(String enteredKey) {
        return redissonClient.getMapCache(cacheName).get(enteredKey);
    }

    public static Object get(String enteredKey, String redisCacheName) {
        return redissonClient.getMapCache(redisCacheName).get(enteredKey);
    }

    public static void remove(String enteredKey) {
        redissonClient.getMapCache(cacheName).remove(enteredKey);
    }

    public static void remove(String enteredKey, String redisCacheName) {
        redissonClient.getMapCache(redisCacheName).remove(enteredKey);
    }
}
