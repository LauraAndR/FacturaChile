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
	
	public class Tests_NotaDebito {
		private WebDriver driver;
		String datapool = Configuration.ROOT_DIR+"DataPool_v2.xlsx";
		LeerExcel leerExcel = new LeerExcel();
		
		@BeforeMethod
		public void setUp() throws FileNotFoundException, IOException {
//			DesiredCapabilities caps = new DesiredCapabilities();
			System.setProperty("webdriver.chrome.driver", "Drivers/chromedriver.exe");
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			driver.navigate().to("https://escritorio-cert.acepta.com/");// Aquí se ingresa la URL para hacer las pruebas.
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		}
		
		
		// Miércoles 01/09/2021
		
		@Test
		public void Script_0002() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
			String cp = "FE_0002";
			// Emision DTE - Individual - Nota de Debito - Contado
			System.out.println(cp);
			String resultado = null;

			PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
			
			CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
			crearLogyDocumento.CrearEvidencias(cp);
			
			pageLoginAdm.ClickIngresarLogin(cp);
			pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
			
			PageEscritorio pageEscritorio = new PageEscritorio(driver);
			pageEscritorio.BarraMenu(cp, "Emisión DTE");
			pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito Electrónica");
			
			PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
			pageEmisionDTE.FechaEmision(cp);
			pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
			pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
			
			Thread.sleep(2000);
			Robot robot = new Robot();
			robot.setAutoDelay(7);
			robot.mouseWheel(7);
			Thread.sleep(2000);
			
			pageEmisionDTE.AgregarProducto2(cp, "000001", "1");
			pageEmisionDTE.AgregarReferencia(cp, "0001");
			pageEmisionDTE.FechaReferencia(cp);
			pageEmisionDTE.BtnAgregarReferencia(cp);
			pageEmisionDTE.BtnEmitirNotaDebito(cp);
			
			if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE DÉBITO ELECTRÓNICA")&&
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado")){
				crearLogyDocumento.CasoOk(cp);
				System.out.println("FLUJO OK");
				resultado = "FLUJO OK";
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
			// Emision DTE - Individual - Nota de Debito - credito
			System.out.println(cp);
			String resultado = null;

			PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
			
			CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
			crearLogyDocumento.CrearEvidencias(cp);
			
			pageLoginAdm.ClickIngresarLogin(cp);
			pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
			
			PageEscritorio pageEscritorio = new PageEscritorio(driver);
			pageEscritorio.BarraMenu(cp, "Emisión DTE");
			pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito Electrónica");
			
			PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
			pageEmisionDTE.FechaEmision(cp);
			pageEmisionDTE.SeleccionarFormaPago(cp, "Crédito");
			pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
			
			Thread.sleep(2000);
			Robot robot = new Robot();
			robot.setAutoDelay(7);
			robot.mouseWheel(7);
			Thread.sleep(2000);
			
			pageEmisionDTE.AgregarProducto2(cp, "000001", "1");
			pageEmisionDTE.AgregarReferencia(cp, "0001");
			pageEmisionDTE.FechaReferencia(cp);
			pageEmisionDTE.BtnAgregarReferencia(cp);
			pageEmisionDTE.BtnEmitirNotaDebito(cp);
			
			if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE DÉBITO ELECTRÓNICA")&&
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Credito")){
				crearLogyDocumento.CasoOk(cp);
				System.out.println("FLUJO OK");
				resultado = "FLUJO OK";
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
			// Emision DTE - Individual - Nota de Debito - sin costo
			System.out.println(cp);
			String resultado = null;

			PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
			
			CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
			crearLogyDocumento.CrearEvidencias(cp);
			
			pageLoginAdm.ClickIngresarLogin(cp);
			pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
			
			PageEscritorio pageEscritorio = new PageEscritorio(driver);
			pageEscritorio.BarraMenu(cp, "Emisión DTE");
			pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito Electrónica");
			
			PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
			pageEmisionDTE.FechaEmision(cp);
			pageEmisionDTE.SeleccionarFormaPago(cp, "Sin Costo");
			pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
			
			Thread.sleep(2000);
			Robot robot = new Robot();
			robot.setAutoDelay(7);
			robot.mouseWheel(7);
			Thread.sleep(2000);
			
			pageEmisionDTE.AgregarProducto2(cp, "000001", "1");
			pageEmisionDTE.AgregarReferencia(cp, "0001");
			pageEmisionDTE.FechaReferencia(cp);
			pageEmisionDTE.BtnAgregarReferencia(cp);
			pageEmisionDTE.BtnEmitirNotaDebito(cp);
			
			if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE DÉBITO ELECTRÓNICA")&&
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Sin Costo")){
				crearLogyDocumento.CasoOk(cp);
				System.out.println("FLUJO OK");
				resultado = "FLUJO OK";
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
			// Emision DTE - Individual - Nota de Debito - producto con impuesto harina retencion 12%
			System.out.println(cp);
			String resultado = null;

			PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
			
			CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
			crearLogyDocumento.CrearEvidencias(cp);
			
			pageLoginAdm.ClickIngresarLogin(cp);
			pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
			
			PageEscritorio pageEscritorio = new PageEscritorio(driver);
			pageEscritorio.BarraMenu(cp, "Emisión DTE");
			pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito Electrónica");
			
			PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
			pageEmisionDTE.FechaEmision(cp);
			pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
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
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE DÉBITO ELECTRÓNICA") &&
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
					
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000")){
				crearLogyDocumento.CasoOk(cp);
				System.out.println("FLUJO OK");
				resultado = "FLUJO OK";
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
			//Emision DTE - Individual - Nota de Debito - producto con impuesto Licores, piscos, whisky, aguardientey vinos licorososo o aromatizados Imp.Adicional 31,5%
			System.out.println(cp);
			String resultado = null;

			PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
			
			CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
			crearLogyDocumento.CrearEvidencias(cp);
			
			pageLoginAdm.ClickIngresarLogin(cp);
			pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
			
			PageEscritorio pageEscritorio = new PageEscritorio(driver);
			pageEscritorio.BarraMenu(cp, "Emisión DTE");
			pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito Electrónica");
			
			PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
			pageEmisionDTE.FechaEmision(cp);
			pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
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
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE DÉBITO ELECTRÓNICA") &&
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
		public void Script_0011() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
			String cp = "FE_0011";
			//Emision DTE - Individual - Nota de Debito - producto con impuesto vino Imp adicional 20,5%
			System.out.println(cp);
			String resultado = null;

			PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
			
			CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
			crearLogyDocumento.CrearEvidencias(cp);
			
			pageLoginAdm.ClickIngresarLogin(cp);
			pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
			
			PageEscritorio pageEscritorio = new PageEscritorio(driver);
			pageEscritorio.BarraMenu(cp, "Emisión DTE");
			pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito Electrónica");
			
			PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
			pageEmisionDTE.FechaEmision(cp);
			pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
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
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE DÉBITO ELECTRÓNICA") &&
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
		public void Script_0012() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
			String cp = "FE_0012";
			//Emision DTE - Individual - Nota de Debito - producto con impuesto Bovino retencion 5%
			System.out.println(cp);
			String resultado = null;

			PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
			
			CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
			crearLogyDocumento.CrearEvidencias(cp);
			
			pageLoginAdm.ClickIngresarLogin(cp);
			pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
			
			PageEscritorio pageEscritorio = new PageEscritorio(driver);
			pageEscritorio.BarraMenu(cp, "Emisión DTE");
			pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito Electrónica");
			
			PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
			pageEmisionDTE.FechaEmision(cp);
			pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
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
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE DÉBITO ELECTRÓNICA") &&
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
		public void Script_0013() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
			String cp = "FE_0013";
			//Emision DTE - Individual - Nota de Debito - producto con impuesto Cerveza y Bebidas alcoholicas; Imp Adicional 20,5%
			System.out.println(cp);
			String resultado = null;

			PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
			
			CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
			crearLogyDocumento.CrearEvidencias(cp);
			
			pageLoginAdm.ClickIngresarLogin(cp);
			pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
			
			PageEscritorio pageEscritorio = new PageEscritorio(driver);
			pageEscritorio.BarraMenu(cp, "Emisión DTE");
			pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito Electrónica");
			
			PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
			pageEmisionDTE.FechaEmision(cp);
			pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
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
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE DÉBITO ELECTRÓNICA") &&
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
		public void Script_0014() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
			String cp = "FE_0014";
			//Emision DTE - Individual - Nota de Debito - producto con impuesto bebidas analcoholicas y minerales; imp Adicional 10%
			System.out.println(cp);
			String resultado = null;

			PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
			
			CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
			crearLogyDocumento.CrearEvidencias(cp);
			
			pageLoginAdm.ClickIngresarLogin(cp);
			pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
			
			PageEscritorio pageEscritorio = new PageEscritorio(driver);
			pageEscritorio.BarraMenu(cp, "Emisión DTE");
			pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito Electrónica");
			
			PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
			pageEmisionDTE.FechaEmision(cp);
			pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
			pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
			
			Thread.sleep(2000);
			Robot robot = new Robot();
			robot.setAutoDelay(7);
			robot.mouseWheel(7);
			Thread.sleep(2000);
			
			pageEmisionDTE.AgregarProducto1(cp, "000006", "1");
			
			
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
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE DÉBITO ELECTRÓNICA") &&
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
		public void Script_0015() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
			String cp = "FE_0015";
			//Emision DTE - Individual - Nota de Debito - producto con impuesto Bebidas analcoholicas y minerales con alto contenido de azucar; Imp Adicional 18%
			System.out.println(cp);
			String resultado = null;

			PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
			
			CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
			crearLogyDocumento.CrearEvidencias(cp);
			
			pageLoginAdm.ClickIngresarLogin(cp);
			pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
			
			PageEscritorio pageEscritorio = new PageEscritorio(driver);
			pageEscritorio.BarraMenu(cp, "Emisión DTE");
			pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito Electrónica");
			
			PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
			pageEmisionDTE.FechaEmision(cp);
			pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
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
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE DÉBITO ELECTRÓNICA") &&
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
		public void Script_0016() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
			String cp = "FE_0016";
			//Emision DTE - Individual - Nota de Debito - producto con impuesto especifico a la gasolina 93; Imp. Adicional de 4,5 a 6 UTM por m3
			System.out.println(cp);
			String resultado = null;

			PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
			
			CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
			crearLogyDocumento.CrearEvidencias(cp);
			
			pageLoginAdm.ClickIngresarLogin(cp);
			pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
			
			PageEscritorio pageEscritorio = new PageEscritorio(driver);
			pageEscritorio.BarraMenu(cp, "Emisión DTE");
			pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito Electrónica");
			
			PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
			pageEmisionDTE.FechaEmision(cp);
			pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
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
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE DÉBITO ELECTRÓNICA") &&
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
		public void Script_0017() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
			String cp = "FE_0017";
			//Emision DTE - Individual - Nota de Debito - producto con impuesto especifico a la gasolina 97; Imp. Adicional de 4,5 a 6 UTM por m3
			System.out.println(cp);
			String resultado = null;

			PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
			
			CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
			crearLogyDocumento.CrearEvidencias(cp);
			
			pageLoginAdm.ClickIngresarLogin(cp);
			pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
			
			PageEscritorio pageEscritorio = new PageEscritorio(driver);
			pageEscritorio.BarraMenu(cp, "Emisión DTE");
			pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito Electrónica");
			
			PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
			pageEmisionDTE.FechaEmision(cp);
			pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
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
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE DÉBITO ELECTRÓNICA") &&
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
		public void Script_0018() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
			String cp = "FE_0018";
			//Emision DTE - Individual - Nota de Debito - producto con impuesto compra diesel; Imp.Adicional 1,5 UTM por m3
			System.out.println(cp);
			String resultado = null;

			PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
			
			CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
			crearLogyDocumento.CrearEvidencias(cp);
			
			pageLoginAdm.ClickIngresarLogin(cp);
			pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
			
			PageEscritorio pageEscritorio = new PageEscritorio(driver);
			pageEscritorio.BarraMenu(cp, "Emisión DTE");
			pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito Electrónica");
			
			PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
			pageEmisionDTE.FechaEmision(cp);
			pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
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
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE DÉBITO ELECTRÓNICA") &&
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
		public void Script_0019() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
			String cp = "FE_0019";
			//Emision DTE - Individual - Nota de Debito - producto con impuesto Gas Natural comprimido; Imp adicional 1,93 por KM3
			System.out.println(cp);
			String resultado = null;

			PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
			
			CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
			crearLogyDocumento.CrearEvidencias(cp);
			
			pageLoginAdm.ClickIngresarLogin(cp);
			pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
			
			PageEscritorio pageEscritorio = new PageEscritorio(driver);
			pageEscritorio.BarraMenu(cp, "Emisión DTE");
			pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito Electrónica");
			
			PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
			pageEmisionDTE.FechaEmision(cp);
			pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
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
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE DÉBITO ELECTRÓNICA") &&
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
		public void Script_0020() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
			String cp = "FE_0020";
			//Emision DTE - Individual - Nota de Debito - producto con impuesto Gas licuado de petroleo; Imp adicional 1,4 por M3
			System.out.println(cp);
			String resultado = null;

			PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
			
			CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
			crearLogyDocumento.CrearEvidencias(cp);
			
			pageLoginAdm.ClickIngresarLogin(cp);
			pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
			
			PageEscritorio pageEscritorio = new PageEscritorio(driver);
			pageEscritorio.BarraMenu(cp, "Emisión DTE");
			pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito Electrónica");
			
			PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
			pageEmisionDTE.FechaEmision(cp);
			pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
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
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE DÉBITO ELECTRÓNICA") &&
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
		public void Script_0021() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
			String cp = "FE_0016";
			//Emision DTE - Individual - Nota de Debito - producto con impuesto Faenamiento Bovino retencion 5%
			System.out.println(cp);
			String resultado = null;

			PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
			
			CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
			crearLogyDocumento.CrearEvidencias(cp);
			
			pageLoginAdm.ClickIngresarLogin(cp);
			pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
			
			PageEscritorio pageEscritorio = new PageEscritorio(driver);
			pageEscritorio.BarraMenu(cp, "Emisión DTE");
			pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito Electrónica");
			
			PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
			pageEmisionDTE.FechaEmision(cp);
			pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
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
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE DÉBITO ELECTRÓNICA") &&
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
		public void Script_0022() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
			String cp = "FE_0022";
			//Emision DTE - Individual - Nota de Debito - producto con impuesto Retencion 5%
			System.out.println(cp);
			String resultado = null;

			PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
			
			CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
			crearLogyDocumento.CrearEvidencias(cp);
			
			pageLoginAdm.ClickIngresarLogin(cp);
			pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
			
			PageEscritorio pageEscritorio = new PageEscritorio(driver);
			pageEscritorio.BarraMenu(cp, "Emisión DTE");
			pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito Electrónica");
			
			PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
			pageEmisionDTE.FechaEmision(cp);
			pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
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
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE DÉBITO ELECTRÓNICA") &&
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
		public void Script_0023() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
			String cp = "FE_0023";
			//Emision DTE - Individual - Nota de Debito - producto con impuesto IVA de margen de comercializacion
			System.out.println(cp);
			String resultado = null;

			PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
			
			CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
			crearLogyDocumento.CrearEvidencias(cp);
			
			pageLoginAdm.ClickIngresarLogin(cp);
			pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
			
			PageEscritorio pageEscritorio = new PageEscritorio(driver);
			pageEscritorio.BarraMenu(cp, "Emisión DTE");
			pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito Electrónica");
			
			PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
			pageEmisionDTE.FechaEmision(cp);
			pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
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
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE DÉBITO ELECTRÓNICA") &&
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
					
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000016") &&
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("IVA Margen comercialización") &&
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
		public void Script_0024() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
			String cp = "FE_0024";
			//Emision DTE - Individual - Nota de Debito - producto con retencion 19%
			System.out.println(cp);
			String resultado = null;

			PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
			
			CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
			crearLogyDocumento.CrearEvidencias(cp);
			
			pageLoginAdm.ClickIngresarLogin(cp);
			pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
			
			PageEscritorio pageEscritorio = new PageEscritorio(driver);
			pageEscritorio.BarraMenu(cp, "Emisión DTE");
			pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito Electrónica");
			
			PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
			pageEmisionDTE.FechaEmision(cp);
			pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
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
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE DÉBITO ELECTRÓNICA") &&
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
		public void Script_0025() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
			String cp = "FE_0025";
			//Emision DTE - Individual - Nota de Debito - producto con Frijoles retencion 13% o 19%
			System.out.println(cp);
			String resultado = null;

			PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
			
			CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
			crearLogyDocumento.CrearEvidencias(cp);
			
			pageLoginAdm.ClickIngresarLogin(cp);
			pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
			
			PageEscritorio pageEscritorio = new PageEscritorio(driver);
			pageEscritorio.BarraMenu(cp, "Emisión DTE");
			pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito Electrónica");
			
			PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
			pageEmisionDTE.FechaEmision(cp);
			pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
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
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE DÉBITO ELECTRÓNICA") &&
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
		public void Script_0026() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
			String cp = "FE_0026";
			//Emision DTE - Individual - Nota de Debito - producto con lenteja retencion 13% o 19%
			System.out.println(cp);
			String resultado = null;

			PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
			
			CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
			crearLogyDocumento.CrearEvidencias(cp);
			
			pageLoginAdm.ClickIngresarLogin(cp);
			pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
			
			PageEscritorio pageEscritorio = new PageEscritorio(driver);
			pageEscritorio.BarraMenu(cp, "Emisión DTE");
			pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito Electrónica");
			
			PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
			pageEmisionDTE.FechaEmision(cp);
			pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
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
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE DÉBITO ELECTRÓNICA") &&
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
					
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("30") &&
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Lenteja retención 13% o 19%") &&
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
		public void Script_0027() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
			String cp = "FE_0027";
			//Emision DTE - Individual - Nota de Debito - producto con garbanzo retencion 13% o 19%
			System.out.println(cp);
			String resultado = null;

			PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
			
			CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
			crearLogyDocumento.CrearEvidencias(cp);
			
			pageLoginAdm.ClickIngresarLogin(cp);
			pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
			
			PageEscritorio pageEscritorio = new PageEscritorio(driver);
			pageEscritorio.BarraMenu(cp, "Emisión DTE");
			pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito Electrónica");
			
			PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
			pageEmisionDTE.FechaEmision(cp);
			pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
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
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE DÉBITO ELECTRÓNICA") &&
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
		public void Script_0028() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
			String cp = "FE_0028";
			//Emision DTE - Individual - Nota de Debito - producto con iva exento
			System.out.println(cp);
			String resultado = null;

			PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
			
			CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
			crearLogyDocumento.CrearEvidencias(cp);
			
			pageLoginAdm.ClickIngresarLogin(cp);
			pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
			
			PageEscritorio pageEscritorio = new PageEscritorio(driver);
			pageEscritorio.BarraMenu(cp, "Emisión DTE");
			pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito Electrónica");
			
			PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
			pageEmisionDTE.FechaEmision(cp);
			pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
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
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE DÉBITO ELECTRÓNICA") &&
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
		public void Script_0029() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
			String cp = "FE_0029";
			//Emision DTE - Individual - Nota de Debito - producto con descuento en $
			System.out.println(cp);
			String resultado = null;

			PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
			
			CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
			crearLogyDocumento.CrearEvidencias(cp);
			
			pageLoginAdm.ClickIngresarLogin(cp);
			pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
			
			PageEscritorio pageEscritorio = new PageEscritorio(driver);
			pageEscritorio.BarraMenu(cp, "Emisión DTE");
			pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito Electrónica");
			
			PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
			pageEmisionDTE.FechaEmision(cp);
			pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
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
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE DÉBITO ELECTRÓNICA") &&
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
		public void Script_0030() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
			String cp = "FE_0030";
			//Emision DTE - Individual - Nota de Debito - producto con descuento en %
			System.out.println(cp);
			String resultado = null;

			PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
			
			CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
			crearLogyDocumento.CrearEvidencias(cp);
			
			pageLoginAdm.ClickIngresarLogin(cp);
			pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
			
			PageEscritorio pageEscritorio = new PageEscritorio(driver);
			pageEscritorio.BarraMenu(cp, "Emisión DTE");
			pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito Electrónica");
			
			PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
			pageEmisionDTE.FechaEmision(cp);
			pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
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
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE DÉBITO ELECTRÓNICA") &&
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
		public void Script_0031() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
			String cp = "FE_0031";
			//Emision DTE - Individual - Nota de Debito - producto con codigo item
			System.out.println(cp);
			String resultado = null;

			PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
			
			CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
			crearLogyDocumento.CrearEvidencias(cp);
			
			pageLoginAdm.ClickIngresarLogin(cp);
			pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
			
			PageEscritorio pageEscritorio = new PageEscritorio(driver);
			pageEscritorio.BarraMenu(cp, "Emisión DTE");
			pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito Electrónica");
			
			PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
			pageEmisionDTE.FechaEmision(cp);
			pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
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
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE DÉBITO ELECTRÓNICA") &&
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
		public void Script_0032() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
			String cp = "FE_0032";
			//Emision DTE - Individual - Nota de Debito - producto - agregar
			System.out.println(cp);
			String resultado = null;

			PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
			
			CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
			crearLogyDocumento.CrearEvidencias(cp);
			
			pageLoginAdm.ClickIngresarLogin(cp);
			pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
			
			PageEscritorio pageEscritorio = new PageEscritorio(driver);
			pageEscritorio.BarraMenu(cp, "Emisión DTE");
			pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito Electrónica");
			
			PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
			pageEmisionDTE.FechaEmision(cp);
			pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
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
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE DÉBITO ELECTRÓNICA") &&
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
		public void Script_0034() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
			String cp = "FE_0034";
			//Emision DTE - Individual - Nota de Debito - referencia - factura
			System.out.println(cp);
			String resultado = null;

			PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
			
			CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
			crearLogyDocumento.CrearEvidencias(cp);
			
			pageLoginAdm.ClickIngresarLogin(cp);
			pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
			
			PageEscritorio pageEscritorio = new PageEscritorio(driver);
			pageEscritorio.BarraMenu(cp, "Emisión DTE");
			pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito Electrónica");
			
			PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
			pageEmisionDTE.FechaEmision(cp);
			pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
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
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE DÉBITO ELECTRÓNICA") &&
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
		public void Script_0035() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
			String cp = "FE_0035";
			//Emision DTE - Individual - Nota de Debito - referencia - factura no afecta
			System.out.println(cp);
			String resultado = null;

			PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
			
			CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
			crearLogyDocumento.CrearEvidencias(cp);
			
			pageLoginAdm.ClickIngresarLogin(cp);
			pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
			
			PageEscritorio pageEscritorio = new PageEscritorio(driver);
			pageEscritorio.BarraMenu(cp, "Emisión DTE");
			pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito Electrónica");
			
			PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
			pageEmisionDTE.FechaEmision(cp);
			pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
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
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE DÉBITO ELECTRÓNICA") &&
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
		public void Script_0036() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
			String cp = "FE_0036";
			//Emision DTE - Individual - Nota de Debito - referencia - Factura Afecta electronica
			System.out.println(cp);
			String resultado = null;

			PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
			
			CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
			crearLogyDocumento.CrearEvidencias(cp);
			
			pageLoginAdm.ClickIngresarLogin(cp);
			pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
			
			PageEscritorio pageEscritorio = new PageEscritorio(driver);
			pageEscritorio.BarraMenu(cp, "Emisión DTE");
			pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito Electrónica");
			
			PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
			pageEmisionDTE.FechaEmision(cp);
			pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
			pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
			
			Thread.sleep(2000);
			Robot robot = new Robot();
			robot.setAutoDelay(7);
			robot.mouseWheel(7);
			Thread.sleep(2000);
			
			pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
			
			pageEmisionDTE.SeleccionartipoDoc(cp, "33 - Factura Afecta Electrónica");
			pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
			pageEmisionDTE.AgregarFolio(cp, "0001");
			pageEmisionDTE.FechaReferencia(cp);
			pageEmisionDTE.BtnAgregarReferencia(cp);
			
			robot.setAutoDelay(7);
			robot.mouseWheel(7);
			Thread.sleep(2000);
			
			
			pageEmisionDTE.BtnEmitirNotaCredito(cp);
			
			if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE DÉBITO ELECTRÓNICA") &&
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
					
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[1]")).getText().contains("Factura Electrónica") &&
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
			String cp = "FE_0037";
			//Emision DTE - Individual - Nota de Debito - referencia - Nota de Debito electronica
			System.out.println(cp);
			String resultado = null;

			PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
			
			CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
			crearLogyDocumento.CrearEvidencias(cp);
			
			pageLoginAdm.ClickIngresarLogin(cp);
			pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
			
			PageEscritorio pageEscritorio = new PageEscritorio(driver);
			pageEscritorio.BarraMenu(cp, "Emisión DTE");
			pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito Electrónica");
			
			PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
			pageEmisionDTE.FechaEmision(cp);
			pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
			pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
			
			Thread.sleep(2000);
			Robot robot = new Robot();
			robot.setAutoDelay(7);
			robot.mouseWheel(7);
			Thread.sleep(2000);
			
			pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
			
			pageEmisionDTE.SeleccionartipoDoc(cp, "56 - Nota de Débito Electrónica");
			pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
			pageEmisionDTE.AgregarFolio(cp, "0001");
			pageEmisionDTE.FechaReferencia(cp);
			pageEmisionDTE.BtnAgregarReferencia(cp);
			
			robot.setAutoDelay(7);
			robot.mouseWheel(7);
			Thread.sleep(2000);
			
			
			pageEmisionDTE.BtnEmitirNotaCredito(cp);
			
			if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE DÉBITO ELECTRÓNICA") &&
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
					
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[1]")).getText().contains("Nota de Débito") &&
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
			String cp = "FE_0038";
			//Emision DTE - Individual - Nota de Debito - referencia - liquidacion de factura
			System.out.println(cp);
			String resultado = null;

			PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
			
			CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
			crearLogyDocumento.CrearEvidencias(cp);
			
			pageLoginAdm.ClickIngresarLogin(cp);
			pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
			
			PageEscritorio pageEscritorio = new PageEscritorio(driver);
			pageEscritorio.BarraMenu(cp, "Emisión DTE");
			pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito Electrónica");
			
			PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
			pageEmisionDTE.FechaEmision(cp);
			pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
			pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
			
			Thread.sleep(2000);
			Robot robot = new Robot();
			robot.setAutoDelay(7);
			robot.mouseWheel(7);
			Thread.sleep(2000);
			
			pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
			
			pageEmisionDTE.SeleccionartipoDoc(cp, "40 - Liquidación Factura");
			pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
			pageEmisionDTE.AgregarFolio(cp, "0001");
			pageEmisionDTE.FechaReferencia(cp);
			pageEmisionDTE.BtnAgregarReferencia(cp);
			
			robot.setAutoDelay(7);
			robot.mouseWheel(7);
			Thread.sleep(2000);
			
			
			pageEmisionDTE.BtnEmitirNotaCredito(cp);
			
			if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE DÉBITO ELECTRÓNICA") &&
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
					
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[1]")).getText().contains("Liquidación Factura") &&
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
			String cp = "FE_0039";
			//Emision DTE - Individual - Nota de Debito - referencia - liquidacion de factura electronica
			System.out.println(cp);
			String resultado = null;

			PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
			
			CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
			crearLogyDocumento.CrearEvidencias(cp);
			
			pageLoginAdm.ClickIngresarLogin(cp);
			pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
			
			PageEscritorio pageEscritorio = new PageEscritorio(driver);
			pageEscritorio.BarraMenu(cp, "Emisión DTE");
			pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito Electrónica");
			
			PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
			pageEmisionDTE.FechaEmision(cp);
			pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
			pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
			
			Thread.sleep(2000);
			Robot robot = new Robot();
			robot.setAutoDelay(7);
			robot.mouseWheel(7);
			Thread.sleep(2000);
			
			pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
			
			pageEmisionDTE.SeleccionartipoDoc(cp, "43 - Liquidación Factura Electrónica");
			pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
			pageEmisionDTE.AgregarFolio(cp, "0001");
			pageEmisionDTE.FechaReferencia(cp);
			pageEmisionDTE.BtnAgregarReferencia(cp);
			
			robot.setAutoDelay(7);
			robot.mouseWheel(7);
			Thread.sleep(2000);
			
			
			pageEmisionDTE.BtnEmitirNotaCredito(cp);
			
			if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE DÉBITO ELECTRÓNICA") &&
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
					
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[1]")).getText().contains("Liquidación Factura Electrónica") &&
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
			String cp = "FE_0040";
			//Emision DTE - Individual - Nota de Debito - referencia - factura de compra
			System.out.println(cp);
			String resultado = null;

			PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
			
			CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
			crearLogyDocumento.CrearEvidencias(cp);
			
			pageLoginAdm.ClickIngresarLogin(cp);
			pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
			
			PageEscritorio pageEscritorio = new PageEscritorio(driver);
			pageEscritorio.BarraMenu(cp, "Emisión DTE");
			pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito Electrónica");
			
			PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
			pageEmisionDTE.FechaEmision(cp);
			pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
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
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE DÉBITO ELECTRÓNICA") &&
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
		public void Script_0041() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
			String cp = "FE_0041";
			//Emision DTE - Individual - Nota de Debito - referencia - factura de compra electronica
			System.out.println(cp);
			String resultado = null;

			PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
			
			CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
			crearLogyDocumento.CrearEvidencias(cp);
			
			pageLoginAdm.ClickIngresarLogin(cp);
			pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
			
			PageEscritorio pageEscritorio = new PageEscritorio(driver);
			pageEscritorio.BarraMenu(cp, "Emisión DTE");
			pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito Electrónica");
			
			PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
			pageEmisionDTE.FechaEmision(cp);
			pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
			pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
			
			Thread.sleep(2000);
			Robot robot = new Robot();
			robot.setAutoDelay(7);
			robot.mouseWheel(7);
			Thread.sleep(2000);
			
			pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
			
			pageEmisionDTE.SeleccionartipoDoc(cp, "46 - Factura de Compra Electrónica");
			pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
			pageEmisionDTE.AgregarFolio(cp, "0001");
			pageEmisionDTE.FechaReferencia(cp);
			pageEmisionDTE.BtnAgregarReferencia(cp);
			
			robot.setAutoDelay(7);
			robot.mouseWheel(7);
			Thread.sleep(2000);
			
			
			pageEmisionDTE.BtnEmitirNotaCredito(cp);
			
			if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE DÉBITO ELECTRÓNICA") &&
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
					
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[1]")).getText().contains("Factura de Compra Electrónica") &&
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
			String cp = "FE_0042";
			//Emision DTE - Individual - Nota de Debito - referencia - nota de credito
			System.out.println(cp);
			String resultado = null;

			PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
			
			CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
			crearLogyDocumento.CrearEvidencias(cp);
			
			pageLoginAdm.ClickIngresarLogin(cp);
			pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
			
			PageEscritorio pageEscritorio = new PageEscritorio(driver);
			pageEscritorio.BarraMenu(cp, "Emisión DTE");
			pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito Electrónica");
			
			PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
			pageEmisionDTE.FechaEmision(cp);
			pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
			pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
			
			Thread.sleep(2000);
			Robot robot = new Robot();
			robot.setAutoDelay(7);
			robot.mouseWheel(7);
			Thread.sleep(2000);
			
			pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
			
			pageEmisionDTE.SeleccionartipoDoc(cp, "60 - Nota de Crédito");
			pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
			pageEmisionDTE.AgregarFolio(cp, "0001");
			pageEmisionDTE.FechaReferencia(cp);
			pageEmisionDTE.BtnAgregarReferencia(cp);
			
			robot.setAutoDelay(7);
			robot.mouseWheel(7);
			Thread.sleep(2000);
			
			
			pageEmisionDTE.BtnEmitirNotaCredito(cp);
			
			if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE DÉBITO ELECTRÓNICA") &&
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
					
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[1]")).getText().contains("Nota de Crédito") &&
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
		public void Script_0043() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
			String cp = "FE_0043";
			//Emision DTE - Individual - Nota de Debito - referencia - nota de credito electronica
			System.out.println(cp);
			String resultado = null;

			PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
			
			CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
			crearLogyDocumento.CrearEvidencias(cp);
			
			pageLoginAdm.ClickIngresarLogin(cp);
			pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
			
			PageEscritorio pageEscritorio = new PageEscritorio(driver);
			pageEscritorio.BarraMenu(cp, "Emisión DTE");
			pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito Electrónica");
			
			PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
			pageEmisionDTE.FechaEmision(cp);
			pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
			pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
			
			Thread.sleep(2000);
			Robot robot = new Robot();
			robot.setAutoDelay(7);
			robot.mouseWheel(7);
			Thread.sleep(2000);
			
			pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
			
			pageEmisionDTE.SeleccionartipoDoc(cp, "61 - Nota de Crédito Electrónica");
			pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
			pageEmisionDTE.AgregarFolio(cp, "0001");
			pageEmisionDTE.FechaReferencia(cp);
			pageEmisionDTE.BtnAgregarReferencia(cp);
			
			robot.setAutoDelay(7);
			robot.mouseWheel(7);
			Thread.sleep(2000);
			
			
			pageEmisionDTE.BtnEmitirNotaCredito(cp);
			
			if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE DÉBITO ELECTRÓNICA") &&
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
					
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[1]")).getText().contains("Nota de Crédito Electrónica") &&
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
			String cp = "FE_0044";
			//Emision DTE - Individual - Nota de Debito - referencia - liquidacion
			System.out.println(cp);
			String resultado = null;

			PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
			
			CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
			crearLogyDocumento.CrearEvidencias(cp);
			
			pageLoginAdm.ClickIngresarLogin(cp);
			pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
			
			PageEscritorio pageEscritorio = new PageEscritorio(driver);
			pageEscritorio.BarraMenu(cp, "Emisión DTE");
			pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito Electrónica");
			
			PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
			pageEmisionDTE.FechaEmision(cp);
			pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
			pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
			
			Thread.sleep(2000);
			Robot robot = new Robot();
			robot.setAutoDelay(7);
			robot.mouseWheel(7);
			Thread.sleep(2000);
			
			pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
			
			pageEmisionDTE.SeleccionartipoDoc(cp, "103 - Liquidación");
			pageEmisionDTE.SeleccionarCodReferencia(cp, "1 - Anula Documento");
			pageEmisionDTE.AgregarFolio(cp, "0001");
			pageEmisionDTE.FechaReferencia(cp);
			pageEmisionDTE.BtnAgregarReferencia(cp);
			
			robot.setAutoDelay(7);
			robot.mouseWheel(7);
			Thread.sleep(2000);
			
			
			pageEmisionDTE.BtnEmitirNotaCredito(cp);
			
			if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE DÉBITO ELECTRÓNICA") &&
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
					
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td[1]")).getText().contains("Liquidación") &&
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
			//Emision DTE - Individual - Nota de Debito - referencia - con indicador de referencia global
			System.out.println(cp);
			String resultado = null;
			
			PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
			
			CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
			crearLogyDocumento.CrearEvidencias(cp);
			
			pageLoginAdm.ClickIngresarLogin(cp);
			pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
			
			PageEscritorio pageEscritorio = new PageEscritorio(driver);
			pageEscritorio.BarraMenu(cp, "Emisión DTE");
			pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito Electrónica");
			
			PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
			pageEmisionDTE.FechaEmision(cp);
			pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
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
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE DÉBITO ELECTRÓNICA") &&
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
		public void Script_0056() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
			String cp = "FA_0056";
			// Emision DTE - Individual - Nota de Debito - Sin Transporte
			System.out.println(cp);
			String resultado = null;
			
			PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
			
			CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
			crearLogyDocumento.CrearEvidencias(cp);
			
			pageLoginAdm.ClickIngresarLogin(cp);
			pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
			
			PageEscritorio pageEscritorio = new PageEscritorio(driver);
			pageEscritorio.BarraMenu(cp, "Emisión DTE");
			pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito Electrónica");
			
			PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
			pageEmisionDTE.FechaEmision(cp);
			pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
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
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE DÉBITO ELECTRÓNICA") &&
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
		public void Script_0057() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
			String cp = "FA_0057";
			//Emision DTE - Individual - Nota de Debito - Agregar Transporte con Despacho por cuenta del Receptor
			System.out.println(cp);
			String resultado = null;
			
			PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
			
			CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
			crearLogyDocumento.CrearEvidencias(cp);
			
			pageLoginAdm.ClickIngresarLogin(cp);
			pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
			
			PageEscritorio pageEscritorio = new PageEscritorio(driver);
			pageEscritorio.BarraMenu(cp, "Emisión DTE");
			pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito Electrónica");
			
			PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
			pageEmisionDTE.FechaEmision(cp);
			pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
			pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
			
			Thread.sleep(2000);
			Robot robot = new Robot();
			robot.setAutoDelay(7);
			robot.mouseWheel(7);
			Thread.sleep(2000);
		
			pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
			pageEmisionDTE.BtnAgregarTransporte(cp);
			pageEmisionDTE.IngresoDatosTransporte(cp, "ABCD12", "11.111.111-1", "Juan Pérez Pérez", "Calle 1", "Santiago", "Metropolitana", "1 - Despacho por Cuenta del Receptor");
			
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
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE DÉBITO ELECTRÓNICA") &&
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
		public void Script_0058() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
			String cp = "FA_0058";
			//Emision DTE - Individual - Nota de Debito - Agregar Transporte con Despacho por cuenta del Emisor
			System.out.println(cp);
			String resultado = null;
			
			PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
			
			CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
			crearLogyDocumento.CrearEvidencias(cp);
			
			pageLoginAdm.ClickIngresarLogin(cp);
			pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
			
			PageEscritorio pageEscritorio = new PageEscritorio(driver);
			pageEscritorio.BarraMenu(cp, "Emisión DTE");
			pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito Electrónica");
			
			PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
			pageEmisionDTE.FechaEmision(cp);
			pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
			pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
			
			Thread.sleep(2000);
			Robot robot = new Robot();
			robot.setAutoDelay(7);
			robot.mouseWheel(7);
			Thread.sleep(2000);
		
			pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
			pageEmisionDTE.BtnAgregarTransporte(cp);
			pageEmisionDTE.IngresoDatosTransporte(cp, "ABCD12", "11.111.111-1", "Juan Pérez Pérez", "Calle 1", "Santiago", "Metropolitana", "2 - Despacho por Cuenta del Emisor");
			
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
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE DÉBITO ELECTRÓNICA") &&
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
		public void Script_0059() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
			String cp = "FA_0059";
			//Emision DTE - Individual - Nota de Debito - Agregar Transporte con Despacho por cuenta del Emisor a Otras Instalaciones
			System.out.println(cp);
			String resultado = null;
			
			PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
			
			CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
			crearLogyDocumento.CrearEvidencias(cp);
			
			pageLoginAdm.ClickIngresarLogin(cp);
			pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
			
			PageEscritorio pageEscritorio = new PageEscritorio(driver);
			pageEscritorio.BarraMenu(cp, "Emisión DTE");
			pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito Electrónica");
			
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
			pageEmisionDTE.IngresoDatosTransporte(cp, "ABCD12", "11.111.111-1", "Juan Pérez Pérez", "Calle 1", "Santiago", "Metropolitana", "3 - Despacho por Cuenta del Emisor a Otras Instalaciones");
			
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
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE DÉBITO ELECTRÓNICA") &&
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
		public void Script_0060() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
			String cp = "FA_0060";
			//Emision DTE - Individual - Nota de Debito - parametros adicionales
			System.out.println(cp);
			String resultado = null;
			
			PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
			
			CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
			crearLogyDocumento.CrearEvidencias(cp);
			
			pageLoginAdm.ClickIngresarLogin(cp);
			pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
			
			PageEscritorio pageEscritorio = new PageEscritorio(driver);
			pageEscritorio.BarraMenu(cp, "Emisión DTE");
			pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito Electrónica");
			
			PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
			pageEmisionDTE.FechaEmision(cp);
			pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
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
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE DÉBITO ELECTRÓNICA") &&		
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
		public void Script_0061() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
			String cp = "FA_0061";
			//Emision DTE - Individual - Nota de Debito - parametros agrupados adicionales
			System.out.println(cp);
			String resultado = null;
			
			PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
			
			CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
			crearLogyDocumento.CrearEvidencias(cp);
			
			pageLoginAdm.ClickIngresarLogin(cp);
			pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
			
			PageEscritorio pageEscritorio = new PageEscritorio(driver);
			pageEscritorio.BarraMenu(cp, "Emisión DTE");
			pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito Electrónica");
			
			PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
			pageEmisionDTE.FechaEmision(cp);
			pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
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
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE DÉBITO ELECTRÓNICA") &&		
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
		public void Script_0062() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
			String cp = "FA_0062";
			//Emision DTE - Individual - Nota de Debito - con observaciones
			System.out.println(cp);
			String resultado = null;
			
			PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
			
			CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
			crearLogyDocumento.CrearEvidencias(cp);
			
			pageLoginAdm.ClickIngresarLogin(cp);
			pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
			
			PageEscritorio pageEscritorio = new PageEscritorio(driver);
			pageEscritorio.BarraMenu(cp, "Emisión DTE");
			pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito Electrónica");
			
			PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
			pageEmisionDTE.FechaEmision(cp);
			pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
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
			
			pageEmisionDTE.IngresoObservaciones(cp, "Observación de Prueba");
			
			robot.setAutoDelay(7);
			robot.mouseWheel(7);
			Thread.sleep(2000);
			
			
			pageEmisionDTE.BtnEmitirNotaCredito(cp);
			
			if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE DÉBITO ELECTRÓNICA") &&		
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

					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[1]/table/tbody/tr[2]/td")).getText().contains("Observación de Prueba")){
				crearLogyDocumento.CasoOk(cp);
				System.out.println("FLUJO OK");
				resultado = "FLUJO OK";
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
			String cp = "FA_0063";
			//Emision DTE - Individual - Nota de Debito - sin observaciones
			System.out.println(cp);
			String resultado = null;
			
			PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
			
			CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
			crearLogyDocumento.CrearEvidencias(cp);
			
			pageLoginAdm.ClickIngresarLogin(cp);
			pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
			
			PageEscritorio pageEscritorio = new PageEscritorio(driver);
			pageEscritorio.BarraMenu(cp, "Emisión DTE");
			pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito Electrónica");
			
			PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
			pageEmisionDTE.FechaEmision(cp);
			pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
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
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE DÉBITO ELECTRÓNICA") &&		
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
		public void Script_0065() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
			String cp = "FA_0065";
			//Emision DTE - Individual - Nota de Debito - monto exento - descuento/recargo - Descuento - %
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
			pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito Electrónica");
			
			PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
			pageEmisionDTE.FechaEmision(cp);
			pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
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
			
			//Primera validación
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
			
			//Segunda validación
			if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE DÉBITO ELECTRÓNICA") &&		
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
		public void Script_0066() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
			String cp = "FA_0066";
			//Emision DTE - Individual - Nota de Debito - monto exento - descuento/recargo - Descuento - pesos
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
			pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito Electrónica");
			
			PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
			pageEmisionDTE.FechaEmision(cp);
			pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
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
			pageEmisionDTE.IngresoDescuentoPeso(cp, "1", "Descuento QA");
			
			robot.setAutoDelay(7);
			robot.mouseWheel(7);
			Thread.sleep(2000);
			
//			//Primera validación
//			if(driver.findElement(By.xpath("//*[@id=\"formEmitirdocumento_tablaTotales\"]/tbody/tr[3]/th")).getText().contains("Sub-Total Exento") &&
//					driver.findElement(By.id("formEmitirdocumento_sub-tota-exento")).getAttribute("value").contains("2000") &&
//					driver.findElement(By.xpath("//*[@id=\"formEmitirdocumento_tablaTotales\"]/tbody/tr[4]/th")).getText().contains("Descuento Exento Descuento QA") &&
//					driver.findElement(By.id("formEmitirdocumento_DescuentoExento")).getAttribute("value").contains("1") &&
//					driver.findElement(By.xpath("//*[@id=\"formEmitirdocumento_tablaTotales\"]/tbody/tr[4]/td[2]/a")).isDisplayed()){
//				resultado1 = "FLUJO OK";
//			}
//			else {
//				resultado1 = "FLUJO NOOK";
//			}
					
			pageEmisionDTE.BtnEmitirNotaCredito(cp);
			
			//Segunda validación
			if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE DÉBITO ELECTRÓNICA") &&		
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
					
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1") &&
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("1") &&
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("1") &&
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("0") &&
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[5]/td[3]")).getText().contains("2") &&
					
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

//			assertEquals(resultado1, "FLUJO OK", "Se verifica resultado del test "+cp);
			assertEquals(resultado2, "FLUJO OK", "Se verifica resultado del test "+cp);
		}
		
		@Test
		public void Script_0067() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
			String cp = "FA_0067";
			// Emision DTE - Individual - Nota de Debito - monto exento - descuento/recargo - Recargo - %
			System.out.println(cp);
