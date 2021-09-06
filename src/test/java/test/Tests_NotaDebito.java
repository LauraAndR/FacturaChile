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
		public void Script_0001() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
			String cp = "FE_0001";
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
		public void Script_0002() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
			String cp = "FE_0002";
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
		public void Script_0003() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
			String cp = "FE_0003";
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
		public void Script_0004() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
			String cp = "FE_0004";
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
			
			pageEmisionDTE.AgregarProducto2(cp, "000001", "1");

			pageEmisionDTE.AgregarReferencia(cp, "0001");
			pageEmisionDTE.FechaReferencia(cp);
			pageEmisionDTE.BtnAgregarReferencia(cp);
			pageEmisionDTE.BtnEmitirNotaDebito(cp);
			
			if(driver.findElement(By.className("facturaDocumento")).isDisplayed()&& 
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE DÉBITO ELECTRÓNICA")&&
					driver.findElement(By.xpath("/html/body/div[8]/div/section/div[2]/div/div/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12")){
				crearLogyDocumento.CasoOk(cp);
				System.out.println("FLUJO OK");
				resultado = "FLUJO OK";
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
			
			pageEmisionDTE.AgregarProducto2(cp, "000001", "1");
			
			pageEmisionDTE.AgregarReferencia(cp, "0001");
			pageEmisionDTE.FechaReferencia(cp);
			pageEmisionDTE.BtnAgregarReferencia(cp);
			pageEmisionDTE.BtnEmitirNotaDebito(cp);
			
			if(driver.findElement(By.className("facturaDocumento")).isDisplayed()&& 
					driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("NOTA DE DÉBITO ELECTRÓNICA")&&
					driver.findElement(By.xpath("/html/body/div[8]/div/section/div[2]/div/div/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12")){
				crearLogyDocumento.CasoOk(cp);
				System.out.println("FLUJO OK");
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

	