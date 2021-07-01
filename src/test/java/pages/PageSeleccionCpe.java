package pages;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import common.*;

public class PageSeleccionCpe {
	private WebDriver driver;
	CapturaPantalla capturaPantalla = new CapturaPantalla();
	Log log = new Log();
	CrearDocEvidencia crearDocEvidencia = new CrearDocEvidencia();
	public PageSeleccionCpe(WebDriver driver) {
		this.driver=driver;
	}
	PageAlerta pageAlerta = new PageAlerta(driver);
	
	public void clickEmisionExpress(String caso) throws FileNotFoundException, InvalidFormatException, IOException, InterruptedException {
		log.crearArchivoLog(caso);
		crearDocEvidencia.crearArchivoEvidencia(caso);
		int i=0;
		int j=0;
		int hijos=0;
		int op=0;
		String opcion="";
		do {
			try {
				hijos=driver.findElements(By.xpath("//*[@id=\"panel-lis-ul\"]/child::li")).size();
				System.out.println(hijos);
				for(int h=1;h<=hijos;h++) {
					opcion=driver.findElement(By.xpath("//*[@id=\"panel-lis-ul\"]/child::li["+h+"]")).getText();
					if(opcion.equalsIgnoreCase("Emisión Express")) {
						driver.findElement(By.xpath("//*[@id=\"panel-lis-ul\"]/child::li["+h+"]")).click();
						driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
						crearDocEvidencia.modificarArchivoEvidencia(caso, "Click a Emisión Express");
						capturaPantalla.takeScreenShotTest(driver, "Click_Emisión_Express", caso);
//						System.out.println(h);
						op=h;
					}
				}
				
//				Click a subopción Facturas, Boletas ...
				driver.findElement(By.xpath("//*[@id=\"panel-lis-ul\"]/li["+op+"]/ul/li[2]/a/span[1]")).click();
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				crearDocEvidencia.modificarArchivoEvidencia(caso, "Click a subopción Facturas Boletas NotaCredito NotaDebito");
				capturaPantalla.takeScreenShotTest(driver, "Click_Subopcion_Facturas_Boletas_NotaCredito_NotaDebito", caso);
				i=1;
			} catch (Exception e) {
				pageAlerta.alertaManejoError();
				j++;
				if(j==3) {
					System.out.println("no fue posible ingresar en Emisión Express");
					i=1;
				}
			}
		}while(i==0);
	}
	
	public void seleccionTipoCpe(String tipo, String caso) throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException{
		int i=0;
		int j=0;
		do {
			try {
				Select tipoCpe = new Select (driver.findElement(By.xpath("//*[@id=\"form_params\"]/div[5]/select")));
				switch (tipo){
					case "Factura":
						tipoCpe.selectByValue("01");
						break;
					case "Boleta":
						tipoCpe.selectByValue("03");
						break;
					case "Nota Crédito":
						tipoCpe.selectByValue("07");
						break;
					case "Nota Débito":
						tipoCpe.selectByValue("08");
						break;
					default:
						System.out.println("Tipo CPE Valor inválido");
						break;
				}
				Thread.sleep(2000);
				log.modificarArchivoLog(caso, "Tipo CPE: "+tipo);
				crearDocEvidencia.modificarArchivoEvidencia(caso, "Selección Tipo de CPE");
				capturaPantalla.takeScreenShotTest(driver, "Seleccion_Tipo_CPE_"+tipo, caso);
				i=1;
			} catch(Exception e) {
				pageAlerta.alertaManejoError();
				j++;
				if(j==3) {
					System.out.println("No fue posible seleccionar CPE");
					i=1;
				}
			}
		}while(i==0);
	}
	
	public void seleccionSubtipoCpe (String subtipo, String caso) throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException {
		int i=0;
		int j=0;
		do {
			try {
				Select subTipoCpe = new Select (driver.findElement(By.xpath("//*[@id=\"form_params\"]/div[7]/select")));
				switch (subtipo){
				case "Afecta-Inafecta":
					subTipoCpe.selectByValue("A");
					break;
				case "Afecta Gratuita":
					subTipoCpe.selectByValue("AGRA");
					break;
				case "Inafecta Gratuita":
					subTipoCpe.selectByValue("IGRA");
					break;
				case "Descuentos":
					subTipoCpe.selectByValue("DES");
					break;
				case "Cargo":
					subTipoCpe.selectByValue("CARG");
					break;
				case "Exonerada":
					subTipoCpe.selectByValue("EXO");
					break;
				case "Exonerada Gratuita":
					subTipoCpe.selectByValue("EGRA");
					break;
				case "Exportación":
					subTipoCpe.selectByValue("EXP");
					break;
				case "Exportación Gratuita":
					subTipoCpe.selectByValue("EXPG");
					break;
				default:
					System.out.println("Subtipo CPE Valor inválido");
					break;
				}
				Thread.sleep(2000);
				log.modificarArchivoLog(caso, "Subtipo CPE: "+subtipo);
				crearDocEvidencia.modificarArchivoEvidencia(caso, "Selección Subtipo de CPE");
				capturaPantalla.takeScreenShotTest(driver, "Seleccion_Subtipo_CPE_"+subtipo, caso);
				i=1;
			} catch (Exception e) {
				pageAlerta.alertaManejoError();
				j++;
				if(j==3) {
					System.out.println("No fue posible seleccionar Subtipo CPE");
					i=1;
				}
			}
		}while(i==0);
	}

