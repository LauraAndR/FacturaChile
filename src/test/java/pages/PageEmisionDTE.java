package pages;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import common.CapturaPantalla;
import common.CrearDocEvidencia;
import common.FechaActual;
import common.Log;

public class PageEmisionDTE {
	private WebDriver driver;
	Log log = new Log();
	CrearDocEvidencia crearDocEvidencia = new CrearDocEvidencia();
	CapturaPantalla capturaPantalla = new CapturaPantalla();
	
	public PageEmisionDTE(WebDriver driver) {
		this.driver=driver;
	}
	
	
	// new implementation **************
	
	public void FechaEmision (String caso) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				FechaActual fechaActual = new FechaActual();
				String fecha = fechaActual.FechaHoy();
				driver.findElement(By.id("formEmitirdocumento_fechaEmision")).sendKeys(fecha);
				Thread.sleep(1000);
				String texto ="Ingreso Fecha Desde";
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				//driver.findElement(By.id("formEmitirdocumento_fechaEmision")).sendKeys(Keys.TAB);

				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible ingresar Fecha Desde");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(2000);
	}
	
	
	
	public void FechaVencimiento (String caso) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				FechaActual fechaActual = new FechaActual();
				String fecha = fechaActual.FechaVencimiento();
				driver.findElement(By.id("formEmitirdocumento_fechaVencimiento")).sendKeys(fecha);
				Thread.sleep(1000);
				String texto ="Ingreso Fecha Emisión";
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible ingresar Fecha Emisión");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(2000);
	}
	
	public void SeleccionarFormaPago (String caso, String opcion) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				Select tipoDocumento = new Select (driver.findElement(By.id("formEmitirdocumento_forma_pago")));
				tipoDocumento.selectByVisibleText(opcion);
				String texto ="Seleccion Forma de Pago";	
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible Seleccionar Forma de Pago");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(2000);
	}
	
	
	public void SeleccionarTipoCompra (String caso, String opcion) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				Select tipoDocumento = new Select (driver.findElement(By.id("formEmitirdocumento_TpoTranCompra")));
				tipoDocumento.selectByVisibleText(opcion);
				String texto ="Seleccion Tipo de Compra";	
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible Seleccionar Tipo de Compra");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(2000);
	}
	
	
	public void SeleccionarTipoVenta (String caso, String opcion) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				Select tipoDocumento = new Select (driver.findElement(By.id("formEmitirdocumento_TpoTranVenta")));
				tipoDocumento.selectByVisibleText(opcion);
				String texto ="Seleccion Tipo de Venta";	
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible Seleccionar Tipo de Venta");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(2000);
	}
	
	public void IngresoRutCliente (String caso, String rut) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				driver.findElement(By.id("formEmitirdocumento_rutReceptor")).sendKeys(rut);
				String texto ="Ingresar RUT";
				driver.findElement(By.id("btnBuscarRut")).click();
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible Ingresar RUT");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(3000);
	}
	
	public void AgregarProducto1 (String caso, String codigo, String cant) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				driver.findElement(By.id("txt_codpro")).sendKeys(codigo);
				Thread.sleep(2000);
				driver.findElement(By.id("txt_codpro")).sendKeys(Keys.TAB);
				Thread.sleep(6000);
				
				driver.findElement(By.id("txt_cantprod")).sendKeys(cant);
				Thread.sleep(2000);
				
				driver.findElement(By.id("btn_addTogrid")).click();
				
				String texto ="Ingresar Producto";
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible Ingresar Producto");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(3000);
	}
	
	public void BtnEmitirFacturaAfecta (String caso) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				driver.findElement(By.id("btn_firmar_factura")).click();
				
				String texto ="Clic botón Emitir Factura Afecta";
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible hacer clic botón Emitir Factura Afecta");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(5000);
	}
	
	public void AgregarProductoFaenamientoBovino (String caso, String codigo, String monto_base, String precio_final, String cant) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				driver.findElement(By.id("txt_codpro")).sendKeys(codigo);
				Thread.sleep(2000);
				driver.findElement(By.id("txt_codpro")).sendKeys(Keys.TAB);
				Thread.sleep(5000);
				
				driver.findElement(By.id("txt_cantprod")).sendKeys(cant);
				Thread.sleep(2000);
				
				driver.findElement(By.id("txt_MntBaseFaena")).sendKeys(monto_base);
				Thread.sleep(2000);
				
				driver.findElement(By.id("txt_PrcConsFinal")).sendKeys(precio_final);
				Thread.sleep(2000);
				
				driver.findElement(By.id("btn_addTogrid")).click();
				
				String texto ="Ingresar Producto";
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible Ingresar Producto");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(3000);
	}
	
	public void AgregarProductoIVAMargenCom (String caso, String codigo, String unidad_medida, String cant, String tasa, String precio_balon) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				driver.findElement(By.id("txt_codpro")).sendKeys(codigo);
				Thread.sleep(2000);
				driver.findElement(By.id("txt_codpro")).sendKeys(Keys.TAB);
				Thread.sleep(5000);
				
				driver.findElement(By.id("txt_medprod")).sendKeys(unidad_medida);
				Thread.sleep(2000);
				
				driver.findElement(By.id("txt_cantprod")).sendKeys(cant);
				Thread.sleep(2000);
				
				driver.findElement(By.id("txt_TasaImpGas")).sendKeys(tasa);
				Thread.sleep(2000);
				
				driver.findElement(By.id("txt_PesoGas")).sendKeys(precio_balon);
				Thread.sleep(2000);
				
				driver.findElement(By.id("btn_addTogrid")).click();
				
				String texto ="Ingresar Producto";
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible Ingresar Producto");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(3000);
	}
	
	public void AgregarProductoDescuentoPeso (String caso, String codigo, String cant, String desc) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				driver.findElement(By.id("txt_codpro")).sendKeys(codigo);
				Thread.sleep(2000);
				driver.findElement(By.id("txt_codpro")).sendKeys(Keys.TAB);
				Thread.sleep(5000);
				
				driver.findElement(By.id("txt_cantprod")).sendKeys(cant);
				Thread.sleep(2000);
				driver.findElement(By.id("txt_cantprod")).sendKeys(Keys.TAB);
				Thread.sleep(1000);
				
				driver.findElement(By.id("txt_descprod")).sendKeys(desc);
				Thread.sleep(2000);
				
				driver.findElement(By.id("btn_addTogrid")).click();
				
				String texto ="Ingresar Producto";
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible Ingresar Producto");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(3000);
	}
	
	public void AgregarProductoDescuentoPrc (String caso, String codigo, String cant, String desc) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				driver.findElement(By.id("txt_codpro")).sendKeys(codigo);
				Thread.sleep(2000);
				driver.findElement(By.id("txt_codpro")).sendKeys(Keys.TAB);
				Thread.sleep(5000);
				
				driver.findElement(By.id("txt_cantprod")).sendKeys(cant);
				Thread.sleep(2000);
				driver.findElement(By.id("txt_cantprod")).sendKeys(Keys.TAB);
				Thread.sleep(1000);
				
				driver.findElement(By.id("txt_descprod")).sendKeys(desc);
				Thread.sleep(2000);
				
				driver.findElement(By.id("checkbox_descuento")).click();
				Thread.sleep(2000);
				
				driver.findElement(By.id("btn_addTogrid")).click();
				
				String texto ="Ingresar Producto";
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible Ingresar Producto");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(3000);
	}
	
	public void AgregarProductoCodigoItem (String caso, String codigo, String cant, String cod_item, String valor_item) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				driver.findElement(By.id("txt_codpro")).sendKeys(codigo);
				Thread.sleep(2000);
				driver.findElement(By.id("txt_codpro")).sendKeys(Keys.TAB);
				Thread.sleep(5000);
				
				driver.findElement(By.id("txt_cantprod")).sendKeys(cant);
				Thread.sleep(2000);
				driver.findElement(By.id("txt_cantprod")).sendKeys(Keys.TAB);
				Thread.sleep(1000);
				
				driver.findElement(By.id("codigo_qbli")).sendKeys(cod_item);
				Thread.sleep(2000);
				
				driver.findElement(By.id("valor_qbli")).sendKeys(valor_item);
				Thread.sleep(2000);
				
				driver.findElement(By.id("btn_addTogrid")).click();
				
				String texto ="Ingresar Producto";
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible Ingresar Producto");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(3000);
	}
	
	public void AgregarDatosProducto1 (String caso, String codigo, String cant) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				driver.findElement(By.id("txt_codpro")).sendKeys(codigo);
				Thread.sleep(2000);
				driver.findElement(By.id("txt_codpro")).sendKeys(Keys.TAB);
				Thread.sleep(5000);
				
				driver.findElement(By.id("txt_cantprod")).sendKeys(cant);
				Thread.sleep(2000);
				
				String texto ="Ingresar Datos del Producto";
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible Ingresar Datos del Producto");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(3000);
	}
	
	public void BtnLimpiar (String caso) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				driver.findElement(By.id("clean_productos")).click();
				
				String texto ="Clic botón Limpiar";
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible hacer clic botón Limpiar");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(3000);
	}
	
	public void BtnEstablecerReferencias (String caso) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				driver.findElement(By.id("btnreferencia")).click();
				
				String texto ="Clic botón Establecer Referencias";
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible hacer clic botón Establecer Referencias");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(3000);
	}
	
	public void SeleccionartipoDoc (String caso, String opcion) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				Select tipoDocumento = new Select (driver.findElement(By.id("formEmitirdocumento_referenciaTipodoc")));
				tipoDocumento.selectByVisibleText(opcion);
				String texto ="Seleccion Tipo de Documento";	
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible Seleccionar Tipo de Documento");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(2000);
	}
	
	public void AgregarFolio (String caso, String folio) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				driver.findElement(By.id("formEmitirdocumento_referenciafolio")).sendKeys(folio);
				Thread.sleep(2000);
				
				String texto ="Ingresar Folio";
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible Ingresar Folio");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(3000);
	}
	
	public void FechaReferencia (String caso) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				FechaActual fechaActual = new FechaActual();
				String fecha = fechaActual.FechaHoy();
				driver.findElement(By.id("formEmitirdocumento_referenciaFechaEmision")).sendKeys(fecha);
				Thread.sleep(1000);
				String texto ="Ingreso Fecha Referencia";
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				//driver.findElement(By.id("formEmitirdocumento_fechaEmision")).sendKeys(Keys.TAB);

				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible ingresar Fecha Referencia");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(2000);
	}
	
	public void BtnAgregarReferencia (String caso) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				driver.findElement(By.id("btnAddNewreference")).click();
				
				String texto ="Clic botón Agregar Referencias";
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible hacer clic botón Agregar Referencias");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(2000);
	}
	
	public void IngresarDatosOtroDocumento (String caso, String tipo, String nombre) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				driver.findElement(By.id("codigo_referencia_otro_doc")).sendKeys(tipo);
				Thread.sleep(2000);
				
				driver.findElement(By.id("glosa_referencia_otro_doc")).sendKeys(nombre);
				Thread.sleep(2000);
				
				driver.findElement(By.id("btn_agregar_otro_doc_referencia")).click();
				Thread.sleep(2000);
				
				String texto ="Ingresar datos Otro Documento";
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible Ingresar datos Otro Documento");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(2000);
	}
	
	public void ClickCheckboxIndicadorReferenciaGlobal (String caso) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				driver.findElement(By.id("checkbox_indGlobal")).click();
				
				String texto ="Clic en checkbox Indicador de Referencia Global";
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible hacer clic en checkbox Indicador de Referencia Global");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(2000);
	}
	
	public void BtnAgregarTransporte (String caso) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				driver.findElement(By.id("btntransporte")).click();
				
				String texto ="Clic botón Agregar Transporte";
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible hacer clic botón Agregar Transporte");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(2000);
	}
	
	public void IngresoDatosTransporte (String caso, String patente, String rut, String nombre, String direccion, String comuna, String region, String tipoDespacho) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				driver.findElement(By.id("formEmitirdocumento_transportePatente")).sendKeys(patente);
				Thread.sleep(1000);
				driver.findElement(By.id("formEmitirdocumento_transporteRutTransportista")).sendKeys(rut);
				Thread.sleep(1000);
				driver.findElement(By.id("formEmitirdocumento_transporterutChofer")).sendKeys(rut);
				Thread.sleep(1000);
				driver.findElement(By.id("formEmitirdocumento_transporteNombreChofer")).sendKeys(nombre);
				Thread.sleep(1000);
				driver.findElement(By.id("formEmitirdocumento_transporteDireccionDestino")).sendKeys(direccion);
				Thread.sleep(1000);
				driver.findElement(By.id("formEmitirdocumento_transporteComuna")).sendKeys(comuna);
				Thread.sleep(1000);
				driver.findElement(By.id("formEmitirdocumento_transporteCuidad")).sendKeys(comuna);
				Thread.sleep(1000);
				
				Select region2 = new Select (driver.findElement(By.id("formEmitirdocumento_transporteRegion")));
				region2.selectByVisibleText(region);
				Thread.sleep(2000);
				
				Select tipoDespacho2 = new Select (driver.findElement(By.id("formEmitirdocumento_guiaDespachoTipoDespacho")));
				tipoDespacho2.selectByVisibleText(tipoDespacho);
				Thread.sleep(2000);
	
				String texto ="Ingresar datos de transporte";
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible Ingresar datos de transporte");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(2000);
	}
	
	public void IngresoParametrosAdicionales (String caso, String etiqueta, String rut) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				driver.findElement(By.id("formEmitirdocumento_eti2")).sendKeys(etiqueta);
				Thread.sleep(1000);
				driver.findElement(By.id("formEmitirdocumento_eti3")).sendKeys(rut);
				Thread.sleep(1000);
				
				String texto ="Ingresar parámetros adicionales";
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible Ingresar parámetros adicionales");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(2000);
	}
	
	
	public void IngresoParametrosAgrupadosAdicionales (String caso, String usuario, String tipoPago) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				Select lista_usuario = new Select (driver.findElement(By.id("formEmitirdocumento_PARAMETRO2")));
				lista_usuario.selectByVisibleText(usuario);
				Thread.sleep(2000);
				
				Select lista_tipoPago = new Select (driver.findElement(By.id("formEmitirdocumento_PARAMETRO3")));
				lista_tipoPago.selectByVisibleText(tipoPago);
				Thread.sleep(2000);
				
				String texto ="Ingresar parámetros agrupados adicionales";
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible Ingresar parámetros agrupados adicionales");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(2000);
	}
	
	public void IngresoObservaciones (String caso, String observacion) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				driver.findElement(By.id("formEmitirdocumento_obeservaciones")).sendKeys(observacion);
				Thread.sleep(1000);
				
				String texto ="Ingresar observaciones";
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible Ingresar observaciones");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(2000);
	}
	
	public void AgregarProductoCheckboxExento (String caso, String codigo, String cant) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				driver.findElement(By.id("txt_codpro")).sendKeys(codigo);
				Thread.sleep(2000);
				driver.findElement(By.id("txt_codpro")).sendKeys(Keys.TAB);
				Thread.sleep(5000);
				
				driver.findElement(By.id("txt_cantprod")).sendKeys(cant);
				Thread.sleep(2000);
				
				driver.findElement(By.id("checkbox_productoExento")).click();
				Thread.sleep(1000);
				
				driver.findElement(By.id("btn_addTogrid")).click();
				
				String texto ="Ingresar Producto";
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible Ingresar Producto");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(3000);
	}
	
	public void SwithSIDescuentoRecargo (String caso) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {		
				driver.findElement(By.xpath("//*[@id=\"columnaDescuentosExento\"]/div/div")).click();
				Thread.sleep(1000);
				
				String texto ="Cambiar swith en Descuento/Recargo";
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("Cambiar swith en Descuento/Recargo");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(3000);
	}
	
	public void IngresoDescuentoPrc (String caso, String valor, String motivo) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				driver.findElement(By.id("radio_desc")).click();
				Thread.sleep(1000);
				
				driver.findElement(By.id("radio_porcentaje_desc_rec")).click();
				Thread.sleep(1000);
				
				driver.findElement(By.id("valor_desc_rec")).sendKeys(valor);
				Thread.sleep(1000);
				
				driver.findElement(By.id("motivo_desc_rec")).sendKeys(motivo);
				Thread.sleep(1000);
				
				driver.findElement(By.id("btn_agregar_desc_rec")).click();
				Thread.sleep(2000);
								
				String texto ="Ingresar Descuento";
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible Ingresar Descuento");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(2000);
	}
	
	public void IngresoDescuentoPeso (String caso, String valor, String motivo) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				driver.findElement(By.id("radio_desc")).click();
				Thread.sleep(1000);
				
				driver.findElement(By.id("radio_absoluto_desc_rec")).click();
				Thread.sleep(1000);
				
				driver.findElement(By.id("valor_desc_rec")).sendKeys(valor);
				Thread.sleep(1000);
				
				driver.findElement(By.id("motivo_desc_rec")).sendKeys(motivo);
				Thread.sleep(1000);
				
				driver.findElement(By.id("btn_agregar_desc_rec")).click();
				Thread.sleep(2000);
								
				String texto ="Ingresar Descuento";
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible Ingresar Descuento");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(2000);
	}
	
	public void IngresoRecargoPrc (String caso, String valor, String motivo) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				driver.findElement(By.id("radio_reca")).click();
				Thread.sleep(1000);
				
				driver.findElement(By.id("radio_porcentaje_desc_rec")).click();
				Thread.sleep(1000);
				
				driver.findElement(By.id("valor_desc_rec")).sendKeys(valor);
				Thread.sleep(1000);
				
				driver.findElement(By.id("motivo_desc_rec")).sendKeys(motivo);
				Thread.sleep(1000);
				
				driver.findElement(By.id("btn_agregar_desc_rec")).click();
				Thread.sleep(2000);
								
				String texto ="Ingresar Descuento";
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible Ingresar Descuento");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(2000);
	}
	
	public void IngresoRecargoPeso (String caso, String valor, String motivo) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				driver.findElement(By.id("radio_reca")).click();
				Thread.sleep(1000);
				
				driver.findElement(By.id("radio_absoluto_desc_rec")).click();
				Thread.sleep(1000);
				
				driver.findElement(By.id("valor_desc_rec")).sendKeys(valor);
				Thread.sleep(1000);
				
				driver.findElement(By.id("motivo_desc_rec")).sendKeys(motivo);
				Thread.sleep(1000);
				
				driver.findElement(By.id("btn_agregar_desc_rec")).click();
				Thread.sleep(2000);
								
				String texto ="Ingresar Descuento";
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible Ingresar Descuento");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(2000);
	}
	
	public void ClickEliminar (String caso) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				driver.findElement(By.xpath("//*[@id=\"formEmitirdocumento_tablaTotales\"]/tbody/tr[4]/td[2]/a")).click();
				String texto ="Click en Eliminar";
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible dar clic en Eliminar");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(3000);
	}
	
	public void SwithSIDescuentoNeto (String caso) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {		
				driver.findElement(By.xpath("//*[@id=\"columnaDescuentosNeto\"]/div/div/div/label[2]")).click();
				Thread.sleep(1000);
				
				String texto ="Cambiar swith en Descuento/Recargo";
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("Cambiar swith en Descuento/Recargo");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(3000);
	}
	
	public void IngresoDescuentoPrcNeto (String caso, String valor, String motivo) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				driver.findElement(By.id("radio_desc")).click();
				Thread.sleep(1000);
				
				driver.findElement(By.id("radio_porcentaje_desc_rec")).click();
				Thread.sleep(1000);
				
				driver.findElement(By.id("valor_desc_rec")).sendKeys(valor);
				Thread.sleep(1000);
				
				driver.findElement(By.id("motivo_desc_rec")).sendKeys(motivo);
				Thread.sleep(1000);
				
				driver.findElement(By.id("btn_agregar_desc_rec")).click();
				Thread.sleep(2000);
								
				String texto ="Ingresar Descuento";
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible Ingresar Descuento");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(2000);
	}
	
	public void IngresoDescuentoPesoNeto (String caso, String valor, String motivo) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				driver.findElement(By.id("radio_desc")).click();
				Thread.sleep(1000);
				
				driver.findElement(By.id("radio_absoluto_desc_rec")).click();
				Thread.sleep(1000);
				
				driver.findElement(By.id("valor_desc_rec")).sendKeys(valor);
				Thread.sleep(1000);
				
				driver.findElement(By.id("motivo_desc_rec")).sendKeys(motivo);
				Thread.sleep(1000);
				
				driver.findElement(By.id("btn_agregar_desc_rec")).click();
				Thread.sleep(2000);
								
				String texto ="Ingresar Descuento";
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible Ingresar Descuento");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(2000);
	}
	
	public void IngresoRecargoPrcNeto (String caso, String valor, String motivo) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				driver.findElement(By.id("radio_reca")).click();
				Thread.sleep(1000);
				
				driver.findElement(By.id("radio_porcentaje_desc_rec")).click();
				Thread.sleep(1000);
				
				driver.findElement(By.id("valor_desc_rec")).sendKeys(valor);
				Thread.sleep(1000);
				
				driver.findElement(By.id("motivo_desc_rec")).sendKeys(motivo);
				Thread.sleep(1000);
				
				driver.findElement(By.id("btn_agregar_desc_rec")).click();
				Thread.sleep(2000);
								
				String texto ="Ingresar Recargo";
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible Ingresar Recargo");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(2000);
	}
	
	public void IngresoRecargoPesoNeto (String caso, String valor, String motivo) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				driver.findElement(By.id("radio_reca")).click();
				Thread.sleep(1000);
				
				driver.findElement(By.id("radio_absoluto_desc_rec")).click();
				Thread.sleep(1000);
				
				driver.findElement(By.id("valor_desc_rec")).sendKeys(valor);
				Thread.sleep(1000);
				
				driver.findElement(By.id("motivo_desc_rec")).sendKeys(motivo);
				Thread.sleep(1000);
				
				driver.findElement(By.id("btn_agregar_desc_rec")).click();
				Thread.sleep(2000);
								
				String texto ="Ingresar Recargo";
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible Ingresar Recargo");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(2000);
	}
	
	
	public void ClickLimpiar (String caso) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				driver.findElement(By.id("btnLimpiarFormulario")).click();
				String texto ="Click en Limpiar";
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible dar clic en Limpiar");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(3000);
	}
	
	/*
	 * Factura Exenta Electrónica
	 */
	
	public void AgregarProducto2 (String caso, String codigo, String cant) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				driver.findElement(By.id("txt_codpro")).sendKeys(codigo);
				Thread.sleep(1000);
				driver.findElement(By.id("txt_codpro")).sendKeys(Keys.TAB);
				Thread.sleep(5000);
				
				driver.findElement(By.id("txt_cantprod")).sendKeys(cant);
				Thread.sleep(2000);
				
				driver.findElement(By.id("btn_addTogrid")).click();
				
				String texto ="Ingresar Producto";
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible Ingresar Producto");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(2000);
	}
	
	
	public void AgregarReferencia (String caso, String folio) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				driver.findElement(By.xpath("//*[@id=\"formEmitirdocumento_referenciaTipodoc\"]/option[2]")).click();
				Thread.sleep(1000);          
				driver.findElement(By.xpath("//*[@id=\"formEmitirdocumento_CodRef\"]/option[2]")).click();
				Thread.sleep(1000); 
				driver.findElement(By.id("formEmitirdocumento_referenciafolio")).sendKeys(folio);
				Thread.sleep(1000);
				String texto ="Ingresar Referencia";
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible ingresar Referencia");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(2000);
	}
	
	public void estadoEspecificacion (String caso, String especificacion) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				
				driver.findElement(By.xpath("//*[@id=\"primerPaso\"]/div[4]/div[3]/div/div[6]/div/span[1]/span[1]/span/ul/li/input")).click();
				Thread.sleep(1000);          
				driver.findElement(By.xpath("//*[text()= '"+ especificacion + "']")).click();//para enviar un texto a un campo y lo puedan seleccionar
				Thread.sleep(1000); 
				String texto ="Seleccion Especificación";
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible ingresar estado Especificación");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(2000);
	}
	
	public void BtnEmitirFacturaExenta (String caso) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				driver.findElement(By.id("btn_firmar_factura")).click();
				
				String texto ="Clic botón Emitir Factura Afecta";
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible hacer clic botón Emitir Factura Afecta");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(5000);
	}
	
	public void BtnEmitirNotaCredito (String caso) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				driver.findElement(By.id("btn_firmar_factura")).click();
				
				String texto ="Clic botón Emitir Nota Crédito";
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible hacer clic botón Emitir Nota Crédito");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(5000);
	}
	
	public void BtnEmitirNotaDebito (String caso) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				driver.findElement(By.id("btn_firmar_factura")).click();
				
				String texto ="Clic botón Emitir Nota Crédito";
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible hacer clic botón Emitir Nota Crédito");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(5000);
	}
	
	public void SeleccionarCodReferencia (String caso, String opcion) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				Select codReferencia = new Select (driver.findElement(By.id("formEmitirdocumento_CodRef")));
				codReferencia.selectByVisibleText(opcion);
				String texto ="Seleccion código de referencia";	
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible Seleccionar código de referencia");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(1000);
	}
	
	public void CheckboxFacturaTuristica (String caso) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				driver.findElement(By.id("formEmitirdocumento_TipoFactEsp1")).click();
				
				String texto ="Clic checkbox Factura Turística";
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible hacer clic checkbox Factura Turística");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(1000);
	}
	
	
	public void CheckboxIndicadorNoRebaja (String caso) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				driver.findElement(By.id("id=\"formEmitirdocumento_IndNoRebaja\"")).click();
				
				String texto ="Clic checkbox Indicador de no rebaja";
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible hacer clic checkbox Indicador de no rebaja");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(1000);
	}
	
	public void AgregarNumeroIdExtranjero (String caso, String num) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				driver.findElement(By.id("formEmitirdocumento_NumId")).sendKeys(num);
	
				String texto ="Ingresar Número de identificación Extranjero";
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible Ingresar Número de identificación Extranjero");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(1000);
	}
	
	public void SeleccionarTipoDoctoTurista (String caso, String opcion) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				Select tipoDocumento = new Select (driver.findElement(By.id("formEmitirdocumento_TipoDocID")));
				tipoDocumento.selectByVisibleText(opcion);
				String texto ="Seleccion Tipo Documento Turista";	
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible Seleccionar Tipo Documento Turista");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(1000);
	}
	
	public void AgregarGiroExtranjero (String caso, String giro) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				driver.findElement(By.id("formEmitirdocumento_giro")).sendKeys(giro);

				String texto ="Ingresar giro Extranjero";
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible Ingresar giro Extranjero");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(1000);
	}
	
	public void AgregarDireccionExtranjero (String caso, String direccion) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				driver.findElement(By.id("formEmitirdocumento_direccion")).sendKeys(direccion);

				String texto ="Ingresar Dirección Extranjero";
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible Ingresar dirección Extranjero");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(1000);
	}
	
	public void AgregarComunaExtranjero (String caso, String comuna) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				driver.findElement(By.id("formEmitirdocumento_comuna")).sendKeys(comuna);

				String texto ="Ingresar Comuna Extranjero";
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible Ingresar Comuna Extranjero");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(1000);
	}
	
	public void AgregarCiudadExtranjero (String caso, String ciudad) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				driver.findElement(By.id("formEmitirdocumento_CuidadRecep")).sendKeys(ciudad);

				String texto ="Ingresar ciudad Extranjero";
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible Ingresar ciudad Extranjero");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(1000);
	}
	
	public void AgregarProductoUnidadMedida (String caso, String codigo, String cant, String uni) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				driver.findElement(By.id("txt_codpro")).sendKeys(codigo);
				Thread.sleep(2000);
				driver.findElement(By.id("txt_codpro")).sendKeys(Keys.TAB);
				Thread.sleep(6000);
				
				driver.findElement(By.id("txt_cantprod")).sendKeys(cant);
				Thread.sleep(2000);
				
				driver.findElement(By.id("txt_medprod")).sendKeys(uni);
				Thread.sleep(2000);
				
				driver.findElement(By.id("btn_addTogrid")).click();
				
				String texto ="Ingresar Producto";
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible Ingresar Producto");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(3000);
	}
	
	public void AgregarProductoConRetencionTotal (String caso, String codigo, String cant) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				driver.findElement(By.id("txt_codpro")).sendKeys(codigo);
				Thread.sleep(2000);
				driver.findElement(By.id("txt_codpro")).sendKeys(Keys.TAB);
				Thread.sleep(6000);
				
				driver.findElement(By.id("txt_cantprod")).sendKeys(cant);
				Thread.sleep(1000);
				
				driver.findElement(By.id("checkboxretenciontotal")).click();
				Thread.sleep(1000);
				
				driver.findElement(By.id("btn_addTogrid")).click();
				
				String texto ="Ingresar Producto";
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible Ingresar Producto");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(1000);
	}
	
	
	public void AgregarProductoConIvaExento (String caso, String codigo, String cant) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				driver.findElement(By.id("txt_codpro")).sendKeys(codigo);
				Thread.sleep(2000);
				driver.findElement(By.id("txt_codpro")).sendKeys(Keys.TAB);
				Thread.sleep(6000);
				
				driver.findElement(By.id("txt_cantprod")).sendKeys(cant);
				Thread.sleep(1000);
				
				driver.findElement(By.id("checkbox_productoExento")).click();
				Thread.sleep(1000);
				
				driver.findElement(By.id("btn_addTogrid")).click();
				
				String texto ="Ingresar Producto";
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible Ingresar Producto");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(1000);
	}
	
	public void SeleccionarIndicadorTraslado (String caso, String opcion) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				Select indicador = new Select (driver.findElement(By.id("formEmitirdocumento_guiaDespachoindTransl")));
				indicador.selectByVisibleText(opcion);
				String texto ="Seleccion Indicador de traslado";	
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible Seleccionar Indicador de traslado");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(2000);
	}
	
	
	public void SeleccionarCodigoTraslado (String caso, String opcion) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				Select codigo = new Select (driver.findElement(By.id("formEmitirdocumento_CdgTraslado")));
				codigo.selectByVisibleText(opcion);
				String texto ="Seleccion código de traslado";	
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible Seleccionar código de traslado");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(2000);
	}
	
	public void SeleccionarTipoServicioDatosBoleta (String caso, String opcion) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				Select tipoServicio = new Select (driver.findElement(By.id("formEmitirdocumento_boletaTiposerv")));
				tipoServicio.selectByVisibleText(opcion);
				String texto ="Seleccion Tipo de Servicio";	
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible Seleccionar Tipo de Servicio");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(2000);
	}
	
	
	
	public void BtnEmitirGuiaDespacho (String caso) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				driver.findElement(By.id("btn_firmar_factura")).click();
				
				String texto ="Clic botón Emitir Guia de Despacho";
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible hacer clic botón Emitir Guia de Despacho");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(4000);
	}
	
	// Ricardo 05-10-2021
	
	public void BtnAgregarAduana (String caso) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				driver.findElement(By.id("btnAduana")).click();
				
				String texto ="Clic botón Agregar Aduana";
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible hacer clic botón Agregar Aduana");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(5000);
	}
	
	public void SeleccionarModalidadAduana (String caso, String opcion) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				Select modalidad = new Select (driver.findElement(By.id("formEmitirdocumento_CodModVenta")));
				modalidad.selectByVisibleText(opcion);
				String texto ="Seleccion Modalidad";	
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible Seleccionar Modalidad");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(2000);
	}
	
	public void SeleccionarClausulaAduana (String caso, String opcion) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				Select clausula = new Select (driver.findElement(By.id("formEmitirdocumento_CodClauVenta")));
				clausula.selectByVisibleText(opcion);
				String texto ="Seleccion cláusula";	
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible Seleccionar cláusula");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(2000);
	}
	
	public void IngresoTotalAduana (String caso, String total) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				driver.findElement(By.id("formEmitirdocumento_TotClauVenta")).sendKeys(total);
				String texto ="Ingresar Total";
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible Ingresar Total");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(1000);
	}
	
	public void TipoBultoAduana (String caso, String opcion) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				//driver.findElement(By.xpath("//*[@id=\"primerPaso\"]/div[4]/div[3]/div/div[6]/div/span[1]/span[1]/span/ul/li/input")).click();
				driver.findElement(By.xpath("select2-formEmitirdocumento_CodTpoBultos-container")).click();
				Thread.sleep(1000);          
				driver.findElement(By.xpath("//*[text()= '"+ opcion + "']")).click();//para enviar un texto a un campo y lo puedan seleccionar     
	
				String texto ="Seleccion tipo bulto";
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible ingresar tipo bulto");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(2000);
	}
	
	
	public void IngresoCantidadMarcaAduana (String caso, String cantidad, String marca) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				driver.findElement(By.id("formEmitirdocumento_CantBultos")).sendKeys(cantidad);
				driver.findElement(By.id("formEmitirdocumento_Marcas")).sendKeys(marca);
				Thread.sleep(2000);
				driver.findElement(By.id("add_bultoAduana")).click();
				
				String texto ="Ingresar Cantidad y Marca";
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible Ingresar  Cantidad y Marca");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(3000);
	}
	
	public void IngresoTotalBultosAduana (String caso, String total) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				driver.findElement(By.id("formEmitirdocumento_TotBultos")).sendKeys(total);
				String texto ="Ingresar Total";
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible Ingresar Total");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(3000);
	}
	
	// Factura de Exportación
	
	public void SeleccionarIndicadorServicio (String caso, String opcion) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				Select indicador = new Select (driver.findElement(By.id("formEmitirdocumento_IndServicio")));
				indicador.selectByVisibleText(opcion);
				String texto ="Seleccion Indicador de servicio";	
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible Seleccionar Indicador de servicio");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(1000);
	}
	
	public void IngresoRazonSocialCliente (String caso, String razonSocial) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				driver.findElement(By.id("brazonsocial")).sendKeys(razonSocial);
				String texto ="Ingresar Razón social";
				driver.findElement(By.id("btnBuscarRz")).click();
				driver.findElement(By.xpath("//*[@id=\"modal_buscar_rz\"]/div[2]/div/div[2]/div/table/tbody/tr/td[1]")).click();
				
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible Ingresar Razón social");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(1000);
	}
	
	
	public void IngresoDatosRazonSocialCliente (String caso, String ciudad) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				driver.findElement(By.id("formEmitirdocumento_CuidadRecep")).sendKeys(ciudad);
				String texto ="Ingresar ciudad";
				
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible Ingresar ciudad");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(1000);
	}
	
	public void IngresoDatosTransporte2 (String caso, String direccion, String comuna, String region, String tipoDespacho) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				driver.findElement(By.id("formEmitirdocumento_transporteDireccionDestino")).sendKeys(direccion);
				Thread.sleep(1000);
				driver.findElement(By.id("formEmitirdocumento_transporteComuna")).sendKeys(comuna);
				Thread.sleep(1000);
				driver.findElement(By.id("formEmitirdocumento_transporteCuidad")).sendKeys(comuna);
				Thread.sleep(1000);
				
				Select region2 = new Select (driver.findElement(By.id("formEmitirdocumento_transporteRegion")));
				region2.selectByVisibleText(region);
				Thread.sleep(1000);
				
				Select tipoDespacho2 = new Select (driver.findElement(By.id("formEmitirdocumento_guiaDespachoTipoDespacho")));
				tipoDespacho2.selectByVisibleText(tipoDespacho);
				Thread.sleep(1000);
	
				String texto ="Ingresar datos de transporte";
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible Ingresar datos de transporte");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(1000);
	}
	
	
	public void IngresoPaisReceptor (String caso, String pais) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				driver.findElement(By.xpath("//*[@id=\"select2-formEmitirdocumento_CodPaisRecep-container\"]/span")).click();
				
				driver.findElement(By.xpath("/html/body/span/span/span[1]/input")).sendKeys(pais);
				driver.findElement(By.xpath("/html/body/span/span/span[1]/input")).sendKeys(Keys.chord(Keys.ENTER));
				
				String texto ="Ingresar país receptor";
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible Ingresar país receptor");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(1000);
	}
	
	public void IngresoPuertoEmbarque (String caso, String puerto) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				driver.findElement(By.xpath("//*[@id=\"select2-formEmitirdocumento_CodPtoEmbarque-container\"]/span")).click();
				driver.findElement(By.xpath("/html/body/span/span/span[1]/input")).sendKeys(puerto);
				driver.findElement(By.xpath("/html/body/span/span/span[1]/input")).sendKeys(Keys.chord(Keys.ENTER));
				
				String texto ="Ingresar país receptor";
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible Ingresar país receptor");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(1000);
	}
	
	
	public void IngresoPuertoDesembarque (String caso, String puerto) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				driver.findElement(By.xpath("//*[@id=\"select2-formEmitirdocumento_CodPtoDesemb-container\"]")).click();
				driver.findElement(By.xpath("/html/body/span/span/span[1]/input")).sendKeys(puerto);
				driver.findElement(By.xpath("/html/body/span/span/span[1]/input")).sendKeys(Keys.chord(Keys.ENTER));
				
				String texto ="Ingresar país receptor";
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible Ingresar país receptor");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(1000);
	}
	
	public void AgregarProductoExportación (String caso, String codigo, String cant) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				driver.findElement(By.id("txt_codpro")).sendKeys(codigo);
				Thread.sleep(1000);
				driver.findElement(By.id("txt_codpro")).sendKeys(Keys.TAB);
				Thread.sleep(6000);
				
				driver.findElement(By.id("txt_cantprod")).sendKeys(cant);
				Thread.sleep(1000);
				
				driver.findElement(By.id("btn_addTogridExport")).click();
				
				String texto ="Ingresar Producto";
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible Ingresar Producto");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(1000);
	}
	
	
	public void SeleccionarViaAduana (String caso, String opcion) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				Select indicador = new Select (driver.findElement(By.id("formEmitirdocumento_CodViaTransp")));
				indicador.selectByVisibleText(opcion);
				String texto ="Seleccion Vía";	
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible Seleccionar Vía");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(1000);
	}
	
	public void IngresoTipoMoneda (String caso, String moneda) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				driver.findElement(By.xpath("//*[@id=\"select2-formEmitirdocumento_TpoMoneda-container\"]/span")).click();
				driver.findElement(By.xpath("/html/body/span/span/span[1]/input")).sendKeys(moneda);
				driver.findElement(By.xpath("/html/body/span/span/span[1]/input")).sendKeys(Keys.chord(Keys.ENTER));
				
				String texto ="Ingresar tipo moneda";
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible Ingresar tipo moneda");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(1000);
	}
	
	
	public void IngresoSegundaMoneda (String caso, String tipoCambio, String TotalOtraMoneda, String TotalExentoOtraMoneda) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				
				driver.findElement(By.id("formEmitirdocumento_TpoCambio")).sendKeys(tipoCambio);
				
				driver.findElement(By.id("formEmitirdocumento_MntTotOtrMnda")).sendKeys(TotalOtraMoneda);
				
				driver.findElement(By.id("formEmitirdocumento_MntExeOtrMnda")).sendKeys(TotalExentoOtraMoneda);
				
				String texto ="Ingresar segunda moneda";
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible Ingresar segunda moneda");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(1000);
	}
	
	public void AgregarIndormacionBulto (String caso, String tipo, String cantidad, String marca) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				driver.findElement(By.xpath("//*[@id=\"select2-formEmitirdocumento_CodTpoBultos-container\"]/span")).click();
				driver.findElement(By.xpath("/html/body/span/span/span[1]/input")).sendKeys(tipo);
				driver.findElement(By.xpath("/html/body/span/span/span[1]/input")).sendKeys(Keys.chord(Keys.ENTER));
				
				driver.findElement(By.id("formEmitirdocumento_CantBultos")).sendKeys(cantidad);
				
				driver.findElement(By.id("formEmitirdocumento_Marcas")).sendKeys(marca);
				
				driver.findElement(By.id("add_bultoAduana")).click();
				
				String texto ="Ingresar Información de bulto";
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible Ingresar información de bulto");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(1000);
	}
	
	public void AgregarProductoExportacionCodigoItem (String caso, String codigo, String cant, String cod_item, String valor_item) throws InterruptedException {
		int i=0;
		int j=0;
		do {
			try {
				driver.findElement(By.id("txt_codpro")).sendKeys(codigo);
				Thread.sleep(2000);
				driver.findElement(By.id("txt_codpro")).sendKeys(Keys.TAB);
				Thread.sleep(5000);
				
				driver.findElement(By.id("txt_cantprod")).sendKeys(cant);
				Thread.sleep(1000);
				//driver.findElement(By.id("txt_cantprod")).sendKeys(Keys.TAB);
				//Thread.sleep(1000);
				
				driver.findElement(By.id("codigo_qbli")).sendKeys(cod_item);
				Thread.sleep(1000);
				
				driver.findElement(By.id("valor_qbli")).sendKeys(valor_item);
				Thread.sleep(1000);
				
				driver.findElement(By.xpath("//*[@id=\"btn_addTogridExport\"]")).click();
				
				String texto ="Ingresar Producto";
				log.modificarArchivoLog(caso,texto);
				crearDocEvidencia.modificarArchivoEvidencia(caso,texto);
				texto=texto.replace(" ","_");
				capturaPantalla.takeScreenShotTest(driver,texto, caso);
				i=1;
			}catch (Exception e) {
				// TODO: handle exception
				j++;
				if(j==3) {
					System.out.println("No fue posible Ingresar Producto");
					i=1;
				}
			}
		}while(i==0);
		Thread.sleep(3000);
	}
}
