Feature: Templates are behaving as intended

  Scenario: Super template test
    Given super and sub templates exists
    When super template is asked for through its sub template
    Then super template is provided

  Scenario: Non null languages test
    Given super templates and corresponding languages exists
    When languages is asked for
    Then list of languages are provided

  Scenario: Inherited match test
    Given super templates with a match exists
    When match is asked for
    Then match is provided

  Scenario: Inherited query test
    Given super templates with a query exists
    When query is asked for
    Then query is provided

  Scenario: Inherited update test
    Given super templates with an update exists
    When update is asked for
    Then update is provided

  Scenario: Inherited priority test
    Given super templates with a priority exists
    When priority is asked for
    Then priority is provided