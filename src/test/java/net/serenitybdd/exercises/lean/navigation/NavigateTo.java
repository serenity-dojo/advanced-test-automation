package net.serenitybdd.exercises.lean.navigation;

import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.core.steps.UIInteractionSteps;
import net.thucydides.core.annotations.Step;
import org.openqa.selenium.By;

public class NavigateTo extends UIInteractionSteps {

    TFLHomePage tflHomePage;

    @Step("Navigate to TFL home page")
    public void toTFLHomePage() {
        tflHomePage.open();
        findAll(TFLHomePage.COOKIE_BUTTON).stream().findFirst().ifPresent(
                WebElementFacade::click
        );
    }

    @Step("Navigate to the '{0}' section")
    public void toSection(String sectionName) {
        tflHomePage.open();
        find(MenuBar.MENU_BAR)
                .then(By.linkText(sectionName))
                .then().click();
    }
}
