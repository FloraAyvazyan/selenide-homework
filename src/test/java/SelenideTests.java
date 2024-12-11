import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ge.tbcitacademy.data.Constants;
import org.testng.annotations.Test;

import java.util.List;

import static com.codeborne.selenide.CollectionCondition.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;


@Test(groups = {"Selenide 1"})
public class SelenideTests extends ConfigTest{

    @Test
    public void validateBundleOffers() {
        open(Constants.TELERIK_URL);
        $(byText("Pricing")).click();

        //	1) 'Mocking solution for rapid unit testing' feature is not included in DevCraft UI.
        ElementsCollection firstOffer = $$x("//th[@class='UI is-active']//child::ul/li");
        for (SelenideElement offerItem : firstOffer) {
            offerItem.shouldNotHave(text("Mocking solution for rapid unit testing"));
            offerItem.shouldNotHave(text("End-to-end report management solution"));
        }

        //	2) 'Issue escalation' is supported only in DevCraft Ultimate.
        $x("//th[@class= 'Ultimate u-pb2']//p[@class = 'u-fs12 u-fwn u-mb0']").shouldHave(text("Issue escalation"));
        $x("//th[@class= 'Complete u-pb2']//p[@class = 'u-fs12 u-fwn u-mb0']").shouldNotHave(text("Issue escalation"));
        $x("//th[@class= 'UI u-pb2 is-active']//p[@class = 'u-fs12 u-fwn u-mb0']").shouldNotHave(text("Issue escalation"));


        //	3) 'End-to-end report management solution' is supported only in DevCraft Ultimate.
        ElementsCollection secondOffer = $$x("//th[@class='Complete']//child::ul/li");
        ElementsCollection thirdOffer = $$x("//th[@class='Ultimate']//child::ul/li");
        for (SelenideElement offerItem : secondOffer) {
            offerItem.shouldNotHave(text("End-to-end report management solution"));
        }
        thirdOffer.shouldHave(CollectionCondition.itemWithText("End-to-end report management solution"));

        //	4) 'Telerik Test Studio Dev Edition' is supported only in DevCraft Ultimate.
        SelenideElement telerikText = $x("//p[contains(text(),'Telerik Test Studio')]/parent::td");
        ElementsCollection telerikTextSupp = telerikText.parent().$$("th");
        for (int i = 0; i < telerikTextSupp.size(); i++) {
            if (i == telerikTextSupp.size() - 1) {
                telerikTextSupp.get(1).shouldHave(text("●"));
            } else {
                telerikTextSupp.get(i).shouldNotHave(text("●"));
            }
        }

        //	5) 'Kendo UI for jQuery' is supported on all offers.
        SelenideElement KendoUI = $x("//td[text() = 'Kendo UI for jQuery']");
        ElementsCollection KendoUISupp = KendoUI.parent().$$("tr");
        for (int i = 0; i < KendoUISupp.size(); i++) {
            if (i == 0) {
                KendoUISupp.get(i).shouldNotHave(text("●"));
            } else {
                KendoUISupp.get(i).shouldHave(text("●"));
            }
        }
        //	6) DevCraft Ultimate supports 1 instance of 'Telerik Report Server' with 15 users .

        SelenideElement TelerikReport = $x("//td//p[contains(text(),'Telerik Report Server')]");
        ElementsCollection users = TelerikReport.parent().$$("tr");
        for (int i = 0; i < users.size(); i++) {
            if (i == users.size() - 1) {
                users.get(i).shouldHave(text("1 instance with 15 users"));
            }

        }

        //	7) 'Telerik Reporting' is supported by only DevCraft Complete and DevCraft Ultimate.
        SelenideElement TelerikReporting = $x("//td//p[contains(text(),'Telerik Reporting')]");
        ElementsCollection items = TelerikReporting.parent().$$("tr");
        for (int i = 0; i < items.size(); i++) {
            if (i == 0) {
                items.get(i).shouldNotHave(text("●"));
            } else {
                items.get(i).shouldHave(text("●"));
            }
        }

        //	8) 'Access to on-demand videos' is supported by all offers.
        SelenideElement Accessto = $(byText("Access to on-demand videos"));
        ElementsCollection AccessToItems = Accessto.parent().$$("tr");
        for (int i = 0; i < AccessToItems.size(); i++) {
            AccessToItems.get(i).shouldHave(text("●"));
        }

        // - Validate that the offer names remain sticky (if you don't know what sticky term means,
        // search it up on web) when the page is being scrolled down.
        SelenideElement stickyHeader = $("#js-sticky-head");
        stickyHeader.shouldNotHave(cssClass("is-fixed"));
        TelerikReport.scrollTo();
        stickyHeader.shouldHave(cssClass("is-fixed"));


    }


