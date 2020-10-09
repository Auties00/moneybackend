package it.auties.money.form;

import it.auties.money.util.Gender;
import it.auties.money.util.JsonSerializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.ws.rs.FormParam;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegistrationUser implements JsonSerializable {
    @FormParam("username")
    private String username;
    @FormParam("password")
    private String password;
    @FormParam("email")
    private String email;
    @FormParam("firstName")
    private String firstName;
    @FormParam("lastName")
    private String lastName;
    @FormParam("gender")
    private Gender gender;
}
