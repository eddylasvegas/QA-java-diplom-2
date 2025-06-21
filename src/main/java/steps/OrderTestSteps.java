package steps;

import api.ApiClient;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.Order;
import static org.apache.http.HttpStatus.*;

import static org.hamcrest.Matchers.equalTo;

public class OrderTestSteps {
    private final ApiClient apiClient = new ApiClient();

    @Step("Создать заказ")
    public Response createOrder(Order order, String token) {
        return apiClient.createOrder(order, token);
    }

    @Step("Проверить успешное создание заказа")
    public void verifyOrderCreatedSuccessfully(Response response) {
        response.then()
                .statusCode(SC_OK)
                .body("success", equalTo(true));
    }

    @Step("Проверить ошибку при создании заказа без ингредиентов")
    public void verifyEmptyIngredientsError(Response response) {
        response.then()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Ingredient ids must be provided"));
    }

    @Step("Проверить ошибку при невалидных ингредиентах")
    public void verifyInvalidIngredientsError(Response response) {
        response.then()
                .statusCode(SC_INTERNAL_SERVER_ERROR);
    }
}