    @Test
    public void validateIndividualOffers(){
        // - Find pricing page and check following things in Individual Products:
        open(Constants.TELERIK_URL);
        $(byText("Pricing")).click();
        $(byText("Individual Products")).click();

        //	1) A Kendo Ninja image appears once you hover over any of the two offers.
        SelenideElement firstOffer = $(by("data-opti-expid", "Kendo UI")).shouldBe(visible);
        SelenideElement secondOffer =  $(by("data-opti-expid", "KendoReact"));
        firstOffer.scrollTo().click();
        $("img[title='Kendo Ui Ninja']").shouldBe(visible); //firstNinja
        secondOffer.hover();
        $("img[title='Kendo React Ninja']").shouldBe(visible); //secondNinja

        //	2) Both offers have Priority Support selected by default.
        $x("//a[@href = \"#\" and @data-track-instance = '1']").shouldHave(text("Priority Support"));
        $x("//a[@href = \"#\" and @data-track-instance = '2']").shouldHave(text("Priority Support"));

        //	3) The price of Priority Support is $999 on KendoReact.
        secondOffer.$(".js-price").shouldHave(text("999"));
        //	4) The price of Priority Support is $1149 on Kendo UI.
        firstOffer.$(".js-price").shouldHave(text("1,149"));


    }



    @Test(groups = {"CheckBoxes-FrontEnd"})
    public void checkBoxTest(){
        open(Constants.CHECKBOXES_URL);
        // - Set first checkbox selected
        $(by("type", "checkbox")).setSelected(true);
        // - Validate that both checkboxes have type 'checkbox'.
        $$("#checkboxes input").forEach(el -> el.shouldHave(type("checkbox")));
    }



    @Test(groups = {"dropDown-FrontEnd"}, priority = 3)
    public void dropDownTest(){
        open(Constants.DROPDOWN_URL);
        SelenideElement dropdown =  $("#dropdown");
        // - Validate that 'Please select an option' is selected
        dropdown.getSelectedOption().shouldHave(text("Please select an option"));
        // - Select 'Option 2'
        dropdown.selectOption("Option 2");
        // - Validate that 'Option 2' was selected
        dropdown.getSelectedOption().shouldHave(text("Option 2"));

    }

    //5) collectionsTest (RUN ONLY ON FIREFOX):


    @Test
    public void collectionsTest() {
        open(Constants.DEMOQA_URL);
        $(byText("Text Box")).scrollTo();
        // - Fill fullname, valid email, current and permanent adresses using different selectors
        $("#userName").setValue("Flora");
        $(by("type", "email")).setValue("plora.ayvazyan053@hum.tsu.edu.ge");
        $("textarea[placeholder = 'Current Address']").setValue("Tbilisi");
        $("textarea", 1).setValue("Tsalka");
        $("#submit").scrollTo().click();

        // - Validate the result with a Collection Assetion
        List<String> result = List.of(
                "Name:Flora\n",

                "Email:plora.ayvazyan053@hum.tsu.edu.ge\n",

                "Current Address :Tbilisi\n",

                "Permananet Address :Tsalka"
        );

        ElementsCollection realResult = $$(".mb-1");
        realResult.shouldHave(exactTexts(result));

    }


}
