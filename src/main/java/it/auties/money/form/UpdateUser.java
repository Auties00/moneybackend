package it.auties.money.form;

import it.auties.money.util.JsonSerializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.ws.rs.FormParam;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateUser implements JsonSerializable {
    @FormParam("role")
    private String role;
}
