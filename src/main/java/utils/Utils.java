package utils;

import io.restassured.response.Response;
import org.testng.Assert;
import org.apache.log4j.Logger;

import static org.testng.Assert.assertEquals;

public class Utils {
    private static final Logger logger = Logger.getLogger(Utils.class);

    public static void verifyStatusCode(Response response) {
        logger.info("Verifying status code is 200");
        int actualStatusCode = response.getStatusCode();
        if (actualStatusCode == 200) {
            logger.info("Status code is 200 ** assertion passed **");
        } else {
            logger.error("Status code assertion failed. Expected status code: 200, Actual status code: " + actualStatusCode);
            Assert.fail("Response status code is not 200");
        }

    }

    public static void verifyContentType(Response response) {
        logger.info("Verifying that the content type is JSON");
        String actualContentType = response.getContentType();
        if ("application/json".equals(actualContentType)) {
            logger.info("Content type is JSON ** assertion passed **");
        } else {
            logger.error("Content type assertion failed. Expected content type: application/json, Actual content type: " + actualContentType);
            Assert.fail("Content type is not JSON");
        }

    }

    public static void verifyResponseTime(Response response) {
        logger.info("Verifying response time");
        long actualTimeInMS = response.getTime();
        if (actualTimeInMS < 1000) {
            logger.info("Response time is below 1s ** assertion passed **. Actual response time: " + actualTimeInMS);
        } else {
            String errorMessage = "Response time assertion failed. Expected response time to be less than 1000 ms. Actual response time: " + actualTimeInMS + " ms";
            logger.error(errorMessage);
            Assert.fail(errorMessage);
        }

    }

    public static void verifyCountryAndState(Response response, String expectedCountry, String expectedState) {
        logger.info("Verify in Response - That country is **Germany** and the state is **Baden-Württemberg**");
        String actualCountry = response.path("country");
        String actualState = response.path("state");

        if (actualCountry.equals(expectedCountry) && actualState.equals(expectedState)) {
            logger.info("Country and state assertions passed. Actual country: " + actualCountry + ", Actual state: " + actualState);
        } else {
            String errorMessage = "Country or state assertion failed. Expected country: " + expectedCountry + ", Expected state: " + expectedState +
                    ". Actual country: " + actualCountry + ", Actual state: " + actualState;
            logger.error(errorMessage);
            Assert.fail(errorMessage);
        }
    }

    public static void verifyPlaceNameForPostCode(Response response, String postcode, String expectedPlaceName) {
        String placeName = response.path("places.find { it.'post code' == '" + postcode + "' }.'place name'");

        assertEquals(placeName, expectedPlaceName, "Place name for postcode " + postcode + " is not as expected");
        logger.info("Actual Place Name for Postal Code " + postcode + ": *** " + placeName + " ***");
        logger.info("Place name for postcode " + postcode + " assertion passed.");
    }

    public static void verifyPlaceNameForCountryAndPostalCode(Response response, String country, String postalCode, String expectedPlaceName) {
        String placeName = response.path("places[0].'place name'");
        Assert.assertEquals(placeName, expectedPlaceName, "Place name for country " + country + " and postal code " + postalCode + " is not as expected");
        logger.info("Actual Place Name for Country " + country + " and Postal Code " + postalCode + ": *** " + placeName + " ***");
        logger.info("Place name for country " + country + " and postal code " + postalCode + " assertion passed.");
    }


}
