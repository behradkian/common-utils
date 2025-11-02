package ir.radman.common.general.exception.system.redisson;

public class RedissonNullLockObjectException extends RedissonException{
    public RedissonNullLockObjectException(String message){
        super(message);
    }
}