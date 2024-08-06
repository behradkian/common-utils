package ir.baarmaan.utility.database.redisson.manager;

import com.tosan.bpms.process.infra.config.BPMSComplexConfigurationKey;
import com.tosan.bpms.process.infra.config.BPMSConfigurationKey;
import com.tosan.bpms.process.infra.config.ConfigObserver;
import com.tosan.bpms.process.infra.config.Configuration;
import com.tosan.bpms.process.utils.converter.MapperUtil;
import org.redisson.Redisson;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;

public class DistributedRedissonManager implements ConfigObserver {

    private static RedissonClient redissonClient;
    private static RedissonClientConfiguration redissonClientConfiguration;
    private static final HashMap<String, RRateLimiter> limiterHashMap = new HashMap<>();
    private static DistributedRedissonManager DISTRIBUTED_REDISSON_MANAGER = null;
    private static final Logger LOGGER = LoggerFactory.getLogger(DistributedRedissonManager.class);

    private DistributedRedissonManager() {
        distributedRedissonCacheManagerInit();
    }

    public static DistributedRedissonManager getInstance() {
        if (DISTRIBUTED_REDISSON_MANAGER == null) {
            synchronized (DistributedRedissonManager.class) {
                if (DISTRIBUTED_REDISSON_MANAGER == null) {
                    DISTRIBUTED_REDISSON_MANAGER = new DistributedRedissonManager();
                }
                return DISTRIBUTED_REDISSON_MANAGER;
            }
        }
        return DISTRIBUTED_REDISSON_MANAGER;
    }

