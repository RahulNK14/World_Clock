package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.*;

import POMfiles.GooglePOM;

public class BaseClass {
	
	static public WebDriver driver;
	Properties properties;
	
	static public Logger logger;
	
	@BeforeClass
	@Parameters({"browser"})
	public void setup(String value) {
		
		try {
			FileReader file = new FileReader(".//src/test/resources/config.properties");
			properties = new Properties();
			properties.load(file);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
		//loading log4j2 file
		logger = LogManager.getLogger(this.getClass());
		
		if(value.equalsIgnoreCase("chrome")) {
			driver = new ChromeDriver();
		}
		else if(value.equalsIgnoreCase("edge")){
			driver = new EdgeDriver();
		}
		
		driver.get(properties.getProperty("appURL"));
		
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
	    driver.manage().deleteAllCookies();
		
	}
	
	public static String captureScreen(String tname) throws IOException {
		
		Date date = new Date();  
	 
	    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM hh;mm;ss");  
	    String strDate = formatter.format(date);    
				
		TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
		File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
		
		String targetFilePath = System.getProperty("user.dir")+"\\screenshots\\" + strDate + "_" + tname + ".png";
		File targetFile = new File(targetFilePath);
		
		sourceFile.renameTo(targetFile);
			
		return targetFilePath;

	}
	
	public String getGoogleDate(GooglePOM googlePOM) throws ParseException {
		
		String gd = googlePOM.getDate();
		SimpleDateFormat inpFormat = new SimpleDateFormat("d MMMM yyyy");
		SimpleDateFormat outFormat = new SimpleDateFormat("M/d/yyyy");
		Date date = inpFormat.parse(gd);
		String googleDate = outFormat.format(date);
		
		return googleDate;
	}
	
	public String getActualTime(String actdiff) throws ParseException {
		
		int hours = Integer.parseInt(actdiff.substring(1, actdiff.indexOf('h')));
		int mins = Integer.parseInt(actdiff.substring(actdiff.indexOf('h')+1, actdiff.indexOf('m')));	
		LocalTime time = LocalTime.now();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("hh:mma");
		String actTime = time.minusMinutes(hours*60 + mins).format(dtf);
		
		return actTime;
	}
	
	@AfterClass
	public static void tearDown()
	{
		driver.quit();
	}

}
