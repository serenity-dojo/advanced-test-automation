# Advanced Test Automation Practices workshop

[![CircleCI](https://circleci.com/gh/serenity-bdd/advanced-bdd-test-automation.svg?style=svg)](https://circleci.com/gh/serenity-bdd/advanced-bdd-test-automation)

In each exercise, you will experiment with one aspect or technique of advanced test automation using Cucumber and Serenity BDD.
You will first see a working scenario which illustrates the approach. 
Then you will need to implement another similar scenario to put the approach into practice. The tutorial will guide you through one possible solution, though there are others.

In this section, you will learn how to implement the same tests using the Screenplay pattern.

### Exercise 1

The first exercise is to study the existing code base to understand the basics of how a Screenplay test works.

The starting feature file for this exercise are in the `src/test/resource/features/exercise_1` folder.
The [view_service_status.feature](src/test/resources/features/exercise_1/view_service_status.feature) feature file contains two scenarios.
The first, "Should see the list of available services in the status updates section", has been implemented. 
Let's walk through the code to see what it does.

The first scenario looks like this:
```gherkin
  Scenario: Should see the list of available services in the status updates section
    Given Trevor is in the "Status updates" section
    When he consults the line statuses
    Then he should see the following lines:
      | Central  |
      | Circle   |
      | District |
```

The first step is implemented in the [NavigationStepDefinitons](src/test/java/net/serenitybdd/exercises/screenplay/stepdefinitions/NavigationStepDefinitons.java) class.
Screenplay is an actor-centric pattern, so all actions are performed by _actors_. 
Actors are identified by name, and can have different abilities, such as the ability to interact with web pages using Selenium,
or to call a REST API using Rest Assured. 

Actors can be created individually, but in Cucumber it is more convenient to create them dynamically, by name. 
To do this, we need to define a cast of actors, using the `OnStage.setTheStage()`. 
A `Cast` is like a factory for actors, which creates actors with a set of predefined abilities. 

A convenient way to create a `Cast` is to use the `OnlineCast` class, which provides actors with the `BrowseTheWeb` ability. 
This gives the actors access to the Serenity WebDriver API and their own WebDriver instance, which is enough if you are only doing web testing.
We do this in a `@Before` method, as shown below:

```java
    @Before
    public void prepareActors() {
        OnStage.setTheStage(new OnlineCast());
    }
```

This first step passes in the name of the actor ("Trevor") and the section that the actor wants to navigate to ("Status Updates"):

```java
    @Given("{word} is in the {string} section")
    public void trevorIsInTheSection(String actor, String sectionName) {
        theActorCalled(actor).attemptsTo(           (1)
            Navigate.toSection(sectionName)         (2)
        );
    }
```

Here we obtain an actor with the specified nam using the `theActorCalled()` method (1), and tell the actor to attempt to navigate to the specified section (2).

The [Navigate](src/test/java/net/serenitybdd/exercises/screenplay/navigation/Navigate.java) class is a factor class that returns _Performable_s. A _Performable_ is a class that represents an action that an actor can perform. 
For readability, we distinguish two types of Performables: `Task`s, which represent business tasks, and `Interactions`, which represent interactions with the system.

The `Navigate` class contains a `toSection()` method that returns a `Task` which opens the browser on the application home page, and clicks on the requested menu entry.
The `Task.where()` method is a short-hand way of creating a task that executes one or more other tasks or interactions.
```java
public class Navigate {
    public static Performable toSection(String sectionName) {
        return Task.where("{0} navigates to the '#sectionName' section",  (1)
                Open.browserOn().the(TFLHomePage.class),                  (2)
                Click.on(MenuBar.MENU_TAB.of(sectionName))                (3)
        ).with("sectionName").of(sectionName);                            (4)

    }
}
```
(1) The name of the task, as it will appear in the reports
(2) An `Interaction` that opens the browser on a specified page
(3) An `Interaction` that clicks on a specified element of a page.
(4) We need to pass the `sectionName` variable so that it will appear correctly in the report.


The last interaction, `Click.on(MenuBar.MENU_TAB.of(sectionName))`, uses a `Target` to locate the element on the web page.
A `Target` is a class that associates a human-readable label with a WebDriver locator. For example:

```java
static Target JOURNEY_RESULTS = Target.the("Journey results list").locatedBy(".journey-results");
```

The `MENU_TAB` target in the [MenuBar](src/test/java/net/serenitybdd/exercises/screenplay/navigation/MenuBar.java) class is a dynamic target.
By using the `of()` method, you can pass in a parameter that will be injected into the locator at run time. 
The full `MenuBar` class is shown below:

```java
class MenuBar {
    static Target MENU_TAB = Target.the("Menu tab {0}")
                                   .locatedBy("//div[@class='top-row']//li[.='{0}']");
}
```

The _Then_ clause checks that the list of lines displayed includes the ones mentioned in the scenario. 
This step definition uses the `should()` method, Serenity Screnplay's equivalent to an `assertThat()` statement. 
The `should()` method combines a `Question`, an object that queries the state of the application, and a Hamcrest assertion.
In the following step definition, we fetch the list of visible tube lines, and compare this list to the expected ones:

```java
    @Then("^he should see the following lines:")
    public void he_should_see_the_following_lines(List<String> expectedServices) {
        theActorInTheSpotlight().should(
                seeThat(TheLines.displayed(), containsAll(expectedServices))
        );
    }
```

The `containsAll()` matcher is not a standard Hamcrest matcher: it is a Serenity matcher from the `serenity-assertions` package. 
This package contains the `CollectionsMatcher` class, which provides a number of assertions that often come in handy when writing Screenplay tests.

The [TheLines](src/test/java/net/serenitybdd/exercises/screenplay/lines/TheLines.java) class reads the text contents of the service lines,
as viewed in the actor's WebDriver instance, and returns these values as a list of strings:

```java
public class TheLines {
    public static Question<List<String>> displayed() {
        return actor -> TextContent.of(ServiceLinePanel.SERVICE_NAME)
                .viewedBy(actor)
                .asList()
                .stream()
                .map(String::trim)
                .collect(Collectors.toList());
    }
}
```

You can run this scenario by running the [ViewServiceStatus](src/test/java/net/serenitybdd/exercises/screenplay/acceptancetests/ViewServiceStatus.java) class.


## Exercise 2
The next exercise involves implementing the second: "Should see the list of available services for a future date".

#### Step 1 - Add the missing step definitions
The second scenario is currently marked with the `@pending` annotation, which will prevent it from being executed when you run the full test suite.
Remove this annotation and run only this scenario by launching the `PlanMyJourney` test runner. 
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
To do this, he needs to click on the "Select Date" dropdown, and then click on the "This weekend" option. 
We will model this action as a business task called `ConsultTheLineStatus.forThisWeekEnd()`, like this:

```java
    @When("he consults the line statuses for this weekend")
    public void he_consults_the_line_statuses_for_this_weekend() {
        theActorInTheSpotlight().attemptsTo(
                ConsultTheLineStatus.forThisWeekEnd()
        );
    }
```

The `ConsultTheLineStatus` class is just a factory class for the `forThisWeekEnd()` method, which does all the real work. 
To see the line statuses for the upcoming weekend, we need to click on the "This Weekend" link shown below:

[Travel Preferences](src/test/resources/assets/tfl-preferences.png)

And to click this link, we create a `Task` that does exactly that:

```java
public class ConsultTheLineStatus {
    public static Performable forThisWeekEnd() {
        return Task.where("Consult status for this weekend",
                Click.on(StatusUpdatesHeader.THIS_WEEKEND)
        );
    }
}
```

The `StatusUpdatesHeader` is Screenplay's answer to a Page Object: a simple class containing the locators for elements on a part of a screen.

```java
class StatusUpdatesHeader {
    static Target THIS_WEEKEND = Target.the("Weekend").locatedBy("css:li.weekend");
}
```

And that's all for this step. Let's see how we check the results.

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
    @Then("the statuses should be shown for this weekend")
    public void the_statuses_should_be_shown_for_this_weekend() {
        theActorInTheSpotlight().should(
                seeThat(
                        SelectedStatus.timeframe(), equalTo(UPDATES_FOR_THIS_WEEKEND)
                )
        );
    }

```

The `SelectedStatus.timeframe()` Question class would query the screen to check which of the options has been selected. 
We could expand our `StatusUpdatesHeader` class to identify the different options on the page:

```java
class StatusUpdatesHeader {
    static Target THIS_WEEKEND = Target.the("Weekend").locatedBy("css:li.weekend");
    static Target NOW = Target.the("Now").locatedBy("css:li.now");
    static Target FUTURE = Target.the("Future").locatedBy("css:li.future");
}

```


Finally, the Question class itself needs to check which of these values has the "selected" CSS class. 
We can do this check in three parts. Firstly, we need a map to describe the `StatusUpdatesTimeframe` values that
correspond to each element on the page:

```java
    private static final Map<Target, StatusUpdatesTimeframe> SELECTED_STATUS_UPDATE_TIMEFRAMES = ImmutableMap.of(
            THIS_WEEKEND, UPDATES_FOR_THIS_WEEKEND,
            NOW,          UPDATES_FOR_NOW,
            FUTURE,       UPDATES_FOR_A_FUTURE_DATE
    );
```

Next, we need to write the `timeframe()` method, which provides a Screenplay `Question` that returns the `StatusUpdatesTimeframe` value corresponding to the selected element on the page.
To provide better reporting, we can use the `Question.about("...").answeredBy()` construct, like this:

```java
    public static Question<StatusUpdatesTimeframe> timeframe() {
        return Question.about("selected status").answeredBy(
                actor -> SELECTED_STATUS_UPDATE_TIMEFRAMES.keySet().stream()
                    .filter( target -> isSelected(target.resolveFor(actor)))
                    .findFirst()
                    .map(SELECTED_STATUS_UPDATE_TIMEFRAMES::get)
                    .orElseThrow(() -> new AssertionError("No timeframe selected"))
        );
    }
```

Here we go through all the selected status elements on the page (the key values in the `SELECTED_STATUS_UPDATE_TIMEFRAMES` map),
and find the first one that is selected. We then get the corresponding `StatusUpdatesTimeframe` value from the `SELECTED_STATUS_UPDATE_TIMEFRAMES` map,
or throw an assertion error if none of the elements was selected.

Finally, we need to implement the `isSelected` method. 
This uses the Serenity `WebElementFacade` class and `hasClass()` method to check whether a given web element has the "selected" class:

```java
    private static boolean isSelected(WebElementFacade element) {
        return element.hasClass("selected");
    }
```

The complete class could look like this:
```java
public class SelectedStatus {

    private static final Map<Target, StatusUpdatesTimeframe> SELECTED_STATUS_UPDATE_TIMEFRAMES = ImmutableMap.of(
            THIS_WEEKEND, UPDATES_FOR_THIS_WEEKEND,
            NOW, UPDATES_FOR_NOW,
            FUTURE, UPDATES_FOR_A_FUTURE_DATE
    );

    public static Question<StatusUpdatesTimeframe> timeframe() {
        return Question.about("selected status").answeredBy(
                actor -> SELECTED_STATUS_UPDATE_TIMEFRAMES.keySet().stream()
                    .filter( target -> isSelected(target.resolveFor(actor)))
                    .findFirst()
                    .map(SELECTED_STATUS_UPDATE_TIMEFRAMES::get)
                    .orElseThrow(() -> new AssertionError("No timeframe selected"))
        );
    }

    private static boolean isSelected(WebElementFacade element) {
        return element.hasClass("selected");
    }
}
```

You should now be able to run this scenario successfully.

### Exercise 3

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
Using a builder-style DSL and a Screenplay Task, we could imagine a `PlanAJourney` action class like the one shown below:

```java
    @When("he views trips between {string} and {string}")
    public void he_views_trips_between_and(String departure, String destination) {
        theActorInTheSpotlight().attemptsTo(
                PlanAJourney.from(departure).to(destination)
        );
    }
```

There are several ways to implement the `PlanAJourney` class. One approach would be to use a simple builder pattern to collect the variables it needs to build the task,
and then to return a new task with the appropriate departure and destination values.
```java
public class PlanAJourney implements Task {

    private String departure;
    private String destination;

    public PlanAJourney(String departure, String destination) {
        this.departure = departure;
        this.destination = destination;
    }

    @Step("Plan a journey from #departure to #destination")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                SelectStation.from(departure),
                SelectStation.to(destination),
                Click.on(PLAN_MY_JOURNEY),
                WaitUntil.the(JOURNEY_RESULTS, isVisible())
        );
    }

    public static PlanAJourneyBuilder from(String departure) {
        return new PlanAJourneyBuilder(departure);
    }

    public static class PlanAJourneyBuilder {
        private String departure;

        PlanAJourneyBuilder(String departure) {
            this.departure = departure;
        }

        public Performable to(String destination) {
            return new PlanAJourney(departure, destination);
        }
    }
}
```

A slightly more concise approach would be to use a class and an interface to build the DSL, like this:

```java
public interface ToDestination {
    Performable to(String destination);
}

