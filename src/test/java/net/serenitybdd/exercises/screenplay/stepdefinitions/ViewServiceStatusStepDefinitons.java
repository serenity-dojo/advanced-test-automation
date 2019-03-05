package net.serenitybdd.exercises.screenplay.stepdefinitions;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.serenitybdd.exercises.screenplay.lines.TheLines;
import net.serenitybdd.exercises.screenplay.statusupdates.ConsultTheLineStatus;
import net.serenitybdd.exercises.screenplay.statusupdates.SelectedStatus;

import java.util.List;

import static net.serenitybdd.assertions.CollectionMatchers.containsAll;
import static net.serenitybdd.exercises.screenplay.statusupdates.StatusUpdatesTimeframe.UPDATES_FOR_THIS_WEEKEND;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.hamcrest.Matchers.equalTo;

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

    @When("he consults the line statuses for this weekend")
    public void he_consults_the_line_statuses_for_this_weekend() {
        theActorInTheSpotlight().attemptsTo(
                ConsultTheLineStatus.forThisWeekEnd()
        );
    }

    @Then("the statuses should be shown for this weekend")
    public void the_statuses_should_be_shown_for_this_weekend() {
        theActorInTheSpotlight().should(
                seeThat(
                        SelectedStatus.timeframe(), equalTo(UPDATES_FOR_THIS_WEEKEND)
                )
        );
    }
}