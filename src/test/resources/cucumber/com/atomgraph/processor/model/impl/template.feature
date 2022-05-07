Feature: Templates are behaving as intended

  Scenario: Super template test
    Given super and sub templates exists
    When super template is asked for through its sub template
    Then super template is provided