package tests;

import pages.LoginPage;
import utils.ExtentReportManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import io.github.bonigarcia.wdm.WebDriverManager;

@Listeners(utils.TestListener.class)
public class LoginTest {

    public WebDriver driver;
    LoginPage loginPage;
    ExtentReports extent;
    ExtentTest test;

    @BeforeSuite
    public void setUpReport() {
        extent = ExtentReportManager.getExtentReports();
    }

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://the-internet.herokuapp.com/login");
        loginPage = new LoginPage(driver);
    }

    @Test
    public void TC_Login_01_ValidLogin() {
        test = extent.createTest("TC_Login_01 - Valid Login");
        loginPage.enterUsername("tomsmith");
        loginPage.enterPassword("SuperSecretPassword!");
        loginPage.clickLogin();
        Assert.assertTrue(loginPage.getMessage().contains("You logged into a secure area!"));
        test.pass("Valid login test passed");
    }

    @Test
    public void TC_Login_02_InvalidLogin() {
        test = extent.createTest("TC_Login_02 - Invalid Login");
        loginPage.enterUsername("tomsmith");
        loginPage.enterPassword("WrongPassword");
        loginPage.clickLogin();
        Assert.assertTrue(loginPage.getMessage().contains("Your password is invalid!"));
        test.pass("Invalid login test passed");
    }
    @Test(dataProvider = "loginExcelData", dataProviderClass = utils.TestData.class)
    public void TC_Login_Excel_DDT(String username, String password, String isValidStr) {

        boolean isValid = Boolean.parseBoolean(isValidStr);

        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLogin();

        if (isValid) {
            Assert.assertTrue(loginPage.getMessage().contains("You logged into a secure area!"));
        } else {
            Assert.assertTrue(loginPage.getMessage().contains("invalid"));
        }
    }


    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    @AfterSuite
    public void tearDownReport() {
        extent.flush();
    }
}
