package config.connection;

import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;


/**
 *
 * Para Hibernate no se necesita la clase Config ni un fichero xml/txt de donde sacar la contraseña y demás.
 * Se saca del Persistance. El Persistance se copia y se pega tal cual está puesto aquí.
 *
 * -----------------------------------------------------------
 *
 * Usuario base de datos SQL : root
 * Contraseña base de datos SQL : alumno
 * Usuario base de datos Mongo : root
 * Contraseña base de datos Mongo : root
 *
 */

@Singleton
public class JPAUtil {

    private EntityManagerFactory emf;
    public JPAUtil() {
       emf=getEmf();
    }

    private EntityManagerFactory getEmf() {
        return Persistence.createEntityManagerFactory("unit3.hibernate");
    }
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
