package pages;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import common.CapturaPantalla;
import common.CrearDocEvidencia;
import common.FechaActual;
import common.Log;

public class PageEmisionDTE {
	private WebDriver driver;
	Log log = new Log();
	CrearDocEvidencia crearDocEvidencia = new CrearDocEvidencia();
	CapturaPantalla capturaPantalla = new CapturaPantalla();
	
	public PageEmisionDTE(WebDriver driver) {
		this.driver=driver;
	}
	
	
	// new implementation **************
	
	public void FechaEmision (String caso) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				FechaActual fechaActual = new FechaActual();
				String fecha = fechaActual.FechaHoy();
				driver.findElement(By.id("formEmitirdocumento_fechaEmision")).sendKeys(fecha);
				Thread.sleep(1000);
				String texto ="Ingreso Fecha Desde";
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				//driver.findElement(By.id("formEmitirdocumento_fechaEmision")).sendKeys(Keys.TAB);

				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible ingresar Fecha Desde");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(2000);
	}
	
	public void FechaVencimiento (String caso) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				FechaActual fechaActual = new FechaActual();
				String fecha = fechaActual.FechaVencimiento();
				driver.findElement(By.id("formEmitirdocumento_fechaEmision")).sendKeys(fecha);
				Thread.sleep(1000);
				String texto ="Ingreso Fecha Emisión";
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible ingresar Fecha Emisión");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(2000);
	}
	
	public void SeleccionarFormaPago (String caso, String opcion) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				Select tipoDocumento = new Select (driver.findElement(By.id("formEmitirdocumento_forma_pago")));
				tipoDocumento.selectByVisibleText(opcion);
				String texto ="Seleccion Forma de Pago";	
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible Seleccionar Forma de Pago");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(2000);
	}
	
	
	public void SeleccionarTipoCompra (String caso, String opcion) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				Select tipoDocumento = new Select (driver.findElement(By.id("formEmitirdocumento_TpoTranCompra")));
				tipoDocumento.selectByVisibleText(opcion);
				String texto ="Seleccion Tipo de Compra";	
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible Seleccionar Tipo de Compra");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(2000);
	}
	
	
	public void SeleccionarTipoVenta (String caso, String opcion) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				Select tipoDocumento = new Select (driver.findElement(By.id("formEmitirdocumento_TpoTranVenta")));
				tipoDocumento.selectByVisibleText(opcion);
				String texto ="Seleccion Tipo de Venta";	
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible Seleccionar Tipo de Venta");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(2000);
	}
	
	public void IngresoRutCliente (String caso, String rut) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				driver.findElement(By.id("formEmitirdocumento_rutReceptor")).sendKeys(rut);
				String texto ="Ingresar RUT";
				driver.findElement(By.id("btnBuscarRut")).click();
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible Ingresar RUT");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(3000);
	}
	
	public void AgregarProducto1 (String caso, String codigo, String cant) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				driver.findElement(By.id("txt_codpro")).sendKeys(codigo);
				Thread.sleep(2000);
				driver.findElement(By.id("txt_codpro")).sendKeys(Keys.TAB);
				Thread.sleep(5000);
				
				driver.findElement(By.id("txt_cantprod")).sendKeys(cant);
				Thread.sleep(2000);
				driver.findElement(By.id("txt_cantprod")).sendKeys(Keys.TAB);
				Thread.sleep(2000);
				
				driver.findElement(By.id("btn_addTogrid")).click();
				
				String texto ="Ingresar Producto";
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible Ingresar Producto");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(3000);
	}
	
	public void BtnEmitirFacturaAfecta (String caso) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				driver.findElement(By.id("btn_firmar_factura")).click();
				
				String texto ="Clic botón Emitir Factura Afecta";
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible hacer clic botón Emitir Factura Afecta");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(5000);
	}
}
