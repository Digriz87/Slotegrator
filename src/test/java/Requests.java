import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

import static io.restassured.RestAssured.*;

public class Requests {
    public static final String BASIC_AUTH_TOKEN = "front_2d6b0a8391742f5d789d7d915755e09e";
    public static final String BASE_URI = "http://test-api.d6.dev.devcaz.com";
    public static final String BASE_PATH = "/v2";
    public static final String OAUTH2 = "/oauth2/token";
    public static final String PLAYERS = "/players";

    private static RequestSpecBuilder requestSpecificationBuilder;

    static RequestSpecBuilder getRequestSpecificationBuilder() {
        createRequestSpecification();
        return requestSpecificationBuilder;
    }

    private static void createRequestSpecification() {
        requestSpecificationBuilder = new RequestSpecBuilder()
                .setBaseUri(BASE_URI)
                .setBasePath(BASE_PATH)
                .addHeader("Accept", ContentType.JSON.getAcceptHeader());
    }

    static Response auth(String grantTypeValue, Map authParams) {
        createRequestSpecification();

        RequestSpecification authParamsSpecification = requestSpecificationBuilder
                .addParams(authParams)
                .build();

        return given().urlEncodingEnabled(true)
                .auth().preemptive()
                .basic(BASIC_AUTH_TOKEN, "")
                .param("grant_type", grantTypeValue)
                .spec(authParamsSpecification)
                .when()
                .log().all()
                .post(OAUTH2);
    }
}
