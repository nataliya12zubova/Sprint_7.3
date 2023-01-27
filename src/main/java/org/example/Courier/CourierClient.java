package org.example.Courier;

import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;
import io.qameta.allure.Step;

public class CourierClient extends Client {
    private static final String COURIER_PATH = "api/v1/courier/";

    @Step
    public ValidatableResponse create(Courier courier) {

        return given()
                .spec(getSpec())
                .body(courier)
                .when()
                .post(COURIER_PATH).then();
    }

    @Step
    public ValidatableResponse login(CourierCredentials credentials) {
        return given()
                .spec(getSpec())
                .body(credentials)
                .when()
                .post(COURIER_PATH + "login/")
                .then();
    }

    @Step
    public ValidatableResponse delete(int courierId) {
        return given()
                .spec(getSpec())
                .when()
                .delete(COURIER_PATH + courierId)
                .then()
                .assertThat()
                .statusCode(200);
    }
}
