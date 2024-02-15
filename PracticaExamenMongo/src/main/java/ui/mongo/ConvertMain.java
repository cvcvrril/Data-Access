package ui.mongo;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import services.mongo.ConverterHibernateToMongo;

public class ConvertMain {

    public static void main(String[] args) {

        SeContainerInitializer seContainerInitializer = SeContainerInitializer.newInstance();
        final SeContainer container = seContainerInitializer.initialize();
        ConverterHibernateToMongo converterHibernateToMongo = container.select(ConverterHibernateToMongo.class).get();

        if (converterHibernateToMongo.convert().isRight()){
            System.out.println("Data transfered correclty");
        }else {
            System.out.println("There was an error");
        }

    }

}
