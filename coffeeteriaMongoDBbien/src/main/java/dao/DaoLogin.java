package dao;

import model.Credential;
import model.mongo.CredentialMongo;

public interface DaoLogin {
    boolean doLogin(CredentialMongo credential);

}
