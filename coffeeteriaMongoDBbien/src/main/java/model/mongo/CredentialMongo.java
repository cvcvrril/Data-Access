package model.mongo;

import lombok.*;
import org.bson.types.ObjectId;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CredentialMongo {
    private ObjectId _id;
    private String username;
    private String password;

}
