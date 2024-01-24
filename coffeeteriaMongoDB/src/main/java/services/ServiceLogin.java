package services;

import dao.db.DaoCredentials;
import dao.DaoLogin;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Credential;
import model.errors.ErrorCCredential;

import java.util.ArrayList;
import java.util.List;

public class ServiceLogin {

    private final DaoLogin daOlogin;
    private final DaoCredentials daOcredentials;

    @Inject
    public ServiceLogin(DaoLogin daOlogin, DaoCredentials daOcredentials) {
        this.daOlogin = daOlogin;
        this.daOcredentials = daOcredentials;
    }

    public boolean doLogin(Credential credential){
        Credential storedCredential = getCredentialByUsername(credential.getUserName());
        if (storedCredential != null && (storedCredential.getPassword().equals(credential.getPassword()))) {
                if (storedCredential.getId() < 0) {
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

    public Either<ErrorCCredential, List<Credential>> getAll(){
        return daOcredentials.getAll();
    }

    public Either<ErrorCCredential, Credential> get(int id){
        return daOcredentials.get(id);
    }

}
