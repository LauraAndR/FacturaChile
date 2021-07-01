package pages;

import java.io.FileNotFoundException;
import java.io.IOException;
//import java.util.Calendar;
//import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import common.CapturaPantalla;
import common.CrearDocEvidencia;
import common.Log;

public class PageEmitidos {
	private WebDriver driver;
	CapturaPantalla capturaPantalla = new CapturaPantalla();
	CrearDocEvidencia crearDocEvidencia = new CrearDocEvidencia();
	Log log = new Log();
	public PageEmitidos(WebDriver driver) {
		this.driver=driver;
	}
	
	public void clickEmitidos(String caso) throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException {
		driver.findElement(By.id("badge_294")).click();
		crearDocEvidencia.modificarArchivoEvidencia(caso, "Click a Emitidos");
		capturaPantalla.takeScreenShotTest(driver, "Emitidos", caso);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(By.name("busqueda_avanzada")).click();
		crearDocEvidencia.modificarArchivoEvidencia(caso, "Click a Búsqueda Avanzada");
		capturaPantalla.takeScreenShotTest(driver, "Busqueda Avanzada", caso);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		// Función que permite buscar el CPE creado en el flujo
		PageEmisionBuscarCpe pageEmisionBuscarCpe = new PageEmisionBuscarCpe(driver);
		pageEmisionBuscarCpe.buscarCpeEmitido();
		
		// Almacena el ID de la ventana original
		String originalWindow = driver.getWindowHandle();
		String nroDocumento = driver.findElement(By.xpath("//*[@id='0']/td[8]")).getText();
		// Almacena el número de CPE para el Caso de Prueba ejecutado
		crearDocEvidencia.modificarArchivoEvidencia(caso, "Número de documento generado: "+nroDocumento);
		System.out.println(nroDocumento);
		// Da click en el icono de traza correspondiente al CPE creado en el flujo
		driver.findElement(By.id("1TRAZA")).click();
		crearDocEvidencia.modificarArchivoEvidencia(caso, "Click a Traza de CPE");
		capturaPantalla.takeScreenShotTest(driver, "Click Traza", caso);
		
		// Espera a la nueva ventana o pestaña
		Thread.sleep(5000);
		for (String windowHandle : driver.getWindowHandles()) {
		    if(!originalWindow.contentEquals(windowHandle)) {
		        driver.switchTo().window(windowHandle);
		        break;
		    }
		}
		
		// Espera a que la nueva ventana cargue su contenido
		log.modificarArchivoLog(caso, "ScreenShot de la traza");
		crearDocEvidencia.modificarArchivoEvidencia(caso, "Traza de CPE");
		capturaPantalla.takeScreenShotTest(driver, "Traza",caso);
		driver.close();
		driver.switchTo().window(originalWindow);
	}
}
