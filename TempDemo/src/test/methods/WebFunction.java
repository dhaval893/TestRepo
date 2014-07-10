package test.methods;

import java.io.File;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;

import test.variables.*;

public class WebFunction extends LoginObject {
	
	protected final String BLANK = "" ;
	protected final String SPACE = " " ;
	protected String test_name;
	protected String test_id;
	
	protected final String SCREENSHOT_FOLDER_NAME = "screenshots" ;
	protected final String TESTDATA_FOLDER_NAME = "test_data" ;
	
	// TEST_DATA_FOLDER_PATH
	protected static String TEST_DATA_FOLDER_PATH;
	// SCREENSHOT_FOLDER_PATH
	protected static String SCREENSHOT_FOLDER_PATH;
	// BROWSER	
	protected static String BROWSER;
	// ACTIVE_TEST_NAME
	protected static String ACTIVE_TEST_NAME;
	// ACTIVE_TEST_ID
	protected static String ACTIVE_TEST_ID;
	// MESSAGE FOR FAILED TEST
	protected final String FAILURE_REPORT_LINK_MESSAGE=" view screen-shot.";
	
	
	
	
	/**
	 * Verifies if element is present on the page. Returns true if present, else false.
	 * 
	 * @param locator Element locator
	 * @return
	 */
	public final boolean isElementPresent(final String locator) {
		
		WebElement _element = this.findElement(locator);
		if(_element != null ) return true;
		else return false;
		
	}
	
	/**
	 * This method finds argument element based on locator strategy 
	 * 
	 * @param elementName Element locator
	 * @return WebElement 
	 */
	public final WebElement findElement(final String elementName){
		
		String locator;
		locator = elementName;
		
		int count = 0; 
		while (count < 4){
		    try {
		    	
		    	if(locator.startsWith("link=") || locator.startsWith("LINK=")){
					locator = locator.substring(5);		//remove "link=" from locator
					
					if(locator.contains(SPACE)) return driver.findElement(By.partialLinkText(locator));
					return driver.findElement(By.linkText(locator));
					
				} else if(locator.startsWith("id=")){
					
					locator = locator.substring(3);		//remove "id=" from locator
					return driver.findElement(By.id(locator));
					
				} else if(locator.startsWith("xpath=")){
					locator = locator.substring(6);
					System.out.println(locator);
						return driver.findElement(By.xpath(locator));
					
				} else if(locator.startsWith("css=")){
					
					locator = locator.substring(4);		//remove "css=" from locator
					return driver.findElement(By.cssSelector(locator));
					
				} else if(locator.startsWith("name=")){
					
					locator = locator.substring(5);		//remove "name=" from locator
					return driver.findElement(By.name(locator));
					
				} else{
					
					return driver.findElement(By.id(locator));
				
				}
					
		    } catch (StaleElementReferenceException e){
		       
		    	e.toString();
		    	count = count+1;
		    	System.out.println("Trying["+ count+"] to recover from a stale element :" + e.getMessage());
		       
		    } catch( Exception e ) {
		    	
		    	return null;
		    	
		    } 
		   count = count+4;
		} 
		return null;
		
	}
	
	
	public final void type(final String locator, final String text) {
		this.findElement(locator).clear();
		this.findElement(locator).sendKeys(text);
	}
	
	
	
	/**
	 * This method will wait for at-least 60 seconds for an element to be loaded.
	 * 
	 * @param locator
	 * @throws InterruptedException
	 */
	public final void  waitForElementPresent(final String locator ) throws InterruptedException {
		
		for (int second = 0;second >= 60; second++) {
        	try { 
        		if (isElementPresent(locator)) 
        			break; 
        		} 
        	catch (Exception e) {}
        	Thread.sleep(1000L);
        }
	}
	
	/**
	 * Click on element
	 * @param locator Element locator
	 */
	public final void click (final String locator) throws InterruptedException {
		//waitForElementPresent(locator);
		this.findElement(locator).click();
		
	}
	
	
	
	
	/**
	 * This method parses test data from input XLS file
	 * 
	 * @param xlsFilePath
	 * @param sheetName
	 * @param tableName
	 * @return String[][]
	 * @throws Exception
	 */
	public final String[][] getTableArray(final String xlsFilePath, final String sheetName,
			final String tableName) throws Exception {
	
		String[][] tableArray = null;

		System.out.println("XLS File:" + xlsFilePath + 
				 " SheetName:" + sheetName +
				 " TableName:" + tableName ) ;
		// Get reference to XLS file
		Workbook workbook = Workbook.getWorkbook( new File( xlsFilePath ) ) ;
		// System.out.println("File created") ;
		// Get reference to specific sheet
		Sheet sheet = workbook.getSheet( sheetName ) ;
		System.out.println(sheet.getName());

		int startRow, startCol, endRow, endCol, ci, cj;

		Cell tableStart = sheet.findCell( tableName );

		startRow = tableStart.getRow()   ;
		startCol = tableStart.getColumn();

		Cell tableEnd = sheet.findCell( tableName, startCol + 1, startRow + 1,
				                        100, 64000, false);
		endRow = tableEnd.getRow();

		endCol = tableEnd.getColumn();

		//System.out.println(" Start Row : " + startRow + " End Row : " + endRow
		//		+ " Start Col : " + startCol + " End Col :" + endCol);
		
		tableArray = new String[endRow - startRow - 1][endCol - startCol - 1];
		ci = 0;

		for (int i = startRow + 1; i < endRow; i++, ci++) {
			cj = 0;

			for (int j = startCol + 1; j < endCol; j++, cj++) {
				tableArray[ci][cj] = sheet.getCell(j, i).getContents();
				
				System.out.println(tableArray[ci][cj]) ;
			}
		}

		return tableArray;
	}

	
	
	/**
	 * This method returns true if argument text is present on page else false
	 * 
	 * @param text
	 * @return
	 */
	public final boolean isTextPresent(final String text ) {					
		
		boolean isTextOnThePage;
		try{
			isTextOnThePage = isElementPresent("//*[contains(text(),'" + text + "')]");
		}
		catch(Exception e){
			isTextOnThePage = false;
		}
		return isTextOnThePage;
		
	}
	
	
	/**
	 * Throws Error object if text is not present.
	 * @param text
	 */
	public final void assertTextPresent(final String text){
		Assert.assertTrue(isTextPresent(text));
	}
	/*******************************************************
	 * Below are the static methods
	 * *****************************************************/
	/**
	 * This method logs messages to reporter object
	 * @param logMessage
	 */
	public final static void log(final String logMessage){
		 Reporter.log(logMessage);
	 }
	 
	
}





