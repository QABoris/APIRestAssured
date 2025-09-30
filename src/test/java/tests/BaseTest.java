package tests;

import config.ApiConfig;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    @BeforeClass
    public void setUp() {
        ApiConfig.configureRestAssured();
    }

    @BeforeMethod
    public void beforeMethod() {
        // Any setup needed before each test method
    }
}