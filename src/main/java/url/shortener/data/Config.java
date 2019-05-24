package url.shortener.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import url.shortener.cache.impl.RedisConstants.RedisConfigType;

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
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RedisConfig {
        private RedisConfigType configType;
        private String hosts;
        private String password;
        private int database;
        private int timeout;
        private int maxActive;
        private boolean testOnBorrow;
        private boolean testOnReturn;
        private int maxIdle;
        private int minIdle;
        private boolean testWhileIdle;
        private int testNumPerEviction;
        private long timeBetweenEviction;
    }
}
