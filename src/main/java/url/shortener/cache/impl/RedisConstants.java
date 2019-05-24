package url.shortener.cache.impl;

/** author: Ranjith Manickam @ 25 May' 2019 */
public interface RedisConstants {

    String REDIS_CONN_FAILED_RETRY_MSG = "Jedis connection failed, retrying...";

    enum RedisConfigType {
        DEFAULT,
        SENTINEL,
        CLUSTER
    }
}
