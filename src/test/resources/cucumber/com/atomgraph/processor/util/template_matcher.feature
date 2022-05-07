Feature: Template matcher

  Scenario Outline: Path is match
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

  Scenario: No path
    Given an invalid template in no path test
    When the path is valid in no path test
    Then we get an exception in no path test

  Scenario: Template with numerical path
    Given an invalid template in numerical path test
    When the path is valid in numerical path test
    Then we get an exception in numerical path test