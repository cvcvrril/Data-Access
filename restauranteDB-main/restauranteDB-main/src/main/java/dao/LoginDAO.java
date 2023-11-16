package dao;

import common.Error;
import io.vavr.control.Either;
import model.Credentials;

import java.util.List;

public interface LoginDAO {
    Either<Error,Credentials>  get(int id);
    Either<Error,List<Credentials>> getAll();
}
