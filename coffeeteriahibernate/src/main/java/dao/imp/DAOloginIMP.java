package dao.imp;

import common.Configuration;
import dao.DAOlogin;
import jakarta.inject.Inject;
import model.Credential;

public class DAOloginIMP implements DAOlogin {

    private final Configuration conf;

    @Inject
    public DAOloginIMP(Configuration conf) {
        this.conf = conf;
    }

    @Override
    public boolean doLogin(Credential credential) {
        return credential.getUserName().equals("root") && credential.getPassword().equals("2dam");
    }

}
