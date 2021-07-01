package pages;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import common.CapturaPantalla;

public class PageLogin {
	private WebDriver driver;
	CapturaPantalla capturaPantalla = new CapturaPantalla();
	public PageLogin(WebDriver driver) {
		this.driver=driver;
	}
	
//	Login de la página
	public void login(String user, String pass) {
		driver.findElement(By.xpath("//*[@id=\"loginrut\"]")).sendKeys(user);
		driver.findElement(By.xpath("//*[@id=\"container_login_form\"]/div/div[2]/form/fieldset/div[2]/div/div[2]/div/input[1]")).sendKeys(pass);
		driver.findElement(By.xpath("//*[@id=\"container_login_form\"]/div/div[2]/form/fieldset/div[2]/div/div[3]/input")).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
}