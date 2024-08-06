package ir.baarmaan.utility.database.redisson;

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
        final StringBuilder sb = new StringBuilder("RedissonCacheInfo{");
        sb.append("name='").append(super.getName()).append('\'');
        sb.append(", timeUnit=").append(super.getTimeUnit());
        sb.append(", expireTime=").append(super.getExpireTime());
        sb.append(", map=").append(getMap());
        sb.append('}');
        return sb.toString();
    }
}
