import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import dataProvider.DataProviderCustom;
import ge.tbcitacademy.data.Constants;
import org.openqa.selenium.By;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import static com.codeborne.selenide.Selenide.*;

public class AlternativeParametrizations extends ConfigTest{

    @Parameters({"firstname", "lastname", "gender", "mobile"})
    @Test(dataProvider="studentsForm", dataProviderClass = DataProviderCustom.class)
    public void formFill(String firstname, String lastname, String gender, String mobile ){

        //მონაცემების შევსება
        open(Constants.PRACING_FORM);
        $x(Constants.INTO_VIEW).scrollTo();
        $(By.id("firstName")).sendKeys(firstname);
        $(By.id("lastName")).sendKeys(lastname);
        if(gender.equalsIgnoreCase("Male")){  //id-ით არ კლიკავდა ¯\_(ツ)_/¯
            $(Constants.MALE_BUTTON_XPATH).click();
        }else if(gender.equalsIgnoreCase("female")){
            $(Constants.FEMALE_BUTTON_XPATH).click();
        }else{
            $(Constants.OTHER_BUTTON_XPATH).click();
        }
        $(By.id("userNumber")).setValue(mobile);

        //დაასაბმითება
        SelenideElement submit = $(By.id("submit"));
        submit.scrollTo();
        submit.click();

        //სახელის და გვარის დინამიურად ვალიდაცია
        SelenideElement studentName = $x(Constants.STUDENTNAME_FORM)
                .sibling(0)
                .shouldHave(Condition.text(firstname + " " + lastname));

    }



}
