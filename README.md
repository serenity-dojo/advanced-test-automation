# Advanced Test Automation Practices workshop

In each exercise, you will experiment with one aspect or technique of advanced test automation using Cucumber and Serenity BDD.
You will first see a working scenario which illustrates the approach. 
Then you will need to implement another similar scenario to put the approach into practice. The tutorial will guide you through one possible solution, though there are others.

## Part 1 - Lean Page Objects and Action Classes

In this section you will learn about the Lean Page Object and Action Class pattern.

### Exercise 1

The starting feature file for this exercise are in the `src/test/resource/features/exercise_1` folder.
The [view_service_status.feature](src/test/resources/features/exercise_1/view_service_status.feature) feature file contains two scenarios.
The first, "Should see the list of available services in the status updates section", has been implemented. 
This exercise involves implementing the second: "Should see the list of available services for a future date".

#### Step 1 - Add the missing step definitions
The second scenario is currently marked with the `@pending` annotation, which will prevent it from being executed when you run the full test suite.
Remove this annotation and run only this scenario by launching the `CurremtTests` test runner. 
This will generate step definition code for the two new steps in the console output. 
Copy these step definitions into the `ViewServiceStatusStepDefinitons` class, and rename the parameters to make them more meaningful.
The resulting code might look like this:

```java
    @When("he consults the line statuses for this weekend")
    public void he_consults_the_line_statuses_for_this_weekend() {
    }

    @Then("the selected date should be this weekend")
    public void the_selected_date_should_be_this_weekend() {
    }
```

#### Step 2 - Implement the first step definition

The first step definiton, "he consults the line statuses for {string}", involves selecting the date that Trevor wants to see the service statuses for.
To do this, he needs to click on the "Select Date" dropdown, and then click on the "This weekend" option. You could automate this action as `ConsultStatus` action class, 
with a `forThisWeekEnd()` method to click on the "This Weekend" option, e.g.

```java
public class ConsultStatus extends UIInteractionSteps {
    @Step("Consult status for this weekend")
    public void forThisWeekEnd() {
    }
}
```

And

```java

    @Steps
    ConsultStatus consultStatus;

    @When("he consults the line statuses for this weekend")
    public void he_consults_the_line_statuses_for_this_weekend() {
        consultStatus.forThisWeekend();
    }
```

Now implement the `forThisWeekend()` method. This will simply click on the "This Weekend" element in the status update header.

```java
    @Step("Consult status for this weekend")
    public void forThisWeekEnd() {
        $(StatusUpdatesHeader.THIS_WEEKEND).click();
    }
```

The `StatusUpdatesHeader` is a simple Lean Page Object class like this:

```java
class StatusUpdatesHeader {
    static By THIS_WEEKEND = By.cssSelector("li.weekend");
}
```

#### Step 3 - implement the verification step

The verification steps check the visible lines, and that the selected time period has been updated to "This weekend".
The first check is repeated from the previous scenario, so no extra coding is needed.
The second involves checking the currently selected period. 
We could do a simple boolean check, but a more realistic solution would involve modelling the returned update period so that it can cater for the different options: Now, This Weekend, and a future date.
We could implement this as a simple enum:

```java
public enum StatusUpdatesTimeframe {
    UPDATES_FOR_NOW,
    UPDATES_FOR_THIS_WEEKEND,
    UPDATES_FOR_A_FUTURE_DATE
}
```

Next, we could imagine a _Question_ class that queries the UI and returns the current selected state. 
We would use this _Question_ class as shown below:

```java
    @Steps
    SelectedStatusUpdates selectedStatus;

    @Then("the statuses should be shown for this weekend")
    public void the_statuses_should_be_shown_for_this_weekend() {
        assertThat(selectedStatus.timeframe()).isEqualTo(UPDATES_FOR_THIS_WEEKEND);
    }
```

The `SelectedStatusUpdates` Question class would query the screen to check which of the options has been selected. 
It could use a Lean Page Object class called `StatusUpdatesHeader` to identify the different options on the page:

