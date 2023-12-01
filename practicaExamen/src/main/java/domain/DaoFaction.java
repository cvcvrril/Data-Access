package domain;

import io.vavr.control.Either;
import model.Faction;
import model.error.ErrorDb;

import java.util.List;

public interface DaoFaction {

    Either<ErrorDb, List<Faction>> getAll();
    Either<ErrorDb, Integer>readXML();

}
