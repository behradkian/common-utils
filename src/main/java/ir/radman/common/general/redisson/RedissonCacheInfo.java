package ir.radman.common.general.redisson;

import java.util.concurrent.TimeUnit;

public class RedissonCacheInfo extends RedissonCacheBaseInfo {


    private String key;
    private Object value;

    public RedissonCacheInfo() {
    }

    public RedissonCacheInfo(String name, String key, Object value, TimeUnit timeUnit, Long expireTime, String rateLimiterName) {
        super(name,timeUnit,expireTime);
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
        final StringBuilder sb = new StringBuilder("RedissonCacheInfo{");
        sb.append("name='").append(super.getName()).append('\'');
        sb.append(", key='").append(key).append('\'');
        sb.append(", value=").append(value);
        sb.append(", timeUnit=").append(super.getTimeUnit());
        sb.append(", expireTime=").append(super.getExpireTime());
        sb.append('}');
        return sb.toString();
    }

}

