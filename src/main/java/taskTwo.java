import com.codeborne.selenide.ClickOptions;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.ex.SoftAssertionError;
import com.codeborne.selenide.selector.ByText;
import org.eclipse.sisu.inject.Soft;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;

import java.util.ArrayList;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import org.testng.asserts.SoftAssert;

public class taskTwo {

    SoftAssert softAssert = new SoftAssert();

    @Test
    public void testt() throws InterruptedException {
        open("https://demoqa.com/books"); //navigate to URL
        ElementsCollection rows = $$(By.className("rt-tr-group")); //books are represented as a collection of rows that share same className
        ArrayList<String> specificTitles = new ArrayList<String>(0); //this arrayList is used to store book names that contain "JavaScript" and are published by O'Reilly Media


        for (int i = 0; i < rows.size(); ++i) {
            ElementsCollection colls = rows.get(i).$$(By.className("rt-td"));
            for(int j=0; j<colls.size(); ++j){
                if(colls.get(j).getText().contains("JavaScript") && colls.get(j+2).getText().contains("O'Reilly Media")) { //if coll has "JavaScript" in it, another condition is checked immediately. If both conditions matched,title is stored into predefined arrayList
                    specificTitles.add(colls.get(j).getText());
                }
            }
        }
        softAssert.assertEquals(10, specificTitles.size()); //the size of the array of specific books is compared to 10
        softAssert.assertEquals("Learning JavaScript Design Patterns", specificTitles.get(0)); //first element in specific array is compared to given string


        for(int i=0; i<specificTitles.size(); ++i){ //iterate through specific-book array
            String str = specificTitles.get(i); //get each of them one by one
            $(byText(str)).scrollTo().click(); //click on title by book name itself. Ads might overlap some titles, that is why scroll method is used
            Thread.sleep(3000); //for visual aid, small delay is used
            back(); //navigating back is mandatory to click on all the books
        }

        softAssert.assertAll();


    }
}