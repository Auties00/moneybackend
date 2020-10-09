package it.auties.money.main;

import io.quarkus.runtime.StartupEvent;
import it.auties.money.model.User;
import it.auties.money.repository.UserRepository;
import it.auties.money.util.Gender;
import it.auties.money.util.SocialType;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import java.util.ArrayList;

@ApplicationScoped
public class MoneyApplication {
    @Autowired
    UserRepository repository;

    void onStart(@Observes StartupEvent event) {
        if(repository.findUserByUsername("admin") == null) {
            var admin = new User("admin", BCrypt.hashpw("adminpass", BCrypt.gensalt(15)), "admin@admin.it", "admin", "admin", "admin", Gender.MALE, new ArrayList<>(), "", SocialType.NONE);
            repository.save(admin);
        }

        if(repository.findUserByUsername("sample") == null){
            var admin = new User("sample", BCrypt.hashpw("samplepass", BCrypt.gensalt(15)), "sample@sample.it", "user", "sample", "sample", Gender.MALE, new ArrayList<>(), "", SocialType.NONE);
            repository.save(admin);
        }
    }
}