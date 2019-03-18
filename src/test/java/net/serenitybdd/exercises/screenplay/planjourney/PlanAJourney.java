package net.serenitybdd.exercises.screenplay.planjourney;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;
import net.thucydides.core.annotations.Step;

import static net.serenitybdd.exercises.screenplay.planjourney.JourneyResultList.JOURNEY_RESULTS;
import static net.serenitybdd.exercises.screenplay.planjourney.PlanAJourneyForm.PLAN_MY_JOURNEY;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class PlanAJourney implements Task, ToDestination {

    private String departure;
    private String destination;

    PlanAJourney(String departure, String destination) {
        this.departure = departure;
        this.destination = destination;
    }

    @Step("Plan a journey from #departure to #destination")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                SelectStation.from(departure),
                SelectStation.to(destination),
                Click.on(PLAN_MY_JOURNEY),
                WaitUntil.the(JOURNEY_RESULTS, isVisible()).forNoMoreThan(60).seconds()
        );
    }

    public static ToDestination from(String departure) {
        return new PlanAJourney(departure, null);
    }

    public Performable to(String destination) {
        return new PlanAJourney(departure, destination);
    }
}
