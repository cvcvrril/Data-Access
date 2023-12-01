package services;

import domain.DaoFaction;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Faction;
import model.Spy;
import model.error.ErrorDb;

import java.util.List;

public class ServFaction {
    private final DaoFaction daoFaction;

    @Inject
    public ServFaction(DaoFaction daoFaction) {
        this.daoFaction = daoFaction;
    }

    public Either<ErrorDb, List<Faction>> getAll() {
        return daoFaction.getAll();
    }

}
