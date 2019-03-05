package net.serenitybdd.exercises.screenplay.statusupdates;

import net.serenitybdd.screenplay.targets.Target;

class StatusUpdatesHeader {
    static Target THIS_WEEKEND = Target.the("Weekend").locatedBy("css:li.weekend");
    static Target NOW = Target.the("Now").locatedBy("css:li.now");
    static Target FUTURE = Target.the("Future").locatedBy("css:li.future");
}
