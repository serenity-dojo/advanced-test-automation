package net.serenitybdd.exercises.lean.planjourney;

import org.openqa.selenium.By;

class TravelPreferenceOptions {
    static By EDIT_PREFERENCES = By.linkText("Edit preferences");
    static By HIDE_PREFERENCES = By.linkText("Hide preferences");
    static By DESELECT_ALL = By.linkText("deselect all");
    static By PLAN_MY_JOURNEY_BUTTON = By.cssSelector("#more-journey-options .plan-journey-button");

    static By travelOptionFor(String travelOption) {
        return By.xpath(String.format("//label[.='%s']", travelOption));
    }
}
