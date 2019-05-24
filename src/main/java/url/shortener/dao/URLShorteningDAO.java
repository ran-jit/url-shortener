package url.shortener.dao;

import url.shortener.data.URLInfo;

public interface URLShorteningDAO {

    void create(URLInfo info);

    URLInfo get(String id);

    Boolean isExists(String id);

}
