package common;

import jakarta.inject.Singleton;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.util.Properties;

@Singleton
@Log4j2
@Data
public class Configuration {
    private Properties pxml;
    private String pathDB;
    private String userDB;
    private String passDB;
    private String driver;

    private Configuration(){
        try {
            pxml = new Properties();
            pxml.load(getClass().getClassLoader().getResourceAsStream("config/properties.xml"));
            this.pathDB = pxml.getProperty("pathDB");
            this.userDB = pxml.getProperty("userDB");
            this.passDB = pxml.getProperty("passDB");
            this.driver = pxml.getProperty("driver");
        } catch (IOException e) {
            log.error(e.getMessage(),e);
        }
    }
}
