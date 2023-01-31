package org.example.сourier;

import org.apache.commons.lang3.RandomStringUtils;

public class CourierCredentials {
    public String login;
    public String password;

    public CourierCredentials(){};

    public CourierCredentials(String login, String password) {
        this.login = login;
        this.password = password;
    }
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static CourierCredentials from(Courier courier) {
        return new CourierCredentials(courier.login, courier.password);
    }

    public static CourierCredentials getCourierCredentialsWithInvalidLogin(Courier courier){
        courier.setLogin(RandomStringUtils.randomAlphabetic(5));
        return new CourierCredentials(courier.login, courier.password);
    }
    public static CourierCredentials getCourierCredentialsWithInvalidPassword(Courier courier){
        courier.setPassword(RandomStringUtils.randomAlphabetic(5));
        return new CourierCredentials(courier.login, courier.password);
    }
    public static CourierCredentials getCourierCredentialsWithoutPassword(Courier courier){
        return new CourierCredentials(courier.login, null);
    }

    public static CourierCredentials getCourierCredentialsWithoutLogin(Courier courier){
        courier.setLogin(null);
        return new CourierCredentials(courier.login, courier.password);
    }


}
