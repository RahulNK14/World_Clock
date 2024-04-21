package POMfiles;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import testBase.BaseClass;
import utilities.ExcelUtility;

 
public class BecognizantPOM extends BasePage {
 
	WebDriverWait wait;
	JavascriptExecutor js;

	
	//Constructor
	public BecognizantPOM(WebDriver driver) {
		super(driver);
		wait = new WebDriverWait(driver,Duration.ofSeconds(30));
		driver.manage().window().maximize();
		js = (JavascriptExecutor)(driver);
		
	}
	
	//Locators
	@FindBy(id = "meInitialsButton") WebElement profile;
	@FindBy(id = "mectrl_currentAccount_primary") WebElement User;
	@FindBy(id = "mectrl_currentAccount_secondary") WebElement mail;
	@FindBy(xpath = "/html/body/div/div[1]/div/div[1]/div[3]/div[3]") WebElement profileClose;
	@FindBy(xpath="//*[@class='_8ZYZKvxC8bvw1xgQGSkvvA==']")
	WebElement userprofile;
	@FindBy(xpath="//*[@class='_8ZYZKvxC8bvw1xgQGSkvvA==']")
	WebElement profileXpathClose;
	@FindBy(id="mectrl_headerPicture")
	WebElement profileIdClose;
	
	
	public String[] userDetails() throws InterruptedException, IOException 
	{
		wait.until(ExpectedConditions.visibilityOf(profile));
//		TimeUnit.SECONDS.sleep(5);
		js.executeScript("arguments[0].click()", profile);
		TimeUnit.SECONDS.sleep(5);
		js.executeScript("arguments[0].click()", profile);
		TimeUnit.SECONDS.sleep(5);
		
		BaseClass.captureScreen("User Details");
		
		String[] userDetails = new String[2];
		userDetails[0] = User.getText();
		userDetails[1] = mail.getText();
		
		ExcelUtility.write("Profile Info", 0, 0, "USER NAME");
		ExcelUtility.write("Profile Info", 0, 1, "EMAIL");
		
		ExcelUtility.write("Profile Info", 1, 0, userDetails[0]);
		ExcelUtility.write("Profile Info", 1, 1, userDetails[1]);
		
		System.out.println("--------------- Personal Info ---------------");
		System.out.println("Name     : "+userDetails[0]+"\nMail Id  : "+userDetails[1]);
		System.out.println("---------------------------------------------");
		System.out.println("");
		
		profileClose.click();
		return userDetails;
	}

	
	@FindBy(xpath = "//strong[normalize-space()='See all']") WebElement scrollto;
	
	public void scrollDown() {
		
		js.executeScript("arguments[0].scrollIntoView();", scrollto);
 
	}
	
	@FindBy(xpath = "//span[text()='World Clock']") WebElement worldClockText;
	
	public WebElement worldClockHeading() throws IOException {
		BaseClass.captureScreen("World Clock");
		return worldClockText;
	}
	
	@FindBy(xpath = "(//div[@data-automation-id='clock-card-location'])[1]") WebElement currLoc;
	@FindBy(xpath = "(//div[@data-automation-id='clock-card-AM-PM-time'])[1]") WebElement currLocTime;
	@FindBy(xpath = "(//div[@data-automation-id='clock-card-time-offset']/parent::div//div[2])[1]") WebElement currLocDayNdDate;
	
	public String[] blrZoneDetails() throws IOException {
		
		String[] zone1 = new String[3];
		zone1[0] = currLoc.getText();
		zone1[1] = currLocTime.getText();
		zone1[2] = currLocDayNdDate.getText();
		
		ExcelUtility.write("Time Zone Details", 0, 0, "ZONE");
		ExcelUtility.write("Time Zone Details", 0, 1, "TIMING");
		
		ExcelUtility.write("Time Zone Details", 1, 0, zone1[0]);
		ExcelUtility.write("Time Zone Details", 1, 1, zone1[1]);
		return zone1;
		
	}
	
	@FindBy(xpath = "(//div[@data-automation-id='clock-card-location'])[2]") WebElement Loc1;
	@FindBy(xpath = "(//div[@data-automation-id='clock-card-AM-PM-time'])[2]") WebElement Loc1Time;
	@FindBy(xpath = "(//div[@data-automation-id='clock-card-time-offset']/parent::div//div[2])[2]") WebElement Loc1DayNdDate;
	@FindBy(xpath = "(//div[@data-automation-id='clock-card-time-offset'])[2]") WebElement Loc1Timediff;
	
	public String[] londonZoneDetails() throws IOException {
		
		String[] zone2 = new String[4];
		zone2[0] = Loc1.getText();
		zone2[1] = Loc1Time.getText();
		zone2[2] = Loc1DayNdDate.getText();
		zone2[3] = Loc1Timediff.getText();
		
		ExcelUtility.write("Time Zone Details", 2, 0, zone2[0]);
		ExcelUtility.write("Time Zone Details", 2, 1, zone2[1]);

		return zone2;
		
	}
	
	@FindBy(xpath = "(//div[@data-automation-id='clock-card-location'])[3]") WebElement Loc2;
	@FindBy(xpath = "(//div[@data-automation-id=\"clock-card-AM-PM-time\"])[3]") WebElement Loc2Time;
	@FindBy(xpath = "(//div[@data-automation-id='clock-card-time-offset']/parent::div//div[2])[3]") WebElement Loc2DayNdDate;
	@FindBy(xpath = "(//div[@data-automation-id='clock-card-time-offset'])[3]") WebElement Loc2Timediff;
	
	public String[] nyrkZoneDetails() throws IOException {
		
		String[] zone3 = new String[4];
		zone3[0] = Loc2.getText();
		zone3[1] = Loc2Time.getText();
		zone3[2] = Loc2DayNdDate.getText();
		zone3[3] = Loc2Timediff.getText();
		
		ExcelUtility.write("Time Zone Details", 3, 0, zone3[0]);
		ExcelUtility.write("Time Zone Details", 3, 1, zone3[1]);
		return zone3;
		
	}
	
//	@FindBy(xpath = "(//div[text()=\"OneCognizant\"])") WebElement oneCognizant;
	@FindBy(xpath = "//div[@id = 'QuicklinksItemTitle' and @title='OneCognizant']") WebElement oneCognizant;
	@FindBy(xpath = "//*[@id='CaptionElementView' and text() = 'Apps and Tools']") WebElement Apps;
	
	public void oneCognizantClick() throws InterruptedException 
	{
		TimeUnit.SECONDS.sleep(4);
		js.executeScript("arguments[0].scrollIntoView();", Apps);
		TimeUnit.SECONDS.sleep(4);
		oneCognizant.click();
	}

}