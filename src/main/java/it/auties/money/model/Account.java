package it.auties.money.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import it.auties.money.form.RegistrationAccount;
import it.auties.money.form.UpdateAccount;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "accounts")
public class Account extends PanacheEntity {
    @NonNull
    @NotEmpty
    private String name;
    @NonNull
    @NotEmpty
    private String description;
    @NonNull
    private int balance;
    @NonNull
    @ManyToMany
    private List<Movement> movements;

    public Account(RegistrationAccount account){
        this(account.getName(), account.getDescription(), account.getInitialBalance(), new ArrayList<>());
    }

    public void update(UpdateAccount account){
        this.name = account.getName();
        this.description = account.getDescription();
    }
}
