package test.variables;

import org.openqa.selenium.WebDriver;


public class LoginObject {
	
	  // Driver settings
	  public WebDriver driver;
	  public String baseUrl;
	  public StringBuffer verificationErrors = new StringBuffer();
	  
	  
	  // Map Object with variable
	// USER ID TEXT BOX  
		protected String USER_ID = "id=UserName" ;
		// PASSWORD TEXT BOX
		protected String PASSWORD = "id=Password" ;
		
		protected String SUBMIT = "xpath=.//*[@id='SignInTroubleForm']/div[4]/button" ;
		//protected String SUBMIT = "xpath=//button[@type='submit']" ;

}
