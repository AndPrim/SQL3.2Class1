package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import data.DataHelper;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private SelenideElement login = $("[data-test-id=login] input");
    private SelenideElement pass = $("[data-test-id=password] input");
    private SelenideElement button = $("[data-test-id=action-login]");
    private SelenideElement notification = $("[data-test-id=error-notification]" +
            " div.notification__content");
    public void verifyErrorNotification(String text){
        notification.shouldHave(exactText(text)).shouldBe(visible);
    }

    public VerificationPage validLogin(DataHelper.AuthInfo info){
        login.setValue(info.getLogin());
        pass.setValue(info.getPassword());
        button.click();
        return new VerificationPage();
    }
}
