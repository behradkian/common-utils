package ir.baarmaan.utility.database;

import redis.clients.jedis.Jedis;

public class RedisConnection {

    public static void main(String[] args) {
       setData("Ali", "Asghar");
       getData("Ali");
    }

    public static void getData(String key){
        Jedis jedis = new Jedis("localhost", 6379);
        // دریافت داده از Redis
        String value = jedis.get(key);
        System.out.println("داده دریافت شد: key -> " +  value);
        jedis.close();
    }

    public static void setData(String key, String value){
        // اتصال به سرور Redis
        Jedis jedis = new Jedis("localhost", 6379);

        // بررسی ارتباط
        System.out.println("اتصال به سرور Redis برقرار شد");
        System.out.println("وضعیت سرور Redis: " + jedis.ping());

        // ذخیره داده در Redis
        jedis.set(key, value);
        System.out.println("داده ذخیره شد: key -> value");
        jedis.close();
    }
}
