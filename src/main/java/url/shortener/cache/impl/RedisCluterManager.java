package url.shortener.cache.impl;

import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.Protocol;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisRedirectionException;
import url.shortener.data.Config.RedisConfig;
import url.shortener.data.URLInfo;

final class RedisCluterManager extends RedisManager {

    private final JedisCluster cluster;

    private static final int NUM_RETRIES = 30;
    private static final int DEFAULT_MAX_REDIRECTIONS = 5;
    private static final long FAILURE_WAIT_TIME = 4000L;

    RedisCluterManager(RedisConfig config) {
        super(null, FAILURE_WAIT_TIME);
        this.cluster = new JedisCluster(config.getClusterNodes(), config.getTimeout(), Protocol.DEFAULT_TIMEOUT, DEFAULT_MAX_REDIRECTIONS, config.getPassword(), config.getJedisPoolConfig());
    }

    @Override
    public void create(URLInfo urlInfo) {
        int tries = 0;
        boolean retry = true;
        do {
            tries++;
            try {
                this.cluster.set(urlInfo.getId(), urlInfo.getUrl());
                retry = false;
            } catch (JedisRedirectionException | JedisConnectionException ex) {
                handleException(tries, ex);
            }
        } while (retry && tries <= NUM_RETRIES);
    }

    @Override
    public URLInfo get(String id) {
        int tries = 0;
        boolean retry = true;
        byte[] url = null;
        do {
            tries++;
            try {
                url = this.cluster.get(id.getBytes());
                retry = false;
            } catch (JedisRedirectionException | JedisConnectionException ex) {
                handleException(tries, ex);
            }
        } while (retry && tries <= NUM_RETRIES);
        return url == null ? null : URLInfo.builder().url(new String(url)).id(id).build();
    }
}
