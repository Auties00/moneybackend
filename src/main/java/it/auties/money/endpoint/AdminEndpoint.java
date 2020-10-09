package it.auties.money.endpoint;

import it.auties.money.form.UpdateUser;
import it.auties.money.repository.UserRepository;
import it.auties.money.util.ErrorDetail;
import lombok.NonNull;
import org.jboss.resteasy.annotations.Form;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.RolesAllowed;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Path("/admin")
@RolesAllowed("admin")
public class AdminEndpoint {
    @Autowired
    UserRepository userRepository;

    @Path("/roles/{username}")
    @PUT
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response changeUserPermission(@PathParam String username, @Form UpdateUser updateUser, @Context @NonNull SecurityContext securityContext){
        var user = userRepository.findUserByUsername(username);
        if (user == null) {
            return Response
                    .status(400)
                    .entity(ErrorDetail
                            .builder()
                            .title("User not found")
                            .detail(username + " cannot be found")
                            .code(400)
                            .timeStamp(System.currentTimeMillis())
                            .build())
                    .build();
        }

        user.setRole(updateUser.getRole());
        userRepository.save(user);
        return Response
                .status(201)
                .entity(user)
                .build();
    }
}
