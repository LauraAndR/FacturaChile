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
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
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
	

public class Tests_NotaCreditoExportacion {

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
		String cp = "NCE_002";
		// Emisión DTE - Individual - Factura Exp - contado - factura de Servicios
		System.out.println(cp);
		String resultado1 = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_NELIDA, Configuration.PASS_NELIDA);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.menuEmisionIndividual(cp, "Emisión Individual");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Crédito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "CODELCO");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "100");
		
		pageEmisionDTE.AgregarReferenciaNCExp(cp, "3003648", "PRUEBA QA");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
		
		//Aduana
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "1 - A FIRME");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "1 - COSTO, SEGURO Y FLETE");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisDestino(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "10", "Marca QA");
		pageEmisionDTE.IngresoTotalBultosAduana(cp, "");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
				
		pageEmisionDTE.IngresoTipoMoneda(cp, "13 - DOLAR USA");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE CRÉDITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("ISALUD ISAPRE DE CODELCO LIMITADA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("ALEMANIA") &&
				
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("DOLAR USA")&&
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000")&&
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("100.000")&&
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("100.000")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("3003648")) {
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado1 = "FLUJO OK";
		}
		else {
			crearLogyDocumento.CasoNok(cp);
			System.out.println("FLUJO NOOK");
			resultado1 = "FLUJO NOOK";
		
		}
	
		assertEquals(resultado1, "FLUJO OK", "Se verifica resultado del test "+cp);
	}
	
	@Test
	public void Script_0003() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NCE_003";
		// Emisión DTE - Individual - Nota Credito Exp - contado - Servicios de hoteleria
		System.out.println(cp);
		String resultado1 = null;
				
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_NELIDA, Configuration.PASS_NELIDA);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.menuEmisionIndividual(cp, "Emisión Individual");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Crédito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "4 - Servicios de Hotelería");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "CODELCO");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "100");
		
		pageEmisionDTE.AgregarReferenciaNCExp(cp, "3003648", "PRUEBA QA");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
		
		//Aduana
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "1 - A FIRME");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "1 - COSTO, SEGURO Y FLETE");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisDestino(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "10", "Marca QA");
		pageEmisionDTE.IngresoTotalBultosAduana(cp, "");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
				
		pageEmisionDTE.IngresoTipoMoneda(cp, "13 - DOLAR USA");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE CRÉDITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("ISALUD ISAPRE DE CODELCO LIMITADA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Servicios de Hotelería") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("ALEMANIA")&&
				
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("DOLAR USA")&&
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000")&&
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("100.000")&&
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("100.000")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("3003648")) {
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado1 = "FLUJO OK";
		}
		else {
			crearLogyDocumento.CasoNok(cp);
			System.out.println("FLUJO NOOK");
			resultado1 = "FLUJO NOOK";
		
		}
				
	
		assertEquals(resultado1, "FLUJO OK", "Se verifica resultado del test "+cp);
		
	}
	
	@Test
	public void Script_0004() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NCE_004";
		// Emisión DTE - Individual - Nota Credito Exp - contado - Servicio de Transporte Terrestre Internacional
		System.out.println(cp);
		String resultado1 = null;
				
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_NELIDA, Configuration.PASS_NELIDA);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.menuEmisionIndividual(cp, "Emisión Individual");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Crédito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "5 - Servicio de Transporte Terrestre Internacional");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "CODELCO");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "100");
		
		pageEmisionDTE.AgregarReferenciaNCExp(cp, "3003648", "PRUEBA QA");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
	
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
		
		//Aduana
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "1 - A FIRME");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "1 - COSTO, SEGURO Y FLETE");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisDestino(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "10", "Marca QA");
		pageEmisionDTE.IngresoTotalBultosAduana(cp, "");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
				
		pageEmisionDTE.IngresoTipoMoneda(cp, "13 - DOLAR USA");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE CRÉDITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("ISALUD ISAPRE DE CODELCO LIMITADA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("ALEMANIA")&&
		
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("DOLAR USA")&&
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000")&&
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("100.000")&&
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("100.000")&&
		
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("3003648")) {
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado1 = "FLUJO OK";
		}
		else {
			crearLogyDocumento.CasoNok(cp);
			System.out.println("FLUJO NOOK");
			resultado1 = "FLUJO NOOK";
		
		}
				
	
		assertEquals(resultado1, "FLUJO OK", "Se verifica resultado del test "+cp);
		
	}
	
	@Test
	public void Script_0005() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NCE_005";
		// Emisión DTE - Individual - Nota Credito Exp - credito - factura de Servicios
		System.out.println(cp);
		String resultado1 = null;
				
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_NELIDA, Configuration.PASS_NELIDA);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.menuEmisionIndividual(cp, "Emisión Individual");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Crédito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Crédito");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "CODELCO");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "100");
		
		pageEmisionDTE.AgregarReferenciaNCExp(cp, "3003648", "PRUEBA QA");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
		
		//Aduana
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "1 - A FIRME");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "1 - COSTO, SEGURO Y FLETE");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisDestino(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "10", "Marca QA");
		pageEmisionDTE.IngresoTotalBultosAduana(cp, "");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
				
		pageEmisionDTE.IngresoTipoMoneda(cp, "13 - DOLAR USA");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE CRÉDITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("ISALUD ISAPRE DE CODELCO LIMITADA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Crédito") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("ALEMANIA")&&
				
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("DOLAR USA")&&
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000")&&
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("100.000")&&
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("100.000")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("3003648")) {
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado1 = "FLUJO OK";
		}
		else {
			crearLogyDocumento.CasoNok(cp);
			System.out.println("FLUJO NOOK");
			resultado1 = "FLUJO NOOK";
		
		}
				
	
		assertEquals(resultado1, "FLUJO OK", "Se verifica resultado del test "+cp);
		
	}
	
	@Test
		public void Script_0006() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
			String cp = "NCE_006";
			// Emisión DTE - Individual - Nota Credito Exp - credito - Servicios de hoteleria
			System.out.println(cp);
			String resultado1 = null;
					
			PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
			
			CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
			crearLogyDocumento.CrearEvidencias(cp);
			
			pageLoginAdm.ClickIngresarLogin(cp);
			pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_NELIDA, Configuration.PASS_NELIDA);
			
			PageEscritorio pageEscritorio = new PageEscritorio(driver);
			pageEscritorio.BarraMenu(cp, "Emisión DTE");
			pageEscritorio.menuEmisionIndividual(cp, "Emisión Individual");
			pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Crédito de Exportación Electrónica");
			
			PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
			pageEmisionDTE.FechaEmision(cp);
			pageEmisionDTE.SeleccionarFormaPago(cp, "Crédito");
			pageEmisionDTE.SeleccionarIndicadorServicio(cp, "4 - Servicios de Hotelería");
			
			pageEmisionDTE.IngresoRazonSocialCliente(cp, "CODELCO");
			pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
			
			Robot robot = new Robot();
			robot.setAutoDelay(7);
			robot.mouseWheel(7);
			
			pageEmisionDTE.AgregarProductoExportación(cp, "000001", "100");
			
			pageEmisionDTE.AgregarReferenciaNCExp(cp, "3003648", "PRUEBA QA");
			pageEmisionDTE.FechaReferencia(cp);
			pageEmisionDTE.BtnAgregarReferencia(cp);
			
			pageEmisionDTE.BtnAgregarTransporte(cp);
			pageEmisionDTE.BtnAgregarAduana(cp);
			
			//transporte
			robot.setAutoDelay(5);
			robot.mouseWheel(5);
			pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
			
			//Aduana
			robot.setAutoDelay(8);
			robot.mouseWheel(8);
			pageEmisionDTE.SeleccionarModalidadAduana(cp, "1 - A FIRME");
			pageEmisionDTE.SeleccionarClausulaAduana(cp, "1 - COSTO, SEGURO Y FLETE");
			pageEmisionDTE.IngresoTotalAduana(cp, "1000");
			pageEmisionDTE.IngresoPaisDestino(cp, "563 - ALEMANIA");
			pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
			pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
			pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
			pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
			
			pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "10", "Marca QA");
			pageEmisionDTE.IngresoTotalBultosAduana(cp, "");
			
			robot.setAutoDelay(8);
			robot.mouseWheel(8);
			
					
			pageEmisionDTE.IngresoTipoMoneda(cp, "13 - DOLAR USA");
			
			pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
			
			
			if(
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE CRÉDITO DE EXPORTACIÓN ELECTRÓNICA") &&
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("ISALUD ISAPRE DE CODELCO LIMITADA") &&
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Servicios de Hotelería") &&
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Crédito") &&
					
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("ALEMANIA")&&
					
					driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("DOLAR USA")&&
					driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000")&&
					driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("100.000")&&
					driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("100.000")&&
					
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica")&&
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("3003648")) {
				crearLogyDocumento.CasoOk(cp);
				System.out.println("FLUJO OK");
				resultado1 = "FLUJO OK";
			}
			else {
				crearLogyDocumento.CasoNok(cp);
				System.out.println("FLUJO NOOK");
				resultado1 = "FLUJO NOOK";
			
			}
					
		
			assertEquals(resultado1, "FLUJO OK", "Se verifica resultado del test "+cp);
			
		}

	@Test
	public void Script_0007() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NCE_007";
		// Emisión DTE - Individual - Nota Credito Exp - credito - Servicio de Transporte Terrestre Internacional
		System.out.println(cp);
		String resultado1 = null;
				
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_NELIDA, Configuration.PASS_NELIDA);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.menuEmisionIndividual(cp, "Emisión Individual");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Crédito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Crédito");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "5 - Servicio de Transporte Terrestre Internacional");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "CODELCO");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "100");
		
		pageEmisionDTE.AgregarReferenciaNCExp(cp, "3003648", "PRUEBA QA");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
		
		//Aduana
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "1 - A FIRME");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "1 - COSTO, SEGURO Y FLETE");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisDestino(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "10", "Marca QA");
		pageEmisionDTE.IngresoTotalBultosAduana(cp, "");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
				
		pageEmisionDTE.IngresoTipoMoneda(cp, "13 - DOLAR USA");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE CRÉDITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("ISALUD ISAPRE DE CODELCO LIMITADA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Crédito") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("ALEMANIA")&&
				
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("DOLAR USA")&&
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000")&&
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("100.000")&&
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("100.000")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("3003648")) {
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado1 = "FLUJO OK";
		}
		else {
			crearLogyDocumento.CasoNok(cp);
			System.out.println("FLUJO NOOK");
			resultado1 = "FLUJO NOOK";
		
		}
				
	
		assertEquals(resultado1, "FLUJO OK", "Se verifica resultado del test "+cp);
		
	}
	
	@Test
	public void Script_0008() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NCE_008";
		// Emisión DTE - Individual - Nota Credito Exp - sin costo - factura de Servicios
		System.out.println(cp);
		String resultado1 = null;
				
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_NELIDA, Configuration.PASS_NELIDA);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.menuEmisionIndividual(cp, "Emisión Individual");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Crédito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Sin Costo");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "CODELCO");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "100");
		
		pageEmisionDTE.AgregarReferenciaNCExp(cp, "3003648", "PRUEBA QA");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
		
		//Aduana
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "1 - A FIRME");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "1 - COSTO, SEGURO Y FLETE");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisDestino(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "10", "Marca QA");
		pageEmisionDTE.IngresoTotalBultosAduana(cp, "");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
				
		pageEmisionDTE.IngresoTipoMoneda(cp, "13 - DOLAR USA");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE CRÉDITO DE EXPORTACIÓN ELECTRÓNICA")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("ISALUD ISAPRE DE CODELCO LIMITADA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Sin costo(Entrega Gratuita)") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("ALEMANIA")&&
				
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("DOLAR USA")&&
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000")&&
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("100.000")&&
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("100.000")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("3003648")) {
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado1 = "FLUJO OK";
		}
		else {
			crearLogyDocumento.CasoNok(cp);
			System.out.println("FLUJO NOOK");
			resultado1 = "FLUJO NOOK";
		
		}
				
	
		assertEquals(resultado1, "FLUJO OK", "Se verifica resultado del test "+cp);
		
	}
	
	@Test
	public void Script_0009() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NCE_009";
		// Emisión DTE - Individual - Nota Credito Exp - sin costo - Servicios de hoteleria
		System.out.println(cp);
		String resultado1 = null;
				
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_NELIDA, Configuration.PASS_NELIDA);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.menuEmisionIndividual(cp, "Emisión Individual");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Crédito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Sin Costo");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "4 - Servicios de Hotelería");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "CODELCO");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "100");
		
		pageEmisionDTE.AgregarReferenciaNCExp(cp, "3003648", "PRUEBA QA");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
	
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
		
		//Aduana
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "1 - A FIRME");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "1 - COSTO, SEGURO Y FLETE");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisDestino(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "10", "Marca QA");
		pageEmisionDTE.IngresoTotalBultosAduana(cp, "");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
				
		pageEmisionDTE.IngresoTipoMoneda(cp, "13 - DOLAR USA");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE CRÉDITO DE EXPORTACIÓN ELECTRÓNICA")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("ISALUD ISAPRE DE CODELCO LIMITADA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Servicios de Hotelería") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Sin costo(Entrega Gratuita)") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("ALEMANIA")&&
				
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("DOLAR USA")&&
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000")&&
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("100.000")&&
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("100.000")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("3003648")) {
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado1 = "FLUJO OK";
		}
		else {
			crearLogyDocumento.CasoNok(cp);
			System.out.println("FLUJO NOOK");
			resultado1 = "FLUJO NOOK";
		
		}
				
	
		assertEquals(resultado1, "FLUJO OK", "Se verifica resultado del test "+cp);
		
	}
	
	@Test
	public void Script_0010() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NCE_0010";
		// Emisión DTE - Individual - Nota Credito Exp - sin costo - Servicio de Transporte Terrestre Internacional
		System.out.println(cp);
		String resultado1 = null;
				
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_NELIDA, Configuration.PASS_NELIDA);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.menuEmisionIndividual(cp, "Emisión Individual");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Crédito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Sin Costo");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "5 - Servicio de Transporte Terrestre Internacional");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "CODELCO");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "100");
		
		pageEmisionDTE.AgregarReferenciaNCExp(cp, "3003648", "PRUEBA QA");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
		
		//Aduana
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "1 - A FIRME");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "1 - COSTO, SEGURO Y FLETE");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisDestino(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "10", "Marca QA");
		pageEmisionDTE.IngresoTotalBultosAduana(cp, "");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
				
		pageEmisionDTE.IngresoTipoMoneda(cp, "13 - DOLAR USA");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE CRÉDITO DE EXPORTACIÓN ELECTRÓNICA")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("ISALUD ISAPRE DE CODELCO LIMITADA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Sin costo(Entrega Gratuita)") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("ALEMANIA")&&
				
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("DOLAR USA")&&
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000")&&
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("100.000")&&
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("100.000")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("3003648")) {
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado1 = "FLUJO OK";
		}
		else {
			crearLogyDocumento.CasoNok(cp);
			System.out.println("FLUJO NOOK");
			resultado1 = "FLUJO NOOK";
		
		}
				
	
		assertEquals(resultado1, "FLUJO OK", "Se verifica resultado del test "+cp);
		
	}
	
	@Test
	public void Script_0015() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NCE_0015";
		// Emisión DTE - Individual - Nota Credito Exp - producto con código ítem
		System.out.println(cp);
		String resultado1 = null;
				
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_NELIDA, Configuration.PASS_NELIDA);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.menuEmisionIndividual(cp, "Emisión Individual");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Crédito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Sin Costo");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "5 - Servicio de Transporte Terrestre Internacional");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "CODELCO");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoCodigoItem(cp, "000001", "1", "01", "500");
		
		pageEmisionDTE.AgregarReferenciaNCExp(cp, "3003648", "PRUEBA QA");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
	
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
		
		//Aduana
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "1 - A FIRME");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "1 - COSTO, SEGURO Y FLETE");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisDestino(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "10", "Marca QA");
		pageEmisionDTE.IngresoTotalBultosAduana(cp, "");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
				
		pageEmisionDTE.IngresoTipoMoneda(cp, "13 - DOLAR USA");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE CRÉDITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("ISALUD ISAPRE DE CODELCO LIMITADA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Sin costo(Entrega Gratuita)") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("ALEMANIA")&&
				
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("DOLAR USA")&&
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000")&&
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("100.000")&&
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("100.000")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("3003648")) {
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado1 = "FLUJO OK";
		}
		else {
			crearLogyDocumento.CasoNok(cp);
			System.out.println("FLUJO NOOK");
			resultado1 = "FLUJO NOOK";
		
		}
				
	
		assertEquals(resultado1, "FLUJO OK", "Se verifica resultado del test "+cp);
		
	}
	
	@Test
	public void Script_0016() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NCE_0016";
		// Emisión DTE - Individual - Nota Credito Exp - producto - limpiar
		System.out.println(cp);
		String resultado1 = null;
						
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_NELIDA, Configuration.PASS_NELIDA);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.menuEmisionIndividual(cp, "Emisión Individual");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Crédito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "CODELCO");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportaciónLimpiar(cp, "000001", "100");
		pageEmisionDTE.ClickLimpiarProducto(cp);
		
		pageEmisionDTE.AgregarReferenciaNCExp(cp, "3003648", "PRUEBA QA");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		
		if(driver.findElement(By.xpath("//*[@id=\"modal_form_incompleto\"]/div[2]/div/div[1]")).isDisplayed()) {
			crearLogyDocumento.CasoOk(cp);
			System.out.println("popUp Msj Error OK");
			resultado1 = "FLUJO OK";
		}
		else {
			crearLogyDocumento.CasoNok(cp);
			System.out.println("popUp Msj Error NOOK");
			resultado1 = "FLUJO NOOK";
		}
		
		System.out.println("FLUJO OK");
		assertEquals(resultado1, "FLUJO OK", "Se verifica resultado del test "+cp);
		
	}
	
	
	
	@Test
	public void Script_0019() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NCE_0019";
		// Emisión DTE - Individual - Nota Credito Exp - Referencias - Factura de Exportacion Electronica - Anula Documento
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_NELIDA, Configuration.PASS_NELIDA);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.menuEmisionIndividual(cp, "Emisión Individual");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Crédito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "CODELCO");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
		
		pageEmisionDTE.BtnEstablecerReferencias(cp);
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Referencias
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.AgregarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "3003648");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
		
				
		//Aduana
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "1 - A FIRME");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "1 - COSTO, SEGURO Y FLETE");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "10", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "13 - DOLAR USA");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE CRÉDITO DE EXPORTACIÓN ELECTRÓNICA")  &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("ISALUD ISAPRE DE CODELCO LIMITADA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				//driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("ALEMANIA")&& //Campo en blanco
				
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("DOLAR USA")&&
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000")&&
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000")&&
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("3003648")) {
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
		String cp = "NCE_0020";
		// Emisión DTE - Individual - Nota Credito Exp - Referencias - Nota de Debito de Exportacion Electronica - Anula Documento
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_NELIDA, Configuration.PASS_NELIDA);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.menuEmisionIndividual(cp, "Emisión Individual");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Crédito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "CODELCO");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
		
		pageEmisionDTE.BtnEstablecerReferencias(cp);
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Referencias
		pageEmisionDTE.SeleccionartipoDoc(cp, "111 - Nota de Débito de Exportación Electrónica");
		pageEmisionDTE.AgregarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "3003648");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
		
				
		//Aduana
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "1 - A FIRME");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "1 - COSTO, SEGURO Y FLETE");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "10", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "13 - DOLAR USA");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE CRÉDITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("ISALUD ISAPRE DE CODELCO LIMITADA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO")&&
				
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("DOLAR USA")&&
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000")&&
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000")&&
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Nota de Débito de Exportación Electrónica")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("3003648")) {
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
		String cp = "NCE_0038";
		// Emisión DTE - Individual - Nota Credito Exp - Referencias - Factura de Exportacion Electronica - Corrige Texto
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_NELIDA, Configuration.PASS_NELIDA);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.menuEmisionIndividual(cp, "Emisión Individual");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Crédito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "CODELCO");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
		
		pageEmisionDTE.BtnEstablecerReferencias(cp);
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Referencias
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.AgregarCodReferencia(cp, "2 - Corrige Texto");
		pageEmisionDTE.AgregarReferenciaNCExp(cp, "3003648", "PRUEBA QA");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
		
				
		//Aduana
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "1 - A FIRME");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "1 - COSTO, SEGURO Y FLETE");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "10", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "13 - DOLAR USA");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE CRÉDITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("ISALUD ISAPRE DE CODELCO LIMITADA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO")&&
				
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("DOLAR USA")&&
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000")&&
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000")&&
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("3003648")) {
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
		String cp = "NCE_0039";
		// Emisión DTE - Individual - Nota Credito Exp - Referencias - Nota de Debito de Exportacion Electronica - Corrige Texto
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_NELIDA, Configuration.PASS_NELIDA);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.menuEmisionIndividual(cp, "Emisión Individual");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Crédito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "CODELCO");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
		
		pageEmisionDTE.BtnEstablecerReferencias(cp);
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Referencias
		pageEmisionDTE.SeleccionartipoDoc(cp, "111 - Nota de Débito de Exportación Electrónica");
		pageEmisionDTE.AgregarCodReferencia(cp, "2 - Corrige Texto");
		pageEmisionDTE.AgregarReferenciaNCExp(cp, "3003648", "PRUEBA QA");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
		
				
		//Aduana
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "1 - A FIRME");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "1 - COSTO, SEGURO Y FLETE");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "10", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "13 - DOLAR USA");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE CRÉDITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("ISALUD ISAPRE DE CODELCO LIMITADA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO")&&
				
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("DOLAR USA")&&
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000")&&
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000")&&
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("3003648")) {
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
	public void Script_0057() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NCE_0057";
		// Emisión DTE - Individual - Nota Credito Exp - Referencias - Factura de Exportacion Electronica - Corrige Monto
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_NELIDA, Configuration.PASS_NELIDA);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.menuEmisionIndividual(cp, "Emisión Individual");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Crédito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "CODELCO");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
		
		pageEmisionDTE.BtnEstablecerReferencias(cp);
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Referencias
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.AgregarCodReferencia(cp, "3 - Corrige Monto");
		pageEmisionDTE.AgregarReferenciaNCExp(cp, "3003648", "2000");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
		
				
		//Aduana
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "1 - A FIRME");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "1 - COSTO, SEGURO Y FLETE");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "10", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "13 - DOLAR USA");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE CRÉDITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("ISALUD ISAPRE DE CODELCO LIMITADA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO")&&
				
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("DOLAR USA")&&
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000")&&
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000")&&
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("3003648")) {
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
	public void Script_0058() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NCE_0058";
		// Emisión DTE - Individual - Nota Credito Exp - Referencias - Nota de Debito de Exportacion Electronica - Corrige Monto
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_NELIDA, Configuration.PASS_NELIDA);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.menuEmisionIndividual(cp, "Emisión Individual");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Crédito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "CODELCO");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
		
		pageEmisionDTE.BtnEstablecerReferencias(cp);
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Referencias
		pageEmisionDTE.SeleccionartipoDoc(cp, "111 - Nota de Débito de Exportación Electrónica");
		pageEmisionDTE.AgregarCodReferencia(cp, "3 - Corrige Monto");
		pageEmisionDTE.AgregarReferenciaNCExp(cp, "3003648", "2000");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
		
				
		//Aduana
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "1 - A FIRME");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "1 - COSTO, SEGURO Y FLETE");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "10", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "13 - DOLAR USA");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE CRÉDITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("ISALUD ISAPRE DE CODELCO LIMITADA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO")&&
				
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("DOLAR USA")&&
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000")&&
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000")&&
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("3003648")) {
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
		String cp = "NCE_0077";
		// Emisión DTE - Individual - Nota Credito Exp - Agregar Transporte con Despacho por cuenta del receptor
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_NELIDA, Configuration.PASS_NELIDA);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.menuEmisionIndividual(cp, "Emisión Individual");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Crédito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "CODELCO");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Referencias
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.AgregarCodReferencia(cp, "2 - Corrige Texto");
		pageEmisionDTE.AgregarReferenciaNCExp(cp, "3003648", "PRUEBA QA");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
		
				
		//Aduana
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "1 - A FIRME");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "1 - COSTO, SEGURO Y FLETE");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "10", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "13 - DOLAR USA");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE CRÉDITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("ISALUD ISAPRE DE CODELCO LIMITADA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO")&&
				
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("DOLAR USA")&&
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000")&&
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000")&&
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("3003648")&&
				
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL")&&
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("0,0001")&&
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("0,0001")) {
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
	public void Script_0078() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NCE_0078";
		// Emisión DTE - Individual - Nota Credito Exp - Agregar Transporte con Despacho por cuenta del emisor
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_NELIDA, Configuration.PASS_NELIDA);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.menuEmisionIndividual(cp, "Emisión Individual");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Crédito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "CODELCO");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Referencias
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.AgregarCodReferencia(cp, "2 - Corrige Texto");
		pageEmisionDTE.AgregarReferenciaNCExp(cp, "3003648", "PRUEBA QA");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "2 - Despacho por Cuenta del Emisor");
		
				
		//Aduana
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "1 - A FIRME");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "1 - COSTO, SEGURO Y FLETE");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "10", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "13 - DOLAR USA");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE CRÉDITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("ISALUD ISAPRE DE CODELCO LIMITADA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO")&&
				
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("DOLAR USA")&&
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000")&&
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000")&&
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("3003648")&&
				
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL")&&
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("0,0001")&&
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("0,0001")) {
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
		String cp = "NCE_0079";
		// Emisión DTE - Individual - Nota Credito Exp - Agregar Transporte con Despacho por cuenta del emisor a otras instalaciones
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_NELIDA, Configuration.PASS_NELIDA);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.menuEmisionIndividual(cp, "Emisión Individual");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Crédito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "CODELCO");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Referencias
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.AgregarCodReferencia(cp, "2 - Corrige Texto");
		pageEmisionDTE.AgregarReferenciaNCExp(cp, "3003648", "PRUEBA QA");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "3 - Despacho por Cuenta del Emisor a Otras Instalaciones");
		
				
		//Aduana
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "1 - A FIRME");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "1 - COSTO, SEGURO Y FLETE");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "10", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "13 - DOLAR USA");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE CRÉDITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("ISALUD ISAPRE DE CODELCO LIMITADA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO")&&
				
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("DOLAR USA")&&
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000")&&
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000")&&
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("3003648")&&
				
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL")&&
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("0,0001")&&
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("0,0001")) {
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
	
//	@AfterMethod
//	public void FinEjecucion() {
//		driver.close();
//	}

}
