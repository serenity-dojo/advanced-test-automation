package net.serenitybdd.exercises.screenplay.planjourney.travelpreferences;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;

public class SetRoutePreference implements PreferenceSetter {
    @Override
    public Performable to(String preferredRoute) {
        return Task.where("{0} prefers to be shown " + preferredRoute,
                Click.on(TravelPreferenceOptions.travelOptionFor(preferredRoute)));
    }
}
