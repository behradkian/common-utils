package ir.baarmaan.general.exception.redisson;

public class RedissonException extends Exception{
    public RedissonException(String message){
        super(message);
    }
    public RedissonException(Throwable throwable){
        super(throwable);
    }
    public RedissonException(String message,Throwable throwable){
        super(message,throwable);
    }
}
