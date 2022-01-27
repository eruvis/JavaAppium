package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import lib.Platform;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class ArticlePageObject extends MainPageObject {
    protected static String
            TITLE,
            SUBTITLE,
            FOOTER_ELEMENT,
            OPTIONS_BUTTON,
            OPTIONS_LAST_ELEMENT,
            OPTIONS_ADD_TO_MY_lIST_BUTTON,
            OPTIONS_REMOVE_FROM_MY_LIST_BUTTON,
            ADD_TO_MY_LIST_OVERLAY,
            MY_LIST_NAME_INPUT,
            MY_LIST_NAME_TPL,
            MY_LIST_OK_BUTTON,
            CLOSE_ARTICLE_BUTTON;

    public ArticlePageObject(RemoteWebDriver driver) {
        super(driver);
    }

    /* TEMPlATES METHODS */
    public static String getFolderXpathByName(String nameOfFolder) {
        return MY_LIST_NAME_TPL.replace("{FOLDER_NAME}", nameOfFolder);
    }
    /* TEMPlATES METHODS */

    public WebElement waitForTitleElement() {
        return this.waitForElementPresent(TITLE, "Cannot find article title on page!", 15);
    }

    public String getArticleTitle() {
        WebElement titleElement = waitForTitleElement();
        if (Platform.getInstance().isAndroid()) {
            return titleElement.getAttribute("text");
        } else if (Platform.getInstance().isIOS()) {
            return titleElement.getAttribute("name");
        } else {
            return titleElement.getText();
        }
    }

    public WebElement waitForSubtitleElement() {
        return this.waitForElementPresent(SUBTITLE, "Cannot find subtitle title on page!", 15);
    }

    public String getArticleSubtitle() {
        WebElement descriptionElement = waitForSubtitleElement();
        if (Platform.getInstance().isAndroid()) {
            return descriptionElement.getAttribute("text");
        } else {
            return descriptionElement.getAttribute("name");
        }
    }

    public void swipeToFooter() {
        if (Platform.getInstance().isAndroid()) {
            this.swipeUpToFindElement(FOOTER_ELEMENT, "Cannot find the end of the article", 40);
        } else if (Platform.getInstance().isIOS()) {
            this.swipeUpTitleElementAppear(FOOTER_ELEMENT, "Cannot find the end of the article", 40);
        } else {
            this.scrollWebPageTitleElementNotVisible(FOOTER_ELEMENT, "Cannot find the end of the article", 40);
        }
    }

    public void addArticleToMyNewList(String nameOfFolder) {
        this.waitForElementAndClick(
                OPTIONS_BUTTON,
                "Cannot find button to open article options",
                5
        );

        /* Explicit Wait */
        this.waitForElementPresent(
                OPTIONS_LAST_ELEMENT,
                "Cannot find 'Font and theme'",
                5
        );
        /* Explicit Wait */

        this.waitForElementAndClick(
                OPTIONS_ADD_TO_MY_lIST_BUTTON,
                "Cannot find options to add article to reading list",
                10
        );

        this.waitForElementAndClick(
                ADD_TO_MY_LIST_OVERLAY,
                "Cannot find 'Go it' top overlay",
                5
        );

        this.waitForElementAndClear(
                MY_LIST_NAME_INPUT,
                "Cannot find input to set name of articles folder",
                5
        );

        this.waitForElementAndSendKeys(
                MY_LIST_NAME_INPUT,
                nameOfFolder,
                "Cannot put text into articles folder input",
                5
        );

        this.waitForElementAndClick(
                MY_LIST_OK_BUTTON,
                "Cannot press 'OK' button",
                5
        );
    }

    public void addArticleToMyExistingList(String nameOfFolder) {
        this.waitForElementAndClick(
                OPTIONS_BUTTON,
                "Cannot find button to open article options",
                5
        );

        /* Explicit Wait */
        this.waitForElementPresent(
                OPTIONS_LAST_ELEMENT,
                "Cannot find 'Font and theme'",
                5
        );
        /* Explicit Wait */

        this.waitForElementAndClick(
                OPTIONS_ADD_TO_MY_lIST_BUTTON,
                "Cannot find options to add article to reading list",
                10
        );

        String folderNameXpath = getFolderXpathByName(nameOfFolder);

        this.waitForElementAndClick(
                folderNameXpath,
                "Cannot find folder: " + nameOfFolder,
                5
        );
    }

    public void closeArticle() {
        if (Platform.getInstance().isMW()) {
            System.out.println("Method closeArticle() do nothing for platform " +Platform.getInstance().getPlatformVar());
            return;
        }

        this.waitForElementAndClick(
                CLOSE_ARTICLE_BUTTON,
                "Cannot close article. Cannot find 'X' link",
                5
        );
    }

    public void assertTitleElementPresent() {
        this.assertElementPresent(TITLE, "Cannot find title");
    }

    public void addArticlesToMySaved() {
        /*if (Platform.getInstance().isMW()) {
            removeArticleFromSavedIfItAdded();
        }*/
        this.waitForElementAndClick(OPTIONS_ADD_TO_MY_lIST_BUTTON, "Cannot find options to add article to my list", 5);
    }

    public void removeArticleFromSavedIfItAdded() {
        if (isElementPresent(OPTIONS_REMOVE_FROM_MY_LIST_BUTTON)) {
            this.waitForElementAndClick(
                    OPTIONS_REMOVE_FROM_MY_LIST_BUTTON,
                    "Cannot click button to remove and article from saved",
                    1
            );
            this.waitForElementPresent(
                    OPTIONS_ADD_TO_MY_lIST_BUTTON,
                    "Cannot find button to add an article to saved list after removing it from this list before"
            );
        }
    }
}
