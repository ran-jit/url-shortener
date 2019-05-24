package url.shortener.cache.impl;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;
import url.shortener.cache.URLShorteningCache;
import url.shortener.data.Config.RedisConfig;
import url.shortener.data.URLInfo;

/** author: Ranjith Manickam @ 25 May' 2019 */
public class RedisCache implements URLShorteningCache {

    private URLShorteningCache cache;

    public RedisCache(RedisConfig config) {
        initialize(config);
    }

    /** {@inheritDoc} */
    @Override
    public void set(URLInfo urlInfo) {
        this.cache.set(urlInfo);
    }

    /** {@inheritDoc} */
    @Override
    public URLInfo get(String id) {
        return this.cache.get(id);
    }

    /**
     * To initialize redis data cache.
     *
     * @param config - Redis configuration.
     */
    private void initialize(RedisConfig config) {
        String hosts = config.getHosts().replaceAll("\\s", "");
        String[] hostPorts = hosts.split(",");

        for (String hostPort : hostPorts) {
            String[] hostPortArr = hostPort.split(":");
            switch (config.getConfigType()) {
                case CLUSTER:
                    config.getClusterNodes().add(new HostAndPort(hostPortArr[0], Integer.valueOf(hostPortArr[1])));
                    break;
                case SENTINEL:
                    config.getSentinelNodes().add(new HostAndPort(hostPortArr[0], Integer.valueOf(hostPortArr[1])).toString());
                    break;
                default:
                    int port = Integer.valueOf(hostPortArr[1]);
                    if (!hostPortArr[0].isEmpty() && port > 0) {
                        config.setHost(hostPortArr[0]);
                        config.setPort(port);
                        break;
                    }
            }
        }
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMinIdle(config.getMinIdle());
        poolConfig.setMaxIdle(config.getMaxIdle());
        poolConfig.setMaxTotal(config.getMaxActive());
        poolConfig.setTestOnBorrow(config.getTestOnBorrow());
        poolConfig.setTestOnReturn(config.getTestOnReturn());
        poolConfig.setTestWhileIdle(config.getTestWhileIdle());
        poolConfig.setNumTestsPerEvictionRun(config.getTestNumPerEviction());
        poolConfig.setTimeBetweenEvictionRunsMillis(config.getTimeBetweenEviction());
        config.setJedisPoolConfig(poolConfig);
        config.setTimeout((config.getTimeout() < Protocol.DEFAULT_TIMEOUT) ? Protocol.DEFAULT_TIMEOUT : config.getTimeout());

        switch (config.getConfigType()) {
            case CLUSTER:
                this.cache = new RedisCluterManager(config);
                break;
            case SENTINEL:
                this.cache = new RedisSentinelManager(config);
                break;
            default:
                this.cache = new RedisManager(config);
                break;
        }
    }
}
