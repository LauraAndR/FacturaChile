package pages;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import common.CapturaPantalla;
import common.CrearDocEvidencia;
import common.Log;

public class PageFooter {
	private WebDriver driver;
	CapturaPantalla capturaPantalla = new CapturaPantalla();
	CrearDocEvidencia crearDocEvidencia = new CrearDocEvidencia();
	Log log = new Log();
	public PageFooter(WebDriver driver) {
		this.driver=driver;
	}
	
	public void clickPrevisualizacion(String caso) throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException{
//		PageAlerta pageAlerta = new PageAlerta(driver);
		int i=0;
		int j=0;
		do {
			driver.findElement(By.name("btn_previsualizar")).click();
			try {
				Thread.sleep(2000);
				//Defino la variable alerta y le paso el foco
				Alert alert = driver.switchTo().alert();
				String mensaje = alert.getText();
				//le doy click a aceptar en la alerta
				alert.accept();
				Thread.sleep(2000);
				System.out.println(mensaje);
				if(mensaje.contains("incorrecto")) {
					i=1;
					log.modificarArchivoLog(caso,"Error en el documento de referencia");
					driver.close();
				}
			} catch (Exception e) {
				i=1;
				j++;
				if(j==3) {
					System.out.println("No se puede Previsualizar documento, revisar datos");
					i=1;
				}
			}
		}while(i==0);
		crearDocEvidencia.modificarArchivoEvidencia(caso, "Click a botón Previsualizar");
		capturaPantalla.takeScreenShotTest(driver, "Boton_Previsualizar",caso);
	}
	
	public void clickEnviaraSunat(String caso) throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException{
		crearDocEvidencia.modificarArchivoEvidencia(caso, "Click a botón Enviar a SUNAT");
		capturaPantalla.takeScreenShotTest(driver, "Boton_Emitir",caso);
		driver.findElement(By.name("btn_emitir")).click();
		String url = driver.findElement(By.cssSelector("#emision_express_peru_modal_v2_modal > div > div > div.modal-body.container-fluid > h4 > a")).getText();
		log.modificarArchivoLog(caso, "URL: "+url);
		crearDocEvidencia.modificarArchivoEvidencia(caso, "URL: "+url);
		System.out.println(url);
		String originalWindow = driver.getWindowHandle();
		driver.findElement(By.cssSelector("#emision_express_peru_modal_v2_modal > div > div > div.modal-body.container-fluid > h4 > a")).click();
		url = url.replace("/v01/", "/traza/");
		System.out.println(url);
		Thread.sleep(5000);
		for (String windowHandle : driver.getWindowHandles()) {
		    if(!originalWindow.contentEquals(windowHandle)) {
		        driver.switchTo().window(windowHandle);
		        break;
		    }
		}
		driver.get(url);
		String documento="";
		int i=0;
		do{
			try {
				documento = driver.findElement(By.cssSelector("body > div > center > center > div > div:nth-child(1) > div > a")).getText();
				documento = documento.substring(documento.length()-12, documento.length());
				System.out.println(documento);
				log.modificarArchivoLog(caso, "Número de CPE: "+documento);
				crearDocEvidencia.modificarArchivoEvidencia(caso, "Número de CPE: "+documento);
				Thread.sleep(2000);
				log.modificarArchivoLog(caso, "Resultado: "+driver.findElement(By.xpath("//*[@id=\"example\"]/tbody/tr[3]/td[4]")).getText());
				log.modificarArchivoLog(caso, "ScreenShot de la traza");
				crearDocEvidencia.modificarArchivoEvidencia(caso, "Traza de CPE");
				capturaPantalla.takeScreenShotTest(driver, "Traza",caso);
				i=1;
			}catch (Exception e){
				driver.navigate().refresh();
			}
		}while(i==0);
		driver.close();
		driver.switchTo().window(originalWindow);
//		Actions action = new Actions(driver);
//		action.sendKeys(Keys.ESCAPE).build().perform();
//		Thread.sleep(3000);
	}
}
