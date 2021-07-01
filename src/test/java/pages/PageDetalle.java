package pages;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import common.CapturaPantalla;
import common.CrearDocEvidencia;

public class PageDetalle {
	private WebDriver driver;
	CapturaPantalla capturaPantalla = new CapturaPantalla();
	CrearDocEvidencia crearDocEvidencia = new CrearDocEvidencia();
	public PageDetalle(WebDriver driver) {
		this.driver=driver;
	}
	
	public void completarDetalleProductos(String caso,String cantidad, String precio) throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException{
		PageAlerta pageAlerta= new PageAlerta(driver);
		int i=0;
		int c=0;
		do {
			try {
				int hijos = driver.findElements(By.xpath("//*[@id=\"codigo_producto_datalist\"]/child::option")).size();
				String[] cod_prod = new String[hijos];
				for(int h=1;h<=hijos;h++) {
					cod_prod[h-1]=driver.findElement(By.xpath("//*[@id=\"codigo_producto_datalist\"]/child::option["+h+"]")).getAttribute("value");					
				}
				int j=(int)(Math. random()*hijos+0);
				driver.findElement(By.name("codigo_producto")).click();
				driver.findElement(By.name("codigo_producto")).clear();
				driver.findElement(By.name("codigo_producto")).sendKeys(cod_prod[j]);
				driver.findElement(By.name("codigo_producto")).sendKeys(Keys.TAB);
				Thread.sleep(2000);
				driver.findElement(By.name("detalle_producto")).clear();
				driver.findElement(By.name("detalle_producto")).sendKeys("DETALLEQA");
				Thread.sleep(2000);
				driver.findElement(By.name("cantidad_producto")).sendKeys(cantidad);
				Thread.sleep(2000);
				driver.findElement(By.name("precio_producto")).sendKeys(precio);
				Thread.sleep(2000);
				driver.findElement(By.name("precio_producto")).sendKeys(Keys.TAB);
				//agregar lo de la unidad de medida
				
				hijos = driver.findElements(By.xpath("//*[@id=\"unidad_medida_producto_datalist\"]/child::option")).size();
				String[] uni_med = new String[hijos];
				for(int h=1;h<=hijos;h++) {
					uni_med[h-1]=driver.findElement(By.xpath("//*[@id=\"unidad_medida_producto_datalist\"]/child::option["+h+"]")).getAttribute("value");					
				}
				j=(int)(Math. random()*hijos+0);
				driver.findElement(By.name("unidad_medida_producto")).click();
				driver.findElement(By.name("unidad_medida_producto")).clear();
				driver.findElement(By.name("unidad_medida_producto")).sendKeys(uni_med[j]);
				driver.findElement(By.name("unidad_medida_producto")).sendKeys(Keys.TAB);
				Thread.sleep(2000);
				i=1;
			} catch (Exception e) {
				pageAlerta.alertaManejoError();
				c++;
				if(c==3) {
					System.out.println("No fue posible completar los datos del producto");
					i=1;
				}
			}
		}while(i==0);
	}

	public void botonAgregarProductosdivTreintayUno(String caso) throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException{
		PageAlerta pageAlerta= new PageAlerta(driver);
		int i=0;
		int j=0;
		do {
			try {
				driver.findElement(By.xpath("/html/body/div[8]/div[1]/section/div[2]/div/div/div[2]/div[17]/form/div[1]/input")).click(); //*[@id=\"form_params\"]/div[31]/input
				Thread.sleep(2000); 
				pageAlerta.alertaPostDetalle();
				crearDocEvidencia.modificarArchivoEvidencia(caso, "Se agrega Detalle de producto");
				capturaPantalla.takeScreenShotTest(driver, "Agregar_Detalle_Producto",caso);
				i=1;
			} catch (Exception e) {
				pageAlerta.alertaManejoError();
				j++;
				if(j==3) {
					System.out.println("No se puede agregar detalle de producto");
					i=1;
				}
			}
		}while(i==0);
	}
	
