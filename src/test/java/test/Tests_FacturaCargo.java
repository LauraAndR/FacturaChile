package test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import common.Configuration;
import common.LeerExcel;
import pages.PageDatosEmision;
import pages.PageDetalle;
import pages.PageFooter;
import pages.PageLogin;
import pages.PageReceptor;
import pages.PageSeleccionCpe;

public class Tests_FacturaCargo {
	private WebDriver driver;
	String datapool = Configuration.ROOT_DIR+"DataPool_v2.xlsx";
	LeerExcel leerExcel = new LeerExcel();
	@BeforeMethod
	public void setUp() throws FileNotFoundException, IOException {
//		DesiredCapabilities caps = new DesiredCapabilities();
		System.setProperty("webdriver.chrome.driver", "Drivers/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.navigate().to("https://escritorio-qa.acepta.pe/");// Aquí se ingresa la URL para hacer las pruebas.
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	@Test
	public void Script_0012() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException {
//		EAP_0012
		String cp = "EAP_0012";
		System.out.println(cp);
		
		PageLogin pageLogin = new PageLogin(driver);
		String[] datos = leerExcel.ObtenerDatosCP(datapool,cp);
		pageLogin.login(datos[1], datos[2]);
		
		PageSeleccionCpe pageSeleccionCpe = new PageSeleccionCpe(driver);
		pageSeleccionCpe.clickEmisionExpress(cp);
		pageSeleccionCpe.seleccionTipoCpe(datos[3], cp);
		pageSeleccionCpe.seleccionSubtipoCpe(datos[4], cp);
		pageSeleccionCpe.seleccionTipoCargo(datos[5],cp);
		
		PageDatosEmision pageDatosEmision = new PageDatosEmision(driver);
		pageDatosEmision.completarDatosEmision(cp);
		
		PageReceptor pageReceptor = new PageReceptor(driver);
		pageReceptor.completarReceptor(cp);
		pageReceptor.datosReceptor(cp, datos[10], datos[11]);
		pageReceptor.seleccionTipoDocumentoReceptor(cp, datos[12]);
		pageReceptor.botonAgregarReceptordivVeintiTres(cp);
				
		
		PageDetalle pageDetalle = new PageDetalle(driver);
		pageDetalle.completarDetalleProductos(cp, datos[8], datos[9]);
		pageDetalle.cargoItem(cp);
		pageDetalle.botonAgregarProductosdivTreintayUno(cp);
		//pageDetalle.botonAgregarProductosdivTreintayCinco(cp);
		
		PageFooter pageFooter = new PageFooter(driver);
		pageFooter.clickPrevisualizacion(cp);
		pageFooter.clickEnviaraSunat(cp);
		
		System.out.println("FLUJO OK");
	}
	
	@Test
	public void Script_0013() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException {
//		EAP_0013
		String cp = "EAP_0013";
		System.out.println(cp);
		
		PageLogin pageLogin = new PageLogin(driver);
		String[] datos = leerExcel.ObtenerDatosCP(datapool,cp);
		pageLogin.login(datos[1], datos[2]);
		
		PageSeleccionCpe pageSeleccionCpe = new PageSeleccionCpe(driver);
		pageSeleccionCpe.clickEmisionExpress(cp);
		pageSeleccionCpe.seleccionTipoCpe(datos[3], cp);
		pageSeleccionCpe.seleccionSubtipoCpe(datos[4], cp);
		pageSeleccionCpe.seleccionTipoCargo(datos[5],cp);
		
		PageDatosEmision pageDatosEmision = new PageDatosEmision(driver);
		pageDatosEmision.completarDatosEmision(cp);
		
		PageReceptor pageReceptor = new PageReceptor(driver);
		pageReceptor.completarReceptor(cp);
		pageReceptor.datosReceptor(cp, datos[10], datos[11]);
		pageReceptor.seleccionTipoDocumentoReceptor(cp, datos[12]);
		pageReceptor.botonAgregarReceptordivVeintiTres(cp);
		
		PageDetalle pageDetalle = new PageDetalle(driver);
		pageDetalle.completarDetalleProductos(cp, datos[8], datos[9]);
		pageDetalle.cargoItem(cp);
		pageDetalle.botonAgregarProductosdivTreintayUno(cp);
		//pageDetalle.botonAgregarProductosdivTreintayCinco(cp);
		pageDetalle.cargoNivelGlobal(cp);
		
		PageFooter pageFooter = new PageFooter(driver);
		pageFooter.clickPrevisualizacion(cp);
		pageFooter.clickEnviaraSunat(cp);
		
		System.out.println("FLUJO OK");
	}
	
	@Test
	public void Script_0014() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException {
//		EAP_0014
		String cp = "EAP_0014";
		System.out.println(cp);
		
		PageLogin pageLogin = new PageLogin(driver);
		String[] datos = leerExcel.ObtenerDatosCP(datapool,cp);
		pageLogin.login(datos[1], datos[2]);
		
		PageSeleccionCpe pageSeleccionCpe = new PageSeleccionCpe(driver);
		pageSeleccionCpe.clickEmisionExpress(cp);
		pageSeleccionCpe.seleccionTipoCpe(datos[3], cp);
		pageSeleccionCpe.seleccionSubtipoCpe(datos[4], cp);
		pageSeleccionCpe.seleccionTipoCargo(datos[5],cp);
		
		PageDatosEmision pageDatosEmision = new PageDatosEmision(driver);
		pageDatosEmision.completarDatosEmision(cp);
		
		PageReceptor pageReceptor = new PageReceptor(driver);
		pageReceptor.completarReceptor(cp);
		pageReceptor.datosReceptor(cp, datos[10], datos[11]);
		pageReceptor.seleccionTipoDocumentoReceptor(cp, datos[12]);
		pageReceptor.botonAgregarReceptordivVeintiTres(cp);
		
		PageDetalle pageDetalle = new PageDetalle(driver);
		pageDetalle.completarDetalleProductos(cp, datos[8], datos[9]);
		pageDetalle.botonAgregarProductosdivTreintayUno(cp);
		//pageDetalle.botonAgregarProductosdivTreintayCinco(cp);
		pageDetalle.cargoNivelGlobal(cp);
		
		PageFooter pageFooter = new PageFooter(driver);
		pageFooter.clickPrevisualizacion(cp);
		pageFooter.clickEnviaraSunat(cp);
		
		System.out.println("FLUJO OK");
	}
	
	@Test
	public void Script_0015() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException {
//		EAP_0015
		String cp = "EAP_0015";
		System.out.println(cp);
		
		PageLogin pageLogin = new PageLogin(driver);
		String[] datos = leerExcel.ObtenerDatosCP(datapool,cp);
		pageLogin.login(datos[1], datos[2]);
		
		PageSeleccionCpe pageSeleccionCpe = new PageSeleccionCpe(driver);
		pageSeleccionCpe.clickEmisionExpress(cp);
		pageSeleccionCpe.seleccionTipoCpe(datos[3], cp);
		pageSeleccionCpe.seleccionSubtipoCpe(datos[4], cp);
		pageSeleccionCpe.seleccionTipoCargo(datos[5],cp);
		
		PageDatosEmision pageDatosEmision = new PageDatosEmision(driver);
		pageDatosEmision.completarDatosEmision(cp);
		
		PageReceptor pageReceptor = new PageReceptor(driver);
		pageReceptor.completarReceptor(cp);
		pageReceptor.datosReceptor(cp, datos[10], datos[11]);
		pageReceptor.seleccionTipoDocumentoReceptor(cp, datos[12]);
		pageReceptor.botonAgregarReceptordivVeintiTres(cp);
		
		PageDetalle pageDetalle = new PageDetalle(driver);
		pageDetalle.itemNoAfecto(cp);
		pageDetalle.completarDetalleProductos(cp, datos[8], datos[9]);
		pageDetalle.cargoItem(cp);
		pageDetalle.botonAgregarProductosdivTreintayUno(cp);
		//pageDetalle.botonAgregarProductosdivTreintayCinco(cp);
		
		PageFooter pageFooter = new PageFooter(driver);
		pageFooter.clickPrevisualizacion(cp);
		pageFooter.clickEnviaraSunat(cp);
		
		System.out.println("FLUJO OK");
	}
	
	@Test
	public void Script_0016() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException {
//		EAP_0016
		String cp = "EAP_0016";
		System.out.println(cp);
		
		PageLogin pageLogin = new PageLogin(driver);
		String[] datos = leerExcel.ObtenerDatosCP(datapool,cp);
		pageLogin.login(datos[1], datos[2]);
		
		PageSeleccionCpe pageSeleccionCpe = new PageSeleccionCpe(driver);
		pageSeleccionCpe.clickEmisionExpress(cp);
		pageSeleccionCpe.seleccionTipoCpe(datos[3], cp);
		pageSeleccionCpe.seleccionSubtipoCpe(datos[4], cp);
		pageSeleccionCpe.seleccionTipoCargo(datos[5],cp);
		
		PageDatosEmision pageDatosEmision = new PageDatosEmision(driver);
		pageDatosEmision.completarDatosEmision(cp);
		
		PageReceptor pageReceptor = new PageReceptor(driver);
		pageReceptor.completarReceptor(cp);
		pageReceptor.datosReceptor(cp, datos[10], datos[11]);
		pageReceptor.seleccionTipoDocumentoReceptor(cp, datos[12]);
		pageReceptor.botonAgregarReceptordivVeintiTres(cp);
		
		PageDetalle pageDetalle = new PageDetalle(driver);
		pageDetalle.itemNoAfecto(cp);
		pageDetalle.completarDetalleProductos(cp, datos[8], datos[9]);
		pageDetalle.botonAgregarProductosdivTreintayUno(cp);
		//pageDetalle.botonAgregarProductosdivTreintayCinco(cp);
		pageDetalle.cargoNivelGlobal(cp);
		
		PageFooter pageFooter = new PageFooter(driver);
		pageFooter.clickPrevisualizacion(cp);
		pageFooter.clickEnviaraSunat(cp);
		
		System.out.println("FLUJO OK");
	}
	
	@Test
	public void Script_0017() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException {
//		EAP_0017
		String cp = "EAP_0017";
		System.out.println(cp);
		
		PageLogin pageLogin = new PageLogin(driver);
		String[] datos = leerExcel.ObtenerDatosCP(datapool,cp);
		pageLogin.login(datos[1], datos[2]);
		
		PageSeleccionCpe pageSeleccionCpe = new PageSeleccionCpe(driver);
		pageSeleccionCpe.clickEmisionExpress(cp);
		pageSeleccionCpe.seleccionTipoCpe(datos[3], cp);
		pageSeleccionCpe.seleccionSubtipoCpe(datos[4], cp);
		pageSeleccionCpe.seleccionTipoCargo(datos[5],cp);
		
		PageDatosEmision pageDatosEmision = new PageDatosEmision(driver);
		pageDatosEmision.completarDatosEmision(cp);
		
		PageReceptor pageReceptor = new PageReceptor(driver);
		pageReceptor.completarReceptor(cp);
		pageReceptor.datosReceptor(cp, datos[10], datos[11]);
		pageReceptor.seleccionTipoDocumentoReceptor(cp, datos[12]);
		pageReceptor.botonAgregarReceptordivVeintiTres(cp);
		
		PageDetalle pageDetalle = new PageDetalle(driver);
		pageDetalle.completarDetalleProductos(cp, datos[8], datos[9]);
		pageDetalle.cargoItem(cp);
		pageDetalle.botonAgregarProductosdivTreintayUno(cp);
		//pageDetalle.botonAgregarProductosdivTreintayCinco(cp);
		
		PageFooter pageFooter = new PageFooter(driver);
		pageFooter.clickPrevisualizacion(cp);
		pageFooter.clickEnviaraSunat(cp);
		
		System.out.println("FLUJO OK");
	}
	
	@Test
	public void Script_0018() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException {
//		EAP_0018
		String cp = "EAP_0018";
		System.out.println(cp);
		
		PageLogin pageLogin = new PageLogin(driver);
		String[] datos = leerExcel.ObtenerDatosCP(datapool,cp);
		pageLogin.login(datos[1], datos[2]);
		
		PageSeleccionCpe pageSeleccionCpe = new PageSeleccionCpe(driver);
		pageSeleccionCpe.clickEmisionExpress(cp);
		pageSeleccionCpe.seleccionTipoCpe(datos[3], cp);
		pageSeleccionCpe.seleccionSubtipoCpe(datos[4], cp);
		pageSeleccionCpe.seleccionTipoCargo(datos[5],cp);
		
		PageDatosEmision pageDatosEmision = new PageDatosEmision(driver);
		pageDatosEmision.completarDatosEmision(cp);
		
		PageReceptor pageReceptor = new PageReceptor(driver);
		pageReceptor.completarReceptor(cp);
		pageReceptor.datosReceptor(cp, datos[10], datos[11]);
		pageReceptor.seleccionTipoDocumentoReceptor(cp, datos[12]);
		pageReceptor.botonAgregarReceptordivVeintiTres(cp);
		
		PageDetalle pageDetalle = new PageDetalle(driver);
		pageDetalle.completarDetalleProductos(cp, datos[8], datos[9]);
		pageDetalle.botonAgregarProductosdivTreintayUno(cp);
		//pageDetalle.botonAgregarProductosdivTreintayCinco(cp);
		pageDetalle.cargoNivelGlobal(cp);
		
		PageFooter pageFooter = new PageFooter(driver);
		pageFooter.clickPrevisualizacion(cp);
		pageFooter.clickEnviaraSunat(cp);
		
		System.out.println("FLUJO OK");
	}
	
	@Test
	public void Script_0019() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException {
//		EAP_0019
		String cp = "EAP_0019";
		System.out.println(cp);
		
		PageLogin pageLogin = new PageLogin(driver);
		String[] datos = leerExcel.ObtenerDatosCP(datapool,cp);
		pageLogin.login(datos[1], datos[2]);
		
		PageSeleccionCpe pageSeleccionCpe = new PageSeleccionCpe(driver);
		pageSeleccionCpe.clickEmisionExpress(cp);
		pageSeleccionCpe.seleccionTipoCpe(datos[3], cp);
		pageSeleccionCpe.seleccionSubtipoCpe(datos[4], cp);
		pageSeleccionCpe.seleccionTipoCargo(datos[5],cp);
		
		PageDatosEmision pageDatosEmision = new PageDatosEmision(driver);
		pageDatosEmision.completarDatosEmision(cp);
		
		PageReceptor pageReceptor = new PageReceptor(driver);
		pageReceptor.completarReceptor(cp);
		pageReceptor.datosReceptor(cp, datos[10], datos[11]);
		pageReceptor.seleccionTipoDocumentoReceptor(cp, datos[12]);
		pageReceptor.botonAgregarReceptordivVeintiTres(cp);
		
		PageDetalle pageDetalle = new PageDetalle(driver);
		pageDetalle.itemNoAfecto(cp);
		pageDetalle.completarDetalleProductos(cp, datos[8], datos[9]);
		pageDetalle.cargoItem(cp);
		pageDetalle.botonAgregarProductosdivTreintayUno(cp);
		//pageDetalle.botonAgregarProductosdivTreintayCinco(cp);
		
		PageFooter pageFooter = new PageFooter(driver);
		pageFooter.clickPrevisualizacion(cp);
		pageFooter.clickEnviaraSunat(cp);
		
		System.out.println("FLUJO OK");
	}
	
	@Test
	public void Script_0020() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException {
//		EAP_0020
		String cp = "EAP_0020";
		System.out.println(cp);
		
		PageLogin pageLogin = new PageLogin(driver);
		String[] datos = leerExcel.ObtenerDatosCP(datapool,cp);
		pageLogin.login(datos[1], datos[2]);
		
		PageSeleccionCpe pageSeleccionCpe = new PageSeleccionCpe(driver);
		pageSeleccionCpe.clickEmisionExpress(cp);
		pageSeleccionCpe.seleccionTipoCpe(datos[3], cp);
		pageSeleccionCpe.seleccionSubtipoCpe(datos[4], cp);
		pageSeleccionCpe.seleccionTipoCargo(datos[5],cp);
		
		PageDatosEmision pageDatosEmision = new PageDatosEmision(driver);
		pageDatosEmision.completarDatosEmision(cp);
		
		PageReceptor pageReceptor = new PageReceptor(driver);
		pageReceptor.completarReceptor(cp);
		pageReceptor.datosReceptor(cp, datos[10], datos[11]);
		pageReceptor.seleccionTipoDocumentoReceptor(cp, datos[12]);
		pageReceptor.botonAgregarReceptordivVeintiTres(cp);
		
		PageDetalle pageDetalle = new PageDetalle(driver);
		pageDetalle.itemNoAfecto(cp);
		pageDetalle.completarDetalleProductos(cp, datos[8], datos[9]);
		pageDetalle.botonAgregarProductosdivTreintayUno(cp);
		//pageDetalle.botonAgregarProductosdivTreintayCinco(cp);
		pageDetalle.cargoNivelGlobal(cp);
		
		PageFooter pageFooter = new PageFooter(driver);
		pageFooter.clickPrevisualizacion(cp);
		pageFooter.clickEnviaraSunat(cp);
		
		System.out.println("FLUJO OK");
	}
	
	@Test
	public void Script_0021() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException {
//		EAP_0021
		String cp = "EAP_0021";
		System.out.println(cp);
		
		PageLogin pageLogin = new PageLogin(driver);
		String[] datos = leerExcel.ObtenerDatosCP(datapool,cp);
		pageLogin.login(datos[1], datos[2]);
		
		PageSeleccionCpe pageSeleccionCpe = new PageSeleccionCpe(driver);
		pageSeleccionCpe.clickEmisionExpress(cp);
		pageSeleccionCpe.seleccionTipoCpe(datos[3], cp);
		pageSeleccionCpe.seleccionSubtipoCpe(datos[4], cp);
		pageSeleccionCpe.seleccionTipoCargo(datos[5],cp);
		
		PageDatosEmision pageDatosEmision = new PageDatosEmision(driver);
		pageDatosEmision.completarDatosEmision(cp);
		
		PageReceptor pageReceptor = new PageReceptor(driver);
		pageReceptor.completarReceptor(cp);
		pageReceptor.datosReceptor(cp, datos[10], datos[11]);
		pageReceptor.seleccionTipoDocumentoReceptor(cp, datos[12]);
		pageReceptor.botonAgregarReceptordivVeintiTres(cp);
		
		PageDetalle pageDetalle = new PageDetalle(driver);
		pageDetalle.completarDetalleProductos(cp, datos[8], datos[9]);
		pageDetalle.botonAgregarProductosdivTreintayUno(cp);
		pageDetalle.cargoNivelGlobal(cp);
		
		PageFooter pageFooter = new PageFooter(driver);
		pageFooter.clickPrevisualizacion(cp);
		pageFooter.clickEnviaraSunat(cp);
		
		System.out.println("FLUJO OK");
	}
	
	@Test
	public void Script_0022() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException {
//		EAP_0022
		String cp = "EAP_0022";
		System.out.println(cp);
		
		PageLogin pageLogin = new PageLogin(driver);
		String[] datos = leerExcel.ObtenerDatosCP(datapool,cp);
		pageLogin.login(datos[1], datos[2]);
		
		PageSeleccionCpe pageSeleccionCpe = new PageSeleccionCpe(driver);
		pageSeleccionCpe.clickEmisionExpress(cp);
		pageSeleccionCpe.seleccionTipoCpe(datos[3], cp);
		pageSeleccionCpe.seleccionSubtipoCpe(datos[4], cp);
		pageSeleccionCpe.seleccionTipoCargo(datos[5],cp);
		
		PageDatosEmision pageDatosEmision = new PageDatosEmision(driver);
		pageDatosEmision.completarDatosEmision(cp);
		
		PageReceptor pageReceptor = new PageReceptor(driver);
		pageReceptor.completarReceptor(cp);
		pageReceptor.datosReceptor(cp, datos[10], datos[11]);
		pageReceptor.seleccionTipoDocumentoReceptor(cp, datos[12]);
		pageReceptor.botonAgregarReceptordivVeintiTres(cp);
		
		PageDetalle pageDetalle = new PageDetalle(driver);
		pageDetalle.itemNoAfecto(cp);
		pageDetalle.completarDetalleProductos(cp, datos[8], datos[9]);
		pageDetalle.botonAgregarProductosdivTreintayUno(cp);
		pageDetalle.cargoNivelGlobal(cp);
		
		PageFooter pageFooter = new PageFooter(driver);
		pageFooter.clickPrevisualizacion(cp);
		pageFooter.clickEnviaraSunat(cp);
		
		System.out.println("FLUJO OK");
	}
	
	@AfterMethod
	public void FinEjecucion() {
		driver.close();
	}
}
