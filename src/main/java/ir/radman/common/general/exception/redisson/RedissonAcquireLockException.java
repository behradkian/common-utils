package ir.radman.common.general.exception.redisson;

public class RedissonAcquireLockException extends RedissonException{
    public RedissonAcquireLockException(String message) {
        super(message);
    }

}