	public void seleccionTipoOperacionSunat (String tipoOperacion, String caso) throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException {
		int i=0;
		int j=0;
		do {
			try {
				Select operacionSunat = new Select (driver.findElement(By.id("tipo_op_sunat")));
				switch (tipoOperacion){
				case "Prestación servicios realizados íntegramente en el país":
					operacionSunat.selectByValue("0201");
					break;
				case "Exportación de Bienes":
					operacionSunat.selectByValue("0200");
					break;
				case "Prestación de servicios de hospedaje No Domiciliado":
					operacionSunat.selectByValue("0202");
					break;
				case "Transporte de navieras":
					operacionSunat.selectByValue("0203");
					break;
				case "Servicios  a naves y aeronaves de bandera extranjera":
					operacionSunat.selectByValue("0204");
					break;
				case "Servicios que conformen un Paquete Turístico":
					operacionSunat.selectByValue("0205");
					break;
				case "Servicios complementarios al transporte de carga":
					operacionSunat.selectByValue("0206");
					break;
				case "Suministro de energía eléctrica a favor de sujetos domiciliados en ZED":
					operacionSunat.selectByValue("0207");
					break;
				case "Prestación servicios realizados parcialmente en el extranjero":
					operacionSunat.selectByValue("0208");
					break;
				default:
					System.out.println("Operacion SUNAT Valor inválido");
					break;
				}
				Thread.sleep(2000);
				log.modificarArchivoLog(caso, "Tipo Operacion SUNAT: "+tipoOperacion);
				crearDocEvidencia.modificarArchivoEvidencia(caso, "Seleccion Tipo Operacion SUNAT");
				capturaPantalla.takeScreenShotTest(driver, "Seleccion_Tipo_Operacion_SUNAT", caso);
				i=1;
			} catch(Exception e) {
				pageAlerta.alertaManejoError();
				j++;
				if(j==3) {
					System.out.println("No fue posible seleccionar Tipo Operacion SUNAT");
					i=1;
				}
			}
		}while(i==0);
	}
	
	public void seleccionTipoAfectaGratuita (String tipoOperacion, String caso) throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException {
		int i=0;
		int j=0;
		do {
			try {
				Select operacionSunat = new Select (driver.findElement(By.id("tipo_op_sunat_agra")));
				switch (tipoOperacion){
				case "Retiro por premio":
					operacionSunat.selectByValue("11");
					break;
				case "Retiro por donación":
					operacionSunat.selectByValue("12");
					break;
				case "Retiro":
					operacionSunat.selectByValue("13");
					break;
				case "Retiro por publicidad":
					operacionSunat.selectByValue("14");
					break;
				case "Bonificaciones":
					operacionSunat.selectByValue("15");
					break;
				case "Retiro por entrega a trabajadores":
					operacionSunat.selectByValue("16");
					break;
				case "IVAP":
					operacionSunat.selectByValue("17");
					break;
				default:
					System.out.println("Tipo Afecta Gratuita Valor inválido");
					break;
				}
				Thread.sleep(2000);
				log.modificarArchivoLog(caso, "Tipo Afecta Gratuita: "+tipoOperacion);
				crearDocEvidencia.modificarArchivoEvidencia(caso, "Seleccion Tipo Afecta Gratuita");
				capturaPantalla.takeScreenShotTest(driver, "Seleccion_Tipo_Afecta_Gratuita", caso);
				i=1;
			} catch (Exception e) {
				pageAlerta.alertaManejoError();
				j++;
				if(j==3) {
					System.out.println("No fue posible seleccionar Tipo Afecta Gratuita");
					i=1;
				}
			}
		}while(i==0);
	}
	
