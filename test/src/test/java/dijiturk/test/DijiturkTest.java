package dijiturk.test;

import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Unit test for simple App.
 */
public class DijiturkTest 
{

	public WebDriver driver;
	Dijiturk objDijiturk;	
	String firstName;
	String lastName;
	String emailOrPhone;
	String password;
	String cardNumber;
	protected String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 10) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }
	@Before
	public void setup() {
		System.setProperty("webdriver.chrome.driver", ".\\driver\\chromedriver.exe");
		driver = new ChromeDriver();
		objDijiturk = new Dijiturk(driver);
		firstName = DijiProperties.firstName;
		lastName = DijiProperties.lastName;
		emailOrPhone = getSaltString() + "@" + getSaltString() + ".com";
		password = DijiProperties.password;
		cardNumber = DijiProperties.cardNumber;
	}
    @Test
    public void shouldAnswerWithTrue()
    {
    	objDijiturk.initializeDriver();
    	objDijiturk.connectURL(driver, DijiProperties.Url);
    	objDijiturk.subscribe(driver);
    	objDijiturk.register(driver);
    	objDijiturk.createAccaunt(driver, firstName, lastName, emailOrPhone, password);
    	objDijiturk.ignoreEmailVerification(driver);
    	objDijiturk.waitForUrlChange(driver);
    	System.out.println(driver.findElement(By.xpath("//*[@id=\"ncol_ref\"]/tbody/tr[2]/td[2]/small")).getText());
    	assertEquals("1.00 THB", driver.findElement(By.xpath("//*[@id=\"ncol_ref\"]/tbody/tr[2]/td[2]/small")).getText());
    	objDijiturk.makePayment(driver, firstName, cardNumber);
    	assertEquals("The transaction has been denied",driver.findElement(By.cssSelector("#content > div > table:nth-child(6) > tbody > tr > td > h3")).getText());
    }
}
