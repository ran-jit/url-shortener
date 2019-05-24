package url.shortener.dao.impl.mysql;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import url.shortener.dao.URLShorteningDAO;
import url.shortener.data.Config.DatabaseConfig;
import url.shortener.data.URLInfo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;

/** author: Ranjith Manickam @ 25 May' 2019 */
public class MySQLDAO implements URLShorteningDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(MySQLDAO.class);

    private static final String QUERY_INSERT = "INSERT INTO url_info (id, url, created_on) VALUES (?,?,?)";
    private static final String QUERY_SELECT = "SELECT id, url, created_on FROM url_info WHERE  id = ? ";
    private static final String QUERY_COUNT = "SELECT Count(url) FROM url_info WHERE  id = ?";

    private Connection connection;
    private final DatabaseConfig config;

    @Inject
    public MySQLDAO(DatabaseConfig config) {
        this.config = config;
        init();
    }

    /**
     * To initialize the database connection.
     */
    private void init() {
        try {
            Class.forName(this.config.getDriver());
            this.connection = DriverManager.getConnection(this.config.getUrl(), this.config.getUsername(), this.config.getPassword());
        } catch (ClassNotFoundException | SQLException ex) {
            LOGGER.error("Error initializing database connection", ex);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void save(URLInfo urlInfo) {
        try (PreparedStatement statement = this.connection.prepareStatement(QUERY_INSERT)) {
            statement.setString(1, urlInfo.getId());
            statement.setString(2, urlInfo.getUrl());
            statement.setDate(3, Date.valueOf(LocalDate.now()));
        } catch (SQLException ex) {
            LOGGER.error("Error in save: {} - ex: {}", urlInfo, ex);
        }
    }

    /** {@inheritDoc} */
    @Override
    public URLInfo get(String id) {
        URLInfo urlInfo = null;
        try (PreparedStatement statement = this.connection.prepareStatement(QUERY_SELECT)) {
            statement.setString(1, id);

            try (ResultSet result = statement.executeQuery()) {
                if (result != null && result.next()) {
                    Timestamp timestamp = result.getTimestamp(3);
                    urlInfo = URLInfo.builder()
                            .id(result.getString(1))
                            .url(result.getString(2))
                            .createdOn(new java.util.Date(timestamp.getTime()))
                            .build();
                }
            }

        } catch (SQLException ex) {
            LOGGER.error("Error in get: {} - ex: {}", id, ex);
        }
        return urlInfo;
    }

    /** {@inheritDoc} */
    @Override
    public Boolean isExists(String id) {
        Boolean exists = Boolean.FALSE;
        try (PreparedStatement statement = this.connection.prepareStatement(QUERY_COUNT)) {
            statement.setString(1, id);

            try (ResultSet result = statement.executeQuery()) {
                if (result != null && result.next()) {
                    exists = (result.getInt(1) < 0);
                }
            }

        } catch (SQLException ex) {
            LOGGER.error("Error in get: {} - ex: {}", id, ex);
        }
        return exists;
    }
}