public class PlanAJourney implements Task, ToDestination {

    private String departure;
    private String destination;

    PlanAJourney(String departure, String destination) {
        this.departure = departure;
        this.destination = destination;
    }

    @Step("Plan a journey from #departure to #destination")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(                                       (1)
                SelectStation.from(departure),          
                SelectStation.to(destination),
                Click.on(PLAN_MY_JOURNEY),
                WaitUntil.the(JOURNEY_RESULTS, isVisible())
        );
    }

    public static ToDestination from(String departure) {
        return new PlanAJourney(departure, null);
    }
    
    public Performable to(String destination) {
        return new PlanAJourney(departure, destination);
    }
}
```

In both cases the action happens in the `attemptsTo()` method (1). Here, we use some domain-specific steps (`SelectStation.from()` and `SelectStation.to()`) 
and some Serenity interaction classes (`Click.on()` and `WaitUntil`) to select the stations and plan the journey.

Now add the `SelectStation` class. This is a factory class that provides two performable tasks: one to select the FROM station, and one for the TO station.
These both return a variation of the same task, which clicks on the station field, waits until the list of stations appears, and the clicks on the first of the suggested stops:

```java
public class SelectStation {
    public static Performable from(String departure) {
        return selectStation(PlanAJourneyForm.FROM, departure);
    }

