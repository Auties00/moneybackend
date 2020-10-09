package it.auties.money.form;

import it.auties.money.util.JsonSerializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.ws.rs.FormParam;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegistrationAccount implements JsonSerializable {
    @FormParam("name")
    private String name;
    @FormParam("description")
    private String description;
    @FormParam("numberOfUsers")
    private int numberOfUsers;
    @FormParam("initialBalance")
    private int initialBalance;
}
