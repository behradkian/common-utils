package radman.util.redis;

import java.util.concurrent.TimeUnit;

public class RedissonCacheInfo extends RedissonCacheBaseInfo {


    private String key;
    private Object value;

    public RedissonCacheInfo() {
    }

    public RedissonCacheInfo(String name, String key, Object value, TimeUnit timeUnit, Long expireTime, String rateLimiterName) {
        super(name, timeUnit, expireTime);
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String toString() {
        String sb = "RedissonCacheInfo{" + "name='" + super.getName() + '\'' +
                ", key='" + key + '\'' +
                ", value=" + value +
                ", timeUnit=" + super.getTimeUnit() +
                ", expireTime=" + super.getExpireTime() +
                '}';
        return sb;
    }

}

