package it.auties.money.endpoint;

import it.auties.money.form.JwtAuthenticationRequest;
import it.auties.money.form.RegistrationUser;
import it.auties.money.model.User;
import it.auties.money.repository.UserRepository;
import it.auties.money.util.ErrorDetail;
import it.auties.money.util.TokenUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.resteasy.annotations.Form;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.PermitAll;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Set;

@Path("/auth")
public class AuthEndpoint {
    @Autowired
    UserRepository userRepository;

    @ConfigProperty(name = "mp.jwt.verify.issuer")
    String issuer;

    @Path("/register")
    @PermitAll
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Transactional
    public Response register(@Form RegistrationUser registrationUser){
        if(userRepository.findUserByUsername(registrationUser.getUsername()) != null){
            return Response
                    .status(400)
                    .entity(ErrorDetail
                            .builder()
                            .title("Invalid data to create user")
                            .detail("Username already exists")
                            .code(400)
                            .timeStamp(System.currentTimeMillis())
                            .build())
                    .build();
        }


        var user = new User(registrationUser);
        userRepository.save(user);
        return Response
                .status(Response.Status.CREATED)
                .entity(user)
                .build();
    }

    @POST
    @PermitAll
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Transactional
    public Response login(@Form JwtAuthenticationRequest request) throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
        var user = userRepository.findUserByUsername(request.getUsername());
        if(user == null){
            return Response
                    .status(401)
                    .entity(ErrorDetail
                            .builder()
                            .title("Login failed")
                            .detail("This username doesn't exist")
                            .code(401)
                            .timeStamp(System.currentTimeMillis())
                            .build())
                    .build();
        }

        return BCrypt.checkpw(request.getPassword(), user.getPassword()) ?
                Response
                        .status(Response.Status.OK)
                        .entity(TokenUtils.generateToken(user.getUsername(), Set.of(user.getRole()), 3600L, issuer))
                        .build() :
                Response
                        .status(401)
                        .entity(ErrorDetail
                                .builder()
                                .title("Login failed")
                                .detail("Wrong password")
                                .code(401)
                                .timeStamp(System.currentTimeMillis())
                                .build())
                        .build();
    }
}