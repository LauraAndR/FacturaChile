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
import pages.PageAgregarPropiedadesAdicionales;
import pages.PageDatosEmision;
import pages.PageDetalle;
import pages.PageFooter;
import pages.PageLogin;
import pages.PageReceptor;
import pages.PageSeleccionCpe;

public class Test_FacturaExportacion {
	private WebDriver driver;
	String datapool = Configuration.ROOT_DIR+"DataPool_v2.xlsx";
	LeerExcel leerExcel = new LeerExcel();
	@BeforeMethod
	public void setUp() throws FileNotFoundException, IOException {
//		DesiredCapabilities caps = new DesiredCapabilities();
		System.setProperty("webdriver.chrome.driver", "Drivers/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.navigate().to("https://escritorio-qa.acepta.pe/");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@Test
	public void Script_0085() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException {
//		EAP_0085
		String cp ="EAP_0085";
		System.out.println(cp);
		
		PageLogin pageLogin = new PageLogin(driver);
		String[] datos = leerExcel.ObtenerDatosCP(datapool,cp);
		pageLogin.login(datos[1], datos[2]);
		
		PageSeleccionCpe pageSeleccionCpe = new PageSeleccionCpe(driver);
		pageSeleccionCpe.clickEmisionExpress(cp);
		pageSeleccionCpe.seleccionTipoCpe(datos[3], cp);
		pageSeleccionCpe.seleccionSubtipoCpe(datos[4], cp);
		pageSeleccionCpe.seleccionTipoOperacionSunat(datos[5],cp);
		
		PageDatosEmision pageDatosEmision = new PageDatosEmision(driver);
		pageDatosEmision.completarDatosEmision(cp);
		
		PageReceptor pageReceptor = new PageReceptor(driver);
		pageReceptor.completarRazonSocial("PruebaAutomationQA", cp);

		PageDetalle pageDetalle = new PageDetalle(driver);
		pageDetalle.completarDetalleProductos(cp, datos[8], datos[9]);
		pageDetalle.botonAgregarProductosdivTreintayUno(cp);
//		pageDetalle.botonAgregarProductosdivVeintiNueve(cp);

		PageFooter pageFooter = new PageFooter(driver);
		pageFooter.clickPrevisualizacion(cp);
		pageFooter.clickEnviaraSunat(cp);
		
		System.out.println("FLUJO OK");
	}
	
	@Test
	public void Script_0086() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException {
//		EAP_0086
		String cp ="EAP_0086";
		System.out.println(cp);
		
		PageLogin pageLogin = new PageLogin(driver);
		String[] datos = leerExcel.ObtenerDatosCP(datapool,cp);
		pageLogin.login(datos[1], datos[2]);
		
		PageSeleccionCpe pageSeleccionCpe = new PageSeleccionCpe(driver);
		pageSeleccionCpe.clickEmisionExpress(cp);
		pageSeleccionCpe.seleccionTipoCpe(datos[3], cp);
		pageSeleccionCpe.seleccionSubtipoCpe(datos[4], cp);
		pageSeleccionCpe.seleccionTipoOperacionSunat(datos[5],cp);
		
		PageDatosEmision pageDatosEmision = new PageDatosEmision(driver);
		pageDatosEmision.completarDatosEmision(cp);
		
		PageReceptor pageReceptor = new PageReceptor(driver);
		pageReceptor.completarRazonSocial("PruebaAutomationQA", cp);

		PageDetalle pageDetalle = new PageDetalle(driver);
		pageDetalle.completarDetalleProductos(cp, datos[8], datos[9]);
		pageDetalle.botonAgregarProductosdivTreintayUno(cp);
//		pageDetalle.botonAgregarProductosdivVeintiNueve(cp);

		PageFooter pageFooter = new PageFooter(driver);
		pageFooter.clickPrevisualizacion(cp);
		pageFooter.clickEnviaraSunat(cp);
		
		System.out.println("FLUJO OK");
	}
	
	@Test
	public void Script_0087() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException {
//		EAP_0087
		String cp ="EAP_0087";
		System.out.println(cp);
		
		PageLogin pageLogin = new PageLogin(driver);
		String[] datos = leerExcel.ObtenerDatosCP(datapool,cp);
		pageLogin.login(datos[1], datos[2]);
		
		PageSeleccionCpe pageSeleccionCpe = new PageSeleccionCpe(driver);
		pageSeleccionCpe.clickEmisionExpress(cp);
		pageSeleccionCpe.seleccionTipoCpe(datos[3], cp);
		pageSeleccionCpe.seleccionSubtipoCpe(datos[4], cp);
		pageSeleccionCpe.seleccionTipoOperacionSunat(datos[5],cp);
		
		PageDatosEmision pageDatosEmision = new PageDatosEmision(driver);
		pageDatosEmision.completarDatosEmision(cp);
		
		PageReceptor pageReceptor = new PageReceptor(driver);
		pageReceptor.completarRazonSocial("PruebaAutomationQA", cp);

		PageDetalle pageDetalle = new PageDetalle(driver);
		pageDetalle.completarDetalleProductos(cp, datos[8], datos[9]);
		pageDetalle.botonAgregarProductosdivTreintayUno(cp);
//		pageDetalle.botonAgregarProductosdivVeintiNueve(cp);
		
		PageAgregarPropiedadesAdicionales pageAgregarPropiedadesAdicionales = new PageAgregarPropiedadesAdicionales(driver);
		pageAgregarPropiedadesAdicionales.agregarItem(cp);

		PageFooter pageFooter = new PageFooter(driver);
		pageFooter.clickPrevisualizacion(cp);
		pageFooter.clickEnviaraSunat(cp);
		
		System.out.println("FLUJO OK");
	}
	
	@Test
	public void Script_0088() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException {
//		EAP_0088
		String cp ="EAP_0088";
		System.out.println(cp);
		
		PageLogin pageLogin = new PageLogin(driver);
		String[] datos = leerExcel.ObtenerDatosCP(datapool,cp);
		pageLogin.login(datos[1], datos[2]);
		
		PageSeleccionCpe pageSeleccionCpe = new PageSeleccionCpe(driver);
		pageSeleccionCpe.clickEmisionExpress(cp);
		pageSeleccionCpe.seleccionTipoCpe(datos[3], cp);
		pageSeleccionCpe.seleccionSubtipoCpe(datos[4], cp);
		pageSeleccionCpe.seleccionTipoOperacionSunat(datos[5],cp);
		
		PageDatosEmision pageDatosEmision = new PageDatosEmision(driver);
		pageDatosEmision.completarDatosEmision(cp);
		
		PageReceptor pageReceptor = new PageReceptor(driver);
		pageReceptor.completarRazonSocial("PruebaAutomationQA", cp);

		PageDetalle pageDetalle = new PageDetalle(driver);
		pageDetalle.completarDetalleProductos(cp, datos[8], datos[9]);
		pageDetalle.botonAgregarProductosdivTreintayUno(cp);
//		pageDetalle.botonAgregarProductosdivVeintiNueve(cp);

		PageFooter pageFooter = new PageFooter(driver);
		pageFooter.clickPrevisualizacion(cp);
		pageFooter.clickEnviaraSunat(cp);
		
		System.out.println("FLUJO OK");
	}
	
	@Test
	public void Script_0089() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException {
//		EAP_0089
		String cp ="EAP_0089";
		System.out.println(cp);
		
		PageLogin pageLogin = new PageLogin(driver);
		String[] datos = leerExcel.ObtenerDatosCP(datapool,cp);
		pageLogin.login(datos[1], datos[2]);
		
		PageSeleccionCpe pageSeleccionCpe = new PageSeleccionCpe(driver);
		pageSeleccionCpe.clickEmisionExpress(cp);
		pageSeleccionCpe.seleccionTipoCpe(datos[3], cp);
		pageSeleccionCpe.seleccionSubtipoCpe(datos[4], cp);
		pageSeleccionCpe.seleccionTipoOperacionSunat(datos[5],cp);
		
		PageDatosEmision pageDatosEmision = new PageDatosEmision(driver);
		pageDatosEmision.completarDatosEmision(cp);
		
		PageReceptor pageReceptor = new PageReceptor(driver);
		pageReceptor.completarRazonSocial("PruebaAutomationQA", cp);

		PageDetalle pageDetalle = new PageDetalle(driver);
		pageDetalle.completarDetalleProductos(cp, datos[8], datos[9]);
		pageDetalle.botonAgregarProductosdivTreintayUno(cp);
//		pageDetalle.botonAgregarProductosdivVeintiNueve(cp);

		PageFooter pageFooter = new PageFooter(driver);
		pageFooter.clickPrevisualizacion(cp);
		pageFooter.clickEnviaraSunat(cp);
		
		System.out.println("FLUJO OK");
	}
	
	@Test
	public void Script_0090() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException {
//		EAP_0090
		String cp ="EAP_0090";
		System.out.println(cp);
		
		PageLogin pageLogin = new PageLogin(driver);
		String[] datos = leerExcel.ObtenerDatosCP(datapool,cp);
		pageLogin.login(datos[1], datos[2]);
		
		PageSeleccionCpe pageSeleccionCpe = new PageSeleccionCpe(driver);
		pageSeleccionCpe.clickEmisionExpress(cp);
		pageSeleccionCpe.seleccionTipoCpe(datos[3], cp);
		pageSeleccionCpe.seleccionSubtipoCpe(datos[4], cp);
		pageSeleccionCpe.seleccionTipoOperacionSunat(datos[5],cp);
		
		PageDatosEmision pageDatosEmision = new PageDatosEmision(driver);
		pageDatosEmision.completarDatosEmision(cp);
		
		PageReceptor pageReceptor = new PageReceptor(driver);
		pageReceptor.completarRazonSocial("PruebaAutomationQA", cp);

		PageDetalle pageDetalle = new PageDetalle(driver);
		pageDetalle.completarDetalleProductos(cp, datos[8], datos[9]);
		pageDetalle.botonAgregarProductosdivTreintayUno(cp);
//		pageDetalle.botonAgregarProductosdivVeintiNueve(cp);
		
		PageAgregarPropiedadesAdicionales pageAgregarPropiedadesAdicionales = new PageAgregarPropiedadesAdicionales(driver);
		pageAgregarPropiedadesAdicionales.agregarItem(cp);

		PageFooter pageFooter = new PageFooter(driver);
		pageFooter.clickPrevisualizacion(cp);
		pageFooter.clickEnviaraSunat(cp);
		
		System.out.println("FLUJO OK");
	}
	
	@Test
	public void Script_0091() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException {
//		EAP_0091
		String cp ="EAP_0091";
		System.out.println(cp);
		
		PageLogin pageLogin = new PageLogin(driver);
		String[] datos = leerExcel.ObtenerDatosCP(datapool,cp);
		pageLogin.login(datos[1], datos[2]);
		
		PageSeleccionCpe pageSeleccionCpe = new PageSeleccionCpe(driver);
		pageSeleccionCpe.clickEmisionExpress(cp);
		pageSeleccionCpe.seleccionTipoCpe(datos[3], cp);
		pageSeleccionCpe.seleccionSubtipoCpe(datos[4], cp);
		pageSeleccionCpe.seleccionTipoOperacionSunat(datos[5],cp);
		
		PageDatosEmision pageDatosEmision = new PageDatosEmision(driver);
		pageDatosEmision.completarDatosEmision(cp);
		
		PageReceptor pageReceptor = new PageReceptor(driver);
		pageReceptor.completarRazonSocial("PruebaAutomationQA", cp);

		PageDetalle pageDetalle = new PageDetalle(driver);
		pageDetalle.completarDetalleProductos(cp, datos[8], datos[9]);
		pageDetalle.botonAgregarProductosdivTreintayUno(cp);
//		pageDetalle.botonAgregarProductosdivVeintiNueve(cp);

		PageFooter pageFooter = new PageFooter(driver);
		pageFooter.clickPrevisualizacion(cp);
		pageFooter.clickEnviaraSunat(cp);
		
		System.out.println("FLUJO OK");
	}
	
	@Test
	public void Script_0092() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException {
//		EAP_0092
		String cp ="EAP_0092";
		System.out.println(cp);
		
		PageLogin pageLogin = new PageLogin(driver);
		String[] datos = leerExcel.ObtenerDatosCP(datapool,cp);
		pageLogin.login(datos[1], datos[2]);
		
		PageSeleccionCpe pageSeleccionCpe = new PageSeleccionCpe(driver);
		pageSeleccionCpe.clickEmisionExpress(cp);
		pageSeleccionCpe.seleccionTipoCpe(datos[3], cp);
		pageSeleccionCpe.seleccionSubtipoCpe(datos[4], cp);
		pageSeleccionCpe.seleccionTipoOperacionSunat(datos[5],cp);
		
		PageDatosEmision pageDatosEmision = new PageDatosEmision(driver);
		pageDatosEmision.completarDatosEmision(cp);
		
		PageReceptor pageReceptor = new PageReceptor(driver);
		pageReceptor.completarRazonSocial("PruebaAutomationQA", cp);

		PageDetalle pageDetalle = new PageDetalle(driver);
		pageDetalle.completarDetalleProductos(cp, datos[8], datos[9]);
		pageDetalle.botonAgregarProductosdivTreintayUno(cp);
//		pageDetalle.botonAgregarProductosdivVeintiNueve(cp);

		PageFooter pageFooter = new PageFooter(driver);
		pageFooter.clickPrevisualizacion(cp);
		pageFooter.clickEnviaraSunat(cp);
		
		System.out.println("FLUJO OK");
	}
	
	@Test
	public void Script_0093() throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException {
//		EAP_0093
		String cp ="EAP_0093";
		System.out.println(cp);
		
		PageLogin pageLogin = new PageLogin(driver);
		String[] datos = leerExcel.ObtenerDatosCP(datapool,cp);
		pageLogin.login(datos[1], datos[2]);
		
		PageSeleccionCpe pageSeleccionCpe = new PageSeleccionCpe(driver);
		pageSeleccionCpe.clickEmisionExpress(cp);
		pageSeleccionCpe.seleccionTipoCpe(datos[3], cp);
		pageSeleccionCpe.seleccionSubtipoCpe(datos[4], cp);
		pageSeleccionCpe.seleccionTipoOperacionSunat(datos[5],cp);
		
		PageDatosEmision pageDatosEmision = new PageDatosEmision(driver);
		pageDatosEmision.completarDatosEmision(cp);
		
		PageReceptor pageReceptor = new PageReceptor(driver);
		pageReceptor.completarRazonSocial("PruebaAutomationQA", cp);

		PageDetalle pageDetalle = new PageDetalle(driver);
		pageDetalle.completarDetalleProductos(cp, datos[8], datos[9]);
		pageDetalle.botonAgregarProductosdivTreintayUno(cp);
//		pageDetalle.botonAgregarProductosdivVeintiNueve(cp);

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