//			String resultado1 = null;
			String resultado2 = null;
			
			PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
			
			CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
			crearLogyDocumento.CrearEvidencias(cp);
			
			pageLoginAdm.ClickIngresarLogin(cp);
			pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
			
			PageEscritorio pageEscritorio = new PageEscritorio(driver);
			pageEscritorio.BarraMenu(cp, "Emisión DTE");
			pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito Electrónica");
			
			PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
			pageEmisionDTE.FechaEmision(cp);
			pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
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
			
//			//Primera validación
//			if(driver.findElement(By.xpath("//*[@id=\"formEmitirdocumento_tablaTotales\"]/tbody/tr[3]/th")).getText().contains("Sub-Total Exento") &&
//					driver.findElement(By.id("formEmitirdocumento_sub-tota-exento")).getAttribute("value").contains("2.000") &&
//					driver.findElement(By.xpath("//*[@id=\"formEmitirdocumento_tablaTotales\"]/tbody/tr[4]/th")).getText().contains("Recargo Exento Recargo QA (20%)") &&
//					driver.findElement(By.id("formEmitirdocumento_RecargoExento")).getAttribute("value").contains("400") &&
//					driver.findElement(By.xpath("//*[@id=\"formEmitirdocumento_tablaTotales\"]/tbody/tr[4]/td[2]/a")).isDisplayed()){
//				resultado1 = "FLUJO OK";
//			}
//			else {
//				resultado1 = "FLUJO NOOK";
//			}
					
			pageEmisionDTE.BtnEmitirNotaCredito(cp);
			
			//Segunda validación
			if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE DÉBITO ELECTRÓNICA") &&		
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
					
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("0") && //400
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("1") && //1.000
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("2") && //2.400
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("0") && //190
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[5]/td[3]")).getText().contains("3") && //3.590
					
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

