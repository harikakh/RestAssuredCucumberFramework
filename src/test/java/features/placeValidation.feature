Feature: Validating Place API


@AddPlace
  Scenario Outline: Verify if Place is being successfully added
    Given Add place payload with "<name>" "<language>" "<address>"
    When User call "addPlaceAPI" with "Post" http request
    Then The API response is 200
    And "status" in response body is "OK"
    And "scope" in response body is "APP"
    And verify place_Id created maps to "<name>" using "getPlaceAPI"

    Examples:
      | name   | language | address       |
      | house  | English  | World center  |
      | ahouse | Spanish  | Global center |

  @DeletePlace
    Scenario: Verify Delete Place functionality is working
      Given Delete place payload
      When User call "deletePlaceAPI" with "Post" http request
      Then The API response is 200
