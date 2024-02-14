package config.connection;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import common.Configuration;
import dao.ConstantsDao;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Singleton
public class DbConnectionPool {

    private final Configuration config;
    private final DataSource hikariDataSource;

    @Inject
    public DbConnectionPool(Configuration config) {
        this.config = config;
        this.hikariDataSource = getHikariPool();
    }

    private DataSource getHikariPool() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(config.getPropertySQL(ConstantsDao.PATH_DB));
        hikariConfig.setUsername(config.getPropertySQL(ConstantsDao.USER_DB));
        hikariConfig.setPassword(config.getPropertySQL(ConstantsDao.PASS_DB));
        hikariConfig.setDriverClassName(config.getPropertySQL(ConstantsDao.DRIVER));
        hikariConfig.setMaximumPoolSize(4);

        hikariConfig.addDataSourceProperty("cachePrepStmts", true);
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", 250);
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);

        return new HikariDataSource(hikariConfig);
    }

    public Connection getConnection() {
        Connection con=null;
        try {
            con = hikariDataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    @PreDestroy
    public void closePool() {
        ((HikariDataSource) hikariDataSource).close();
    }

    public DataSource getDataSource() {
        return hikariDataSource;
    }
}
