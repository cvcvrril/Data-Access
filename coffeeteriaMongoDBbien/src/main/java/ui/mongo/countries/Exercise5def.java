package ui.mongo.countries;

import dao.aggregations.DaoAggregationsCountries;
import services.mongo.aggregations.ServiceCountries;

public class Exercise5def {

    public static void main(String[] args) {

        DaoAggregationsCountries daoAggregationsCountries = new DaoAggregationsCountries();
        ServiceCountries serviceCountries = new ServiceCountries(daoAggregationsCountries);

        System.out.println("5. Identify regions with the highest number of landlocked countries");
        System.out.println("");

        System.out.println(serviceCountries.exercise5());


    }
}
