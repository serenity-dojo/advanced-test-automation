package net.serenitybdd.exercises.lean.statusupdates;

import net.serenitybdd.core.steps.UIInteractionSteps;
import net.thucydides.core.annotations.Step;

public class ConsultStatus extends UIInteractionSteps {
    @Step("Consult status for this weekend")
    public void forThisWeekEnd() {
        $(StatusUpdatesHeader.THIS_WEEKEND).click();
    }
}
