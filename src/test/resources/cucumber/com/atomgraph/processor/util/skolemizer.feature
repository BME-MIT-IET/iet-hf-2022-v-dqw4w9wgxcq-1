Feature: Skolemizer working well

  Scenario: Skolemizer inheritance case
    Given the expected skolemizer of inheritance test, which is a superclass
    When the actual skolemizer is its inherit
    Then the super and the inherit skolemizer are equal

  Scenario: Skolemizer no path case
    Given the expected uri of no path skolemizer, what is null
    When the actual uri has no path
    Then the null and the pathless uri are equal

  Scenario: Skolemizer invalid type class case
    Given the expected uri of invalid class type skolemizer, what is null
    When the actual uri's class type is invalid
    Then the null and the invalid class type skolemizer are equal

  Scenario: Skolemizer invalid super class case
    Given the expected uri of invalid super class skolemizer test
    When the actual uri is inheritance of this invalid superclass
    Then the invalid superclass uri and its inherit are equal

  Scenario: Skolemizer absolute path case
    Given the expected uri of absolute path skolemizer test
    When the actual uri is gave of absolute path skolemizer test
    Then the expected and the actual uri in absolute path test are equal

  Scenario: Skolemizer primary topic case
    Given the expected uri of primary topic skolemizer test
    When the actual uri is gave of primary topic skolemizer test
    Then the expected and the actual uri in primary topic test are equal

  Scenario: Skolemizer has value restriction path case
    Given the expected uri of has value restriction path skolemizer test
    When the actual uri is gave of has value restriction path skolemizer test
    Then the expected and the actual uri in has value restriction path test are equal

  Scenario: Skolemizer all values from restriction path case
    Given the expected uri of all values from restriction path skolemizer test
    When the actual uri is gave of all values from restriction path skolemizer test
    Then the expected and the actual uri in all values from restriction path test are equal

  Scenario: Skolemizer fragment case
    Given the expected uri of fragment skolemizer test
    When the actual uri is gave of fragment skolemizer test
    Then the expected and the actual uri in fragment test are equal

  Scenario: Skolemizer invalid path case
    Given an invalid resource of invalid path test
    When the model and the uri are valid of invalid path test
    Then we get an exception when we want get the skolemizer in invalid path test

  Scenario: Skolemizer get name value map case
    Given the expected map of get name value map skolemizer test
    When the result map is calculable of get name value map skolemizer test
    Then the expected and the result map in get name value map test are equal

  Scenario Outline: Skolemizer get literal case
    Given the name path of get literal skolemizer test, what is "<namePath>"
    When the expected literal of get literal skolemizer test, what is "<expected>"
    Then the expected and the result literal in get literal test are equal

    Examples:
    | namePath            | expected |
    | title               | first    |
    | primaryTopic.title  | second   |
    | primaryTopic        | null     |
    | whatever            | null     |

  Scenario Outline: Skolemizer get resource case
    Given the name of result of get resource skolemizer test, what is "<name>"
    When the expected resource of get resource skolemizer test, what is "<expected>"
    Then the expected and the result resource in get resource test are equal

    Examples:
      | name                | expected |
      | primaryTopic        | second   |
      | title               | null     |
      | primaryTopic.title  | null     |
      | whatever            | null     |