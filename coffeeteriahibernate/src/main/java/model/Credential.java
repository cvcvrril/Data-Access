package model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

@Table(name = "credential")
public class Credential {

    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "username")
    private String userName;
    @Column
    private String password;
}
