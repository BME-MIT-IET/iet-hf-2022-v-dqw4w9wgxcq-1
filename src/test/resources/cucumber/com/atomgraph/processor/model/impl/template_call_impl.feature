Feature: Template calls are handled properly

  Scenario Outline: Apply arguments
    Given argument parameters are "<parameter1>" and "<parameter2>"
    When arguments are applied
    Then argument URLs matches

    Examples:
      | parameter1 | parameter2        |
      | param1     | param1 with space |
      | param2     | param2 with space |

  Scenario: Get arguments
    Given parameter is "param"
    When argument is asked for
    Then argument is provided

  Scenario: Arg function
    Given parameters are "param" and "param with space"
    When call is made with arg function
    Then URL matches

  Scenario: Validate optionals
    Given parameters for optionals are "param1" and "param with space"
    When optionals are validated
    Then argument should exist

  Scenario: Query solution map
    Given query solution map exists
    When query solution map is asked for
    Then query solution map is provided