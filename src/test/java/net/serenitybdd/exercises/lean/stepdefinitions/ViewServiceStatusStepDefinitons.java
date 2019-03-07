package net.serenitybdd.exercises.lean.stepdefinitions;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.serenitybdd.exercises.lean.lines.ServiceInfo;
import net.thucydides.core.annotations.Steps;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ViewServiceStatusStepDefinitons {

    @Steps
    ServiceInfo theService;

    @Then("^he should see the following lines:")
    public void he_should_see_the_following_lines(List<String> expectedServices) {
        assertThat(theService.lines()).containsAll(expectedServices);
    }

    @When("he consults the line statuses")
    public void heConsultsTheLineStatuses() {}
}
