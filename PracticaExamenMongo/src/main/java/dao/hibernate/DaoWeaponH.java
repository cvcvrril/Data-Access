package dao.hibernate;

import config.connection.JPAUtil;
import data.error.ErrorObject;
import data.hibernate.Weapon;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.time.LocalDateTime;
import java.util.List;

public class DaoWeaponH {

    /**
     * Si se necesita objetos externos, en los Daos NO se puede llamar a los servicios; se tira del otro Dao
     *
     * **/

    private final JPAUtil jpaUtil;
    private EntityManager em;

    @Inject
    public DaoWeaponH(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }

    public Either<ErrorObject, List<Weapon>> getAll(){
        Either<ErrorObject, List<Weapon>> res;
        List<Weapon> weaponList;
        em = jpaUtil.getEntityManager();
        try {
            weaponList = em
                    .createNamedQuery("HQL_GET_ALL_WEAPONS", Weapon.class)
                    .getResultList();
            res = Either.right(weaponList);
        }catch (Exception e){
            res = Either.left(new ErrorObject("Ha habido un error", 0, LocalDateTime.now()));
        }
        return res;
    }

    public Either<ErrorObject, List<Weapon>> getByName(String name){
        Either<ErrorObject, List<Weapon>> res;
        List<Weapon> weaponList;
        em = jpaUtil.getEntityManager();
        try {
            weaponList = em
                    .createNamedQuery("HQL_GET_ALL_WEAPONS_BY_NAME")
                    .setParameter("nameFaction", name)
                    .getResultList();
            res = Either.right(weaponList);
        }catch (Exception e){
            res = Either.left(new ErrorObject("Ha habido un error", 0, LocalDateTime.now()));
        }
        return res;
    }

    public Either<ErrorObject, Integer> update(Weapon weaponUpdate) {
        Either<ErrorObject, Integer> res;
        em = jpaUtil.getEntityManager();
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();
            em.merge(weaponUpdate);
            et.commit();

            res = Either.right(1);

        }catch (Exception e){
            if (et.isActive()) et.rollback();
            res = Either.left(new ErrorObject("Ha habido un error", 0, LocalDateTime.now()));
        }finally {
            em.close();
        }
        return res;
    }
}
