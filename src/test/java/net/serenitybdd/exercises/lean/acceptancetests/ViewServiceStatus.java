package net.serenitybdd.exercises.lean.acceptancetests;

import cucumber.api.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = "classpath:features/exercise_1/view_service_status.feature",
        plugin = {"pretty"},
        glue = "net.serenitybdd.exercises.lean.stepdefinitions"
)
public class ViewServiceStatus {}
