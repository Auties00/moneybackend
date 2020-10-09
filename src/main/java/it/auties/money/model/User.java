package it.auties.money.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.security.jpa.Roles;
import it.auties.money.form.RegistrationUser;
import it.auties.money.util.Gender;
import it.auties.money.util.SocialType;
import lombok.*;
import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User extends PanacheEntity {
    @NonNull
    @NotEmpty
    @Size.List ({
            @Size(min=5, message="Invalid username, please provide one with at least 5 characters!"),
            @Size(max=12, message="Invalid username, please provide one with a maximum of 12 characters!")
    })
    private String username;

    @NonNull
    @NotEmpty
    @Size.List ({
            @Size(min=8, message="Invalid password, please provide one with at least 8 characters!"),
    })
    private String password;

    @NonNull
    @NotEmpty
    @Email
    private String email;

    @Roles
    @NotNull
    @NotEmpty
    private String role;

    @NonNull
    @NotEmpty
    private String firstName;

    @NonNull
    @NotEmpty
    private String lastName;

    @NonNull
    private Gender gender;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Account> accounts;

    private String profileImageUrl;
    private SocialType socialType;

    public User(RegistrationUser user){
        this(user.getUsername(), BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(15)), user.getEmail(), "user", user.getFirstName(), user.getLastName(), user.getGender(), new ArrayList<>(),null, null);
    }
}
