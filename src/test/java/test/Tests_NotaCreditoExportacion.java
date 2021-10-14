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
		String cp = "NCE_0002";
		// Emisión DTE - Individual - Nota Credito Exp - contado - factura de Servicios
		System.out.println(cp);
		String resultado1 = null;
				
		PageLoginAdm pageLoginAdm = new PageLoginAdm(driver);
		
		CrearLogyDocumento crearLogyDocumento = new CrearLogyDocumento(driver);
		crearLogyDocumento.CrearEvidencias(cp);
		
		pageLoginAdm.ClickIngresarLogin(cp);
		pageLoginAdm.LoginIdentidadDigital(cp, Configuration.USER_RUTH, Configuration.PASS_RUTH);
		
		PageEscritorio pageEscritorio = new PageEscritorio(driver);
		pageEscritorio.BarraMenu(cp, "Emisión DTE");
		pageEscritorio.SeleccionarTipoDocumento(cp, "Nota de Crédito de Exportación Electronica");
		
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
				driver.findElement(By.xpath("//*[@id=\"pintador\"]/div/div/div/div/table/tbody/tr/td/table[1]/tbody/tr/td[2]/table/tbody/tr/td/font/b/div[2]")).getText().contains("BOLETA EXENTA ELECTRÓNICA") &&
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
	
	@AfterMethod
	public void FinEjecucion() {
		driver.close();
	}

}
