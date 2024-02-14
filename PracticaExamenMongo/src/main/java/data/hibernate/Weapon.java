package data.hibernate;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "weapons")
@NamedQueries({
        @NamedQuery(name = "HQL_GET_ALL_WEAPONS", query = "from Weapon"),
        @NamedQuery(name = "HQL_GET_ALL_WEAPONS_BY_NAME" , query = "from Weapon w where w.id in (select id from WeaponsFactionsEntity wf where wf.nameFaction = : nameFaction)")
})
public class Weapon {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "wname", nullable = false)
    private String name;
    @Column(name = "wprice", nullable = false)
    private double price;

}
