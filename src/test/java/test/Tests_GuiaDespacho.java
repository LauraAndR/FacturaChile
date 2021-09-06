package test;

import static org.testng.Assert.assertEquals;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import common.Configuration;
import common.LeerExcel;
import evidence.CrearLogyDocumento;
import pages.PageEmisionDTE;
import pages.PageEscritorio;
import pages.PageLoginAdm;


public class Tests_GuiaDespacho {
	private WebDriver driver;
	String datapool = Configuration.ROOT_DIR+"DataPool_v2.xlsx";
	LeerExcel leerExcel = new LeerExcel();
	
	@BeforeMethod
	public void setUp() throws FileNotFoundException, IOException {
//		DesiredCapabilities caps = new DesiredCapabilities();
		System.setProperty("webdriver.chrome.driver", "Drivers/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.navigate().to("https://escritorio-cert.acepta.com/");// Aquí se ingresa la URL para hacer las pruebas.
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	
	
	@Test
	public void Script_0002() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "GD_0002";
		// Emisión DTE - Individual - guía de despacho - contado - Operación constituye venta - exportador
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Guía de Despacho Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorTraslado(cp, "1 - Operación constituye venta");
		pageEmisionDTE.SeleccionarCodigoTraslado(cp, "1 - Exportador");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
		
		pageEmisionDTE.IngresoDatosTransporte(cp, "ABCD12", "11.111.111-1", "Juan Pérez Pérez", "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("GUÍA DE DESPACHO ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[2]")).getText().contains("Exportador") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("190") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("1.190") &&

				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[2]/td[2]")).getText().contains("Operación constituye venta") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("ABCD12") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[4]/td[2]")).getText().contains("11.111.111-1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[5]/td[2]")).getText().contains("11.111.111-1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[6]/td[2]")).getText().contains("Juan Pérez Pérez") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[7]/td[2]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[8]/td[2]")).getText().contains("Santiago")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
		}
		else {
			crearLogyDocumento.CasoNok(cp);
			System.out.println("FLUJO NOOK");
			resultado = "FLUJO NOOK";
		}
		assertEquals(resultado, "FLUJO OK", "Se verifica resultado del test "+cp);
	}
	
	@Test
	public void Script_0003() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "GD_0003";
		// Emisión DTE - Individual - guía de despacho - contado - ventas por efectuar - exportador
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Guía de Despacho Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorTraslado(cp, "2 - Ventas por efectuar");
		pageEmisionDTE.SeleccionarCodigoTraslado(cp, "1 - Exportador");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
		
		pageEmisionDTE.IngresoDatosTransporte(cp, "ABCD12", "11.111.111-1", "Juan Pérez Pérez", "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("GUÍA DE DESPACHO ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[2]")).getText().contains("Exportador") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("190") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("1.190") &&

				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[2]/td[2]")).getText().contains("Venta por efectuar") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("ABCD12") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[4]/td[2]")).getText().contains("11.111.111-1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[5]/td[2]")).getText().contains("11.111.111-1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[6]/td[2]")).getText().contains("Juan Pérez Pérez") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[7]/td[2]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[8]/td[2]")).getText().contains("Santiago")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
		}
		else {
			crearLogyDocumento.CasoNok(cp);
			System.out.println("FLUJO NOOK");
			resultado = "FLUJO NOOK";
		}
		assertEquals(resultado, "FLUJO OK", "Se verifica resultado del test "+cp);
	}
	

	@Test
	public void Script_0004() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "GD_0004";
		// Emisión DTE - Individual - guía de despacho - contado - consignaciones - exportador
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Guía de Despacho Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorTraslado(cp, "3 - Consignaciones");
		pageEmisionDTE.SeleccionarCodigoTraslado(cp, "1 - Exportador");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
		
		pageEmisionDTE.IngresoDatosTransporte(cp, "ABCD12", "11.111.111-1", "Juan Pérez Pérez", "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("GUÍA DE DESPACHO ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[2]")).getText().contains("Exportador") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("190") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("1.190") &&

				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[2]/td[2]")).getText().contains("Consignaciones") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("ABCD12") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[4]/td[2]")).getText().contains("11.111.111-1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[5]/td[2]")).getText().contains("11.111.111-1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[6]/td[2]")).getText().contains("Juan Pérez Pérez") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[7]/td[2]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[8]/td[2]")).getText().contains("Santiago")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
		}
		else {
			crearLogyDocumento.CasoNok(cp);
			System.out.println("FLUJO NOOK");
			resultado = "FLUJO NOOK";
		}
		assertEquals(resultado, "FLUJO OK", "Se verifica resultado del test "+cp);
	}
	
	
	@Test
	public void Script_0005() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "GD_0005";
		// Emisión DTE - Individual - guía de despacho - contado - entrega gratuita - exportador
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Guía de Despacho Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorTraslado(cp, "4 - Entrega gratuita");
		pageEmisionDTE.SeleccionarCodigoTraslado(cp, "1 - Exportador");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
		
		pageEmisionDTE.IngresoDatosTransporte(cp, "ABCD12", "11.111.111-1", "Juan Pérez Pérez", "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("GUÍA DE DESPACHO ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[2]")).getText().contains("Exportador") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("190") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("1.190") &&

				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[2]/td[2]")).getText().contains("Entrega gratuita") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("ABCD12") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[4]/td[2]")).getText().contains("11.111.111-1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[5]/td[2]")).getText().contains("11.111.111-1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[6]/td[2]")).getText().contains("Juan Pérez Pérez") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[7]/td[2]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[8]/td[2]")).getText().contains("Santiago")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
		}
		else {
			crearLogyDocumento.CasoNok(cp);
			System.out.println("FLUJO NOOK");
			resultado = "FLUJO NOOK";
		}
		assertEquals(resultado, "FLUJO OK", "Se verifica resultado del test "+cp);
	}
	
	
	@Test
	public void Script_0006() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "GD_0006";
		// Emisión DTE - Individual - guía de despacho - contado - traslado interno - exportador
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Guía de Despacho Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorTraslado(cp, "5 - Traslado interno");
		pageEmisionDTE.SeleccionarCodigoTraslado(cp, "1 - Exportador");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
		
		pageEmisionDTE.IngresoDatosTransporte(cp, "ABCD12", "11.111.111-1", "Juan Pérez Pérez", "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("GUÍA DE DESPACHO ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[2]")).getText().contains("Exportador") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("190") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("1.190") &&

				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[2]/td[2]")).getText().contains("Traslado interno") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("ABCD12") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[4]/td[2]")).getText().contains("11.111.111-1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[5]/td[2]")).getText().contains("11.111.111-1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[6]/td[2]")).getText().contains("Juan Pérez Pérez") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[7]/td[2]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[8]/td[2]")).getText().contains("Santiago")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
		}
		else {
			crearLogyDocumento.CasoNok(cp);
			System.out.println("FLUJO NOOK");
			resultado = "FLUJO NOOK";
		}
		assertEquals(resultado, "FLUJO OK", "Se verifica resultado del test "+cp);
	}
	
	
	@Test
	public void Script_0007() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "GD_0007";
		// Emisión DTE - Individual - guía de despacho - contado - otros traslado no venta - exportador
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Guía de Despacho Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorTraslado(cp, "6 - Otros traslados no venta");
		pageEmisionDTE.SeleccionarCodigoTraslado(cp, "1 - Exportador");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
		
		pageEmisionDTE.IngresoDatosTransporte(cp, "ABCD12", "11.111.111-1", "Juan Pérez Pérez", "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("GUÍA DE DESPACHO ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[2]")).getText().contains("Exportador") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("190") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("1.190") &&

				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[2]/td[2]")).getText().contains("Otro traslado no venta") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("ABCD12") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[4]/td[2]")).getText().contains("11.111.111-1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[5]/td[2]")).getText().contains("11.111.111-1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[6]/td[2]")).getText().contains("Juan Pérez Pérez") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[7]/td[2]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[8]/td[2]")).getText().contains("Santiago")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
		}
		else {
			crearLogyDocumento.CasoNok(cp);
			System.out.println("FLUJO NOOK");
			resultado = "FLUJO NOOK";
		}
		assertEquals(resultado, "FLUJO OK", "Se verifica resultado del test "+cp);
	}
	
	
	@Test
	public void Script_0008() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "GD_0008";
		// Emisión DTE - Individual - guía de despacho - contado - guía de devolución - exportador
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Guía de Despacho Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorTraslado(cp, "7 - Guía de devolución");
		pageEmisionDTE.SeleccionarCodigoTraslado(cp, "1 - Exportador");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
		
		pageEmisionDTE.IngresoDatosTransporte(cp, "ABCD12", "11.111.111-1", "Juan Pérez Pérez", "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("GUÍA DE DESPACHO ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[2]")).getText().contains("Exportador") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("190") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("1.190") &&

				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[2]/td[2]")).getText().contains("GuÍa de devolución") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("ABCD12") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[4]/td[2]")).getText().contains("11.111.111-1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[5]/td[2]")).getText().contains("11.111.111-1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[6]/td[2]")).getText().contains("Juan Pérez Pérez") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[7]/td[2]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[8]/td[2]")).getText().contains("Santiago")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
		}
		else {
			crearLogyDocumento.CasoNok(cp);
			System.out.println("FLUJO NOOK");
			resultado = "FLUJO NOOK";
		}
		assertEquals(resultado, "FLUJO OK", "Se verifica resultado del test "+cp);
	}
	
	
	@Test
	public void Script_0011() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "GD_0011";
		// Emisión DTE - Individual - guía de despacho - contado - Operación constituye venta - agente de aduana (En la devolución de mercaderías de aduanas)
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Guía de Despacho Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorTraslado(cp, "1 - Operación constituye venta");
		pageEmisionDTE.SeleccionarCodigoTraslado(cp, "2 - Agente de Aduana (En la devolución de mercaderías de Aduanas)");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
		
		pageEmisionDTE.IngresoDatosTransporte(cp, "ABCD12", "11.111.111-1", "Juan Pérez Pérez", "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");

		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("GUÍA DE DESPACHO ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[2]")).getText().contains("Agente de Aduana") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("190") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("1.190") &&

				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[2]/td[2]")).getText().contains("Operación constituye venta") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("ABCD12") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[4]/td[2]")).getText().contains("11.111.111-1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[5]/td[2]")).getText().contains("11.111.111-1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[6]/td[2]")).getText().contains("Juan Pérez Pérez") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[7]/td[2]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[8]/td[2]")).getText().contains("Santiago")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
		}
		else {
			crearLogyDocumento.CasoNok(cp);
			System.out.println("FLUJO NOOK");
			resultado = "FLUJO NOOK";
		}
		assertEquals(resultado, "FLUJO OK", "Se verifica resultado del test "+cp);
	}
	
	
	@Test
	public void Script_0012() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "GD_0012";
		// Emisión DTE - Individual - guía de despacho - contado - ventas por efectuar - agente de aduana (En la devolución de mercaderías de aduanas)
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Guía de Despacho Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorTraslado(cp, "2 - Ventas por efectuar");
		pageEmisionDTE.SeleccionarCodigoTraslado(cp, "2 - Agente de Aduana (En la devolución de mercaderías de Aduanas)");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
		
		pageEmisionDTE.IngresoDatosTransporte(cp, "ABCD12", "11.111.111-1", "Juan Pérez Pérez", "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");

		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("GUÍA DE DESPACHO ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[2]")).getText().contains("Agente de Aduana") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("190") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("1.190") &&

				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[2]/td[2]")).getText().contains("Venta por efectuar") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("ABCD12") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[4]/td[2]")).getText().contains("11.111.111-1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[5]/td[2]")).getText().contains("11.111.111-1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[6]/td[2]")).getText().contains("Juan Pérez Pérez") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[7]/td[2]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[8]/td[2]")).getText().contains("Santiago")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
		}
		else {
			crearLogyDocumento.CasoNok(cp);
			System.out.println("FLUJO NOOK");
			resultado = "FLUJO NOOK";
		}
		assertEquals(resultado, "FLUJO OK", "Se verifica resultado del test "+cp);
	}
	
	
	@Test
	public void Script_0013() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "GD_0013";
		// Emisión DTE - Individual - guía de despacho - contado - consignaciones - agente de aduana (En la devolución de mercaderías de aduanas)
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Guía de Despacho Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorTraslado(cp, "3 - Consignaciones");
		pageEmisionDTE.SeleccionarCodigoTraslado(cp, "2 - Agente de Aduana (En la devolución de mercaderías de Aduanas)");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
		
		pageEmisionDTE.IngresoDatosTransporte(cp, "ABCD12", "11.111.111-1", "Juan Pérez Pérez", "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");

		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("GUÍA DE DESPACHO ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[2]")).getText().contains("Agente de Aduana") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("190") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("1.190") &&

				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[2]/td[2]")).getText().contains("Consignaciones") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("ABCD12") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[4]/td[2]")).getText().contains("11.111.111-1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[5]/td[2]")).getText().contains("11.111.111-1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[6]/td[2]")).getText().contains("Juan Pérez Pérez") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[7]/td[2]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[8]/td[2]")).getText().contains("Santiago")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
		}
		else {
			crearLogyDocumento.CasoNok(cp);
			System.out.println("FLUJO NOOK");
			resultado = "FLUJO NOOK";
		}
		assertEquals(resultado, "FLUJO OK", "Se verifica resultado del test "+cp);
	}
	
	
	@Test
	public void Script_0014() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "GD_0014";
		// Emisión DTE - Individual - guía de despacho - contado - entrega gratuita - agente de aduana (En la devolución de mercaderías de aduanas)
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Guía de Despacho Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorTraslado(cp, "4 - Entrega gratuita");
		pageEmisionDTE.SeleccionarCodigoTraslado(cp, "2 - Agente de Aduana (En la devolución de mercaderías de Aduanas)");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
		
		pageEmisionDTE.IngresoDatosTransporte(cp, "ABCD12", "11.111.111-1", "Juan Pérez Pérez", "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");

		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("GUÍA DE DESPACHO ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[2]")).getText().contains("Agente de Aduana") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("190") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("1.190") &&

				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[2]/td[2]")).getText().contains("Entrega gratuita") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("ABCD12") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[4]/td[2]")).getText().contains("11.111.111-1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[5]/td[2]")).getText().contains("11.111.111-1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[6]/td[2]")).getText().contains("Juan Pérez Pérez") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[7]/td[2]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[8]/td[2]")).getText().contains("Santiago")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
		}
		else {
			crearLogyDocumento.CasoNok(cp);
			System.out.println("FLUJO NOOK");
			resultado = "FLUJO NOOK";
		}
		assertEquals(resultado, "FLUJO OK", "Se verifica resultado del test "+cp);
	}
	
	
	@Test
	public void Script_0015() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "GD_0015";
		// Emisión DTE - Individual - guía de despacho - contado - traslado interno - agente de aduana (En la devolución de mercaderías de aduanas)
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Guía de Despacho Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorTraslado(cp, "5 - Traslado interno");
		pageEmisionDTE.SeleccionarCodigoTraslado(cp, "2 - Agente de Aduana (En la devolución de mercaderías de Aduanas)");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
		
		pageEmisionDTE.IngresoDatosTransporte(cp, "ABCD12", "11.111.111-1", "Juan Pérez Pérez", "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");

		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("GUÍA DE DESPACHO ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[2]")).getText().contains("Agente de Aduana") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("190") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("1.190") &&

				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[2]/td[2]")).getText().contains("Traslado interno") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("ABCD12") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[4]/td[2]")).getText().contains("11.111.111-1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[5]/td[2]")).getText().contains("11.111.111-1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[6]/td[2]")).getText().contains("Juan Pérez Pérez") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[7]/td[2]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[8]/td[2]")).getText().contains("Santiago")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
		}
		else {
			crearLogyDocumento.CasoNok(cp);
			System.out.println("FLUJO NOOK");
			resultado = "FLUJO NOOK";
		}
		assertEquals(resultado, "FLUJO OK", "Se verifica resultado del test "+cp);
	}
	
	
	@Test
	public void Script_0016() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "GD_0016";
		// Emisión DTE - Individual - guía de despacho - contado - otros traslado no venta - agente de aduana (En la devolución de mercaderías de aduanas)
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Guía de Despacho Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorTraslado(cp, "6 - Otros traslados no venta");
		pageEmisionDTE.SeleccionarCodigoTraslado(cp, "2 - Agente de Aduana (En la devolución de mercaderías de Aduanas)");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
		
		pageEmisionDTE.IngresoDatosTransporte(cp, "ABCD12", "11.111.111-1", "Juan Pérez Pérez", "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");

		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("GUÍA DE DESPACHO ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[2]")).getText().contains("Agente de Aduana") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("190") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("1.190") &&

				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[2]/td[2]")).getText().contains("Otro traslado no venta") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("ABCD12") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[4]/td[2]")).getText().contains("11.111.111-1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[5]/td[2]")).getText().contains("11.111.111-1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[6]/td[2]")).getText().contains("Juan Pérez Pérez") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[7]/td[2]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[8]/td[2]")).getText().contains("Santiago")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
		}
		else {
			crearLogyDocumento.CasoNok(cp);
			System.out.println("FLUJO NOOK");
			resultado = "FLUJO NOOK";
		}
		assertEquals(resultado, "FLUJO OK", "Se verifica resultado del test "+cp);
	}
	
	
	
	@Test
	public void Script_0017() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "GD_0017";
		// Emisión DTE - Individual - guía de despacho - contado - guía de devolución - agente de aduana (En la devolución de mercaderías de aduanas)
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Guía de Despacho Electrónica");

		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorTraslado(cp, "7 - Guía de devolución");
		pageEmisionDTE.SeleccionarCodigoTraslado(cp, "2 - Agente de Aduana (En la devolución de mercaderías de Aduanas)");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
		
		pageEmisionDTE.IngresoDatosTransporte(cp, "ABCD12", "11.111.111-1", "Juan Pérez Pérez", "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");

		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("GUÍA DE DESPACHO ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[2]")).getText().contains("Agente de Aduana") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("190") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("1.190") &&

				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[2]/td[2]")).getText().contains("GuÍa de devolución") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("ABCD12") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[4]/td[2]")).getText().contains("11.111.111-1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[5]/td[2]")).getText().contains("11.111.111-1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[6]/td[2]")).getText().contains("Juan Pérez Pérez") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[7]/td[2]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[8]/td[2]")).getText().contains("Santiago")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
		}
		else {
			crearLogyDocumento.CasoNok(cp);
			System.out.println("FLUJO NOOK");
			resultado = "FLUJO NOOK";
		}
		assertEquals(resultado, "FLUJO OK", "Se verifica resultado del test "+cp);
	}
	
	
	@Test
	public void Script_0020() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "GD_0020";
		// Emisión DTE - Individual - guía de despacho - contado - Operación constituye venta - vendedor (Entre otros, se refiere a aquel Productor que vende mercadería con entrega en
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Guía de Despacho Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorTraslado(cp, "1 - Operación constituye venta");
		pageEmisionDTE.SeleccionarCodigoTraslado(cp, "3 - Vendedor (Entre otros, se refiere a aquel Productor que vende mercadería con entrega en Zona Primaria).");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
		
		pageEmisionDTE.IngresoDatosTransporte(cp, "ABCD12", "11.111.111-1", "Juan Pérez Pérez", "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("GUÍA DE DESPACHO ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[2]")).getText().contains("Vendedor") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("190") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("1.190") &&

				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[2]/td[2]")).getText().contains("Operación constituye venta") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("ABCD12") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[4]/td[2]")).getText().contains("11.111.111-1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[5]/td[2]")).getText().contains("11.111.111-1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[6]/td[2]")).getText().contains("Juan Pérez Pérez") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[7]/td[2]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[8]/td[2]")).getText().contains("Santiago")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
		}
		else {
			crearLogyDocumento.CasoNok(cp);
			System.out.println("FLUJO NOOK");
			resultado = "FLUJO NOOK";
		}
		assertEquals(resultado, "FLUJO OK", "Se verifica resultado del test "+cp);
	}
	
	
	
	@Test
	public void Script_0021() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "GD_0021";
		// Emisión DTE - Individual - guía de despacho - contado - ventas por efectuar - vendedor (Entre otros, se refiere a aquel Productor que vende mercadería con entrega en Zona
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Guía de Despacho Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorTraslado(cp, "2 - Ventas por efectuar");
		pageEmisionDTE.SeleccionarCodigoTraslado(cp, "3 - Vendedor (Entre otros, se refiere a aquel Productor que vende mercadería con entrega en Zona Primaria).");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
		
		pageEmisionDTE.IngresoDatosTransporte(cp, "ABCD12", "11.111.111-1", "Juan Pérez Pérez", "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("GUÍA DE DESPACHO ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[2]")).getText().contains("Vendedor") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("190") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("1.190") &&

				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[2]/td[2]")).getText().contains("Venta por efectuar") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("ABCD12") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[4]/td[2]")).getText().contains("11.111.111-1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[5]/td[2]")).getText().contains("11.111.111-1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[6]/td[2]")).getText().contains("Juan Pérez Pérez") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[7]/td[2]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[8]/td[2]")).getText().contains("Santiago")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
		}
		else {
			crearLogyDocumento.CasoNok(cp);
			System.out.println("FLUJO NOOK");
			resultado = "FLUJO NOOK";
		}
		assertEquals(resultado, "FLUJO OK", "Se verifica resultado del test "+cp);
	}
	
	
	@Test
	public void Script_0022() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "GD_0022";
		// Emisión DTE - Individual - guía de despacho - contado - consignaciones - vendedor (Entre otros, se refiere a aquel Productor que vende mercadería con entrega en Zona
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Guía de Despacho Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorTraslado(cp, "3 - Consignaciones");
		pageEmisionDTE.SeleccionarCodigoTraslado(cp, "3 - Vendedor (Entre otros, se refiere a aquel Productor que vende mercadería con entrega en Zona Primaria).");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
		
		pageEmisionDTE.IngresoDatosTransporte(cp, "ABCD12", "11.111.111-1", "Juan Pérez Pérez", "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("GUÍA DE DESPACHO ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[2]")).getText().contains("Vendedor") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("190") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("1.190") &&

				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[2]/td[2]")).getText().contains("Consignaciones") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("ABCD12") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[4]/td[2]")).getText().contains("11.111.111-1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[5]/td[2]")).getText().contains("11.111.111-1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[6]/td[2]")).getText().contains("Juan Pérez Pérez") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[7]/td[2]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[8]/td[2]")).getText().contains("Santiago")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
		}
		else {
			crearLogyDocumento.CasoNok(cp);
			System.out.println("FLUJO NOOK");
			resultado = "FLUJO NOOK";
		}
		assertEquals(resultado, "FLUJO OK", "Se verifica resultado del test "+cp);
	}
	
	
	@Test
	public void Script_0023() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "GD_0023";
		// Emisión DTE - Individual - guía de despacho - contado - entrega gratuita - vendedor (Entre otros, se refiere a aquel Productor que vende mercadería con entrega en Zona
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Guía de Despacho Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorTraslado(cp, "4 - Entrega gratuita");
		pageEmisionDTE.SeleccionarCodigoTraslado(cp, "3 - Vendedor (Entre otros, se refiere a aquel Productor que vende mercadería con entrega en Zona Primaria).");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
		
		pageEmisionDTE.IngresoDatosTransporte(cp, "ABCD12", "11.111.111-1", "Juan Pérez Pérez", "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("GUÍA DE DESPACHO ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[2]")).getText().contains("Vendedor") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("190") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("1.190") &&

				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[2]/td[2]")).getText().contains("Entrega gratuita") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("ABCD12") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[4]/td[2]")).getText().contains("11.111.111-1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[5]/td[2]")).getText().contains("11.111.111-1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[6]/td[2]")).getText().contains("Juan Pérez Pérez") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[7]/td[2]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[8]/td[2]")).getText().contains("Santiago")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
		}
		else {
			crearLogyDocumento.CasoNok(cp);
			System.out.println("FLUJO NOOK");
			resultado = "FLUJO NOOK";
		}
		assertEquals(resultado, "FLUJO OK", "Se verifica resultado del test "+cp);
	}
	
	
	@Test
	public void Script_0024() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "GD_0024";
		// Emisión DTE - Individual - guía de despacho - contado - traslado interno - vendedor (Entre otros, se refiere a aquel Productor que vende mercadería con entrega en Zona
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Guía de Despacho Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorTraslado(cp, "5 - Traslado interno");
		pageEmisionDTE.SeleccionarCodigoTraslado(cp, "3 - Vendedor (Entre otros, se refiere a aquel Productor que vende mercadería con entrega en Zona Primaria).");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
		
		pageEmisionDTE.IngresoDatosTransporte(cp, "ABCD12", "11.111.111-1", "Juan Pérez Pérez", "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("GUÍA DE DESPACHO ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[2]")).getText().contains("Vendedor") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("190") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("1.190") &&

				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[2]/td[2]")).getText().contains("Traslado interno") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("ABCD12") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[4]/td[2]")).getText().contains("11.111.111-1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[5]/td[2]")).getText().contains("11.111.111-1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[6]/td[2]")).getText().contains("Juan Pérez Pérez") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[7]/td[2]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[8]/td[2]")).getText().contains("Santiago")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
		}
		else {
			crearLogyDocumento.CasoNok(cp);
			System.out.println("FLUJO NOOK");
			resultado = "FLUJO NOOK";
		}
		assertEquals(resultado, "FLUJO OK", "Se verifica resultado del test "+cp);
	}
	
	
	@Test
	public void Script_0025() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "GD_0025";
		// Emisión DTE - Individual - guía de despacho - contado - otros traslado no venta - vendedor (Entre otros, se refiere a aquel Productor que vende mercadería con entrega en Zona
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Guía de Despacho Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorTraslado(cp, "6 - Otros traslados no venta");
		pageEmisionDTE.SeleccionarCodigoTraslado(cp, "3 - Vendedor (Entre otros, se refiere a aquel Productor que vende mercadería con entrega en Zona Primaria).");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
		
		pageEmisionDTE.IngresoDatosTransporte(cp, "ABCD12", "11.111.111-1", "Juan Pérez Pérez", "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("GUÍA DE DESPACHO ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[2]")).getText().contains("Vendedor") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("190") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("1.190") &&

				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[2]/td[2]")).getText().contains("Otro traslado no venta") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("ABCD12") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[4]/td[2]")).getText().contains("11.111.111-1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[5]/td[2]")).getText().contains("11.111.111-1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[6]/td[2]")).getText().contains("Juan Pérez Pérez") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[7]/td[2]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[8]/td[2]")).getText().contains("Santiago")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
		}
		else {
			crearLogyDocumento.CasoNok(cp);
			System.out.println("FLUJO NOOK");
			resultado = "FLUJO NOOK";
		}
		assertEquals(resultado, "FLUJO OK", "Se verifica resultado del test "+cp);
	}
	
	
	@Test
	public void Script_0026() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "GD_0026";
		// Emisión DTE - Individual - guía de despacho - contado - guía de devolución - vendedor (Entre otros, se refiere a aquel Productor que vende mercadería con entrega en Zona
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Guía de Despacho Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorTraslado(cp, "7 - Guía de devolución");
		pageEmisionDTE.SeleccionarCodigoTraslado(cp, "3 - Vendedor (Entre otros, se refiere a aquel Productor que vende mercadería con entrega en Zona Primaria).");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
		
		pageEmisionDTE.IngresoDatosTransporte(cp, "ABCD12", "11.111.111-1", "Juan Pérez Pérez", "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("GUÍA DE DESPACHO ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[2]")).getText().contains("Vendedor") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("190") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("1.190") &&

				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[2]/td[2]")).getText().contains("GuÍa de devolución") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("ABCD12") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[4]/td[2]")).getText().contains("11.111.111-1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[5]/td[2]")).getText().contains("11.111.111-1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[6]/td[2]")).getText().contains("Juan Pérez Pérez") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[7]/td[2]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[8]/td[2]")).getText().contains("Santiago")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
		}
		else {
			crearLogyDocumento.CasoNok(cp);
			System.out.println("FLUJO NOOK");
			resultado = "FLUJO NOOK";
		}
		assertEquals(resultado, "FLUJO OK", "Se verifica resultado del test "+cp);
	}
	
	
	@Test
	public void Script_0029() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "GD_0029";
		// Emisión DTE - Individual - guía de despacho - contado - Operación constituye venta - Contribuyente autorizado expresamente por el SII
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Guía de Despacho Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorTraslado(cp, "1 - Operación constituye venta");
		pageEmisionDTE.SeleccionarCodigoTraslado(cp, "4 - Contribuyente autorizado expresamente por el SII.");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
		
		pageEmisionDTE.IngresoDatosTransporte(cp, "ABCD12", "11.111.111-1", "Juan Pérez Pérez", "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("GUÍA DE DESPACHO ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[2]")).getText().contains("Contribuyente autorizado (SII)") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("190") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("1.190") &&

				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[2]/td[2]")).getText().contains("Operación constituye venta") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("ABCD12") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[4]/td[2]")).getText().contains("11.111.111-1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[5]/td[2]")).getText().contains("11.111.111-1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[6]/td[2]")).getText().contains("Juan Pérez Pérez") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[7]/td[2]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[8]/td[2]")).getText().contains("Santiago")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
		}
		else {
			crearLogyDocumento.CasoNok(cp);
			System.out.println("FLUJO NOOK");
			resultado = "FLUJO NOOK";
		}
		assertEquals(resultado, "FLUJO OK", "Se verifica resultado del test "+cp);
	}
	
	
	
	
	@AfterMethod
	public void FinEjecucion() {
		driver.close();
	}
	
}
