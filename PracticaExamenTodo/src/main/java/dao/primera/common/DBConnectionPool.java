package dao.primera.common;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import common.Configuration;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.extern.log4j.Log4j2;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Log4j2
@Singleton
public class DBConnectionPool {
    private final Configuration configuration;
    private final DataSource hikariDataSource;

    @Inject
    private DBConnectionPool(Configuration configuration){
        this.configuration = configuration;
        hikariDataSource = getHikariPool();
    }

    /**
     * Este es el Hikari. Le gusta mucho a Lucía. Usar este en vez del pool normal y corriente
     * **/

    private DataSource getHikariPool(){
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(configuration.getPropertyXML("sqlUrl"));
        hikariConfig.setUsername(configuration.getPropertyXML("sqlUser"));
        hikariConfig.setPassword(configuration.getPropertyXML("sqlPassword"));
        hikariConfig.setDriverClassName(configuration.getPropertyXML("driver"));
        hikariConfig.setMaximumPoolSize(4);

        hikariConfig.addDataSourceProperty("cachePrepStmts", true);
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", 250);
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);

        return new HikariDataSource(hikariConfig);

    }

    /**
     * Este get se toma para Spring
     * **/

    public DataSource getDataSource(){
        return hikariDataSource;
    }

    /**
     * Este get se toma para JDBC
     * **/

    public Connection getConnection(){
        Connection connection = null;
        try{
            connection = hikariDataSource.getConnection();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return connection;
    }

    /**
     * Esto lo que hace es cerrar las conexiones de forma manual (no es necesario)
     * **/

    public void closeConnection(Connection connection){
        try {
            connection.close();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * Esto lo que hace es que cuando termine la conexión te cierra la sesión, para no saturar la base de datos
     * **/

    @PreDestroy
    public void closePool(){
        ((HikariDataSource) hikariDataSource).close();
    }
}
