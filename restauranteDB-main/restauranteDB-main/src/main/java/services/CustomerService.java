package services;

import dao.implJDBC.CustomersDAOdb;
import dao.implJDBC.DBConnection;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Customer;
import java.util.List;
import common.Error;

public class CustomerService {
    private final DBConnection db;
    public CustomersDAOdb dao;
    @Inject
    public CustomerService(DBConnection db) {
        this.db = db;
        this.dao=new CustomersDAOdb(db);
    }

    public Either<Error, List<Customer>> getAll() {
        return dao.getAll();
    }

    public Either<Error, Customer> get(int id) {
        return dao.get(id);
    }
    public Either<Error, Integer> save(Customer c) {
        return dao.save(c);
    }

    public Either<Error, Integer> update(Customer c) {
        return dao.update(c);
    }

    public Either<Error, Integer> delete(Customer c, boolean delete) {
        return dao.delete(c, delete);
    }
    public int getIdByName(String name){
        List<Customer> customerList=getAll().getOrNull();
        int customerID = 0;
        for (Customer customer:customerList) {
            if(name.equalsIgnoreCase(customer.getFirstName()+" "+customer.getLastName())){
                customerID= customer.getId();
            }
        }
        return customerID;
    }
}
