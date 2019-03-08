package net.serenitybdd.exercises.lean.planjourney;

import net.serenitybdd.core.steps.UIInteractionSteps;
import net.thucydides.core.annotations.Step;

import java.util.List;

import static net.serenitybdd.exercises.lean.planjourney.TravelPreferenceOptions.*;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class SetTravelPreferences extends UIInteractionSteps {

    @Step("Set travel preferences to {0}")
    public void toTravelBy(List<String> travelModes) {
        moveTo(EDIT_PREFERENCES);
        $(EDIT_PREFERENCES).click();
        waitFor(visibilityOfElementLocated(PLAN_MY_JOURNEY_BUTTON));
        $(DESELECT_ALL).click();
        travelModes.forEach(
                option -> $(travelOptionFor(option)).click()
        );
        $(HIDE_PREFERENCES).click();
    }
}
