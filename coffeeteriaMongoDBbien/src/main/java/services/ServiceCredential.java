package services;

import dao.db.DaoCredentials;
import dao.hibernate.DaoCredentialHibernate;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Credential;
import model.errors.ErrorCCredential;

import java.util.List;

public class ServiceCredential {

    private final DaoCredentials daOcredentials;
    private final DaoCredentialHibernate daoCredentialHibernate;
    @Inject
    public ServiceCredential(DaoCredentials daOcredentials, DaoCredentialHibernate daoCredentialHibernate) {
        this.daOcredentials = daOcredentials;
        this.daoCredentialHibernate = daoCredentialHibernate;
    }

    public Either<ErrorCCredential, List<Credential>> getAll(){
        //return daOcredentials.getAll();
        return daoCredentialHibernate.getAll();
    }
    public Either<ErrorCCredential, Credential> get (int id){
        //return daOcredentials.get(id);
        return daoCredentialHibernate.get(id);
    }
}
