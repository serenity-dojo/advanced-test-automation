package net.serenitybdd.exercises.lean.planjourney;

import net.serenitybdd.core.steps.UIInteractionSteps;
import net.thucydides.core.annotations.Step;
import org.openqa.selenium.By;

import static net.serenitybdd.exercises.lean.planjourney.JourneyResultList.JOURNEY_RESULTS;
import static net.serenitybdd.exercises.lean.planjourney.PlanAJourneyForm.*;

public class PlanAJourney extends UIInteractionSteps {

    @Step("Plan a journey from {0}")
    public PlanAJourney from(String departure) {
        selectStation(FROM, departure);
        return this;
    }

    @Step("Travelling to {0}")
    public void to(String destination) {
        selectStation(TO, destination);
        $(PLAN_MY_JOURNEY).click();
        waitForRenderedElements(JOURNEY_RESULTS);
    }

    private void selectStation(By stationField, String station) {
        $(stationField).click();
        $(stationField).sendKeys(station);
        $(SUGGESTED_STOPS).click();
    }
}
