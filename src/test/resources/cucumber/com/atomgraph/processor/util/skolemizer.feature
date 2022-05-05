Feature: Skolemizer working well

  Scenario: Skolemizer inheritance case
    Given the expected skolemizer of inheritance test, which is a superclass
    When the actual skolemizer is its inherit
    Then the super and the inherit skolemizer are equal

  Scenario: Skolemizer no path case
    Given the expected skolemizer of no path skolemizer, what is null
    When the actual skolemizer has no path
    Then the null and the pathless skolemizer are equal

  Scenario: Skolemizer invalid class type case
    Given the expected skolemizer of invalid class type skolemizer, what is null
    When the actual skolemizer's class type is invalid
    Then the null and the invalid class type skolemizer are equal

  Scenario: Skolemizer invalid super class case
    Given the expected skolemizer of invalid super class skolemizer test
    When the actual skolemizer is inheritance of this invalid superclass
    Then the invalid superclass skolemizer and its inherit are equal

  Scenario: Skolemizer absolute path case
    Given the expected skolemizer of absolute path skolemizer test
    When the actual skolemizer is gave of absolute path skolemizer test
    Then the expected and the actual skolemizer in absolute path test are equal

  Scenario: Skolemizer primary topic case
    Given the expected skolemizer of primary topic skolemizer test
    When the actual skolemizer is gave of primary topic skolemizer test
    Then the expected and the actual skolemizer in primary topic test are equal