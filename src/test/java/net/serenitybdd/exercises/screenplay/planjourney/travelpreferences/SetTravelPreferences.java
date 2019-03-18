package net.serenitybdd.exercises.screenplay.planjourney.travelpreferences;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;

import java.util.List;

import static net.serenitybdd.exercises.screenplay.planjourney.travelpreferences.TravelPreferenceOptions.*;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class SetTravelPreferences implements Performable {

    public static Performable toTravelBy(List<String> travelModes) {
        return new SetTravelPreferences(travelModes);
    }

    private List<String> travelModes;

    private SetTravelPreferences(List<String> travelModes) {
        this.travelModes = travelModes;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Click.on(EDIT_PREFERENCES),
                WaitUntil.the(PLAN_MY_JOURNEY_BUTTON, isVisible()),
                Click.on(DESELECT_ALL),

                SelectEachTravelMode.in(travelModes),

                Click.on(HIDE_PREFERENCES)
        );
    }
}
