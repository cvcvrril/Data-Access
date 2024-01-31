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

    private Properties ptxt;
    private Properties pxml;
    private Properties psql;
    private String pathDataCustomers;
    private String pathDataOrders;
    private String pathXMLOrders;
    private String pathSQL;
    private String userSQL;
    private String passSQL;
    private String driver;

    private Configuration(){
        try{
            ptxt = new Properties();
            ptxt.load(getClass().getClassLoader().getResourceAsStream(ConstantsCommon.CONFIG_PROPERTIES_TXT));
            this.pathDataCustomers = ptxt.getProperty(ConstantsCommon.PATH_DATA_CUSTOMERS);
            this.pathDataOrders = ptxt.getProperty(ConstantsCommon.PATH_DATA_ORDERS);
            pxml = new Properties();
            pxml.loadFromXML(Configuration.class.getClassLoader().getResourceAsStream(ConstantsCommon.CONFIG_PROPERTIES_XML));
            this.pathXMLOrders = pxml.getProperty(ConstantsCommon.XML_ORDERS_PATH);
            psql = new Properties();
            psql.loadFromXML(Configuration.class.getClassLoader().getResourceAsStream(ConstantsCommon.CONFIG_PROPERTIES_DB_XML));
            this.pathSQL = psql.getProperty(ConstantsCommon.PATH_DB);
            this.userSQL = psql.getProperty(ConstantsCommon.USER_DB);
            this.passSQL = psql.getProperty(ConstantsCommon.PASS_DB);
            this.driver = psql.getProperty(ConstantsCommon.DRIVER);

        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    public String getPropertyTXT(String key){
        return ptxt.getProperty(key);
    }

    public String getPropertyXML(String key){
        return pxml.getProperty(key);
    }

    public String getPropertySQL(String key){
        return psql.getProperty(key);
    }

}
