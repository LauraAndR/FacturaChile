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
	

public class Tests_BoletaAfecta {

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
	public void Script_0004() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "BA_0004";
		// Emisión DTE - Individual - Boleta Afecta - Boleta de servicios periódicos
		System.out.println(cp);
		String resultado1 = null;
				
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Boleta Afecta Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		driver.findElement(By.id("formEmitirdocumento_rutReceptor")).clear();
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.SeleccionarTipoServicioDatosBoleta(cp, "1 Boletas de servicios periódicos");
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
		pageEmisionDTE.BtnEmitirFacturaAfecta(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("BOLETA ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("840") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("160") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("1.000")){
			
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
		String cp = "BA_0005";
		// Emisión DTE - Individual - Boleta Afecta - Boleta de servicios periódicos domiciliarios
		System.out.println(cp);
		String resultado1 = null;
				
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Boleta Afecta Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		driver.findElement(By.id("formEmitirdocumento_rutReceptor")).clear();
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.SeleccionarTipoServicioDatosBoleta(cp, "2 Boletas de servicios periódicos domiciliarios");
		pageEmisionDTE.FechaVencimiento(cp);
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
		pageEmisionDTE.BtnEmitirFacturaAfecta(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("BOLETA ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("840") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("160") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("1.000")){
			
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
		String cp = "BA_0006";
		// Emisión DTE - Individual - Boleta Afecta - Boleta de ventas y servicios
		System.out.println(cp);
		String resultado1 = null;
				
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Boleta Afecta Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		driver.findElement(By.id("formEmitirdocumento_rutReceptor")).clear();
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.SeleccionarTipoServicioDatosBoleta(cp, "3 Boletas de venta y servicios");
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
		pageEmisionDTE.BtnEmitirFacturaAfecta(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("BOLETA ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("840") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("160") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("1.000")){
			
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
		String cp = "BA_0007";
		// Emisión DTE - Individual - Boleta Afecta - Boleta de Espectáculo emitidas por cuenta de Terceros
		System.out.println(cp);
		String resultado1 = null;
				
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Boleta Afecta Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		driver.findElement(By.id("formEmitirdocumento_rutReceptor")).clear();
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.SeleccionarTipoServicioDatosBoleta(cp, "4 Boleta de Espectáculo emitida por cuenta de Terceros");
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
		pageEmisionDTE.BtnEmitirFacturaAfecta(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("BOLETA ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("840") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("160") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("1.000")){
			
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
		String cp = "BA_0010";
		// Emisión DTE - Individual - Boleta Afecta - producto con impuesto harina retención 12%
		System.out.println(cp);
		String resultado1 = null;
				
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Boleta Afecta Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		driver.findElement(By.id("formEmitirdocumento_rutReceptor")).clear();
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.SeleccionarTipoServicioDatosBoleta(cp, "1 Boletas de servicios periódicos");
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
		pageEmisionDTE.BtnEmitirFacturaAfecta(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("BOLETA ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("840") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("160") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("1.000")){
			
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
	public void Script_0011() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "BA_0011";
		// Emisión DTE - Individual - Boleta Afecta - producto con impuesto Licores, piscos, whisky, aguardiente y vinos licorosos p aromatizados imp. Adicional 31,5%
		System.out.println(cp);
		String resultado1 = null;
				
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Boleta Afecta Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		driver.findElement(By.id("formEmitirdocumento_rutReceptor")).clear();
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.SeleccionarTipoServicioDatosBoleta(cp, "1 Boletas de servicios periódicos");
		pageEmisionDTE.AgregarProducto1(cp, "000002", "1");
		pageEmisionDTE.BtnEmitirFacturaAfecta(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("BOLETA ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000002") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Licores 31.5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("uni") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("2000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("2000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.681") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("319") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("2.000")){
			
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
	public void Script_0012() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "BA_0012";
		// Emisión DTE - Individual - Boleta Afecta - producto con impuesto Vino imp adicional 20,5%
		System.out.println(cp);
		String resultado1 = null;
				
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Boleta Afecta Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		driver.findElement(By.id("formEmitirdocumento_rutReceptor")).clear();
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.SeleccionarTipoServicioDatosBoleta(cp, "1 Boletas de servicios periódicos");
		pageEmisionDTE.AgregarProducto1(cp, "000003", "1");
		pageEmisionDTE.BtnEmitirFacturaAfecta(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("BOLETA ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000003") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("vino adicional 20.5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("uni") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("3000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("3000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("2.004") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("381") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("615")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("3.000")){
			
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
	public void Script_0013() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "BA_0013";
		// Emisión DTE - Individual - Boleta Afecta - producto con impuesto Bovino retención 5%
		System.out.println(cp);
		String resultado1 = null;
				
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Boleta Afecta Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		driver.findElement(By.id("formEmitirdocumento_rutReceptor")).clear();
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.SeleccionarTipoServicioDatosBoleta(cp, "1 Boletas de servicios periódicos");
		pageEmisionDTE.AgregarProducto1(cp, "000004", "1");
		pageEmisionDTE.BtnEmitirFacturaAfecta(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("BOLETA ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000004") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("bobino retencion 5%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("KG") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("4000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("4000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("3.193") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("607") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("200")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("4.000")){
			
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
	public void Script_0014() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "BA_0014";
		// Emisión DTE - Individual - Boleta Afecta - producto con impuesto Cerveza y bebidas alcohólicas; Imp Adicional 20,5%
		System.out.println(cp);
		String resultado1 = null;
				
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Boleta Afecta Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		driver.findElement(By.id("formEmitirdocumento_rutReceptor")).clear();
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.SeleccionarTipoServicioDatosBoleta(cp, "1 Boletas de servicios periódicos");
		pageEmisionDTE.AgregarProducto1(cp, "000005", "1");
		pageEmisionDTE.BtnEmitirFacturaAfecta(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("BOLETA ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000005") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("bebidas analcoholicas 20.5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("uni") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("756") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("144") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("100")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("1.000")){
			
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
		String cp = "BA_0015";
		// Emisión DTE - Individual - Boleta Afecta - producto con impuesto Bebidas analcohólicas y minerales; Imp Adicional 10%
		System.out.println(cp);
		String resultado1 = null;
				
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Boleta Afecta Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		driver.findElement(By.id("formEmitirdocumento_rutReceptor")).clear();
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.SeleccionarTipoServicioDatosBoleta(cp, "1 Boletas de servicios periódicos");
		pageEmisionDTE.AgregarProducto1(cp, "000006", "1");
		pageEmisionDTE.BtnEmitirFacturaAfecta(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("BOLETA ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000006") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Cerveza 20.5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("uni") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("2000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("2000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("1.336") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("254") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("410")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("2.000")){
			
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
		String cp = "BA_0016";
		// Emisión DTE - Individual - Boleta Afecta - producto con impuesto bebidas analcohólicas y minerales con alto contenido de azúcar; imp Adicional 18%
		System.out.println(cp);
		String resultado1 = null;
				
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Boleta Afecta Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		driver.findElement(By.id("formEmitirdocumento_rutReceptor")).clear();
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.SeleccionarTipoServicioDatosBoleta(cp, "1 Boletas de servicios periódicos");
		pageEmisionDTE.AgregarProducto1(cp, "000007", "1");
		pageEmisionDTE.BtnEmitirFacturaAfecta(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("BOLETA ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000007") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("bebidas azucaradas 18%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("uni") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("5000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("5000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("3.445") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("655") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("900")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("5.000")){
			
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
	public void Script_0017() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "BA_0017";
		// Emisión DTE - Individual - Boleta Afecta - producto con impuesto especifica a la gasolina 93; imp. Adicional de 4,5 a 6 UTM por m3
		System.out.println(cp);
		String resultado1 = null;
				
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Boleta Afecta Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		driver.findElement(By.id("formEmitirdocumento_rutReceptor")).clear();
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.SeleccionarTipoServicioDatosBoleta(cp, "1 Boletas de servicios periódicos");
		pageEmisionDTE.AgregarProducto1(cp, "000008", "1");
		pageEmisionDTE.BtnEmitirFacturaAfecta(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("BOLETA ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000008") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Gasolina 93") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("M3") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("800") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("800") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("428") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("81") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("291")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("800")){
			
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
	public void Script_0018() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "BA_0018";
		// Emisión DTE - Individual - Boleta Afecta - producto con impuesto especifica a la gasolina 97; imp. Adicional de 4,5 a 6 UTM por m3
		System.out.println(cp);
		String resultado1 = null;
				
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Boleta Afecta Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		driver.findElement(By.id("formEmitirdocumento_rutReceptor")).clear();
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.SeleccionarTipoServicioDatosBoleta(cp, "1 Boletas de servicios periódicos");
		pageEmisionDTE.AgregarProducto1(cp, "000009", "1");
		pageEmisionDTE.BtnEmitirFacturaAfecta(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("BOLETA ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000009") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Gasolina 97") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("M3") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("900") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("900") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("507") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("96") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("297")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("900")){
			
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
	public void Script_0019() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "BA_0019";
		// Emisión DTE - Individual - Boleta Afecta - producto con impuesto compra de Diesel; imp Adicional 1,5 UTM por m3
		System.out.println(cp);
		String resultado1 = null;
				
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Boleta Afecta Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		driver.findElement(By.id("formEmitirdocumento_rutReceptor")).clear();
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.SeleccionarTipoServicioDatosBoleta(cp, "1 Boletas de servicios periódicos");
		pageEmisionDTE.AgregarProducto1(cp, "000010", "1");
		pageEmisionDTE.BtnEmitirFacturaAfecta(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("BOLETA ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000010") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Diesel") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("M3") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("500") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("500") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("361") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("69") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("70")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("500")){
			
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
	public void Script_0020() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "BA_0020";
		// Emisión DTE - Individual - Boleta Afecta - producto con impuesto Gas Natural comprimido; imp adicional 1,93 por KM3
		System.out.println(cp);
		String resultado1 = null;
				
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Boleta Afecta Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		driver.findElement(By.id("formEmitirdocumento_rutReceptor")).clear();
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.SeleccionarTipoServicioDatosBoleta(cp, "1 Boletas de servicios periódicos");
		pageEmisionDTE.AgregarProducto1(cp, "000011", "1");
		pageEmisionDTE.BtnEmitirFacturaAfecta(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("BOLETA ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000011") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("gas natural") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("M3") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1200") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1200") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("939") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("179") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("82")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("1200")){
			
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
	public void Script_0021() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "BA_0021";
		// Emisión DTE - Individual - Boleta Afecta - producto con impuesto Gas licuado de petróleo; imp adicional 1,4 por M3
		System.out.println(cp);
		String resultado1 = null;
				
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Boleta Afecta Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		driver.findElement(By.id("formEmitirdocumento_rutReceptor")).clear();
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.SeleccionarTipoServicioDatosBoleta(cp, "1 Boletas de servicios periódicos");
		pageEmisionDTE.AgregarProducto1(cp, "000012", "1");
		pageEmisionDTE.BtnEmitirFacturaAfecta(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("BOLETA ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000012") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Gas licuado") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("M3") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1330") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1330") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("-44.176") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("-8.393") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("53.899")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("1330")){
			
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
	public void Script_0022() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "BA_0022";
		// Emisión DTE - Individual - Boleta Afecta - producto con impuesto Faenamiento Bovino retención 5%
		System.out.println(cp);
		String resultado1 = null;
				
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Boleta Afecta Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		driver.findElement(By.id("formEmitirdocumento_rutReceptor")).clear();
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.SeleccionarTipoServicioDatosBoleta(cp, "1 Boletas de servicios periódicos");
		pageEmisionDTE.AgregarProductoFaenamientoBovino(cp, "000013", "20000", "25000", "1");
		pageEmisionDTE.BtnEmitirFacturaAfecta(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("BOLETA ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000013") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("faenamiento bovino") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("KG") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("798") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("152") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("50")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("1.000")){
			
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
	public void Script_0023() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "BA_0023";
		// Emisión DTE - Individual - Boleta Afecta - producto con impuesto Retención 5%
		System.out.println(cp);
		String resultado1 = null;
				
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Boleta Afecta Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		driver.findElement(By.id("formEmitirdocumento_rutReceptor")).clear();
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.SeleccionarTipoServicioDatosBoleta(cp, "1 Boletas de servicios periódicos");
		pageEmisionDTE.AgregarProductoFaenamientoBovino(cp, "000014", "20000", "25000", "1");
		pageEmisionDTE.BtnEmitirFacturaAfecta(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("BOLETA ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000014") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("retencion") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("KG") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("10000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("10000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("7.983") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("1.517") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("500")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("10.000")){
			
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
	public void Script_0024() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "BA_0024";
		// Emisión DTE - Individual - Boleta Afecta - producto con impuesto IVA de margen de comercialización
		System.out.println(cp);
		String resultado1 = null;
				
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Boleta Afecta Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		driver.findElement(By.id("formEmitirdocumento_rutReceptor")).clear();
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.SeleccionarTipoServicioDatosBoleta(cp, "1 Boletas de servicios periódicos");
		pageEmisionDTE.AgregarProductoIVAMargenCom(cp, "000016", "KG", "1", "10", "12000");
		pageEmisionDTE.BtnEmitirFacturaAfecta(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("BOLETA ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000016") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("IVA Margen comercialización") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("KG") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("10000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("10000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("756") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("144") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("100")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("1.000")){
			
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
	public void Script_0025() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "BA_0025";
		// Emisión DTE - Individual - Boleta Afecta - producto con IVA exento
		System.out.println(cp);
		String resultado1 = null;
				
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Boleta Afecta Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		driver.findElement(By.id("formEmitirdocumento_rutReceptor")).clear();
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.SeleccionarTipoServicioDatosBoleta(cp, "1 Boletas de servicios periódicos");
		pageEmisionDTE.AgregarProductoConIvaExento(cp, "000001", "1");
		pageEmisionDTE.BtnEmitirFacturaAfecta(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("BOLETA ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				
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
	public void Script_0026() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "BA_0026";
		// Emisión DTE - Individual - Boleta Afecta - producto con descuento en %
		System.out.println(cp);
		String resultado1 = null;
				
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Boleta Afecta Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		driver.findElement(By.id("formEmitirdocumento_rutReceptor")).clear();
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.SeleccionarTipoServicioDatosBoleta(cp, "1 Boletas de servicios periódicos");
		pageEmisionDTE.AgregarProductoDescuentoPrc(cp, "000001", "1", "5");	
		pageEmisionDTE.BtnEmitirFacturaAfecta(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("BOLETA ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("50") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[7]")).getText().contains("950") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("840") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("160") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("1.000")){
			
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
	public void Script_0027() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "BA_0027";
		// Emisión DTE - Individual - Boleta Afecta - producto con descuento en pesos
		System.out.println(cp);
		String resultado1 = null;
				
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Boleta Afecta Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		driver.findElement(By.id("formEmitirdocumento_rutReceptor")).clear();
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.SeleccionarTipoServicioDatosBoleta(cp, "1 Boletas de servicios periódicos");
		pageEmisionDTE.AgregarProductoDescuentoPeso(cp, "000001", "1", "200");
		pageEmisionDTE.BtnEmitirFacturaAfecta(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("BOLETA ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("200") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[7]")).getText().contains("800") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("840") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("160") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("1.000")){
			
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
	public void Script_0028() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "BA_0028";
		// Emisión DTE - Individual - Boleta Afecta - producto con código de ítem
		System.out.println(cp);
		String resultado1 = null;
				
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Boleta Afecta Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		driver.findElement(By.id("formEmitirdocumento_rutReceptor")).clear();
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.SeleccionarTipoServicioDatosBoleta(cp, "1 Boletas de servicios periódicos");
		pageEmisionDTE.AgregarProductoCodigoItem(cp, "000001", "1", "01", "500");
		pageEmisionDTE.BtnEmitirFacturaAfecta(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("BOLETA ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("840") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("160") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("1.000")){
			
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
	public void Script_0029() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "BA_0029";
		// Emisión DTE - Individual - Boleta Afecta - producto - limpiar
		System.out.println(cp);
		String resultado1 = null;
				
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Boleta Afecta Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		driver.findElement(By.id("formEmitirdocumento_rutReceptor")).clear();
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.SeleccionarTipoServicioDatosBoleta(cp, "1 Boletas de servicios periódicos");
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
		pageEmisionDTE.ClickLimpiarProducto(cp);
		
		List<WebElement> listaCodigoProducto = new ArrayList<WebElement>();
		listaCodigoProducto = pageEscritorio.BuscarAgregarProducto();//SE CREA MÉTODO BUSCARAGREGARPRODUCTO
		
		List<String> lista_referencia = new ArrayList<String>();
		lista_referencia.add("Código Producto *");
		lista_referencia.add("Código Producto Aux");
		lista_referencia.add("Producto *");
		lista_referencia.add("Precio Bruto");
		lista_referencia.add("Unidad de Medida");
		lista_referencia.add("Cantidad");
		
		for (int i = 0; i <= listaCodigoProducto.size() - 1; i++) {
			System.out.println("print gettext():");
			System.out.println(listaCodigoProducto.get(i).getText());
			if(listaCodigoProducto.get(i).getText().equals(lista_referencia.get(i))) {
				System.out.println("SON IGUALES");
				resultado1 = "FLUJO OK";
			}else {
				System.out.println("SON DISTINTOS");
				resultado1 = "FLUJO NOOK";
			}
		}
		
		//pageEmisionDTE.BtnEmitirFacturaAfecta(cp);
		
		
		assertEquals(resultado1, "FLUJO OK", "Se verifica resultado del test "+cp);
		
	}
	
	@Test
	public void Script_0030() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "BA_0030";
		// Emisión DTE - Individual - Boleta Afecta - parámetros adicionales
		System.out.println(cp);
		String resultado1 = null;
				
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Boleta Afecta Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		driver.findElement(By.id("formEmitirdocumento_rutReceptor")).clear();
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.SeleccionarTipoServicioDatosBoleta(cp, "1 Boletas de servicios periódicos");
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
		pageEmisionDTE.IngresoParametrosAdicionales(cp, "PRUEBA QA", "11.111.111-1");
		
		pageEmisionDTE.BtnEmitirFacturaAfecta(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("BOLETA ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[2]")).getText().contains("PRUEBA QA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[2]/table/tbody/tr[5]/td[2]")).getText().contains("11.111.111-1") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("840") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("160") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("1.000")){
			
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
	public void Script_0031() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "BA_0031";
		// Emisión DTE - Individual - Boleta Afecta - parámetros agrupados adicionales
		System.out.println(cp);
		String resultado1 = null;
				
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Boleta Afecta Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		driver.findElement(By.id("formEmitirdocumento_rutReceptor")).clear();
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.SeleccionarTipoServicioDatosBoleta(cp, "1 Boletas de servicios periódicos");
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
		pageEmisionDTE.IngresoParametrosAgrupadosAdicionales(cp, "coca_cola", "Efectivo");
		
		pageEmisionDTE.BtnEmitirFacturaAfecta(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("BOLETA ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("840") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("160") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("1.000")){
			
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
	public void Script_0032() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "BA_0032";
		// Emisión DTE - Individual - Boleta Afecta - con observaciones
		System.out.println(cp);
		String resultado1 = null;
				
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Boleta Afecta Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		driver.findElement(By.id("formEmitirdocumento_rutReceptor")).clear();
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.SeleccionarTipoServicioDatosBoleta(cp, "1 Boletas de servicios periódicos");
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
		pageEmisionDTE.IngresoObservaciones(cp, "Observación de Prueba");
		
		pageEmisionDTE.BtnEmitirFacturaAfecta(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("BOLETA ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("840") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("160") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("1.000")&&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[4]/tbody/tr/td[1]/table/tbody/tr[2]/td")).getText().contains("Observación de Prueba")){
			
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
	public void Script_0033() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "BA_0033";
		// Emisión DTE - Individual - Boleta Afecta - sin observaciones
		System.out.println(cp);
		String resultado1 = null;
				
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Boleta Afecta Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		driver.findElement(By.id("formEmitirdocumento_rutReceptor")).clear();
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.SeleccionarTipoServicioDatosBoleta(cp, "1 Boletas de servicios periódicos");
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
		
		pageEmisionDTE.BtnEmitirFacturaAfecta(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("BOLETA ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("840") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("160") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("1.000")){
			
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
	public void Script_0035() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "BA_0035";
		// Emisión DTE - Individual - Boleta Afecta - monto exento - descuento/recargo - Descuento %
		System.out.println(cp);
		String resultado1 = null;
				
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Boleta Afecta Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		driver.findElement(By.id("formEmitirdocumento_rutReceptor")).clear();
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.SeleccionarTipoServicioDatosBoleta(cp, "1 Boletas de servicios periódicos");
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
		pageEmisionDTE.AgregarProductoCheckboxExento(cp, "000002", "1");
		pageEmisionDTE.SwithSIDescuentoRecargo(cp);
		pageEmisionDTE.IngresoDescuentoPrc(cp, "10", "Descuento QA");
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.BtnEmitirFacturaAfecta(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("BOLETA ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("840") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("1.800")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("160") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[5]/td[3]")).getText().contains("2.800")){
			
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
	public void Script_0036() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "BA_0036";
		// Emisión DTE - Individual - Boleta Afecta - monto exento - descuento/recargo - Descuento - pesos
		System.out.println(cp);
		String resultado1 = null;
				
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Boleta Afecta Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		driver.findElement(By.id("formEmitirdocumento_rutReceptor")).clear();
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.SeleccionarTipoServicioDatosBoleta(cp, "1 Boletas de servicios periódicos");
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
		pageEmisionDTE.AgregarProductoCheckboxExento(cp, "000002", "1");
		pageEmisionDTE.SwithSIDescuentoRecargo(cp);
		pageEmisionDTE.IngresoDescuentoPeso(cp, "100", "Descuento QA");
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.BtnEmitirFacturaAfecta(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("BOLETA ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				
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
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("100") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("840") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("1.900")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("160") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[5]/td[3]")).getText().contains("2.900")){
			
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
	public void Script_0037() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "BA_0037";
		// Emisión DTE - Individual - Boleta Afecta - monto exento - descuento/recargo - Recargo - %
		System.out.println(cp);
		String resultado1 = null;
				
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Boleta Afecta Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		driver.findElement(By.id("formEmitirdocumento_rutReceptor")).clear();
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.SeleccionarTipoServicioDatosBoleta(cp, "1 Boletas de servicios periódicos");
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
		pageEmisionDTE.AgregarProductoCheckboxExento(cp, "000002", "1");
		pageEmisionDTE.SwithSIDescuentoRecargo(cp);
		pageEmisionDTE.IngresoRecargoPrc(cp, "2", "Recargo QA");
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.BtnEmitirFacturaAfecta(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("BOLETA ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				
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
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("40") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("840") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("2.040")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("160") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[5]/td[3]")).getText().contains("3.040")){
			
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
	public void Script_0038() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "BA_0038";
		// Emisión DTE - Individual - Boleta Afecta - monto exento - descuento/recargo - Recargo - pesos
		System.out.println(cp);
		String resultado1 = null;
				
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Boleta Afecta Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		driver.findElement(By.id("formEmitirdocumento_rutReceptor")).clear();
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.SeleccionarTipoServicioDatosBoleta(cp, "1 Boletas de servicios periódicos");
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
		pageEmisionDTE.AgregarProductoCheckboxExento(cp, "000002", "1");
		pageEmisionDTE.SwithSIDescuentoRecargo(cp);
		pageEmisionDTE.IngresoRecargoPeso(cp, "5", "Recargo QA");
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.BtnEmitirFacturaAfecta(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("BOLETA ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				
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
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("5") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("840") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("2.005")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("160") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[5]/td[3]")).getText().contains("3.005")){
			
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
	public void Script_0039() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "BA_0039";
		// Emisión DTE - Individual - Boleta Afecta - monto exento - descuento/recargo - Eliminar
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
		pageEscritorio.SeleccionarTipoDocumento(cp, "Boleta Afecta Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		driver.findElement(By.id("formEmitirdocumento_rutReceptor")).clear();
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.SeleccionarTipoServicioDatosBoleta(cp, "1 Boletas de servicios periódicos");
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
		pageEmisionDTE.AgregarProductoCheckboxExento(cp, "000002", "1");
		pageEmisionDTE.SwithSIDescuentoRecargo(cp);
		pageEmisionDTE.IngresoRecargoPeso(cp, "2", "Recargo QA");
		pageEmisionDTE.ClickEliminar(cp);
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		//Primera validación
		if(driver.findElement(By.xpath("//*[@id=\"columnaDescuentosExento\"]/div/label")).getText().contains("Descuento/Recargo") &&
				driver.findElement(By.xpath("//*[@id=\"columnaDescuentosExento\"]/div/div/div/label[2]")).getText().contains("No")){
			resultado1 = "FLUJO OK";
		}
		else {
			resultado1 = "FLUJO NOOK";
		}
		
		pageEmisionDTE.BtnEmitirFacturaAfecta(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("BOLETA ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				
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
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("840") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("2.000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("160")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("3.000")){
			
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
	public void Script_0041() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "BA_0041";
		// Emisión DTE - Individual - Boleta Afecta - monto neto - descuento/recargo - Descuento %
		System.out.println(cp);
		String resultado1 = null;
				
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Boleta Afecta Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		driver.findElement(By.id("formEmitirdocumento_rutReceptor")).clear();
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.SeleccionarTipoServicioDatosBoleta(cp, "1 Boletas de servicios periódicos");
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
		pageEmisionDTE.AgregarProductoCheckboxExento(cp, "000002", "1");
		pageEmisionDTE.SwithSIDescuentoRecargo(cp);
		pageEmisionDTE.IngresoDescuentoPrcNeto(cp, "2", "Descuento QA");
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.BtnEmitirFacturaAfecta(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("BOLETA ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				
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
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("40") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("840") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("1.960")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("160")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[5]/td[3]")).getText().contains("2.960")){
			
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
	public void Script_0042() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "BA_0042";
		// Emisión DTE - Individual - Boleta Afecta - monto neto - descuento/recargo - Descuento - pesos
		System.out.println(cp);
		String resultado1 = null;
				
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Boleta Afecta Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		driver.findElement(By.id("formEmitirdocumento_rutReceptor")).clear();
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.SeleccionarTipoServicioDatosBoleta(cp, "1 Boletas de servicios periódicos");
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
		pageEmisionDTE.AgregarProductoCheckboxExento(cp, "000002", "1");
		pageEmisionDTE.SwithSIDescuentoRecargo(cp);
		pageEmisionDTE.IngresoDescuentoPesoNeto(cp, "100", "Descuento QA");
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.BtnEmitirFacturaAfecta(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("BOLETA ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				
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
		
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("100") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("840") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("1900")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("160")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[5]/td[3]")).getText().contains("2.900")){
			
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
	public void Script_0043() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "BA_0043";
		// Emisión DTE - Individual - Boleta Afecta - monto neto - descuento/recargo - Recargo - %
		System.out.println(cp);
		String resultado1 = null;
				
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Boleta Afecta Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		driver.findElement(By.id("formEmitirdocumento_rutReceptor")).clear();
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.SeleccionarTipoServicioDatosBoleta(cp, "1 Boletas de servicios periódicos");
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
		pageEmisionDTE.SwithSIDescuentoNeto(cp);
		pageEmisionDTE.IngresoRecargoPrcNeto(cp, "10", "Recargo QA");
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.BtnEmitirFacturaAfecta(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("BOLETA ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("84") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("840") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("160")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("1.000")){
			
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
	public void Script_0044() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "BA_0044";
		// Emisión DTE - Individual - Boleta Afecta - monto neto - descuento/recargo - Recargo - pesos
		System.out.println(cp);
		String resultado1 = null;
				
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Boleta Afecta Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		driver.findElement(By.id("formEmitirdocumento_rutReceptor")).clear();
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.SeleccionarTipoServicioDatosBoleta(cp, "1 Boletas de servicios periódicos");
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
		pageEmisionDTE.SwithSIDescuentoNeto(cp);
		pageEmisionDTE.IngresoRecargoPesoNeto(cp, "100", "Recargo QA");
		
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.BtnEmitirFacturaAfecta(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("BOLETA ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("100") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("840") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("160")&&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[4]/td[3]")).getText().contains("1.000")){
			
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
	public void Script_0045() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "BA_0045";
		// Emisión DTE - Individual - Boleta Afecta - monto neto - descuento/recargo - Eliminar
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
		pageEscritorio.SeleccionarTipoDocumento(cp, "Boleta Afecta Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		driver.findElement(By.id("formEmitirdocumento_rutReceptor")).clear();
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.SeleccionarTipoServicioDatosBoleta(cp, "1 Boletas de servicios periódicos");
		pageEmisionDTE.AgregarProducto1(cp, "000001", "1");
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
		pageEmisionDTE.BtnEmitirFacturaAfecta(cp);
		
		if(driver.findElement(By.className("facturaDocumento")).isDisplayed() && 
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("BOLETA ELECTRÓNICA") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]")).getText().contains("81.537.600-5") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[1]")).getText().contains("000001") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[2]")).getText().contains("Harina retencion 12%") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[3]")).getText().contains("1") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[4]")).getText().contains("TO") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[5]")).getText().contains("1000") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[2]/tbody/tr/td[1]/table[2]/tbody/tr[2]/td[6]")).getText().contains("1000") &&
				
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td[3]")).getText().contains("840") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[2]/td[3]")).getText().contains("160") &&
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[3]/tbody/tr/td[3]/table/tbody/tr[3]/td[3]")).getText().contains("1.000")){
			
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
	public void Script_0046() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException, AWTException {
		String cp = "BA_0046";
		// Emisión DTE - Individual - Boleta Afecta - Limpiar
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
		pageEscritorio.SeleccionarTipoDocumento(cp, "Boleta Afecta Electrónica");
		
		PageEmisionDTE pageEmisionDTE = new PageEmisionDTE(driver);
		pageEmisionDTE.FechaEmision(cp);
		driver.findElement(By.id("formEmitirdocumento_rutReceptor")).clear();
		pageEmisionDTE.IngresoRutCliente(cp, "81.537.600-5");
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.setAutoDelay(7);
		robot.mouseWheel(7);
		Thread.sleep(2000);
		
		pageEmisionDTE.SeleccionarTipoServicioDatosBoleta(cp, "1 Boletas de servicios periódicos");
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
