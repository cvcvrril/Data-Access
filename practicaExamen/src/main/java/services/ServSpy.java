package services;

import domain.DaoSpy;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Spy;
import model.error.ErrorDb;
import java.util.List;
public class ServSpy {
    private final DaoSpy daoSpy;
    @Inject
    public ServSpy(DaoSpy daoSpy) {
        this.daoSpy = daoSpy;
    }
    public Either<ErrorDb, List<Spy>> getAll() {
        return daoSpy.getAll();
    }

    public Either<ErrorDb, Spy> get(int id){
        return daoSpy.get(id);
    }

    public Either<ErrorDb, Integer>add(Spy spy){
        return daoSpy.add(spy);
    }

    public Either<ErrorDb, Integer>update(Spy spy){
        return daoSpy.update(spy);
    }

    public Either<ErrorDb, Integer>delete(int id){
        return daoSpy.delete(id);
    }

}
