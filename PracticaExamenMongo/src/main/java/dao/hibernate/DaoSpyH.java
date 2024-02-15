package dao.hibernate;

import config.connection.JPAUtil;
import data.error.ErrorObject;
import data.hibernate.SpiesEntity;
import data.hibernate.Spy;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.time.LocalDateTime;

public class DaoSpyH {

    private final JPAUtil jpaUtil;
    private EntityManager em;

    @Inject
    public DaoSpyH(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }

    public Either<ErrorObject, Spy> get(int id){
        Either<ErrorObject, Spy> res;
        em = jpaUtil.getEntityManager();
        Spy spy;
        try {
            spy = em.find(Spy.class, id);
            res = Either.right(spy);
        }catch (Exception e){
            res = Either.left(new ErrorObject("Ha habido un error", 0, LocalDateTime.now()));
        }
        return res;
    }

    public Either<ErrorObject, Integer> delete(Spy spyDelete){
        Either<ErrorObject, Integer> res;
        em = jpaUtil.getEntityManager();
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();

            /**
             * En este caso hay que eliminar las batallas antes de eliminar al espía.
             * En caso de que haya algún error, el programa debe de hacer un rollback.
             * **/

            em.createNamedQuery("HQL_DELETE_ALL_BATTLES_BY_IDSPY")
                    .setParameter("id", spyDelete.getId())
                    .executeUpdate();

            /**
             * Ahora toca eliminar al espía.
             * Es MUY IMPORTANTE usar de la siguiente forma el remove.
             * Sino, va a saltar la siguiente excepción:
             *
             * Removing a detached instance data.hibernate.Spy#1
             *
             * **/
            em.remove(em.merge(spyDelete));
            et.commit();
            res = Either.right(1);
        }catch (Exception e){
            res = Either.left(new ErrorObject("Ha habido un error", 0, LocalDateTime.now()));
        }finally {
            em.close();
        }
        return res;
    }

}
