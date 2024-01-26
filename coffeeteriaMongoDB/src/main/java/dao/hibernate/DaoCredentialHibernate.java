package dao.hibernate;

import dao.connection.JPAUtil;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import lombok.extern.log4j.Log4j2;
import model.Credential;
import model.errors.ErrorCCredential;

import java.util.List;

@Log4j2
public class DaoCredentialHibernate {

    private final JPAUtil jpaUtil;
    private EntityManager em;

    @Inject
    public DaoCredentialHibernate(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }

    public Either<ErrorCCredential, List<Credential>> getAll(){
        Either<ErrorCCredential, List<Credential>> res;
        List<Credential> credentialList;
        em = jpaUtil.getEntityManager();
        try {
            credentialList = em
                    .createNamedQuery("HQL_GET_ALL_CREDENTIAL", Credential.class)
                    .getResultList();
            res = Either.right(credentialList);
        }catch (PersistenceException e){
            log.error(e.getMessage(), e);
            res = Either.left(new ErrorCCredential(e.getMessage(), 0 ));
        }finally {
            if (em != null) em.close();
        }
        return res;
    }

    public Either<ErrorCCredential, Credential> get(int id){
        Either<ErrorCCredential, Credential> res;
        Credential credential;
        em = jpaUtil.getEntityManager();
        try {
            credential = em.find(Credential.class, id);
            res = Either.right(credential);
        }catch (PersistenceException e){
            log.error(e.getMessage(), e);
            res = Either.left(new ErrorCCredential(e.getMessage(), 0 ));
        }finally {
            if (em != null) em.close();
        }
        return res;
    }

}