    public static Performable to(String destination) {
        return selectStation(PlanAJourneyForm.TO, destination);
    }

    private static Performable selectStation(Target stationField, String stationName) {
        return Task.where(
                "{0} selects station #stationField",
                Click.on(stationField),
                SendKeys.of(stationName).into(stationField),
                Click.on(PlanAJourneyForm.SUGGESTED_STOPS)
        ).with("stationField").of(stationField);
    }
}
```


The locators mentioned in the code above come from a Lean Page Object like the following:

```java
class PlanAJourneyForm {
    static Target FROM = Target.the("From field").locatedBy("#InputFrom");
    static Target TO = Target.the("To field").locatedBy("#InputTo");
    static Target SUGGESTED_STOPS = Target.the("Selected Stops").locatedBy(".stop-name");
    static Target PLAN_MY_JOURNEY = Target.the("Plan Journey button").locatedBy("#plan-a-journey .plan-journey-button");
}
```

As it belongs to a separate page, the `JOURNEY_RESULTS` can be placed in it's own `JourneyResultList` Lean Page Object:

```java
class JourneyResultList {
    static Target JOURNEY_RESULTS = Target.the("Journey results list")
                                          .locatedBy("css:.journey-results");
}
```

#### Step 3 - adding a Question class

The last step definition checks that the displayed journey options include a specified trip leg ("Jubilee line to Canary Wharf").
 
We could do this with a `JourneyResults` _Question_ class, that can provide us with the list of stops displayed on the result page:

```java
    @Then("he should see a journey option for {string}")
    public void he_should_see_a_journey_option_for(String journeyDescription) {
        theActorInTheSpotlight().should(
                seeThat(JourneyResults.stops(), hasItem(journeyDescription))
        );
    }
