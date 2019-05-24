package url.shortener.dao.impl.bigtable;

import url.shortener.dao.URLShorteningDAO;
import url.shortener.data.URLInfo;

public class BigtableDAO implements URLShorteningDAO {

    @Override
    public void create(URLInfo info) {
        
    }

    @Override
    public URLInfo get(String id) {
        return null;
    }

    @Override
    public Boolean isExists(String id) {
        return null;
    }
}
