package org.example;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.Courier.Courier;
import org.example.Courier.CourierClient;
import org.example.Courier.CourierCredentials;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CourierCreateTest {

    private Courier courier;
    private CourierClient courierClient;
    private int courierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = Courier.getRandom();
    }
    @After
    public void tearDown() {
        courierClient.delete(courierId);
    }

    @Test
    @DisplayName("Успешное создание курьера")
    public void courierCanBeCreated() {

        ValidatableResponse response = courierClient.create(courier);
        courierId = courierClient.login(CourierCredentials.from(courier)).extract().path("id");
        int statusCode = response.extract().statusCode();
        boolean isCreated = response.extract().path("ok");
        Assert.assertEquals(statusCode, 201);
        Assert.assertTrue(isCreated);
        assertThat("Courier ID is incorrect", courierId, is(not(0)));
    }

    @Test
    @DisplayName("Нельзя создать двух одинаковых курьеров")
    public void courierDoubleCanNotBeCreated()  {

        courierClient.create(courier);
        courierId = courierClient.login(CourierCredentials.from(courier)).extract().path("id");
        ValidatableResponse response = courierClient.create(courier);
        int statusCode = response.extract().statusCode();
        String messageError = response.extract().path("message");
        Assert.assertEquals(statusCode, 409);
        Assert.assertEquals(messageError, "Этот логин уже используется");
    }

    @Test
    @DisplayName("Нельзя создать курьера с существующим логином")
    public void courierCanNotBeCreatedWithExistingLogin()  {

        courierClient.create(courier);
        courierId = courierClient.login(CourierCredentials.from(courier)).extract().path("id");
        courier.setPassword(RandomStringUtils.randomAlphabetic(5));
        courier.setFirstName(RandomStringUtils.randomAlphabetic(5));
        ValidatableResponse response = courierClient.create(courier);
        int statusCode = response.extract().statusCode();
        String messageError = response.extract().path("message");
        Assert.assertEquals(statusCode, 409);
        Assert.assertEquals(messageError, "Этот логин уже используется");
    }
}


