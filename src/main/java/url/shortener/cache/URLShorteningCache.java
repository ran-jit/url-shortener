package url.shortener.cache;

import url.shortener.data.URLInfo;

public interface URLShorteningCache {

    void create(URLInfo urlInfo);

    URLInfo get(String id);

}
