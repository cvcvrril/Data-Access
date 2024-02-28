package dao.primera.jdbc;

import dao.primera.common.DBConnectionPool;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import model.error.ExamError;
import model.primera.jdbc.Faction;
import model.primera.jdbc.Weapon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class DaoFactionJ {

    /**
     * Importamos el DB Connection, muy importante hacer el inject
     **/

    private final DBConnectionPool db;
    private final DaoWeaponJ daoWeaponJ;


    @Inject
    public DaoFactionJ(DBConnectionPool db, DaoWeaponJ daoWeaponJ) {
        this.db = db;
        this.daoWeaponJ = daoWeaponJ;
    }

    public Either<ExamError, List<Faction>> getAll(){
        Either<ExamError, List<Faction>> res;
        List<Faction> factions;
        try(Connection connection = db.getConnection()) {
            PreparedStatement pstmt = connection.prepareStatement("select * from faction");
            ResultSet rs = pstmt.executeQuery();
            factions = readRS(rs);
            if (factions.isEmpty()) {
                res = Either.left(new ExamError(0, "The list of factions is empty"));
            } else {
                res = Either.right(factions);
            }
        }catch (SQLException e){
            log.error(e.getMessage(), e);
            res = Either.left(new ExamError(0, "There was an error"));
        }
        return res;
    }


    /**
     * !!!! IMPORTANTE !!!!
     * Si el Dao necesita tomar un objeto/lista de objetos, recurrir al dao de ese objeto.
     * No tirar del servicio; a Lucía no le gusta eso.
     *
     * Ejemplo:
     *
     * ------------------------------------------------
     *
     *   List<Weapon> weaponList = daoWeaponJ.getAllByNameFaction(name).getOrElse(new ArrayList<>());
     *
     * ------------------------------------------------
     *
     * Explicación:
     *
     * La lista de Weapon se obtiene a partir de un método (usado en el ejercicio 5) que toma los objetos a partir
     * del nombre de la facción.
     * Sí, hay que montar métodos adicionales en casos así.
     *
     * **/

    /**
     * También importante.
     * Si la lista está vacía, crear un nuevo ArrayList<> (porque sino pega error).
     * **/

    private List<Faction> readRS(ResultSet rs) throws SQLException {
        List<Faction> factionList = new ArrayList<>();
        while (rs.next()) {
            String name = rs.getString("fname");
            String contact = rs.getString("contact");
            String planet = rs.getString("planet");
            int numberCS = rs.getInt("number_controlled_systems");
            LocalDate datePurchase = rs.getDate("date_last_purchase").toLocalDate();
            List<Weapon> weaponList = daoWeaponJ.getAllByNameFaction(name).getOrElse(new ArrayList<>());
            if (weaponList.isEmpty()) {
                factionList.add(new Faction(name, contact, planet, numberCS, datePurchase, new ArrayList<>()));
            } else {
                factionList.add(new Faction(name, contact, planet, numberCS, datePurchase, weaponList));
            }
        }
        return factionList;
    }

}
