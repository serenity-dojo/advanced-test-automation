package net.serenitybdd.exercises.lean.navigation;

import net.serenitybdd.core.pages.PageObject;
import net.thucydides.core.annotations.DefaultUrl;
import org.openqa.selenium.By;

@DefaultUrl("https://tfl.gov.uk")
class TFLHomePage extends PageObject {
    public static final By COOKIE_BUTTON = By.cssSelector(".cookie-policy-button .hide-text");
}
