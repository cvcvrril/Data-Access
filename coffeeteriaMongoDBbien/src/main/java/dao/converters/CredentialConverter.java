package dao.converters;

import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import model.Credential;
import model.errors.ErrorCObject;
import model.mongo.CredentialMongo;

import java.util.ArrayList;
import java.util.List;

@Log4j2
public class CredentialConverter {

    public Either<ErrorCObject, List<CredentialMongo>> fromHibernateToMongoCredential(List<Credential> credentialList){
        Either<ErrorCObject, List<CredentialMongo>> res;
        List<CredentialMongo> credentialMongoList = new ArrayList<>();
        try {
            for (Credential credential: credentialList){
                CredentialMongo credentialMongo = new CredentialMongo(null, credential.getUserName(), credential.getPassword());
                credentialMongoList.add(credentialMongo);
            }
            res = Either.right(credentialMongoList);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            res = Either.left(new ErrorCObject(e.getMessage(), 0));
        }
        return res;
    }
}
