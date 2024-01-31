package model.mongo;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CredentialMongo {
    private int _id;
    private String username;
    private String password;

}
