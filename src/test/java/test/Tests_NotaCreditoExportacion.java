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
	
	
	
	/*
	 * RICARDO
	 */
	
	@Test
	public void Script_0100() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NCE_0100";
		// Emisión DTE - Individual - Nota Credito Exp - Agregar Aduana - modalidad: bajo condición - Clausula: otros
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
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
		
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "2 - BAJO CONDICION");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "8 - OTROS");
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("CODELCO TEC SPA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("BAJO CONDICION") &&
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("0,0001")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("0,0001")) {
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
	public void Script_0101() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NCE_0101";
		// Emisión DTE - Individual - Nota Credito Exp - Agregar Aduana - modalidad: bajo condición - Clausula: entregadas derechos pagados
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
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
		
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "2 - BAJO CONDICION");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "9 - ENTREGADAS DERECHOS PAGADOS");
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("CODELCO TEC SPA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("BAJO CONDICION") &&
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("0,0001")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("0,0001")) {
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
	public void Script_0102() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NCE_0102";
		// Emisión DTE - Individual - Nota Credito Exp - Agregar Aduana - modalidad: bajo condición - Clausula:  FRANCO TRANSPORTISTA
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
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
		
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "2 - BAJO CONDICION");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "10 - FRANCO TRANSPORTISTA");
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("CODELCO TEC SPA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("BAJO CONDICION") &&
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("0,0001")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("0,0001")) {
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
	public void Script_0103() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NCE_0103";
		// Emisión DTE - Individual - Nota Credito Exp - Agregar Aduana - modalidad: bajo condición - Clausula:  TRANSPORTE PAGADO HASTA
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
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
		
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "2 - BAJO CONDICION");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "11 - TRANSPORTE PAGADO HASTA");
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("CODELCO TEC SPA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("BAJO CONDICION") &&
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("0,0001")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("0,0001")) {
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
	public void Script_0104() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NCE_0104";
		// Emisión DTE - Individual - Nota Credito Exp - Agregar Aduana - modalidad: bajo condición - Clausula:  TRANSPORTE Y SEGURO PAGADO HASTA
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
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
		
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "2 - BAJO CONDICION");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "12 - TRANSPORTE Y SEGURO PAGADO HASTA");
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("CODELCO TEC SPA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("BAJO CONDICION") &&
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("0,0001")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("0,0001")) {
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
	public void Script_0105() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NCE_0105";
		// Emisión DTE - Individual - Nota Credito Exp - Agregar Aduana - modalidad: bajo condición - Clausula: ENTREGADAS EN PUERTO DESTINO
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
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
		
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "2 - BAJO CONDICION");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "17 - ENTREGADAS EN PUERTO DESTINO");
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("CODELCO TEC SPA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("BAJO CONDICION") &&
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("0,0001")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("0,0001")) {
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
	public void Script_0106() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NCE_0106";
		// Emisión DTE - Individual - Nota Credito Exp - Agregar Aduana - modalidad: bajo condición - Clausula: ENTREGADAS EN LUGAR ACORDADO
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
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
		
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "2 - BAJO CONDICION");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "9 - ENTREGADAS DERECHOS PAGADOS");
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("CODELCO TEC SPA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("BAJO CONDICION") &&
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("0,0001")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("0,0001")) {
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
	public void Script_0107() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NCE_0107";
		// Emisión DTE - Individual - Nota Credito Exp - Agregar Aduana -  modalidad: en consignación libre - Clausula: costo, seguro y flete
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
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
		
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "3 - EN CONSIGNACION LIBRE");
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("CODELCO TEC SPA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("EN CONSIGNACION LIBRE") &&
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("0,0001")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("0,0001")) {
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
	public void Script_0108() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NCE_0108";
		// Emisión DTE - Individual - Nota Credito Exp - Agregar Aduana -  modalidad: en consignación libre - Clausula: COSTO Y FLETE
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
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
		
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "3 - EN CONSIGNACION LIBRE");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "2 - COSTO Y FLETE");
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("CODELCO TEC SPA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("EN CONSIGNACION LIBRE") &&
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("0,0001")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("0,0001")) {
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
	public void Script_0109() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NCE_0109";
		// Emisión DTE - Individual - Nota Credito Exp - Agregar Aduana -  modalidad: en consignación libre - Clausula:  EN FÁBRICA
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
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
		
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "3 - EN CONSIGNACION LIBRE");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "3 - EN FÁBRICA");
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("CODELCO TEC SPA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("EN CONSIGNACION LIBRE") &&
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("0,0001")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("0,0001")) {
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
	public void Script_0110() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NCE_0110";
		// Emisión DTE - Individual - Nota Credito Exp - Agregar Aduana -  modalidad: en consignación libre - Clausula: FRANCO AL COSTADO DEL BUQUE
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
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
		
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "3 - EN CONSIGNACION LIBRE");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "4 - FRANCO AL COSTADO DEL BUQUE");
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("CODELCO TEC SPA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("EN CONSIGNACION LIBRE") &&
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("0,0001")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("0,0001")) {
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
	public void Script_0111() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NCE_0111";
		// Emisión DTE - Individual - Nota Credito Exp - Agregar Aduana -  modalidad: en consignación libre - Clausula: FRANCO A BORDO
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
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
		
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "3 - EN CONSIGNACION LIBRE");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "5 - FRANCO A BORDO");
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("CODELCO TEC SPA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("EN CONSIGNACION LIBRE") &&
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("0,0001")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("0,0001")) {
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
	public void Script_0112() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NCE_0112";
		// Emisión DTE - Individual - Nota Credito Exp - Agregar Aduana -  modalidad: en consignación libre - Clausula:  SIN CLAUSULA DE COMPRAVENTA
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
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
		
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "3 - EN CONSIGNACION LIBRE");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "6 - SIN CLAUSULA DE COMPRAVENTA");
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("CODELCO TEC SPA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("EN CONSIGNACION LIBRE") &&
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("0,0001")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("0,0001")) {
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
	public void Script_0113() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NCE_0113";
		// Emisión DTE - Individual - Nota Credito Exp - Agregar Aduana -  modalidad: en consignación libre - Clausula: OTROS
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
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
		
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "3 - EN CONSIGNACION LIBRE");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "8 - OTROS");
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("CODELCO TEC SPA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("EN CONSIGNACION LIBRE") &&
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("0,0001")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("0,0001")) {
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
	public void Script_0114() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NCE_0114";
		// Emisión DTE - Individual - Nota Credito Exp - Agregar Aduana -  modalidad: en consignación libre - Clausula:  ENTREGADAS DERECHOS PAGADOS
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
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
		
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "3 - EN CONSIGNACION LIBRE");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "9 - ENTREGADAS DERECHOS PAGADOS");
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("CODELCO TEC SPA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("EN CONSIGNACION LIBRE") &&
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("0,0001")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("0,0001")) {
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
	public void Script_0115() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NCE_0115";
		// Emisión DTE - Individual - Nota Credito Exp - Agregar Aduana -  modalidad: en consignación libre - Clausula: FRANCO TRANSPORTISTA
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
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
		
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "3 - EN CONSIGNACION LIBRE");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "10 - FRANCO TRANSPORTISTA");
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("CODELCO TEC SPA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("EN CONSIGNACION LIBRE") &&
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("0,0001")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("0,0001")) {
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
	public void Script_0116() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NCE_0116";
		// Emisión DTE - Individual - Nota Credito Exp - Agregar Aduana -  modalidad: en consignación libre - Clausula: TRANSPORTE PAGADO HASTA
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
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
		
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "3 - EN CONSIGNACION LIBRE");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "11 - TRANSPORTE PAGADO HASTA");
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("CODELCO TEC SPA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("EN CONSIGNACION LIBRE") &&
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("0,0001")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("0,0001")) {
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
	public void Script_0117() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NCE_0117";
		// Emisión DTE - Individual - Nota Credito Exp - Agregar Aduana -  modalidad: en consignación libre - Clausula: TRANSPORTE Y SEGURO PAGADO HASTA
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
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
		
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "3 - EN CONSIGNACION LIBRE");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "12 - TRANSPORTE Y SEGURO PAGADO HASTA");
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("CODELCO TEC SPA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("EN CONSIGNACION LIBRE") &&
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("0,0001")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("0,0001")) {
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
	public void Script_0118() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NCE_0118";
		// Emisión DTE - Individual - Nota Credito Exp - Agregar Aduana -  modalidad: en consignación libre - Clausula: ENTREGADAS EN PUERTO DESTINO
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
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
		
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "3 - EN CONSIGNACION LIBRE");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "17 - ENTREGADAS EN PUERTO DESTINO");
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("CODELCO TEC SPA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("EN CONSIGNACION LIBRE") &&
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("0,0001")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("0,0001")) {
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
	public void Script_0119() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NCE_0119";
		// Emisión DTE - Individual - Nota Credito Exp - Agregar Aduana -  modalidad: en consignación libre - Clausula: ENTREGADAS EN LUGAR ACORDADO
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
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
		
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "3 - EN CONSIGNACION LIBRE");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "18 - ENTREGADAS EN LUGAR ACORDADO");
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("CODELCO TEC SPA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("EN CONSIGNACION LIBRE") &&
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("0,0001")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("0,0001")) {
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
	public void Script_0120() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NCE_0120";
		// Emisión DTE - Individual - Nota Credito Exp - Agregar Aduana -  modalidad: EN CONSIGNACION CON UN MINIMO A FIRME - Clausula: COSTO, SEGURO Y FLETE
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
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
		
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "4 - EN CONSIGNACION CON UN MINIMO A FIRME");
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("CODELCO TEC SPA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("MINIMO A FIRME") &&
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("0,0001")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("0,0001")) {
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
	public void Script_0121() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NCE_0121";
		// Emisión DTE - Individual - Nota Credito Exp - Agregar Aduana -  modalidad: EN CONSIGNACION CON UN MINIMO A FIRME - Clausula:  COSTO Y FLETE
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
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
		
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "4 - EN CONSIGNACION CON UN MINIMO A FIRME");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "2 - COSTO Y FLETE");
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("CODELCO TEC SPA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("MINIMO A FIRME") &&
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("0,0001")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("0,0001")) {
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
	public void Script_0122() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NCE_0122";
		// Emisión DTE - Individual - Nota Credito Exp - Agregar Aduana -  modalidad: EN CONSIGNACION CON UN MINIMO A FIRME - Clausula:  EN FÁBRICA
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
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
		
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "4 - EN CONSIGNACION CON UN MINIMO A FIRME");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "3 - EN FÁBRICA");
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("CODELCO TEC SPA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("MINIMO A FIRME") &&
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("0,0001")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("0,0001")) {
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
	public void Script_0123() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NCE_0123";
		// Emisión DTE - Individual - Nota Credito Exp - Agregar Aduana -  modalidad: EN CONSIGNACION CON UN MINIMO A FIRME - Clausula: FRANCO AL COSTADO DEL BUQUE
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
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
		
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "4 - EN CONSIGNACION CON UN MINIMO A FIRME");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "4 - FRANCO AL COSTADO DEL BUQUE");
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("CODELCO TEC SPA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("MINIMO A FIRME") &&
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("0,0001")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("0,0001")) {
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
	public void Script_0124() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NCE_0124";
		// Emisión DTE - Individual - Nota Credito Exp - Agregar Aduana -  modalidad: EN CONSIGNACION CON UN MINIMO A FIRME - Clausula: FRANCO A BORDO
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
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
		
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "4 - EN CONSIGNACION CON UN MINIMO A FIRME");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "5 - FRANCO A BORDO");
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("CODELCO TEC SPA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("MINIMO A FIRME") &&
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("0,0001")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("0,0001")) {
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
	public void Script_0125() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NCE_0125";
		// Emisión DTE - Individual - Nota Credito Exp - Agregar Aduana -  modalidad: EN CONSIGNACION CON UN MINIMO A FIRME - Clausula: SIN CLAUSULA DE COMPRAVENTA
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
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
		
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "4 - EN CONSIGNACION CON UN MINIMO A FIRME");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "6 - SIN CLAUSULA DE COMPRAVENTA");
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("CODELCO TEC SPA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("MINIMO A FIRME") &&
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("0,0001")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("0,0001")) {
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
	public void Script_0126() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NCE_0126";
		// Emisión DTE - Individual - Nota Credito Exp - Agregar Aduana -  modalidad: EN CONSIGNACION CON UN MINIMO A FIRME - Clausula: OTROS
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
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
		
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "4 - EN CONSIGNACION CON UN MINIMO A FIRME");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "8 - OTROS");
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("CODELCO TEC SPA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("MINIMO A FIRME") &&
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("0,0001")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("0,0001")) {
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
	public void Script_0127() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NCE_0127";
		// Emisión DTE - Individual - Nota Credito Exp - Agregar Aduana -  modalidad: EN CONSIGNACION CON UN MINIMO A FIRME - Clausula: ENTREGADAS DERECHOS PAGADOS
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
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
		
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "4 - EN CONSIGNACION CON UN MINIMO A FIRME");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "9 - ENTREGADAS DERECHOS PAGADOS");
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("CODELCO TEC SPA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("MINIMO A FIRME") &&
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("0,0001")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("0,0001")) {
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
	public void Script_0128() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NCE_0128";
		// Emisión DTE - Individual - Nota Credito Exp - Agregar Aduana -  modalidad: EN CONSIGNACION CON UN MINIMO A FIRME - Clausula: FRANCO TRANSPORTISTA
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
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
		
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "4 - EN CONSIGNACION CON UN MINIMO A FIRME");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "10 - FRANCO TRANSPORTISTA");
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("CODELCO TEC SPA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("MINIMO A FIRME") &&
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("0,0001")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("0,0001")) {
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
	public void Script_0129() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NCE_0129";
		// Emisión DTE - Individual - Nota Credito Exp - Agregar Aduana -  modalidad: EN CONSIGNACION CON UN MINIMO A FIRME - Clausula:  TRANSPORTE PAGADO HASTA
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
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
		
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "4 - EN CONSIGNACION CON UN MINIMO A FIRME");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "11 - TRANSPORTE PAGADO HASTA");
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("CODELCO TEC SPA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("MINIMO A FIRME") &&
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("0,0001")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("0,0001")) {
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
	public void Script_0130() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NCE_0130";
		// Emisión DTE - Individual - Nota Credito Exp - Agregar Aduana -  modalidad: EN CONSIGNACION CON UN MINIMO A FIRME - Clausula: TRANSPORTE Y SEGURO PAGADO HASTA
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
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
		
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "4 - EN CONSIGNACION CON UN MINIMO A FIRME");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "12 - TRANSPORTE Y SEGURO PAGADO HASTA");
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("CODELCO TEC SPA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("MINIMO A FIRME") &&
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("0,0001")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("0,0001")) {
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
	public void Script_0131() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NCE_0131";
		// Emisión DTE - Individual - Nota Credito Exp - Agregar Aduana -  modalidad: EN CONSIGNACION CON UN MINIMO A FIRME - Clausula: ENTREGADAS EN PUERTO DESTINO
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
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
		
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "4 - EN CONSIGNACION CON UN MINIMO A FIRME");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "17 - ENTREGADAS EN PUERTO DESTINO");
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("CODELCO TEC SPA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("MINIMO A FIRME") &&
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("0,0001")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("0,0001")) {
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
	public void Script_0132() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NCE_0132";
		// Emisión DTE - Individual - Nota Credito Exp - Agregar Aduana -  modalidad: EN CONSIGNACION CON UN MINIMO A FIRME - Clausula: ENTREGADAS EN LUGAR ACORDADO
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
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
		
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "4 - EN CONSIGNACION CON UN MINIMO A FIRME");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "18 - ENTREGADAS EN LUGAR ACORDADO");
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("CODELCO TEC SPA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("MINIMO A FIRME") &&
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("0,0001")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("0,0001")) {
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
	public void Script_0133() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NCE_0133";
		// Emisión DTE - Individual - Nota Credito Exp - Agregar Aduana -  modalidad: SIN PAGO - Clausula: COSTO, SEGURO Y FLETE
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
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
		
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "9 - SIN PAGO");
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("CODELCO TEC SPA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("SIN PAGO") &&
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("0,0001")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("0,0001")) {
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
	public void Script_0134() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NCE_0134";
		// Emisión DTE - Individual - Nota Credito Exp - Agregar Aduana -  modalidad: SIN PAGO - Clausula: COSTO Y FLETE
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
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
		
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "9 - SIN PAGO");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "2 - COSTO Y FLETE");
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("CODELCO TEC SPA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("SIN PAGO") &&
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("0,0001")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("0,0001")) {
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
	public void Script_0135() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NCE_0135";
		// Emisión DTE - Individual - Nota Credito Exp - Agregar Aduana -  modalidad: SIN PAGO - Clausula:  EN FÁBRICA
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
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
		
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "9 - SIN PAGO");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "3 - EN FÁBRICA");
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("CODELCO TEC SPA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("SIN PAGO") &&
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("0,0001")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("0,0001")) {
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
	public void Script_0136() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NCE_0136";
		// Emisión DTE - Individual - Nota Credito Exp - Agregar Aduana -  modalidad: SIN PAGO - Clausula: FRANCO AL COSTADO DEL BUQUE
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
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
		
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "9 - SIN PAGO");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "4 - FRANCO AL COSTADO DEL BUQUE");
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("CODELCO TEC SPA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("SIN PAGO") &&
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("0,0001")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("0,0001")) {
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
	public void Script_0137() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NCE_0137";
		// Emisión DTE - Individual - Nota Credito Exp - Agregar Aduana -  modalidad: SIN PAGO - Clausula: FRANCO A BORDO
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
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
		
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "9 - SIN PAGO");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "5 - FRANCO A BORDO");
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("CODELCO TEC SPA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("SIN PAGO") &&
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("0,0001")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("0,0001")) {
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
	public void Script_0138() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NCE_0138";
		// Emisión DTE - Individual - Nota Credito Exp - Agregar Aduana -  modalidad: SIN PAGO - Clausula: SIN CLAUSULA DE COMPRAVENTA
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
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
		
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "9 - SIN PAGO");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "6 - SIN CLAUSULA DE COMPRAVENTA");
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("CODELCO TEC SPA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("SIN PAGO") &&
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("0,0001")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("0,0001")) {
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
	public void Script_0139() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NCE_0139";
		// Emisión DTE - Individual - Nota Credito Exp - Agregar Aduana -  modalidad: SIN PAGO - Clausula: OTROS
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
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
		
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "9 - SIN PAGO");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "8 - OTROS");
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("CODELCO TEC SPA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("SIN PAGO") &&
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("0,0001")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("0,0001")) {
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
	public void Script_0140() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NCE_0140";
		// Emisión DTE - Individual - Nota Credito Exp - Agregar Aduana -  modalidad: SIN PAGO - Clausula: ENTREGADAS DERECHOS PAGADOS
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
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
		
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "9 - SIN PAGO");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "9 - ENTREGADAS DERECHOS PAGADOS");
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("CODELCO TEC SPA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("SIN PAGO") &&
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("0,0001")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("0,0001")) {
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
	public void Script_0141() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NCE_0141";
		// Emisión DTE - Individual - Nota Credito Exp - Agregar Aduana -  modalidad: SIN PAGO - Clausula:  FRANCO TRANSPORTISTA
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
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
		
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "9 - SIN PAGO");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "10 - FRANCO TRANSPORTISTA");
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("CODELCO TEC SPA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("SIN PAGO") &&
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("0,0001")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("0,0001")) {
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
	public void Script_0142() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NCE_0142";
		// Emisión DTE - Individual - Nota Credito Exp - Agregar Aduana -  modalidad: SIN PAGO - Clausula:  TRANSPORTE PAGADO HASTA
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
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
		
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "9 - SIN PAGO");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "11 - TRANSPORTE PAGADO HASTA");
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("CODELCO TEC SPA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("SIN PAGO") &&
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("0,0001")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("0,0001")) {
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
	public void Script_0143() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NCE_0143";
		// Emisión DTE - Individual - Nota Credito Exp - Agregar Aduana -  modalidad: SIN PAGO - Clausula: TRANSPORTE Y SEGURO PAGADO HASTA
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
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
		
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "9 - SIN PAGO");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "12 - TRANSPORTE Y SEGURO PAGADO HASTA");
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("CODELCO TEC SPA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("SIN PAGO") &&
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("0,0001")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("0,0001")) {
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
	public void Script_0144() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NCE_0144";
		// Emisión DTE - Individual - Nota Credito Exp - Agregar Aduana -  modalidad: SIN PAGO - Clausula:  ENTREGADAS EN PUERTO DESTINO
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
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
		
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "9 - SIN PAGO");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "17 - ENTREGADAS EN PUERTO DESTINO");
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("CODELCO TEC SPA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("SIN PAGO") &&
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("0,0001")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("0,0001")) {
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
	public void Script_0145() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NCE_0145";
		// Emisión DTE - Individual - Nota Credito Exp - Agregar Aduana -  modalidad: SIN PAGO - Clausula:  ENTREGADAS EN LUGAR ACORDADO
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
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
		
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "9 - SIN PAGO");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "18 - ENTREGADAS EN LUGAR ACORDADO");
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("CODELCO TEC SPA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("SIN PAGO") &&
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("0,0001")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("0,0001")) {
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
	public void Script_0146() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NCE_0146";
		// Emisión DTE - Individual - Nota Credito Exp -  parámetros adicionales
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
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
		
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "9 - SIN PAGO");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "18 - ENTREGADAS EN LUGAR ACORDADO");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "10", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoParametrosAdicionales(cp, "PRUEBA QA", "11.111.111-1");
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "13 - DOLAR USA");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE CRÉDITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("CODELCO TEC SPA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[5]/td[3]")).getText().contains("PRUEBA QA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[6]/td[3]")).getText().contains("11.111.111-1") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("SIN PAGO") &&
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("0,0001")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("0,0001")) {
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
	public void Script_0147() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NCE_0147";
		// Emisión DTE - Individual - Nota Credito Exp -  parámetros agrupados adicionales
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
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
		
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "9 - SIN PAGO");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "18 - ENTREGADAS EN LUGAR ACORDADO");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "10", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoParametrosAgrupadosAdicionales(cp, "coca_cola", "Efectivo");
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "13 - DOLAR USA");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE CRÉDITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("CODELCO TEC SPA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("SIN PAGO") &&
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("0,0001")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("0,0001")) {
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
	public void Script_0148() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NCE_0148";
		// Emisión DTE - Individual - Nota Credito Exp -  con observaciones
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
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
		
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "9 - SIN PAGO");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "18 - ENTREGADAS EN LUGAR ACORDADO");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "10", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoObservaciones(cp, "Observación de Prueba");
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "13 - DOLAR USA");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE CRÉDITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("CODELCO TEC SPA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("SIN PAGO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO")&&
				
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("DOLAR USA")&&
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000")&&
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000")&&
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[6]/tbody/tr/td/table/tbody/tr[2]/td")).getText().contains("Observación de Prueba") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("3003648")&&
				
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("0,0001")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("0,0001")) {
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
	public void Script_0149() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NCE_0149";
		// Emisión DTE - Individual - Nota Credito Exp -  sin observaciones
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
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
		
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "9 - SIN PAGO");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "18 - ENTREGADAS EN LUGAR ACORDADO");
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("CODELCO TEC SPA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("SIN PAGO") &&
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("0,0001")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("0,0001")) {
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
	public void Script_0151() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NCE_0151";
		// Emisión DTE - Individual - Nota Credito Exp -  Limpiar
		System.out.println(cp);
		String resultado1 = null;
		String resultado2 = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
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
		
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "9 - SIN PAGO");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "18 - ENTREGADAS EN LUGAR ACORDADO");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "10", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "13 - DOLAR USA");
		
		pageEmisionDTE.ClickLimpiar(cp);
		
		List<WebElement> lista_TipoDocumento = new ArrayList<WebElement>();
		lista_TipoDocumento = pageEscritorio.BuscarTipoDocumento();
		
		// Definiendo la lista de referencia
		List<String> lista_referencia = new ArrayList<String>();
		lista_referencia.add("Documento a Emitir");
		lista_referencia.add("Factura Afecta Electrónica");
		lista_referencia.add("Factura Exenta Electrónica");
		lista_referencia.add("Nota de Débito Electrónica");
		lista_referencia.add("Nota de Crédito Electrónica");
		lista_referencia.add("Guía de Despacho Electrónica");
		lista_referencia.add("Boleta Afecta Electrónica");
		lista_referencia.add("Boleta Exenta Electrónica");
		lista_referencia.add("Factura de Exportación Electrónica");
		lista_referencia.add("Nota de Débito de Exportación Electrónica");
		lista_referencia.add("Nota de Crédito de Exportación Electrónica");
		
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
	
	
	
//	@AfterMethod
//	public void FinEjecucion() {
//		driver.close();
//	}

}
