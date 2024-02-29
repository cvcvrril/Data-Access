package dao.primera.spring;

import dao.primera.common.DBConnectionPool;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.error.ExamError;
import model.primera.jdbc.Weapon;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.util.List;

public class DaoWeaponS {

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
    public DaoWeaponS(DBConnectionPool db) {
        this.db = db;
    }

    public Either<ExamError, List<Weapon>> getAll(LocalDate lastPurchase){
        Either<ExamError,List<Weapon>> either;
        JdbcTemplate jdbcTemplate = new JdbcTemplate(db.getDataSource());
        List<Weapon> weapons = jdbcTemplate.query("select * from weapons w inner join weapons_factions wf on w.id = wf.id_weapon inner join faction f on wf.name_faction = f.fname where date_last_purchase = ?", BeanPropertyRowMapper.newInstance(Weapon.class), lastPurchase);
        either = weapons.isEmpty() ? Either.left(new ExamError(0, "The list with this date is empty")) : Either.right(weapons);
        return either;
    }

}
