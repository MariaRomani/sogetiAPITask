package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.log4j.PropertyConfigurator;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.ApiResponses;
import utils.Utils;


public class ApiSecondTestCase {
    private ApiResponses apiPage;

    @BeforeClass
    public void setup() {
        PropertyConfigurator.configure("src/main/resources/log4j.properties");
        this.apiPage = new ApiResponses();
        RestAssured.baseURI = "http://api.zippopotam.us";


    }

    @DataProvider(name = "countryForPostalCodeForPlaceName")
    public Object[][] testData() {
        return new Object[][]{
                {"us", "90210", "Beverly Hills"},
                {"us", "12345", "Schenectady"},
                {"ca", "B2R", "Waverley"}
        };
    }

    @Test(dataProvider = "countryForPostalCodeForPlaceName")
    public void verifyThatThePlaceNameForEachInputOfCountryAndPostalCode(String country, String postalCode, String expectedPlaceName) {

        Response response = ApiResponses.getResponseWithPathParams(country, postalCode);
        Utils.verifyStatusCode(response);
        Utils.verifyContentType(response);
        Utils.verifyResponseTime(response);
        Utils.verifyPlaceNameForCountryAndPostalCode(response, country, postalCode, expectedPlaceName);
    }
}