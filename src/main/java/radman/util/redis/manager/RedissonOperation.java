package radman.util.redis.manager;

import org.redisson.api.RLock;
import org.redisson.api.RRateLimiter;
import radman.util.redis.RedissonCacheInfo;
import radman.util.redis.RedissonCacheMap;
import radman.util.redis.RedissonLockInfo;
import radman.util.redis.exception.RedissonCheckLockException;
import radman.util.redis.exception.RedissonException;
import radman.util.redis.exception.RedissonNullLockObjectException;

public interface RedissonOperation {

    Object add(RedissonCacheInfo redissonCacheInfo);

    Object addAndUpdate(RedissonCacheInfo redissonCacheInfo);

    Object get(RedissonCacheInfo redissonCacheInfo);

    Object remove(RedissonCacheInfo redissonCacheInfo);

    RRateLimiter getRateLimiter(String rateLimiterName);

    boolean addAndUpdateAll(RedissonCacheMap redissonCacheMap);

    RLock lock(RedissonLockInfo lockInfo) throws RedissonException;

    boolean unlock(RLock lock) throws RedissonException;

}
