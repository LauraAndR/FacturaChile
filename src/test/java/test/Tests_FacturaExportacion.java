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


public class Tests_FacturaExportacion {
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
		String cp = "FEXP_002";
		// Emisión DTE - Individual - Factura Exp - contado - factura de Servicios
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
		
		pageEmisionDTE.BtnAgregarAduana(cp);
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "1 - A FIRME");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "1 - COSTO, SEGURO Y FLETE");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		pageEmisionDTE.IngresoTotalBultosAduana(cp, "2");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_003";
		// Emisión DTE - Individual - Factura Exp - contado - Servicios de hoteleria
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
		
		pageEmisionDTE.BtnAgregarAduana(cp);
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "1 - A FIRME");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "1 - COSTO, SEGURO Y FLETE");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		pageEmisionDTE.IngresoTotalBultosAduana(cp, "2");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Servicios de Hotelería") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_004";
		// Emisión DTE - Individual - Factura Exp - contado - Servicio de Transporte Terrestre Internacional
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
		
		pageEmisionDTE.BtnAgregarAduana(cp);
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "1 - A FIRME");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "1 - COSTO, SEGURO Y FLETE");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		pageEmisionDTE.IngresoTotalBultosAduana(cp, "2");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_005";
		// Emisión DTE - Individual - Factura Exp - credito - factura de Servicios
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
		
		pageEmisionDTE.BtnAgregarAduana(cp);
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "1 - A FIRME");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "1 - COSTO, SEGURO Y FLETE");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		pageEmisionDTE.IngresoTotalBultosAduana(cp, "2");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Crédito") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_006";
		// Emisión DTE - Individual - Factura Exp - credito - Servicios de hoteleria
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
		
		pageEmisionDTE.BtnAgregarAduana(cp);
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "1 - A FIRME");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "1 - COSTO, SEGURO Y FLETE");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		pageEmisionDTE.IngresoTotalBultosAduana(cp, "2");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Servicios de Hotelería") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Crédito") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_007";
		// Emisión DTE - Individual - Factura Exp - crédito - Servicio de Transporte Terrestre Internacional
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
		
		pageEmisionDTE.BtnAgregarAduana(cp);
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "1 - A FIRME");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "1 - COSTO, SEGURO Y FLETE");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		pageEmisionDTE.IngresoTotalBultosAduana(cp, "2");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Crédito") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_008";
		// Emisión DTE - Individual - Factura Exp - sin costo - factura de Servicios
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
		
		pageEmisionDTE.BtnAgregarAduana(cp);
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "1 - A FIRME");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "1 - COSTO, SEGURO Y FLETE");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		pageEmisionDTE.IngresoTotalBultosAduana(cp, "2");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Factura de Servicios") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Sin costo(Entrega Gratuita)") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_009";
		// Emisión DTE - Individual - Factura Exp - Sin Costo - Servicios de hoteleria
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
		
		pageEmisionDTE.BtnAgregarAduana(cp);
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "1 - A FIRME");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "1 - COSTO, SEGURO Y FLETE");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		pageEmisionDTE.IngresoTotalBultosAduana(cp, "2");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Servicios de Hotelería") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Sin costo(Entrega Gratuita)") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_010";
		// Emisión DTE - Individual - Factura Exp - Sin costo - Servicio de Transporte Terrestre Internacional
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
		
		pageEmisionDTE.BtnAgregarAduana(cp);
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "1 - A FIRME");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "1 - COSTO, SEGURO Y FLETE");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		pageEmisionDTE.IngresoTotalBultosAduana(cp, "2");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Sin costo(Entrega Gratuita)") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_015";
		// Emisión DTE - Individual - Factura Exp - producto con código ítem
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		pageEmisionDTE.SeleccionarFormaPago(cp, "Sin Costo");
		pageEmisionDTE.SeleccionarIndicadorServicio(cp, "5 - Servicio de Transporte Terrestre Internacional");
		
		pageEmisionDTE.IngresoRazonSocialCliente(cp, "JUAN MANUEL GANTES MELENDEZ");
		pageEmisionDTE.IngresoDatosRazonSocialCliente(cp, "SANTIAGO");
		
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		
		pageEmisionDTE.AgregarProductoCodigoItem(cp, "000001", "1", "01", "500");
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
		
		pageEmisionDTE.BtnAgregarAduana(cp);
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.SeleccionarModalidadAduana(cp, "1 - A FIRME");
		pageEmisionDTE.SeleccionarClausulaAduana(cp, "1 - COSTO, SEGURO Y FLETE");
		pageEmisionDTE.IngresoTotalAduana(cp, "1000");
		pageEmisionDTE.IngresoPaisReceptor(cp, "563 - ALEMANIA");
		pageEmisionDTE.IngresoPuertoEmbarque(cp, "641 - AARHUS");
		pageEmisionDTE.IngresoPuertoDesembarque(cp, "218 - ACAPULCO");
		pageEmisionDTE.SeleccionarViaAduana(cp, "1 - MARITIMA, FLUVIAL Y LACUSTRE");
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		pageEmisionDTE.IngresoTotalBultosAduana(cp, "2");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Sin costo(Entrega Gratuita)") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_018";
		// Emisión DTE - Individual - Factura Exp - Referencias - Pasaporte
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Referencias
		pageEmisionDTE.SeleccionartipoDoc(cp, "813 - Pasaporte");
		pageEmisionDTE.AgregarFolio(cp, "0001");
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
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Sin costo(Entrega Gratuita)") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Pasaporte") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_019";
		// Emisión DTE - Individual - Factura Exp - Referencias - Guia de Despacho
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Referencias
		pageEmisionDTE.SeleccionartipoDoc(cp, "50 - Guía de Despacho");
		pageEmisionDTE.AgregarFolio(cp, "0001");
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
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Sin costo(Entrega Gratuita)") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				//driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Guía de Despacho") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_020";
		// Emisión DTE - Individual - Factura Exp - Referencias - Guia de Despacho electrónica
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Referencias
		pageEmisionDTE.SeleccionartipoDoc(cp, "52 - Guía de Despacho Electrónica");
		pageEmisionDTE.AgregarFolio(cp, "0001");
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
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Sin costo(Entrega Gratuita)") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Guía de Despacho Electrónica") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_021";
		// Emisión DTE - Individual - Factura Exp - Referencias - Oden de Compra
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Referencias
		pageEmisionDTE.SeleccionartipoDoc(cp, "801 - Orden de Compra");
		pageEmisionDTE.AgregarFolio(cp, "0001");
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
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Sin costo(Entrega Gratuita)") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Orden De Compra") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_022";
		// Emisión DTE - Individual - Factura Exp - Referencias - Nota de Pedido
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Referencias
		pageEmisionDTE.SeleccionartipoDoc(cp, "802 - Nota de Pedido");
		pageEmisionDTE.AgregarFolio(cp, "0001");
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
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Sin costo(Entrega Gratuita)") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Nota de Pedido") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_023";
		// Emisión DTE - Individual - Factura Exp - Referencias - Contrato
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Referencias
		pageEmisionDTE.SeleccionartipoDoc(cp, "803 - Contrato");
		pageEmisionDTE.AgregarFolio(cp, "0001");
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
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Sin costo(Entrega Gratuita)") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Contrato") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_024";
		// Emisión DTE - Individual - Factura Exp - Referencias - Resolución
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Referencias
		pageEmisionDTE.SeleccionartipoDoc(cp, "804 - Resolución");
		pageEmisionDTE.AgregarFolio(cp, "0001");
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
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Sin costo(Entrega Gratuita)") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Resolución") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_025";
		// Emisión DTE - Individual - Factura Exp - Referencias - Proceso Chile Compra
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Referencias
		pageEmisionDTE.SeleccionartipoDoc(cp, "805 - Proceso Chile Compra");
		pageEmisionDTE.AgregarFolio(cp, "0001");
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
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Sin costo(Entrega Gratuita)") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Proceso ChileCompra") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_026";
		// Emisión DTE - Individual - Factura Exp - Referencias - Ficha Chile Compra
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Referencias
		pageEmisionDTE.SeleccionartipoDoc(cp, "806 - Ficha Chile Compra");
		pageEmisionDTE.AgregarFolio(cp, "0001");
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
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Sin costo(Entrega Gratuita)") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Ficha ChileCompra") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_027";
		// Emisión DTE - Individual - Factura Exp - Referencias - DUS
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Referencias
		pageEmisionDTE.SeleccionartipoDoc(cp, "807 - DUS");
		pageEmisionDTE.AgregarFolio(cp, "0001");
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
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Sin costo(Entrega Gratuita)") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("DUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_028";
		// Emisión DTE - Individual - Factura Exp - Referencias - B/L (Conocimiento de embarque)
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Referencias
		pageEmisionDTE.SeleccionartipoDoc(cp, "808 - B/L (Conocimiento de embarque)");
		pageEmisionDTE.AgregarFolio(cp, "0001");
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
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);

		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Sin costo(Entrega Gratuita)") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("B/L") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_029";
		// Emisión DTE - Individual - Factura Exp - Referencias - AWB (Air Will Bill)
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Referencias
		pageEmisionDTE.SeleccionartipoDoc(cp, "809 - AWB (Air Will Bill)");
		pageEmisionDTE.AgregarFolio(cp, "0001");
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
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);

		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Sin costo(Entrega Gratuita)") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("AWB") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_030";
		// Emisión DTE - Individual - Factura Exp - Referencias -  MIC/DATA
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Referencias
		pageEmisionDTE.SeleccionartipoDoc(cp, "810 - MIC/DTA");
		pageEmisionDTE.AgregarFolio(cp, "0001");
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
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);

		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Sin costo(Entrega Gratuita)") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("MIC/DTA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_031";
		// Emisión DTE - Individual - Factura Exp - Referencias -  Carta de Porte
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Referencias
		pageEmisionDTE.SeleccionartipoDoc(cp, "811 - Carta de Porte");
		pageEmisionDTE.AgregarFolio(cp, "0001");
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
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);

		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Sin costo(Entrega Gratuita)") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Carta De Porte") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_032";
		// Emisión DTE - Individual - Factura Exp - Referencias -  Resolucion del SNA donde califica Servicios de Exportacion
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Referencias
		pageEmisionDTE.SeleccionartipoDoc(cp, "812 - Resolución del SNA donde califica Servicios de Exportación");
		pageEmisionDTE.AgregarFolio(cp, "0001");
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
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		//Thread.sleep(10000);
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Sin costo(Entrega Gratuita)") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Resolución Del SNA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_033";
		// Emisión DTE - Individual - Factura Exp - Referencias -  Certificado de Deposito Bolsa Prod.Chile
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Referencias
		pageEmisionDTE.SeleccionartipoDoc(cp, "814 - Certificado de Depósito Bolsa Prod. Chile");
		pageEmisionDTE.AgregarFolio(cp, "0001");
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
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);

		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Sin costo(Entrega Gratuita)") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Certificado De Depósito Bolsa Prod. Chile") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_034";
		// Emisión DTE - Individual - Factura Exp - Referencias -  Vale de Prenda Bolsa Prod. Chile
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Referencias
		pageEmisionDTE.SeleccionartipoDoc(cp, "815 - Vale de Prenda Bolsa Prod. Chile");
		pageEmisionDTE.AgregarFolio(cp, "0001");
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
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);

		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Sin costo(Entrega Gratuita)") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Vale De Prenda Bolsa Prod. Chile") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_036";
		// Emisión DTE - Individual - Factura Exp - Referencias -  Otro Documento - Otro Documento OK
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Referencias
		pageEmisionDTE.SeleccionartipoDoc(cp, "Otro Documento - Otro Documento");
		//pupup Otro Documento
		pageEmisionDTE.IngresarDatosOtroDocumento(cp, "pru", "Documento de Prueba");
		pageEmisionDTE.AgregarFolio(cp, "0001");
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
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);

		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Sin costo(Entrega Gratuita)") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				//driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("pru") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0001") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_037";
		// Emisión DTE - Individual - Factura Exp - Referencias -  Otro Documento - Otro Documento OK
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Referencias
		pageEmisionDTE.SeleccionartipoDoc(cp, "815 - Vale de Prenda Bolsa Prod. Chile");
		pageEmisionDTE.AgregarFolio(cp, "0001");
		pageEmisionDTE.FechaReferencia(cp);
		pageEmisionDTE.ClickCheckboxIndicadorReferenciaGlobal(cp);
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
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		//Thread.sleep(10000);
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Sin costo(Entrega Gratuita)") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText().contains("Vale De Prenda Bolsa Prod. Chile") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]")).getText().contains("0") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_039";
		// Emisión DTE - Individual - Factura Exp - Agregar Transporte con Despacho por cuenta del receptor
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
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
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_040";
		// Emisión DTE - Individual - Factura Exp - Agregar Transporte con Despacho por cuenta del emisor
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
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
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_041";
		// Emisión DTE - Individual - Factura Exp - Agregar Transporte con Despacho por Cuenta del Emisor a Otras Instalaciones
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
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
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
		}
		else {
			crearLogyDocumento.CasoNok(cp);
			System.out.println("FLUJO NOOK");
			resultado = "FLUJO NOOK";
		}
		assertEquals(resultado, "FLUJO OK", "Se verifica resultado del test "+cp);
		
	}
	
	
	@Test
	public void Script_0043() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "FEXP_043";
		// Emisión DTE - Individual - Factura Exp -modalidad: a firme - Clausula: costo, seguro y flete
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
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
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_044";
		// Emisión DTE - Individual - Factura Exp -modalidad: a firme - Clausula: costo y flete
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
		//Aduana
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		
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
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_045";
		// Emisión DTE - Individual - Factura Exp -modalidad: a firme - Clausula: en fabrica
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
		//Aduana
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		
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
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_046";
		// Emisión DTE - Individual - Factura Exp -modalidad: a firme - Clausula: franco al costado del buque
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
		//Aduana
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		
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
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_047";
		// Emisión DTE - Individual - Factura Exp -modalidad: a firme -  Clausula: franco a bordo
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
		//Aduana
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		
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
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_048";
		// Emisión DTE - Individual - Factura Exp -modalidad: a firme -  Clausula: SIN CLAUSULA DE COMPRAVENTA
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
		//Aduana
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		
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
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_049";
		// Emisión DTE - Individual - Factura Exp -modalidad: a firme -  Clausula: OTROS
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
		//Aduana
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		
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
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_050";
		// Emisión DTE - Individual - Factura Exp -modalidad: a firme -  Clausula: ENTREGADAS DERECHOS PAGADOS
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
		//Aduana
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		
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
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_051";
		// Emisión DTE - Individual - Factura Exp -modalidad: a firme -  Clausula: FRANCO TRANSPORTISTA
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
		//Aduana
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		
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
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_052";
		// Emisión DTE - Individual - Factura Exp -modalidad: a firme -  Clausula: TRANSPORTE PAGADO HASTA
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
		//Aduana
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		
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
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_053";
		// Emisión DTE - Individual - Factura Exp -modalidad: a firme -  Clausula: TRANSPORTE Y SEGURO PAGADO HASTA
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
		//Aduana
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		
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
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_054";
		// Emisión DTE - Individual - Factura Exp -modalidad: a firme -  Clausula: ENTREGADAS EN PUERTO DESTINO
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
		//Aduana
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		
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
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_055";
		// Emisión DTE - Individual - Factura Exp -modalidad: a firme -  Clausula: ENTREGADAS EN LUGAR ACORDADO
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
		//Aduana
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		
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
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_056";
		// Emisión DTE - Individual - Factura Exp -modalidad: BAJO CONDICION -  Clausula: COSTO, SEGURO Y FLETE
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
		//Aduana
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		
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
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("BAJO CONDICION") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_057";
		// Emisión DTE - Individual - Factura Exp -modalidad: BAJO CONDICION -  Clausula: COSTO Y FLETE
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
		//Aduana
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		
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
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("BAJO CONDICION") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_058";
		// Emisión DTE - Individual - Factura Exp -modalidad: BAJO CONDICION -  Clausula: EN FÁBRICA
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
		//Aduana
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		
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
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("BAJO CONDICION") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_059";
		// Emisión DTE - Individual - Factura Exp -modalidad: BAJO CONDICION -  Clausula: FRANCO AL COSTADO DEL BUQUE
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
		//Aduana
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		
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
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("BAJO CONDICION") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_060";
		// Emisión DTE - Individual - Factura Exp -modalidad: BAJO CONDICION -  Clausula: FRANCO A BORDO
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
		//Aduana
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		
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
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("BAJO CONDICION") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
		}
		else {
			crearLogyDocumento.CasoNok(cp);
			System.out.println("FLUJO NOOK");
			resultado = "FLUJO NOOK";
		}
		assertEquals(resultado, "FLUJO OK", "Se verifica resultado del test "+cp);
		
	}
	
	
	@Test
	public void Script_0061() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "FEXP_061";
		// Emisión DTE - Individual - Factura Exp -modalidad: BAJO CONDICION -  Clausula: SIN CLAUSULA DE COMPRAVENTA
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
		//Aduana
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		
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
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("BAJO CONDICION") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_062";
		// Emisión DTE - Individual - Factura Exp -modalidad: BAJO CONDICION -  Clausula: OTROS
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
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
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("BAJO CONDICION") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_063";
		// Emisión DTE - Individual - Factura Exp -modalidad: BAJO CONDICION -  Clausula: ENTREGADAS DERECHOS PAGADOS
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
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
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("BAJO CONDICION") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_064";
		// Emisión DTE - Individual - Factura Exp -modalidad: BAJO CONDICION -  Clausula: FRANCO TRANSPORTISTA
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
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
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("BAJO CONDICION") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_065";
		// Emisión DTE - Individual - Factura Exp -modalidad: BAJO CONDICION -  Clausula: TRANSPORTE PAGADO HASTA
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
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
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("BAJO CONDICION") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_066";
		// Emisión DTE - Individual - Factura Exp -modalidad: BAJO CONDICION -  Clausula: TRANSPORTE Y SEGURO PAGADO HASTA
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
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
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("BAJO CONDICION") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_067";
		// Emisión DTE - Individual - Factura Exp -modalidad: BAJO CONDICION -  Clausula: ENTREGADAS EN PUERTO DESTINO
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
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
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("BAJO CONDICION") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_068";
		// Emisión DTE - Individual - Factura Exp -modalidad: BAJO CONDICION -  Clausula: ENTREGADAS EN LUGAR ACORDADO
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
		//Aduana
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		
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
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("BAJO CONDICION") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_069";
		// Emisión DTE - Individual - Factura Exp -modalidad: EN CONSIGNACION LIBRE -  Clausula: COSTO, SEGURO Y FLETE
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
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
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("EN CONSIGNACION LIBRE") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_070";
		// Emisión DTE - Individual - Factura Exp -modalidad: EN CONSIGNACION LIBRE -  Clausula: COSTO Y FLETE
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
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
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("EN CONSIGNACION LIBRE") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_071";
		// Emisión DTE - Individual - Factura Exp -modalidad: EN CONSIGNACION LIBRE -  Clausula: EN FÁBRICA
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
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
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("EN CONSIGNACION LIBRE") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_072";
		// Emisión DTE - Individual - Factura Exp -modalidad: EN CONSIGNACION LIBRE -  Clausula: FRANCO AL COSTADO DEL BUQUE
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
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
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("EN CONSIGNACION LIBRE") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_073";
		// Emisión DTE - Individual - Factura Exp -modalidad: EN CONSIGNACION LIBRE -  Clausula:  FRANCO A BORDO
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
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
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("EN CONSIGNACION LIBRE") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_074";
		// Emisión DTE - Individual - Factura Exp -modalidad: EN CONSIGNACION LIBRE -  Clausula: SIN CLAUSULA DE COMPRAVENTA
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
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
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("EN CONSIGNACION LIBRE") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_075";
		// Emisión DTE - Individual - Factura Exp -modalidad: EN CONSIGNACION LIBRE -  Clausula: OTROS
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
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
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("EN CONSIGNACION LIBRE") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_076";
		// Emisión DTE - Individual - Factura Exp -modalidad: EN CONSIGNACION LIBRE -  Clausula:  ENTREGADAS DERECHOS PAGADOS
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
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
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("EN CONSIGNACION LIBRE") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_077";
		// Emisión DTE - Individual - Factura Exp -modalidad: EN CONSIGNACION LIBRE -  Clausula:  FRANCO TRANSPORTISTA
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
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
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("EN CONSIGNACION LIBRE") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_078";
		// Emisión DTE - Individual - Factura Exp -modalidad: EN CONSIGNACION LIBRE -  Clausula:  TRANSPORTE PAGADO HASTA
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
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
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("EN CONSIGNACION LIBRE") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_079";
		// Emisión DTE - Individual - Factura Exp -modalidad: EN CONSIGNACION LIBRE -  Clausula: TRANSPORTE Y SEGURO PAGADO HASTA
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
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
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("EN CONSIGNACION LIBRE") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_080";
		// Emisión DTE - Individual - Factura Exp -modalidad: EN CONSIGNACION LIBRE -  Clausula: ENTREGADAS EN PUERTO DESTINO
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
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
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("EN CONSIGNACION LIBRE") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_081";
		// Emisión DTE - Individual - Factura Exp -modalidad: EN CONSIGNACION LIBRE -  Clausula:  ENTREGADAS EN LUGAR ACORDADO
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
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
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("EN CONSIGNACION LIBRE") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_082";
		// Emisión DTE - Individual - Factura Exp -modalidad: EN CONSIGNACION CON UN MINIMO A FIRME -  Clausula:  COSTO, SEGURO Y FLETE
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
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
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("MINIMO A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_083";
		// Emisión DTE - Individual - Factura Exp -modalidad: EN CONSIGNACION CON UN MINIMO A FIRME -  Clausula:   COSTO Y FLETE
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
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
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("MINIMO A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_084";
		// Emisión DTE - Individual - Factura Exp -modalidad: EN CONSIGNACION CON UN MINIMO A FIRME -  Clausula:  EN FÁBRICA
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
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
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("MINIMO A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_085";
		// Emisión DTE - Individual - Factura Exp -modalidad: EN CONSIGNACION CON UN MINIMO A FIRME -  Clausula:  FRANCO AL COSTADO DEL BUQUE
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
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
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("MINIMO A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_086";
		// Emisión DTE - Individual - Factura Exp -modalidad: EN CONSIGNACION CON UN MINIMO A FIRME -  Clausula:   FRANCO A BORDO
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
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
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("MINIMO A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_087";
		// Emisión DTE - Individual - Factura Exp -modalidad: EN CONSIGNACION CON UN MINIMO A FIRME -  Clausula: SIN CLAUSULA DE COMPRAVENTA
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
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
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("MINIMO A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_088";
		// Emisión DTE - Individual - Factura Exp -modalidad: EN CONSIGNACION CON UN MINIMO A FIRME -  Clausula:   otros
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
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
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("MINIMO A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_089";
		// Emisión DTE - Individual - Factura Exp -modalidad: EN CONSIGNACION CON UN MINIMO A FIRME -  Clausula:  ENTREGADAS DERECHOS PAGADOS
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
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
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("MINIMO A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_090";
		// Emisión DTE - Individual - Factura Exp -modalidad: EN CONSIGNACION CON UN MINIMO A FIRME -  Clausula: FRANCO TRANSPORTISTA
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
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
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("MINIMO A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_091";
		// Emisión DTE - Individual - Factura Exp -modalidad: EN CONSIGNACION CON UN MINIMO A FIRME -  Clausula: TRANSPORTE PAGADO HASTA
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
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
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("MINIMO A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_092";
		// Emisión DTE - Individual - Factura Exp -modalidad: EN CONSIGNACION CON UN MINIMO A FIRME -  Clausula:  TRANSPORTE Y SEGURO PAGADO HASTA
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
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
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("MINIMO A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_093";
		// Emisión DTE - Individual - Factura Exp -modalidad: EN CONSIGNACION CON UN MINIMO A FIRME -  Clausula: ENTREGADAS EN PUERTO DESTINO
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
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
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("MINIMO A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_094";
		// Emisión DTE - Individual - Factura Exp -modalidad: EN CONSIGNACION CON UN MINIMO A FIRME -  Clausula:   ENTREGADAS EN LUGAR ACORDADO
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
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
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("MINIMO A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_095";
		// Emisión DTE - Individual - Factura Exp -modalidad: SIN PAGO -  Clausula:  COSTO, SEGURO Y FLETE
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
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
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("SIN PAGO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_096";
		// Emisión DTE - Individual - Factura Exp -modalidad: SIN PAGO -  Clausula:  COSTO Y FLETE
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
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
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("SIN PAGO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_097";
		// Emisión DTE - Individual - Factura Exp -modalidad: SIN PAGO -  Clausula: EN FÁBRICA
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
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
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("SIN PAGO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_098";
		// Emisión DTE - Individual - Factura Exp -modalidad: SIN PAGO -  Clausula:   FRANCO AL COSTADO DEL BUQUE
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
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
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("SIN PAGO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_099";
		// Emisión DTE - Individual - Factura Exp -modalidad: SIN PAGO -  Clausula:  FRANCO A BORDO
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
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
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("SIN PAGO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_100";
		// Emisión DTE - Individual - Factura Exp -modalidad: SIN PAGO -  Clausula:  SIN CLAUSULA DE COMPRAVENTA
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
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
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("SIN PAGO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_101";
		// Emisión DTE - Individual - Factura Exp -modalidad: SIN PAGO -  Clausula:  OTROS
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
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
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("SIN PAGO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_102";
		// Emisión DTE - Individual - Factura Exp -modalidad: SIN PAGO -  Clausula:  ENTREGADAS DERECHOS PAGADOS
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
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
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("SIN PAGO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_103";
		// Emisión DTE - Individual - Factura Exp -modalidad: SIN PAGO -  Clausula:  FRANCO TRANSPORTISTA
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
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
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("SIN PAGO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_104";
		// Emisión DTE - Individual - Factura Exp -modalidad: SIN PAGO -  Clausula:  TRANSPORTE PAGADO HASTA
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
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
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("SIN PAGO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_105";
		// Emisión DTE - Individual - Factura Exp -modalidad: SIN PAGO -  Clausula:  TRANSPORTE Y SEGURO PAGADO HASTA
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
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
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("SIN PAGO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_106";
		// Emisión DTE - Individual - Factura Exp -modalidad: SIN PAGO -  Clausula:   ENTREGADAS EN PUERTO DESTINO
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
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
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("SIN PAGO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_107";
		// Emisión DTE - Individual - Factura Exp -modalidad: SIN PAGO -  Clausula:   ENTREGADAS EN LUGAR ACORDADO
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
		//Transporte
		robot.setAutoDelay(5);
		robot.mouseWheel(5);
		pageEmisionDTE.IngresoDatosTransporte2(cp, "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
	
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
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("SIN PAGO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_108";
		// Emisión DTE - Individual - Factura Exp - parámetros adicionales
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
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
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoParametrosAdicionales(cp, "PRUEBA QA", "11.111.111-1");
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);

		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[5]/td[3]")).getText().contains("PRUEBA QA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[6]/td[3]")).getText().contains("11.111.111-1") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_109";
		// Emisión DTE - Individual - Factura Exp - parámetros agrupados adicionales
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
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
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoParametrosAgrupadosAdicionales(cp, "coca_cola", "Efectivo");
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);

		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_110";
		// Emisión DTE - Individual - Factura Exp - con observaciones
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
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
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoObservaciones(cp, "Observación de Prueba");
		
		robot.setAutoDelay(3);
		robot.mouseWheel(3);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[6]/tbody/tr/td/table/tbody/tr[2]/td")).getText().contains("Observación de Prueba") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_111";
		// Emisión DTE - Individual - Factura Exp - sin observaciones
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
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
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[3]")).getText().contains("AARHUS") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("ACAPULCO") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[1]/tbody/tr/td[2]")).getText().contains("PESO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[2]/tbody/tr[3]/td[2]")).getText().contains("1.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[5]/tbody/tr/td[3]/table[3]/tbody/tr/td[2]")).getText().contains("PESO CL") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[1]")).getText().contains("1-POLVO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[2]")).getText().contains("1") && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_112";
		// Emisión DTE - Individual - Factura Exp - Tipo Moneda
		System.out.println(cp);
		String resultado = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
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
		
		pageEmisionDTE.AgregarIndormacionBulto(cp, "1 - POLVO", "1", "Marca QA");
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		pageEmisionDTE.IngresoTipoMoneda(cp, "1 - PESO");
		pageEmisionDTE.IngresoSegundaMoneda(cp, "800", "1500", "170");
		
		pageEmisionDTE.BtnEmitirGuiaDespacho(cp);
		
		robot.setAutoDelay(8);
		robot.mouseWheel(8);
		
		if(
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td/table/tbody/tr[2]/td")).getText().contains("FACTURA DE EXPORTACIÓN ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("JUAN MANUEL GANTES MELENDEZ") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[3]")).getText().contains("Serv. de Transporte Internacional") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[3]")).getText().contains("Contado") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[3]")).getText().contains("A FIRME") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[3]")).getText().contains("Marítima, Fluvial y Lacustre") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[3]/tbody/tr/td/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[3]")).getText().contains("Calle 1") &&
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div[1]/div[1]/div/table[8]/tbody/tr/td[2]/table[2]/tbody/tr[2]/td[3]")).getText().contains("Marca QA")){
			crearLogyDocumento.CasoOk(cp);
			System.out.println("FLUJO OK");
			resultado = "FLUJO OK";
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
		String cp = "FEXP_113";
		// Emisión DTE - Individual - Factura Exp - limpiar
		System.out.println(cp);
		String resultado1 = null;
		String resultado2 = null;
		
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Factura de Exportación Electrónica");
		
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
		
		pageEmisionDTE.BtnAgregarTransporte(cp);
		pageEmisionDTE.BtnAgregarAduana(cp);
		
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
