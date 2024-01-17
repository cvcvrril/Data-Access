package services;

import dao.db.DaoCredentials;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Credential;
import model.errors.ErrorCCredential;

import java.util.List;

public class ServiceCredential {

    private final DaoCredentials daOcredentials;

    @Inject
    public ServiceCredential(DaoCredentials daOcredentials) {
        this.daOcredentials = daOcredentials;
    }


    public Either<ErrorCCredential, List<Credential>> getAll(){
        return daOcredentials.getAll();
    }
    public Either<ErrorCCredential, Credential> get (int id){
        return daOcredentials.get(id);
    }
}
