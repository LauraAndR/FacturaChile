package test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import common.Configuration;
import common.FechaActual;
import common.LeerExcel;
import evidence.CrearLogyDocumento;
import pages.PageDatosEmision;
import pages.PageDetalle;
import pages.PageEmisionDTE;
import pages.PageEscritorio;
import pages.PageFooter;
import pages.PageLogin;
import pages.PageLoginAdm;
import pages.PageReceptor;
import pages.PageSeleccionCpe;

public class Tests_FacturaExenta {
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
	
	
	// Miércoles 14/07/2021
	
	
	@Test
	public void Script_0001() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "FE_0001";
		// Emisión DTE - Individual - Factura Exenta - Contado - Compras del giro - Ventas del giro
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		String[] datos = leerExcel.ObtenerDatosCP(datapool,cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, datos[1], datos[2]);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura Exenta Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoCompra(cp, "1. Compras del Giro");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "1. Ventas del Giro");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProducto2(cp, "000001", "1");
		pageEmisionDTE.BtnEmitirFacturaExenta(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("FACTURA NO AFECTA O EXENTA ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("1.000")){
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
	public void Script_0002() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "FE_0002";
		// Emisión DTE - Individual - Factura Exenta - Contado - compras en supermercados o similares - Ventas del giro
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		String[] datos = leerExcel.ObtenerDatosCP(datapool,cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, datos[1], datos[2]);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura Exenta Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoCompra(cp, "2. Compras en Supermercados o similares");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "1. Ventas del Giro");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProducto2(cp, "000001", "1");
		pageEmisionDTE.BtnEmitirFacturaExenta(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("FACTURA NO AFECTA O EXENTA ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("1.000")){
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
		String cp = "FE_0003";
		// Emisión DTE - Individual - Factura Exenta - Contado - adquisición bien raíz - Ventas del giro
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		String[] datos = leerExcel.ObtenerDatosCP(datapool,cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, datos[1], datos[2]);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura Exenta Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoCompra(cp, "3. Adquisición Bien Raíz.");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "1. Ventas del Giro");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProducto2(cp, "000001", "1");
		pageEmisionDTE.BtnEmitirFacturaExenta(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("FACTURA NO AFECTA O EXENTA ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("1.000")){
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
		String cp = "FE_0004";
		// Emisión DTE - Individual - Factura Exenta - Contado - compra de activo fijo - Ventas del giro
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		String[] datos = leerExcel.ObtenerDatosCP(datapool,cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, datos[1], datos[2]);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura Exenta Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoCompra(cp, "4. Compra Activo Fijo");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "1. Ventas del Giro");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProducto2(cp, "000001", "1");
		pageEmisionDTE.BtnEmitirFacturaExenta(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("FACTURA NO AFECTA O EXENTA ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("1.000")){
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
		String cp = "FE_0005";
		// Emisión DTE - Individual - Factura Exenta - Contado - compra con IVA uso común - Ventas del giro
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		String[] datos = leerExcel.ObtenerDatosCP(datapool,cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, datos[1], datos[2]);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura Exenta Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoCompra(cp, "5. Compra con IVA Uso Común");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "1. Ventas del Giro");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProducto2(cp, "000001", "1");
		pageEmisionDTE.BtnEmitirFacturaExenta(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("FACTURA NO AFECTA O EXENTA ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("1.000")){
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
		String cp = "FE_0006";
		// Emisión DTE - Individual - Factura Exenta - Contado - compra sin derecho a crédito - Ventas del giro
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		String[] datos = leerExcel.ObtenerDatosCP(datapool,cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, datos[1], datos[2]);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura Exenta Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoCompra(cp, "6. Compra sin derecho a Crédito");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "1. Ventas del Giro");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProducto2(cp, "000001", "1");
		pageEmisionDTE.BtnEmitirFacturaExenta(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("FACTURA NO AFECTA O EXENTA ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("1.000")){
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
		String cp = "FE_0007";
		// Emisión DTE - Individual - Factura Exenta - Contado - compra que no corresponde incluir - Ventas del giro
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		String[] datos = leerExcel.ObtenerDatosCP(datapool,cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, datos[1], datos[2]);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura Exenta Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoCompra(cp, "7. Compra que no corresponde incluir");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "1. Ventas del Giro");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProducto2(cp, "000001", "1");
		pageEmisionDTE.BtnEmitirFacturaExenta(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("FACTURA NO AFECTA O EXENTA ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("1.000")){
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
		String cp = "FE_0008";
		// Emisión DTE - Individual - Factura Exenta - Contado - Compras del giro - Ventas de activo fijo
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		String[] datos = leerExcel.ObtenerDatosCP(datapool,cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, datos[1], datos[2]);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura Exenta Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoCompra(cp, "1. Compras del Giro");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "2. Venta Activo Fijo");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProducto2(cp, "000001", "1");
		pageEmisionDTE.BtnEmitirFacturaExenta(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("FACTURA NO AFECTA O EXENTA ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("1.000")){
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
		String cp = "FE_0009";
		// Emisión DTE - Individual - Factura Exenta - Contado - compras en supermercados o similares - Ventas de activo fijo
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		String[] datos = leerExcel.ObtenerDatosCP(datapool,cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, datos[1], datos[2]);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura Exenta Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoCompra(cp, "2. Compras en Supermercados o similares");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "2. Venta Activo Fijo");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProducto2(cp, "000001", "1");
		pageEmisionDTE.BtnEmitirFacturaExenta(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("FACTURA NO AFECTA O EXENTA ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("1.000")){
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
		String cp = "FE_0010";
		// Emisión DTE - Individual - Factura Exenta - Contado - adquisición bien raíz - Ventas de activo fijo
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		String[] datos = leerExcel.ObtenerDatosCP(datapool,cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, datos[1], datos[2]);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura Exenta Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoCompra(cp, "3. Adquisición Bien Raíz.");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "2. Venta Activo Fijo");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProducto2(cp, "000001", "1");
		pageEmisionDTE.BtnEmitirFacturaExenta(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("FACTURA NO AFECTA O EXENTA ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("1.000")){
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
		String cp = "FE_0011";
		// Emisión DTE - Individual - Factura Exenta - Contado - compra de activo fijo - Ventas de activo fijo
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		String[] datos = leerExcel.ObtenerDatosCP(datapool,cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, datos[1], datos[2]);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura Exenta Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoCompra(cp, "4. Compra Activo Fijo");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "2. Venta Activo Fijo");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProducto2(cp, "000001", "1");
		pageEmisionDTE.BtnEmitirFacturaExenta(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("FACTURA NO AFECTA O EXENTA ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("1.000")){
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
		String cp = "FE_0012";
		// Emisión DTE - Individual - Factura Exenta - Contado - compra con IVA uso común - Ventas de activo fijo
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		String[] datos = leerExcel.ObtenerDatosCP(datapool,cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, datos[1], datos[2]);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura Exenta Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoCompra(cp, "5. Compra con IVA Uso Común");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "2. Venta Activo Fijo");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProducto2(cp, "000001", "1");
		pageEmisionDTE.BtnEmitirFacturaExenta(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("FACTURA NO AFECTA O EXENTA ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("1.000")){
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
		String cp = "FE_0013";
		// Emisión DTE - Individual - Factura Exenta - Contado - compra sin derecho a crédito - Ventas de activo fijo
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		String[] datos = leerExcel.ObtenerDatosCP(datapool,cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, datos[1], datos[2]);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura Exenta Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoCompra(cp, "6. Compra sin derecho a Crédito");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "2. Venta Activo Fijo");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProducto2(cp, "000001", "1");
		pageEmisionDTE.BtnEmitirFacturaExenta(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("FACTURA NO AFECTA O EXENTA ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("1.000")){
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
		String cp = "FE_0014";
		// Emisión DTE - Individual - Factura Exenta - Contado - compra que no corresponde incluir - Ventas de activo fijo
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		String[] datos = leerExcel.ObtenerDatosCP(datapool,cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, datos[1], datos[2]);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura Exenta Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoCompra(cp, "7. Compra que no corresponde incluir");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "2. Venta Activo Fijo");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProducto2(cp, "000001", "1");
		pageEmisionDTE.BtnEmitirFacturaExenta(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("FACTURA NO AFECTA O EXENTA ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("1.000")){
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
		String cp = "FE_0015";
		// Emisión DTE - Individual - Factura Exenta - Contado - Compras del giro - venta de bien raíz
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		String[] datos = leerExcel.ObtenerDatosCP(datapool,cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, datos[1], datos[2]);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura Exenta Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoCompra(cp, "1. Compras del Giro");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "3. Venta Bien Raíz");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProducto2(cp, "000001", "1");
		pageEmisionDTE.BtnEmitirFacturaExenta(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("FACTURA NO AFECTA O EXENTA ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("1.000")){
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
	
	
	// Jueves 15/07/2021
	
	@Test
	public void Script_0016() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "FE_0016";
		// Emisión DTE - Individual - Factura Exenta - Contado - compras en supermercados o similares - venta de bien raíz
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		String[] datos = leerExcel.ObtenerDatosCP(datapool,cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, datos[1], datos[2]);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura Exenta Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoCompra(cp, "2. Compras en Supermercados o similares");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "3. Venta Bien Raíz");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProducto2(cp, "000001", "1");
		pageEmisionDTE.BtnEmitirFacturaExenta(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("FACTURA NO AFECTA O EXENTA ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("1.000")){
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
		String cp = "FE_0017";
		// Emisión DTE - Individual - Factura Exenta - Contado - adquisición bien raíz - venta de bien raíz
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		String[] datos = leerExcel.ObtenerDatosCP(datapool,cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, datos[1], datos[2]);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura Exenta Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoCompra(cp, "3. Adquisición Bien Raíz.");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "3. Venta Bien Raíz");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProducto2(cp, "000001", "1");
		pageEmisionDTE.BtnEmitirFacturaExenta(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("FACTURA NO AFECTA O EXENTA ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("1.000")){
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
		String cp = "FE_0018";
		// Emisión DTE - Individual - Factura Exenta - Contado - compra de activo fijo - venta de bien raíz
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		String[] datos = leerExcel.ObtenerDatosCP(datapool,cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, datos[1], datos[2]);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura Exenta Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoCompra(cp, "4. Compra Activo Fijo");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "3. Venta Bien Raíz");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProducto2(cp, "000001", "1");
		pageEmisionDTE.BtnEmitirFacturaExenta(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("FACTURA NO AFECTA O EXENTA ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("1.000")){
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
		String cp = "FE_0019";
		// Emisión DTE - Individual - Factura Exenta - Contado - compra con IVA uso común - venta de bien raíz
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		String[] datos = leerExcel.ObtenerDatosCP(datapool,cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, datos[1], datos[2]);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura Exenta Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoCompra(cp, "5. Compra con IVA Uso Común");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "3. Venta Bien Raíz");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProducto2(cp, "000001", "1");
		pageEmisionDTE.BtnEmitirFacturaExenta(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("FACTURA NO AFECTA O EXENTA ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("1.000")){
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
		String cp = "FE_0020";
		// Emisión DTE - Individual - Factura Exenta - Contado - compra sin derecho a crédito - venta de bien raíz
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		String[] datos = leerExcel.ObtenerDatosCP(datapool,cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, datos[1], datos[2]);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura Exenta Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoCompra(cp, "6. Compra sin derecho a Crédito");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "3. Venta Bien Raíz");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProducto2(cp, "000001", "1");
		pageEmisionDTE.BtnEmitirFacturaExenta(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("FACTURA NO AFECTA O EXENTA ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("1.000")){
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
		String cp = "FE_0021";
		// Emisión DTE - Individual - Factura Exenta - Contado - compra que no corresponde incluir - venta de bien raíz
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		String[] datos = leerExcel.ObtenerDatosCP(datapool,cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, datos[1], datos[2]);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura Exenta Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarTipoCompra(cp, "7. Compra que no corresponde incluir");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "3. Venta Bien Raíz");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProducto2(cp, "000001", "1");
		pageEmisionDTE.BtnEmitirFacturaExenta(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("FACTURA NO AFECTA O EXENTA ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("1.000")){
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
		String cp = "FE_0022";
		// Emisión DTE - Individual - Factura Exenta - Crédito - Compras del giro - Ventas del giro
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		String[] datos = leerExcel.ObtenerDatosCP(datapool,cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, datos[1], datos[2]);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura Exenta Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Crédito");
		pageEmisionDTE.SeleccionarTipoCompra(cp, "1. Compras del Giro");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "1. Ventas del Giro");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProducto2(cp, "000001", "1");
		pageEmisionDTE.BtnEmitirFacturaExenta(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("FACTURA NO AFECTA O EXENTA ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Credito") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("1.000")){
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
		String cp = "FE_0023";
		// Emisión DTE - Individual - Factura Exenta - Crédito - compras en supermercados o similares - Ventas del giro
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		String[] datos = leerExcel.ObtenerDatosCP(datapool,cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, datos[1], datos[2]);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura Exenta Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Crédito");
		pageEmisionDTE.SeleccionarTipoCompra(cp, "2. Compras en Supermercados o similares");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "1. Ventas del Giro");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProducto2(cp, "000001", "1");
		pageEmisionDTE.BtnEmitirFacturaExenta(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("FACTURA NO AFECTA O EXENTA ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Credito") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("1.000")){
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
		String cp = "FE_0024";
		// Emisión DTE - Individual - Factura Exenta - Crédito - adquisición bien raíz - Ventas del giro
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		String[] datos = leerExcel.ObtenerDatosCP(datapool,cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, datos[1], datos[2]);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura Exenta Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Crédito");
		pageEmisionDTE.SeleccionarTipoCompra(cp, "3. Adquisición Bien Raíz.");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "1. Ventas del Giro");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProducto2(cp, "000001", "1");
		pageEmisionDTE.BtnEmitirFacturaExenta(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("FACTURA NO AFECTA O EXENTA ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Credito") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("1.000")){
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
		String cp = "FE_0025";
		// Emisión DTE - Individual - Factura Exenta - Crédito - compra de activo fijo - Ventas del giro
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		String[] datos = leerExcel.ObtenerDatosCP(datapool,cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, datos[1], datos[2]);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura Exenta Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Crédito");
		pageEmisionDTE.SeleccionarTipoCompra(cp, "4. Compra Activo Fijo");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "1. Ventas del Giro");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProducto2(cp, "000001", "1");
		pageEmisionDTE.BtnEmitirFacturaExenta(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("FACTURA NO AFECTA O EXENTA ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Credito") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("1.000")){
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
		String cp = "FE_0026";
		// Emisión DTE - Individual - Factura Exenta - Crédito - compra con IVA uso común - Ventas del giro
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		String[] datos = leerExcel.ObtenerDatosCP(datapool,cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, datos[1], datos[2]);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura Exenta Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Crédito");
		pageEmisionDTE.SeleccionarTipoCompra(cp, "5. Compra con IVA Uso Común");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "1. Ventas del Giro");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProducto2(cp, "000001", "1");
		pageEmisionDTE.BtnEmitirFacturaExenta(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("FACTURA NO AFECTA O EXENTA ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Credito") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("1.000")){
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
		String cp = "FE_0027";
		// Emisión DTE - Individual - Factura Exenta - Crédito - compra sin derecho a crédito - Ventas del giro
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		String[] datos = leerExcel.ObtenerDatosCP(datapool,cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, datos[1], datos[2]);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura Exenta Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Crédito");
		pageEmisionDTE.SeleccionarTipoCompra(cp, "6. Compra sin derecho a Crédito");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "1. Ventas del Giro");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProducto2(cp, "000001", "1");
		pageEmisionDTE.BtnEmitirFacturaExenta(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("FACTURA NO AFECTA O EXENTA ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Credito") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("1.000")){
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
		String cp = "FE_0028";
		// Emisión DTE - Individual - Factura Exenta - Crédito - compra que no corresponde incluir - Ventas del giro
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		String[] datos = leerExcel.ObtenerDatosCP(datapool,cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, datos[1], datos[2]);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura Exenta Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Crédito");
		pageEmisionDTE.SeleccionarTipoCompra(cp, "7. Compra que no corresponde incluir");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "1. Ventas del Giro");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProducto2(cp, "000001", "1");
		pageEmisionDTE.BtnEmitirFacturaExenta(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("FACTURA NO AFECTA O EXENTA ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Credito") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("1.000")){
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
		String cp = "FE_0029";
		// Emisión DTE - Individual - Factura Exenta - Crédito - Compras del giro - Ventas de activo fijo
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		String[] datos = leerExcel.ObtenerDatosCP(datapool,cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, datos[1], datos[2]);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura Exenta Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Crédito");
		pageEmisionDTE.SeleccionarTipoCompra(cp, "1. Compras del Giro");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "2. Venta Activo Fijo");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProducto2(cp, "000001", "1");
		pageEmisionDTE.BtnEmitirFacturaExenta(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("FACTURA NO AFECTA O EXENTA ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Credito") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("1.000")){
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
		String cp = "FE_0030";
		// Emisión DTE - Individual - Factura Exenta - Crédito - compras en supermercados o similares - Ventas de activo fijo
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		String[] datos = leerExcel.ObtenerDatosCP(datapool,cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, datos[1], datos[2]);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura Exenta Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Crédito");
		pageEmisionDTE.SeleccionarTipoCompra(cp, "2. Compras en Supermercados o similares");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "2. Venta Activo Fijo");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProducto2(cp, "000001", "1");
		pageEmisionDTE.BtnEmitirFacturaExenta(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("FACTURA NO AFECTA O EXENTA ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Credito") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("1.000")){
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
		String cp = "FE_0031";
		// Emisión DTE - Individual - Factura Exenta - Crédito - adquisición bien raíz - Ventas de activo fijo
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		String[] datos = leerExcel.ObtenerDatosCP(datapool,cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, datos[1], datos[2]);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura Exenta Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Crédito");
		pageEmisionDTE.SeleccionarTipoCompra(cp, "3. Adquisición Bien Raíz.");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "2. Venta Activo Fijo");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProducto2(cp, "000001", "1");
		pageEmisionDTE.BtnEmitirFacturaExenta(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("FACTURA NO AFECTA O EXENTA ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Credito") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("1.000")){
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
		String cp = "FE_0032";
		// Emisión DTE - Individual - Factura Exenta - Crédito - compra de activo fijo - Ventas de activo fijo
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		String[] datos = leerExcel.ObtenerDatosCP(datapool,cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, datos[1], datos[2]);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura Exenta Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Crédito");
		pageEmisionDTE.SeleccionarTipoCompra(cp, "4. Compra Activo Fijo");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "2. Venta Activo Fijo");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProducto2(cp, "000001", "1");
		pageEmisionDTE.BtnEmitirFacturaExenta(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("FACTURA NO AFECTA O EXENTA ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Credito") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("1.000")){
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
		String cp = "FE_0033";
		// Emisión DTE - Individual - Factura Exenta - Crédito - compra con IVA uso común - Ventas de activo fijo
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		String[] datos = leerExcel.ObtenerDatosCP(datapool,cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, datos[1], datos[2]);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura Exenta Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Crédito");
		pageEmisionDTE.SeleccionarTipoCompra(cp, "5. Compra con IVA Uso Común");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "2. Venta Activo Fijo");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProducto2(cp, "000001", "1");
		pageEmisionDTE.BtnEmitirFacturaExenta(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("FACTURA NO AFECTA O EXENTA ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Credito") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("1.000")){
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
		String cp = "FE_0034";
		// Emisión DTE - Individual - Factura Exenta - Crédito - compra sin derecho a crédito - Ventas de activo fijo
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		String[] datos = leerExcel.ObtenerDatosCP(datapool,cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, datos[1], datos[2]);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura Exenta Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Crédito");
		pageEmisionDTE.SeleccionarTipoCompra(cp, "6. Compra sin derecho a Crédito");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "2. Venta Activo Fijo");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProducto2(cp, "000001", "1");
		pageEmisionDTE.BtnEmitirFacturaExenta(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("FACTURA NO AFECTA O EXENTA ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Credito") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("1.000")){
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
		String cp = "FE_0035";
		// Emisión DTE - Individual - Factura Exenta - Crédito - compra que no corresponde incluir - Ventas de activo fijo
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		String[] datos = leerExcel.ObtenerDatosCP(datapool,cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, datos[1], datos[2]);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura Exenta Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Crédito");
		pageEmisionDTE.SeleccionarTipoCompra(cp, "7. Compra que no corresponde incluir");
		pageEmisionDTE.SeleccionarTipoVenta(cp, "2. Venta Activo Fijo");
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.AgregarProducto2(cp, "000001", "1");
		pageEmisionDTE.BtnEmitirFacturaExenta(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("FACTURA NO AFECTA O EXENTA ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Credito") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("1.000")){
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
