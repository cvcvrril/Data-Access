package ui.mongo.countries;

import dao.aggregations.DaoAggregationsCountries;
import services.mongo.aggregations.ServiceCountries;

public class Exercise1def {

    public static void main(String[] args) {
        DaoAggregationsCountries daoAggregationsCountries = new DaoAggregationsCountries();
        ServiceCountries serviceCountries = new ServiceCountries(daoAggregationsCountries);

        System.out.println("1. Find the translation to Spanish of the bigest country (area) which region is Europe, currency is EUR :");
        System.out.println("");

        System.out.println(serviceCountries.exercise1());
    }
}
