package url.shortener.cache.impl;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import java.util.Set;

public final class RedisSentinelManager extends RedisManager {

    private static final long FAILIURE_WAIT_TIME = 2000L;

    public RedisSentinelManager(Set<String> nodes,
                                String masterName,
                                String password,
                                int database,
                                int timeout,
                                JedisPoolConfig poolConfig) {
        super(new JedisSentinelPool(masterName, nodes, poolConfig, timeout, password, database), FAILIURE_WAIT_TIME);
    }
}