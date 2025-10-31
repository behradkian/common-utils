package ir.radman.common.general.redisson;

import java.util.concurrent.TimeUnit;

public abstract class RedissonCacheBaseInfo {
    private TimeUnit timeUnit;
    private Long expireTime;
    private String name;


    public RedissonCacheBaseInfo(String name, TimeUnit timeUnit, Long expireTime) {
        this.timeUnit = timeUnit;
        this.expireTime = expireTime;
        this.name = name;
    }

    public RedissonCacheBaseInfo() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

    public Long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }
}
