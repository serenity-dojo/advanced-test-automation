package net.serenitybdd.exercises.screenplay.planjourney.travelpreferences;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Scroll;
import net.serenitybdd.screenplay.actions.SelectFromOptions;

public class WalkNoMoreThanPreference implements PreferenceSetter {
    @Override
    public Performable to(String maxWalkingTime) {
        return Task.where("{0} prefers to walk no more than " + maxWalkingTime,
                Scroll.to(TravelPreferenceOptions.MAXIMUM_WALKING_TIME),
                SelectFromOptions.byVisibleText(maxWalkingTime).from(TravelPreferenceOptions.MAXIMUM_WALKING_TIME)
        );
    }
}
