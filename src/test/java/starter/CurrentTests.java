package starter;

import cucumber.api.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        plugin = {"pretty"},
        tags = {"@current"},
        glue = "net.serenitybdd.exercises",
        features = "classpath:features"
)
public class CurrentTests {}
