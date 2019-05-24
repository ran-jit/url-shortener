package url.shortener.dao.impl.bigtable;

import url.shortener.dao.URLShorteningDAO;
import url.shortener.data.URLInfo;

/** author: Ranjith Manickam @ 25 May' 2019 */
public class BigtableDAO implements URLShorteningDAO {

    /** {@inheritDoc} */
    @Override
    public void save(URLInfo info) {

    }

    /** {@inheritDoc} */
    @Override
    public URLInfo get(String id) {
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public Boolean isExists(String id) {
        return null;
    }
}
