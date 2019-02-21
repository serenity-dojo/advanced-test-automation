package net.serenitybdd.exercises.lean.lines;

import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.core.steps.UIInteractionSteps;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class ServiceInfo extends UIInteractionSteps {
    public List<String> lines() {

        return findAll(ServiceLinePanel.SERVICE_NAME)
                .stream()
                .map(WebElementFacade::getTextContent)
                .map(String::trim)
                .collect(toList());
    }
}
