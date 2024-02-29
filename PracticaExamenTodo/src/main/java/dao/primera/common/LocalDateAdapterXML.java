package dao.primera.common;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.time.LocalDate;

/**
 * Este adapter se debe de usar en caso de que haya un LocalDate en los atributos de alguno de los objetos XML.
 * **/

public class LocalDateAdapterXML extends XmlAdapter<String, LocalDate> {
    @Override
    public LocalDate unmarshal(String s) throws Exception {
        return LocalDate.parse(s);
    }

    @Override
    public String marshal(LocalDate localDate) throws Exception {
        if ( localDate == null ) return null;
        return localDate.toString();
    }
}
