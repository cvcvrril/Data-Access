package model;

import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
public class Customer {

    private int id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String email;
    private int phoneNumber;

    public Customer(int id, String firstName, String lastName, LocalDate dateOfBirth, String email, int phoneNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth=dateOfBirth;
        this.email = email;
        this.phoneNumber = phoneNumber;

    }

    public String toStringFicheros() {
        return  id + ";" + firstName + ";" + lastName + ";"  +
                dateOfBirth.toString().replace("-","/")
                + ";" + email +";"+ phoneNumber + ";";
    }
}
