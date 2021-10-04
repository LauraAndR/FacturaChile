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


public class Tests_NotaCredito {
	private WebDriver driver;
	String datapool = Configuration.ROOT_DIR+"DataPool_v2.xlsx";
	LeerExcel leerExcel = new LeerExcel();
	
	@BeforeMethod
	public void setUp() throws FileNotFoundException, IOException {
//		DesiredCapabilities caps = new DesiredCapabilities();
		System.setProperty("webdriver.chrome.driver", "Drivers/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.navigate().to("https://escritorio-cert.acepta.com/");// Aqu� se ingresa la URL para hacer las pruebas.
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	
	
	@Test
	public void Script_0002() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "FA_0002";
		//Emisi�n DTE - Individual - Nota de Cr�dito - Contado - Ventas del Giro
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisi�n DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Cr�dito Electr�nica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "1. Ventas del Giro");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
	
		
		pageEmisionDTE.SeleccionartipoDoc(cp, "30 - Factura");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		
		pageEmisionDTE.BtnEmitirNotaCredito(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE CR�DITO ELECTR�NICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("190") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("120") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[5]/td[3]")).getText().contains("1.310") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[1]")).getText().contains("Factura") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[4]")).getText().contains("Anula Documento   -  Anula Documento:")){
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
		String cp = "FA_0003";
		// Emisi�n DTE - Individual - Nota de Cr�dito - Contado - Venta activo fijo
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisi�n DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Cr�dito Electr�nica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "2. Venta Activo Fijo");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
	
		
		pageEmisionDTE.SeleccionartipoDoc(cp, "30 - Factura");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		
		pageEmisionDTE.BtnEmitirNotaCredito(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE CR�DITO ELECTR�NICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("190") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("120") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[5]/td[3]")).getText().contains("1.310") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[1]")).getText().contains("Factura") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[4]")).getText().contains("Anula Documento   -  Anula Documento:")){
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
		String cp = "FA_0004";
		// Emisi�n DTE - Individual - Nota de Cr�dito - Contado - Venta bien ra�z
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisi�n DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Cr�dito Electr�nica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "3. Venta Bien Ra�z");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
	
		
		pageEmisionDTE.SeleccionartipoDoc(cp, "30 - Factura");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		
		pageEmisionDTE.BtnEmitirNotaCredito(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE CR�DITO ELECTR�NICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("190") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("120") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[5]/td[3]")).getText().contains("1.310") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[1]")).getText().contains("Factura") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[4]")).getText().contains("Anula Documento   -  Anula Documento:")){
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
		String cp = "FA_0005";
		// Emisi�n DTE - Individual - Nota de Cr�dito - Cr�dito - Ventas del Giro
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisi�n DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Cr�dito Electr�nica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Cr�dito");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "1. Ventas del Giro");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
	
		
		pageEmisionDTE.SeleccionartipoDoc(cp, "30 - Factura");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		
		pageEmisionDTE.BtnEmitirNotaCredito(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE CR�DITO ELECTR�NICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Credito") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("190") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("120") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[5]/td[3]")).getText().contains("1.310") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[1]")).getText().contains("Factura") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[4]")).getText().contains("Anula Documento   -  Anula Documento:")){
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
		String cp = "FA_0006";
		// Emisi�n DTE - Individual - Nota de Cr�dito - Cr�dito - Venta activo fijo
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisi�n DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Cr�dito Electr�nica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Cr�dito");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "2. Venta Activo Fijo");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
	
		
		pageEmisionDTE.SeleccionartipoDoc(cp, "30 - Factura");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		
		pageEmisionDTE.BtnEmitirNotaCredito(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE CR�DITO ELECTR�NICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Credito") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("190") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("120") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[5]/td[3]")).getText().contains("1.310") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[1]")).getText().contains("Factura") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[4]")).getText().contains("Anula Documento   -  Anula Documento:")){
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
		String cp = "FA_0007";
		// Emisi�n DTE - Individual - Nota de Cr�dito - Cr�dito - Venta bien ra�z
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisi�n DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Cr�dito Electr�nica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Cr�dito");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "3. Venta Bien Ra�z");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
	
		
		pageEmisionDTE.SeleccionartipoDoc(cp, "30 - Factura");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		
		pageEmisionDTE.BtnEmitirNotaCredito(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE CR�DITO ELECTR�NICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Credito") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("190") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("120") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[5]/td[3]")).getText().contains("1.310") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[1]")).getText().contains("Factura") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[4]")).getText().contains("Anula Documento   -  Anula Documento:")){
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
		String cp = "FA_0008";
		// Emisi�n DTE - Individual - Nota de Cr�dito - Sin Costo - Ventas del Giro
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisi�n DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Cr�dito Electr�nica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Sin Costo");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "1. Ventas del Giro");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
	
		
		pageEmisionDTE.SeleccionartipoDoc(cp, "30 - Factura");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		
		pageEmisionDTE.BtnEmitirNotaCredito(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE CR�DITO ELECTR�NICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Sin Costo (entrega gratuita)") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("190") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("120") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[5]/td[3]")).getText().contains("1.310") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[1]")).getText().contains("Factura") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[4]")).getText().contains("Anula Documento   -  Anula Documento:")){
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
	public void Script_0009() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "FA_0009";
		// Emisi�n DTE - Individual - Nota de Cr�dito - Sin Costo - Venta activo fijo
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisi�n DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Cr�dito Electr�nica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Sin Costo");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "2. Venta Activo Fijo");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
	
		
		pageEmisionDTE.SeleccionartipoDoc(cp, "30 - Factura");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		
		pageEmisionDTE.BtnEmitirNotaCredito(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE CR�DITO ELECTR�NICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Sin Costo (entrega gratuita)") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("190") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("120") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[5]/td[3]")).getText().contains("1.310") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[1]")).getText().contains("Factura") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[4]")).getText().contains("Anula Documento   -  Anula Documento:")){
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
	public void Script_0010() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "FA_0010";
		// Emisi�n DTE - Individual - Nota de Cr�dito - Sin Costo - Venta bien ra�z
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisi�n DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Cr�dito Electr�nica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Sin Costo");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "3. Venta Bien Ra�z");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
	
		
		pageEmisionDTE.SeleccionartipoDoc(cp, "30 - Factura");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		
		pageEmisionDTE.BtnEmitirNotaCredito(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE CR�DITO ELECTR�NICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Sin Costo (entrega gratuita)") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("190") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("120") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[5]/td[3]")).getText().contains("1.310") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[1]")).getText().contains("Factura") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[4]")).getText().contains("Anula Documento   -  Anula Documento:")){
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
		String cp = "FA_0012";
		// Emisi�n DTE - Individual - Nota de Cr�dito - Factura Turista datos ok
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisi�n DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Cr�dito Electr�nica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "1. Ventas del Giro");
		
		pageEmisionDTE.CheckboxFacturaTuristica(cp);
		pageEmisionDTE.AgregarNumeroIdExtranjero(cp, "55555555-5");
		pageEmisionDTE.SeleccionarTipoDoctoTurista(cp, "DNI");
		pageEmisionDTE.AgregarGiroExtranjero(cp, "prueba");
		pageEmisionDTE.AgregarDireccionExtranjero(cp, "AVDA. DEL VALLE N� 537");
		pageEmisionDTE.AgregarComunaExtranjero(cp, "HUECHURABA");
		pageEmisionDTE.AgregarCiudadExtranjero(cp, "SANTIAGO");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
	
		
		pageEmisionDTE.SeleccionartipoDoc(cp, "30 - Factura");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		
		pageEmisionDTE.BtnEmitirNotaCredito(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE CR�DITO ELECTR�NICA") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("Extranjero sin RUT") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("55.555.555-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[4]/td[2]")).getText().contains("prueba") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[5]/td[2]")).getText().contains("AVDA. DEL VALLE N� 537") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[6]/td[2]")).getText().contains("HUECHURABA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[7]/td[2]")).getText().contains("SANTIAGO") &&

				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("190") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("120") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[5]/td[3]")).getText().contains("1.310") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[1]")).getText().contains("Factura") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[4]")).getText().contains("Anula Documento   -  Anula Documento:")){
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
		String cp = "FA_0013";
		// Emisi�n DTE - Individual - Nota de Cr�dito - Indicador de no rebaja
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisi�n DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Cr�dito Electr�nica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "1. Ventas del Giro");
		
		pageEmisionDTE.CheckboxIndicadorNoRebaja(cp);
		
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
	
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
	
		
		pageEmisionDTE.SeleccionartipoDoc(cp, "30 - Factura");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		
		pageEmisionDTE.BtnEmitirNotaCredito(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE CR�DITO ELECTR�NICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&

				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("190") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("120") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[5]/td[3]")).getText().contains("1.310") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[1]")).getText().contains("Factura") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[4]")).getText().contains("Anula Documento   -  Anula Documento:")){
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
	public void Script_0018() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "FA_0018";
		//Emisi�n DTE - Individual - Nota de Cr�dito - producto con impuesto harina retenci�n 12%
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisi�n DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Cr�dito Electr�nica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "1. Ventas del Giro");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
	
		
		pageEmisionDTE.SeleccionartipoDoc(cp, "30 - Factura");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		
		pageEmisionDTE.BtnEmitirNotaCredito(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE CR�DITO ELECTR�NICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("190") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("120") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[5]/td[3]")).getText().contains("1.310") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[1]")).getText().contains("Factura") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[4]")).getText().contains("Anula Documento   -  Anula Documento:")){
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
	public void Script_0019() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "FA_0019";
		// Emisi�n DTE - Individual - Nota de Cr�dito - producto con impuesto Licores, piscos, whisky, aguardiente y vinos licorosos o aromatizados Imp. Adicional 31,5
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisi�n DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Cr�dito Electr�nica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "1. Ventas del Giro");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProducto1(cp, "000002", "1");
	
		
		pageEmisionDTE.SeleccionartipoDoc(cp, "30 - Factura");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		
		pageEmisionDTE.BtnEmitirNotaCredito(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE CR�DITO ELECTR�NICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000002") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Licores 31.5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("uni") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("2000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("2000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("2.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("380") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("2.380") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[1]")).getText().contains("Factura") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[4]")).getText().contains("Anula Documento   -  Anula Documento:")){
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
		String cp = "FA_0020";
		// Emisi�n DTE - Individual - Nota de Cr�dito - producto con impuesto vino Imp adicional 20,5%
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisi�n DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Cr�dito Electr�nica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "1. Ventas del Giro");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProducto1(cp, "000003", "1");
	
		
		pageEmisionDTE.SeleccionartipoDoc(cp, "30 - Factura");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		
		pageEmisionDTE.BtnEmitirNotaCredito(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE CR�DITO ELECTR�NICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000003") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("vino adicional 20.5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("uni") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("3000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("3000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("3.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("570") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("615") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[5]/td[3]")).getText().contains("4.185") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[1]")).getText().contains("Factura") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[4]")).getText().contains("Anula Documento   -  Anula Documento:")){
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
		String cp = "FA_0021";
		// Emisi�n DTE - Individual - Nota de Cr�dito - producto con impuesto Bovino retenci�n 5%
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisi�n DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Cr�dito Electr�nica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "1. Ventas del Giro");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProducto1(cp, "000004", "1");
	
		
		pageEmisionDTE.SeleccionartipoDoc(cp, "30 - Factura");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		
		pageEmisionDTE.BtnEmitirNotaCredito(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE CR�DITO ELECTR�NICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000004") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("bobino retencion 5%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("KG") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("4000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("4000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("4.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("760") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("200") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[5]/td[3]")).getText().contains("4.960") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[1]")).getText().contains("Factura") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[4]")).getText().contains("Anula Documento   -  Anula Documento:")){
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
		String cp = "FA_0022";
		// Emisi�n DTE - Individual - Nota de Cr�dito - producto con impuesto Cerveza y Bebidas alcoh�licas; Imp Adicional 20,5%
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisi�n DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Cr�dito Electr�nica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "1. Ventas del Giro");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProducto1(cp, "000005", "1");
	
		
		pageEmisionDTE.SeleccionartipoDoc(cp, "30 - Factura");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		
		pageEmisionDTE.BtnEmitirNotaCredito(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE CR�DITO ELECTR�NICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000005") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("bebidas analcoholicas 20.5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("uni") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("190") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("100") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[5]/td[3]")).getText().contains("1.290") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[1]")).getText().contains("Factura") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[4]")).getText().contains("Anula Documento   -  Anula Documento:")){
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
		String cp = "FA_0023";
		// Emisi�n DTE - Individual - Nota de Cr�dito - producto con impuesto bebidas analcoh�licas y minerales; imp Adicional 10%
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisi�n DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Cr�dito Electr�nica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "1. Ventas del Giro");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProducto1(cp, "000007", "1");
	
		
		pageEmisionDTE.SeleccionartipoDoc(cp, "30 - Factura");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		
		pageEmisionDTE.BtnEmitirNotaCredito(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE CR�DITO ELECTR�NICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000007") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("bebidas azucaradas 18%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("uni") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("5000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("5000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("5.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("950") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("900") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[5]/td[3]")).getText().contains("6.850") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[1]")).getText().contains("Factura") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[4]")).getText().contains("Anula Documento   -  Anula Documento:")){
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
		String cp = "FA_0024";
		// Emisi�n DTE - Individual - Nota de Cr�dito - producto con impuesto Bebidas analcoh�licas y minerales con alto contenido de az�car; Imp Adicional 18%
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisi�n DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Cr�dito Electr�nica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "1. Ventas del Giro");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProducto1(cp, "000007", "1");
	
		
		pageEmisionDTE.SeleccionartipoDoc(cp, "30 - Factura");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		
		pageEmisionDTE.BtnEmitirNotaCredito(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE CR�DITO ELECTR�NICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000007") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("bebidas azucaradas 18%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("uni") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("5000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("5000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("5.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("950") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("900") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[5]/td[3]")).getText().contains("6.850") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[1]")).getText().contains("Factura") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[4]")).getText().contains("Anula Documento   -  Anula Documento:")){
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
		String cp = "FA_0025";
		// Emisi�n DTE - Individual - Nota de Cr�dito - producto con impuesto especifico a la gasolina 93; Imp. Adicional de 4,5 a 6 UTM por m3
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisi�n DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Cr�dito Electr�nica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "1. Ventas del Giro");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProducto1(cp, "000008", "1");
	
		
		pageEmisionDTE.SeleccionartipoDoc(cp, "30 - Factura");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		
		pageEmisionDTE.BtnEmitirNotaCredito(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE CR�DITO ELECTR�NICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000008") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Gasolina 93") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("M3") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("800") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("800") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("800") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("152") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("291") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[5]/td[3]")).getText().contains("1.243") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[1]")).getText().contains("Factura") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[4]")).getText().contains("Anula Documento   -  Anula Documento:")){
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
		String cp = "FA_0026";
		// Emisi�n DTE - Individual - Nota de Cr�dito - producto con impuesto especifico a la gasolina 97; Imp. Adicional de 4,5 a 6 UTM por m3
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisi�n DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Cr�dito Electr�nica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "1. Ventas del Giro");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProducto1(cp, "000009", "1");
	
		
		pageEmisionDTE.SeleccionartipoDoc(cp, "30 - Factura");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		
		pageEmisionDTE.BtnEmitirNotaCredito(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE CR�DITO ELECTR�NICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000009") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("gasolina 97") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("M3") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("900") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("900") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("900") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("171") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("297") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[5]/td[3]")).getText().contains("1.368") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[1]")).getText().contains("Factura") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[4]")).getText().contains("Anula Documento   -  Anula Documento:")){
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
	public void Script_0027() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "FA_0027";
		// Emisi�n DTE - Individual - Nota de Cr�dito - producto con impuesto compra Diesel; Imp. Adicional 1,5 UTM por m3
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisi�n DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Cr�dito Electr�nica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "1. Ventas del Giro");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProducto1(cp, "000010", "1");
	
		
		pageEmisionDTE.SeleccionartipoDoc(cp, "30 - Factura");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		
		pageEmisionDTE.BtnEmitirNotaCredito(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE CR�DITO ELECTR�NICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000010") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Diesel") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("M3") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("500") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("500") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("95") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("70") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[5]/td[3]")).getText().contains("665") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[1]")).getText().contains("Factura") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[4]")).getText().contains("Anula Documento   -  Anula Documento:")){
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
	public void Script_0028() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "FA_0028";
		// Emisi�n DTE - Individual - Nota de Cr�dito - producto con impuesto Gas Natural comprimido; Imp adicional 1,93 por KM3
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisi�n DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Cr�dito Electr�nica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "1. Ventas del Giro");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProducto1(cp, "000011", "1");
	
		
		pageEmisionDTE.SeleccionartipoDoc(cp, "30 - Factura");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		
		pageEmisionDTE.BtnEmitirNotaCredito(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE CR�DITO ELECTR�NICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000011") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("gas natural") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("M3") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1200") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1200") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.200") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("228") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("82") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[5]/td[3]")).getText().contains("1.510") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[1]")).getText().contains("Factura") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[4]")).getText().contains("Anula Documento   -  Anula Documento:")){
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
		String cp = "FA_0029";
		// Emisi�n DTE - Individual - Nota de Cr�dito - producto con impuesto Gas licuado de petr�leo; Imp adicional 1,4 por M3
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisi�n DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Cr�dito Electr�nica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "1. Ventas del Giro");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProducto1(cp, "000012", "1");
	
		
		pageEmisionDTE.SeleccionartipoDoc(cp, "30 - Factura");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		
		pageEmisionDTE.BtnEmitirNotaCredito(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE CR�DITO ELECTR�NICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000012") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Gas licuado") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("M3") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1330") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1330") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.330") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("253") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("53.899") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[5]/td[3]")).getText().contains("55.482") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[1]")).getText().contains("Factura") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[4]")).getText().contains("Anula Documento   -  Anula Documento:")){
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
	public void Script_0030() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "FA_0030";
		// Emisi�n DTE - Individual - Nota de Cr�dito - producto con impuesto Faenamiento Bovino retenci�n 5%
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisi�n DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Cr�dito Electr�nica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "1. Ventas del Giro");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProductoFaenamientoBovino(cp, "000013", "20000", "25000", "1");
	
		
		pageEmisionDTE.SeleccionartipoDoc(cp, "30 - Factura");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		
		pageEmisionDTE.BtnEmitirNotaCredito(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE CR�DITO ELECTR�NICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000013") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("faenamiento bovino") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("KG") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("190") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("50") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[5]/td[3]")).getText().contains("1.240") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[1]")).getText().contains("Factura") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[4]")).getText().contains("Anula Documento   -  Anula Documento:")){
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
	public void Script_0031() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "FA_0031";
		// Emisi�n DTE - Individual - Nota de Cr�dito - producto con impuesto Retenci�n 5%
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisi�n DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Cr�dito Electr�nica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "1. Ventas del Giro");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProductoFaenamientoBovino(cp, "000014", "20000", "25000", "1");
	
		
		pageEmisionDTE.SeleccionartipoDoc(cp, "30 - Factura");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		
		pageEmisionDTE.BtnEmitirNotaCredito(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE CR�DITO ELECTR�NICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000014") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("retencion") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("KG") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("10000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("10000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("10.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("1.900") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("500") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[5]/td[3]")).getText().contains("12.400") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[1]")).getText().contains("Factura") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[4]")).getText().contains("Anula Documento   -  Anula Documento:")){
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
	public void Script_0032() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "FA_0032";
		// Emisi�n DTE - Individual - Nota de Cr�dito - producto con impuesto IVA de margen de comercializaci�n
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisi�n DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Cr�dito Electr�nica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "1. Ventas del Giro");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProductoIVAMargenCom(cp, "000016", "KG", "1", "10", "12000");
	
		
		pageEmisionDTE.SeleccionartipoDoc(cp, "30 - Factura");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		
		pageEmisionDTE.BtnEmitirNotaCredito(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE CR�DITO ELECTR�NICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000016") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("IVA Margen comercializaci�n") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("KG") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("190") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("22.800") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[5]/td[3]")).getText().contains("1.290") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[1]")).getText().contains("Factura") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[4]")).getText().contains("Anula Documento   -  Anula Documento:")){
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
	public void Script_0033() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "FA_0033";
		// Emisi�n DTE - Individual - Nota de Cr�dito - producto con retenci�n 19%
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisi�n DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Cr�dito Electr�nica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "1. Ventas del Giro");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProductoUnidadMedida(cp, "1500", "1", "TO");
		
		pageEmisionDTE.SeleccionartipoDoc(cp, "30 - Factura");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		
		pageEmisionDTE.BtnEmitirNotaCredito(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE CR�DITO ELECTR�NICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("15") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("bebidas") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("30000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("30000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("30.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("5.700") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("5.700") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[5]/td[3]")).getText().contains("30.000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[1]")).getText().contains("Factura") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[4]")).getText().contains("Anula Documento   -  Anula Documento:")){
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
	public void Script_0034() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "FA_0034";
		// Emisi�n DTE - Individual - Nota de Cr�dito - producto con Frijoles retenci�n 13% o 19%
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisi�n DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Cr�dito Electr�nica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "1. Ventas del Giro");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProducto1(cp, "3001", "1");
		
		pageEmisionDTE.SeleccionartipoDoc(cp, "30 - Factura");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		
		pageEmisionDTE.BtnEmitirNotaCredito(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE CR�DITO ELECTR�NICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("30") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Prod_2 ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("531823") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("531823") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("531.823") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("101.046") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("69.137") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[5]/td[3]")).getText().contains("563.732") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[1]")).getText().contains("Factura") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[4]")).getText().contains("Anula Documento   -  Anula Documento:")){
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
	public void Script_0035() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "FA_0035";
		// Emisi�n DTE - Individual - Nota de Cr�dito - producto con lenteja retenci�n 13% o 19%
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisi�n DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Cr�dito Electr�nica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "1. Ventas del Giro");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProducto1(cp, "3002", "1");
		
		pageEmisionDTE.SeleccionartipoDoc(cp, "30 - Factura");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		
		pageEmisionDTE.BtnEmitirNotaCredito(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE CR�DITO ELECTR�NICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("30") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Lenteja retenci�n 13% o 19%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("190") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("130") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[5]/td[3]")).getText().contains("1.060") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[1]")).getText().contains("Factura") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[4]")).getText().contains("Anula Documento   -  Anula Documento:")){
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
	public void Script_0036() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "FA_0036";
		// Emisi�n DTE - Individual - Nota de Cr�dito - producto con garbanzo retenci�n 13% o 19%
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisi�n DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Cr�dito Electr�nica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "1. Ventas del Giro");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProducto1(cp, "3003", "1");
		
		pageEmisionDTE.SeleccionartipoDoc(cp, "30 - Factura");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		
		pageEmisionDTE.BtnEmitirNotaCredito(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE CR�DITO ELECTR�NICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("30") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Garbanzo") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("800") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("800") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("800") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("152") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("104") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[5]/td[3]")).getText().contains("848") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[1]")).getText().contains("Factura") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[4]")).getText().contains("Anula Documento   -  Anula Documento:")){
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
	public void Script_0037() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "FA_0037";
		// Emisi�n DTE - Individual - Nota de Cr�dito - producto con retenci�n total
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisi�n DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Cr�dito Electr�nica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "1. Ventas del Giro");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProductoConRetencionTotal(cp, "000001", "1");
		
		
		pageEmisionDTE.SeleccionartipoDoc(cp, "30 - Factura");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		
		pageEmisionDTE.BtnEmitirNotaCredito(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE CR�DITO ELECTR�NICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("190") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("120") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[5]/td[3]")).getText().contains("1.310") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[1]")).getText().contains("Factura") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[4]")).getText().contains("Anula Documento   -  Anula Documento:")){
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
	public void Script_0038() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "FA_0038";
		// Emisi�n DTE - Individual - Nota de Cr�dito - producto con IVA exento
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisi�n DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Cr�dito Electr�nica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "1. Ventas del Giro");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProductoConIvaExento(cp, "000001", "1");
		
		
		pageEmisionDTE.SeleccionartipoDoc(cp, "30 - Factura");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		
		pageEmisionDTE.BtnEmitirNotaCredito(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE CR�DITO ELECTR�NICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("1.000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[1]")).getText().contains("Factura") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[4]")).getText().contains("Anula Documento   -  Anula Documento:")){
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
	public void Script_0039() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "FA_0039";
		// Emisi�n DTE - Individual - Nota de Cr�dito - producto con descuento en $
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisi�n DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Cr�dito Electr�nica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "1. Ventas del Giro");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProductoDescuentoPeso(cp, "000001", "1", "200");
		
		
		pageEmisionDTE.SeleccionartipoDoc(cp, "30 - Factura");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		
		pageEmisionDTE.BtnEmitirNotaCredito(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE CR�DITO ELECTR�NICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("200") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[7]")).getText().contains("800") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("190") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("1.190") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[1]")).getText().contains("Factura") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[4]")).getText().contains("Anula Documento   -  Anula Documento:")){
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
	public void Script_0040() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "FA_0040";
		// Emisi�n DTE - Individual - Nota de Cr�dito - producto con descuento en %
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisi�n DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Cr�dito Electr�nica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "1. Ventas del Giro");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
	
		pageEmisionDTE.AgregarProductoDescuentoPrc(cp, "000001", "1", "5");		
		
		pageEmisionDTE.SeleccionartipoDoc(cp, "30 - Factura");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		
		pageEmisionDTE.BtnEmitirNotaCredito(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE CR�DITO ELECTR�NICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("50") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[7]")).getText().contains("950") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("190") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("1.190") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[1]")).getText().contains("Factura") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[4]")).getText().contains("Anula Documento   -  Anula Documento:")){
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
	public void Script_0041() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "FA_0041";
		// Emisi�n DTE - Individual - Nota de Cr�dito - producto con c�digo �tem
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisi�n DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Cr�dito Electr�nica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "1. Ventas del Giro");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
	
		pageEmisionDTE.AgregarProductoCodigoItem(cp, "000001", "1", "01", "500");
		
		pageEmisionDTE.SeleccionartipoDoc(cp, "30 - Factura");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		
		pageEmisionDTE.BtnEmitirNotaCredito(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE CR�DITO ELECTR�NICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("190") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("1.190") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[1]")).getText().contains("Factura") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[4]")).getText().contains("Anula Documento   -  Anula Documento:")){
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
	public void Script_0042() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "FA_0042";
		// Emisi�n DTE - Individual - Nota de Cr�dito - producto - agregar
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisi�n DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Cr�dito Electr�nica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "1. Ventas del Giro");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
	
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
		
		pageEmisionDTE.SeleccionartipoDoc(cp, "30 - Factura");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		
		pageEmisionDTE.BtnEmitirNotaCredito(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE CR�DITO ELECTR�NICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("190") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("1.190") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[1]")).getText().contains("Factura") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[4]")).getText().contains("Anula Documento   -  Anula Documento:")){
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
	public void Script_0044() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "FA_0044";
		// Emisi�n DTE - Individual - Nota de Cr�dito - referencia - factura
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisi�n DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Cr�dito Electr�nica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "1. Ventas del Giro");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
	
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
		
		pageEmisionDTE.SeleccionartipoDoc(cp, "30 - Factura");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		
		pageEmisionDTE.BtnEmitirNotaCredito(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE CR�DITO ELECTR�NICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("190") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("1.190") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[1]")).getText().contains("Factura") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[4]")).getText().contains("Anula Documento   -  Anula Documento:")){
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
	public void Script_0045() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "FA_0045";
		// Emisi�n DTE - Individual - Nota de Cr�dito - referencia - Factura No Afecta
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisi�n DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Cr�dito Electr�nica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "1. Ventas del Giro");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
	
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
		
		pageEmisionDTE.SeleccionartipoDoc(cp, "32 - Factura No Afecta");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		
		pageEmisionDTE.BtnEmitirNotaCredito(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE CR�DITO ELECTR�NICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("190") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("1.190") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[1]")).getText().contains("Factura") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[4]")).getText().contains("Anula Documento   -  Anula Documento:")){
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
	public void Script_0046() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "FA_0046";
		// Emisi�n DTE - Individual - Nota de Cr�dito - referencia - Factura Afecta Electr�nica
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisi�n DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Cr�dito Electr�nica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "1. Ventas del Giro");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
	
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
		
		pageEmisionDTE.SeleccionartipoDoc(cp, "33 - Factura Afecta Electr�nica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		
		pageEmisionDTE.BtnEmitirNotaCredito(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE CR�DITO ELECTR�NICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("190") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("1.190") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[1]")).getText().contains("Factura") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[4]")).getText().contains("Anula Documento   -  Anula Documento:")){
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
	public void Script_0047() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "FA_0047";
		// Emisi�n DTE - Individual - Nota de Cr�dito - referencia - Factura Exenta Electr�nica
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisi�n DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Cr�dito Electr�nica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "1. Ventas del Giro");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
	
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
		
		pageEmisionDTE.SeleccionartipoDoc(cp, "34 - Factura Exenta Electr�nica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		
		pageEmisionDTE.BtnEmitirNotaCredito(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE CR�DITO ELECTR�NICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("190") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("1.190") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[1]")).getText().contains("Factura") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[4]")).getText().contains("Anula Documento   -  Anula Documento:")){
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
	public void Script_0048() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "FA_0048";
		// Emisi�n DTE - Individual - Nota de Cr�dito - referencia - Boleta
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisi�n DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Cr�dito Electr�nica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "1. Ventas del Giro");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
	
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
		
		pageEmisionDTE.SeleccionartipoDoc(cp, "35 - Boleta");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		
		pageEmisionDTE.BtnEmitirNotaCredito(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE CR�DITO ELECTR�NICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("190") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("1.190") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[1]")).getText().contains("Boleta") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[4]")).getText().contains("Anula Documento   -  Anula Documento:")){
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
	public void Script_0049() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "FA_0049";
		// Emisi�n DTE - Individual - Nota de Cr�dito - referencia - Boleta Exenta
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisi�n DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Cr�dito Electr�nica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "1. Ventas del Giro");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
	
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
		
		pageEmisionDTE.SeleccionartipoDoc(cp, "38 - Boleta Exenta");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		
		pageEmisionDTE.BtnEmitirNotaCredito(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE CR�DITO ELECTR�NICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("190") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("1.190") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[1]")).getText().contains("Boleta") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[4]")).getText().contains("Anula Documento   -  Anula Documento:")){
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
	public void Script_0050() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "FA_0050";
		// Emisi�n DTE - Individual - Nota de Cr�dito - referencia - Liquidaci�n Factura
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisi�n DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Cr�dito Electr�nica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "1. Ventas del Giro");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
	
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
		
		pageEmisionDTE.SeleccionartipoDoc(cp, "40 - Liquidaci�n Factura");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		
		pageEmisionDTE.BtnEmitirNotaCredito(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE CR�DITO ELECTR�NICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("190") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("1.190") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[1]")).getText().contains("Liquidaci�n Factura") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[4]")).getText().contains("Anula Documento   -  Anula Documento:")){
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
	public void Script_0051() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "FA_0051";
		// Emisi�n DTE - Individual - Nota de Cr�dito - referencia - Liquidaci�n Factura Electr�nica
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisi�n DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Cr�dito Electr�nica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "1. Ventas del Giro");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
	
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
		
		pageEmisionDTE.SeleccionartipoDoc(cp, "43 - Liquidaci�n Factura Electr�nica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		
		pageEmisionDTE.BtnEmitirNotaCredito(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE CR�DITO ELECTR�NICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("190") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("1.190") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[1]")).getText().contains("Liquidaci�n Factura") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[4]")).getText().contains("Anula Documento   -  Anula Documento:")){
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
	public void Script_0052() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "FA_0052";
		// Emisi�n DTE - Individual - Nota de Cr�dito - referencia - Factura de Compra
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisi�n DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Cr�dito Electr�nica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "1. Ventas del Giro");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
	
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
		
		pageEmisionDTE.SeleccionartipoDoc(cp, "45 - Factura de Compra");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		
		pageEmisionDTE.BtnEmitirNotaCredito(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE CR�DITO ELECTR�NICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("190") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("1.190") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[1]")).getText().contains("Factura de Compra") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[4]")).getText().contains("Anula Documento   -  Anula Documento:")){
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
	public void Script_0053() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "FA_0053";
		// Emisi�n DTE - Individual - Nota de Cr�dito - referencia - Factura de Compra Electr�nica
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisi�n DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Cr�dito Electr�nica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "1. Ventas del Giro");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
	
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
		
		pageEmisionDTE.SeleccionartipoDoc(cp, "46 - Factura de Compra Electr�nica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		
		pageEmisionDTE.BtnEmitirNotaCredito(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE CR�DITO ELECTR�NICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("190") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("1.190") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[1]")).getText().contains("Factura de Compra") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[4]")).getText().contains("Anula Documento   -  Anula Documento:")){
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
	public void Script_0054() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "FA_0054";
		// Emisi�n DTE - Individual - Nota de Cr�dito - referencia - Nota de Debito
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisi�n DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Cr�dito Electr�nica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "1. Ventas del Giro");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
	
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
		
		pageEmisionDTE.SeleccionartipoDoc(cp, "55 - Nota de D�bito");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		
		pageEmisionDTE.BtnEmitirNotaCredito(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE CR�DITO ELECTR�NICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("190") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("1.190") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[1]")).getText().contains("Nota de D�bito") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[4]")).getText().contains("Anula Documento   -  Anula Documento:")){
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
	public void Script_0055() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "FA_0055";
		// Emisi�n DEmisi�n DTE - Individual - Nota de Cr�dito - referencia - Nota de Debito Electr�nica
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisi�n DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Cr�dito Electr�nica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "1. Ventas del Giro");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
	
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
		
		pageEmisionDTE.SeleccionartipoDoc(cp, "56 - Nota de D�bito Electr�nica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		
		pageEmisionDTE.BtnEmitirNotaCredito(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE CR�DITO ELECTR�NICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("190") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("1.190") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[1]")).getText().contains("Nota de D�bito") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[4]")).getText().contains("Anula Documento   -  Anula Documento:")){
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
	public void Script_0056() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "FA_0056";
		// Emisi�n DTE - Individual - Nota de Cr�dito - referencia - Liquidaci�n
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisi�n DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Cr�dito Electr�nica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "1. Ventas del Giro");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
	
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
		
		pageEmisionDTE.SeleccionartipoDoc(cp, "103 - Liquidaci�n");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		
		pageEmisionDTE.BtnEmitirNotaCredito(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE CR�DITO ELECTR�NICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("190") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("1.190") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[1]")).getText().contains("Liquidaci�n") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[4]")).getText().contains("Anula Documento   -  Anula Documento:")){
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
	
	
	// REVISAR
	
	@Test
	public void Script_0057() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "FA_0057";
		// Emisi�n DTE - Individual - Nota de Cr�dito - referencia - Orden de Compra
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisi�n DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Cr�dito Electr�nica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "1. Ventas del Giro");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
	
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
		
		pageEmisionDTE.SeleccionartipoDoc(cp, "801 - Orden de Compra");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		
		pageEmisionDTE.BtnEmitirNotaCredito(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE CR�DITO ELECTR�NICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("190") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("1.190") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[1]")).getText().contains("Orden de Compra") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[4]")).getText().contains("Anula Documento   -  Anula Documento:")){
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
	public void Script_0065() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "FA_0065";
		// Emisi�n DTE - Individual - Nota de Cr�dito - referencia - Boleta Afecta Electr�nica
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisi�n DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Cr�dito Electr�nica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "1. Ventas del Giro");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
	
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
		
		pageEmisionDTE.SeleccionartipoDoc(cp, "39 - Boleta Afecta Electr�nica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		
		pageEmisionDTE.BtnEmitirNotaCredito(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE CR�DITO ELECTR�NICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("190") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("1.190") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[1]")).getText().contains("Boleta Electr�nica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[4]")).getText().contains("Anula Documento   -  Anula Documento:")){
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
	public void Script_0066() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "FA_0066";
		// Emisi�n DTE - Individual - Nota de Cr�dito - referencia - Boleta Exenta Electr�nica
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisi�n DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Cr�dito Electr�nica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "1. Ventas del Giro");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
	
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
		
		pageEmisionDTE.SeleccionartipoDoc(cp, "41 - Boleta Exenta Electr�nica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		
		pageEmisionDTE.BtnEmitirNotaCredito(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE CR�DITO ELECTR�NICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("190") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("1.190") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[1]")).getText().contains("Boleta No Afecta o Exenta Electr�nica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[4]")).getText().contains("Anula Documento   -  Anula Documento:")){
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
	public void Script_0068() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "FA_0068";
		// Emisi�n DTE - Individual - Nota de Cr�dito - referencia - con indicador de referencia global
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisi�n DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Cr�dito Electr�nica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "1. Ventas del Giro");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
	
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
		
		pageEmisionDTE.SeleccionartipoDoc(cp, "30 - Factura");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.ClickCheckboxIndicadorReferenciaGlobal(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		
		pageEmisionDTE.BtnEmitirNotaCredito(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE CR�DITO ELECTR�NICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("190") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("1.190") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[1]")).getText().contains("Factura") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("0")){
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
	public void Script_0070() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "FA_0070";
		// Emisi�n DTE - Individual - Nota de Cr�dito - Sin Transporte
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisi�n DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Cr�dito Electr�nica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "1. Ventas del Giro");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
	
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		
		pageEmisionDTE.SeleccionartipoDoc(cp, "30 - Factura");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		
		pageEmisionDTE.BtnEmitirNotaCredito(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE CR�DITO ELECTR�NICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("190") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("1.190") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[1]")).getText().contains("Factura") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[4]")).getText().contains("Anula Documento   -  Anula Documento:")){
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
	public void Script_0071() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "FA_0071";
		// Emisi�n DTE - Individual - Nota de Cr�dito - Agregar Transporte con Despacho por cuenta del Receptor
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisi�n DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Cr�dito Electr�nica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "1. Ventas del Giro");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
	
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.IngresoDatosTransporte(cp, "ABCD12", "11.111.111-1", "Juan P�rez P�rez", "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
		
		pageEmisionDTE.SeleccionartipoDoc(cp, "30 - Factura");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		
		pageEmisionDTE.BtnEmitirNotaCredito(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE CR�DITO ELECTR�NICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("190") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("1.190") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[1]")).getText().contains("Factura") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[4]")).getText().contains("Anula Documento   -  Anula Documento:") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[2]/td[2]")).getText().contains("ABCD12") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("11.111.111-1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[4]/td[2]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[5]/td[2]")).getText().contains("Santiago")){
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
	public void Script_0072() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "FA_0072";
		// Emisi�n DTE - Individual - Nota de Cr�dito - Agregar Transporte con Despacho por cuenta del Emisor
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisi�n DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Cr�dito Electr�nica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "1. Ventas del Giro");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
	
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.IngresoDatosTransporte(cp, "ABCD12", "11.111.111-1", "Juan P�rez P�rez", "Calle 1", "Santiago", "Metropolitana", "2 - Despacho por Cuenta del Emisor");
		
		pageEmisionDTE.SeleccionartipoDoc(cp, "30 - Factura");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		
		pageEmisionDTE.BtnEmitirNotaCredito(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE CR�DITO ELECTR�NICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("190") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("1.190") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[1]")).getText().contains("Factura") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[4]")).getText().contains("Anula Documento   -  Anula Documento:") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[2]/td[2]")).getText().contains("ABCD12") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("11.111.111-1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[4]/td[2]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[5]/td[2]")).getText().contains("Santiago")){
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
	public void Script_0073() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "FA_0073";
		// Emisi�n DTE - Individual - Nota de Cr�dito - Agregar Transporte con Despacho por cuenta del Emisor a Otras Instalaciones
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisi�n DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Cr�dito Electr�nica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "1. Ventas del Giro");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
	
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.IngresoDatosTransporte(cp, "ABCD12", "11.111.111-1", "Juan P�rez P�rez", "Calle 1", "Santiago", "Metropolitana", "3 - Despacho por Cuenta del Emisor a Otras Instalaciones");
		
		pageEmisionDTE.SeleccionartipoDoc(cp, "30 - Factura");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		
		pageEmisionDTE.BtnEmitirNotaCredito(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE CR�DITO ELECTR�NICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("190") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("1.190") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[1]")).getText().contains("Factura") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[4]")).getText().contains("Anula Documento   -  Anula Documento:") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[2]/td[2]")).getText().contains("ABCD12") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("11.111.111-1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[4]/td[2]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[2]/table/tbody/tr[5]/td[2]")).getText().contains("Santiago")){
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
	public void Script_0074() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "FA_0074";
		// Emisi�n DTE - Individual - Nota de Cr�dito - par�metros adicionales
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisi�n DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Cr�dito Electr�nica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "1. Ventas del Giro");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
	
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");

		pageEmisionDTE.IngresoParametrosAdicionales(cp, "PRUEBA QA", "11.111.111-1");
		
		pageEmisionDTE.SeleccionartipoDoc(cp, "30 - Factura");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		
		pageEmisionDTE.BtnEmitirNotaCredito(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE CR�DITO ELECTR�NICA") &&		
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[2]")).getText().contains("PRUEBA QA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[5]/td[2]")).getText().contains("11.111.111-1") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("190") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("1.190") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[1]")).getText().contains("Factura") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[4]")).getText().contains("Anula Documento   -  Anula Documento:")){
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
	public void Script_0075() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "FA_0075";
		// Emisi�n DTE - Individual - Nota de Cr�dito - par�metros agrupados adicionales
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisi�n DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Cr�dito Electr�nica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "1. Ventas del Giro");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
	
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");

		pageEmisionDTE.IngresoParametrosAgrupadosAdicionales(cp, "coca_cola", "Efectivo");
		
		pageEmisionDTE.SeleccionartipoDoc(cp, "30 - Factura");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		
		pageEmisionDTE.BtnEmitirNotaCredito(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE CR�DITO ELECTR�NICA") &&		
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("190") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("1.190") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[1]")).getText().contains("Factura") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[4]")).getText().contains("Anula Documento   -  Anula Documento:")){
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
	public void Script_0076() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "FA_0076";
		// Emisi�n DTE - Individual - Nota de Cr�dito - con observaciones
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisi�n DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Cr�dito Electr�nica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "1. Ventas del Giro");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
	
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");

		pageEmisionDTE.IngresoObservaciones(cp, "Observaci�n de Prueba");
		
		pageEmisionDTE.SeleccionartipoDoc(cp, "30 - Factura");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		
		pageEmisionDTE.BtnEmitirNotaCredito(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE CR�DITO ELECTR�NICA") &&		
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("190") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("1.190") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[1]")).getText().contains("Factura") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[4]")).getText().contains("Anula Documento   -  Anula Documento:") &&

				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[1]/table/tbody/tr[2]/td")).getText().contains("Observaci�n de Prueba")){
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
	public void Script_0077() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "FA_0077";
		// Emisi�n DTE - Individual - Nota de Cr�dito - sin observaciones
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisi�n DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Cr�dito Electr�nica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "1. Ventas del Giro");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
	
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");

		pageEmisionDTE.SeleccionartipoDoc(cp, "30 - Factura");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		
		pageEmisionDTE.BtnEmitirNotaCredito(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE CR�DITO ELECTR�NICA") &&		
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("190") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("1.190") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[1]")).getText().contains("Factura") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[4]")).getText().contains("Anula Documento   -  Anula Documento:")){
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
	public void Script_0079() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "FA_0079";
		// Emisi�n DTE - Individual - Nota de Cr�dito - monto exento - descuento/recargo - Descuento - %
		System.out.println(cp);
		String resultado1 = null;
		String resultado2 = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisi�n DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Cr�dito Electr�nica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "1. Ventas del Giro");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
	
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
		pageEmisionDTE.AgregarProductoCheckboxExento(cp, "000002", "1");

		pageEmisionDTE.SeleccionartipoDoc(cp, "30 - Factura");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		pageEmisionDTE.SwithSIDescuentoRecargo(cp);
		pageEmisionDTE.IngresoDescuentoPrc(cp, "10", "Descuento QA");
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		//Primera validaci�n
		if(driver.findElement(By.xpath("//*[@id=\"formEmitirdocumento_tablaTotales\"]/tbody/tr[3]/th")).getText().contains("Sub-Total Exento") &&
				driver.findElement(By.id("formEmitirdocumento_sub-tota-exento")).getAttribute("value").contains("2.000") &&
				driver.findElement(By.xpath("//*[@id=\"formEmitirdocumento_tablaTotales\"]/tbody/tr[4]/th")).getText().contains("Descuento Exento Descuento QA (10%)") &&
				driver.findElement(By.id("formEmitirdocumento_DescuentoExento")).getAttribute("value").contains("200") &&
				driver.findElement(By.xpath("//*[@id=\"formEmitirdocumento_tablaTotales\"]/tbody/tr[4]/td[2]/a")).isDisplayed()){
			resultado1 = "FLUJO OK";
		}
		else {
			resultado1 = "FLUJO NOOK";
		}
				
		pageEmisionDTE.BtnEmitirNotaCredito(cp);
		
		//Segunda validaci�n
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE CR�DITO ELECTR�NICA") &&		
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[3]/td[1]")).getText().contains("000002") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[3]/td[2]")).getText().contains("Licores 31.5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[3]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[3]/td[4]")).getText().contains("uni") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[3]/td[5]")).getText().contains("2000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[3]/td[6]")).getText().contains("2000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("200") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("1.800") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("190") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[5]/td[3]")).getText().contains("2.990") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[1]")).getText().contains("Factura") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[4]")).getText().contains("Anula Documento   -  Anula Documento:")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado2 = "FLUJO OK";
		}
		else {
			crearLogyDocumento.CasoNok(cp);
			System.out.println("FLUJO NOOK");
			resultado2 = "FLUJO NOOK";
		}

		assertEquals(resultado1, "FLUJO OK", "Se verifica resultado del test "+cp);
		assertEquals(resultado2, "FLUJO OK", "Se verifica resultado del test "+cp);
	}
	
	
	@Test
	public void Script_0080() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "FA_0080";
		// Emisi�n DTE - Individual - Nota de Cr�dito - monto exento - descuento/recargo - Descuento - pesos
		System.out.println(cp);
		String resultado1 = null;
		String resultado2 = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisi�n DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Cr�dito Electr�nica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "1. Ventas del Giro");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
	
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
		pageEmisionDTE.AgregarProductoCheckboxExento(cp, "000002", "1");

		pageEmisionDTE.SeleccionartipoDoc(cp, "30 - Factura");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		pageEmisionDTE.SwithSIDescuentoRecargo(cp);
		pageEmisionDTE.IngresoDescuentoPeso(cp, "300", "Descuento QA");
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		//Primera validaci�n
		if(driver.findElement(By.xpath("//*[@id=\"formEmitirdocumento_tablaTotales\"]/tbody/tr[3]/th")).getText().contains("Sub-Total Exento") &&
				driver.findElement(By.id("formEmitirdocumento_sub-tota-exento")).getAttribute("value").contains("2.000") &&
				driver.findElement(By.xpath("//*[@id=\"formEmitirdocumento_tablaTotales\"]/tbody/tr[4]/th")).getText().contains("Descuento Exento Descuento QA") &&
				driver.findElement(By.id("formEmitirdocumento_DescuentoExento")).getAttribute("value").contains("300") &&
				driver.findElement(By.xpath("//*[@id=\"formEmitirdocumento_tablaTotales\"]/tbody/tr[4]/td[2]/a")).isDisplayed()){
			resultado1 = "FLUJO OK";
		}
		else {
			resultado1 = "FLUJO NOOK";
		}
				
		pageEmisionDTE.BtnEmitirNotaCredito(cp);
		
		//Segunda validaci�n
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE CR�DITO ELECTR�NICA") &&		
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[3]/td[1]")).getText().contains("000002") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[3]/td[2]")).getText().contains("Licores 31.5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[3]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[3]/td[4]")).getText().contains("uni") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[3]/td[5]")).getText().contains("2000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[3]/td[6]")).getText().contains("2000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("300") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("1.700") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("190") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[5]/td[3]")).getText().contains("2.890") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[1]")).getText().contains("Factura") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[4]")).getText().contains("Anula Documento   -  Anula Documento:")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado2 = "FLUJO OK";
		}
		else {
			crearLogyDocumento.CasoNok(cp);
			System.out.println("FLUJO NOOK");
			resultado2 = "FLUJO NOOK";
		}

		assertEquals(resultado1, "FLUJO OK", "Se verifica resultado del test "+cp);
		assertEquals(resultado2, "FLUJO OK", "Se verifica resultado del test "+cp);
	}
	
	
	@Test
	public void Script_0081() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "FA_0081";
		// Emisi�n DTE - Individual - Nota de Cr�dito - monto exento - descuento/recargo - Recargo - %
		System.out.println(cp);
		String resultado1 = null;
		String resultado2 = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisi�n DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Cr�dito Electr�nica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "1. Ventas del Giro");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
	
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
		pageEmisionDTE.AgregarProductoCheckboxExento(cp, "000002", "1");

		pageEmisionDTE.SeleccionartipoDoc(cp, "30 - Factura");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		pageEmisionDTE.SwithSIDescuentoRecargo(cp);
		pageEmisionDTE.IngresoRecargoPrc(cp, "20", "Recargo QA");
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		//Primera validaci�n
		if(driver.findElement(By.xpath("//*[@id=\"formEmitirdocumento_tablaTotales\"]/tbody/tr[3]/th")).getText().contains("Sub-Total Exento") &&
				driver.findElement(By.id("formEmitirdocumento_sub-tota-exento")).getAttribute("value").contains("2.000") &&
				driver.findElement(By.xpath("//*[@id=\"formEmitirdocumento_tablaTotales\"]/tbody/tr[4]/th")).getText().contains("Recargo Exento Recargo QA (20%)") &&
				driver.findElement(By.id("formEmitirdocumento_RecargoExento")).getAttribute("value").contains("400") &&
				driver.findElement(By.xpath("//*[@id=\"formEmitirdocumento_tablaTotales\"]/tbody/tr[4]/td[2]/a")).isDisplayed()){
			resultado1 = "FLUJO OK";
		}
		else {
			resultado1 = "FLUJO NOOK";
		}
				
		pageEmisionDTE.BtnEmitirNotaCredito(cp);
		
		//Segunda validaci�n
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE CR�DITO ELECTR�NICA") &&		
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[3]/td[1]")).getText().contains("000002") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[3]/td[2]")).getText().contains("Licores 31.5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[3]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[3]/td[4]")).getText().contains("uni") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[3]/td[5]")).getText().contains("2000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[3]/td[6]")).getText().contains("2000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("400") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("2.400") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("190") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[5]/td[3]")).getText().contains("3.590") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[1]")).getText().contains("Factura") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[4]")).getText().contains("Anula Documento   -  Anula Documento:")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado2 = "FLUJO OK";
		}
		else {
			crearLogyDocumento.CasoNok(cp);
			System.out.println("FLUJO NOOK");
			resultado2 = "FLUJO NOOK";
		}

		assertEquals(resultado1, "FLUJO OK", "Se verifica resultado del test "+cp);
		assertEquals(resultado2, "FLUJO OK", "Se verifica resultado del test "+cp);
	}
	
	
	@Test
	public void Script_0082() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "FA_0082";
		// Emisi�n DTE - Individual - Nota de Cr�dito - monto exento - descuento/recargo - Recargo - pesos
		System.out.println(cp);
		String resultado1 = null;
		String resultado2 = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisi�n DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Cr�dito Electr�nica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "1. Ventas del Giro");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
	
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
		pageEmisionDTE.AgregarProductoCheckboxExento(cp, "000002", "1");

		pageEmisionDTE.SeleccionartipoDoc(cp, "30 - Factura");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		pageEmisionDTE.SwithSIDescuentoRecargo(cp);
		pageEmisionDTE.IngresoRecargoPeso(cp, "500", "Recargo QA");
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		//Primera validaci�n
		if(driver.findElement(By.xpath("//*[@id=\"formEmitirdocumento_tablaTotales\"]/tbody/tr[3]/th")).getText().contains("Sub-Total Exento") &&
				driver.findElement(By.id("formEmitirdocumento_sub-tota-exento")).getAttribute("value").contains("2.000") &&
				driver.findElement(By.xpath("//*[@id=\"formEmitirdocumento_tablaTotales\"]/tbody/tr[4]/th")).getText().contains("Recargo Exento Recargo QA") &&
				driver.findElement(By.id("formEmitirdocumento_RecargoExento")).getAttribute("value").contains("500") &&
				driver.findElement(By.xpath("//*[@id=\"formEmitirdocumento_tablaTotales\"]/tbody/tr[4]/td[2]/a")).isDisplayed()){
			resultado1 = "FLUJO OK";
		}
		else {
			resultado1 = "FLUJO NOOK";
		}
				
		pageEmisionDTE.BtnEmitirNotaCredito(cp);
		
		//Segunda validaci�n
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE CR�DITO ELECTR�NICA") &&		
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[3]/td[1]")).getText().contains("000002") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[3]/td[2]")).getText().contains("Licores 31.5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[3]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[3]/td[4]")).getText().contains("uni") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[3]/td[5]")).getText().contains("2000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[3]/td[6]")).getText().contains("2000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("500") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("2.500") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("190") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[5]/td[3]")).getText().contains("3.690") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[1]")).getText().contains("Factura") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[4]")).getText().contains("Anula Documento   -  Anula Documento:")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado2 = "FLUJO OK";
		}
		else {
			crearLogyDocumento.CasoNok(cp);
			System.out.println("FLUJO NOOK");
			resultado2 = "FLUJO NOOK";
		}

		assertEquals(resultado1, "FLUJO OK", "Se verifica resultado del test "+cp);
		assertEquals(resultado2, "FLUJO OK", "Se verifica resultado del test "+cp);
	}
	
	
	@Test
	public void Script_0083() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "FA_0083";
		// Emisi�n DTE - Individual - Nota de Cr�dito - monto exento - descuento/recargo - Eliminar
		System.out.println(cp);
		String resultado1 = null;
		String resultado2 = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisi�n DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Cr�dito Electr�nica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "1. Ventas del Giro");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
	
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
		pageEmisionDTE.AgregarProductoCheckboxExento(cp, "000002", "1");

		pageEmisionDTE.SeleccionartipoDoc(cp, "30 - Factura");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		pageEmisionDTE.SwithSIDescuentoRecargo(cp);
		pageEmisionDTE.IngresoRecargoPeso(cp, "500", "Recargo QA");
		pageEmisionDTE.ClickEliminar(cp);
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		//Primera validaci�n
		if(driver.findElement(By.xpath("//*[@id=\"columnaDescuentosExento\"]/div/label")).getText().contains("Descuento/Recargo") &&
				driver.findElement(By.xpath("//*[@id=\"columnaDescuentosExento\"]/div/div/div/label[2]")).getText().contains("No")){
			resultado1 = "FLUJO OK";
		}
		else {
			resultado1 = "FLUJO NOOK";
		}
				
		pageEmisionDTE.BtnEmitirNotaCredito(cp);
		
		//Segunda validaci�n
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE CR�DITO ELECTR�NICA") &&		
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[3]/td[1]")).getText().contains("000002") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[3]/td[2]")).getText().contains("Licores 31.5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[3]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[3]/td[4]")).getText().contains("uni") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[3]/td[5]")).getText().contains("2000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[3]/td[6]")).getText().contains("2000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("2.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("190") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("3.190") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[1]")).getText().contains("Factura") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[4]")).getText().contains("Anula Documento   -  Anula Documento:")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado2 = "FLUJO OK";
		}
		else {
			crearLogyDocumento.CasoNok(cp);
			System.out.println("FLUJO NOOK");
			resultado2 = "FLUJO NOOK";
		}

		assertEquals(resultado1, "FLUJO OK", "Se verifica resultado del test "+cp);
		assertEquals(resultado2, "FLUJO OK", "Se verifica resultado del test "+cp);
	}
	
	
	@Test
	public void Script_0085() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "FA_0085";
		// Emisi�n DTE - Individual - Nota de Cr�dito - monto neto - descuento/recargo - Descuento - %
		System.out.println(cp);
		String resultado1 = null;
		String resultado2 = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisi�n DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Cr�dito Electr�nica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "1. Ventas del Giro");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
	
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");

		pageEmisionDTE.SeleccionartipoDoc(cp, "30 - Factura");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		pageEmisionDTE.SwithSIDescuentoNeto(cp);
		pageEmisionDTE.IngresoDescuentoPrcNeto(cp, "20", "Descuento QA");
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		//Primera validaci�n
		if(driver.findElement(By.xpath("//*[@id=\"formEmitirdocumento_tablaTotales\"]/tbody/tr[3]/th")).getText().contains("Sub-Total Neto") &&
				driver.findElement(By.id("formEmitirdocumento_sub-tota-neto")).getAttribute("value").contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"formEmitirdocumento_tablaTotales\"]/tbody/tr[4]/th")).getText().contains("Descuento Neto Descuento QA (20%)") &&
				driver.findElement(By.id("formEmitirdocumento_DescuentoNeto")).getAttribute("value").contains("200") &&
				driver.findElement(By.xpath("//*[@id=\"formEmitirdocumento_tablaTotales\"]/tbody/tr[4]/td[2]/a")).isDisplayed()){
			resultado1 = "FLUJO OK";
		}
		else {
			resultado1 = "FLUJO NOOK";
		}
				
		pageEmisionDTE.BtnEmitirNotaCredito(cp);
		
		//Segunda validaci�n
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE CR�DITO ELECTR�NICA") &&		
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("200") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("800") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("152") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[5]/td[3]")).getText().contains("952") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[1]")).getText().contains("Factura") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[4]")).getText().contains("Anula Documento   -  Anula Documento:")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado2 = "FLUJO OK";
		}
		else {
			crearLogyDocumento.CasoNok(cp);
			System.out.println("FLUJO NOOK");
			resultado2 = "FLUJO NOOK";
		}

		assertEquals(resultado1, "FLUJO OK", "Se verifica resultado del test "+cp);
		assertEquals(resultado2, "FLUJO OK", "Se verifica resultado del test "+cp);
	}
	
	@Test
	public void Script_0086() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "FA_0086";
		// Emisi�n DTE - Individual - Nota de Cr�dito - monto neto - descuento/recargo - Descuento - pesos
		System.out.println(cp);
		String resultado1 = null;
		String resultado2 = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisi�n DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Cr�dito Electr�nica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "1. Ventas del Giro");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
	
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");

		pageEmisionDTE.SeleccionartipoDoc(cp, "30 - Factura");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		pageEmisionDTE.SwithSIDescuentoNeto(cp);
		pageEmisionDTE.IngresoDescuentoPesoNeto(cp, "300", "Descuento QA");
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		//Primera validaci�n
		if(driver.findElement(By.xpath("//*[@id=\"formEmitirdocumento_tablaTotales\"]/tbody/tr[3]/th")).getText().contains("Sub-Total Neto") &&
				driver.findElement(By.id("formEmitirdocumento_sub-tota-neto")).getAttribute("value").contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"formEmitirdocumento_tablaTotales\"]/tbody/tr[4]/th")).getText().contains("Descuento Neto Descuento QA") &&
				driver.findElement(By.id("formEmitirdocumento_DescuentoNeto")).getAttribute("value").contains("300") &&
				driver.findElement(By.xpath("//*[@id=\"formEmitirdocumento_tablaTotales\"]/tbody/tr[4]/td[2]/a")).isDisplayed()){
			resultado1 = "FLUJO OK";
		}
		else {
			resultado1 = "FLUJO NOOK";
		}
				
		pageEmisionDTE.BtnEmitirNotaCredito(cp);
		
		//Segunda validaci�n
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE CR�DITO ELECTR�NICA") &&		
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("300") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("700") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("133") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[5]/td[3]")).getText().contains("833") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[1]")).getText().contains("Factura") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[4]")).getText().contains("Anula Documento   -  Anula Documento:")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado2 = "FLUJO OK";
		}
		else {
			crearLogyDocumento.CasoNok(cp);
			System.out.println("FLUJO NOOK");
			resultado2 = "FLUJO NOOK";
		}

		assertEquals(resultado1, "FLUJO OK", "Se verifica resultado del test "+cp);
		assertEquals(resultado2, "FLUJO OK", "Se verifica resultado del test "+cp);
	}
	
	
	@Test
	public void Script_0087() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "FA_0087";
		// Emisi�n DTE - Individual - Nota de Cr�dito - monto neto - descuento/recargo - Recargo - %
		System.out.println(cp);
		String resultado1 = null;
		String resultado2 = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisi�n DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Cr�dito Electr�nica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "1. Ventas del Giro");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
	
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");

		pageEmisionDTE.SeleccionartipoDoc(cp, "30 - Factura");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		pageEmisionDTE.SwithSIDescuentoNeto(cp);
		pageEmisionDTE.IngresoRecargoPrcNeto(cp, "10", "Recargo QA");
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		//Primera validaci�n
		if(driver.findElement(By.xpath("//*[@id=\"formEmitirdocumento_tablaTotales\"]/tbody/tr[3]/th")).getText().contains("Sub-Total Neto") &&
				driver.findElement(By.id("formEmitirdocumento_sub-tota-neto")).getAttribute("value").contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"formEmitirdocumento_tablaTotales\"]/tbody/tr[4]/th")).getText().contains("Recargo Neto Recargo QA (10%)") &&
				driver.findElement(By.id("formEmitirdocumento_RecargoNeto")).getAttribute("value").contains("100") &&
				driver.findElement(By.xpath("//*[@id=\"formEmitirdocumento_tablaTotales\"]/tbody/tr[4]/td[2]/a")).isDisplayed()){
			resultado1 = "FLUJO OK";
		}
		else {
			resultado1 = "FLUJO NOOK";
		}
				
		pageEmisionDTE.BtnEmitirNotaCredito(cp);
		
		//Segunda validaci�n
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE CR�DITO ELECTR�NICA") &&		
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("100") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("1.100") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("209") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[5]/td[3]")).getText().contains("1.309") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[1]")).getText().contains("Factura") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[4]")).getText().contains("Anula Documento   -  Anula Documento:")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado2 = "FLUJO OK";
		}
		else {
			crearLogyDocumento.CasoNok(cp);
			System.out.println("FLUJO NOOK");
			resultado2 = "FLUJO NOOK";
		}

		assertEquals(resultado1, "FLUJO OK", "Se verifica resultado del test "+cp);
		assertEquals(resultado2, "FLUJO OK", "Se verifica resultado del test "+cp);
	}
	
	
	@Test
	public void Script_0088() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "FA_0088";
		// Emisi�n DTE - Individual - Nota de Cr�dito - monto neto - descuento/recargo - Recargo - pesos
		System.out.println(cp);
		String resultado1 = null;
		String resultado2 = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisi�n DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Cr�dito Electr�nica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "1. Ventas del Giro");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
	
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");

		pageEmisionDTE.SeleccionartipoDoc(cp, "30 - Factura");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		pageEmisionDTE.SwithSIDescuentoNeto(cp);
		pageEmisionDTE.IngresoRecargoPesoNeto(cp, "200", "Recargo QA");
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		//Primera validaci�n
		if(driver.findElement(By.xpath("//*[@id=\"formEmitirdocumento_tablaTotales\"]/tbody/tr[3]/th")).getText().contains("Sub-Total Neto") &&
				driver.findElement(By.id("formEmitirdocumento_sub-tota-neto")).getAttribute("value").contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"formEmitirdocumento_tablaTotales\"]/tbody/tr[4]/th")).getText().contains("Recargo Neto Recargo QA") &&
				driver.findElement(By.id("formEmitirdocumento_RecargoNeto")).getAttribute("value").contains("200") &&
				driver.findElement(By.xpath("//*[@id=\"formEmitirdocumento_tablaTotales\"]/tbody/tr[4]/td[2]/a")).isDisplayed()){
			resultado1 = "FLUJO OK";
		}
		else {
			resultado1 = "FLUJO NOOK";
		}
				
		pageEmisionDTE.BtnEmitirNotaCredito(cp);
		
		//Segunda validaci�n
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE CR�DITO ELECTR�NICA") &&		
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("200") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("1.200") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("228") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[5]/td[3]")).getText().contains("1.428") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[1]")).getText().contains("Factura") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[4]")).getText().contains("Anula Documento   -  Anula Documento:")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado2 = "FLUJO OK";
		}
		else {
			crearLogyDocumento.CasoNok(cp);
			System.out.println("FLUJO NOOK");
			resultado2 = "FLUJO NOOK";
		}

		assertEquals(resultado1, "FLUJO OK", "Se verifica resultado del test "+cp);
		assertEquals(resultado2, "FLUJO OK", "Se verifica resultado del test "+cp);
	}
	
	
	@Test
	public void Script_0089() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "FA_0089";
		// Emisi�n DTE - Individual - Nota de Cr�dito - monto Neto - descuento/recargo - Eliminar
		System.out.println(cp);
		String resultado1 = null;
		String resultado2 = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisi�n DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Cr�dito Electr�nica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "1. Ventas del Giro");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
	
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");

		pageEmisionDTE.SeleccionartipoDoc(cp, "30 - Factura");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		pageEmisionDTE.SwithSIDescuentoNeto(cp);
		pageEmisionDTE.IngresoRecargoPesoNeto(cp, "200", "Recargo QA");
		pageEmisionDTE.ClickEliminar(cp);
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		//Primera validaci�n
		if(driver.findElement(By.xpath("//*[@id=\"columnaDescuentosNeto\"]/div/label")).getText().contains("Descuento/Recargo") &&
				driver.findElement(By.xpath("//*[@id=\"columnaDescuentosNeto\"]/div/div/div/label[2]")).getText().contains("No")){
			resultado1 = "FLUJO OK";
		}
		else {
			resultado1 = "FLUJO NOOK";
		}
				
		pageEmisionDTE.BtnEmitirNotaCredito(cp);
		
		//Segunda validaci�n
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE CR�DITO ELECTR�NICA") &&		
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("190") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("1.190") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[1]")).getText().contains("Factura") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[4]")).getText().contains("Anula Documento   -  Anula Documento:")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado2 = "FLUJO OK";
		}
		else {
			crearLogyDocumento.CasoNok(cp);
			System.out.println("FLUJO NOOK");
			resultado2 = "FLUJO NOOK";
		}

		assertEquals(resultado1, "FLUJO OK", "Se verifica resultado del test "+cp);
		assertEquals(resultado2, "FLUJO OK", "Se verifica resultado del test "+cp);
	}
	
	@Test
	public void Script_0090() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "FA_0090";
		// Emisi�n DTE - Individual - Nota de Cr�dito - Limpiar
		System.out.println(cp);
		String resultado1 = null;
		String resultado2 = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisi�n DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Cr�dito Electr�nica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "1. Ventas del Giro");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
	
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
		pageEmisionDTE.ClickLimpiar(cp);

		List<WebElement> lista_TipoDocumento = new ArrayList<WebElement>();
		lista_TipoDocumento = pageEscritorio.BuscarTipoDocumento();
		
		// Definiendo la lista de referencia
		List<String> lista_referencia = new ArrayList<String>();
		lista_referencia.add("Documento a Emitir");
		lista_referencia.add("Factura Afecta Electr�nica");
		lista_referencia.add("Factura Exenta Electr�nica");
		lista_referencia.add("Nota de D�bito Electr�nica");
		lista_referencia.add("Nota de Cr�dito Electr�nica");
		lista_referencia.add("Gu�a de Despacho Electr�nica");
		lista_referencia.add("Boleta Afecta Electr�nica");
		lista_referencia.add("Boleta Exenta Electr�nica");
		lista_referencia.add("Factura de Exportaci�n Electr�nica");
		lista_referencia.add("Nota de D�bito de Exportaci�n Electr�nica");
		lista_referencia.add("Nota de Cr�dito de Exportaci�n Electr�nica");
		
		for (int i = 0; i <= lista_TipoDocumento.size() - 1; i++) {
			System.out.println("print gettext():");
			System.out.println(lista_TipoDocumento.get(i).getText());
			if(lista_TipoDocumento.get(i).getText().equals(lista_referencia.get(i))) {
				System.out.println("SON IGUALES");
				resultado1 = "FLUJO OK";
			}else {
				System.out.println("SON DISTINTOS");
				resultado2 = "FLUJO NOOK";
			}
		}
		
		System.out.println("resultado1: "+resultado1);
		System.out.println("resultado2: "+resultado2);
		
		if(resultado1.equals("FLUJO OK") && resultado2==null){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado2 = "FLUJO OK";
		}
		else {
			crearLogyDocumento.CasoNok(cp);
			System.out.println("FLUJO NOOK");
			resultado2 = "FLUJO NOOK";
		}
		
		assertEquals(resultado1, "FLUJO OK", "Se verifica resultado 1 del test "+cp);
		assertEquals(resultado2, "FLUJO OK", "Se verifica resultado 2 del test "+cp);
	}
	
	
	@AfterMethod
	public void FinEjecucion() {
		driver.close();
	}
	
}
