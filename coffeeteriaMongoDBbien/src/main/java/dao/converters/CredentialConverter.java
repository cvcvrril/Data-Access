package dao.converters;

import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import model.Credential;
import model.errors.ErrorCObject;
import model.mongo.CredentialMongo;

@Log4j2
public class CredentialConverter {

    public Either<ErrorCObject, Credential> fromMongoToHibernateCredential(){
        Either<ErrorCObject, Credential> res;
        return null;
    }

    public Either<ErrorCObject, CredentialMongo> fromHibernateToMongoCredential(){
        Either<ErrorCObject, CredentialMongo> res;
        return null;
    }


}
