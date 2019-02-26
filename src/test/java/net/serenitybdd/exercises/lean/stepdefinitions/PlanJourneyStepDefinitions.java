package net.serenitybdd.exercises.lean.stepdefinitions;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.serenitybdd.exercises.lean.planjourney.JourneyResults;
import net.serenitybdd.exercises.lean.planjourney.PlanAJourney;
import net.serenitybdd.exercises.lean.planjourney.SetTravelPreferences;
import net.thucydides.core.annotations.Steps;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PlanJourneyStepDefinitions {

    @Steps
    PlanAJourney planAJourney;

    @Steps
    JourneyResults journeyResults;

    @Steps
    SetTravelPreferences setTravelPreferences;

    @Given("Trevor prefers to travel by:")
    public void trevor_prefers_to_travel_by(List<String> travelModes) {
        setTravelPreferences.toTravelBy(travelModes);
    }


    @When("he views trips between {string} and {string}")
    public void he_views_trips_between_and(String departure, String destination) {
        planAJourney.from(departure).to(destination);
    }

    @Then("he should see a journey option for {string}")
    public void he_should_see_a_journey_option_for(String journeyDescription) {
        assertThat(journeyResults.stops()).contains(journeyDescription);
    }

    @And("Trevor wants to leave {string}")
    public void trevorWantsToLeave(String leaveDate) {
        planAJourney.leavingOn(leaveDate);
    }
}
