package net.serenitybdd.exercises.screenplay.planjourney;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;


class PlanAJourneyForm {
    static Target FROM = Target.the("From field").locatedBy("#InputFrom");
    static Target TO = Target.the("To field").locatedBy("#InputTo");
    static Target SUGGESTED_STOPS = Target.the("Selected Stops").locatedBy(".stop-name");
    static Target PLAN_MY_JOURNEY = Target.the("Plan Journey button").locatedBy("#plan-a-journey .plan-journey-button");
}
