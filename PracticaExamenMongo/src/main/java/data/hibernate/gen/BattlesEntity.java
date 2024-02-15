package data.hibernate.gen;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "battles", schema = "practicaExamenMongoHibernate")
public class BattlesEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "bname")
    private String bname;
    @Basic
    @Column(name = "faction_one")
    private String factionOne;
    @Basic
    @Column(name = "faction_two")
    private String factionTwo;
    @Basic
    @Column(name = "bplace")
    private String bplace;
    @Basic
    @Column(name = "bdate")
    private Date bdate;
    @Basic
    @Column(name = "id_spy")
    private int idSpy;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public String getFactionOne() {
        return factionOne;
    }

    public void setFactionOne(String factionOne) {
        this.factionOne = factionOne;
    }

    public String getFactionTwo() {
        return factionTwo;
    }

    public void setFactionTwo(String factionTwo) {
        this.factionTwo = factionTwo;
    }

    public String getBplace() {
        return bplace;
    }

    public void setBplace(String bplace) {
        this.bplace = bplace;
    }

    public Date getBdate() {
        return bdate;
    }

    public void setBdate(Date bdate) {
        this.bdate = bdate;
    }

    public int getIdSpy() {
        return idSpy;
    }

    public void setIdSpy(int idSpy) {
        this.idSpy = idSpy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BattlesEntity that = (BattlesEntity) o;
        return id == that.id && idSpy == that.idSpy && Objects.equals(bname, that.bname) && Objects.equals(factionOne, that.factionOne) && Objects.equals(factionTwo, that.factionTwo) && Objects.equals(bplace, that.bplace) && Objects.equals(bdate, that.bdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bname, factionOne, factionTwo, bplace, bdate, idSpy);
    }
}
