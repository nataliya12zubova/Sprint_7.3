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
public class CreateCourierParameterizedTest {

    private final Courier courier;
    private final int expectedCodeResult;
    private final String expectedMessage;

    public CreateCourierParameterizedTest(Courier courier, int expectedCodeResult, String expectedMessage){
        this.courier = courier;
        this.expectedCodeResult = expectedCodeResult;
        this.expectedMessage = expectedMessage;
    }

    @Parameterized.Parameters
    public static Object[] getTestData() {
        return new Object[][] {
                {Courier.getCourierWithLoginOnly(), 400, "Недостаточно данных для создания учетной записи"},
                {Courier.getCourierWithFirstnameOnly(), 400, "Недостаточно данных для создания учетной записи"},
                {Courier.getCourierWithPasswordOnly(), 400, "Недостаточно данных для создания учетной записи"},
                {Courier.getCourierWithLoginAndPassword(), 400, "Недостаточно данных для создания учетной записи"}
        };
    }
    @Test
    @DisplayName("Создание курьера с различными невалидными данными")
    public void createCourierWithInvalidData() {
        ValidatableResponse response = new CourierClient().create(courier);
        int actualCodeResult = response.extract().statusCode();
        String actualMessage = response.extract().path("message");

        Assert.assertEquals(actualCodeResult, expectedCodeResult);
        Assert.assertEquals(actualMessage, expectedMessage);
        if(actualCodeResult == 201) {
            int courierId = new CourierClient().login(CourierCredentials.from(courier)).extract().path("id");
            new CourierClient().delete(courierId);
        }
    }
}
