package net.serenitybdd.exercises.screenplay.navigation;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Open;

public class Navigate {
    public static Performable toSection(String sectionName) {
        return Task.where("{0} navigates to the '#sectionName' section",
                Open.browserOn().the(TFLHomePage.class),
                Click.on(MenuBar.MENU_TAB.of(sectionName))
        ).with("sectionName").of(sectionName);

    }
}
