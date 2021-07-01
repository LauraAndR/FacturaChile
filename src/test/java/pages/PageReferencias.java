package pages;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import common.CapturaPantalla;
import common.CrearDocEvidencia;

public class PageReferencias {
	private WebDriver driver;
	CapturaPantalla capturaPantalla = new CapturaPantalla();
	CrearDocEvidencia crearDocEvidencia = new CrearDocEvidencia();
	public PageReferencias(WebDriver driver) {
		this.driver=driver;
	}
	
	public void folioReferencia(String folio,String fecha,String cp) throws FileNotFoundException, InvalidFormatException, InterruptedException, IOException {
		try {
			String tipo=folio.substring(0, 1);
			if(tipo.equals("F")) {
				folioReferenciaFactura(folio,fecha, cp);
			}
			else {
				folioReferenciaBoleta(folio,fecha, cp);
			}
		}catch (Exception e) {
			System.out.println("Folio contiene un Dato no correcto");
		}
	}
	
	public void folioReferenciaFactura (String folio,String fecha, String caso) throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException {
		PageAlerta pageAlerta = new PageAlerta(driver);
		int i=0;
		int j=0;
		do {
			try {
				Select formatoCPE = new Select(driver.findElement(By.id("referencias_formato_cpe")));
				Select tipoDocumento = new Select(driver.findElement(By.id("referencias_tipo_documento")));
				
				formatoCPE.selectByValue("E");
				tipoDocumento.selectByValue("01");
				driver.findElement(By.name("referencias_folio")).sendKeys(folio);
				Thread.sleep(2000);
				driver.findElement(By.name("referencias_descripcion")).sendKeys("PRUEBAQA");
				Thread.sleep(2000);
				driver.findElement(By.name("referencias_fecha")).sendKeys(fecha);
				Thread.sleep(2000);
				crearDocEvidencia.modificarArchivoEvidencia(caso, "Se Agregan Referencias a CPE");
				capturaPantalla.takeScreenShotTest(driver, "Datos_Referencia_Factura", caso);
				i=1;
			} catch(Exception e) {
				pageAlerta.alertaManejoError();
				j++;
				if(j==3) {
					System.out.println("No se puede agregar datos de referencia");
					i=1;
				}
			}
		}while(i==0);
	}
	
	public void folioReferenciaBoleta (String folio,String fecha, String caso) throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException {
		PageAlerta pageAlerta = new PageAlerta(driver);
		int i=0;
		int j=0;
		do {
			try {
				Select formatoCPE = new Select(driver.findElement(By.id("referencias_formato_cpe")));
				Select tipoDocumento = new Select(driver.findElement(By.id("referencias_tipo_documento")));
				
				formatoCPE.selectByValue("E");
				tipoDocumento.selectByValue("03");
				driver.findElement(By.name("referencias_folio")).sendKeys(folio);
				Thread.sleep(2000);
				driver.findElement(By.name("referencias_descripcion")).sendKeys("PRUEBAQA");
				Thread.sleep(2000);
				driver.findElement(By.name("referencias_fecha")).sendKeys(fecha);
				Thread.sleep(2000);
				crearDocEvidencia.modificarArchivoEvidencia(caso, "Se Agregan Referencias a CPE");
				capturaPantalla.takeScreenShotTest(driver, "Datos_Referencia_Boleta", caso);
				i=1;
			} catch(Exception e) {
				pageAlerta.alertaManejoError();
				j++;
				if(j==3) {
					System.out.println("No se puede agregar datos de referencia");
					i=1;
				}
			}
		}while(i==0);
	}
}
