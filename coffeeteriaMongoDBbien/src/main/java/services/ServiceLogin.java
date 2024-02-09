package services;

import dao.db.DaoCredentials;
import dao.DaoLogin;
import dao.mongo.DaoMongoCredential;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Credential;
import model.errors.ErrorCCredential;
import model.errors.ErrorCObject;
import model.mongo.CredentialMongo;

import java.util.ArrayList;
import java.util.List;

public class ServiceLogin {

    private final DaoLogin daOlogin;
    private final DaoCredentials daOcredentials;
    private final DaoMongoCredential daoMongoCredential;

    @Inject
    public ServiceLogin(DaoLogin daOlogin, DaoCredentials daOcredentials, DaoMongoCredential daoMongoCredential) {
        this.daOlogin = daOlogin;
        this.daOcredentials = daOcredentials;
        this.daoMongoCredential = daoMongoCredential;
    }

    public boolean doLogin(CredentialMongo credential){
        CredentialMongo storedCredential = get(credential.getUsername()).get();
        if (storedCredential != null && (storedCredential.getPassword().equals(credential.getPassword()))) {
                if (storedCredential.getUsername().equals("root")) {
                    daOlogin.doLogin(credential);
                    return true;
                } else {
                    daOlogin.doLogin(credential);
                    return true;
                }
        }
        return false;
    }

    public Credential getCredentialByUsername(String username) {
        List<Credential> credentials = daOcredentials.getAll().getOrElse(new ArrayList<>());
        for (Credential credential : credentials) {
            if (credential.getUserName().equals(username)) {
                return credential;
            }
        }
        return null;
    }

    public Either<ErrorCObject, List<CredentialMongo>> getAll(){
        return daoMongoCredential.getAll();
    }

    public Either<ErrorCObject, CredentialMongo> get(String username){
        return daoMongoCredential.get(username);
    }

}
