package dao.db;

import common.Configuration;
import common.SQLqueries;
import dao.ConstantsDao;
import dao.connection.DbConnection;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import model.Credential;
import model.errors.ErrorCCredential;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class DaoCredentials {


    private final DbConnection db;

    @Inject
    public DaoCredentials(Configuration config, DbConnection db) {
        this.db = db;
    }

    public Either<ErrorCCredential, List<Credential>> getAll(){
        List<Credential> credentialList;
        Either<ErrorCCredential, List<Credential>> res;
        try (Connection myConnection = db.getConnection()){
            Statement stmt = myConnection.createStatement();
            ResultSet rs = stmt.executeQuery(SQLqueries.SELECT_FROM_CREDENTIAL);
            credentialList = readRS(rs);
            res = Either.right(credentialList);
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            res = Either.left(new ErrorCCredential(e.getMessage(), 0));
        }
        return res;
    }

    public Either<ErrorCCredential, Credential> get(int id){
        Either<ErrorCCredential, Credential> res;
        try (Connection myConnection = db.getConnection()){
            PreparedStatement stmt = myConnection.prepareStatement(SQLqueries.SELECT_FROM_CREDENTIAL_WHERE_ID);
            stmt.setInt(1,id);
            ResultSet rs = stmt.executeQuery();
            List<Credential>credentialList = readRS(rs);
            if (!credentialList.isEmpty()){
                res = Either.right(credentialList.get(0));
            } else {
                res = Either.left(new ErrorCCredential(ConstantsDao.ERROR_READING_DATABASE, 0));
            }
        } catch (SQLException e) {
            log.error(e.getMessage(),e);
            res = Either.left(new ErrorCCredential(e.getMessage(), 0));
        }
        return res;
    }

    private List<Credential> readRS(ResultSet rs) throws SQLException {
        List<Credential> credentialList = new ArrayList<>();
        while (rs.next()){
            int id = rs.getInt("id");
            String user = rs.getString("username");
            String password = rs.getString("password");
            credentialList.add(new Credential(id, user, password));
        }
        return credentialList;
    }
}
