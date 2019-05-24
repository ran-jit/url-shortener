package url.shortener.manager;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import url.shortener.cache.URLShorteningCache;
import url.shortener.commons.IDGenerator;
import url.shortener.dao.URLShorteningDAO;
import url.shortener.data.Config;
import url.shortener.data.URLInfo;

import java.net.URL;
import java.util.stream.IntStream;

public class URLShorteningManager {

    private final Config config;

    private final URLShorteningDAO dao;
    private final URLShorteningCache cache;

    @Inject
    public URLShorteningManager(Config config,
                                URLShorteningDAO dao,
                                URLShorteningCache cache) {
        this.config = config;
        this.dao = dao;
        this.cache = cache;
    }

    public void shorten(URL url) {
        URLInfo urlInfo = URLInfo.builder().url(url.toString()).build();

        IDGenerator.generateHashCode(urlInfo);
        String id = generateId(urlInfo.getHashCode());

        if (StringUtils.isEmpty(id)) {
            // TODO: throw exception
        }

        urlInfo.setId(id);
        this.dao.create(urlInfo);
        this.cache.create(urlInfo);
    }

    public String resolve(URL url) {
        String id = url.toString().replace(this.config.getBaseUrl(), "");
        URLInfo urlInfo = this.cache.get(id);

        if (urlInfo == null) {
            urlInfo = this.dao.get(id);

            if (urlInfo != null) {
                this.cache.create(urlInfo);
                return urlInfo.getUrl();
            }
        }
        return null;
    }

    private String generateId(String hashCode) {
        if (StringUtils.isEmpty(hashCode)) {
            return null;
        }

        StringBuilder builder = new StringBuilder();
        IntStream.range(0, this.config.getShortenURLSize())
                .forEach(i -> {
                    int index = (int) (hashCode.length() * Math.random());
                    builder.append(hashCode.charAt(index));
                });

        Boolean exists = this.dao.isExists(builder.toString());
        if (Boolean.TRUE.equals(exists)) {
            return generateId(hashCode);
        }
        return builder.toString();
    }
}