```java
class StatusUpdatesHeader {
    static By THIS_WEEKEND = By.cssSelector("li.weekend");
    static By NOW = By.cssSelector("li.now");
    static By FUTURE = By.cssSelector("li.future");
}
```

Finally, the Question class itself could check which of these values has the "selected" CSS class:

```java
public class SelectedStatusUpdates extends UIInteractionSteps {

    public StatusUpdatesTimeframe timeframe() {
        if (isSelected(THIS_WEEKEND)) {
            return UPDATES_FOR_THIS_WEEKEND;
        } else if (isSelected(NOW)) {
            return UPDATES_FOR_NOW;
        } else if (isSelected(FUTURE)) {
            return UPDATES_FOR_A_FUTURE_DATE;
        }
        throw new AssertionError("No timeframe selected");
    }

    private boolean isSelected(By option) {
        return $(option).hasClass("selected");
    }
}
```

### Exercise 2

The next two exercises involves a new feature: Plan a Journey. The feature file looks like this:

```gherkin
Feature:  Plan a journey

  Scenario: Should see the fastest route between two stations
    Given Trevor is in the "Plan a journey" section
    When he views trips between "London Bridge" and "Canary Wharf"
    Then he should see a journey option for "Jubilee line to Canary Wharf"
```

#### Step 1 - Add the missing step definitions

The first step involves no extra code, as the existing step definition code will work. 
However the other two steps will need new step definitions, and these should go in a new step definition class (say `PlanJourneyStepDefinitions`).
These step definitions could look like the following:

```java
    @When("he views trips between {string} and {string}")
    public void he_views_trips_between_and(String departure, String destination) {
    }

    @Then("he should see a journey option for {string}")
    public void he_should_see_a_journey_option_for(String journeyDescription) {
    }
```

#### Step 2 - Adding an action class

Now implement the first of the new step definitions. 
Using a builder-style DSL, we could imagine a `PlanAJourney` action class like the one shown below:

```java
    @Steps
    PlanAJourney planAJourney;

    @When("he views trips between {string} and {string}")
    public void he_views_trips_between_and(String departure, String destination) {
        planAJourney.from(departure).to(destination);
    }

```

There are several ways you could implement the `PlanAJourney` class. 
A simple approach is to have each method perform a specific part of the task, as shown below:

```java
public class PlanAJourney extends UIInteractionSteps {

    @Step("Plan a journey from {0}")
    public PlanAJourney from(String departure) {
        $(FROM).click();
        $(FROM).sendKeys(departure);
        waitForRenderedElements(SUGGESTED_STOPS);
        $(SUGGESTED_STOPS).click();
        return this;
    }

    @Step("Travelling to {0}")
    public void to(String destination) {
        $(TO).click();
        $(TO).sendKeys(destination);
        waitForRenderedElements(SUGGESTED_STOPS);
        $(SUGGESTED_STOPS).click();
        
        $(PLAN_MY_JOURNEY).click();
        withTimeoutOf(WAIT_FOR_RESULTS_TIMEOUT).waitFor(JOURNEY_RESULTS);
    }
}
```

We could refactor the common code that selects a station into a common method:

```java
public class PlanAJourney extends UIInteractionSteps {

    @Step("Plan a journey from {0}")
    public PlanAJourney from(String departure) {
        selectStation(FROM, departure);
        return this;
    }

    @Step("Travelling to {0}")
    public void to(String destination) {
        selectStation(TO, destination);
        $(PLAN_MY_JOURNEY).click();
        withTimeoutOf(WAIT_FOR_RESULTS_TIMEOUT).waitFor(JOURNEY_RESULTS);
    }

    private void selectStation(By stationField, String station) {
        $(stationField).click();
        $(stationField).sendKeys(station);
        waitForRenderedElements(SUGGESTED_STOPS);
        $(SUGGESTED_STOPS).click();
    }
}
```

The locators mentioned in the code above come from a Lean Page Object like the following:

```java
class PlanAJourneyForm {
    static By FROM = By.id("InputFrom");
    static By TO = By.id("InputTo");
    static By SUGGESTED_STOPS = By.cssSelector(".stop-name");
    static By PLAN_MY_JOURNEY = By.cssSelector("#plan-a-journey .plan-journey-button");
}
```

