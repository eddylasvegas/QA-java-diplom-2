package api;

import io.restassured.response.Response;
import models.Order;
import models.User;

import static io.restassured.RestAssured.given;

public class ApiClient {
    private static final String AUTH_REGISTER = "/api/auth/register";
    private static final String AUTH_LOGIN = "/api/auth/login";
    private static final String AUTH_USER = "/api/auth/user";
    private static final String ORDERS = "/api/orders";

    public Response createUser(User user) {
        return given()
                .spec(ApiConfig.getDefaultRequestSpec())
                .body(user)
                .post(AUTH_REGISTER);
    }

    public Response loginUser(User user) {
        return given()
                .spec(ApiConfig.getDefaultRequestSpec())
                .body(user)
                .post(AUTH_LOGIN);
    }

    public Response deleteUser(String token) {
        return given()
                .spec(ApiConfig.getRequestSpecWithAuth(token))
                .delete(AUTH_USER);
    }

    public Response createOrder(Order order, String token) {
        if (token != null) {
            return given()
                    .spec(ApiConfig.getRequestSpecWithAuth(token))
                    .body(order)
                    .post(ORDERS);
        } else {
            return given()
                    .spec(ApiConfig.getDefaultRequestSpec())
                    .body(order)
                    .post(ORDERS);
        }
    }
}