//			assertEquals(resultado1, "FLUJO OK", "Se verifica resultado del test "+cp);
			assertEquals(resultado2, "FLUJO OK", "Se verifica resultado del test "+cp);
		}
		
		@Test
		public void Script_0068() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
			String cp = "FA_0068";
			//Emision DTE - Individual - Nota de Debito - monto exento - descuento/recargo - Recargo - pesos
			System.out.println(cp);
//			String resultado1 = null;
			String resultado2 = null;
			
			PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
			
			CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
			crearLogyDocumento.CrearEvidencias(cp);
			
			pageLoginAdm.ClickIngresarLogin(cp);
			pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
			
			PageEscritorio pageEscritorio = new PageEscritorio(driver);
			pageEscritorio.BarraMenu(cp, "Emisión DTE");
			pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito Electrónica");
			
			PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
			pageEmisionDTE.FechaEmision(cp);
			pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
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
			pageEmisionDTE.IngresoRecargoPeso(cp, "5", "Recargo QA");
			
			robot.setAutoDelay(7);
			robot.mouseWheel(7);
			Thread.sleep(2000);
			
//			//Primera validación
//			if(driver.findElement(By.xpath("//*[@id=\"formEmitirdocumento_tablaTotales\"]/tbody/tr[3]/th")).getText().contains("Sub-Total Exento") &&
//					driver.findElement(By.id("formEmitirdocumento_sub-tota-exento")).getAttribute("value").contains("2.000") &&
//					driver.findElement(By.xpath("//*[@id=\"formEmitirdocumento_tablaTotales\"]/tbody/tr[4]/th")).getText().contains("Recargo Exento Recargo QA") &&
//					driver.findElement(By.id("formEmitirdocumento_RecargoExento")).getAttribute("value").contains("2") &&
//					driver.findElement(By.xpath("//*[@id=\"formEmitirdocumento_tablaTotales\"]/tbody/tr[4]/td[2]/a")).isDisplayed()){
//				resultado1 = "FLUJO OK";
//			}
//			else {
//				resultado1 = "FLUJO NOOK";
//			}
					
			pageEmisionDTE.BtnEmitirNotaCredito(cp);
			
			//Segunda validación
			if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE DÉBITO ELECTRÓNICA") &&		
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
					
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("2") &&
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("1") &&
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("4") &&
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("0") &&
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[5]/td[3]")).getText().contains("5") &&
					
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

