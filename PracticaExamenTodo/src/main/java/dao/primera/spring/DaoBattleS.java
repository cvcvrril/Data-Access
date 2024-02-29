package dao.primera.spring;

import dao.primera.common.DBConnectionPool;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.error.ExamError;
import model.primera.jdbc.Battle;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.util.List;

public class DaoBattleS {

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
    public DaoBattleS(DBConnectionPool db) {
        this.db = db;
    }

    /**
     * Query de SQL usada:
     *
     * SELECT sname, bname FROM spies s inner join battles b on s.id = b.id_spy where s.id = 1;
     *
     * **/

    public Either<ExamError, List<Battle>> getAllByIdSpy(int idSpy){
        Either<ExamError,List<Battle>> either;
        JdbcTemplate jdbcTemplate = new JdbcTemplate(db.getDataSource());
        List<Battle> weapons = jdbcTemplate.query("SELECT sname, bname FROM spies s inner join battles b on s.id = b.id_spy where s.id = ?", BeanPropertyRowMapper.newInstance(Battle.class), idSpy);
        either = weapons.isEmpty() ? Either.left(new ExamError(0, "The list with this id is empty")) : Either.right(weapons);
        return either;
    }

}