```

This _Question_ class queries the stop location elements, extracts the text content of each element, and returns them as a list. 

```java
public class JourneyResults extends UIInteractionSteps {
    public static Question<List<String>> stops() {
        return Question.about("stops").answeredBy(
                actor -> TextContent.of(STOP_LOCATION).viewedBy(actor)
                .asList()
                .stream()
                .map(StringUtils::strip)
                .collect(Collectors.toList())
        );
    }
}
```
We are using the `TextContent` class here to retrieve the text contents of the elements, which allows us to read elements that are not visible without scrolling down.
This is faster and more reliable than having to scroll, but it does mean we need to strip off blank lines and whitespace (using the `StringUtils.strip()` method) before we return the values.

The `STOP_LOCATION` locator is defined in a Lean Page Object class that encapsulates locators from the journey results list on the search results page:

```java
class JourneyResultList {
    static Target JOURNEY_RESULTS = Target.the("Journey results list")
                                          .locatedBy("css:.journey-results");
    static Target STOP_LOCATION = Target.the("stop locations")
                                        .locatedBy("css:.stop-location-description");
}
```

You should now be able to run the scenario.

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
    @Given("Trevor prefers to travel by:")
    public void trevor_prefers_to_travel_by(List<String> travelModes) {
        theActorInTheSpotlight().attemptsTo(
                SetTravelPreferences.toTravelBy(travelModes)
        );
    }
```

