package net.serenitybdd.exercises.screenplay.stepdefinitions;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.serenitybdd.exercises.screenplay.lines.TheLines;

import java.util.List;

import static net.serenitybdd.assertions.CollectionMatchers.containsAll;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;

public class ViewServiceStatusStepDefinitons {

    @When("he consults the line statuses")
    public void heConsultsTheLineStatuses() {
    }

    @Then("^he should see the following lines:")
    public void he_should_see_the_following_lines(List<String> expectedServices) {
        theActorInTheSpotlight().should(
                seeThat(TheLines.displayed(), containsAll(expectedServices))
        );
    }
}