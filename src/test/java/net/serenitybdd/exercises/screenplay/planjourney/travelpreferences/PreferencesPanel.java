package net.serenitybdd.exercises.screenplay.planjourney.travelpreferences;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Scroll;
import net.serenitybdd.screenplay.actions.ScrollTo;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.exercises.screenplay.planjourney.travelpreferences.TravelPreferenceOptions.*;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class PreferencesPanel {
    public static Performable openThePreferencesPanel() {
        return Task.where("{0} opens the preference panel",
                Scroll.to(EDIT_PREFERENCES),
                Click.on(EDIT_PREFERENCES),
                WaitUntil.the(PLAN_MY_JOURNEY_BUTTON, isVisible())
        );
    }

    public static Performable closeThePreferencesPanel() {
        return Task.where("{0} closes the preference panel", Click.on(HIDE_PREFERENCES));
    }

}
