package dao.imp;

import common.Configuration;
import dao.DaoLogin;
import jakarta.inject.Inject;
import model.Credential;
import model.mongo.CredentialMongo;

public class DaoLoginImp implements DaoLogin {

    private final Configuration conf;

    @Inject
    public DaoLoginImp(Configuration conf) {
        this.conf = conf;
    }

    @Override
    public boolean doLogin(CredentialMongo credential) {
        return credential.getUsername().equals("root") && credential.getPassword().equals("2dam");
    }

}
