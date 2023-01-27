package org.example;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.Order.Order;
import org.example.Order.OrderClient;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class OrderListTest {
    private Order order;
    private OrderClient orderClient;

    @Before
    public void setUp(){
        orderClient = new OrderClient();
    }
    @Test
    @DisplayName("Получение списка заказов")
    public void getOrderList (){
        ValidatableResponse response = orderClient.getAllOrders();
        int statusCode = response.extract().statusCode();
        List <Object> orders = response.extract().jsonPath().getList("orders");
        Assert.assertEquals(statusCode,200);
        Assert.assertFalse(orders.isEmpty());
    }
}
