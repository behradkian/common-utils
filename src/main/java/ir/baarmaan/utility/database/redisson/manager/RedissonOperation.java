package ir.baarmaan.utility.database.redisson.manager;

import ir.baarmaan.general.exception.redisson.RedissonCheckLockException;
import ir.baarmaan.general.exception.redisson.RedissonException;
import ir.baarmaan.general.exception.redisson.RedissonNullLockObjectException;
import ir.baarmaan.utility.database.redisson.RedissonCacheInfo;
import ir.baarmaan.utility.database.redisson.RedissonCacheMap;
import ir.baarmaan.utility.database.redisson.RedissonLockInfo;
import org.redisson.api.RLock;
import org.redisson.api.RRateLimiter;

public interface RedissonOperation {

    Object add(RedissonCacheInfo redissonCacheInfo);

    Object addAndUpdate(RedissonCacheInfo redissonCacheInfo);

    Object get(RedissonCacheInfo redissonCacheInfo);

    Object remove(RedissonCacheInfo redissonCacheInfo);

    RRateLimiter getRateLimiter(String rateLimiterName);

    boolean addAndUpdateAll(RedissonCacheMap redissonCacheMap) ;

    RLock lock(RedissonLockInfo lockInfo) throws RedissonException, RedissonCheckLockException;

    boolean unlock(RLock lock) throws RedissonException, RedissonNullLockObjectException;

}
