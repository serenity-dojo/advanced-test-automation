package net.serenitybdd.exercises.screenplay.planjourney;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;

import java.util.List;
import java.util.stream.Collectors;

import static net.serenitybdd.exercises.screenplay.planjourney.TravelPreferenceOptions.travelOptionFor;

class SelectEachTravelMode {
    static Performable in(List<String> travelModes) {
        return Task.where("Select travel modes: " + travelModes,
                          clickOnEachTravelModeFrom(travelModes));
    }

    private static Performable[] clickOnEachTravelModeFrom(List<String> travelModes) {
        return travelModes.stream()
                .map( travelMode -> Click.on(travelOptionFor(travelMode)))
                .collect(Collectors.toList())
                .toArray(new Performable[]{});
    }
}
