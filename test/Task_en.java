
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;
import pageObjects.*;
import utils.Config;
import webDriver.WebDriverFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ivankhaliavka on 11/23/16.
 */
public class Task_en extends BaseTest {

    @Factory(dataProviderClass = WebDriverFactory.class, dataProvider = "driverProvider")
    public Task_en(String browserType) {
        this.browserType = browserType;
    }

    @BeforeClass
    public void before() {
        page = new Page(webDriver);
        homePage = new HomePage(webDriver);
        headerPage = new HeaderPage(webDriver);
        searchPage = new SearchPage(webDriver);
        myMemoPage = new MyMemoPage(webDriver);
    }

    @BeforeMethod
    public void beforeMet() {
        page.openPage(Config.getProjectUrl());
    }

    @Test
    public void test_add_items_to_my_memo() {
        List<String[]> myItems = new ArrayList<>();

        headerPage.changeLanguage("RU");
        homePage.goToSection("Электротехника");
        headerPage.clickInHeaderMenu("Поиск");
        resultPage = searchPage.search("Computer", "Computershop_lv");
        resultPage.clickSortBy("Цена", true);
        resultPage.selectTypeDeal("Продажа");
        searchPage.clickSearch("Расширенный поиск");
        searchPage.typeMinPrice("160");
        searchPage.typeMaxPrice("300");
        searchPage.clickSearchButton();
        if (resultPage.getResultList().size() == 0) {
            log.info("Could not find any result.");
            return;
        }
        myItems.add(resultPage.chooseItem(2));
        myItems.add(resultPage.chooseItem(4));
        myItems.add(resultPage.chooseItem(5));

        resultPage.clickAddToMemo("Добавить выбранные в закладки");

        headerPage.clickInHeaderMenu("Закладки");
        List<String[]> memoItems = myMemoPage.getItemsData();
        Assert.assertTrue(memoItems.size() == myItems.size(), "Wrong amount of items in my memo!");
        for (int i=0; i < memoItems.size(); i++) {
            Assert.assertEquals(myItems.get(i)[0], memoItems.get(i)[0], "Wrong item id!");

            String[] myItem = myItems.get(i)[1].split("\n");
            String[] memoItem = memoItems.get(i)[1].split("\n");

            Assert.assertTrue(myItem[1].contains(memoItem[0]), "Wrong description of item in memo!");
            Assert.assertTrue(memoItem[1].contains(myItem[3]), "Wrong price of item in memo!");
        }
    }

}
