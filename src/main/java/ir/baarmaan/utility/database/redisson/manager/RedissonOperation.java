package ir.baarmaan.utility.database.redisson.manager;

import com.tosan.bpms.process.infra.exception.redisson.RedissonException;
import com.tosan.bpms.process.infra.redisson.RedissonCacheInfo;
import com.tosan.bpms.process.infra.redisson.RedissonCacheMap;
import com.tosan.bpms.process.infra.redisson.RedissonLockInfo;
import org.redisson.api.RLock;
import org.redisson.api.RRateLimiter;

public interface RedissonOperation {

    Object add(RedissonCacheInfo redissonCacheInfo);

    Object addAndUpdate(RedissonCacheInfo redissonCacheInfo);

    Object get(RedissonCacheInfo redissonCacheInfo);

    Object remove(RedissonCacheInfo redissonCacheInfo);

    RRateLimiter getRateLimiter(String rateLimiterName);

    boolean addAndUpdateAll(RedissonCacheMap redissonCacheMap) ;

    RLock lock(RedissonLockInfo lockInfo) throws RedissonException;

    boolean unlock(RLock lock) throws RedissonException;

}
