package model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "credential")

@NamedQueries({ @NamedQuery(name = "HQL_GET_ALL_CREDENTIAL",
        query = "from Credential ") })
public class Credential {


    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "username")
    private String userName;
    @Column
    private String password;
}
