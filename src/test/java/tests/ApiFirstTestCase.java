package tests;

import org.apache.log4j.PropertyConfigurator;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import pages.ApiResponses;
import utils.Utils;

public class ApiFirstTestCase {
    private ApiResponses apiPage;

    @BeforeClass
    public void setup() {
        PropertyConfigurator.configure("src/main/resources/log4j.properties");
        RestAssured.baseURI = "http://api.zippopotam.us/de/bw/stuttgart";
        apiPage = new ApiResponses();
    }

    @Test(priority = 1)
    public void verifyingThatStatusCodeIs200() {
        Utils.verifyStatusCode(apiPage.getResponse(""));
    }

    @Test(priority = 2)
    public void verifyingThatContentTypeIsJson() {
        Utils.verifyContentType(apiPage.getResponse(""));
    }

    @Test(priority = 3)
    public void verifyingThatResponseTimeIsBelow1s() {
        Utils.verifyResponseTime(apiPage.getResponse(""));
    }

    @Test(priority = 4)
    public void verifyingThatCountryIsGermanyAndStateIsBadenWuerttemberg() {
        Utils.verifyCountryAndState(apiPage.getResponse(""), "Germany", "Baden-Württemberg");
    }

    @DataProvider(name = "placeNameAndPostCodeTestData")
    public Object[][] placeNameTestData() {
        return new Object[][]{{"70597", "Stuttgart Degerloch"}};
    }

    @Test(dataProvider = "placeNameAndPostCodeTestData", priority = 5)
    public void verifyingThatThePlaceNameForPostCodeIsAsExpected(String postcode, String expectedPlaceName) {
        Response response = apiPage.getResponse("");
        Utils.verifyPlaceNameForPostCode(response, postcode, expectedPlaceName);
    }

}
