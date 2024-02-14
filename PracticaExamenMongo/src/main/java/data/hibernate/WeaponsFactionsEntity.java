package data.hibernate;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "weapons_factions", schema = "practicaExamenMongoHibernate", catalog = "")
public class WeaponsFactionsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "name_faction")
    private String nameFaction;
    @Basic
    @Column(name = "id_weapon")
    private int idWeapon;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameFaction() {
        return nameFaction;
    }

    public void setNameFaction(String nameFaction) {
        this.nameFaction = nameFaction;
    }

    public int getIdWeapon() {
        return idWeapon;
    }

    public void setIdWeapon(int idWeapon) {
        this.idWeapon = idWeapon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeaponsFactionsEntity that = (WeaponsFactionsEntity) o;
        return id == that.id && idWeapon == that.idWeapon && Objects.equals(nameFaction, that.nameFaction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nameFaction, idWeapon);
    }
}
