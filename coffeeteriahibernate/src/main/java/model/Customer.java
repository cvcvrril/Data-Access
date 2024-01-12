package model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customers")

@NamedQueries({ @NamedQuery(name = "HQL_GET_ALL_CUSTOMERS",
        query = "from Customer ") })
public class Customer {

    @Id
    @Column(name = "id")
    private int idC;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String secondName;
    @Column(name = "email")
    private String emailCus;
    @Column(name = "phone")
    private int phoneNumber;
    @Column(name = "date_of_birth")
    private LocalDate dateBirth;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "id")
    private Credential credential;
}
