package POMfiles;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import utilities.ExcelUtility;


public class OneCognizantPOM extends BasePage{
	
	WebDriver driver;
	JavascriptExecutor js;
	
	//Constructor
	public OneCognizantPOM(WebDriver driver) {
		super(driver);
		js = (JavascriptExecutor)driver;
	}
	
	//Locators
	@FindBy(xpath = "//div[@class=\"charAZBtn\" or @class=\"charAZBtn charAZBtnNoResult\"]") List<WebElement> alphabets;
	@FindBy(xpath = "//div[@class = 'viewAllHotAppsBtn']") WebElement viewAll;
	@FindBy(xpath = "//div[@class = 'col s6 m3 l2 appStoreAppDiv']") List<WebElement> apps;
	
	
	//Actions Class
	public void clickHotApps() throws InterruptedException {
		
		TimeUnit.SECONDS.sleep(3);

		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		
		TimeUnit.SECONDS.sleep(3);
		
		viewAll.click();
	}
	
	public int validateAlphabets() {
		
		return alphabets.size();
	}
	

	
	public boolean randomAlphabetClick(String ch, String browser) throws IOException 
	{
		for(int i=0; i<alphabets.size(); i++) {
			WebElement alphabetElem = alphabets.get(i);
			String alphabet =  alphabetElem.getText();
			
			if(alphabet.equals(ch) && !alphabet.equals("X") && !alphabet.equals("Y")) 
			{
				System.out.println("Random Alphabet is : " + alphabet);
				alphabetElem.click();
				
				if(browser.equalsIgnoreCase("chrome")) 
				{
					ExcelUtility.write("Random Alphabet in Chrome ", 0, 0, "RANDOM ALPHABET");
					ExcelUtility.write("Random Alphabet in Chrome ", 1, 0, alphabet);
				}
				else if(browser.equalsIgnoreCase("edge")) 
				{
					ExcelUtility.write("Random Alphabet in Edge ", 0, 0, "RANDOM ALPHABET");
					ExcelUtility.write("Random Alphabet in Edge ", 1, 0, alphabet);
				}
				return true;
			}
		}
		return false;
	}
	
	
	public void getAppsDetails(String random, String browser) throws IOException {
		
		
		if(browser.equalsIgnoreCase("chrome")) 
		{
			ExcelUtility.clearExcel("App details in Chrome");
			ExcelUtility.write("App details in Chrome", 0, 0, "APP DETAILS");

			int row = 1;
			for(int i=0; i<apps.size(); i++) 
			{
				 System.out.println(apps.get(i).getText());
				 ExcelUtility.write("App details in Chrome", row, 0, apps.get(i).getText());
				 row++;
			}
		}

		else {
			ExcelUtility.clearExcel("App details in Edge");
			ExcelUtility.write("App details in Edge", 0, 0, "APP DETAILS");

			int row = 1;
			 for(int i=0; i<apps.size(); i++) {
				 
				 System.out.println(apps.get(i).getText());
				 ExcelUtility.write("App details in Edge", row, 0, apps.get(i).getText());
				 row++;

			 }

		}
		
		
	}
	
	public int appsWebelementSize() {
		return apps.size();
	}
	
		
}
