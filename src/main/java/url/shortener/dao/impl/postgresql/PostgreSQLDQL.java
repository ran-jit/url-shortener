package url.shortener.dao.impl.postgresql;

import com.google.inject.Inject;
import url.shortener.dao.impl.mysql.MySQLDAO;
import url.shortener.data.Config;

public class PostgreSQLDQL extends MySQLDAO {

    @Inject
    public PostgreSQLDQL(Config.DatabaseConfig config) {
        super(config);
    }
}
