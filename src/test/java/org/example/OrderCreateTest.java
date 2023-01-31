package org.example;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.order.Order;
import org.example.order.OrderClient;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;

@RunWith(Parameterized.class)
public class OrderCreateTest {
    private Order order;
    private OrderClient orderClient;
    private final int expectedCodeResult;


    public OrderCreateTest(Order order,int expectedCodeResult) {
        this.order = order;
        this.expectedCodeResult = expectedCodeResult;
    }

    @Parameterized.Parameters
    public static Object [][] getOrderData (){
        return new Object[][]{
                {Order.getRandomOrder(), 201},
                {Order.getOrderWithoutColor(), 201},
                {Order.getOrderWithColor("BLACK"), 201},
                {Order.getOrderWithColor("GREY"), 201}
        };
    }

    @Before
    public void setUp() {
        orderClient = new OrderClient();
        order = Order.getRandomOrder();
    }

    @After
    public void tearDown(){
        orderClient.cancel(order);
    }

    @Test
    @DisplayName("Создание заказа")
    public void createOrder(){
        ValidatableResponse response = orderClient.create(order);
        int actualCodeResult = response.extract().statusCode();
        int track = response.extract().path("track");
        Assert.assertEquals(expectedCodeResult,actualCodeResult);
        assertThat("Track is null", track, is(not(0)));
    }
}
