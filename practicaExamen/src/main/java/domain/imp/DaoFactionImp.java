package domain.imp;

import domain.DaoFaction;
import io.vavr.control.Either;
import model.Faction;
import model.error.ErrorDb;

import java.util.List;

public class DaoFactionImp implements DaoFaction {
    @Override
    public Either<ErrorDb, List<Faction>> getAll() {
        return null;
    }
}
