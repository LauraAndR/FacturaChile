package pages;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import common.CapturaPantalla;
import common.CrearDocEvidencia;
import common.Log;

public class PageEscritorio {
	private WebDriver driver;
	Log log = new Log();
	CrearDocEvidencia crearDocEvidencia = new CrearDocEvidencia();
	CapturaPantalla capturaPantalla = new CapturaPantalla();
	
	public PageEscritorio(WebDriver driver) {
		this.driver=driver;
	}
	
	
	// new implementation **************
	
	public void BarraMenu (String caso, String opcion) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				int hijos = driver.findElements(By.xpath("//*[@id=\"panel-lis-ul\"]/descendant::li")).size();
				int h=1;
				int g=0;
				do {
					System.out.println(driver.findElement(By.xpath("//*[@id=\"panel-lis-ul\"]/descendant::li["+h+"]")).getText());
					if(driver.findElement(By.xpath("//*[@id=\"panel-lis-ul\"]/descendant::li["+h+"]")).getText().equals(opcion)) {
						driver.findElement(By.xpath("//*[@id=\"panel-lis-ul\"]/descendant::li["+h+"]")).click();
						h=hijos;
					}
					else {
						g++;
						if(g>hijos) {
							System.out.println("No se encuentra opci�n");
							h=hijos;
						}
					}
					h++;
				}while(h<hijos);
				Thread.sleep(3000);
				String texto ="Seleccionar Opcion de Men�";
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible Seleccionar Opci�n");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(4000);
	}
	
	
	public void SeleccionarTipoDocumento (String caso, String opcion) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				Select tipoDocumento = new Select (driver.findElement(By.id("formEmitirdocumento_tipofactura")));
				tipoDocumento.selectByVisibleText(opcion);
				String texto ="Seleccion Tipo Documento";	
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible Seleccionar Tipo Documento");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(2000);
	}
}
