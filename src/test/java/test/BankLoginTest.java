package test;

import data.DataHelper;
import data.SQLHelper;
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
    void tearDown(){
        cleanAuthCodes();
    }
    @AfterEach
    void tearDownAll(){
        cleanDatabase();
    }
    @BeforeEach
    void setUp(){
        loginPage = open("http://Localhost:9999", LoginPage.class);
    }
    @Test
    void shoulValid(){
        var authInfo = DataHelper.getAuthInfo();
        var verifyPage = loginPage.validLogin(authInfo);
        verifyPage.verifyVerificationVisiblity();
        var verifyCode = SQLHelper.getVerificationCode();
        verifyPage.validVerify(verifyCode.getCode());
    }
}
