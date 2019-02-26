package net.serenitybdd.exercises.lean.planjourney;

import org.openqa.selenium.By;

class PlanAJourneyForm {
    static By FROM = By.id("InputFrom");
    static By TO = By.id("InputTo");
    static By SUGGESTED_STOPS = By.cssSelector(".stop-name");
    static By PLAN_MY_JOURNEY = By.cssSelector("#plan-a-journey .plan-journey-button");

    static final By CHANGE_TIME = By.linkText("change time");
    static final By SELECT_DATE = By.id("Date");
}
