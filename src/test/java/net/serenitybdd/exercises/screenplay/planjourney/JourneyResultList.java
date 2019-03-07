package net.serenitybdd.exercises.screenplay.planjourney;

import net.serenitybdd.screenplay.targets.Target;

class JourneyResultList {
    static Target JOURNEY_RESULTS = Target.the("Journey results list")
                                          .locatedBy("css:.journey-results");
    static Target STOP_LOCATION = Target.the("stop locations")
                                        .locatedBy("css:.stop-location-description");
}