//			assertEquals(resultado1, "FLUJO OK", "Se verifica resultado del test "+cp);
			assertEquals(resultado2, "FLUJO OK", "Se verifica resultado del test "+cp);
		}
		
		
		@Test
		public void Script_0069() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
			String cp = "FA_0069";
			//Emision DTE - Individual - Nota de Debito - monto exento - descuento/recargo - Eliminar
			System.out.println(cp);
//			String resultado1 = null;
			String resultado2 = null;
			
			PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
			
			CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
			crearLogyDocumento.CrearEvidencias(cp);
			
			pageLoginAdm.ClickIngresarLogin(cp);
			pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
			
			PageEscritorio pageEscritorio = new PageEscritorio(driver);
			pageEscritorio.BarraMenu(cp, "Emisión DTE");
			pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito Electrónica");
			
			PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
			pageEmisionDTE.FechaEmision(cp);
			pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
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
			pageEmisionDTE.IngresoRecargoPeso(cp, "2", "Recargo QA");
			pageEmisionDTE.ClickEliminar(cp);
			
			robot.setAutoDelay(7);
			robot.mouseWheel(7);
			Thread.sleep(2000);
			
//			//Primera validación
//			if(driver.findElement(By.xpath("//*[@id=\"columnaDescuentosExento\"]/div/label")).getText().contains("Descuento/Recargo") &&
//					driver.findElement(By.xpath("//*[@id=\"columnaDescuentosExento\"]/div/div/div/label[2]")).getText().contains("No")){
//				resultado1 = "FLUJO OK";
//			}
//			else {
//				resultado1 = "FLUJO NOOK";
//			}
					
			pageEmisionDTE.BtnEmitirNotaCredito(cp);
			
			//Segunda validación
			if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE DÉBITO ELECTRÓNICA") &&		
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
					
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1") &&
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("2") &&
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("0") &&
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("3") &&
					
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

