package net.serenitybdd.exercises.screenplay.statusupdates;

import com.google.common.collect.ImmutableMap;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.targets.Target;

import java.util.Map;

import static net.serenitybdd.exercises.screenplay.statusupdates.StatusUpdatesHeader.*;
import static net.serenitybdd.exercises.screenplay.statusupdates.StatusUpdatesTimeframe.*;

public class SelectedStatus {

    private static final Map<Target, StatusUpdatesTimeframe> SELECTED_STATUS_UPDATE_TIMEFRAMES = ImmutableMap.of(
            THIS_WEEKEND, UPDATES_FOR_THIS_WEEKEND,
            NOW, UPDATES_FOR_NOW,
            FUTURE, UPDATES_FOR_A_FUTURE_DATE
    );

    public static Question<StatusUpdatesTimeframe> timeframe() {
        return Question.about("selected status").answeredBy(
                actor -> SELECTED_STATUS_UPDATE_TIMEFRAMES.keySet().stream()
                    .filter( target -> isSelected(target.resolveFor(actor)))
                    .findFirst()
                    .map(SELECTED_STATUS_UPDATE_TIMEFRAMES::get)
                    .orElseThrow(() -> new AssertionError("No timeframe selected"))
        );
    }

    private static boolean isSelected(WebElementFacade element) {
        return element.hasClass("selected");
    }
}
