package ui.mongo.countries;

import dao.aggregations.DaoAggregationsCountries;
import services.mongo.aggregations.ServiceCountries;

public class Exercise2def {

    public static void main(String[] args) {

        DaoAggregationsCountries daoAggregationsCountries = new DaoAggregationsCountries();
        ServiceCountries serviceCountries = new ServiceCountries(daoAggregationsCountries);

        System.out.println("2. Find the currency of the first 4 countries with an area greater than 10000");
        System.out.println("");

        System.out.println(serviceCountries.exercise2());


    }
}
