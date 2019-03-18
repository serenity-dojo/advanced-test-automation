package net.serenitybdd.exercises.screenplay.planjourney.travelpreferences;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Scroll;
import net.serenitybdd.screenplay.actions.ScrollTo;

public class SetAccessOptionsPreference implements PreferenceSetter {
    @Override
    public Performable to(String accessOptionsPreferences) {
        return Task.where("{0} has the following access preferences: " + accessOptionsPreferences,
                Scroll.to(TravelPreferenceOptions.travelOptionFor(accessOptionsPreferences)),
                Click.on(TravelPreferenceOptions.travelOptionFor(accessOptionsPreferences))
        );
    }
}
