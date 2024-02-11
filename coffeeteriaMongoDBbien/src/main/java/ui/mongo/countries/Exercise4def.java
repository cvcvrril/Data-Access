package ui.mongo.countries;

import dao.aggregations.DaoAggregationsCountries;
import services.mongo.aggregations.ServiceCountries;

public class Exercise4def {

    public static void main(String[] args) {

        DaoAggregationsCountries daoAggregationsCountries = new DaoAggregationsCountries();
        ServiceCountries serviceCountries = new ServiceCountries(daoAggregationsCountries);

        System.out.println("4. Identify countries with the most common bordering countries. Show the first 5 ones:");
        System.out.println("");

        System.out.println(serviceCountries.exercise4());


    }

}
