Feature: Test Home of the Application

  Background:
    Given User sets base URI as "https://jsonplaceholder.typicode.com"

  @Smoke
  Scenario: Validate Home Page API
    When User sends GET request to "/posts/1"
    Then Response status code should be 200
    And Response should contain "expedita"
