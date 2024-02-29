package service.primera.sping;

import dao.primera.spring.DaoBattleS;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.error.ExamError;
import model.primera.jdbc.Battle;

import java.util.List;

public class ServiceBattleS {

    private final DaoBattleS daoBattleS;

    @Inject
    public ServiceBattleS(DaoBattleS daoBattleS) {
        this.daoBattleS = daoBattleS;
    }

    public Either<ExamError, List<Battle>> getAllByIdSpy(int idSpy){
        return daoBattleS.getAllByIdSpy(idSpy);
    }

}