	public void botonAgregarProductosdivVeintiNueve(String caso) throws InterruptedException, IOException, InvalidFormatException{
		PageAlerta pageAlerta= new PageAlerta(driver);
		int i=0;
		int j=0;
		do {
			try {
				driver.findElement(By.xpath("//*[@id=\"form_params\"]/div[29]/input")).click();
				Thread.sleep(2000);
				pageAlerta.alertaPostDetalle();
				crearDocEvidencia.modificarArchivoEvidencia(caso, "Se agrega Detalle de producto");
				capturaPantalla.takeScreenShotTest(driver, "Agregar_Detalle_Producto",caso);
				i=1;
			} catch (Exception e) {
				pageAlerta.alertaManejoError();
				j++;
				if(j==3) {
					System.out.println("No se puede agregar detalle de producto");
					i=1;
				}
			}
		}while(i==0);
	}
	
	public void botonAgregarProductosdivTreintayTres(String caso) throws InterruptedException, IOException, InvalidFormatException{
		PageAlerta pageAlerta= new PageAlerta(driver);
		int i=0;
		int j=0;
		do {
			try {
				driver.findElement(By.xpath("//*[@id=\"form_params\"]/div[33]/input")).click();
				Thread.sleep(2000); 
				pageAlerta.alertaPostDetalle();
				crearDocEvidencia.modificarArchivoEvidencia(caso, "Se agrega producto con Descuento");
				capturaPantalla.takeScreenShotTest(driver, "Agregar_Descuento_Producto",caso);
				i=1;
			} catch (Exception e) {
				pageAlerta.alertaManejoError();
				j++;
				if(j==3) {
					System.out.println("No se puede agregar descuento producto");
					i=1;
				}
			}
		}while(i==0);
	}
	
	public void botonAgregarProductosdivTreintayCinco(String caso) throws InterruptedException, IOException, InvalidFormatException{
		PageAlerta pageAlerta= new PageAlerta(driver);
		int i=0;
		int j=0;
		do {
			try {
				driver.findElement(By.xpath("//*[@id=\"form_params\"]/div[35]/input")).click();
				Thread.sleep(2000);
				pageAlerta.alertaPostDetalle();
				crearDocEvidencia.modificarArchivoEvidencia(caso, "Se agrega Detalle de producto");
				capturaPantalla.takeScreenShotTest(driver, "Agregar_Detalle_Producto",caso);
				i=1;
			} catch (Exception e) {
				pageAlerta.alertaManejoError();
				j++;
				if(j==3) {
					System.out.println("No se puede agregar detalle de producto");
					i=1;
				}
			}
		}while(i==0);
	}
	
	public void botonAgregarProductosdivTreintaySiete(String caso) throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException{
		PageAlerta pageAlerta= new PageAlerta(driver);
		int i=0;
		int j=0;
		do {
			try {
				driver.findElement(By.xpath("//*[@id=\"form_params\"]/div[37]/input")).click();
				Thread.sleep(2000);
				pageAlerta.alertaPostDetalle();
				crearDocEvidencia.modificarArchivoEvidencia(caso, "Se agrega Detalle de producto");
				capturaPantalla.takeScreenShotTest(driver, "Agregar_Detalle_Producto",caso);
				i=1;
			} catch (Exception e) {
				pageAlerta.alertaManejoError();
				j++;
				if(j==3) {
					System.out.println("No se puede agregar detalle de producto");
					i=1;
				}
			}
		}while(i==0);
	}
	
	public void cargoItem(String caso) throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException {
		PageAlerta pageAlerta= new PageAlerta(driver);
		int i=0;
		int j=0;
		do {
			try {
				driver.findElement(By.name("valor_cargo_u")).sendKeys("70");
				crearDocEvidencia.modificarArchivoEvidencia(caso, "Cargo en Item");
				capturaPantalla.takeScreenShotTest(driver, "Cargo en Item",caso);
				Thread.sleep(2000);
				i=1;
			} catch (Exception e) {
				pageAlerta.alertaManejoError();
				j++;
				if(j==3) {
					System.out.println("No se puede agregar detalle de producto");
					i=1;
				}
			}
		}while(i==0);
	}
	
	
	public void itemNoAfecto(String caso) throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException {
		driver.findElement(By.cssSelector("#form_params > div:nth-child(7) > div.switch.col-xs-12.content-checkbox > label:nth-child(3)")).click();
		crearDocEvidencia.modificarArchivoEvidencia(caso, "Producto NO Afecto");
		capturaPantalla.takeScreenShotTest(driver, "Item_No_Afecto",caso);
		Thread.sleep(2000);
	}
	
