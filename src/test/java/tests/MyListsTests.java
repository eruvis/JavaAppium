package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.*;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

public class MyListsTests extends CoreTestCase {
    private static final String login = "jafino6682";
    private static final String password = "jafino6682@bubblybank.com";

    @Test
    public void testSaveFirstArticleToMyList() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.waitForTitleElement();

        String articleTitle = ArticlePageObject.getArticleTitle();
        String nameOfFolder = "Learning programming";

        if (Platform.getInstance().isAndroid()) {
            ArticlePageObject.addArticleToMyNewList(nameOfFolder);
        } else {
            ArticlePageObject.addArticlesToMySaved();
        }
        if (Platform.getInstance().isMW()) {
            AuthorizationPageObject auth = new AuthorizationPageObject(driver);
            auth.clickAuthButton();
            auth.enterLoginData(login, password);
            auth.submitForm();

            ArticlePageObject.waitForTitleElement();

            assertEquals(
                    "We are not on the same page after login.",
                    articleTitle,
                    ArticlePageObject.getArticleTitle()
            );

            //ArticlePageObject.addArticlesToMySaved();
        }

        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        NavigationUI.openNavigation();
        NavigationUI.clickMyLists();

        MyListsPageObject MyListPageObject = MyListsPageObjectFactory.get(driver);

        if (Platform.getInstance().isAndroid()) {
            MyListPageObject.openFolderByName(nameOfFolder);
        }

        MyListPageObject.swipeByArticleToDelete(articleTitle);
    }

    @Test
    public void testSaveTwoArticlesToMyList() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        String firstSearchRequest = "Selenium";
        SearchPageObject.typeSearchLine(firstSearchRequest);
        String firstArticleTitle = "Selenium (software)";
        String firstArticleSubtitle = "Testing framework for web applications";
        SearchPageObject.clickByArticleWithSubstring(firstArticleSubtitle);

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.waitForTitleElement();

        String nameOfFolder = "List for ex.5";

        if (Platform.getInstance().isAndroid()) {
            ArticlePageObject.addArticleToMyNewList(nameOfFolder);
        } else {
            ArticlePageObject.addArticlesToMySaved();
        }

        if (Platform.getInstance().isMW()) {
            AuthorizationPageObject auth = new AuthorizationPageObject(driver);
            auth.clickAuthButton();
            auth.enterLoginData(login, password);
            auth.submitForm();

            ArticlePageObject.waitForTitleElement();

            assertEquals(
                    "We are not on the same page after login.",
                    firstArticleTitle,
                    ArticlePageObject.getArticleTitle()
            );
        }

        ArticlePageObject.closeArticle();

        SearchPageObject.initSearchInput();
        String secondSearchRequest = "Java";
        SearchPageObject.typeSearchLine(secondSearchRequest);
        String secondArticleTitle = "Java (programming language)";
        String secondArticleSubtitle = "Object-oriented programming language";
        SearchPageObject.clickByArticleWithSubstring(secondArticleSubtitle);

        ArticlePageObject.waitForTitleElement();

        if (Platform.getInstance().isAndroid()) {
            ArticlePageObject.addArticleToMyExistingList(nameOfFolder);
        } else {
            ArticlePageObject.addArticlesToMySaved();
        }
        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        NavigationUI.openNavigation();
        NavigationUI.clickMyLists();

        MyListsPageObject MyListPageObject = MyListsPageObjectFactory.get(driver);

        if (Platform.getInstance().isAndroid()) {
            MyListPageObject.openFolderByName(nameOfFolder);
        }

        MyListPageObject.swipeByArticleToDelete(firstArticleTitle);
        MyListPageObject.openArticleByName(secondArticleTitle);

        String articleSubtitle = ArticlePageObject.getArticleSubtitle();

        if (Platform.getInstance().isMW()) {
            assertTrue(articleSubtitle.contains(secondArticleSubtitle.toLowerCase()));
        } else {
            assertEquals(
                    "We see unexpected subtitle!",
                    secondArticleSubtitle,
                    articleSubtitle
            );
        }
    }
}
