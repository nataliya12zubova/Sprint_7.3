package org.example;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.Courier.Courier;
import org.example.Courier.CourierClient;
import org.example.Courier.CourierCredentials;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;


@RunWith(Parameterized.class)
public class CourierParametrizedTest {
    private final CourierCredentials courierCredentials;
    private final int expectedCodeResult;
    private final String expectedMessageError;

    public CourierParametrizedTest(CourierCredentials courierCredentials, int expectedCodeResult, String expectedMessageError) {
    this.courierCredentials = courierCredentials;
    this.expectedCodeResult = expectedCodeResult;
    this.expectedMessageError = expectedMessageError;
    }

@Parameterized.Parameters
public static Object[] getTestData() {
    return new Object[][]{
            {CourierCredentials.from(Courier.getCourierWithLoginAndPassword()), 404, "Учетная запись не найдена"},
            {CourierCredentials.getCourierCredentialsWithInvalidLogin(Courier.getCourierWithLoginAndPassword()), 404, "Учетная запись не найдена"},
            {CourierCredentials.getCourierCredentialsWithInvalidPassword(Courier.getCourierWithLoginAndPassword()), 404, "Учетная запись не найдена"},
            {CourierCredentials.from(Courier.getCourierWithLoginOnly()), 400, "Недостаточно данных для входа"},
            {CourierCredentials.from(Courier.getCourierWithPasswordOnly()), 400, "Недостаточно данных для входа"}
    };
}

@Test
@DisplayName("Нельзя залогиниться с некорректными данными")
public void courierLoginWithInvalidData() {
    ValidatableResponse response = new CourierClient().login(courierCredentials);
    int actualCodeResult = response.extract().statusCode();
    String actualMessageError = response.extract().path("message");
    Assert.assertEquals(actualCodeResult, expectedCodeResult);
    Assert.assertEquals(actualMessageError, expectedMessageError);
    }
}

