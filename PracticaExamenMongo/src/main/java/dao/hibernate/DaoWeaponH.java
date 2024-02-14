package dao.hibernate;

import config.connection.JPAUtil;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

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
}
