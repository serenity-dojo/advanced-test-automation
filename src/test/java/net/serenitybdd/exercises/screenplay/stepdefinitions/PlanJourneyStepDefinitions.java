package net.serenitybdd.exercises.screenplay.stepdefinitions;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.serenitybdd.exercises.screenplay.planjourney.JourneyResults;
import net.serenitybdd.exercises.screenplay.planjourney.PlanAJourney;
import net.serenitybdd.exercises.screenplay.planjourney.travelpreferences.PreferencesPanel;
import net.serenitybdd.exercises.screenplay.planjourney.travelpreferences.SetTravelPreference;
import net.serenitybdd.exercises.screenplay.planjourney.travelpreferences.SetTravelPreferences;
import net.serenitybdd.screenplay.Actor;

import java.util.List;
import java.util.Map;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.hamcrest.Matchers.hasItem;

public class PlanJourneyStepDefinitions {


    @Given("Trevor prefers to travel by:")
    public void trevor_prefers_to_travel_by(List<String> travelModes) {
        theActorInTheSpotlight().attemptsTo(
                SetTravelPreferences.toTravelBy(travelModes)
        );
    }


    @When("he views trips between {string} and {string}")
    public void he_views_trips_between_and(String departure, String destination) {
        theActorInTheSpotlight().attemptsTo(
                PlanAJourney.from(departure).to(destination)
        );
    }

    @Then("he should see a journey option for {string}")
    public void he_should_see_a_journey_option_for(String journeyDescription) {
        theActorInTheSpotlight().should(
                seeThat(JourneyResults.stops(), hasItem(journeyDescription))
        );
    }

    @And("Trever prefers to:")
    public void treverPrefersTo(Map<String, String> travelPreferences) {
        Actor theTraveller = theActorInTheSpotlight();

        theTraveller.attemptsTo(PreferencesPanel.openThePreferencesPanel());

        travelPreferences.forEach(
                (travelOption, preference)
                        -> theTraveller.attemptsTo(SetTravelPreference.forOption(travelOption).to(preference))
        );

        theTraveller.attemptsTo(PreferencesPanel.closeThePreferencesPanel());
    }
}
