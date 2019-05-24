package url.shortener.cache.impl;

public interface RedisConstants {

    String REDIS_CONN_FAILED_RETRY_MSG = "Jedis connection failed, retrying...";

    enum RedisConfigType {
        DEFAULT,
        SENTINEL,
        CLUSTER
    }
}
