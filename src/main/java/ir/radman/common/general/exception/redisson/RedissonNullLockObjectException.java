package ir.radman.common.general.exception.redisson;

public class RedissonNullLockObjectException extends RedissonException{
    public RedissonNullLockObjectException(String message){
        super(message);
    }
}