package radman.util.redis;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class RedissonCacheMap extends RedissonCacheBaseInfo {
    private Map<String, String> map;

    public RedissonCacheMap() {
        super();
    }

    public RedissonCacheMap(String name,
                            TimeUnit timeUnit, Long expireTime, Map<String, String> map) {
        super(name, timeUnit, expireTime);
        this.map = map;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    @Override
    public String toString() {
        String sb = "RedissonCacheInfo{" + "name='" + super.getName() + '\'' +
                ", timeUnit=" + super.getTimeUnit() +
                ", expireTime=" + super.getExpireTime() +
                ", map=" + getMap() +
                '}';
        return sb;
    }
}
