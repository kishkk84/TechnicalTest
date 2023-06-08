@all @car
Feature: Car valuation
  As a customer I want to see what is my car worth

  @valuation
  Scenario: Value my car
    Given the customer is on cazoo's value my car page
    When the customer performs quick valuation
    Then the car registration match results are displayed