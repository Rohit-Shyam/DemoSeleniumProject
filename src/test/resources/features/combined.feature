Feature: Basic Test

  Scenario: Sample check
    Given I launch Wikipedia
    When I search for "Selenium (software)"
    Then I should be on a Wikipedia page

  Scenario: Task 2a - Navigate to React Docs
    Given I open ReactJS homepage
    When I click the Learn React link
    Then The Quick Start section should be visible

  Scenario: Task 2b - IMDb Search and Title Check
    Given I open IMDb homepage
    Then The IMDb title should match expected
    And IMDb search box should be visible
    When I search for the movie Inception on IMDb

  Scenario: Task 3 - Search JavaScript on Wikipedia
    Given I open Wikipedia homepage
    Then Wikipedia logo and search box should be visible
    When I search for "JavaScript" on Wikipedia

  Scenario: Task 4 - Perform login tests from CSV
    Given I perform login tests using CSV data

  Scenario: Task 5a - Login with valid credentials
    Given I open SauceDemo login page
    When I login with username "standard_user" and password "secret_sauce"
    Then I should see the products page

  Scenario: Task 5b - Login with locked out user
    Given I open SauceDemo login page
    When I login with username "locked_out_user" and password "secret_sauce"
    Then I should see error "Epic sadface: Sorry, this user has been locked out."

  Scenario: Task 5c - Login with invalid password
    Given I open SauceDemo login page
    When I login with username "standard_user" and password "wrong_password"
    Then I should see error "Epic sadface: Username and password do not match any user in this service"

  Scenario: Task 6a - Invalid login should show error message
    Given I open SauceDemo Task6 login page
    When I enter Task6 username "standard_user" and password "wrong_password"
    Then I should see Task6 error message "Epic sadface: Username and password do not match any user in this service"

  Scenario: Task 6b - Valid login should succeed
    Given I open SauceDemo Task6 login page
    When I enter Task6 username "standard_user" and password "secret_sauce"
    Then I should successfully login for Task6
