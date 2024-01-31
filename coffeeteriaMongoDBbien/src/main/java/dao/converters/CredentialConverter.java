package dao.converters;

import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import model.Credential;
import model.errors.ErrorCObject;
import model.mongo.CredentialMongo;

@Log4j2
public class CredentialConverter {

    public Either<ErrorCObject, Credential> fromMongoToHibernateCredential(CredentialMongo credentialMongo){
        Either<ErrorCObject, Credential> res;
        try {
            Credential credentialConverted = new Credential(credentialMongo.get_id(), credentialMongo.getUsername(), credentialMongo.getPassword());
            res = Either.right(credentialConverted);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            res = Either.left(new ErrorCObject(e.getMessage(), 0));
        }
        return res;
    }

    public Either<ErrorCObject, CredentialMongo> fromHibernateToMongoCredential(Credential credential){
        Either<ErrorCObject, CredentialMongo> res;
        try {
            CredentialMongo credentialMongo = new CredentialMongo(credential.getId(), credential.getUserName(), credential.getPassword());
            res = Either.right(credentialMongo);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            res = Either.left(new ErrorCObject(e.getMessage(), 0));
        }
        return res;
    }
}
