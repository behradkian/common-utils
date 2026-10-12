package radman.util.redis.exception;

public class RedissonNullLockObjectException extends RedissonException {
    public RedissonNullLockObjectException(String message) {
        super(message);
    }
}