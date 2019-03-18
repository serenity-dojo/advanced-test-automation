Feature:  Plan a journey

  Scenario: Should see the fastest route between two stations
    Given Trevor is in the "Plan a journey" section
    When he views trips between "London Bridge" and "Canary Wharf"
    Then he should see a journey option for "Jubilee line to Canary Wharf"

  Scenario: Should be able to select a preferred travel mode
    Given Trevor is in the "Plan a journey" section
    And Trevor prefers to travel by:
      | DLR |
    When he views trips between "London Bridge" and "Canary Wharf"
    Then he should see a journey option for "DLR to Canary Wharf DLR Station"

  Scenario: Should be able to select several preferred travel modes
    Given Trevor is in the "Plan a journey" section
    And Trevor prefers to travel by:
      | Bus |
      | DLR |
    When he views trips between "London Bridge" and "Canary Wharf"
    Then he should see a journey option for "DLR to Canary Wharf DLR Station"

  @current
  Scenario: Should be able to select the route with fewest changes
    Given Trevor is in the "Plan a journey" section
    And Trever prefers to:
      | Travel by           | Bus, DLR                   |
      | Be shown            | Routes with fewest changes |
      | Have access options | Step-free to platform only |
      | Walk no more than   | 30 mins                    |
    When he views trips between "London Bridge" and "Canary Wharf"
    Then he should see a journey option for "DLR to Canary Wharf DLR Station"