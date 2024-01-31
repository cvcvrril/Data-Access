package dao;

import io.vavr.control.Either;
import model.Credential;
import model.Customer;

import model.errors.ErrorCCustomer;

import java.util.List;

public interface DaoCustomer {

    Either<ErrorCCustomer, List<Customer>> getAll();

    Either<ErrorCCustomer, Customer> get(int i);

    Either<ErrorCCustomer, Integer> add(Customer newCustomer, Credential newCredential);

    Either<ErrorCCustomer, Integer> update(Customer updatedCustomer);

    Either<ErrorCCustomer, Integer> delete(int id, boolean conf);


}
