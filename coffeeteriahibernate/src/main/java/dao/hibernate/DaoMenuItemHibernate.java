package dao.hibernate;

import dao.connection.JPAUtil;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import lombok.extern.log4j.Log4j2;
import model.MenuItem;
import model.errors.ErrorCMenuItem;

import java.util.List;

@Log4j2
public class DaoMenuItemHibernate {

    private final JPAUtil jpaUtil;
    private EntityManager em;

    @Inject
    public DaoMenuItemHibernate(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }

    public Either<ErrorCMenuItem, List<MenuItem>> getAll(){
        Either<ErrorCMenuItem, List<MenuItem>> res;
        List<MenuItem> menuItemList;
        em = jpaUtil.getEntityManager();
        try {
            menuItemList = em
                    .createNamedQuery("HQL_GET_ALL_MENU_ITEMS", MenuItem.class)
                    .getResultList();
            res = Either.right(menuItemList);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            res = Either.left(new ErrorCMenuItem(e.getMessage(), 0));
        }finally {
            if (em != null) em.close();
        }
        return res;
    }

    public Either<ErrorCMenuItem, MenuItem> get(int id){
        Either<ErrorCMenuItem, MenuItem> res;
        MenuItem menuItem;
        em = jpaUtil.getEntityManager();
        try {
            menuItem = em.find(MenuItem.class, id);
            res = Either.right(menuItem);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            res = Either.left(new ErrorCMenuItem(e.getMessage(), 0));
        }finally {
            if (em != null) em.close();
        }
        return res;
    }

}
