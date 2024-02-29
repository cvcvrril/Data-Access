package dao.primera.spring;

import dao.primera.common.DBConnectionPool;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.error.ExamError;
import model.primera.jdbc.Faction;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class DaoFactionS {

    /**
     * Importamos el DB Connection, muy importante hacer el inject
     **/

    /**
     * Para que Spring no toque los cojones, al crear los objetos poner los nombres como vienen en las tablas.
     * Así se ahorra tiempo y no hará falta hacer Mappers de todos los objetos.
     * Se podrá usar el BeanPropertyRowMapper y tirando.
     * **/

    private final DBConnectionPool db;

    @Inject
    public DaoFactionS(DBConnectionPool db) {
        this.db = db;
    }

    public Either<ExamError, Faction> getByName(String fname) {
        Either<ExamError, Faction> either;
        JdbcTemplate jdbcTemplate = new JdbcTemplate(db.getDataSource());
        List<Faction> faction = jdbcTemplate.query("select * from faction where fname = ?", BeanPropertyRowMapper.newInstance(Faction.class), fname);
        either = faction.isEmpty() ? Either.left(new ExamError(0, "There's no faction with that name")) : Either.right(faction.get(0));
        return either;
    }

    /**
     * Para el update, meter el atributo de referencia de la query al final
     * Ejemplo:
     *
     * ----------------------------------------------
     *
     * "update faction set contact = ?, planet = ?, number_controlled_systems = ?, date_last_purchase = ? where fname = ?"
     *
     * Aquí el atributo de referencia que se usa es el nombre de la facción
     * Entonces hay que meterlo lo último, sino no lo pilla el programa
     *
     * ----------------------------------------------
     *
     *
     * **/


    public Either<ExamError, Integer> update(Faction faction) {
        Either<ExamError, Integer> either;
        JdbcTemplate jdbcTemplate = new JdbcTemplate(db.getDataSource());

        int result = jdbcTemplate.update("update faction set contact = ?, planet = ?, number_controlled_systems = ?, date_last_purchase = ? where fname = ?",
                faction.getContact(),
                faction.getPlanet(),
                faction.getNumber_controlled_systems(),
                faction.getDate_last_purchase(),
                faction.getFname());
        either = result > 0 ? Either.right(result) : Either.left(new ExamError(result, "There was an error with the update of the faction"));
        return either;
    }

}
