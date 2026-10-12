package radman.util.redis.manager;

public class RedissonClientConfiguration {

    private String password;
    private Integer dataBaseId;
    private String address;
    private String userName;
    private Long expirationTime;
    private String defaultCacheName;

    public RedissonClientConfiguration() {
    }

    public RedissonClientConfiguration(String password, Integer dataBaseId, String address, String userName, Long expirationTime, String defaultCacheName) {
        this.password = password;
        this.dataBaseId = dataBaseId;
        this.address = address;
        this.userName = userName;
        this.expirationTime = expirationTime;
        this.defaultCacheName = defaultCacheName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getDataBaseId() {
        return dataBaseId;
    }

    public void setDataBaseId(Integer dataBaseId) {
        this.dataBaseId = dataBaseId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Long expirationTime) {
        this.expirationTime = expirationTime;
    }

    public String getDefaultCacheName() {
        return defaultCacheName;
    }

    public void setDefaultCacheName(String defaultCacheName) {
        this.defaultCacheName = defaultCacheName;
    }

    @Override
    public String toString() {
        String sb = "RedissonClientConfiguration{" + "password='" + password + '\'' +
                ", dataBaseId=" + dataBaseId +
                ", address='" + address + '\'' +
                ", userName='" + userName + '\'' +
                ", expirationTime=" + expirationTime +
                ", defaultCacheName='" + defaultCacheName + '\'' +
                '}';
        return sb;
    }
}
