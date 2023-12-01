package domain.imp;

import common.Configuration;
import common.DBConnection;
import domain.DaoBattle;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Battle;
import model.Sale;
import model.error.ErrorDb;

import java.util.List;

public class DaoBattleImp implements DaoBattle {

    private final Configuration config;
    private final DBConnection db;

    @Inject
    public DaoBattleImp(Configuration config, DBConnection db) {
        this.config = config;
        this.db = db;
    }

    @Override
    public Either<ErrorDb, List<Battle>> getAll() {
        return null;
    }

    @Override
    public Either<ErrorDb, Sale> get(int id) {
        return null;
    }

    @Override
    public Either<ErrorDb, Integer> add(Sale sale) {
        return null;
    }

    @Override
    public Either<ErrorDb, Integer> update(Sale sale) {
        return null;
    }

    @Override
    public Either<ErrorDb, Integer> delete(int id) {
        return null;
    }
}
