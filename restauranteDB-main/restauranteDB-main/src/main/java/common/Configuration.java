package common;
import jakarta.inject.Singleton;
import java.io.IOException;
import java.util.Properties;

@Singleton
public class Configuration {
        private static Configuration instance;
        private Properties p;
        private Properties pxml;

        private Configuration() {
            try {
                p= new Properties();
                p.load(Configuration.class.getClassLoader().getResourceAsStream("configFiles/properties"));
                pxml= new Properties();
                pxml.loadFromXML(Configuration.class.getClassLoader().getResourceAsStream("configFiles/properties.xml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public static Configuration getInstance() {
            if (instance==null) {
                instance=new Configuration();
            }
            return instance;
        }

        public String getProperty(String key) {
            return p.getProperty(key);
        }
        public String getPropertyXML(String key) {
        return pxml.getProperty(key);
    }

}
