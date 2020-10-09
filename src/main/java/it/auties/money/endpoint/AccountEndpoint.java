package it.auties.money.endpoint;

import it.auties.money.form.RegistrationAccount;
import it.auties.money.form.RegistrationMovement;
import it.auties.money.form.UpdateAccount;
import it.auties.money.model.Account;
import it.auties.money.model.Movement;
import it.auties.money.repository.AccountRepository;
import it.auties.money.repository.MovementRepository;
import it.auties.money.repository.UserRepository;
import it.auties.money.util.ErrorDetail;
import lombok.NonNull;
import org.jboss.resteasy.annotations.Form;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.RolesAllowed;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Path("/accounts")
@RolesAllowed("user")
public class AccountEndpoint {
    @Autowired
    UserRepository userRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    MovementRepository movementRepository;

    @Path("/")
    @GET
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response createAccount(@Context @NonNull SecurityContext securityContext, @Form RegistrationAccount registrationAccount){
        var user = userRepository.findUserByUsername(securityContext.getUserPrincipal().getName());
        var account = new Account(registrationAccount);
        accountRepository.save(account);
        user.getAccounts().add(account);
        userRepository.save(user);
        return Response
                .status(201)
                .entity(account)
                .build();
    }

    @Path("/")
    @GET
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response getAccounts(@Context @NonNull SecurityContext securityContext){
        return Response
                .status(Response.Status.OK)
                .entity(userRepository.findUserByUsername(securityContext.getUserPrincipal().getName()))
                .build();
    }

    @Path("/{accountId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response getAccount(@PathParam long accountId, @Context @NonNull SecurityContext securityContext){
        var user = userRepository.findUserByUsername(securityContext.getUserPrincipal().getName());
        var account = user.getAccounts().stream().filter(match -> match.id == accountId).findFirst();
        if(account.isEmpty()){
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(ErrorDetail
                            .builder()
                            .title("Account not found")
                            .detail("This account doesn't exist or it doesn't belong to the currently logged user")
                            .code(404)
                            .timeStamp(System.currentTimeMillis())
                            .build())
                    .build();
        }

        return Response
                .status(Response.Status.OK)
                .entity(account.get())
                .build();
    }

    @Path("/{accountId}")
    @PUT
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response updateAccount(@PathParam long accountId, @Context @NonNull SecurityContext securityContext, @Form UpdateAccount updateAccount){
        var user = userRepository.findUserByUsername(securityContext.getUserPrincipal().getName());
        var accountOpt = user.getAccounts().stream().filter(match -> match.id == accountId).findFirst();
        if(accountOpt.isEmpty()){
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(ErrorDetail
                            .builder()
                            .title("Account not found")
                            .detail("This account doesn't exist or it doesn't belong to the currently logged user")
                            .code(404)
                            .timeStamp(System.currentTimeMillis())
                            .build())
                    .build();
        }

        var account = accountOpt.get();
        account.update(updateAccount);
        accountRepository.save(account);

        return Response
                .status(Response.Status.OK)
                .entity(account)
                .build();
    }

    @Path("/{accountId}")
    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    @Transactional
    public Response deleteAccount(@PathParam long accountId, @Context @NonNull SecurityContext securityContext){
        var user = userRepository.findUserByUsername(securityContext.getUserPrincipal().getName());
        var accountOpt = user.getAccounts().stream().filter(match -> match.id == accountId).findFirst();
        if(accountOpt.isEmpty()){
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(ErrorDetail
                            .builder()
                            .title("Account not found")
                            .detail("This account doesn't exist or it doesn't belong to the currently logged user")
                            .code(404)
                            .timeStamp(System.currentTimeMillis())
                            .build())
                    .build();
        }

        var account = accountOpt.get();
        user.getAccounts().remove(account);
        accountRepository.delete(account);
        userRepository.save(user);


        return Response
                .status(Response.Status.OK)
                .entity("Account deleted correctly")
                .build();
    }

    @Path("/{accountId}/movements")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response addMovement(@PathParam long accountId, @Context @NonNull SecurityContext securityContext, @Form RegistrationMovement registrationMovement){
        var user = userRepository.findUserByUsername(securityContext.getUserPrincipal().getName());
        var accountOpt = user.getAccounts().stream().filter(match -> match.id == accountId).findFirst();
        if(accountOpt.isEmpty()){
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(ErrorDetail
                            .builder()
                            .title("Account not found")
                            .detail("This account doesn't exist or it doesn't belong to the currently logged user")
                            .code(404)
                            .timeStamp(System.currentTimeMillis())
                            .build())
                    .build();
        }

        var account = accountOpt.get();
        var movement = new Movement(registrationMovement);
        movementRepository.save(movement);
        account.getMovements().add(movement);
        accountRepository.save(account);

        return Response
                .status(Response.Status.OK)
                .entity(movement)
                .build();
    }
}
