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

/** author: Ranjith Manickam @ 25 May' 2019 */
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

    /**
     * To shorten the actual URL.
     *
     * @param url - URL to be shortened.
     * @return - returns shortened URL.
     */
    public String shorten(URL url) {
        URLInfo urlInfo = URLInfo.builder().url(url.toString()).build();

        IDGenerator.generateHashCode(urlInfo);
        String id = generateId(urlInfo.getHashCode());

        if (StringUtils.isEmpty(id)) {
            // TODO: throw exception
        }

        urlInfo.setId(id);
        this.dao.save(urlInfo);
        this.cache.set(urlInfo);
        return id;
    }

    /**
     * To resolve the actual URL from shortened URL.
     *
     * @param url - shortened URL.
     * @return - returns actual URL.
     */
    public String resolve(URL url) {
        String id = url.toString().replace(this.config.getBaseUrl(), "");
        URLInfo urlInfo = this.cache.get(id);

        if (urlInfo == null) {
            urlInfo = this.dao.get(id);

            if (urlInfo != null) {
                this.cache.set(urlInfo);
                return urlInfo.getUrl();
            }
        }
        return null;
    }

    /**
     * To generate unique id from the hashcode.
     *
     * @param hashCode - base62 encoded value.
     * @return - returns the unique id.
     */
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
