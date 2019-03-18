package net.serenitybdd.exercises.screenplay.planjourney.travelpreferences;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;

import java.util.Arrays;
import java.util.List;

import static net.serenitybdd.exercises.screenplay.planjourney.travelpreferences.TravelPreferenceOptions.DESELECT_ALL;

public class TravelByPreference implements PreferenceSetter {
    @Override
    public Performable to(String travelPreferences) {
        List<String> travelPreferenceOptions = Arrays.asList(travelPreferences.split(","));
        return Task.where("{0} selects travel preferences: " + travelPreferences,
                Click.on(DESELECT_ALL),
                SelectEachTravelMode.in(travelPreferenceOptions)
        );
    }
}
