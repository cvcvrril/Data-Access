package dao.hibernate;

import config.connection.JPAUtil;
import data.error.ErrorObject;
import data.hibernate.gen.WeaponsFactionsEntity;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.time.LocalDateTime;
import java.util.List;

public class DaoWeaponsFactionsH {


    private final JPAUtil jpaUtil;
    private EntityManager em;

    @Inject
    public DaoWeaponsFactionsH(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }

    public Either<ErrorObject, List<WeaponsFactionsEntity>> getAllByFactionName(String name){
        Either<ErrorObject, List<WeaponsFactionsEntity>> res;
        em = jpaUtil.getEntityManager();
        List<WeaponsFactionsEntity> weaponsFactionsEntityList;
        try {
            weaponsFactionsEntityList = em.createNamedQuery("HQL_GET_ALL_WEAPONSFACTIONS_BY_NAME_FACTION")
                    .setParameter("nameFaction", name)
                    .getResultList();

            res = Either.right(weaponsFactionsEntityList);
        }catch (Exception e){
            res = Either.left(new ErrorObject(e.getMessage(), 0, LocalDateTime.now()));
        }
        return res;
    }

}
