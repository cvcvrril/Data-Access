package data.hibernate.gen;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "faction", schema = "practicaExamenMongoHibernate")
public class FactionEntity {
    @Id
    @Column(name = "fname")
    private String fname;
    @Basic
    @Column(name = "contact")
    private String contact;
    @Basic
    @Column(name = "planet")
    private String planet;
    @Basic
    @Column(name = "number_controlled_systems")
    private int numberControlledSystems;
    @Basic
    @Column(name = "date_last_purchase")
    private Date dateLastPurchase;

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPlanet() {
        return planet;
    }

    public void setPlanet(String planet) {
        this.planet = planet;
    }

    public int getNumberControlledSystems() {
        return numberControlledSystems;
    }

    public void setNumberControlledSystems(int numberControlledSystems) {
        this.numberControlledSystems = numberControlledSystems;
    }

    public Date getDateLastPurchase() {
        return dateLastPurchase;
    }

    public void setDateLastPurchase(Date dateLastPurchase) {
        this.dateLastPurchase = dateLastPurchase;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FactionEntity that = (FactionEntity) o;
        return numberControlledSystems == that.numberControlledSystems && Objects.equals(fname, that.fname) && Objects.equals(contact, that.contact) && Objects.equals(planet, that.planet) && Objects.equals(dateLastPurchase, that.dateLastPurchase);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fname, contact, planet, numberControlledSystems, dateLastPurchase);
    }
}