As it belongs to a separate page, the `JOURNEY_RESULTS` can be placed in it's own `JourneyResultList` Lean Page Object:

```java
class JourneyResultList {
    static By JOURNEY_RESULTS = By.cssSelector(".journey-results");
}
```

#### Step 3 - adding a Question class

The last step definition checks that the displayed journey options include a specified trip leg ("Jubilee line to Canary Wharf").
 
We could do this with a `JourneyResults` _Question_ class, that can provide us with the list of stops displayed on the result page:

```java
    @Steps
    JourneyResults journeyResults;

    @Then("he should see a journey option for {string}")
    public void he_should_see_a_journey_option_for(String journeyDescription) {
        assertThat(journeyResults.stops()).contains(journeyDescription);
    }
```

This _Question_ class queries the stop location elements, extracts the text content of each element, and returns them as a list:

```java
public class JourneyResults extends UIInteractionSteps {
    public List<String> stops() {
        return findAll(STOP_LOCATION).stream()
                .map(WebElementFacade::getTextContent)
                .collect(Collectors.toList());
    }
}
```

The `STOP_LOCATION` locator is defined in a Lean Page Object class that encapsulates locators from the journey results list on the search results page:

```java
class JourneyResultList {
    static By JOURNEY_RESULTS = By.cssSelector(".journey-results");
    static By STOP_LOCATION = By.cssSelector(".stop-location-description");
}
```

### Exercise 3

The next exercise involves extending the previous scenario to add some search preferences.

```gherkin
  Scenario: Should be able to select a preferred travel mode
    Given Trevor is in the "Plan a journey" section
    And Trevor prefers to travel by:
      | DLR |
    When he views trips between "Bank" and "Canary Wharf"
    Then he should see a journey option for "DLR to Canary Wharf DLR Station"
```

The only new step is the second one, which we could implement like this:

```gherkin
    @Steps
    SetTravelPreferences setTravelPreferences;
    
    @Given("Trevor prefers to travel by:")
    public void trevor_prefers_to_travel_by(List<String> travelModes) {
        setTravelPreferences.toTravelBy(travelModes);
    }
```

Preferences are managed in a separate panel, shown below:

[Travel Preferences](src/test/resources/assets/tfl-preferences.png)

`SetTravelPreferences` is an Action class that opens the preferences panel, waits until it displays completely, unselects ths current travel options, and selects the requested ones:

```java
public class SetTravelPreferences extends UIInteractionSteps {

    @Step("Set travel preferences to {0}")
    public void toTravelBy(List<String> travelModes) {

        $(TravelPreferenceOptions.EDIT_PREFERENCES).click();
        waitFor(visibilityOfElementLocated(TravelPreferenceOptions.PLAN_MY_JOURNEY_BUTTON));
        $(DESELECT_ALL).click();
        travelModes.forEach(
                option -> $(TravelPreferenceOptions.travelOptionFor(option)).click()
        );
        $(TravelPreferenceOptions.HIDE_PREFERENCES).click();
    }
}
```

The `TravelPreferenceOptions` contains locators for these fields. 
The `travelOptionFor()` method returns a dynamically-generated XPath locator as shown below:

```java
class TravelPreferenceOptions {
    static By EDIT_PREFERENCES = By.linkText("Edit preferences");
    static By HIDE_PREFERENCES = By.linkText("Hide preferences");
    static By DESELECT_ALL = By.linkText("deselect all");
    static By PLAN_MY_JOURNEY_BUTTON = By.cssSelector("#more-journey-options .plan-journey-button");

    static By travelOptionFor(String travelOption) {
        return By.xpath(String.format("//label[.='%s']", travelOption));
    }
}

```

This scenario should now run.

### Exercise 4

Add a scenario where the user specifies a different daparture date:

```gherkin
  Scenario: Should be able to specify a different departure time
    Given Trevor is in the "Plan a journey" section
    And Trevor wants to leave "Tomorrow"
    When he views trips between "London Bridge" and "Canary Wharf"
    Then he should see a journey option for "Jubilee line to Canary Wharf"
```
