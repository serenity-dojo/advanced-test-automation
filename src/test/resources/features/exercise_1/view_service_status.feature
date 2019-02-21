Feature: View service status

  Check the current status of the TFL lines. Note that since we are testing against a live website,
  the results will not be as predictable as in a test environment.

  Scenario: Should see the list of available services in the status updates section
    Given Trevor is in the "Status updates" section
    When he consults the line statuses
    Then he should see the following lines:
      | Central  |
      | Circle   |
      | District |

  @current
  Scenario: Should see the list of available services for a future date
    Given Trevor is in the "Status updates" section
    When he consults the line statuses for "This Weekend"
    Then he should see the following lines:
      | Central  |
      | Circle   |
      | District |
    And the selected date should be "This Weekend"
