package url.shortener.cache;

import url.shortener.data.URLInfo;

/** author: Ranjith Manickam @ 25 May' 2019 */
public interface URLShorteningCache {

    /**
     * To add URL data to cache.
     *
     * @param urlInfo - URL data to be added.
     */
    void set(URLInfo urlInfo);

    /**
     * To get URL data from cache.
     *
     * @param id - unique id.
     * @return - returns the URL data.
     */
    URLInfo get(String id);
}
