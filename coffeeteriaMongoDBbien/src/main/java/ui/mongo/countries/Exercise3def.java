package ui.mongo.countries;

import dao.aggregations.DaoAggregationsCountries;
import services.mongo.aggregations.ServiceCountries;

public class Exercise3def {

    public static void main(String[] args) {

        DaoAggregationsCountries daoAggregationsCountries = new DaoAggregationsCountries();
        ServiceCountries serviceCountries = new ServiceCountries(daoAggregationsCountries);

        System.out.println("3. Find the average area of countries in each region");
        System.out.println("");

        System.out.println(serviceCountries.exercise3());


    }

}
