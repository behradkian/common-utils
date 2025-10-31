package ir.radman.common.general.redisson.manager;

public class RateLimiterConfiguration {

    private String processRateLimiterName;
    private Long timeInterval;
    private Long maxRateLimit;
    private RateLimiterStatus rateLimiterStatus;

    public RateLimiterConfiguration() {
    }

    public RateLimiterConfiguration(String processRateLimiterName, Long timeInterval, Long maxRateLimit, RateLimiterStatus rateLimiterStatus) {
        this.processRateLimiterName = processRateLimiterName;
        this.timeInterval = timeInterval;
        this.maxRateLimit = maxRateLimit;
        this.rateLimiterStatus = rateLimiterStatus;
    }

    public String getProcessRateLimiterName() {
        return processRateLimiterName;
    }

    public void setProcessRateLimiterName(String processRateLimiterName) {
        this.processRateLimiterName = processRateLimiterName;
    }

    public Long getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(Long timeInterval) {
        this.timeInterval = timeInterval;
    }

    public Long getMaxRateLimit() {
        return maxRateLimit;
    }

    public void setMaxRateLimit(Long maxRateLimit) {
        this.maxRateLimit = maxRateLimit;
    }

    public RateLimiterStatus getRateLimiterStatus() {
        return rateLimiterStatus;
    }

    public void setRateLimiterStatus(RateLimiterStatus rateLimiterStatus) {
        this.rateLimiterStatus = rateLimiterStatus;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RateLimiterConfiguration{");
        sb.append("processRateLimiterName='").append(processRateLimiterName).append('\'');
        sb.append(", timeInterval=").append(timeInterval);
        sb.append(", maxRateLimit=").append(maxRateLimit);
        sb.append(", rateLimiterStatus=").append(rateLimiterStatus);
        sb.append('}');
        return sb.toString();
    }
}
