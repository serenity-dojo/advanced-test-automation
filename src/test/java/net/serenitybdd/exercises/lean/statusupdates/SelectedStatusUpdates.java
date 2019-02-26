package net.serenitybdd.exercises.lean.statusupdates;

import net.serenitybdd.core.steps.UIInteractionSteps;
import org.openqa.selenium.By;

import static net.serenitybdd.exercises.lean.statusupdates.StatusUpdatesHeader.*;
import static net.serenitybdd.exercises.lean.statusupdates.StatusUpdatesTimeframe.*;

public class SelectedStatusUpdates extends UIInteractionSteps {

    public StatusUpdatesTimeframe timeframe() {
        if (isSelected(THIS_WEEKEND)) {
            return UPDATES_FOR_THIS_WEEKEND;
        } else if (isSelected(NOW)) {
            return UPDATES_FOR_NOW;
        } else if (isSelected(FUTURE)) {
            return UPDATES_FOR_A_FUTURE_DATE;
        }
        throw new AssertionError("No timeframe selected");
    }

    private boolean isSelected(By option) {
        return $(option).hasClass("selected");
    }
}
