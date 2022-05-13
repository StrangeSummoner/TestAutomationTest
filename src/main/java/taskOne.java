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

public class taskOne {

    SoftAssert softAssert = new SoftAssert();

    @Test
    public void test(){
        open("https://demoqa.com/books"); //navigate to URL
        ElementsCollection rows = $$(By.className("rt-tr-group")); //books are represented as a collection of rows that share same className

        for (int i = 0; i < rows.size()-2; ++i) { //iterate size-2, because last two rows are empty
            ElementsCollection colls = rows.get(i).$$(By.className("rt-td")); //each row consists of 4 rows: img, book title, author, publisher. All of them share the same className
            for(int j=0; j<colls.size(); ++j){
                if(j==0){ //each coll's index might be 0, 1, 2, 3. If it is first coll, it means that it should be img
                    //get respective collumn and dive into very last, child element, then check the condition attribute src containts specific link. Our of 8 imgs, we only have 4 indexes, that is why the period is 4 and we take reminder of 4.
                    colls.get(j).lastChild().shouldHave(Condition.attribute("src", "https://demoqa.com/images/bookimage" + String.valueOf(i%4) + ".jpg"));
                }
                else {
                    //if j!=0 it means that coll should be text(book name, author, or publisher) getText method is used to get pure text, and then it should be trimmed.Because multiple whitespaces can bypass this check
                    softAssert.assertNotEquals("", colls.get(j).getText().trim());
                }
            }
        }

        softAssert.assertAll(); //softAssert is used instead of hard one because it allows us to go on even though test is failed
    }
}