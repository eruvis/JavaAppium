package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.remote.RemoteWebDriver;

public class WelcomePageObject extends MainPageObject {
    private static final String
            STEP_LEARN_MORE_LINK = "id:Learn more about Wikipedia",
            STEP_NEW_WAY_TO_EXPLORE_TEXT = "id:New ways to explore",
            STEP_ADD_OR_EDIT_PREFERRED_LANG_LINK = "id:Add or edit preferred languages",
            STEP_LEARN_MORE_ABOUT_DATA_COLLECTED_LINK = "id:Learn more about data collected",
            NEXT_BUTTON = "id:Next",
            GET_STARTED_BUTTON = "id:Get started",
            SKIP = "id:Skip";

    public WelcomePageObject(RemoteWebDriver driver) {
        super(driver);
    }

    @Step("Waiting for 'learn more' link")
    public void waitForLearnMoreLink() {
        this.waitForElementPresent(STEP_LEARN_MORE_LINK, "Cannot find " + STEP_LEARN_MORE_LINK + " link'", 10);
    }

    @Step("Waiting for 'new way to explore' text")
    public void waitForNewWayToExploreText() {
        this.waitForElementPresent(STEP_NEW_WAY_TO_EXPLORE_TEXT, "Cannot find " + STEP_NEW_WAY_TO_EXPLORE_TEXT + " text", 10);
    }

    @Step("Waiting for 'add/edit preferred lang' text")
    public void waitForAddOrEditPreferredLangText() {
        this.waitForElementPresent(STEP_ADD_OR_EDIT_PREFERRED_LANG_LINK, "Cannot find " + STEP_ADD_OR_EDIT_PREFERRED_LANG_LINK + " link", 10);
    }

    @Step("Waiting for 'learn more about data collected' text")
    public void waitForLearnMoreAboutDataCollectedText() {
        this.waitForElementPresent(STEP_LEARN_MORE_ABOUT_DATA_COLLECTED_LINK, "Cannot find " + STEP_LEARN_MORE_ABOUT_DATA_COLLECTED_LINK + " link", 10);
    }

    @Step("Clicking the next button")
    public void clickNextButton() {
        this.waitForElementAndClick(NEXT_BUTTON, "Cannot find and click " + NEXT_BUTTON + " button", 10);
    }

    @Step("Clicking 'get started' button")
    public void clickGetStartedButton() {
        this.waitForElementAndClick(GET_STARTED_BUTTON, "Cannot find and click " + GET_STARTED_BUTTON + "button", 10);
    }

    @Step("Clicking the skip button")
    public void clickSkip() {
        this.waitForElementAndClick(SKIP, "Cannot find and click skip button", 5);
    }
}
