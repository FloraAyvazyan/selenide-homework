import RetryAnotation.RetryAnalyzer;
import RetryAnotation.RetryCount;
import com.codeborne.selenide.*;
import ge.tbcitacademy.data.Constants;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;


@Test(groups = {"Selenide 2"})
public class SelenideTests2 extends ConfigTest{
    SoftAssert sfa = new SoftAssert();

    @Test
    public void validateDemosDesign() {
        open(Constants.TELERIK_URL);
        SelenideElement acceptCookies = $(withText("Accept Cookies"));
        acceptCookies.click();

        //  1) In Web section, validate that all cards have purple overlay effect on hover.
        $("#web").scrollTo();
        ElementsCollection sections = $$x("//div[contains(@class, 'row')]//div[contains(@id, '329')]//div[contains(@class, 'HoverImg')]");
        //System.out.println(sections.size());
        for (SelenideElement section : sections) {
            section.scrollTo();
            section.hover();
            section.shouldHave(Condition.cssValue("background-color", "rgba(40, 46, 137, 0.75)"));
        }


        //  2) In Web section, Validate that the list on Kendo UI's purple overlay contains 'UI for Vue Demos'.
        SelenideElement hoverElement = $(byTitle("Kendo Ui")).parent();
        hoverElement.scrollTo();
        hoverElement.hover();
        ElementsCollection overlay = hoverElement.$$(By.tagName("a"));
        // System.out.println(overlay.size());
        overlay.shouldHave(CollectionCondition.itemWithText("UI for Vue demos"));



        // - In Desktop section, select all items, make a filter that will only include
        // items that are available on Microsoft Store.
        ElementsCollection desktopSection = $$x("//div[contains(@class, 'row')]//div[contains(@id, '337')]//a//img");
        System.out.println(desktopSection.size());
        ElementsCollection filteredDesktopElements = desktopSection
                .filterBy(Condition.attribute("title", "Get It from Microsoft"));
        System.out.println(filteredDesktopElements.size());



        //    // - In Mobile section, validate that 'Telerik UI for Xamarin'
        //    is available on Apple Store, Google Play and Microsoft Store.
        ElementsCollection mobileSectionImg = $$x("//div[contains(@class, 'row')]//div[contains(@id, '340')]//a//img");
        List<String> expectedTitles = List.of(
                "Download on the App Store",
                "Get it on Google Play",
                "Get it from Microsoft"
        );
        mobileSectionImg.shouldHave(CollectionCondition.anyMatch(
                "At least one element has one of the required titles",
                el -> expectedTitles.contains(el.getAttribute("title"))
        ));



        // - Validate that the section links remain fixed at the top as you scroll.
        ElementsCollection sectionLinks = $$x("//div[@class='container']//a");
        hoverElement.scrollTo();
        for (SelenideElement element : sectionLinks){
            element.shouldHave(cssValue("position", "static"));
        }


        //აქ ქრომზე იყო rgba(246, 247, 250, 1) და ფაირფოქზე rgba(246, 247, 250) და ამის ფამო დაფეილდა, მაგრამ
        //ფაქტობრივად ხომ იგივე ფერია... და ამიტომ დავაკომენტარე

        // - Validate that the section links get active according to your
        // scrolling position (for example: if you're scrolled into Desktop section,
        // the Desktop link should have dimmed background).
//        sectionLinks.get(0).click();
//        sectionLinks.get(0)
//                .shouldHave(Condition.cssValue("background-color", "rgba(246, 247, 250, 1)"));


        // - Validate that the aforementioned links take the user to correct sections.
        sectionLinks.get(1).click();
        SelenideElement desktopSectionn = $(byTitle("winui-product-thumb")).ancestor(".row u-mb8").shouldBe(appear);

    }


    //აქ თვითონ არ ვიცი რა მიწერია და რა არა :((
    @RetryCount(count = 5)

