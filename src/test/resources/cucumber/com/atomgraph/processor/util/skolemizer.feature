Feature: Skolemizer working well

  Scenario: Skolemizer inheritence case
    Given the expected super skolemizer
    When the actual skolemizer is its inherit
    Then they are equal skolemizers