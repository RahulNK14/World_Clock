package testFiles;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.annotations.*;

import POMfiles.BecognizantPOM;
import POMfiles.OneCognizantPOM;
import POMfiles.GooglePOM;
import testBase.BaseClass;
 
@Listeners({utilities.ExtentReportManager.class})
public class worldClockTest extends BaseClass{
	
	BecognizantPOM bePOM;
	GooglePOM googlePOM;
	OneCognizantPOM ocPOM;
	
	List<String> windowHandles;
	
	String beCognizant;
	String oneCognizant;
	
	Actions action;
	LocalTime time;
	
	@BeforeClass
	void poms() {
		bePOM = new BecognizantPOM(driver);
		ocPOM = new OneCognizantPOM(driver);
		logger.info("--------------------------- TEST BEGINS ---------------------------------");
	}
	
		
	@Test(priority = 1)
	void verifyUser() throws IOException, InterruptedException {
		
		String[] userDetailsAct = bePOM.userDetails();
		logger.info("Capturing the User Details");
		
		if(userDetailsAct != null) {
			logger.info("Successfully captured the User Details");
			Assert.assertTrue(true);
		}
		else {
			logger.info("Can't find the user details");
			Assert.fail();
		}
		
		
	}
	
	@Test(priority = 2)
	void verifyWorldClockText() throws InterruptedException, IOException {
		bePOM.scrollDown();
		logger.info("Scrolled down to the World Clock");
		TimeUnit.SECONDS.sleep(3);
	
		Assert.assertTrue(bePOM.worldClockHeading().isDisplayed());
	}
	
	
	@Test(priority = 3, dependsOnMethods = {"verifyWorldClockText"})
	void validateBangloreTimeZone() throws InterruptedException, IOException {
		
		TimeUnit.SECONDS.sleep(3);
		
		//STORING THE DETAILS OF BANGLORE
		String[] blrZone = bePOM.blrZoneDetails();
		logger.info("Verifying the current location details with the system details");
		Assert.assertTrue(blrZone[0].toLowerCase().contains("bangalore"));
		logger.info("Verified the current location");
		
		//GETTING THE CURRENT TIME
		LocalTime currTime = LocalTime.now();
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mma");
		String formattedTime = currTime.format(timeFormatter);
		
		//VERIFYING THE CURRENT TIME WITH BLR TIME
		Assert.assertTrue(formattedTime.toLowerCase().contains(blrZone[1].toLowerCase()));
		logger.info("Verified the current time");
		
		//GETTING THE CURRENT DATE
		LocalDate currDate = LocalDate.now();
        DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("E");
        String formattedDay = currDate.format(dayFormatter);
        
        //VERIFYING THE CURRENT DATE WITH BLR TIME
        Assert.assertTrue(blrZone[2].toLowerCase().contains(formattedDay.toLowerCase()));
        logger.info("Verified the current day");
        
        //VERIFYING THE DATE
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        String formattedDate = currDate.format(dateFormatter);
        Assert.assertTrue(blrZone[2].contains(formattedDate));
        logger.info("Verified the current date");
		
	}
	
	
	@Test(priority = 4, dependsOnMethods = {"verifyWorldClockText"})
	void validateLondonTimeZone() throws ParseException, IOException, InterruptedException {
		
		//STORING THE DETAILS OF LONDON
		String londonZone[] = bePOM.londonZoneDetails();
		logger.info("Verifying the "+ londonZone[0] + " details");
		googlePOM = new GooglePOM(driver);
		logger.info("Google tab opened");
		
		//GETTING THE WINDOW HANDLES
		Set<String> wH = driver.getWindowHandles();
		windowHandles = new ArrayList<String>(wH);
		
		driver.switchTo().window(windowHandles.get(1));
		
		//SEARCHING FOR GOOGLE TIME
		String[] sep = londonZone[0].split(",");
		googlePOM.searchData((sep[0]+" time now"));
		action = new Actions(driver);
		action.sendKeys(Keys.ENTER).perform();
		logger.info("search data entered");
		
		TimeUnit.SECONDS.sleep(3);
		captureScreen("London Time");
		
		//CHECKING FOR GOOGLE TIME EQUALS WITH LONDON TIME
		String gt = googlePOM.getTime();
		String googleTime = gt.replaceAll(" ", "");
		Assert.assertEquals(londonZone[1].toLowerCase(), googleTime.toLowerCase());
		logger.info("Verified the time");
		
		//VERIFYING THE DAY AND DATE OF LONDON
		String googleDate = getGoogleDate(googlePOM);
		Assert.assertTrue(londonZone[2].contains(googleDate));
		logger.info("Verified the day and date");
		
		//VERIFYING THE ACTUAL TIME DIFFERENCE
		String actdiff = londonZone[3].replaceAll(" ", "");
		String actTime = getActualTime(actdiff);
		Assert.assertTrue(actTime.contains(londonZone[1].toLowerCase()));
		logger.info("Verified the time difference");
		
		//SWITCH TO BECOGNIZANT - WORLD CLOCK SECTION
		driver.switchTo().window(windowHandles.get(0));
	}
	

