package ir.radman.common.general.exception.system.redisson;

public class RedissonCheckLockException extends RedissonException{

    public RedissonCheckLockException(String message){
        super(message);
    }
}