    private void distributedRedissonCacheManagerInit() {
        try {
            LOGGER.info("initiate {} start has been started", DistributedRedissonManager.class.getName());
            initRedissonClient();
            initRateLimits();
            LOGGER.info("initiate {} successfully! has been ended", DistributedRedissonManager.class.getName());
        } catch (Exception e) {
            LOGGER.error("there is problem in initiate {} with description:{}", DistributedRedissonManager.class.getName(), e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    private static void CreatRedissonClientConfiguration() {
        LOGGER.info("start CreatRedissonClientConfiguration");

        String bpmsRedisAddress = Configuration.getInstance().getConfiguration(BPMSConfigurationKey.BPMS_REDIS_ADDRESS);
        String bpmsRedisUserName = Configuration.getInstance().getConfiguration(BPMSConfigurationKey.BPMS_REDIS_USERNAME);
        String bpmsRedisDataBaseId = Configuration.getInstance().getConfiguration(BPMSConfigurationKey.BPMS_REDIS_DATABASEID);
        String bpmsRedisPassword = Configuration.getInstance().getConfiguration(BPMSConfigurationKey.BPMS_REDIS_PASSWORD);


        if (bpmsRedisAddress != null && !bpmsRedisAddress.isEmpty() &&
                bpmsRedisUserName != null && !bpmsRedisUserName.isEmpty() &&
                bpmsRedisDataBaseId != null && !bpmsRedisDataBaseId.isEmpty()) {

            LOGGER.info("going to create RedissonClientConfiguration");
            redissonClientConfiguration = new RedissonClientConfiguration();
            redissonClientConfiguration.setAddress(bpmsRedisAddress);
            redissonClientConfiguration.setUserName(bpmsRedisUserName);
            redissonClientConfiguration.setPassword(bpmsRedisPassword);
            redissonClientConfiguration.setDataBaseId(Integer.parseInt(Configuration.getInstance().getConfiguration(BPMSConfigurationKey.BPMS_REDIS_DATABASEID)));
            redissonClientConfiguration.setExpirationTime(Long.parseLong(Configuration.getInstance().getConfiguration(BPMSConfigurationKey.BPMS_REDIS_TTL_OBJECT)));
            redissonClientConfiguration.setDefaultCacheName(Configuration.getInstance().getConfiguration(BPMSConfigurationKey.BPMS_REDIS_CACHENAME));
        }

        LOGGER.info("end CreatRedissonClientConfiguration with {}", redissonClientConfiguration);
    }

    private static void initRateLimits() throws IOException {
        LOGGER.info("start CreatRedissonClientConfiguration");
        String rateLimitConfigurations = Configuration.getInstance().getComplexConfiguration(BPMSComplexConfigurationKey.BPMS_REDIS_ALL_RATE_LIMIT);
        if (redissonClient != null && rateLimitConfigurations != null && !rateLimitConfigurations.isEmpty()) {
            LOGGER.info("going to create  rate limits");
            RateLimiterConfiguration[] rateLimiterConfigurations = MapperUtil.getObject(rateLimitConfigurations, RateLimiterConfiguration[].class);
            if (rateLimiterConfigurations != null) {
                for (RateLimiterConfiguration limiterConfiguration : rateLimiterConfigurations) {
                    initSingleRateLimit(limiterConfiguration);
                }
            }
        }
        LOGGER.info("end CreatRedissonClientConfiguration");
    }

    private static void initSingleRateLimit(RateLimiterConfiguration limiterConfiguration) {
        LOGGER.info("start initSingleRateLimit");
        if (limiterConfiguration != null) {
            String processRateLimiterName = limiterConfiguration.getProcessRateLimiterName();
            Long maxRateLimit = limiterConfiguration.getMaxRateLimit();
            Long timeInterval = limiterConfiguration.getTimeInterval();
            RateLimiterStatus rateLimiterStatus = limiterConfiguration.getRateLimiterStatus();
            LOGGER.info("initSingleRateLimit method called with inputs processRateLimiterName:{} maxRateLimit:{} timeInterval:{} rateLimiterStatus:{}",
                    processRateLimiterName, maxRateLimit, timeInterval, rateLimiterStatus);
            if (limiterConfiguration.getRateLimiterStatus() == RateLimiterStatus.ENABLE) {
                LOGGER.info("going to init rate limit with processRateLimiterName:{} ", processRateLimiterName);
                RRateLimiter rRateLimiter;
                Long startTime = System.currentTimeMillis();
                try {
                    rRateLimiter = redissonClient.getRateLimiter(processRateLimiterName);
                    rRateLimiter.setRate(RateType.OVERALL, maxRateLimit, timeInterval, RateIntervalUnit.MINUTES);
                } finally {
                    LOGGER.info("{ \"RedissonAction\" : \"End initSingleRateLimit\" , \"RedissonTime\" : " + (System.currentTimeMillis() - startTime) + " }");
                }

                limiterHashMap.put(processRateLimiterName, rRateLimiter);
            }
        }
    }

    private static void initRedissonClient() {
        LOGGER.info("start initRedissonClient");
        CreatRedissonClientConfiguration();
        if (redissonClientConfiguration != null) {
            Config config = new Config();
            config.useSingleServer().setUsername(redissonClientConfiguration.getUserName());
            config.useSingleServer().setDatabase(redissonClientConfiguration.getDataBaseId());
            config.useSingleServer().setAddress(redissonClientConfiguration.getAddress());
            config.useSingleServer().setPassword(redissonClientConfiguration.getPassword());
            Long startTime = System.currentTimeMillis();
            try {
                redissonClient = Redisson.create(config);
            } finally {
                LOGGER.info("{ \"RedissonAction\" : \"End initRedissonClient\" , \"RedissonTime\" : " + (System.currentTimeMillis() - startTime) + " }");
            }
        } else {
            LOGGER.warn("redissonClientConfiguration is null ");
        }
    }

    public RedissonClient getRedissonClient() {
        return redissonClient;
    }

    public RedissonClientConfiguration getRedissonClientConfiguration() {
        return redissonClientConfiguration;
    }

    public HashMap<String, RRateLimiter> getLimiterHashMap() {
        return limiterHashMap;
    }

    @Override
    public void reset() {
        distributedRedissonCacheManagerInit();
    }
}