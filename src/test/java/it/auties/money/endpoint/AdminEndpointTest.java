package it.auties.money.endpoint;

import io.quarkus.test.junit.NativeImageTest;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import it.auties.money.form.JwtAuthenticationRequest;
import it.auties.money.form.UpdateUser;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
@NativeImageTest
public class AdminEndpointTest {
    @Test
    public void testChangeRoleEndpoint() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        final var form = new JwtAuthenticationRequest(
                "admin",
                "adminpass"
        );

        var token = given()
                .when()
                .formParams(form.toJsonMap())
                .post("/auth/login")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        final var update = new UpdateUser("admin");
        given()
                .when()
                .header("Authorization", "Bearer " + token)
                .formParams(update.toJsonMap())
                .put("/admin/roles/sample")
                .then()
                .statusCode(201);
    }
}