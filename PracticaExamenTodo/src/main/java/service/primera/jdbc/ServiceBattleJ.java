package service.primera.jdbc;

import dao.primera.jdbc.DaoBattleJ;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.error.ExamError;
import model.primera.jdbc.Battle;

public class ServiceBattleJ {

    private final DaoBattleJ daoBattleJ;

    @Inject
    public ServiceBattleJ(DaoBattleJ daoBattleJ) {
        this.daoBattleJ = daoBattleJ;
    }

    public Either<ExamError, Integer> add(Battle newBatte){
        return daoBattleJ.add(newBatte);
    }

}
