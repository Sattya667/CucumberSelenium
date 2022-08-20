Feature: Customers

  Background: backround steps
    Given User Launch Chrome browser
    When User opens URL "http://admin-demo.nopcommerce.com/login"
    And User enters Email as "admin@yourstore.com" and Password as "admin"
    And Click on Login
    Then User can view Dashboad


  @sanity
  Scenario Outline: Add new Customer
    Given user uses following environment details
      | Environment  | <Env>      |
      | Browser      | <Browser>  |
      | Application  | <AppName>  |
    When User click on customers Menu
    And click on customers Menu Item
    And click on Add new button
    Then User can view Add new customer page
    When User enter customer info
    And click on Save button
    Then User can view confirmation message "The new customer has been added successfully."
  Examples:
    |Env    | Browser | AppName      |
    |prod   | Chrome  | AddNewCust   |
    |dev    | Chrome  | AddNewCust   |

  @regression
  Scenario: Search Customer by EMailID
    When User click on customers Menu
    And click on customers Menu Item
    And Enter customer EMail
    When Click on search button
    Then User should found Email in the Search table


  @regression
  Scenario: Search Customer by Name
    When User click on customers Menu
    And click on customers Menu Item
    And Enter customer FirstName
    And Enter customer LastName
    When Click on search button
    Then User should found Name in the Search table