	@Test(priority = 5, dependsOnMethods = {"verifyWorldClockText"})
	void validateNewYorkTimeZone() throws ParseException, IOException, InterruptedException {
		
		//STORING THE DETAILS OF NEW-YORK
		String nyrkZone[] = bePOM.nyrkZoneDetails();
		logger.info("Verifying the "+nyrkZone[0]+" details");
		driver.switchTo().window(windowHandles.get(1));
		
		//SEARCHING FOR GOOGLE TIME
		String[] sep = nyrkZone[0].split(",");
		googlePOM.searchData((sep[0]+" time now"));
		logger.info("search data entered in the google");
		action.sendKeys(Keys.ENTER).perform();
		
		TimeUnit.SECONDS.sleep(3);
		captureScreen("NewYork Time");
		
		//CHECKING FOR GOOGLE TIME EQUALS WITH NEW-YORK TIME
		String gt = googlePOM.getTime();
		String googleTime = gt.replaceAll(" ", "");
		Assert.assertEquals(nyrkZone[1].toLowerCase(), googleTime.toLowerCase());
		logger.info("Verified the time");
		
		//VERIFYING THE DAY AND DATE OF NEW-YORK
		String googleDate = getGoogleDate(googlePOM);
		Assert.assertTrue(nyrkZone[2].contains(googleDate));
		logger.info("Verified the day and date");
		
		//VERIFYING THE ACTUAL TIME DIFFERENCE
		String actdiff = nyrkZone[3].replaceAll(" ", "");
		String actTime = getActualTime(actdiff);
		Assert.assertTrue(actTime.contains(nyrkZone[1].toLowerCase()));
		logger.info("Verified the time difference");
		
		driver.close();
		logger.info("closed the google tab");
		
		//SWITCH TO BECOGNIZANT - WORLD CLOCK SECTION
		driver.switchTo().window(windowHandles.get(0));
	
	}
	
	@Test(priority = 6)
	@Parameters({"browser"})
	void validateHotApps() throws ParseException, InterruptedException, IOException {
		bePOM.oneCognizantClick();
		
		TimeUnit.SECONDS.sleep(3);
		
		//GETING BE-COGNIZANT HANDLE
		beCognizant = driver.getWindowHandle();
		logger.info("Got beCognizant Handle");
		
		//GETTING WINDOW HANDLES
		Set<String> windowHandles = driver.getWindowHandles();
		logger.info("Got Window Handles");
		
		//SWITCHINHG TO OTHER WINDOW
		for(String handle : windowHandles) {
			if(!handle.equals(beCognizant)) {
				driver.switchTo().window(handle);
				oneCognizant = driver.getWindowHandle();
				logger.info("Switched to OneC");
				break;
			}
		}
		
		TimeUnit.SECONDS.sleep(3);
		
		//CLICKING HOT APPS
		ocPOM.clickHotApps();
		logger.info("Hot Apps clicked");
		TimeUnit.SECONDS.sleep(7);
		
		//VALIDATING A-Z APPS ALPHABETS ARE DISPLAYED OR NOT
		int size = ocPOM.validateAlphabets();
		Assert.assertEquals(size, 27);
		logger.info("Alphabets Validated");
		
		captureScreen("A to Z alphabets");
		
	}
	

	@Test(priority = 7, dependsOnMethods = {"validateHotApps"})
	@Parameters({"browser"})
	void validateRandomApp(String value) throws InterruptedException, IOException {
		
		//GETTING A RANDOM CHAR
		Random r = new Random();
		char randomCh = (char)(r.nextInt(27) + 'A');
		String randomChar = String.valueOf(randomCh);
		
		boolean clicked = ocPOM.randomAlphabetClick(randomChar, value);
		Assert.assertEquals(clicked, true);
		logger.info("Alphabet clicked");
		
		captureScreen("Random App Details");
		
		TimeUnit.SECONDS.sleep(3);
		
		//CLICK ON THE RANDOM ALPHABET
		if(clicked) 
		{
			int size = ocPOM.appsWebelementSize();
			System.out.println("Number of apps : " + size);

			System.out.println("The apps are :");
			ocPOM.getAppsDetails(randomChar,value);
			
			TimeUnit.SECONDS.sleep(10);

		}
		
	}
}

