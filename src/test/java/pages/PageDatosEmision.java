package pages;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import common.CapturaPantalla;
import common.CrearDocEvidencia;

public class PageDatosEmision {
	private WebDriver driver;
	CapturaPantalla capturaPantalla = new CapturaPantalla();
	CrearDocEvidencia crearDocEvidencia = new CrearDocEvidencia();
	public PageDatosEmision(WebDriver driver) {
		this.driver=driver;
	}
	
//	Completar campo Fecha de Emision, es el unico que es obligatorio, los demás quedarán comentados en caso de usarlos en el futuro
	public void completarDatosEmision(String caso) throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException
	{
		int i=0;
		int j=0;
		do {
			try {
				Calendar calendario = new GregorianCalendar();
				int dia = calendario.get(Calendar.DAY_OF_MONTH);
				int mes = calendario.get(Calendar.MONTH);
				mes=mes+1;
				int anio = calendario.get(Calendar.YEAR);
				
				String fechaEmision;
//				String fechaVencimiento;
//				String fechaInicioCiclo;
//				String fechaFinCiclo;
//				Fecha de Emision
				if(mes<10) {
					fechaEmision=anio+"-0"+mes;
				}
				else {
					fechaEmision=anio+"-"+mes;
				}
				if(dia<10) {
					fechaEmision=fechaEmision+"-0"+dia;
				}
				else {
					fechaEmision=fechaEmision+"-"+dia;
				}		

////				Fecha de Vencimiento
//				dia=dia+7;
//				if(mes<10) {
//					fechaVencimiento=anio+"-0"+mes;
//				}
//				else {
//					fechaVencimiento=anio+"-"+mes;
//				}
//				if(dia<10) {
//					fechaVencimiento=fechaVencimiento+"-0"+dia;
//				}
//				else {
//					fechaVencimiento=fechaVencimiento+"-"+dia;
//				}
//				
////				Fecha Inicio Ciclo Facturación		
//				if(mes<10) {
//					fechaInicioCiclo=anio+"-0"+mes+"-01";
//				}
//				else {
//					fechaInicioCiclo=anio+"-"+mes+"-01";
//				}
//				
////				Fecha Fin Ciclo Facturación		
//				if(mes<10) {
//					fechaFinCiclo=anio+"-0"+mes+"-28";
//				}
//				else {
//					fechaFinCiclo=anio+"-"+mes+"-28";
//				}
				
				driver.findElement(By.name("fecha_emision")).sendKeys(fechaEmision);
				crearDocEvidencia.modificarArchivoEvidencia(caso, "Se Agrega Fecha de Emisión");
				capturaPantalla.takeScreenShotTest(driver, "Fecha_Emision",caso);
				Thread.sleep(2000);
//				driver.findElement(By.name("fecha_vencimiento")).sendKeys(fechaVencimiento);
//				Thread.sleep(2000);
//				driver.findElement(By.name("fecha_emision_inicio_ciclo")).sendKeys(fechaInicioCiclo);
//				Thread.sleep(2000);
//				driver.findElement(By.name("fecha_emision_fin_ciclo")).sendKeys(fechaFinCiclo);
//				Thread.sleep(2000);
				i=1;
			} catch (Exception e) {
				PageAlerta pageAlerta = new PageAlerta(driver);
				pageAlerta.alertaManejoError();
				j++;
				if(j==3) {
					System.out.println("No se puede agregar Datos de Emision");
					i=1;
				}
			}
		}while(i==0);
	}
}
