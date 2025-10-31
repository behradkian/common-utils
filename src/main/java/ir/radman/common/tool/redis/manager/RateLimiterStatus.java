package ir.radman.common.tool.redis.manager;

public enum RateLimiterStatus {
    ENABLE (1),
    DISABLE (0);

    private final int code;

    RateLimiterStatus(int code) {
        this.code = code;
    }

    public Integer getValue() {
        return code;
    }

    public static RateLimiterStatus getByValue(int value) {
        return switch (value) {
            case 1 -> ENABLE;
            case 0 -> DISABLE;
            default -> null;
        };
    }
}