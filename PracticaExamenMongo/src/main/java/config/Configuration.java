package config;

import jakarta.inject.Singleton;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.util.Properties;

@Singleton
@Log4j2
@Data
public class Configuration {

    private Properties psql;
    private static Configuration instance = null;

    private Configuration(){
        try {
            psql = new Properties();
            psql.loadFromXML(Configuration.class.getClassLoader().getResourceAsStream("config/properties.xml"));
        }catch (Exception e){
         log.error(e.getMessage(), e);
        }
    }

    public static Configuration getInstance() {
        if (instance == null) {
            instance = new Configuration();
        }
        return instance;
    }

    public String getPropertySQL(String key){
        return psql.getProperty(key);
    }

}
