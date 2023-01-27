package org.example.Order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.example.Courier.Client;

import static io.restassured.RestAssured.given;

public class OrderClient  extends Client {
    private static final String ORDER_PATH = "/api/v1/orders";

    @Step
    public ValidatableResponse create(Order order){
        return given()
                .spec(getSpec())
                .body(order)
                .when()
                .post(ORDER_PATH)
                .then();
    }

    @Step
    public ValidatableResponse cancel (Order order) {
        return given ()
                .spec(getSpec())
                .body(order)
                .when()
                .post(ORDER_PATH + "/cancel")
                .then();
    }

    @Step
    public ValidatableResponse getAllOrders() {
        return given ()
                .spec(getSpec())
                .when()
                .post(ORDER_PATH)
                .then();
    }
}
