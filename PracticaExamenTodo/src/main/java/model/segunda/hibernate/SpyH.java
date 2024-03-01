package model.segunda.hibernate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "spies", schema = "practicaExamenMongoHibernate")
@NamedQueries({
        @NamedQuery(name = "HQL_GET_ALL_SPIES", query = "from SpyH "),
})
public class SpyH {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "sname")
    private String sname;
    @Column(name = "srace")
    private String srace;

}
