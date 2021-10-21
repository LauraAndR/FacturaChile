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
import org.openqa.selenium.JavascriptExecutor;
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


public class Tests_NotaDebitoExportacion {
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
		String cp = "NDEXP_002";
		// Emisión DTE - Individual - Nota Debito Exp - contado - factura de Servicios
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");	
	
		pageEmisionDTE.BtnEstablecerReferencias(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);

		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "1 - A FIRME");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "1 - COSTO, SEGURO Y FLETE");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
		String cp = "NDEXP_003";
		// Emisión DTE - Individual - Nota Debito Exp - contado - Servicios de hoteleria
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "4 - Servicios de Hotelería");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");	
	
		pageEmisionDTE.BtnEstablecerReferencias(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);

		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "1 - A FIRME");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "1 - COSTO, SEGURO Y FLETE");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Servicios de Hotelería") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
		String cp = "NDEXP_004";
		// Emisión DTE - Individual - Nota Debito Exp - contado - Servicio de Transporte Terrestre Internacional
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "5 - Servicio de Transporte Terrestre Internacional");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");	
	
		pageEmisionDTE.BtnEstablecerReferencias(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);

		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "1 - A FIRME");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "1 - COSTO, SEGURO Y FLETE");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
		String cp = "NDEXP_005";
		// Emisión DTE - Individual - Nota Debito Exp - credito - factura de Servicios
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Crédito");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");	
	
		pageEmisionDTE.BtnEstablecerReferencias(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);

		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "1 - A FIRME");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "1 - COSTO, SEGURO Y FLETE");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Crédito") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
		String cp = "NDEXP_006";
		// Emisión DTE - Individual - Nota Debito Exp - credito - Servicios de hoteleria
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Crédito");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "4 - Servicios de Hotelería");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");	
	
		pageEmisionDTE.BtnEstablecerReferencias(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);

		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "1 - A FIRME");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "1 - COSTO, SEGURO Y FLETE");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Servicios de Hotelería") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Crédito") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
		String cp = "NDEXP_007";
		// Emisión DTE - Individual - Nota Debito Exp - credito - Servicio de Transporte Terrestre Internacional
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Crédito");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "5 - Servicio de Transporte Terrestre Internacional");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");	
	
		pageEmisionDTE.BtnEstablecerReferencias(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);

		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "1 - A FIRME");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "1 - COSTO, SEGURO Y FLETE");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Crédito") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
		String cp = "NDEXP_008";
		// Emisión DTE - Individual - Nota Debito Exp - sin costo - factura de Servicios
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Sin Costo");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");	
	
		pageEmisionDTE.BtnEstablecerReferencias(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);

		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "1 - A FIRME");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "1 - COSTO, SEGURO Y FLETE");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Sin costo(Entrega Gratuita)") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
		String cp = "NDEXP_009";
		// Emisión DTE - Individual - Nota Debito Exp - Sin Costo - Servicios de hoteleria
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Sin Costo");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "4 - Servicios de Hotelería");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");	
	
		pageEmisionDTE.BtnEstablecerReferencias(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);

		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "1 - A FIRME");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "1 - COSTO, SEGURO Y FLETE");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Servicios de Hotelería") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Sin costo(Entrega Gratuita)") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
		String cp = "NDEXP_010";
		// Emisión DTE - Individual - Nota Debito Exp - Sin Costo - Servicio de Transporte Terrestre Internacional
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Sin Costo");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "5 - Servicio de Transporte Terrestre Internacional");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");	
	
		pageEmisionDTE.BtnEstablecerReferencias(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);

		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "1 - A FIRME");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "1 - COSTO, SEGURO Y FLETE");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Sin costo(Entrega Gratuita)") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
		String cp = "NDEXP_015";
		// Emisión DTE - Individual - Nota Debito Exp - producto con código ítem
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportacionCodigoItem(cp, "000001", "1", "01", "500");
	
		pageEmisionDTE.BtnEstablecerReferencias(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);

		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "1 - A FIRME");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "1 - COSTO, SEGURO Y FLETE");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
		String cp = "NDEXP_019";
		// Emisión DTE - Individual - Referencias - Factura de Exportación Electrónica - Anula documento
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportacionCodigoItem(cp, "000001", "1", "01", "500");
	
		pageEmisionDTE.BtnEstablecerReferencias(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);

		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "1 - A FIRME");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "1 - COSTO, SEGURO Y FLETE");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
		String cp = "NDEXP_020";
		// Emisión DTE - Individual - Referencias - Nota Crédito de Exportación Electrónica - Anula documento
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
	
		pageEmisionDTE.BtnEstablecerReferencias(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);

		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "112 - Nota de Crédito de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "1 - A FIRME");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "1 - COSTO, SEGURO Y FLETE");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Nota de Crédito de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
		String cp = "NDEXP_038";
		// Emisión DTE - Individual - Referencias - Factura de Exportacion Electronica - Corrige Monto
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
	
		pageEmisionDTE.BtnEstablecerReferencias(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);

		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "3 - Corrige Monto");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "1 - A FIRME");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "1 - COSTO, SEGURO Y FLETE");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Corrige Monto:")){
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
		String cp = "NDEXP_039";
		// Emisión DTE - Individual - Referencias - Nota de Credito de Exportacion Electronica - Corrige Monto
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
	
		pageEmisionDTE.BtnEstablecerReferencias(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);

		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "112 - Nota de Crédito de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "3 - Corrige Monto");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "1 - A FIRME");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "1 - COSTO, SEGURO Y FLETE");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Nota de Crédito de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Corrige Monto:")){
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
		String cp = "NDEXP_056";
		// Emisión DTE - Individual - Referencias - Indicador de Referencia Global
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
	
		pageEmisionDTE.BtnEstablecerReferencias(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);

		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "112 - Nota de Crédito de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "3 - Corrige Monto");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.ClickCheckboxIndicadorReferenciaGlobal(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "1 - A FIRME");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "1 - COSTO, SEGURO Y FLETE");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Nota de Crédito de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Corrige Monto:")){
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
		String cp = "NDEXP_058";
		// Emisión DTE - Individual - Nota Debito Exp - Agregar Transporte con Despacho por cuenta del receptor
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);
		
		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);

		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");

		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "1 - A FIRME");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "1 - COSTO, SEGURO Y FLETE");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
	public void Script_0059() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NDEXP_059";
		// Emisión DTE - Individual - Nota Debito Exp - Agregar Transporte con Despacho por cuenta del emisor
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);
		
		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);

		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "2 - Despacho por Cuenta del Emisor");

		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "1 - A FIRME");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "1 - COSTO, SEGURO Y FLETE");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
	public void Script_0060() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NDEXP_060";
		// Emisión DTE - Individual - Nota Debito Exp - Agregar Transporte con Despacho por cuenta del emisor a otras instalaciones
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);
		
		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);

		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "3 - Despacho por Cuenta del Emisor a Otras Instalaciones");

		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "1 - A FIRME");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "1 - COSTO, SEGURO Y FLETE");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
	public void Script_0062() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NDEXP_062";
		// Emisión DTE - Individual - Nota Debito Exp - Agregar Aduana - modalidad: a firme - Clausula: costo, seguro y flete
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);
		
		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);

		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "3 - Despacho por Cuenta del Emisor a Otras Instalaciones");

		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "1 - A FIRME");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "1 - COSTO, SEGURO Y FLETE");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
	public void Script_0063() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NDEXP_063";
		// Emisión DTE - Individual - Nota Debito Exp - Agregar Aduana - modalidad: a firme - Clausula: costo y flete
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);
		
		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);

		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "3 - Despacho por Cuenta del Emisor a Otras Instalaciones");

		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "1 - A FIRME");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "2 - COSTO Y FLETE");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
	public void Script_0064() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NDEXP_064";
		// Emisión DTE - Individual - Nota Debito Exp - Agregar Aduana - modalidad: a firme - Clausula: en fabrica
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);
		
		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);

		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "3 - Despacho por Cuenta del Emisor a Otras Instalaciones");

		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "1 - A FIRME");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "3 - EN FÁBRICA");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
		String cp = "NDEXP_065";
		// Emisión DTE - Individual - Nota Debito Exp - Agregar Aduana - modalidad: a firme - Clausula: FRANCO AL COSTADO DEL BUQUE
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);
		
		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);

		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "3 - Despacho por Cuenta del Emisor a Otras Instalaciones");

		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "1 - A FIRME");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "4 - FRANCO AL COSTADO DEL BUQUE");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
		String cp = "NDEXP_066";
		// Emisión DTE - Individual - Nota Debito Exp - Agregar Aduana - modalidad: a firme - Clausula: FRANCO A BORDO
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);
		
		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);

		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "3 - Despacho por Cuenta del Emisor a Otras Instalaciones");

		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "1 - A FIRME");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "5 - FRANCO A BORDO");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
	public void Script_0067() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NDEXP_067";
		// Emisión DTE - Individual - Nota Debito Exp - Agregar Aduana - modalidad: a firme - Clausula: SIN CLAUSULA DE COMPRAVENTA
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);
		
		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);

		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "3 - Despacho por Cuenta del Emisor a Otras Instalaciones");

		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "1 - A FIRME");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "6 - SIN CLAUSULA DE COMPRAVENTA");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
		String cp = "NDEXP_068";
		// Emisión DTE - Individual - Nota Debito Exp - Agregar Aduana - modalidad: a firme - Clausula: OTROS
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);
		
		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);

		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "3 - Despacho por Cuenta del Emisor a Otras Instalaciones");

		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "1 - A FIRME");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "8 - OTROS");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
	public void Script_0069() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NDEXP_069";
		// Emisión DTE - Individual - Nota Debito Exp - Agregar Aduana - modalidad: a firme - Clausula: ENTREGADAS DERECHOS PAGADOS
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);
		
		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);

		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "3 - Despacho por Cuenta del Emisor a Otras Instalaciones");

		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "1 - A FIRME");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "9 - ENTREGADAS DERECHOS PAGADOS");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
		String cp = "NDEXP_070";
		// Emisión DTE - Individual - Nota Debito Exp - Agregar Aduana - modalidad: a firme - Clausula: FRANCO TRANSPORTISTA
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);
		
		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);

		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "3 - Despacho por Cuenta del Emisor a Otras Instalaciones");

		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "1 - A FIRME");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "10 - FRANCO TRANSPORTISTA");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
		String cp = "NDEXP_071";
		// Emisión DTE - Individual - Nota Debito Exp - Agregar Aduana - modalidad: a firme - Clausula:  TRANSPORTE PAGADO HASTA
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);
		
		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);

		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "3 - Despacho por Cuenta del Emisor a Otras Instalaciones");

		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "1 - A FIRME");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "11 - TRANSPORTE PAGADO HASTA");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
		String cp = "NDEXP_072";
		// Emisión DTE - Individual - Nota Debito Exp - Agregar Aduana - modalidad: a firme - Clausula: TRANSPORTE Y SEGURO PAGADO HASTA
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);
		
		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);

		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "3 - Despacho por Cuenta del Emisor a Otras Instalaciones");

		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "1 - A FIRME");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "12 - TRANSPORTE Y SEGURO PAGADO HASTA");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
		String cp = "NDEXP_073";
		// Emisión DTE - Individual - Nota Debito Exp - Agregar Aduana - modalidad: a firme - Clausula: ENTREGADAS EN PUERTO DESTINO
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);
		
		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);

		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "3 - Despacho por Cuenta del Emisor a Otras Instalaciones");

		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "1 - A FIRME");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "17 - ENTREGADAS EN PUERTO DESTINO");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
		String cp = "NDEXP_074";
		// Emisión DTE - Individual - Nota Debito Exp - Agregar Aduana - modalidad: a firme - Clausula:  ENTREGADAS EN LUGAR ACORDADO
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);
		
		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);

		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "3 - Despacho por Cuenta del Emisor a Otras Instalaciones");

		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "1 - A FIRME");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "18 - ENTREGADAS EN LUGAR ACORDADO");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
		String cp = "NDEXP_075";
		// Emisión DTE - Individual - Nota Debito Exp - Agregar Aduana -  modalidad: bajo condición - Clausula: costo, seguro y flete
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);
		
		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);

		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "3 - Despacho por Cuenta del Emisor a Otras Instalaciones");

		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "2 - BAJO CONDICION");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "1 - COSTO, SEGURO Y FLETE");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("BAJO CONDICION") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
		String cp = "NDEXP_076";
		// Emisión DTE - Individual - Nota Debito Exp - Agregar Aduana -  modalidad: bajo condición - Clausula:  COSTO Y FLETE
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);
		
		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);

		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "3 - Despacho por Cuenta del Emisor a Otras Instalaciones");

		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "2 - BAJO CONDICION");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "2 - COSTO Y FLETE");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("BAJO CONDICION") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
		String cp = "NDEXP_077";
		// Emisión DTE - Individual - Nota Debito Exp - Agregar Aduana -  modalidad: bajo condición - Clausula:  EN FÁBRICA
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);
		
		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);

		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "3 - Despacho por Cuenta del Emisor a Otras Instalaciones");

		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "2 - BAJO CONDICION");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "3 - EN FÁBRICA");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("BAJO CONDICION") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
		String cp = "NDEXP_078";
		// Emisión DTE - Individual - Nota Debito Exp - Agregar Aduana -  modalidad: bajo condición - Clausula:  FRANCO AL COSTADO DEL BUQUE
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);
		
		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);

		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "3 - Despacho por Cuenta del Emisor a Otras Instalaciones");

		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "2 - BAJO CONDICION");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "4 - FRANCO AL COSTADO DEL BUQUE");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("BAJO CONDICION") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
		String cp = "NDEXP_079";
		// Emisión DTE - Individual - Nota Debito Exp - Agregar Aduana -  modalidad: bajo condición - Clausula:  FRANCO A BORDO
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);
		
		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);

		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "3 - Despacho por Cuenta del Emisor a Otras Instalaciones");

		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "2 - BAJO CONDICION");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "5 - FRANCO A BORDO");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("BAJO CONDICION") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
	public void Script_0080() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NDEXP_080";
		// Emisión DTE - Individual - Nota Debito Exp - Agregar Aduana -  modalidad: bajo condición - Clausula:  SIN CLAUSULA DE COMPRAVENTA
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);
		
		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);

		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "3 - Despacho por Cuenta del Emisor a Otras Instalaciones");

		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "2 - BAJO CONDICION");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "6 - SIN CLAUSULA DE COMPRAVENTA");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("BAJO CONDICION") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
	public void Script_0081() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NDEXP_081";
		// Emisión DTE - Individual - Nota Debito Exp - Agregar Aduana -  modalidad: bajo condición - Clausula:  OTROS
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);
		
		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);

		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "3 - Despacho por Cuenta del Emisor a Otras Instalaciones");

		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "2 - BAJO CONDICION");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "8 - OTROS");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("BAJO CONDICION") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
	public void Script_0082() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NDEXP_082";
		// Emisión DTE - Individual - Nota Debito Exp - Agregar Aduana -  modalidad: bajo condición - Clausula:  ENTREGADAS DERECHOS PAGADOS
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);
		
		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);

		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "3 - Despacho por Cuenta del Emisor a Otras Instalaciones");

		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "2 - BAJO CONDICION");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "9 - ENTREGADAS DERECHOS PAGADOS");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("BAJO CONDICION") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
	public void Script_0083() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NDEXP_083";
		// Emisión DTE - Individual - Nota Debito Exp - Agregar Aduana -  modalidad: bajo condición - Clausula:  FRANCO TRANSPORTISTA
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);
		
		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);

		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "3 - Despacho por Cuenta del Emisor a Otras Instalaciones");

		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "2 - BAJO CONDICION");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "10 - FRANCO TRANSPORTISTA");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("BAJO CONDICION") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
	public void Script_0084() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NDEXP_084";
		// Emisión DTE - Individual - Nota Debito Exp - Agregar Aduana -  modalidad: bajo condición - Clausula:  TRANSPORTE PAGADO HASTA
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);
		
		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);

		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "3 - Despacho por Cuenta del Emisor a Otras Instalaciones");

		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "2 - BAJO CONDICION");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "11 - TRANSPORTE PAGADO HASTA");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("BAJO CONDICION") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
	public void Script_0085() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NDEXP_085";
		// Emisión DTE - Individual - Nota Debito Exp - Agregar Aduana -  modalidad: bajo condición - Clausula: TRANSPORTE Y SEGURO PAGADO HASTA
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);
		
		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);

		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "3 - Despacho por Cuenta del Emisor a Otras Instalaciones");

		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "2 - BAJO CONDICION");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "12 - TRANSPORTE Y SEGURO PAGADO HASTA");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("BAJO CONDICION") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
	public void Script_0086() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NDEXP_086";
		// Emisión DTE - Individual - Nota Debito Exp - Agregar Aduana -  modalidad: bajo condición - Clausula:  ENTREGADAS EN PUERTO DESTINO
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);
		
		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);

		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "3 - Despacho por Cuenta del Emisor a Otras Instalaciones");

		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "2 - BAJO CONDICION");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "17 - ENTREGADAS EN PUERTO DESTINO");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("BAJO CONDICION") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
	public void Script_0087() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NDEXP_087";
		// Emisión DTE - Individual - Nota Debito Exp - Agregar Aduana -  modalidad: bajo condición - Clausula: ENTREGADAS EN LUGAR ACORDADO
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);
		
		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);

		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "3 - Despacho por Cuenta del Emisor a Otras Instalaciones");

		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "2 - BAJO CONDICION");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "18 - ENTREGADAS EN LUGAR ACORDADO");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("BAJO CONDICION") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
	public void Script_0088() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NDEXP_088";
		// Emisión DTE - Individual - Nota Debito Exp - Agregar Aduana -   modalidad: en consignación libre - Clausula: costo, seguro y flete
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);
		
		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);

		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "3 - Despacho por Cuenta del Emisor a Otras Instalaciones");

		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "3 - EN CONSIGNACION LIBRE");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "1 - COSTO, SEGURO Y FLETE");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("EN CONSIGNACION LIBRE") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
	public void Script_0089() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NDEXP_089";
		// Emisión DTE - Individual - Nota Debito Exp - Agregar Aduana -   modalidad: en consignación libre - Clausula:  COSTO Y FLETE
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);
		
		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);

		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "3 - Despacho por Cuenta del Emisor a Otras Instalaciones");

		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "3 - EN CONSIGNACION LIBRE");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "2 - COSTO Y FLETE");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("EN CONSIGNACION LIBRE") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
	public void Script_0090() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NDEXP_090";
		// Emisión DTE - Individual - Nota Debito Exp - Agregar Aduana -   modalidad: en consignación libre - Clausula:  EN FÁBRICA
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);
		
		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);

		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "3 - Despacho por Cuenta del Emisor a Otras Instalaciones");

		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "3 - EN CONSIGNACION LIBRE");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "3 - EN FÁBRICA");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("EN CONSIGNACION LIBRE") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
	public void Script_0091() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NDEXP_091";
		// Emisión DTE - Individual - Nota Debito Exp - Agregar Aduana -   modalidad: en consignación libre - Clausula:  FRANCO AL COSTADO DEL BUQUE
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);
		
		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);

		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "3 - Despacho por Cuenta del Emisor a Otras Instalaciones");

		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "3 - EN CONSIGNACION LIBRE");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "4 - FRANCO AL COSTADO DEL BUQUE");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("EN CONSIGNACION LIBRE") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
	public void Script_0092() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NDEXP_092";
		// Emisión DTE - Individual - Nota Debito Exp - Agregar Aduana -   modalidad: en consignación libre - Clausula:  FRANCO A BORDO
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);
		
		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);

		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "3 - Despacho por Cuenta del Emisor a Otras Instalaciones");

		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "3 - EN CONSIGNACION LIBRE");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "5 - FRANCO A BORDO");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("EN CONSIGNACION LIBRE") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
	public void Script_0093() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NDEXP_093";
		// Emisión DTE - Individual - Nota Debito Exp - Agregar Aduana -   modalidad: en consignación libre - Clausula:  SIN CLAUSULA DE COMPRAVENTA
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);
		
		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);

		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "3 - Despacho por Cuenta del Emisor a Otras Instalaciones");

		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "3 - EN CONSIGNACION LIBRE");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "6 - SIN CLAUSULA DE COMPRAVENTA");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("EN CONSIGNACION LIBRE") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
	public void Script_0094() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NDEXP_094";
		// Emisión DTE - Individual - Nota Debito Exp - Agregar Aduana -   modalidad: en consignación libre - Clausula: OTROS
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);
		
		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);

		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "3 - Despacho por Cuenta del Emisor a Otras Instalaciones");

		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "3 - EN CONSIGNACION LIBRE");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "8 - OTROS");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("EN CONSIGNACION LIBRE") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
	public void Script_0095() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NDEXP_095";
		// Emisión DTE - Individual - Nota Debito Exp - Agregar Aduana -   modalidad: en consignación libre - Clausula:  ENTREGADAS DERECHOS PAGADOS
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);
		
		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);

		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "3 - Despacho por Cuenta del Emisor a Otras Instalaciones");

		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "3 - EN CONSIGNACION LIBRE");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "9 - ENTREGADAS DERECHOS PAGADOS");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("EN CONSIGNACION LIBRE") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
	public void Script_0096() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NDEXP_096";
		// Emisión DTE - Individual - Nota Debito Exp - Agregar Aduana -   modalidad: en consignación libre - Clausula:  FRANCO TRANSPORTISTA
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);
		
		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);

		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "3 - Despacho por Cuenta del Emisor a Otras Instalaciones");

		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "3 - EN CONSIGNACION LIBRE");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "10 - FRANCO TRANSPORTISTA");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("EN CONSIGNACION LIBRE") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
	public void Script_0097() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NDEXP_097";
		// Emisión DTE - Individual - Nota Debito Exp - Agregar Aduana -   modalidad: en consignación libre - Clausula: TRANSPORTE PAGADO HASTA
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);
		
		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);

		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "3 - Despacho por Cuenta del Emisor a Otras Instalaciones");

		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "3 - EN CONSIGNACION LIBRE");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "11 - TRANSPORTE PAGADO HASTA");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("EN CONSIGNACION LIBRE") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
	public void Script_0098() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NDEXP_098";
		// Emisión DTE - Individual - Nota Debito Exp - Agregar Aduana -   modalidad: en consignación libre - Clausula: TRANSPORTE Y SEGURO PAGADO HASTA
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);
		
		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);

		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "3 - Despacho por Cuenta del Emisor a Otras Instalaciones");

		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "3 - EN CONSIGNACION LIBRE");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "12 - TRANSPORTE Y SEGURO PAGADO HASTA");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("EN CONSIGNACION LIBRE") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
	public void Script_0099() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NDEXP_099";
		// Emisión DTE - Individual - Nota Debito Exp - Agregar Aduana -   modalidad: en consignación libre - Clausula:  ENTREGADAS EN PUERTO DESTINO
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);
		
		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);

		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "3 - Despacho por Cuenta del Emisor a Otras Instalaciones");

		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "3 - EN CONSIGNACION LIBRE");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "17 - ENTREGADAS EN PUERTO DESTINO");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("EN CONSIGNACION LIBRE") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
	public void Script_0100() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "NDEXP_100";
		// Emisión DTE - Individual - Nota Debito Exp - Agregar Aduana -   modalidad: en consignación libre - Clausula:  ENTREGADAS EN LUGAR ACORDADO
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);
		
		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);

		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "3 - Despacho por Cuenta del Emisor a Otras Instalaciones");

		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "3 - EN CONSIGNACION LIBRE");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "18 - ENTREGADAS EN LUGAR ACORDADO");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("EN CONSIGNACION LIBRE") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
		String cp = "NDEXP_101";
		// Emisión DTE - Individual - Nota Debito Exp - Agregar Aduana -    modalidad: en consignación con un mínimo a firme - Clausula: costo, seguro y flete
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);
		
		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);

		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "3 - Despacho por Cuenta del Emisor a Otras Instalaciones");

		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "4 - EN CONSIGNACION CON UN MINIMO A FIRME");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "1 - COSTO, SEGURO Y FLETE");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("MINIMO A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
		String cp = "NDEXP_102";
		// Emisión DTE - Individual - Nota Debito Exp - Agregar Aduana -    modalidad: en consignación con un mínimo a firme - Clausula:  COSTO Y FLETE
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);
		
		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);

		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "3 - Despacho por Cuenta del Emisor a Otras Instalaciones");

		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "4 - EN CONSIGNACION CON UN MINIMO A FIRME");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "2 - COSTO Y FLETE");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("MINIMO A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
		String cp = "NDEXP_103";
		// Emisión DTE - Individual - Nota Debito Exp - Agregar Aduana -    modalidad: en consignación con un mínimo a firme - Clausula:  EN FÁBRICA
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);
		
		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);

		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "3 - Despacho por Cuenta del Emisor a Otras Instalaciones");

		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "4 - EN CONSIGNACION CON UN MINIMO A FIRME");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "3 - EN FÁBRICA");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("MINIMO A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
		String cp = "NDEXP_104";
		// Emisión DTE - Individual - Nota Debito Exp - Agregar Aduana -    modalidad: en consignación con un mínimo a firme - Clausula:  FRANCO AL COSTADO DEL BUQUE
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);
		
		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);

		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "3 - Despacho por Cuenta del Emisor a Otras Instalaciones");

		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "4 - EN CONSIGNACION CON UN MINIMO A FIRME");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "4 - FRANCO AL COSTADO DEL BUQUE");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("MINIMO A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
		String cp = "NDEXP_105";
		// Emisión DTE - Individual - Nota Debito Exp - Agregar Aduana -    modalidad: en consignación con un mínimo a firme - Clausula:   FRANCO A BORDO
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);
		
		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);

		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "3 - Despacho por Cuenta del Emisor a Otras Instalaciones");

		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "4 - EN CONSIGNACION CON UN MINIMO A FIRME");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "5 - FRANCO A BORDO");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("MINIMO A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
		String cp = "NDEXP_106";
		// Emisión DTE - Individual - Nota Debito Exp - Agregar Aduana -    modalidad: en consignación con un mínimo a firme - Clausula: SIN CLAUSULA DE COMPRAVENTA
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);
		
		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);

		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "3 - Despacho por Cuenta del Emisor a Otras Instalaciones");

		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "4 - EN CONSIGNACION CON UN MINIMO A FIRME");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "6 - SIN CLAUSULA DE COMPRAVENTA");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("MINIMO A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
		String cp = "NDEXP_107";
		// Emisión DTE - Individual - Nota Debito Exp - Agregar Aduana -    modalidad: en consignación con un mínimo a firme - Clausula:   OTROS
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);
		
		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);

		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "3 - Despacho por Cuenta del Emisor a Otras Instalaciones");

		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "4 - EN CONSIGNACION CON UN MINIMO A FIRME");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "8 - OTROS");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("MINIMO A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
		String cp = "NDEXP_108";
		// Emisión DTE - Individual - Nota Debito Exp - Agregar Aduana -    modalidad: en consignación con un mínimo a firme - Clausula: ENTREGADAS DERECHOS PAGADOS
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);
		
		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);

		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "3 - Despacho por Cuenta del Emisor a Otras Instalaciones");

		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "4 - EN CONSIGNACION CON UN MINIMO A FIRME");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "9 - ENTREGADAS DERECHOS PAGADOS");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("MINIMO A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
		String cp = "NDEXP_109";
		// Emisión DTE - Individual - Nota Debito Exp - Agregar Aduana -    modalidad: en consignación con un mínimo a firme - Clausula:  FRANCO TRANSPORTISTA
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);
		
		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);

		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "3 - Despacho por Cuenta del Emisor a Otras Instalaciones");

		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "4 - EN CONSIGNACION CON UN MINIMO A FIRME");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "10 - FRANCO TRANSPORTISTA");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("MINIMO A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
		String cp = "NDEXP_110";
		// Emisión DTE - Individual - Nota Debito Exp - Agregar Aduana -    modalidad: en consignación con un mínimo a firme - Clausula:    TRANSPORTE PAGADO HASTA
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);
		
		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);

		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "3 - Despacho por Cuenta del Emisor a Otras Instalaciones");

		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "4 - EN CONSIGNACION CON UN MINIMO A FIRME");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "11 - TRANSPORTE PAGADO HASTA");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("MINIMO A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
		String cp = "NDEXP_111";
		// Emisión DTE - Individual - Nota Debito Exp - Agregar Aduana -    modalidad: en consignación con un mínimo a firme - Clausula:  TRANSPORTE Y SEGURO PAGADO HASTA
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);
		
		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);

		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "3 - Despacho por Cuenta del Emisor a Otras Instalaciones");

		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "4 - EN CONSIGNACION CON UN MINIMO A FIRME");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "12 - TRANSPORTE Y SEGURO PAGADO HASTA");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("MINIMO A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
		String cp = "NDEXP_112";
		// Emisión DTE - Individual - Nota Debito Exp - Agregar Aduana -    modalidad: en consignación con un mínimo a firme - Clausula:   ENTREGADAS EN PUERTO DESTINO
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);
		
		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);

		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "3 - Despacho por Cuenta del Emisor a Otras Instalaciones");

		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "4 - EN CONSIGNACION CON UN MINIMO A FIRME");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "17 - ENTREGADAS EN PUERTO DESTINO");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("MINIMO A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
		String cp = "NDEXP_113";
		// Emisión DTE - Individual - Nota Debito Exp - Agregar Aduana -    modalidad: en consignación con un mínimo a firme - Clausula:  ENTREGADAS EN LUGAR ACORDADO
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);
		
		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);

		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "3 - Despacho por Cuenta del Emisor a Otras Instalaciones");

		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "4 - EN CONSIGNACION CON UN MINIMO A FIRME");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "5 - FRANCO A BORDO");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("MINIMO A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
		String cp = "NDEXP_114";
		// Emisión DTE - Individual - Nota Debito Exp - Agregar Aduana - modalidad: sin pago - Clausula: costo, seguro y flete
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);
		
		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);

		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "3 - Despacho por Cuenta del Emisor a Otras Instalaciones");

		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "9 - SIN PAGO");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "1 - COSTO, SEGURO Y FLETE");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("SIN PAGO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
		String cp = "NDEXP_115";
		// Emisión DTE - Individual - Nota Debito Exp - Agregar Aduana - modalidad: sin pago - Clausula: COSTO Y FLETE
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);
		
		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);

		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "3 - Despacho por Cuenta del Emisor a Otras Instalaciones");

		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "9 - SIN PAGO");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "2 - COSTO Y FLETE");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("SIN PAGO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
		String cp = "NDEXP_116";
		// Emisión DTE - Individual - Nota Debito Exp - Agregar Aduana - modalidad: sin pago - Clausula: EN FÁBRICA
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);
		
		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);

		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "3 - Despacho por Cuenta del Emisor a Otras Instalaciones");

		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "9 - SIN PAGO");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "3 - EN FÁBRICA");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("SIN PAGO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
		String cp = "NDEXP_117";
		// Emisión DTE - Individual - Nota Debito Exp - Agregar Aduana - modalidad: sin pago - Clausula: FRANCO AL COSTADO DEL BUQUE
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);
		
		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);

		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "3 - Despacho por Cuenta del Emisor a Otras Instalaciones");

		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "9 - SIN PAGO");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "4 - FRANCO AL COSTADO DEL BUQUE");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("SIN PAGO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
		String cp = "NDEXP_118";
		// Emisión DTE - Individual - Nota Debito Exp - Agregar Aduana - modalidad: sin pago - Clausula:  FRANCO A BORDO
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);
		
		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);

		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "3 - Despacho por Cuenta del Emisor a Otras Instalaciones");

		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "9 - SIN PAGO");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "5 - FRANCO A BORDO");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("SIN PAGO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
		String cp = "NDEXP_119";
		// Emisión DTE - Individual - Nota Debito Exp - Agregar Aduana - modalidad: sin pago - Clausula:  SIN CLAUSULA DE COMPRAVENTA
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);
		
		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);

		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "3 - Despacho por Cuenta del Emisor a Otras Instalaciones");

		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "9 - SIN PAGO");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "6 - SIN CLAUSULA DE COMPRAVENTA");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("SIN PAGO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
		String cp = "NDEXP_120";
		// Emisión DTE - Individual - Nota Debito Exp - Agregar Aduana - modalidad: sin pago - Clausula: OTROS
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);
		
		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);

		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "3 - Despacho por Cuenta del Emisor a Otras Instalaciones");

		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "9 - SIN PAGO");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "8 - OTROS");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("SIN PAGO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
		String cp = "NDEXP_121";
		// Emisión DTE - Individual - Nota Debito Exp - Agregar Aduana - modalidad: sin pago - Clausula: ENTREGADAS DERECHOS PAGADOS
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);
		
		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);

		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "3 - Despacho por Cuenta del Emisor a Otras Instalaciones");

		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "9 - SIN PAGO");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "9 - ENTREGADAS DERECHOS PAGADOS");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("SIN PAGO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
		String cp = "NDEXP_122";
		// Emisión DTE - Individual - Nota Debito Exp - Agregar Aduana - modalidad: sin pago - Clausula:  FRANCO TRANSPORTISTA
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);
		
		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);

		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "3 - Despacho por Cuenta del Emisor a Otras Instalaciones");

		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "9 - SIN PAGO");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "10 - FRANCO TRANSPORTISTA");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("SIN PAGO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
		String cp = "NDEXP_123";
		// Emisión DTE - Individual - Nota Debito Exp - Agregar Aduana - modalidad: sin pago - Clausula:  TRANSPORTE PAGADO HASTA
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);
		
		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);

		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "3 - Despacho por Cuenta del Emisor a Otras Instalaciones");

		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "9 - SIN PAGO");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "11 - TRANSPORTE PAGADO HASTA");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("SIN PAGO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
		String cp = "NDEXP_124";
		// Emisión DTE - Individual - Nota Debito Exp - Agregar Aduana - modalidad: sin pago - Clausula: TRANSPORTE Y SEGURO PAGADO HASTA
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);
		
		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);

		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "3 - Despacho por Cuenta del Emisor a Otras Instalaciones");

		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "9 - SIN PAGO");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "12 - TRANSPORTE Y SEGURO PAGADO HASTA");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("SIN PAGO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
		String cp = "NDEXP_125";
		// Emisión DTE - Individual - Nota Debito Exp - Agregar Aduana - modalidad: sin pago - Clausula:  ENTREGADAS EN PUERTO DESTINO
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);
		
		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);

		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "3 - Despacho por Cuenta del Emisor a Otras Instalaciones");

		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "9 - SIN PAGO");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "17 - ENTREGADAS EN PUERTO DESTINO");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("SIN PAGO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
		String cp = "NDEXP_126";
		// Emisión DTE - Individual - Nota Debito Exp - Agregar Aduana - modalidad: sin pago - Clausula:  ENTREGADAS EN LUGAR ACORDADO
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);
		
		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);

		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "3 - Despacho por Cuenta del Emisor a Otras Instalaciones");

		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "9 - SIN PAGO");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "18 - ENTREGADAS EN LUGAR ACORDADO");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("SIN PAGO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
		String cp = "NDEXP_127";
		// Emisión DTE - Individual - Nota Debito Exp - parámetros adicionales
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);
		
		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);

		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "3 - Despacho por Cuenta del Emisor a Otras Instalaciones");

		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "9 - SIN PAGO");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "18 - ENTREGADAS EN LUGAR ACORDADO");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoParametrosAdicionales(cp, "PRUEBA QA", "11.111.111-1");
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[5]/td[3]")).getText().contains("PRUEBA QA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[6]/td[3]")).getText().contains("11.111.111-1") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("SIN PAGO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
		String cp = "NDEXP_128";
		// Emisión DTE - Individual - Nota Debito Exp - parámetros agrupados adicionales
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);
		
		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);

		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "3 - Despacho por Cuenta del Emisor a Otras Instalaciones");

		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "9 - SIN PAGO");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "18 - ENTREGADAS EN LUGAR ACORDADO");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoParametrosAgrupadosAdicionales(cp, "coca_cola", "Efectivo");
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("SIN PAGO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
		String cp = "NDEXP_129";
		// Emisión DTE - Individual - Nota Debito Exp - con observaciones
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);
		
		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);

		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "3 - Despacho por Cuenta del Emisor a Otras Instalaciones");

		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "9 - SIN PAGO");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "18 - ENTREGADAS EN LUGAR ACORDADO");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoObservaciones(cp, "Observación de Prueba");
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("SIN PAGO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[6]/tbody/tr/td/table/tbody/tr[2]/td")).getText().contains("Observación de Prueba") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
		String cp = "NDEXP_130";
		// Emisión DTE - Individual - Nota Debito Exp - sin observaciones
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);
		
		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);

		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "3 - Despacho por Cuenta del Emisor a Otras Instalaciones");

		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "9 - SIN PAGO");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "18 - ENTREGADAS EN LUGAR ACORDADO");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);

		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("SIN PAGO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
		String cp = "NDEXP_131";
		// Emisión DTE - Individual - Nota Debito Exp - Tipo Moneda
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RICARDO, Configuration.PASS_RICARDO);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);
		
		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);

		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "3 - Despacho por Cuenta del Emisor a Otras Instalaciones");

		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "9 - SIN PAGO");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "18 - ENTREGADAS EN LUGAR ACORDADO");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);

		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("NOTA DE DÉBITO DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("SIN PAGO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[1]/td[2]")).getText().contains("170") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[4]/tbody/tr[2]/td[2]")).getText().contains("1.500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Factura de Exportación Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[4]")).getText().contains("Anula Documento:")){
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
		String cp = "NDEXP_132";
		// Emisión DTE - Individual - Nota Debito Exp - Limpiar
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
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "3 - Factura de Servicios.");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoExportación(cp, "000001", "1");
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);
		
		// Ingresar Referencia
		pageEmisionDTE.SeleccionartipoDoc(cp, "110 - Factura de Exportación Electrónica");
		pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.BtnAgregarReferencia(cp);

		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "3 - Despacho por Cuenta del Emisor a Otras Instalaciones");

		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		// Ingresar Aduana
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "9 - SIN PAGO");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "18 - ENTREGADAS EN LUGAR ACORDADO");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);

		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
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
	
	@AfterMethod
	public void FinEjecucion() {
		driver.close();
	}
	
}
