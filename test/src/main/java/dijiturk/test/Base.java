package dijiturk.test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Base {
	public static WebDriver driver;
	public static WebDriverWait wait;
	
	public static void connectURL(WebDriver driver, String URL) {
		driver.get(URL);
	}
	
	//initialize driver
	public static void initializeDriver() throws IOException {
		
		Properties prop = new Properties();
		FileInputStream fs = new FileInputStream("/test/src/main/java/resources/data.properties");
		prop.load(fs);
		String browserName = prop.getProperty("browser");
		
		if(browserName.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", "/test/driver/chromedriver.exe");
			driver = new ChromeDriver();
		}

		driver.manage().window().maximize();
		
		connectURL(driver, prop.getProperty("url"));
		waitForLoading();

		int currentWindowWidth = driver.manage().window().getSize().getWidth();
		
		if (currentWindowWidth > 991) {
			driver.findElement(By.name("Subscribe")).click();
		}
		else {
			driver.findElement(By.cssSelector("button[data-target='#navbarCollapse']")).click();
			driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
			driver.findElement(By.xpath("//*[@id=\"navbarCollapse\"]/ul[1]/li/a")).click();
		}

		waitForLoading();
		
		driver.findElement(By.cssSelector("body > div.layout-wide > div.container-onboarding.step1 > div:nth-child(4)")).click();

		driver.findElement(By.cssSelector("a[data-btn-index='1']")).click();
		
		waitForUrlChange();
		waitForLoading();

		String firstName = prop.getProperty("firstName");
		String lastName = prop.getProperty("lastName");
		String emailOrPhone = prop.getProperty("emailOrPhone");
		String password = prop.getProperty("password");
		WebElement formRegister = driver.findElement(By.cssSelector("#form-register"));
		formRegister.findElement(By.cssSelector("input[name='FirstName']")).sendKeys(firstName);
		formRegister.findElement(By.cssSelector("input[name='LastName']")).sendKeys(lastName);
		formRegister.findElement(By.cssSelector("input[name='EmailOrPhone']")).sendKeys(emailOrPhone);
		formRegister.findElement(By.cssSelector("input[name='Password']")).sendKeys(password);
		formRegister.findElement(By.cssSelector("input[type='submit']")).click();
		
		waitForUrlChange();
		waitForLoading();
		
		driver.findElement(By.id("close")).click();

		driver.findElement(By.cssSelector("label[for='checkTerms']")).click();

		driver.findElement(By.cssSelector("input[type='submit'][name='pay-now']")).click();
		
	
		waitForUrlChange();
		String totalCharge = driver.findElement(By.xpath("//*[@id=\"ncol_ref\"]/tbody/tr[2]/td[2]/small")).getText();
		System.out.println(totalCharge);
		driver.findElement(By.id("Ecom_Payment_Card_Name")).sendKeys(firstName);
		
		String cardNumber = prop.getProperty("cardNumber");
		driver.findElement(By.id("Ecom_Payment_Card_Number")).sendKeys(cardNumber);
		
		driver.findElement(By.id("Ecom_Payment_Card_ExpDate_Month")).click();
		driver.findElement(By.xpath("//*[@id=\"Ecom_Payment_Card_ExpDate_Month\"]/option[5]")).click();
		
		driver.findElement(By.id("Ecom_Payment_Card_ExpDate_Year")).click();
		driver.findElement(By.xpath("//*[@id=\"Ecom_Payment_Card_ExpDate_Year\"]/option[5]")).click();
		
		driver.findElement(By.id("Ecom_Payment_Card_Verification")).sendKeys("123");
		

		driver.findElement(By.id("submit3")).click();

		String result = driver.findElement(By.cssSelector("#content > div > table:nth-child(6) > tbody > tr > td > h3")).getText();
		System.out.println(result);
		System.out.println("yüklendi");
	}

	
	public static void waitForLoading() {
		wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.attributeToBe(By.id("loading"), "style", "display: none;"));
	}
	
	public static void waitForUrlChange() {
		wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(driver.getCurrentUrl())));
	}
    
}
