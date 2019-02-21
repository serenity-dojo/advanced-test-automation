package net.serenitybdd.exercises.lean.stepdefinitions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.serenitybdd.exercises.lean.lines.ServiceInfo;
import net.serenitybdd.exercises.lean.navigation.NavigateTo;
import net.thucydides.core.annotations.Steps;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ViewServiceStatusStepDefinitons {

    @Steps
    NavigateTo navigate;

    @Steps
    ServiceInfo theService;

    @Given("Trevor is in the {string} section")
    public void trevorIsInTheSection(String sectionName) {
        navigate.toSection(sectionName);
    }

    @Then("^he should see the following lines:")
    public void he_should_see_the_following_lines(List<String> expectedServices) {
        assertThat(theService.lines()).containsAll(expectedServices);
    }

    @When("he consults the line statuses")
    public void heConsultsTheLineStatuses() {}
}
