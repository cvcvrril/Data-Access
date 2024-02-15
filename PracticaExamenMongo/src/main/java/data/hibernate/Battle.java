package data.hibernate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;


/**
 * Las queries de DELETE se tienen que hacer en el objeto que se tiene que eliminar (sino, no funciona).
 * **/

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "battles", schema = "practicaExamenMongoHibernate")
@NamedQueries({
        @NamedQuery(name = "HQL_GET_ALL_BATTLES", query = "from Battle "),
        @NamedQuery(name = "HQL_DELETE_ALL_BATTLES_BY_IDSPY", query = "delete from Battle b where b.idSpy in (select id from Spy s where s.id = : id)")
})
public class Battle {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "bname")
    private String bname;
    @Column(name = "faction_one")
    private String factionOne;
    @Column(name = "faction_two")
    private String factionTwo;
    @Column(name = "bplace")
    private String bplace;
    @Column(name = "bdate")
    private Date bdate;
    @Column(name = "id_spy")
    private int idSpy;


}
