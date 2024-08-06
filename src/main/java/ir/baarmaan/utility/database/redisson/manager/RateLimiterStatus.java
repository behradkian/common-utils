package ir.baarmaan.utility.database.redisson.manager;


public enum RateLimiterStatus {
    ENABLE (1),
    DISABLE (0);

    private int code;

    RateLimiterStatus(int code) {
        this.code = code;
    }

    public Integer getValue() {
        return code;
    }

    public static RateLimiterStatus formatInteger(int value) {
        switch (value) {
            case 1:
                return ENABLE;
            case 0:
                return DISABLE;
            default:
                return null;
        }
    }
}
