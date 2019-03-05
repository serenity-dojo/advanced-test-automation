package net.serenitybdd.exercises.screenplay.acceptancetests;

import cucumber.api.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = "classpath:features/exercise_2/plan_journey.feature",
        plugin = {"pretty"},
        glue = "net.serenitybdd.exercises.screenplay.stepdefinitions"
)
public class PlanMyJourney {}
