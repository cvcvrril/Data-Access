package data.hibernate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "faction")
@NamedQueries({
        @NamedQuery(name = "HQL_GET_ALL_FACTIONS", query = "from Faction")
})
public class Faction {
    @Id
    @Column(name = "fname", nullable = false)
    private String name;
    @Column(name = "contact", nullable = false)
    private String contact;
    @Column(name = "planet", nullable = false)
    private String planet;
    @Column(name = "number_controlled_systems", nullable = false)
    private int systems;
    @Column(name = "date_last_purchase", nullable = false)
    private LocalDate dateLastPurchase;
}
