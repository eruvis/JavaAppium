package lib.ui;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class MyListsPageObject extends MainPageObject {
    protected static String
            FOLDER_BY_NAME_TPL,
            ARTICLE_BY_TITLE_TPL,
            REMOVE_FROM_SAVED_BUTTON;

    public MyListsPageObject(RemoteWebDriver driver) {
        super(driver);
    }

    /* TEMPlATES METHODS */
    public static String getFolderXpathByName(String nameOfFolder) {
        return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}", nameOfFolder);
    }

    public static String getSavedArticleXpathByTitle(String articleTitle) {
        return ARTICLE_BY_TITLE_TPL.replace("{TITLE}", articleTitle);
    }

    public static String getRemoveButtonByTittle(String articleTitle) {
        return REMOVE_FROM_SAVED_BUTTON.replace("{TITLE}", articleTitle);
    }
    /* TEMPlATES METHODS */

    public void openFolderByName(String nameOfFolder) {
        String folderNameXpath = getFolderXpathByName(nameOfFolder);

        this.waitForElementAndClick(
                folderNameXpath,
                "Cannot find folder by name " + nameOfFolder,
                5
        );
    }

    public void openArticleByName(String articleTitle) {
        String articleXpath = getSavedArticleXpathByTitle(articleTitle);

        this.waitForElementAndClick(articleXpath, "Cannot find saved article by title " + articleTitle, 5);
    }

    public void waitForArticleToAppearByTitle(String articleTitle) {
        String articleXpath = getSavedArticleXpathByTitle(articleTitle);

        this.waitForElementPresent(articleXpath, "Cannot find saved article by title " + articleTitle, 15);
    }

    public void waitForArticleToDisappearByTitle(String articleTitle) {
        String articleXpath = getSavedArticleXpathByTitle(articleTitle);

        this.waitForElementNotPresent(articleXpath, "Saved article still present with title " + articleTitle, 15);
    }

    public void swipeByArticleToDelete(String articleTitle) {
        String articleXpath = getSavedArticleXpathByTitle(articleTitle);

        this.waitForArticleToAppearByTitle(articleTitle);

        if (Platform.getInstance().isMW()) {
            String removeLocator = getRemoveButtonByTittle(articleTitle);
            System.out.println(removeLocator);
            this.waitForElementAndClick(
                    removeLocator,
                    "Cannot click button to remove article from saved.",
                    10
            );
        } else {
            this.swipeElementToLeft(articleXpath, "Cannot find send article");

            if (Platform.getInstance().isIOS()) {
                this.clickElementToTheRightUpperCorner(articleXpath, "Cannot find save article");
            }
        }

        if (Platform.getInstance().isMW()) {
            driver.navigate().refresh();
        }

        this.waitForArticleToDisappearByTitle(articleTitle);
    }
}
