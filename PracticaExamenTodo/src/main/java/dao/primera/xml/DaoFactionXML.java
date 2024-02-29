package dao.primera.xml;


import common.Configuration;
import io.vavr.control.Either;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import lombok.extern.log4j.Log4j2;
import model.error.ExamError;
import model.primera.xml.FactionXML;
import model.primera.xml.FactionsXML;
import model.primera.xml.WeaponXML;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Log4j2
public class DaoFactionXML {

    public Either<ExamError, Object> read() {
        Either<ExamError, Object> either;
        try {
            JAXBContext context = JAXBContext.newInstance(FactionsXML.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            Path xmlFile = Paths.get(Configuration.getInstance().getPropertyXML("xmlPath"));

            FactionsXML listXml = (FactionsXML) unmarshaller.unmarshal(Files.newInputStream(xmlFile));

            either = Either.right(listXml);

        } catch (JAXBException | IOException e) {
            either = Either.left(new ExamError(0, "Empty"));
            log.error(e.getMessage());
        }
        return either;
    }

}