	public void isc(String caso) throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException {
		PageAlerta pageAlerta= new PageAlerta(driver);
		int i=0;
		int j=0;
		do {
			try {
				driver.findElement(By.cssSelector("#form_params > div:nth-child(25) > div.switch.col-xs-12.content-checkbox > label:nth-child(3)")).click();
				Select tipoIsc = new Select (driver.findElement(By.name("tipo_sistema_isc")));
				tipoIsc.selectByValue("01");
				crearDocEvidencia.modificarArchivoEvidencia(caso, "Afección ISC");
				capturaPantalla.takeScreenShotTest(driver, "Afección ISC",caso);
				Thread.sleep(2000);
				i=1;
			} catch (Exception e) {
				pageAlerta.alertaManejoError();
				j++;
				if(j==3) {
					System.out.println("No se puede agregar Afección ISC");
					i=1;
				}
			}
		}while(i==0);
	}
	
	public void cargoNivelGlobal(String caso) throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException {
		PageAlerta pageAlerta= new PageAlerta(driver);
		int i=0;
		int j=0;
		do {
			try {
				driver.findElement(By.name("valor_cargo_global_u")).sendKeys("50");
				crearDocEvidencia.modificarArchivoEvidencia(caso, "Se Agrega Cargo global");
				capturaPantalla.takeScreenShotTest(driver, "Cargo_Global", caso);
				Thread.sleep(2000);
				i=1;
			} catch (Exception e) {
				pageAlerta.alertaManejoError();
				j++;
				if(j==3) {
					System.out.println("No se puede agregar Cargo Global");
					i=1;
				}
			}
		}while(i==0);
	}
	
	public void itemconIcbper(String caso) throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException {
		PageAlerta pageAlerta= new PageAlerta(driver);
		int i=0;
		int j=0;
		do {
			try {
				driver.findElement(By.cssSelector("#form_params > div:nth-child(29) > div.switch.col-xs-12.content-checkbox > label:nth-child(3)")).click();
				driver.findElement(By.name("valor_impuesto_bp")).sendKeys("10");
				driver.findElement(By.name("valor_impuesto_bp")).sendKeys(Keys.TAB);
				crearDocEvidencia.modificarArchivoEvidencia(caso, "Item Con ICBPER");
				capturaPantalla.takeScreenShotTest(driver, "Item Con ICBPER",caso);
				Thread.sleep(2000);
				i=1;
			} catch (Exception e) {
				pageAlerta.alertaManejoError();
				j++;
				if(j==3) {
					System.out.println("No se puede agregar ICBPER");
					i=1;
				}
			}
		}while(i==0);
	}
	
	public void datosDescuento (String caso, String descuentoPesos) throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException {
		
		driver.findElement(By.name("descuento_precio_producto")).clear();
		Thread.sleep(2000);
		driver.findElement(By.name("descuento_precio_producto")).sendKeys(descuentoPesos);
		Thread.sleep(2000);
		driver.findElement(By.name("descuento_precio_producto")).sendKeys(Keys.TAB);
//		driver.findElement(By.name("total_descuentos")).clear();
//		Thread.sleep(5000);
//		driver.findElement(By.name("total_descuentos")).sendKeys(montoDescuento);
		Thread.sleep(2000);
		crearDocEvidencia.modificarArchivoEvidencia(caso, "Datos de Descuento");
		capturaPantalla.takeScreenShotTest(driver, "Datos_Descuento",caso);
	}
	
public void montoDescuento (String caso, String montoDescuento) throws InterruptedException, FileNotFoundException, InvalidFormatException, IOException {
		
		driver.findElement(By.name("total_descuentos")).clear();
		Thread.sleep(2000);
		driver.findElement(By.name("total_descuentos")).sendKeys(montoDescuento);
		Thread.sleep(2000);
		driver.findElement(By.name("total_descuentos")).sendKeys(Keys.TAB);
		Thread.sleep(2000);
		crearDocEvidencia.modificarArchivoEvidencia(caso, "Monto de Descuento");
		capturaPantalla.takeScreenShotTest(driver, "Monto_Descuento",caso);
	}
}
