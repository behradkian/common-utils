package ir.radman.common.tool.redis;

import ir.radman.common.general.exception.redisson.RedissonAcquireLockException;
import ir.radman.common.general.exception.redisson.RedissonCheckLockException;
import ir.radman.common.general.exception.redisson.RedissonNullLockObjectException;
import ir.radman.common.tool.redis.manager.DistributedRedissonManager;
import ir.radman.common.tool.redis.manager.RedissonClientConfiguration;
import ir.radman.common.tool.redis.manager.RedissonOperation;
import org.redisson.api.RLock;
import org.redisson.api.RMapCache;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class RedissonOperationImpl implements RedissonOperation {

    private static RedissonOperationImpl REDISSON_OPERATION_IMPL;
    private static final Logger LOGGER = LoggerFactory.getLogger(RedissonOperationImpl.class);

    private RedissonOperationImpl() {

    }

    public static RedissonOperationImpl getInstance() {

        if (REDISSON_OPERATION_IMPL == null) {
            synchronized (RedissonOperationImpl.class) {
                if (REDISSON_OPERATION_IMPL == null) {
                    REDISSON_OPERATION_IMPL = new RedissonOperationImpl();
                }
                return REDISSON_OPERATION_IMPL;
            }
        }
        return REDISSON_OPERATION_IMPL;

    }

    @Override
    public Object add(RedissonCacheInfo redissonCacheInfo) {
        LOGGER.info("Before call getMapCache method from redis with parameter {}", redissonCacheInfo);
        redissonCacheInfo = setCache(redissonCacheInfo);
        RMapCache<String, Object> rMapCache;
        RedissonClient redissonClient = DistributedRedissonManager.getInstance().getRedissonClient();
        rMapCache = redissonClient.getMapCache(redissonCacheInfo.getName());
        LOGGER.info("getMapCache was called from redis with result {}", rMapCache);
        if (rMapCache != null) {
            Long startTime = null;
            Object putIfAbsentObj;
            try {
                startTime = System.currentTimeMillis();
                putIfAbsentObj = rMapCache.putIfAbsent(redissonCacheInfo.getKey(), redissonCacheInfo.getValue(), redissonCacheInfo.getExpireTime(), redissonCacheInfo.getTimeUnit());
            } finally {
                LOGGER.info("{ \"RedissonAction\" : \"Add\" , \"RedissonTime\" : " + (System.currentTimeMillis() - startTime) + " }");
            }
            return putIfAbsentObj;
        }
        LOGGER.info("rMapCache is null");
        throw new NullPointerException("RMapCache Could Not Be Null");
    }

    @Override
    public boolean addAndUpdateAll(RedissonCacheMap redissonCacheMap) {
        LOGGER.info("Before call addAll method from redis with parameter {}", redissonCacheMap);
        RMapCache<String, Object> rMapCache;
        RedissonClient redissonClient = DistributedRedissonManager.getInstance().getRedissonClient();
        rMapCache = redissonClient.getMapCache(redissonCacheMap.getName());
        LOGGER.info("getMapCache was called from redis addAll with result {}", rMapCache);
        if (rMapCache == null) throw new NullPointerException("RMapCache Could Not Be Null");
        Long startTime = null;
        try {
            startTime = System.currentTimeMillis();
            rMapCache.putAll(redissonCacheMap.getMap(), redissonCacheMap.getExpireTime(), redissonCacheMap.getTimeUnit());
        } finally {
            LOGGER.info("{ \"RedissonAction\" : \"Add\" , \"RedissonTime\" : " + (System.currentTimeMillis() - startTime) + " }");
        }
        return true;
    }

    @Override
    public RLock lock(RedissonLockInfo lockInfo) throws RedissonAcquireLockException, RedissonNullLockObjectException, RedissonCheckLockException {
        RLock lock;
        Long startTime;
        if (lockInfo != null && lockInfo.getLockItem() != null && !lockInfo.getLockItem().isEmpty() && lockInfo.getExpireTime() != null) {
            try {
                startTime = System.currentTimeMillis();
                lock = DistributedRedissonManager.getInstance().getRedissonClient().getLock(lockInfo.getLockItem());
                LOGGER.info("{ \"RedissonAction\" : \"GetLock\" , \"RedissonTime\" : " + (System.currentTimeMillis() - startTime) + " }");

                if (lock.isLocked()) {
                    LOGGER.info("Item {} has been locked before", lockInfo.getLockItem());
                    throw new RedissonCheckLockException("Item has been locked before");
                }

                startTime = System.currentTimeMillis();
                lock.lock(lockInfo.getExpireTime(), TimeUnit.SECONDS);
                LOGGER.info("{ \"RedissonAction\" : \"Lock\" , \"RedissonTime\" : " + (System.currentTimeMillis() - startTime) + " }");
                LOGGER.info("{} item is locked by redisson now", lockInfo.getLockItem());
            } catch (Exception e) {
                if ((e instanceof RedissonCheckLockException)) {
                    LOGGER.error("Error in locking Item {} by redisson ", lockInfo.getLockItem());
                    throw new RedissonCheckLockException(e.getMessage());
                }
                throw new RedissonAcquireLockException(e.getMessage());
            }
            return lock;
        } else {
            LOGGER.info("lockInfo is null");
            throw new RedissonNullLockObjectException("lockInfo is null");
        }
    }

    @Override
    public boolean unlock(RLock lock) throws RedissonNullLockObjectException {
        Long startTime;
        if (lock != null) {
            if (lock.isHeldByCurrentThread()) {
                startTime = System.currentTimeMillis();
                lock.unlock();
                LOGGER.info("{ \"RedissonAction\" : \"Unlock\" , \"RedissonTime\" : " + (System.currentTimeMillis() - startTime) + " }");
                LOGGER.info("redisson is unlocked now");
                return true;
            }
            LOGGER.info("RLock isn't held by current thread!");
            return false;
        } else {
            LOGGER.info("lock is null");
            throw new RedissonNullLockObjectException("lock is null");
        }
    }

    @Override
    public Object addAndUpdate(RedissonCacheInfo redissonCacheInfo) {
        LOGGER.info("Before call getMapCache method from redis with parameter {}", redissonCacheInfo);
        redissonCacheInfo = setCache(redissonCacheInfo);
        RMapCache<String, Object> rMapCache;
        RedissonClient redissonClient = DistributedRedissonManager.getInstance().getRedissonClient();
        rMapCache = redissonClient.getMapCache(redissonCacheInfo.getName());
        LOGGER.info("getMapCache was called from redis with result {}", rMapCache);
        if (rMapCache != null) {
            Long startTime = null;
            Object putObj;
            try {
                startTime = System.currentTimeMillis();
                putObj = rMapCache.put(redissonCacheInfo.getKey(), redissonCacheInfo.getValue(), redissonCacheInfo.getExpireTime(), redissonCacheInfo.getTimeUnit());
            } finally {
                LOGGER.info("{ \"RedissonAction\" : \"AddAndUpdate\" , \"RedissonTime\" : " + (System.currentTimeMillis() - startTime) + " }");
            }
            return putObj;
        }
        LOGGER.info("rMapCache is null");
        throw new NullPointerException("RMapCache Could Not Be Null");
    }

    @Override
    public Object get(RedissonCacheInfo redissonCacheInfo) {
        LOGGER.info("Before call getMapCache method from redis with parameter {}", redissonCacheInfo);
        redissonCacheInfo = setCache(redissonCacheInfo);
        RMapCache<String, Object> rMapCache;
        RedissonClient redissonClient = DistributedRedissonManager.getInstance().getRedissonClient();
        rMapCache = redissonClient.getMapCache(redissonCacheInfo.getName());
        LOGGER.info("getMapCache was called from redis with result {}", rMapCache);
        if (rMapCache != null) {
            Long startTime = null;
            Object getObj;
            try {
                startTime = System.currentTimeMillis();
                getObj = rMapCache.get(redissonCacheInfo.getKey());
            } finally {
                LOGGER.info("{ \"RedissonAction\" : \"Get\" , \"RedissonTime\" : " + (System.currentTimeMillis() - startTime) + " }");
            }
            return getObj;
        }
        LOGGER.info("rMapCache is null");
        throw new NullPointerException("RMapCache Could Not Be Null");
    }

    @Override
    public Object remove(RedissonCacheInfo redissonCacheInfo) {
        redissonCacheInfo = setCache(redissonCacheInfo);
        RMapCache<String, Object> rMapCache;
        RedissonClient redissonClient = DistributedRedissonManager.getInstance().getRedissonClient();
        rMapCache = redissonClient.getMapCache(redissonCacheInfo.getName());
        if (rMapCache != null) {
            Long startTime = null;
            Object removeObj;
            try {
                startTime = System.currentTimeMillis();
                removeObj = rMapCache.remove(redissonCacheInfo.getKey());
            } finally {
                LOGGER.info("{ \"RedissonAction\" : \"Remove\" , \"RedissonTime\" : " + (System.currentTimeMillis() - startTime) + " }");
            }
            return removeObj;
        }
        throw new NullPointerException("RMapCache Could Not Be Null");
    }

    @Override
    public RRateLimiter getRateLimiter(String rateLimiterName) {
        return DistributedRedissonManager.getInstance().getLimiterHashMap().getOrDefault(rateLimiterName, null);
    }

    private RedissonCacheInfo setCache(RedissonCacheInfo redissonCacheInfo) {
        if (redissonCacheInfo != null) {
            if (redissonCacheInfo.getKey() == null || redissonCacheInfo.getKey().isEmpty()) {
                throw new NullPointerException("RedissonCacheInfo Key Could Not Be Null Or Empty");
            }
            RedissonClientConfiguration redissonClientConfiguration = DistributedRedissonManager.getInstance().getRedissonClientConfiguration();
            String cacheName = redissonCacheInfo.getName() != null && !redissonCacheInfo.getName().isEmpty() ? redissonCacheInfo.getName() : redissonClientConfiguration.getDefaultCacheName();
            redissonCacheInfo.setName(cacheName);
            Long expireTime = redissonCacheInfo.getExpireTime() != null && !redissonCacheInfo.getExpireTime().equals(0L) ? redissonCacheInfo.getExpireTime() : redissonClientConfiguration.getExpirationTime();
            redissonCacheInfo.setExpireTime(expireTime);
            TimeUnit timeUnit = redissonCacheInfo.getTimeUnit() != null ? redissonCacheInfo.getTimeUnit() : TimeUnit.SECONDS;
            redissonCacheInfo.setTimeUnit(timeUnit);
        } else {
            throw new NullPointerException("RedissonCacheInfo Could Not Be Null");
        }
        return redissonCacheInfo;
    }


}
