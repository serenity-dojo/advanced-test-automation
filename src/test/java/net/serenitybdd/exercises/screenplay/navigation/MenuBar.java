package net.serenitybdd.exercises.screenplay.navigation;

import net.serenitybdd.screenplay.targets.Target;

class MenuBar {
    static Target MENU_TAB = Target.the("Menu tab {0}")
                                   .locatedBy("//div[@class='top-row']//li[.='{0}']");
}