//			assertEquals(resultado1, "FLUJO OK", "Se verifica resultado del test "+cp);
			assertEquals(resultado2, "FLUJO OK", "Se verifica resultado del test "+cp);
		}
		
		
		@Test
		public void Script_0071() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
			String cp = "FA_0071";
			//Emision DTE - Individual - Nota de Debito - monto Neto - descuento/recargo - Descuento - %
			System.out.println(cp);
//			String resultado1 = null;
			String resultado2 = null;
			
			PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
			
			CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
			crearLogyDocumento.CrearEvidencias(cp);
			
			pageLoginAdm.ClickIngresarLogin(cp);
			pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
			
			PageEscritorio pageEscritorio = new PageEscritorio(driver);
			pageEscritorio.BarraMenu(cp, "Emisión DTE");
			pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito Electrónica");
			
			PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
			pageEmisionDTE.FechaEmision(cp);
			pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
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
			pageEmisionDTE.IngresoDescuentoPrcNeto(cp, "2", "Descuento QA");
			
			robot.setAutoDelay(7);
			robot.mouseWheel(7);
			Thread.sleep(2000);
			
//			//Primera validación
//			if(driver.findElement(By.xpath("//*[@id=\"formEmitirdocumento_tablaTotales\"]/tbody/tr[3]/th")).getText().contains("Sub-Total Neto") &&
//					driver.findElement(By.id("formEmitirdocumento_sub-tota-neto")).getAttribute("value").contains("1.000") &&
//					driver.findElement(By.xpath("//*[@id=\"formEmitirdocumento_tablaTotales\"]/tbody/tr[4]/th")).getText().contains("Descuento Neto Descuento QA (20%)") &&
//					driver.findElement(By.id("formEmitirdocumento_DescuentoNeto")).getAttribute("value").contains("2") &&
//					driver.findElement(By.xpath("//*[@id=\"formEmitirdocumento_tablaTotales\"]/tbody/tr[4]/td[2]/a")).isDisplayed()){
//				resultado1 = "FLUJO OK";
//			}
//			else {
//				resultado1 = "FLUJO NOOK";
//			}
					
			pageEmisionDTE.BtnEmitirNotaCredito(cp);
			
			//Segunda validación
			if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE DÉBITO ELECTRÓNICA") &&		
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
					
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
					
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("0") &&
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("1") &&
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("0") &&
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("0") &&
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[5]/td[3]")).getText().contains("1") &&
					
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

