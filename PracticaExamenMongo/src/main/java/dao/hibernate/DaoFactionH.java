package dao.hibernate;

import config.connection.JPAUtil;
import data.error.ErrorObject;
import data.hibernate.Faction;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;

public class DaoFactionH {

    /**
     * Si se necesita objetos externos, en los Daos NO se puede llamar a los servicios; se tira del otro Dao
     *
     * **/

    private final JPAUtil jpaUtil;
    private EntityManager em;

    @Inject
    public DaoFactionH(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }

    public Either<ErrorObject, List<Faction>> getAll(){
        Either<ErrorObject, Integer> res;
        try {

        }catch (Exception e){

        }finally {
            em.close();
        }
        return null;
    }


}
