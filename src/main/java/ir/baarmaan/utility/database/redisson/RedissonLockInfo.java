package ir.baarmaan.utility.database.redisson;

public class RedissonLockInfo {
    private String lockItem;
    private Long expireTime;

    public RedissonLockInfo() {
    }

    public RedissonLockInfo(String lockItem, Long expireTime) {
        this.lockItem = lockItem;
        this.expireTime = expireTime;
    }

    public String getLockItem() {
        return lockItem;
    }

    public void setLockItem(String lockItem) {
        this.lockItem = lockItem;
    }

    public Long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }
}
