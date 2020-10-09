package it.auties.money.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import it.auties.money.form.RegistrationMovement;
import it.auties.money.util.MovementType;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "movements")
public class Movement extends PanacheEntity {
    @NonNull
    private MovementType type;
    @NonNull
    private long amount;
    @NonNull
    private long executedAt;
    @NonNull
    private String note;
    @NonNull
    private String category;

    public Movement(RegistrationMovement registrationMovement){
        this(registrationMovement.getType(), registrationMovement.getAmount(), registrationMovement.getExecutedAt(), registrationMovement.getNote(), registrationMovement.getCategory());
    }
}
