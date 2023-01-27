package org.example;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.example.Order.Order;
import org.example.Order.OrderClient;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;

@RunWith(Parameterized.class)
public class OrderCreateTest {
    private final Order order;
    private final int expectedCodeResult;
    Response response;

    public OrderCreateTest(Order order,int expectedCodeResult) {
        this.order = order;
        this.expectedCodeResult = expectedCodeResult;
    };

    @Parameterized.Parameters
    public static Object [][] getOrderData (){
        return new Object[][]{
                {Order.getRandomOrder(), 201},
                {Order.getOrderWithoutColor(), 201},
                {Order.getOrderWithColor("BLACK"),201},
                {Order.getOrderWithColor("GREY"),201}
        };
    }

    @Test
    @DisplayName("Создание заказа")
    public void createOrder(){
        ValidatableResponse response = new OrderClient().create(order);
        int actualCodeResult = response.extract().statusCode();
        int track = response.extract().path("track");
        Assert.assertEquals(actualCodeResult,expectedCodeResult);
        assertThat("Track is null", track, is(not(0)));
        new OrderClient().cancel(order);
    }
}
