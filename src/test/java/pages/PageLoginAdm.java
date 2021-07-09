package pages;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import common.CapturaPantalla;
import common.CrearDocEvidencia;
import common.Log;

public class PageLoginAdm {
	private WebDriver driver;
	Log log = new Log();
	CrearDocEvidencia crearDocEvidencia = new CrearDocEvidencia();
	CapturaPantalla capturaPantalla = new CapturaPantalla();
	public PageLoginAdm(WebDriver driver) {
		this.driver=driver;
	}
	
	public void ClickIngresarLogin(String caso) {
		int i=0;
		int j=0;
		do {
			try {
				driver.findElement(By.id("id-login")).click();
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				System.out.println();
				j++;
				if(j==3) {
					System.out.println("No fue posible ingresar al login");
					i=1;
				}
			}
		}while(i==0);
	}
	
	public void LoginCorrecto(String caso, String rut, String pass) throws InterruptedException, IOException, InvalidFormatException {
		int i=0;
		do {
			try {
				driver.findElement(By.id("rut")).sendKeys(rut);
				driver.findElement(By.id("password")).sendKeys(pass);
				driver.findElement(By.id("log-button")).click();
				//Corregir la toma del mensaje de advertencia
				String mensaje = driver.findElement(By.id("log-msg_error")).getText();
				if(mensaje.contains("inválido")) {
					log.modificarArchivoLog(caso, "Ingreso Datos Personales Incorrectos");
					crearDocEvidencia.modificarArchivoEvidencia(caso, "Ingreso Datos Personales Incorrectos");
					capturaPantalla.takeScreenShotTest(driver, "Ingreso_Datos_Personales_Incorrectos", caso);
				}
				else {
					log.modificarArchivoLog(caso, "Login OK");
					crearDocEvidencia.modificarArchivoEvidencia(caso, "Login OK");
					capturaPantalla.takeScreenShotTest(driver, "Login_Ok", caso);
					BtnAutorizar(caso);
				}
				
				i=1;
			}catch (Exception e) {
				String texto ="Se intentará por Identidad Digital";
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				LoginIdentidadDigital(caso, rut, pass);
				i=1;
			}
		}while(i==0);		
	}
	
	public void LoginIdentidadDigital(String caso, String rut, String pass) throws IOException, InvalidFormatException, InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				driver.findElement(By.id("taxpayer_id")).sendKeys(rut);
				driver.findElement(By.id("password")).sendKeys(pass);
				driver.findElement(By.id("submit")).click();
				String texto ="Login por Identidad Digital";
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				BtnAutorizar(caso);
				i=1;
			} catch(Exception e) {
				j++;
				if(j==3) {
					String texto ="No fue posible el login";
					log.modificarArchivoLog(caso,texto);
					crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
					texto=texto.replace(" ","_");
					capturaPantalla.takeScreenShotTest(driver,texto, caso);
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(2000);
	}
	
	public void BtnAutorizar(String caso) throws IOException, InvalidFormatException, InterruptedException {
		try {
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.findElement(By.xpath("//*[@id=\"submit\"]")).click();
			String texto ="Click al botón Autorizar";
			log.modificarArchivoLog(caso,texto);
			crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
			texto=texto.replace(" ","_");
			capturaPantalla.takeScreenShotTest(driver,texto, caso);
		} catch(Exception e) {
			String texto ="No aparece el botón Autorizar";
			log.modificarArchivoLog(caso,texto);
			crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
			texto=texto.replace(" ","_");
			capturaPantalla.takeScreenShotTest(driver,texto, caso);
		}
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
}
