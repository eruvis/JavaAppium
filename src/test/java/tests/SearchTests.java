package tests;

import lib.CoreTestCase;
import lib.ui.SearchPageObject;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

public class SearchTests extends CoreTestCase {
    @Test
    public void testSearch() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForSearchResult("Object-oriented programming language");
    }

    @Test
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
    public void testCancelSearch() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForCancelButtonToAppear();
        SearchPageObject.clickCancelSearch();
        SearchPageObject.clickCancelSearch();
        SearchPageObject.waitForCancelButtonToDisappear();
    }

    @Test
    public void testAmountOfNotEmptySearch() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        String searchLine = "Linkin Park Diskography";
        SearchPageObject.typeSearchLine(searchLine);

        int amountOfSearchResults = SearchPageObject.getAmountOfFoundArticles();

        assertTrue(
                "We found top few results!",
                amountOfSearchResults > 0
        );
    }

    @Test
    public void testAmountOfEmptySearch() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        String searchLine = "Lefwfwfewfwef";
        SearchPageObject.typeSearchLine(searchLine);
        SearchPageObject.waitForEmptyResultsLabel();
        SearchPageObject.assertThereIsNoResultOfSearch();
    }

    @Test
    public void testSearchElementByTitleAndDescription() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        String searchLine = "Kotlin";
        SearchPageObject.typeSearchLine(searchLine);

        String firstTitle = "Kotlin",
                firstDescription = "Wikimedia disambiguation page";
        String secondTitle = "Kotlin (programming language)",
                secondDescription = "Programming language";
        String thirdTitle = "Kotlin-class destroyer",
                thirdDescription = "Class of Soviet cold-war destroyers";

        SearchPageObject.waitForElementByTitleAndDescription(firstTitle, firstDescription);
        SearchPageObject.waitForElementByTitleAndDescription(secondTitle, secondDescription);
        SearchPageObject.waitForElementByTitleAndDescription(thirdTitle, thirdDescription);
    }
}
