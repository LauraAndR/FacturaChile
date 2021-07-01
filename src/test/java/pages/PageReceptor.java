package pages;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import common.CapturaPantalla;
import common.CrearDocEvidencia;
import common.Log;

public class PageReceptor {
	private WebDriver driver;
	CapturaPantalla capturaPantalla = new CapturaPantalla();
	Log log = new Log();
	CrearDocEvidencia crearDocEvidencia = new CrearDocEvidencia();
	public PageReceptor(WebDriver driver) {
		this.driver=driver;
	}
	PageAlerta pageAlerta = new PageAlerta(driver);
	
	public void completarReceptor (String caso) throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException {
		int i=0;
		int j=0;
		do {
			try {
				String rucEmisor;
				rucEmisor=driver.findElement(By.xpath("//*[@id=\"form_params\"]/div[5]/h4")).getText();
				rucEmisor=rucEmisor.substring(12,rucEmisor.length());
				driver.findElement(By.name("ruc_dni")).click();
				Thread.sleep(1000);
				driver.findElement(By.name("ruc_dni")).sendKeys(rucEmisor);
				Thread.sleep(1000);
				driver.findElement(By.name("ruc_dni")).sendKeys(Keys.TAB);
				Thread.sleep(5000);
				driver.findElement(By.name("lista_correos_receptor_agregar")).click();
				Thread.sleep(2000);
				crearDocEvidencia.modificarArchivoEvidencia(caso, "Datos de Receptor");
				capturaPantalla.takeScreenShotTest(driver, "Datos_Receptor",caso);
				i=1;
			} catch(Exception e) {
				pageAlerta.alertaManejoError();
				j++;
				if(j==3) {
					System.out.println("No se puede agregar datos de receptor");
					i=1;
				}
			}
		}while(i==0);
	}
	
			
		public void datosReceptor (String caso, String razonSocial, String mailReceptor) throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException {
			
			driver.findElement(By.name("razon_social")).clear();
			Thread.sleep(2000);
			driver.findElement(By.name("razon_social")).sendKeys(razonSocial);
			Thread.sleep(2000);
			driver.findElement(By.name("lista_correos_receptor")).clear();
			Thread.sleep(2000);
			driver.findElement(By.name("lista_correos_receptor")).sendKeys(mailReceptor);
			Thread.sleep(2000);
			driver.findElement(By.name("lista_correos_receptor")).sendKeys(Keys.TAB);
			Thread.sleep(2000);
			crearDocEvidencia.modificarArchivoEvidencia(caso, "Datos de Receptor");
			capturaPantalla.takeScreenShotTest(driver, "Datos_Receptor",caso);
		}
	
			
//		public void seleccionTipoDocumentoReceptor (String tipoDocumento, String caso) throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException {
//			int i=0;
//			int j=0;
//			do {
//				try {
//					Select tipodeDocumento = new Select (driver.findElement(By.id("tipo_documento")));
//					switch (tipoDocumento){
//					case "REG. UNICO DE CONTRIBUYENTES":
//						tipodeDocumento.selectByValue("6");
//						break;
//					case "DOC.TRIB.NO.DOM.SIN.RUC":
//						tipodeDocumento.selectByValue("0");
//						break;
//					case "DOC. NACIONAL DE IDENTIDAD":
//						tipodeDocumento.selectByValue("1");
//						break;
//					case "CARNET DE EXTRANJEERIA":
//						tipodeDocumento.selectByValue("4");
//						break;
//					case "PASAPORTE":
//						tipodeDocumento.selectByValue("7");
//						break;
//					case "CED. DIPLOMATICA DE IDENTIDAD":
//						tipodeDocumento.selectByValue("A");
//						break;
//					default:
//						System.out.println("Tipo de Documento Receptor Valor inválido");
//						break;
//					}
//					Thread.sleep(2000);
//					log.modificarArchivoLog(caso, "Tipo Cargo: "+tipoDocumento);
//					crearDocEvidencia.modificarArchivoEvidencia(caso, "Seleccion Tipo Documento Receptor");
//					capturaPantalla.takeScreenShotTest(driver, "Seleccion_Tipo_Documento", caso);
//					i=1;
//				} catch (Exception e){
//					pageAlerta.alertaManejoError();
//					j++;
//					if(j==3) {
//						System.out.println("No fue posible seleccionar Tipo Documento Receptor");
//						i=1;
//					}
//				}
//			}while(i==0);
//		}
		
