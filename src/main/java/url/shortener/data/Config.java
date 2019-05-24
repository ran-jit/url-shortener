package url.shortener.data;

import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;
import url.shortener.cache.impl.RedisConstants.RedisConfigType;

import java.util.Set;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Config {
    private String baseUrl;
    private Integer shortenURLSize;

    public String getBaseUrl() {
        return StringUtils.isNotEmpty(this.baseUrl) && this.baseUrl.endsWith("/") ? this.baseUrl : this.baseUrl.concat("/");
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RedisConfig {
        private RedisConfigType configType;
        private String hosts = String.format("%s:%s", Protocol.DEFAULT_HOST, Protocol.DEFAULT_PORT);
        private String password = null;
        private Integer database = 0;
        private Integer timeout = Protocol.DEFAULT_TIMEOUT;
        private Integer maxActive = 10;
        private Boolean testOnBorrow = Boolean.TRUE;
        private Boolean testOnReturn = Boolean.TRUE;
        private Integer maxIdle = 5;
        private Integer minIdle = 1;
        private Boolean testWhileIdle = Boolean.TRUE;
        private Integer testNumPerEviction = 10;
        private Long timeBetweenEviction = 60000L;
        private String masterName = "mymaster";

        private String host;
        private Integer port;
        private Set<String> sentinelNodes = Sets.newHashSet();
        private Set<HostAndPort> clusterNodes = Sets.newHashSet();
        private JedisPoolConfig jedisPoolConfig;
    }
}
