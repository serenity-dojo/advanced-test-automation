package net.serenitybdd.exercises.screenplay.planjourney.travelpreferences;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

class TravelPreferenceOptions {
    static Target EDIT_PREFERENCES = Target.the("Edit Preferences link").located(By.linkText("Edit preferences"));
    static Target HIDE_PREFERENCES = Target.the("Hide Preferences link").located(By.linkText("Hide preferences"));
    static Target DESELECT_ALL = Target.the("Deselect all options").located(By.linkText("deselect all"));
    static Target PLAN_MY_JOURNEY_BUTTON = Target.the("Plan my journey button")
                                                 .locatedBy("css:#more-journey-options .plan-journey-button");

    static Target MAXIMUM_WALKING_TIME = Target.the("Maximum walking time").located(By.name("MaxWalkingMinutes"));

    static By travelOptionFor(String travelOption) {
        return By.xpath(String.format("//label[.='%s']", travelOption.trim()));
    }
}