		public void seleccionTipoDocumentoReceptor (String caso, String tipoDocumento) throws InterruptedException {
			int i=0;
			int j=0;
			do {
				try {
					Select rol = new Select (driver.findElement(By.cssSelector("select[name='tipo_documento']")));
					rol.selectByVisibleText(tipoDocumento); 
					String texto ="Seleccion tipo de documento";
					log.modificarArchivoLog(caso,texto);
					crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
					texto=texto.replace(" ","_");
					capturaPantalla.takeScreenShotTest(driver,texto, caso);
					i=1;
				}catch (Exception e) {
					// TODO: handle exception
					j++;
					if(j==3) {
						System.out.println("No fue posible seleccionar tipo de documento");
						i=1;
					}
				}
			}while(i==0);
			Thread.sleep(3000);
		}
	
	public void completarRazonSocial(String razonSocial, String caso) throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException {
		int i=0;
		int j=0;
		do {
			try {
				driver.findElement(By.name("razon_social")).click();
				driver.findElement(By.name("razon_social")).sendKeys(razonSocial);
				Thread.sleep(2000);
				crearDocEvidencia.modificarArchivoEvidencia(caso, "Datos de Receptor");
				capturaPantalla.takeScreenShotTest(driver, "Datos_Receptor", caso);
				i=1;
			} catch(Exception e) {
				pageAlerta.alertaManejoError();
				j++;
				if(j==3) {
					System.out.println("No se puede agregar datos de receptor");
					i=1;
				}
			}
		}while(i==0);
	}
	
	public void completarReceptorBoleta(String razonSocial, String caso) throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException {
		int i=0;
		int j=0;
		do {
			try {
				Select tipoDocumento = new Select (driver.findElement(By.id("tipo_documento")));
				tipoDocumento.selectByValue("1");
				Thread.sleep(2000);
				driver.findElement(By.name("ruc_dni")).click();
				driver.findElement(By.name("ruc_dni")).sendKeys("12345678");
				Thread.sleep(2000);
				driver.findElement(By.name("ruc_dni")).sendKeys(Keys.TAB);
				Thread.sleep(2000);
				driver.findElement(By.name("razon_social")).sendKeys(razonSocial);
				Thread.sleep(2000);
				driver.findElement(By.name("lista_correos_receptor_agregar")).click();
				PageAlerta pageAlerta = new PageAlerta(driver);
				pageAlerta.alertaManejoError();
				Thread.sleep(2000);
				crearDocEvidencia.modificarArchivoEvidencia(caso, "Datos de Receptor");
				capturaPantalla.takeScreenShotTest(driver, "Datos_Receptor", caso);
				i=1;
			} catch(Exception e) {
				pageAlerta.alertaManejoError();
				j++;
				if(j==3) {
					System.out.println("No fue posible completar los datos del receptor");
					i=1;
				}
			}
		}while(i==0);
	}
	
	public void botonAgregarReceptordivVeintiTres(String caso) throws InterruptedException, IOException, InvalidFormatException{
		PageAlerta pageAlerta= new PageAlerta(driver);
		int i=0;
		int j=0;
		do {
			try {
				driver.findElement(By.xpath("//*[@id=\"form_params\"]/div[23]/div/div[2]/input")).click();
				Thread.sleep(2000);          
				pageAlerta.alertaPostDetalle();
				crearDocEvidencia.modificarArchivoEvidencia(caso, "Se agrega datos Receptor");
				capturaPantalla.takeScreenShotTest(driver, "Agregar_Datos_Receptor",caso);
				i=1;
			} catch (Exception e) {
				pageAlerta.alertaManejoError();
				j++;
				if(j==3) {
					System.out.println("No se puede agregar datos de Receptor");
					i=1;
				}
			}
		}while(i==0);
	}
	
//	public void botonAgregarReceptordivVeintiTres(String caso) throws InterruptedException, IOException, InvalidFormatException{
//		PageAlerta pageAlerta= new PageAlerta(driver);
//		int i=0;
//		int j=0;
//		do {
//			try {
//				driver.findElement(By.name("btn_agregar")).click();
//				Thread.sleep(2000);          
//				pageAlerta.alertaPostDetalle();
//				crearDocEvidencia.modificarArchivoEvidencia(caso, "Se agrega datos Receptor");
//				capturaPantalla.takeScreenShotTest(driver, "Agregar_Datos_Receptor",caso);
//				i=1;
//			} catch (Exception e) {
//				pageAlerta.alertaManejoError();
//				j++;
//				if(j==3) {
//					System.out.println("No se puede agregar datos de Receptor");
//					i=1;
//				}
//			}
//		}while(i==0);
//	}
}
