package test;

import data.DataHelper;
import data.SQLHelper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static data.SQLHelper.cleanAuthCodes;
import static data.SQLHelper.cleanDatabase;

public class BankLoginTest {
    LoginPage loginPage;

    @AfterEach
    void tearDown() {
        cleanAuthCodes();
    }

    @AfterAll
    static void tearDownAll () {
        cleanDatabase();
    }

    @BeforeEach
    void setUp() {
        loginPage = open("http://Localhost:9999", LoginPage.class);
    }

    @Test
    void shoulValidUserTest() {
        var authInfo = DataHelper.getAuthInfo();
        var verifyPage = loginPage.validLogin(authInfo);
        verifyPage.verifyVerificationVisiblity();
        var verifyCode = SQLHelper.getVerificationCode();
        verifyPage.validVerify(verifyCode.getCode());
    }

    @Test
    void randomInvalidUserTest() {
        var authInfo = DataHelper.generateRandomUser();
        loginPage.validLogin(authInfo);
        loginPage.verifyErrorNotification("Ошибка! \nНеверно указан логин или пароль");
    }

    @Test
    void invalidCodeTest() {
        var authInfo = DataHelper.getAuthInfo();
        var verifyPage = loginPage.validLogin(authInfo);
        verifyPage.verifyVerificationVisiblity();
        var invalidCode = DataHelper.generateRandomVerificationCode();
        verifyPage.verify(invalidCode.getCode());
        verifyPage.verifyErrorNotification("Ошибка! Неверно указан код! Попробуйте ещё раз.");
    }

    @Test
    void fourInvalidUsersTest() {
        var authInfo = DataHelper.getUsersRandomPass();
        loginPage.validLogin(authInfo);
        loginPage.cleanLoginAndPass();
        authInfo = DataHelper.getUsersRandomPass();
        loginPage.validLogin(authInfo);
        loginPage.cleanLoginAndPass();
        authInfo = DataHelper.getUsersRandomPass();
        loginPage.validLogin(authInfo);
        loginPage.cleanLoginAndPass();
        authInfo = DataHelper.getUsersRandomPass();
        loginPage.validLogin(authInfo);
        loginPage.verifyErrorNotification("Пользователь заблокирован! \nВы 3 раза неверно ввели пароль." +
                " \nДля разблокировки аккаунта Вам необходимо обратиться в службу поддержки клиентов банка.");
    }

}
