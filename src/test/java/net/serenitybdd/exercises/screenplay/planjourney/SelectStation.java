package net.serenitybdd.exercises.screenplay.planjourney;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.SendKeys;
import net.serenitybdd.screenplay.targets.Target;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class SelectStation {
    public static Performable from(String departure) {
        return selectStation(PlanAJourneyForm.FROM, departure);
    }

    public static Performable to(String destination) {
        return selectStation(PlanAJourneyForm.TO, destination);
    }

    private static Performable selectStation(Target stationField, String stationName) {
        return Task.where(
                "{0} selects station #stationField",
                Click.on(stationField),
                SendKeys.of(stationName).into(stationField),
                WaitUntil.the(PlanAJourneyForm.SUGGESTED_STOPS, isVisible()),
                Click.on(PlanAJourneyForm.SUGGESTED_STOPS)
        ).with("stationField").of(stationField);
    }
}
