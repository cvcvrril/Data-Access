package model.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "faction")
@XmlAccessorType(XmlAccessType.FIELD)
public class FactionXml {
    @XmlElement
    private String name;
    @XmlElement
    private String contact;
    @XmlElement
    private String planet;
    @XmlElement(name = "numberCS")
    private int numberControlledSystems;
    @XmlElement(name = "dateLastPurchase")
    private LocalDate datePurchase;
    @XmlElement(name = "weapons")
    private WeaponsXml weaponsXml;
}
