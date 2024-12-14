import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import dataProvider.DataProviderCustom;
import ge.tbcitacademy.data.Constants;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.concurrent.atomic.AtomicInteger;
import static com.codeborne.selenide.Condition.innerText;
import static com.codeborne.selenide.Selenide.*;

public class ParametrizedSwoopTests extends ConfigTest{
    @BeforeMethod
    public void before(){
        open(Constants.SWOOP_URL);
    }


    @Test(dataProvider = "offersDataProvider", dataProviderClass = DataProviderCustom.class)
    public void checkSaleValuesTest(String offerName, int originalPrice, int discountPrice ) throws InterruptedException {

        $(By.linkText("სპორტი")).click();
        Thread.sleep(2000);

        ElementsCollection allOffers = $$x(Constants.ALLOFFERS_XPATH);
        AtomicInteger atomicExpectedPrice = new AtomicInteger(Integer.MAX_VALUE);
        allOffers.filterBy(innerText(offerName))
                .get(0).$$x(Constants.PRICE_XPATH_FROMOFFER).forEach(element -> {
                    if (!element.getText().isBlank()) {
                        int price = (int) Double.parseDouble(element.getText().trim().replaceAll("[^\\d.]", ""));
                        if (price < atomicExpectedPrice.get()) {
                            atomicExpectedPrice.set(price); //აქ   set(int)-ის გამო Int-ად გარდავქმნი price
                        }

                    }

                });

        int actualPrice = atomicExpectedPrice.get();
        Assert.assertEquals(actualPrice, originalPrice - discountPrice);

    }


    @Test(dataProvider = "offerNames", dataProviderClass = DataProviderCustom.class)
    public void validateCartBehavior(String offerName) throws InterruptedException {
        $(By.linkText("სპორტი")).shouldBe(Condition.visible).click();

        Thread.sleep(2000);

        SelenideElement offer = $$x(Constants.ALLOFFERS_XPATH).filterBy(innerText(offerName)).get(0);
        offer.scrollTo().click();

        SelenideElement addBtr = $x(Constants.ADD_BOX);
        addBtr.scrollTo().click();

        SelenideElement kalataBtn = $x(Constants.ADD_BOX_ICON);
        kalataBtn.click();

        ElementsCollection itemTitleInBasket = $$x("//p[contains(@class, 'mt-1')]");
        itemTitleInBasket.shouldHave(CollectionCondition.itemWithText(offerName));

    }


}











