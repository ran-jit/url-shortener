package url.shortener.dao.impl.dynamodb;

import url.shortener.dao.URLShorteningDAO;
import url.shortener.data.URLInfo;

public class DynamoDBDAO implements URLShorteningDAO {

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
