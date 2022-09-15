import io.restassured.http.ContentType;
import io.restassured.response.Response;
import objects.Player;
import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SlotegratorAPITests {
    private static String accessToken;
    private static String registratedUserAccessToken;
    private static String registratedUserRefreshToken;
    private static Player newPlayer = new Player();

    @Order(1)
    @Test
    @DisplayName("get guest Access Token")
    public void postRequestToken_expect200andAccessToken() {
        System.out.println("STEP 1: Get Unique Token");
        Map<String, String> params = new HashMap<>();
        params.put("scope", "guest:default");

        Response response = Requests.auth("client_credentials", params);

        response
                .then().log().body();
        accessToken = response.path("access_token");
        System.out.println("Token is: " + accessToken);
        int statusCode = response.getStatusCode();

        assertThat(accessToken, is(notNullValue()));
        assertThat(accessToken, is(not(equalTo(""))));
        assertEquals(200, statusCode, "Incorrect status code returned! Should be 200!");
    }

    @Order(2)
    @Test
    @DisplayName("register a new objects.Player")
    public void registerANewPlayer() {
        System.out.println("STEP 2: Register new objects.Player");

        Response registration = given().urlEncodingEnabled(true)
                .spec(Requests.getRequestSpecificationBuilder().build())
                .header("Accept", ContentType.JSON.getAcceptHeader())
                .header("Authorization", "Bearer " + accessToken)
                .param("username", newPlayer.getUsername())
                .param("name", newPlayer.getName())
                .param("surname", newPlayer.getSurname())
                .param("password_change", newPlayer.getEncodedPasswordString())
                .param("password_repeat", newPlayer.getEncodedPasswordString())
                .param("email", newPlayer.getEmail())
                .log().params()
                .when()
                .post(Requests.PLAYERS);

        registration
                .then()
                .log().body()
                .assertThat()
                .statusCode(201)
                .body("username", equalTo(newPlayer.getUsername()))
                .body("email", equalTo(newPlayer.getEmail()))
                .body("name", equalTo(newPlayer.getName()))
                .body("surname", equalTo(newPlayer.getSurname()))
                .body(matchesJsonSchemaInClasspath("createNewUserResponseJSONSchema.json"));

        newPlayer.setId(registration.path("id"));
        newPlayer.setCountry_id(registration.path("country_id"));
        newPlayer.setTimezone_id(registration.path("timezone_id"));
        newPlayer.setGender(registration.path("gender"));
        newPlayer.setPhone_number(registration.path("phone_number"));
        newPlayer.setBirthdate(registration.path("birthdate"));
        newPlayer.setBonuses_allowed(registration.path("bonuses_allowed"));
        newPlayer.setIs_verified(registration.path("is_verified"));
    }

    @Order(3)
    @Test
    @DisplayName("authenticate as recently created objects.Player")
    public void authAsCreatedPlayer() {
        System.out.println("STEP 3: Authenticate new objects.Player");
        Map<String, String> params = new HashMap<>();
        params.put("username", newPlayer.getUsername());
        params.put("password", newPlayer.getEncodedPasswordString());

        Response response = Requests.auth("password", params);

        response
                .then().log().body();

        int statusCode = response.getStatusCode();
        registratedUserAccessToken = response.path("access_token");
        registratedUserRefreshToken = response.path("refresh_token");

        assertEquals(200, statusCode, "Wrong status code returned! Should be 200!");
        assertThat(registratedUserAccessToken, is(notNullValue()));
        assertThat(registratedUserAccessToken, is(not(equalTo(""))));
    }

    @Order(4)
    @Test
    @DisplayName("get player self profile")
    public void getSelfPlayerProfile() {
        System.out.println("STEP 4: objects.Player get yourself profile");

        Response playerProfile = given().urlEncodingEnabled(true)
                .spec(Requests.getRequestSpecificationBuilder().build())
                .header("Authorization", "Bearer " + registratedUserAccessToken)
                .pathParam("userId", newPlayer.getId())
                .log().all()
                .when()
                .get(Requests.PLAYERS + "/{userId}");


        playerProfile
                .then()
                .log().body()
                .assertThat().statusCode(200)
                .body(matchesJsonSchemaInClasspath("getUserResponseJSONSchema.json"));

    }

    @Order(5)
    @Test
    @DisplayName("get another player profile")
    public void getOtherPlayerProfile() {
        System.out.println("STEP 5: objects.Player get profile another player");

        Response otherProfile = given().urlEncodingEnabled(true)
                .spec(Requests.getRequestSpecificationBuilder().build())
                .header("Authorization", "Bearer " + registratedUserAccessToken)
                .pathParam("userId", newPlayer.getId() - 1)
                .log().all()
                .when()
                .get(Requests.PLAYERS + "/{userId}");


        otherProfile
                .then()
                .log().body()
                .assertThat().statusCode(404);
    }
}
