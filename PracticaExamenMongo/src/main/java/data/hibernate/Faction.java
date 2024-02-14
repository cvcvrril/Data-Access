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
public class Faction {
    @Id
    @Column(name = "fname", nullable = false)
    private String name;
    @Column(name = "contact")
    private String contact;
    @Column(name = "planet")
    private String planet;
    @Column(name = "number_controlled_systems")
    private int systems;
    @Column(name = "date_last_purchase")
    private LocalDate dateLastPurchase;
}
