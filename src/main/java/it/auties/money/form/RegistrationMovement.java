package it.auties.money.form;

import it.auties.money.util.JsonSerializable;
import it.auties.money.util.MovementType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.ws.rs.FormParam;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegistrationMovement implements JsonSerializable {
    @FormParam("type")
    private MovementType type;
    @FormParam("amount")
    private long amount;
    @FormParam("executeAt")
    private long executedAt;
    @FormParam("note")
    private String note;
    @FormParam("category")
    private String category;
}
