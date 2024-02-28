package common;

import jakarta.inject.Singleton;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.util.Properties;

/**
 * Es más probable que pida las propiedades a partir de un archivo XML, así que a tirar de ello
 * **/

@Log4j2
@Singleton
public class Configuration {
    private static Configuration instance = null;
    //private Properties propertiestxt;
    private Properties propertiesxml;

    private Configuration() {
        try {
            //text files
//            propertiestxt = new Properties();
//            propertiestxt.load(Objects.requireNonNull(Configuration.class.getClassLoader().getResourceAsStream(UtilitiesCommon.PROPERTIESTXT)));

            //xml files
            propertiesxml = new Properties();
            propertiesxml.loadFromXML(Configuration.class.getClassLoader().getResourceAsStream("configFiles/properties.xml"));
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public static Configuration getInstance() {
        if (instance == null) {
            instance = new Configuration();
        }
        return instance;
    }

//    public String getPropertyTXT(String key) {
//        return propertiestxt.getProperty(key);
//    }

    public String getPropertyXML(String key) {
        return propertiesxml.getProperty(key);
    }

}
