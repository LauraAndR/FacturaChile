package pages;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import common.CapturaPantalla;
import common.CrearDocEvidencia;

public class PageAgregarPropiedadesAdicionales {
	private WebDriver driver;
	CapturaPantalla capturaPantalla = new CapturaPantalla();
	CrearDocEvidencia crearDocEvidencia = new CrearDocEvidencia();
	public PageAgregarPropiedadesAdicionales(WebDriver driver) {
		this.driver=driver;
	}
	
	public void agregarItem(String caso) throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException
	{
		Select item = new Select (driver.findElement(By.id("item")));
		Select tipo = new Select (driver.findElement(By.id("tipo")));
		
//		Item 1		
		item.selectByValue("52");
		Thread.sleep(1000);
		tipo.selectByValue("4000");
		Thread.sleep(1000);
		driver.findElement(By.name("valor")).sendKeys("CL");
		clickAgregar();
		
//		Item 2
		item.selectByValue("52");
		Thread.sleep(1000);
		tipo.selectByValue("4001");
		Thread.sleep(1000);
		driver.findElement(By.name("valor")).sendKeys("CL");
		clickAgregar();
		
//		Item 3
		item.selectByValue("52");
		Thread.sleep(1000);
		tipo.selectByValue("4002");
		Thread.sleep(1000);
		driver.findElement(By.name("valor")).sendKeys("2021-03-01");
		clickAgregar();
		
//		Item 4
		item.selectByValue("52");
		Thread.sleep(1000);
		tipo.selectByValue("4003");
		Thread.sleep(1000);
		driver.findElement(By.name("valor")).sendKeys("2021-03-02");
		clickAgregar();
		
//		Item 5
		item.selectByValue("52");
		Thread.sleep(1000);
		tipo.selectByValue("4004");
		Thread.sleep(1000);
		driver.findElement(By.name("valor")).sendKeys("2021-03-08");
		clickAgregar();
		
//		Item 6
		item.selectByValue("52");
		Thread.sleep(1000);
		tipo.selectByValue("4005");
		Thread.sleep(1000);
		driver.findElement(By.name("valor")).sendKeys("6");
		clickAgregar();
		
//		Item 7
		item.selectByValue("52");
		Thread.sleep(1000);
		tipo.selectByValue("4006");
		Thread.sleep(1000);
		driver.findElement(By.name("valor")).sendKeys("2021-03-09");
		clickAgregar();
		
//		Item 8
		item.selectByValue("52");
		Thread.sleep(1000);
		tipo.selectByValue("4007");
		Thread.sleep(1000);
		driver.findElement(By.name("valor")).sendKeys("Prueba QA Automation");
		clickAgregar();
		
//		Item 9
		item.selectByValue("52");
		Thread.sleep(1000);
		tipo.selectByValue("4008");
		Thread.sleep(1000);
		driver.findElement(By.name("valor")).sendKeys("7");
		clickAgregar();
		
//		Item 10
		item.selectByValue("52");
		Thread.sleep(1000);
		tipo.selectByValue("4009");
		Thread.sleep(1000);
		driver.findElement(By.name("valor")).sendKeys("12345678");
		clickAgregar();
		
		crearDocEvidencia.modificarArchivoEvidencia(caso, "Se Agregan Propiedades Adicionales");
		capturaPantalla.takeScreenShotTest(driver, "Propiedades_Adicionales",caso);
	}
	
	public void clickAgregar() throws InterruptedException {
		int i=0;
		int j=0;
		PageAlerta pageAlerta = new PageAlerta(driver);
		do {
			try {
				Thread.sleep(1000);
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[2]/div[16]/form/div[11]/input")).click();
				Thread.sleep(2000);
				pageAlerta.alertaPostDetalle();
				Thread.sleep(2000);
				i=1;
			} catch (Exception e) {
				pageAlerta.alertaManejoError();
				j++;
				if(j==3) {
					System.out.println("No se puede agregar");
					i=1;
				}
			}
		}while(i==0);		
	}
}
