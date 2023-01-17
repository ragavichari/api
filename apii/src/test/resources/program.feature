@tag
Feature: Validate Post Request

  
  Scenario Outline: To create new record
    Given User with URL "{{baseurl}}/saveprogram"(Create new record).
    When I add sheetname"<SheetName>" and endpoint as "<saveprogram>"
        Then Status code should be 201 AND user should see a success message with all the fields Successfully created.

    Examples: 
      | SheetName               |
      | Sheet1                 |     