//			assertEquals(resultado1, "FLUJO OK", "Se verifica resultado del test "+cp);
			assertEquals(resultado2, "FLUJO OK", "Se verifica resultado del test "+cp);
		}
		
		@Test
		public void Script_0072() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
			String cp = "FA_0072";
			//Emision DTE - Individual - Nota de Debito - monto Neto - descuento/recargo - Descuento - pesos
			System.out.println(cp);
//			String resultado1 = null;
			String resultado2 = null;
			
			PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
			
			CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
			crearLogyDocumento.CrearEvidencias(cp);
			
			pageLoginAdm.ClickIngresarLogin(cp);
			pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
			
			PageEscritorio pageEscritorio = new PageEscritorio(driver);
			pageEscritorio.BarraMenu(cp, "Emisión DTE");
			pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito Electrónica");
			
			PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
			pageEmisionDTE.FechaEmision(cp);
			pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
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
			pageEmisionDTE.IngresoDescuentoPesoNeto(cp, "1", "Descuento QA");
			
			robot.setAutoDelay(7);
			robot.mouseWheel(7);
			Thread.sleep(2000);
			
//			//Primera validación
//			if(driver.findElement(By.xpath("//*[@id=\"formEmitirdocumento_tablaTotales\"]/tbody/tr[3]/th")).getText().contains("Sub-Total Neto") &&
//					driver.findElement(By.id("formEmitirdocumento_sub-tota-neto")).getAttribute("value").contains("1.000") &&
//					driver.findElement(By.xpath("//*[@id=\"formEmitirdocumento_tablaTotales\"]/tbody/tr[4]/th")).getText().contains("Descuento Neto Descuento QA") &&
//					driver.findElement(By.id("formEmitirdocumento_DescuentoNeto")).getAttribute("value").contains("1") &&
//					driver.findElement(By.xpath("//*[@id=\"formEmitirdocumento_tablaTotales\"]/tbody/tr[4]/td[2]/a")).isDisplayed()){
//				resultado1 = "FLUJO OK";
//			}
//			else {
//				resultado1 = "FLUJO NOOK";
//			}
					
			pageEmisionDTE.BtnEmitirNotaCredito(cp);
			
			//Segunda validación
			if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE DÉBITO ELECTRÓNICA") &&		
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
					
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
					
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1") &&
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("0") &&
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("0") &&
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[5]/td[3]")).getText().contains("0") &&
					
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

