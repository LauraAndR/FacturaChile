package pages;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
							System.out.println("No se encuentra opción");
							h=hijos;
						}
					}
					h++;
				}while(h<hijos);
				Thread.sleep(3000);
				String texto ="Seleccionar Opcion de Menú";
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible Seleccionar Opción");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(4000);
	}
	
	public void menuEmisionIndividual (String caso, String especificacion) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				
				driver.findElement(By.xpath("//*[@id=\"panel-lis-ul\"]/li[5]/ul/li[3]/a")).click();
				Thread.sleep(1000);          
				driver.findElement(By.xpath("//*[text()= '"+ especificacion + "']")).click();//para enviar un texto a un campo y lo puedan seleccionar
				Thread.sleep(1000); 
				String texto ="Seleccion Emision Individual";
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible seleccionar Emision Individual");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(2000);
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
	
	
	
	public void ClickCubo (String caso) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				driver.findElement(By.id("myApps")).click();
				String texto ="Click en Cubo";
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible dar clic en Cubo");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(3000);
	}
	
	public void SeleccionarOpcionCuboDTE (String caso) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				Thread.sleep(3000);
				driver.findElement(By.xpath("//*[@id=\"myAppsDialog\"]/div/div/div[2]/div/div/div[2]/a/div/div/div/div")).click();
				String texto ="Seleccionar opción en Cubo";
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible seleccionar opción en Cubo");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(3000);
	}
	
	public List<WebElement> BuscarTipoDocumento () throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				Select tipoDocumento = new Select (driver.findElement(By.id("formEmitirdocumento_tipofactura")));
				i=1;
				return tipoDocumento.getOptions();
				
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible encontrar la opción de Tipo Documento");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(2000);
		return null;
	}
	
	public List<WebElement> BuscarAgregarProducto () throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				Select tipoProducto = new Select (driver.findElement(By.id("btns_agregar_producto")));
				i=1;
				return tipoProducto.getOptions();
				
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible ingresar código Producto");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(2000);
		return null;
	}

}