	public void seleccionTipoInafectaGratuita (String tipoOperacion, String caso) throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException {
		int i=0;
		int j=0;
		do {
			try {
				Select operacionSunat = new Select (driver.findElement(By.id("tipo_op_sunat_igra")));
				switch (tipoOperacion){
				case "Retiro por Bonificación":
					operacionSunat.selectByValue("31");
					break;
				case "Retiro":
					operacionSunat.selectByValue("32");
					break;
				case "Retiro por Muestras Médicas":
					operacionSunat.selectByValue("33");
					break;
				case "Retiro por Convenio Colectivo":
					operacionSunat.selectByValue("34");
					break;
				case "Retiro por premio":
					operacionSunat.selectByValue("35");
					break;
				case "Retiro por publicidad":
					operacionSunat.selectByValue("36");
					break;
				case "Transferencia gratuita":
					operacionSunat.selectByValue("37");
					break;
				default:
					System.out.println("Tipo Inafecta Gratuita Valor inválido");
					break;
				}
				Thread.sleep(2000);
				log.modificarArchivoLog(caso, "Tipo Inafecta Gratuita: "+tipoOperacion);
				crearDocEvidencia.modificarArchivoEvidencia(caso, "Seleccion Tipo Inafecta Gratuita");
				capturaPantalla.takeScreenShotTest(driver, "Seleccion_Tipo_Inafecta_Gratuita", caso);
				i=1;
			} catch (Exception e) {
				pageAlerta.alertaManejoError();
				j++;
				if(j==3) {
					System.out.println("No fue posible seleccionar Tipo Inafecta Gratutita");
					i=1;
				}
			}
		}while(i==0);
	}
	
	public void seleccionTipoCargo (String tipoCargo, String caso) throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException {
		int i=0;
		int j=0;
		do {
			try {
				Select tipodeCargo = new Select (driver.findElement(By.name("tipo_cargos")));
				switch (tipoCargo){
				case "Si afectan a la base imponible del IGV/IVAP":
					tipodeCargo.selectByValue("49");
					break;
				case "No afectan a la base imponible del IGV/IVAP":
					tipodeCargo.selectByValue("50");
					break;
				case "Recargo al consumo y/o propinas":
					tipodeCargo.selectByValue("46");
					break;
				default:
					System.out.println("Tipo de Cargo Valor inválido");
					break;
				}
				Thread.sleep(2000);
				log.modificarArchivoLog(caso, "Tipo Cargo: "+tipoCargo);
				crearDocEvidencia.modificarArchivoEvidencia(caso, "Seleccion Tipo Cargo");
				capturaPantalla.takeScreenShotTest(driver, "Seleccion_Tipo_Cargo", caso);
				i=1;
			} catch (Exception e){
				pageAlerta.alertaManejoError();
				j++;
				if(j==3) {
					System.out.println("No fue posible seleccionar Tipo Cargo");
					i=1;
				}
			}
		}while(i==0);
	}
	
	public void seleccionTipoExoneradaGratuita (String tipoExonerada, String caso) throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException {
		int i=0;
		int j=0;
		do {
			try {
				Select tipoExoneradaGratuita = new Select (driver.findElement(By.name("tipo_op_sunat_egra")));
				switch (tipoExonerada){
				case "Transferencia gratuita":
					tipoExoneradaGratuita.selectByValue("21");
					break;
				default:
					System.out.println("Tipo de Cargo Valor inválido");
					break;
				}
				Thread.sleep(2000);
				log.modificarArchivoLog(caso, "Tipo Exonerda Gratuita: "+tipoExonerada);
				crearDocEvidencia.modificarArchivoEvidencia(caso, "Seleccion Tipo Exonerda Gratuita");
				capturaPantalla.takeScreenShotTest(driver, "Seleccion_Tipo_Exonerda_Gratuita", caso);
				i=1;
			} catch(Exception e) {
				pageAlerta.alertaManejoError();
				j++;
				if(j==3) {
					System.out.println("No fue posible seleccionar Tipo Exonerada Gratuita");
					i=1;
				}
			}
		}while(i==0);
	}
	
	public void seleccionTipoDescuento (String tipoDescuento, String caso) throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException {
		int i=0;
		int j=0;
		do {
			try {
				Select tipodeCargo = new Select (driver.findElement(By.name("tipo_descuentos")));
				switch (tipoDescuento){
				case "Si afectan a la base imponible":
					tipodeCargo.selectByValue("00");
					break;
				case "No afectan a la base imponible":
					tipodeCargo.selectByValue("01");
					break;
				default:
					System.out.println("Tipo de Descuento Valor inválido");
					break;
				}
				Thread.sleep(2000);
				log.modificarArchivoLog(caso, "Tipo Cargo: "+tipoDescuento);
				crearDocEvidencia.modificarArchivoEvidencia(caso, "Seleccion Tipo Descuento");
				capturaPantalla.takeScreenShotTest(driver, "Seleccion_Tipo_Descuento", caso);
				i=1;
			} catch (Exception e){
				pageAlerta.alertaManejoError();
				j++;
				if(j==3) {
					System.out.println("No fue posible seleccionar Tipo Descuento");
					i=1;
				}
			}
		}while(i==0);
	}
}
