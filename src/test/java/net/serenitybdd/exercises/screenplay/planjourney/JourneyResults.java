package net.serenitybdd.exercises.screenplay.planjourney;

import net.serenitybdd.core.steps.UIInteractionSteps;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.TextContent;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

import static net.serenitybdd.exercises.screenplay.planjourney.JourneyResultList.STOP_LOCATION;

public class JourneyResults extends UIInteractionSteps {
    public static Question<List<String>> stops() {
        return Question.about("stops").answeredBy(
                actor -> TextContent.of(STOP_LOCATION).viewedBy(actor)
                .asList()
                .stream()
                .map(StringUtils::strip)
                .collect(Collectors.toList())
        );
    }
}
