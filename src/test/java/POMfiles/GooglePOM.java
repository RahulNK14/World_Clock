package POMfiles;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class GooglePOM extends BasePage{
	
//	WebDriver driver;
	JavascriptExecutor js;
	
	//contructor
	public GooglePOM(WebDriver driver) {
		super(driver);
		js = (JavascriptExecutor)driver;
		js.executeScript("window.open('https://www.google.com/','_blank');");
	}
	
	@FindBy(name = "q") WebElement searchBar;
	
	public void searchData(String loc) {
		searchBar.clear();
		searchBar.sendKeys(loc);
	}
	
	@FindBy(xpath = "//div[@class='gsrt vk_bk FzvWSb YwPhnf']") WebElement googleTime;
	
	public String getTime() {
		return googleTime.getText();
	}
	
	@FindBy(xpath = "(//span[@class='KfQeJ'])[1]") WebElement googleDate;
	
	public String getDate() {
		return googleDate.getText();
	}
	
	
	
}
