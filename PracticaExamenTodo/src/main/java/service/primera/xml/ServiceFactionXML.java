package service.primera.xml;

import dao.primera.xml.DaoFactionXML;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.error.ExamError;

public class ServiceFactionXML {

    private final DaoFactionXML daoFactionXML;

    @Inject
    public ServiceFactionXML(DaoFactionXML daoFactionXML) {
        this.daoFactionXML = daoFactionXML;
    }

    public Either<ExamError, Object> read() {
        return daoFactionXML.read();
    }
}