//			assertEquals(resultado1, "FLUJO OK", "Se verifica resultado del test "+cp);
			assertEquals(resultado2, "FLUJO OK", "Se verifica resultado del test "+cp);
		}
		
		
		@Test
		public void Script_0073() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
			String cp = "FA_0073";
			//Emision DTE - Individual - Nota de Debito - monto Neto - descuento/recargo - Recargo - %
			System.out.println(cp);
//			String resultado1 = null;
			String resultado2 = null;
			
			PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
			
			CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
			crearLogyDocumento.CrearEvidencias(cp);
			
			pageLoginAdm.ClickIngresarLogin(cp);
			pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
			
			PageEscritorio pageEscritorio = new PageEscritorio(driver);
			pageEscritorio.BarraMenu(cp, "Emisión DTE");
			pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito Electrónica");
			
			PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
			pageEmisionDTE.FechaEmision(cp);
			pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
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
			
//			//Primera validación
//			if(driver.findElement(By.xpath("//*[@id=\"formEmitirdocumento_tablaTotales\"]/tbody/tr[3]/th")).getText().contains("Sub-Total Neto") &&
//					driver.findElement(By.id("formEmitirdocumento_sub-tota-neto")).getAttribute("value").contains("1.000") &&
//					driver.findElement(By.xpath("//*[@id=\"formEmitirdocumento_tablaTotales\"]/tbody/tr[4]/th")).getText().contains("Recargo Neto Recargo QA (10%)") &&
//					driver.findElement(By.id("formEmitirdocumento_RecargoNeto")).getAttribute("value").contains("100") &&
//					driver.findElement(By.xpath("//*[@id=\"formEmitirdocumento_tablaTotales\"]/tbody/tr[4]/td[2]/a")).isDisplayed()){
//				resultado1 = "FLUJO OK";
//			}
//			else {
//				resultado1 = "FLUJO NOOK";
//			}
					
			pageEmisionDTE.BtnEmitirNotaCredito(cp);
			
			//Segunda validación
			if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE DÉBITO ELECTRÓNICA") &&		
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
					
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
					
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("0") &&
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("1") &&
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("0") &&
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("0") &&
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[5]/td[3]")).getText().contains("1") &&
					
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

