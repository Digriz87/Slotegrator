Feature: Login -> Select Players in menu -> Setup Filter in Dashboard
  As a user,
  After correct form filling with correct credentials,
  I want to login with email and password
  After Selecting Users/Players tab in menu,
  I want to be at players page
  After setup filter for Players
  I need to see that all list of Players are sorted

  @selenium
  Scenario: Logging in to Admin Dashboard with valid credentials and setup filter for Players

    Given I am at the login page
    When I logging in as user "admin1" with password "[9k<k8^z!+$$GkuP"
    And I click the Sign in button
    Then I should be in Dashboard page
    When I click on the Users tab in menus
    When I click on the Players sub-tab
    And I select filter by Active of Players list
    Then I checking that filter by Active is working