Preferences are managed in a separate panel, shown below:

[Travel Preferences](src/test/resources/assets/tfl-preferences.png)

`SetTravelPreferences` is a _Task_ that opens the preferences panel, waits until it displays completely, deselects ths current travel options, and selects the requested ones:

```java
public class SetTravelPreferences implements Performable {

    public static Performable toTravelBy(List<String> travelModes) {
        return new SetTravelPreferences(travelModes);
    }

    private List<String> travelModes;

    private SetTravelPreferences(List<String> travelModes) {
        this.travelModes = travelModes;
    }
}
```

This is a _Performable_, so it needs to implement the `performAs()` method:

```java
    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Click.on(EDIT_PREFERENCES),
                WaitUntil.the(PLAN_MY_JOURNEY_BUTTON, isVisible()),
                Click.on(DESELECT_ALL),

                SelectEachTravelMode.in(travelModes),

                Click.on(HIDE_PREFERENCES)
        );
    }
```

The `SelectEachTravelMode.in()` method uses an interaction class that we need to create. 
This class will be responsible for checking each option from the provided list. 
Placing this operation in a separate class is easier to read and provides better separation of concerns.

```java
class SelectEachTravelMode {
    static Performable in(List<String> travelModes) {
        return Task.where("Select travel modes: " + travelModes, 
                          clickOnEachTravelModeFrom(travelModes));      (1)  
    }

    private static Performable[] clickOnEachTravelModeFrom(List<String> travelModes) {  (2)
        return travelModes.stream()
                .map( travelMode -> Click.on(travelOptionFor(travelMode)))              (3)
                .collect(Collectors.toList())
                .toArray(new Performable[]{});
    }
}
```

The `in()` method returns a _Task_ made up of a list of Click interactions. 
Because the `Task.where()` method accepts a vararg parameter, we can pass in a list of interactions as an array.
This is what the `clickOnEachTravelModeFrom()` method (2) does. 
We convert the list of travel option values to click actions on the corresponding web elements (3),
and then convert this list into an array that the `Task.where()` method can consume (4).

The final piece of the puzzle is the `travelOptionFor()` method. 

The `TravelPreferenceOptions` contains `Target`s for the fields used in these tasks, as well as the `travelOptionFor()` method 
which returns a dynamically-generated XPath locator corresponding to the checkbox for a given travel option:

```java
class TravelPreferenceOptions {
    static Target EDIT_PREFERENCES = Target.the("Edit Preferences link").located(By.linkText("Edit preferences"));
    static Target HIDE_PREFERENCES = Target.the("Hide Preferences link").located(By.linkText("Hide preferences"));
    static Target DESELECT_ALL = Target.the("Deselect all options").located(By.linkText("deselect all"));
    static Target PLAN_MY_JOURNEY_BUTTON = Target.the("Plan my journey button")
                                                 .locatedBy("css:#more-journey-options .plan-journey-button");

    static By travelOptionFor(String travelOption) {
        return By.xpath(String.format("//label[.='%s']", travelOption));
    }
}
```

This scenario should now run.
