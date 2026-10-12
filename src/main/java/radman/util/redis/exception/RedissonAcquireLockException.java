package radman.util.redis.exception;

public class RedissonAcquireLockException extends RedissonException {
    public RedissonAcquireLockException(String message) {
        super(message);
    }

}
