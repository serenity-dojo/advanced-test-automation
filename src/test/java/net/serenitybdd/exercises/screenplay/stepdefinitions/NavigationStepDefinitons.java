package net.serenitybdd.exercises.screenplay.stepdefinitions;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import net.serenitybdd.exercises.screenplay.navigation.Navigate;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;

import static net.serenitybdd.screenplay.actors.OnStage.theActorCalled;

public class NavigationStepDefinitons {

    @Before
    public void prepareActors() {
        OnStage.setTheStage(new OnlineCast());
    }

    @Given("{word} is in the {string} section")
    public void trevorIsInTheSection(String actor, String sectionName) {
        theActorCalled(actor).attemptsTo(
            Navigate.toSection(sectionName)
        );
    }
}
