Feature: Template matcher working well

  Scenario Outline: Template matching when they are matching
    Given template is "<template>"
    When the path is "<path>"
    Then they are equal

    Examples:
      | template                          | path                             |
      | whateverTemp                      | whatever                         |
      | oneTwoThreeTemp                   | one/two/three                    |
      | oneTwoTemp                        | one/two                          |
      | moreSpecificSomethingTemp         | more/specific/something          |
      | otherSomethingTemp                | other/something                  |
      | nullTemp                          | more/specific/something/and/more |

  Scenario: Template with no path
    Given an invalid template in no path test
    When the path is valid in no path test
    Then we get an exception in no path test

  Scenario: Template with numerical path
    Given an invalid template in numerical path test
    When the path is valid in numerical path test
    Then we get an exception in numerical path test