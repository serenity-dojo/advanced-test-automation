package net.serenitybdd.exercises.screenplay.lines;

import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.TextContent;

import java.util.List;
import java.util.stream.Collectors;

public class TheLines {
    public static Question<List<String>> displayed() {
        return actor -> TextContent.of(ServiceLinePanel.SERVICE_NAME)
                .viewedBy(actor)
                .asList()
                .stream()
                .map(String::trim)
                .collect(Collectors.toList());
    }
}
