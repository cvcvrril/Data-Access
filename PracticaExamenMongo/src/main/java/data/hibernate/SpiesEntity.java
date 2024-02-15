package data.hibernate;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "spies", schema = "practicaExamenMongoHibernate")
public class SpiesEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "sname")
    private String sname;
    @Basic
    @Column(name = "srace")
    private String srace;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getSrace() {
        return srace;
    }

    public void setSrace(String srace) {
        this.srace = srace;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpiesEntity that = (SpiesEntity) o;
        return id == that.id && Objects.equals(sname, that.sname) && Objects.equals(srace, that.srace);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sname, srace);
    }
}
