Feature:  Plan a journey

  Scenario: Should see the fastest route between two stations
    Given Trevor is in the "Plan a journey" section
    When he views trips between "London Bridge" and "Canary Wharf"
    Then he should see a journey option for "Jubilee line to Canary Wharf"

    @current
  Scenario: Should be able to select a preferred travel mode
    Given Trevor is in the "Plan a journey" section
    And Trevor prefers to travel by:
      | DLR |
    When he views trips between "London Bridge" and "Canary Wharf"
    Then he should see a journey option for "DLR to Canary Wharf DLR Station"
