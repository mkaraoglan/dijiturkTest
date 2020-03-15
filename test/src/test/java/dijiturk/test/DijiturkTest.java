package dijiturk.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

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
	
	@Before
	public void setup() {
		objDijiturk = new Dijiturk();
		WebDriver driver = objDijiturk.driver;
		firstName = DijiProperties.firstName;
		lastName = DijiProperties.lastName;
		emailOrPhone = DijiProperties.emailOrPhone;
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
    	objDijiturk.waitForUrlChange();
    	assertEquals("1", driver.findElement(By.xpath("//*[@id=\"ncol_ref\"]/tbody/tr[2]/td[2]/small")).getText());
    	objDijiturk.makePayment(driver, firstName, cardNumber);
    	assertEquals("The transaction has been denied",driver.findElement(By.cssSelector("#content > div > table:nth-child(6) > tbody > tr > td > h3")).getText());
    }
}
