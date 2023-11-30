package ui.pruebas;
import common.Configuration;
import common.DBConnection;
import domain.DaoSpy;
import domain.imp.DaoSpyImp;
import model.Spy;
import services.ServSpy;

import java.util.List;
public class PruebaSpy {
    public static void main(String[] args) {
        DBConnection dbConnection = new DBConnection(Configuration.getInstance());
        DaoSpy daoSpy = new DaoSpyImp(Configuration.getInstance(), dbConnection);
        ServSpy servSpy = new ServSpy(daoSpy);
        List<Spy> spyList = servSpy.getAll().get();
        System.out.println(spyList);
    }
}
