package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    private int idC;
    private String firstName;
    private String secondName;
    private String emailCus;
    private int phoneNumber;
    private LocalDate dateBirth;
    private Credential credential;

}
