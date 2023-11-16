package model;

import lombok.Data;

@Data
public class Credentials {
    private int id;
    private String username;
    private String password;

    public Credentials(String username, String password, int id) {
        this.id=id;
        this.username = username;
        this.password = password;
    }
}
