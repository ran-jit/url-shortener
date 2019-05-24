package url.shortener.dao;

import url.shortener.data.URLInfo;

/** author: Ranjith Manickam @ 25 May' 2019 */
public interface URLShorteningDAO {

    /**
     * To save URL data.
     *
     * @param info - URL data to be saved.
     */
    void save(URLInfo info);

    /**
     * To get URL data based on id.
     *
     * @param id - unique id.
     * @return - returns the URL data.
     */
    URLInfo get(String id);

    /**
     * To check unique id already exists.
     *
     * @param id - unique id.
     * @return - returns true if exists.
     */
    Boolean isExists(String id);
}
