package page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    private SelenideElement codeField = $("[data-test-id=code] input");
    private SelenideElement verifyButton = $("[data-test-id=action-verify]");
    private SelenideElement notification = $("[data-test-id=error-notification]" +
            " div.notification__content");
    public void verifyVerificationVisiblity(){
        codeField.shouldBe(visible);
    }
    public void verifyErrorNotification(String text){
        notification.shouldHave(exactText(text)).shouldBe(visible);
    }
    public void verify(String verifyCode){
        codeField.setValue(verifyCode);
        verifyButton.shouldBe();
    }
    public DashboardPage validVerify(String verifyCode){
        verify(verifyCode);
        return new DashboardPage();
    }
}
