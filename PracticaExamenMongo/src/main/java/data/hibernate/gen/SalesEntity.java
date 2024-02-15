package data.hibernate.gen;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "sales", schema = "practicaExamenMongoHibernate", catalog = "")
public class SalesEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "id_weapons_faction")
    private int idWeaponsFaction;
    @Basic
    @Column(name = "units")
    private int units;
    @Basic
    @Column(name = "sldate")
    private Date sldate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdWeaponsFaction() {
        return idWeaponsFaction;
    }

    public void setIdWeaponsFaction(int idWeaponsFaction) {
        this.idWeaponsFaction = idWeaponsFaction;
    }

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }

    public Date getSldate() {
        return sldate;
    }

    public void setSldate(Date sldate) {
        this.sldate = sldate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SalesEntity that = (SalesEntity) o;
        return id == that.id && idWeaponsFaction == that.idWeaponsFaction && units == that.units && Objects.equals(sldate, that.sldate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idWeaponsFaction, units, sldate);
    }
}
