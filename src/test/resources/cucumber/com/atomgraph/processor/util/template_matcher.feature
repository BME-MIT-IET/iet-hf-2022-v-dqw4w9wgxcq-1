Feature: Template matcher working well

  Scenario Outline: Template matcher match path case
    Given template of the template matcher is "<template>"
    When the path of the template matcher is "<path>"
    Then the template and the path are equal

    Examples:
      | template                          | path                             |
      | whateverTemp                      | whatever                         |
      | oneTwoThreeTemp                   | one/two/three                    |
      | oneTwoTemp                        | one/two                          |
      | moreSpecificSomethingTemp         | more/specific/something          |
      | otherSomethingTemp                | other/something                  |
      | nullTemp                          | more/specific/something/and/more |

  Scenario: Template matcher no path case
    Given an invalid template of no path test
    When the path is valid of no path test
    Then we get an exception in no path test

  Scenario: Template with numerical path
    Given an invalid template of numerical path test
    When the path is valid of numerical path test
    Then we get an exception in numerical path test