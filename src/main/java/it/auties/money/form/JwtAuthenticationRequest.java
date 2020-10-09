package it.auties.money.form;

import it.auties.money.util.JsonSerializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.ws.rs.FormParam;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class JwtAuthenticationRequest implements JsonSerializable {
    @FormParam("username")
    private String username;
    @FormParam("password")
    private String password;
}
