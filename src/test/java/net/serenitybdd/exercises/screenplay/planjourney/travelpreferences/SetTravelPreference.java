package net.serenitybdd.exercises.screenplay.planjourney.travelpreferences;

import com.google.common.collect.ImmutableMap;
import java.util.Map;

public class SetTravelPreference {

    private static final Map<String, PreferenceSetter> TRAVEL_OPTION_ACTIONS = ImmutableMap.of(
            "Travel by", new TravelByPreference(),
            "Be shown", new SetRoutePreference(),
            "Have access options", new SetAccessOptionsPreference(),
            "Walk no more than", new WalkNoMoreThanPreference()
    );

    public static PreferenceSetter forOption(String travelOption) {
        return TRAVEL_OPTION_ACTIONS.get(travelOption.trim());
    }
}
