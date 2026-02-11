package utils;

import org.testng.annotations.DataProvider;

public class TestData {

    @DataProvider(name = "loginData")
    public static Object[][] getLoginData() {
        return new Object[][]{
                {"tomsmith", "SuperSecretPassword!", true},   // valid
                {"tomsmith", "WrongPassword", false},         // invalid password
                {"wrongUser", "SuperSecretPassword!", false}, // invalid username
                {"", "", false},                              // empty fields
        };
    }
}

