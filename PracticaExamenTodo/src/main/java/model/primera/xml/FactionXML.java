package model.primera.xml;

import dao.primera.common.LocalDateAdapterXML;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


/**
 * En caso de que haya un LocalDate, hay que usar un adapter.
 * **/

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "faction")
@XmlAccessorType(XmlAccessType.FIELD)
public class FactionXML {

    @XmlElement
    private String name;
    @XmlElement
    private String contact;
    @XmlElement
    private String planet;
    @XmlElement
    private int numberCS;
    @XmlElement
    @XmlJavaTypeAdapter(LocalDateAdapterXML.class)
    private LocalDate dateLastPurchase;
    @XmlElement
    private WeaponsXML weapons;
}