//			assertEquals(resultado1, "FLUJO OK", "Se verifica resultado del test "+cp);
			assertEquals(resultado2, "FLUJO OK", "Se verifica resultado del test "+cp);
		}
		
		
		@Test
		public void Script_0074() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
			String cp = "FA_0074";
			//Emision DTE - Individual - Nota de Debito - monto Neto - descuento/recargo - Recargo - pesos
			System.out.println(cp);
//			String resultado1 = null;
			String resultado2 = null;
			
			PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
			
			CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
			crearLogyDocumento.CrearEvidencias(cp);
			
			pageLoginAdm.ClickIngresarLogin(cp);
			pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
			
			PageEscritorio pageEscritorio = new PageEscritorio(driver);
			pageEscritorio.BarraMenu(cp, "Emisión DTE");
			pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito Electrónica");
			
			PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
			pageEmisionDTE.FechaEmision(cp);
			pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
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
			pageEmisionDTE.IngresoRecargoPesoNeto(cp, "1", "Recargo QA");
			
			robot.setAutoDelay(7);
			robot.mouseWheel(7);
			Thread.sleep(2000);
			
//			//Primera validación
//			if(driver.findElement(By.xpath("//*[@id=\"formEmitirdocumento_tablaTotales\"]/tbody/tr[3]/th")).getText().contains("Sub-Total Neto") &&
//					driver.findElement(By.id("formEmitirdocumento_sub-tota-neto")).getAttribute("value").contains("1.000") &&
//					driver.findElement(By.xpath("//*[@id=\"formEmitirdocumento_tablaTotales\"]/tbody/tr[4]/th")).getText().contains("Recargo Neto Recargo QA") &&
//					driver.findElement(By.id("formEmitirdocumento_RecargoNeto")).getAttribute("value").contains("1") &&
//					driver.findElement(By.xpath("//*[@id=\"formEmitirdocumento_tablaTotales\"]/tbody/tr[4]/td[2]/a")).isDisplayed()){
//				resultado1 = "FLUJO OK";
//			}
//			else {
//				resultado1 = "FLUJO NOOK";
//			}
					
			pageEmisionDTE.BtnEmitirNotaCredito(cp);
			
			//Segunda validación
			if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE DÉBITO ELECTRÓNICA") &&		
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
					
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
					
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1") &&
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("2") &&
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("0") &&
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("0") &&
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[5]/td[3]")).getText().contains("2") &&
					
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

//			assertEquals(resultado1, "FLUJO OK", "Se verifica resultado del test "+cp);
			assertEquals(resultado2, "FLUJO OK", "Se verifica resultado del test "+cp);
		}
		
		
		@Test
		public void Script_0075() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
			String cp = "FA_0075";
			// Emision DTE - Individual - Nota de Debito - monto Neto - descuento/recargo - Eliminar
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
			pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito Electrónica");
			
			PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
			pageEmisionDTE.FechaEmision(cp);
			pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
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
			pageEmisionDTE.IngresoRecargoPesoNeto(cp, "1", "Recargo QA");
			pageEmisionDTE.ClickEliminar(cp);
			
			robot.setAutoDelay(7);
			robot.mouseWheel(7);
			Thread.sleep(2000);
			
			//Primera validación
			if(driver.findElement(By.xpath("//*[@id=\"columnaDescuentosNeto\"]/div/label")).getText().contains("Descuento/Recargo") &&
					driver.findElement(By.xpath("//*[@id=\"columnaDescuentosNeto\"]/div/div/div/label[2]")).getText().contains("No")){
				resultado1 = "FLUJO OK";
			}
			else {
				resultado1 = "FLUJO NOOK";
			}
					
			pageEmisionDTE.BtnEmitirNotaCredito(cp);
			
			//Segunda validación
			if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE DÉBITO ELECTRÓNICA") &&		
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]")).getText().contains("Contado") &&
					
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
					
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1") &&
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("0") &&
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("0") &&
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("1") &&
					
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
		public void Script_0076() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
			String cp = "FA_0076";
			// Emision DTE - Individual - Nota de Debito - Limpiar
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
			pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Débito Electrónica");
			
			PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
			pageEmisionDTE.FechaEmision(cp);
			pageEmisionDTE.SeleccionarFormaPago(cp, "Contado");
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

	