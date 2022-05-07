Feature: Skolemizer

  Scenario: Inheritance
    Given the expected skolemizer in inheritance test, which is a superclass
    When the actual skolemizer in inheritance test, which is its inherit
    Then the super and the inherit skolemizer are equal in inheritance test

  Scenario: No path
    Given the expected uri in no path test, which is null
    When the actual uri has no path in no path test
    Then the null and the pathless uri are equal in no path test

  Scenario: Invalid type class
    Given the expected uri in invalid class type test, which is null
    When the actual uri's class type is invalid in invalid class type test
    Then the null and the invalid class type skolemizer are equal in invalid class type test

  Scenario: Invalid super class
    Given the expected uri in invalid super class test
    When the actual uri is inheritance of this invalid superclass in invalid super class test
    Then the invalid superclass uri and its inherit are equal in invalid super class test

  Scenario: Absolute path
    Given the expected uri in absolute path test
    When the actual uri is gave in absolute path test
    Then the expected and the actual uri are equal in absolute path test

  Scenario: Primary topic
    Given the expected uri in primary topic test
    When the actual uri is gave in primary topic test
    Then the expected and the actual uri are equal in primary topic test

  Scenario: Has value restriction path
    Given the expected uri in has value restriction path test
    When the actual uri is gave in has value restriction path test
    Then the expected and the actual uri are equal in has value restriction path test

  Scenario: All values from restriction path
    Given the expected uri in all values from restriction path test
    When the actual uri is gave in all values from restriction path test
    Then the expected and the actual uri are equal in all values from restriction path test

  Scenario: Fragment
    Given the expected uri in fragment test
    When the actual uri is gave in fragment test
    Then the expected and the actual uri are equal in fragment test

  Scenario: Invalid path
    Given an invalid resource in invalid path test
    When the model and the uri are valid in invalid path test
    Then we get an exception when we want get the skolemizer in invalid path test

  Scenario: Get name value map
    Given the expected map in get name value map test
    When the result map is calculable in get name value map test
    Then the expected and the result map are equal in get name value map test

  Scenario Outline: Get literal
    Given the name path in get literal test, what is "<namePath>"
    When the expected literal in get literal test, what is "<expected>"
    Then the expected and the result literal are equal in get literal test

    Examples:
    | namePath            | expected |
    | title               | first    |
    | primaryTopic.title  | second   |
    | primaryTopic        | null     |
    | whatever            | null     |

  Scenario Outline: Get resource
    Given the name of result in get resource test, what is "<name>"
    When the expected resource in get resource test, what is "<expected>"
    Then the expected and the result resource are equal in get resource test

    Examples:
      | name                | expected |
      | primaryTopic        | second   |
      | title               | null     |
      | primaryTopic.title  | null     |
      | whatever            | null     |