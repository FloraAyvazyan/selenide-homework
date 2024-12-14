import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import ge.tbcitacademy.data.Constants;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static com.codeborne.selenide.Selenide.*;

public class ParametrizedSwoopTests2{

    private final String url;
    public ParametrizedSwoopTests2(String url){
        this.url = url;
    }
    @BeforeMethod
    public void setUp() {
        System.out.println("URL არის: " + url);
        open(url);
        WebDriverRunner.getWebDriver().manage().window().maximize();

    }

    @Test
    public void filterTest() throws InterruptedException {
        $x(Constants.SHESABAMISOBIT_XPATH).click();
        $x(Constants.BT_DESCENDING_ORDER).click();

        Thread.sleep(2000); //Thread.sleep-ის გარეშე ვერ გადავიდა გვერდეზე, და მგონი ინტერნეტის ბრალია ಥ_ಥ

        //მხოლოდ პირველ გვერდიდან წამოვიღე, იმედია არაა პრობლემა (┬┬﹏┬┬)
        List<Double> prices = new ArrayList<>();
        ElementsCollection pricesInWeb = $$x(Constants.PRICE_XPATH);
        System.out.println(pricesInWeb.size());
        for (SelenideElement priceElement : pricesInWeb) {
            String priceText = priceElement.getText();
            prices.add(Double.parseDouble(priceText.replace("₾", "").replace(",", "").trim()));
        }
        System.out.println(prices);

        double firstPrice = prices.getFirst();
        double maxPrice = Collections.max(prices);
        Assert.assertEquals(firstPrice, maxPrice, "The first offer is not the most expensive one.");

    }


    @Test
    public void priceRangeTest() throws InterruptedException {
        ElementsCollection inputs = $$x(Constants.INPUTS_XPATH);
        inputs.first().setValue(Constants.minP);
        inputs.last().setValue(Constants.maxP);
        $x(Constants.ENTER_BUTTON).click();

        Thread.sleep(2000); //აქაც ამის გარეშე არ გადავიდა

        List<Double> prices = new ArrayList<>();
        ElementsCollection pricesInWeb = $$x(Constants.PRICE_XPATH);
        System.out.println(pricesInWeb.size());
        for (SelenideElement priceElement : pricesInWeb) {
            String priceText = priceElement.getText().replace("₾", "").replace(",", "").trim();
            double price = Double.parseDouble(priceText);
            prices.add(price);
            Assert.assertTrue(price >= Double.parseDouble(Constants.minP) && price <= Double.parseDouble(Constants.maxP),
                    "Offer price " + price + " is outside the specified range (" + Constants.minP + " - " + Constants.maxP + ")");

        }
        System.out.println(prices);


    }


}














































