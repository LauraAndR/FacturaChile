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

public class Test_FacturaDescuentosGlobalAfecta {

	private WebDriver driver;
	String datapool = Configuration.ROOT_DIR+"DataPool_v2.xlsx";
	LeerExcel leerExcel = new LeerExcel();
	@BeforeMethod
	public void setUp() throws FileNotFoundException, IOException {

		System.setProperty("webdriver.chrome.driver", "Drivers/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.navigate().to("https://escritorio-qa.acepta.pe/");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	@Test
	public void Script_0096() throws InterruptedException, IOException, InvalidFormatException {
//		EAP_0096
		String cp = "EAP_0096";
		System.out.println(cp);
		
		PageLogin pageLogin = new PageLogin(driver);
		String[] datos = leerExcel.ObtenerDatosCP(datapool,cp);
		pageLogin.login(datos[1], datos[2]);
		
		PageSeleccionCpe pageSeleccionCpe = new PageSeleccionCpe(driver);
		pageSeleccionCpe.clickEmisionExpress(cp);
		pageSeleccionCpe.seleccionTipoCpe(datos[3], cp);
		pageSeleccionCpe.seleccionSubtipoCpe(datos[4], cp);
		pageSeleccionCpe.seleccionTipoDescuento(datos[5],cp);
		
		PageDatosEmision pageDatosEmision = new PageDatosEmision(driver);
		pageDatosEmision.completarDatosEmision(cp);
		
		PageReceptor pageReceptor = new PageReceptor(driver);
		pageReceptor.completarReceptor(cp);
		pageReceptor.datosReceptor(cp, datos[10], datos[11]);
		pageReceptor.seleccionTipoDocumentoReceptor(cp, datos[12]);
		pageReceptor.botonAgregarReceptordivVeintiTres(cp);
		
		PageDetalle pageDetalle = new PageDetalle(driver);
		pageDetalle.completarDetalleProductos(cp, datos[8], datos[9]);
		pageDetalle.datosDescuento(cp, datos[13]);
		pageDetalle.botonAgregarProductosdivTreintayUno(cp);
//		pageDetalle.botonAgregarProductosdivVeintiNueve(cp);
//		pageDetalle.botonAgregarProductosdivTreintayTres(cp);
		pageDetalle.montoDescuento(cp, datos[14]);
		
		PageFooter pageFooter = new PageFooter(driver);
		pageFooter.clickPrevisualizacion(cp);
		pageFooter.clickEnviaraSunat(cp);
		
		System.out.println("FLUJO OK");
	}
	
	@Test
	public void Script_0097() throws InterruptedException, IOException, InvalidFormatException {
//		EAP_0097
		String cp = "EAP_0097";
		System.out.println(cp);
		
		PageLogin pageLogin = new PageLogin(driver);
		String[] datos = leerExcel.ObtenerDatosCP(datapool,cp);
		pageLogin.login(datos[1], datos[2]);
		
		PageSeleccionCpe pageSeleccionCpe = new PageSeleccionCpe(driver);
		pageSeleccionCpe.clickEmisionExpress(cp);
		pageSeleccionCpe.seleccionTipoCpe(datos[3], cp);
		pageSeleccionCpe.seleccionSubtipoCpe(datos[4], cp);
		pageSeleccionCpe.seleccionTipoDescuento(datos[5],cp);
		
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
		pageDetalle.datosDescuento(cp, datos[13]);
		pageDetalle.botonAgregarProductosdivTreintayUno(cp);
//		pageDetalle.botonAgregarProductosdivVeintiNueve(cp);
//		pageDetalle.botonAgregarProductosdivTreintayTres(cp);
		pageDetalle.montoDescuento(cp, datos[14]);
		
		PageFooter pageFooter = new PageFooter(driver);
		pageFooter.clickPrevisualizacion(cp);
		pageFooter.clickEnviaraSunat(cp);
		
		System.out.println("FLUJO OK");
	}
	
	@Test
	public void Script_0098() throws InterruptedException, IOException, InvalidFormatException {
//		EAP_0096
		String cp = "EAP_0098";
		System.out.println(cp);
		
		PageLogin pageLogin = new PageLogin(driver);
		String[] datos = leerExcel.ObtenerDatosCP(datapool,cp);
		pageLogin.login(datos[1], datos[2]);
		
		PageSeleccionCpe pageSeleccionCpe = new PageSeleccionCpe(driver);
		pageSeleccionCpe.clickEmisionExpress(cp);
		pageSeleccionCpe.seleccionTipoCpe(datos[3], cp);
		pageSeleccionCpe.seleccionSubtipoCpe(datos[4], cp);
		pageSeleccionCpe.seleccionTipoDescuento(datos[5],cp);
		
		PageDatosEmision pageDatosEmision = new PageDatosEmision(driver);
		pageDatosEmision.completarDatosEmision(cp);
		
		PageReceptor pageReceptor = new PageReceptor(driver);
		pageReceptor.completarReceptor(cp);
		pageReceptor.datosReceptor(cp, datos[10], datos[11]);
		pageReceptor.seleccionTipoDocumentoReceptor(cp, datos[12]);
		pageReceptor.botonAgregarReceptordivVeintiTres(cp);
		
		PageDetalle pageDetalle = new PageDetalle(driver);
		pageDetalle.completarDetalleProductos(cp, datos[8], datos[9]);
		pageDetalle.datosDescuento(cp, datos[13]);
		pageDetalle.botonAgregarProductosdivTreintayUno(cp);
//		pageDetalle.botonAgregarProductosdivVeintiNueve(cp);
//		pageDetalle.botonAgregarProductosdivTreintayTres(cp);
		pageDetalle.montoDescuento(cp, datos[14]);
		
		PageFooter pageFooter = new PageFooter(driver);
		pageFooter.clickPrevisualizacion(cp);
		pageFooter.clickEnviaraSunat(cp);
		
		System.out.println("FLUJO OK");
	}
	
	@Test
	public void Script_0099() throws InterruptedException, IOException, InvalidFormatException {
//		EAP_0097
		String cp = "EAP_0099";
		System.out.println(cp);
		
		PageLogin pageLogin = new PageLogin(driver);
		String[] datos = leerExcel.ObtenerDatosCP(datapool,cp);
		pageLogin.login(datos[1], datos[2]);
		
		PageSeleccionCpe pageSeleccionCpe = new PageSeleccionCpe(driver);
		pageSeleccionCpe.clickEmisionExpress(cp);
		pageSeleccionCpe.seleccionTipoCpe(datos[3], cp);
		pageSeleccionCpe.seleccionSubtipoCpe(datos[4], cp);
		pageSeleccionCpe.seleccionTipoDescuento(datos[5],cp);
		
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
		pageDetalle.datosDescuento(cp, datos[13]);
		pageDetalle.botonAgregarProductosdivTreintayUno(cp);
//		pageDetalle.botonAgregarProductosdivVeintiNueve(cp);
//		pageDetalle.botonAgregarProductosdivTreintayTres(cp);
		pageDetalle.montoDescuento(cp, datos[14]);
		
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
