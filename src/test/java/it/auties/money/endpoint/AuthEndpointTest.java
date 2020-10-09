package it.auties.money.endpoint;

import io.quarkus.test.junit.NativeImageTest;
import io.quarkus.test.junit.QuarkusTest;
import it.auties.money.form.RegistrationUser;
import it.auties.money.util.Gender;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static it.auties.money.endpoint.RandomString.randomString;

@QuarkusTest
@NativeImageTest
public class AuthEndpointTest {
    @Test
    public void testRegisterEndpoint() {
        final var form = new RegistrationUser(
                randomString(8),
                randomString(10),
                "alautiero12@gmail.com",
                "Alessandro",
                "Autiero",
                Gender.MALE
        );

        given()
                .when()
                .formParams(form.toJsonMap())
                .post("/auth/register")
                .then()
                .statusCode(201);
    }
}