package test.script;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.openqa.jetty.html.Page;
import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import test.methods.*;

public class TestLogin extends WebFunction {

	@BeforeClass
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		baseUrl = "http://agi01.testapplication3.com/Login";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		TEST_DATA_FOLDER_PATH = new File(TESTDATA_FOLDER_NAME)
				.getAbsolutePath();
		SCREENSHOT_FOLDER_PATH = new File(SCREENSHOT_FOLDER_NAME)
				.getAbsolutePath();
	}

	@Test(priority = 0)
	public void OpenBrowser() throws Exception {
		driver.get(baseUrl);
	}

	@Test(dataProvider = "login", priority = 1)
	public void Login(final String uid, final String pwd,
			final String acceptance_criteria) throws Exception {

		type(USER_ID, uid);
		type(PASSWORD, pwd);
		click(SUBMIT);
		driver.getTitle();
		Thread.sleep(5000);
		
		//((driver.findElement(By.xpath("html/body/div[1]/div"))).getText().trim()).contentEquals((acceptance_criteria.trim()));
	}
	@AfterClass
	public void tearDown() throws Exception {
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			Assert.fail(verificationErrorString);
		}
		driver.quit();
	}

	@DataProvider(name = "login")
	public Object[][] get_login_test_input_data() throws Exception {
		Object[][] retObjectArr = getTableArray(TEST_DATA_FOLDER_PATH
				+ File.separator + "srs_test_data.xls", "srs_login", "users");

		return retObjectArr;
	}

}
