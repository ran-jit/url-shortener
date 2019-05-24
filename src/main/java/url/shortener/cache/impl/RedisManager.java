package url.shortener.cache.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.util.Pool;
import url.shortener.cache.URLShorteningCache;
import url.shortener.data.Config.RedisConfig;
import url.shortener.data.URLInfo;

/** author: Ranjith Manickam @ 25 May' 2019 */
class RedisManager implements URLShorteningCache {

    private static final int NUM_RETRIES = 3;
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisManager.class);

    private final Pool<Jedis> pool;
    private final long failureWaitTime;

    RedisManager(RedisConfig config) {
        this.pool = new JedisPool(config.getJedisPoolConfig(), config.getHost(), config.getPort(), config.getTimeout(), config.getPassword(), config.getDatabase());
        this.failureWaitTime = 2000L;
    }

    RedisManager(Pool<Jedis> pool, long failiureWaitTime) {
        this.pool = pool;
        this.failureWaitTime = failiureWaitTime;
    }

    /** {@inheritDoc} */
    @Override
    public void set(URLInfo urlInfo) {
        int tries = 0;
        boolean retry = true;
        do {
            tries++;
            try (Jedis jedis = this.pool.getResource()) {
                jedis.set(urlInfo.getId(), urlInfo.getUrl());
                retry = false;
            } catch (JedisConnectionException ex) {
                handleException(tries, ex);
            }
        } while (retry && tries <= NUM_RETRIES);
    }

    /** {@inheritDoc} */
    @Override
    public URLInfo get(String id) {
        int tries = 0;
        boolean retry = true;
        byte[] url = null;
        do {
            tries++;
            try (Jedis jedis = this.pool.getResource()) {
                url = jedis.get(id.getBytes());
                retry = false;
            } catch (JedisConnectionException ex) {
                handleException(tries, ex);
            }
        } while (retry && tries <= NUM_RETRIES);
        return url == null ? null : URLInfo.builder().url(new String(url)).id(id).build();
    }

    /**
     * To handle redis runtime exception
     *
     * @param tries - retry count
     * @param ex    - exception
     */
    void handleException(int tries, RuntimeException ex) {
        LOGGER.error(RedisConstants.REDIS_CONN_FAILED_RETRY_MSG + tries);
        if (tries == NUM_RETRIES) {
            throw ex;
        }
        try {
            Thread.sleep(this.failureWaitTime);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
