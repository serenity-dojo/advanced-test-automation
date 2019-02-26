package net.serenitybdd.exercises.lean.planjourney;

import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.core.steps.UIInteractionSteps;

import java.util.List;
import java.util.stream.Collectors;

import static net.serenitybdd.exercises.lean.planjourney.JourneyResultList.STOP_LOCATION;

public class JourneyResults extends UIInteractionSteps {
    public List<String> stops() {
        return findAll(STOP_LOCATION).stream()
                .map(WebElementFacade::getTextContent)
                .collect(Collectors.toList());
    }
}
