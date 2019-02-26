package net.serenitybdd.exercises.lean.stepdefinitions;

import cucumber.api.java.en.Given;
import net.serenitybdd.exercises.lean.navigation.NavigateTo;
import net.thucydides.core.annotations.Steps;

public class NavigationStepDefinitons {

    @Steps
    NavigateTo navigate;

    @Given("Trevor is in the {string} section")
    public void trevorIsInTheSection(String sectionName) {
        navigate.toSection(sectionName);
    }
}
