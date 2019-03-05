package net.serenitybdd.exercises.screenplay.planjourney;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;

import java.util.List;
import java.util.stream.Collectors;

import static net.serenitybdd.exercises.screenplay.planjourney.TravelPreferenceOptions.*;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class SetTravelPreferences implements Performable {

    private List<String> travelModes;

    public SetTravelPreferences(List<String> travelModes) {
        this.travelModes = travelModes;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Click.on(EDIT_PREFERENCES),
                WaitUntil.the(PLAN_MY_JOURNEY_BUTTON, isVisible()),
                Click.on(DESELECT_ALL),

                selectEachTravelModeIn(travelModes),

                Click.on(HIDE_PREFERENCES)
        );
    }

    private Performable selectEachTravelModeIn(List<String> travelModes) {
        List<Performable> selectTravelModes = travelModes.stream()
                .map( travelMode -> Click.on(travelOptionFor(travelMode)))
                .collect(Collectors.toList());

        return Task.where("Select travel modes: " + travelModes,
                selectTravelModes.toArray(new Performable[]{}));
    }

    public static Performable toTravelBy(List<String> travelModes) {
        return new SetTravelPreferences(travelModes);
    }

}
