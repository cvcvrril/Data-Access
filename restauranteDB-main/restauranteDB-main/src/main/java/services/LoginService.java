package services;

import common.Error;
import dao.LoginDAO;
import dao.implJDBC.DBConnection;
import dao.implJDBC.LoginDAOdb;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Credentials;

import java.util.List;

public class LoginService {

    public LoginDAO dao;
    private final DBConnection db;
    @Inject
    public LoginService(DBConnection db) {
        this.db = db;
        this.dao=new LoginDAOdb(this.db);
    }

    public Either<Error, Credentials> get(int id) {return dao.get(id);}
    public Either<Error, List<Credentials>> getAll(){return dao.getAll();}
}
