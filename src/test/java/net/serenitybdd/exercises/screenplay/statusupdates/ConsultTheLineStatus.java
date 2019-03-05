package net.serenitybdd.exercises.screenplay.statusupdates;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;

public class ConsultTheLineStatus {
    public static Performable forThisWeekEnd() {
        return Task.where("Consult status for this weekend",
                Click.on(StatusUpdatesHeader.THIS_WEEKEND)
        );
    }
}
