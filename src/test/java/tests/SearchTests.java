package tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.Platform;
import lib.ui.SearchPageObject;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;

@Epic("Test for search")
public class SearchTests extends CoreTestCase {
    @Test
    @Features(value = {@Feature(value = "Search")})
    @DisplayName("Search")
    @Description("We are looking for articles by search query")
    @Step("Starting test testSearch")
    @Severity(value = SeverityLevel.CRITICAL)
    public void testSearch() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForSearchResult("Object-oriented programming language");
    }

    @Test
    @Features(value = {@Feature(value = "Search")})
    @DisplayName("Waiting for the search result and cancel it")
    @Description("Checking if the search is canceled when the cancel button is clicked")
    @Step("Starting test testCancelSearching")
    @Severity(value = SeverityLevel.CRITICAL)
    public void testCancelSearching() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        String searchLine = "computer";
        SearchPageObject.typeSearchLine(searchLine);
        SearchPageObject.waitForSearchResult("Computer");
        SearchPageObject.waitForSearchResult("Computer program");
        SearchPageObject.clickCancelSearch();
        SearchPageObject.assertTherePageOfSearchIsClose();
    }

    @Test
    @Features(value = {@Feature(value = "Search")})
    @DisplayName("Cancel search")
    @Description("Checking if the cancel search button disappears after clicking it")
    @Step("Starting test testCancelSearch")
    @Severity(value = SeverityLevel.CRITICAL)
    public void testCancelSearch() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForCancelButtonToAppear();
        SearchPageObject.clickCancelSearch();
        if (!Platform.getInstance().isMW()) {
            SearchPageObject.clickCancelSearch();
        }
        SearchPageObject.waitForCancelButtonToDisappear();
    }

    @Test
    @Features(value = {@Feature(value = "Search")})
    @DisplayName("Amount of not empty search")
    @Description("We check the number of requests that are not empty, and then we check that they are not 0")
    @Step("Starting test testAmountOfNotEmptySearch")
    @Severity(value = SeverityLevel.CRITICAL)
    public void testAmountOfNotEmptySearch() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        String searchLine = "Linkin Park Diskography";
        SearchPageObject.typeSearchLine(searchLine);

        int amountOfSearchResults = SearchPageObject.getAmountOfFoundArticles();

        Assert.assertTrue(
                "We found top few results!",
                amountOfSearchResults > 0
        );
    }

    @Test
    @Features(value = {@Feature(value = "Search")})
    @DisplayName("Amount of empty search")
    @Description("We check that there are no search queries")
    @Step("Starting test testAmountOfEmptySearch")
    @Severity(value = SeverityLevel.CRITICAL)
    public void testAmountOfEmptySearch() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        String searchLine = "Lefwfwfewfwef";
        SearchPageObject.typeSearchLine(searchLine);
        SearchPageObject.waitForEmptyResultsLabel();
        SearchPageObject.assertThereIsNoResultOfSearch();
    }

    @Test
    @Features(value = {@Feature(value = "Search")})
    @DisplayName("Check search element")
    @Description("We check the title and description of the articles in search")
    @Step("Starting test testSearchElementByTitleAndDescription")
    @Severity(value = SeverityLevel.CRITICAL)
    public void testSearchElementByTitleAndDescription() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        String searchLine = "Valve";
        SearchPageObject.typeSearchLine(searchLine);

        String firstTitle = "Valve",
                firstDescription = "Flow control device";
        String secondTitle = "Valve Corporation",
                secondDescription = "American video game company";
        String thirdTitle = "Valve amplifier",
                thirdDescription = "Type of electronic amplifier";

        SearchPageObject.waitForElementByTitleAndDescription(firstTitle, firstDescription);
        SearchPageObject.waitForElementByTitleAndDescription(secondTitle, secondDescription);
        SearchPageObject.waitForElementByTitleAndDescription(thirdTitle, thirdDescription);
    }
}
