package url.shortener.cache.impl;

import redis.clients.jedis.JedisSentinelPool;
import url.shortener.data.Config.RedisConfig;

final class RedisSentinelManager extends RedisManager {

    private static final long FAILURE_WAIT_TIME = 2000L;

    RedisSentinelManager(RedisConfig config) {
        super(new JedisSentinelPool(config.getMasterName(), config.getSentinelNodes(), config.getJedisPoolConfig(), config.getTimeout(), config.getPassword(), config.getDatabase()), FAILURE_WAIT_TIME);
    }
}