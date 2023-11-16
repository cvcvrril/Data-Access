package dao;

import io.vavr.control.Either;
import model.Customer;
import common.Error;
import java.util.List;

public interface CustomersDAO {
    Either<Error, List<Customer>> getAll();
    Either<Error, Customer> get(int id);
    Either<Error, Integer> save(Customer c);
    Either<Error, Integer> update(Customer c);
    Either<Error, Integer> delete(Customer c,boolean b);
}
