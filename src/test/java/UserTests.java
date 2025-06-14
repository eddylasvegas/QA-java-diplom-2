import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import models.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import steps.UserTestSteps;
import utils.RandomDataGenerator;

public class UserTests {
    private UserTestSteps steps;
    private User user;
    private String token;

    @Before
    public void setUp() {
        steps = new UserTestSteps();
        user = RandomDataGenerator.createRandomUser();
    }

    @After
    public void tearDown() {
        if (token != null) {
            steps.deleteUser(token);
        }
    }

    @Test
    @DisplayName("Создание уникального пользователя")
    @Description("Проверка успешного создания пользователя с валидными данными")
    public void testCreateUniqueUser() {
        Response response = steps.createUser(user);
        steps.verifyUserCreatedSuccessfully(response);
        token = response.path("accessToken");
    }

    @Test
    @DisplayName("Создание пользователя, который уже зарегистрирован")
    @Description("Проверка ошибки при попытке создать уже существующего пользователя")
    public void testCreateDuplicateUser() {
        steps.createUser(user);
        Response duplicateResponse = steps.createUser(user);
        steps.verifyDuplicateUserError(duplicateResponse);
    }

    @Test
    @DisplayName("Создание пользователя без обязательного поля")
    @Description("Проверка ошибки при попытке создать пользователя без email")
    public void testCreateUserWithoutRequiredField() {
        user.setEmail(null);
        Response response = steps.createUser(user);
        steps.verifyMissingFieldError(response);
    }
}