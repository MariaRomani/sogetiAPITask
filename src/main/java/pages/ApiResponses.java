package pages;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ApiResponses {
    public Response getResponse(String uri) {
        return given().get(uri);
    }

    public static Response getResponseWithPathParams(String country, String postalCode) {
        String baseURI = "http://api.zippopotam.us";
        String url = String.format("%s/%s/%s", baseURI, country, postalCode);

        return given()
                .when()
                .get(url)
                .then()
                .extract()
                .response();


    }
}
