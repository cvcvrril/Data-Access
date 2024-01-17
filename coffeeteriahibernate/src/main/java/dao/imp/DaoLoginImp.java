package dao.imp;

import common.Configuration;
import dao.DaoLogin;
import jakarta.inject.Inject;
import model.Credential;

public class DaoLoginImp implements DaoLogin {

    private final Configuration conf;

    @Inject
    public DaoLoginImp(Configuration conf) {
        this.conf = conf;
    }

    @Override
    public boolean doLogin(Credential credential) {
        return credential.getUserName().equals("root") && credential.getPassword().equals("2dam");
    }

}
