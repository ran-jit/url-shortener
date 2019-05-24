package url.shortener.dao.impl.postgresql;

import com.google.inject.Inject;
import url.shortener.dao.impl.mysql.MySQLDAO;
import url.shortener.data.Config;

/** author: Ranjith Manickam @ 25 May' 2019 */
public class PostgreSQLDQL extends MySQLDAO {

    @Inject
    public PostgreSQLDQL(Config.DatabaseConfig config) {
        super(config);
    }
}
