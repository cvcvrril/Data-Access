package dao.hibernate;

import config.connection.JPAUtil;
import data.error.ErrorObject;
import data.hibernate.Faction;
import data.hibernate.Weapon;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.time.LocalDateTime;
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
        Either<ErrorObject, List<Faction>> res;
        List<Faction> factionList;
        em = jpaUtil.getEntityManager();
        try {
            factionList = em
                    .createNamedQuery("HQL_GET_ALL_FACTIONS", Faction.class)
                    .getResultList();
            res = Either.right(factionList);
        }catch (Exception e){
            res = Either.left(new ErrorObject("Ha habido un error", 0, LocalDateTime.now()));
        }finally {
            em.close();
        }
        return res;
    }


}