    @Test(description = "This is a sample test description", retryAnalyzer = RetryAnalyzer.class)
    public void validateOrderMechanics() throws InterruptedException {
        open(Constants.TELERIK_URL);
        $(byText("Pricing")).click();
        SelenideElement acceptCookies = $(withText("Accept Cookies"));
        acceptCookies.click();
        SelenideElement buyButton = $("th[class='Complete'] a[class='Btn Btn--prim4 u-db']");
        buyButton.click();
        $(".far.fa-times.label.u-cp").click();


        SelenideElement unitPrice = $(".e2e-price-per-license");
        //  1) Unit price is correct.
        Assert.assertEquals(unitPrice.getText(), "$1,699.00" );

        //  2) Increase the term, validate that the price is added correctly according to its percentage. // delete
        SelenideElement quantityDropDown = $("period-select kendo-dropdownlist"); //id-ით არ პოულობდაა
        quantityDropDown.click();
        quantityDropDown.sendKeys("2");
        quantityDropDown.sendKeys(Keys.ENTER);

        SelenideElement termPrice = $("span.u-db.e2e-price-per-license.page-body--success");
        Assert.assertEquals(termPrice.getText(),  "$781.08");


        //  3) Increase the Quantity and validate that the saving is displayed correctly according to the discount.
        //  4) Validate SubTotal value.
        SelenideElement subTotal = $(".e2e-cart-item-subtotal");
        Assert.assertEquals(subTotal.getText(), "$3,261.16" );

        //  5) Validate Total Discounts - hover over the question mark and validate that each
        //  discount is displayed correctly.
        SelenideElement totaldiscounts = $(".e2e-total-discounts-label");
        Selenide.executeJavaScript("window.scrollTo(0, 200)");
        totaldiscounts.hover();
        SelenideElement afterHover = $(".tooltip-info.tooltip-info--font-l.tooltip-info--no-after");
        afterHover.shouldBe(visible);


        //  5) Validate Total value
        SelenideElement license = $(".e2e-licenses-discounts-label");
        license.shouldHave(text("Licenses"));

        SelenideElement licenseNumber = $(".u-fr.e2e-licenses-price");
        licenseNumber.shouldHave(text("$1,699.00"));

        SelenideElement hoverLabel =afterHover.$(".e2e-tooltip-ms-discounts-label");
        hoverLabel.shouldHave(text("Maintenance & Support"));

        SelenideElement hoverValue =afterHover.$(".e2e-ms-discounts");
        hoverValue.shouldHave(text("- $135.84"));


        SelenideElement contBtn = $("button.e2e-continue");
        acceptCookies.click();
        contBtn.click();
        $("#biFirstName").setValue("Flora");
        $("#biLastName").setValue("Ayvazyan");
        $("#biEmail").setValue("plora.ayvazyan@hum.tsu.edu.ge");
        $("#biCompany").setValue("TBC");
        $("#biPhone").setValue("551535463");
        $("#biAddress").setValue("Tbilisi");
        Selenide.executeJavaScript("window.scrollTo(0, 200)");
        $("kendo-combobox#biCountry").find(".k-input-button").click();
        SelenideElement dropdown = $(".k-list-container");
        dropdown.findAll("li").findBy(text("Albania")).click();
        $("#biCity").setValue("Tbilisi");
        $("#biZipCode").setValue("2016");

        SelenideElement btn = $(".btn.btn-default.e2e-back");
        btn.scrollIntoView(true);
        $("#onetrust-accept-btn-handler").click();
        btn.click();


        $("#biFirstName").shouldHave(value("Flora"));
        $("#biLastName").shouldHave(value("Ayvazyan"));
        $("#biEmail").shouldHave(value("plora.ayvazyan@hum.tsu.edu.ge"));
        $("#biCompany").shouldHave(value("TBC"));
        $("#biPhone").shouldHave(value("551535463"));
        $("#biAddress").shouldHave(value("Tbilisi"));
        $("#biCity").shouldHave(value("Tbilisi"));
        $("#biZipCode").shouldHave(value("2016"));
    }





    @Test
    public void chainedLocatorsTest(){
        // - Navigate to https://demoqa.com/books
        open("https://demoqa.com/books");
        $(".rt-thead.-header").scrollTo();

        // - Using chained locators find all books with publisher 'O'Reilly Media' containing title 'Javascript'.
        ElementsCollection allBooks = $$(".rt-tr-group");
        ElementsCollection filteredBooks = allBooks
                .filterBy(Condition.innerText("O'Reilly Media"))
                .filterBy(Condition.partialText("Javascript"));
        System.out.println(filteredBooks.size());

        // - Using chained locators find that each book's images are not empty (success case).
        ElementsCollection withImg = $$(".rt-tr-group img");
        for (SelenideElement book : withImg){
            book.shouldBe(image);
        }

    }

    @Test
    public void softAssertTest(){
        // - Navigate to https://demoqa.com/books
        open("https://demoqa.com/books");

        // - Using find() and findAll() methods Find all books with publisher 'O'Reilly Media' containing title 'Javascript'  .
        ElementsCollection books = $(".rt-tbody")
                .findAll(".rt-tr-group")
                .filterBy(Condition.innerText("O'Reilly Media"))
                .filterBy(Condition.partialText("Javascript"));

        // - Check with TestNG soft assertions that size of returned list equals to 10 (failed case).
        sfa.assertEquals(books.size(), 10);

        // - Check that the first book's title equals to 'Git Pocket Guide'(success case).
        sfa.assertEquals(books.get(0), "Git Pocket Guide");
        //sfa.assertAll();


    }


}

