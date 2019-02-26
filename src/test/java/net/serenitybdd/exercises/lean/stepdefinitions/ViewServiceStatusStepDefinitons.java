package net.serenitybdd.exercises.lean.stepdefinitions;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.serenitybdd.exercises.lean.lines.ServiceInfo;
import net.serenitybdd.exercises.lean.statusupdates.ConsultStatus;
import net.serenitybdd.exercises.lean.statusupdates.SelectedStatusUpdates;
import net.thucydides.core.annotations.Steps;

import java.util.List;

import static net.serenitybdd.exercises.lean.statusupdates.StatusUpdatesTimeframe.UPDATES_FOR_THIS_WEEKEND;
import static org.assertj.core.api.Assertions.assertThat;

public class ViewServiceStatusStepDefinitons {

    @Steps
    ServiceInfo theService;

    @Steps
    ConsultStatus consultStatus;

    @Steps
    SelectedStatusUpdates selectedStatus;

    @Then("^he should see the following lines:")
    public void he_should_see_the_following_lines(List<String> expectedServices) {
        assertThat(theService.lines()).containsAll(expectedServices);
    }

    @When("he consults the line statuses")
    public void heConsultsTheLineStatuses() {}

    @When("he consults the line statuses for this weekend")
    public void he_consults_the_line_statuses_for_this_weekend() {
        consultStatus.forThisWeekEnd();
    }

    @Then("the statuses should be shown for this weekend")
    public void the_statuses_should_be_shown_for_this_weekend() {
        assertThat(selectedStatus.timeframe()).isEqualTo(UPDATES_FOR_THIS_WEEKEND);
    }
}
