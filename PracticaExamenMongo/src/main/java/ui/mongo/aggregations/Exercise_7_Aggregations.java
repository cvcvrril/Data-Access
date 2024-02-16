package ui.mongo.aggregations;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import services.mongo.aggregations.ServiceAggregations;

public class Exercise_7_Aggregations {

    public static void main(String[] args) {

        SeContainerInitializer seContainerInitializer = SeContainerInitializer.newInstance();
        final SeContainer container = seContainerInitializer.initialize();
        ServiceAggregations serviceAggregations = container.select(ServiceAggregations.class).get();
        System.out.println(serviceAggregations.exercise7());

    }

}
