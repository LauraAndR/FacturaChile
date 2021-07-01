package pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;

public class PageAlerta {
	private WebDriver driver;
	public PageAlerta(WebDriver driver) {
		this.driver=driver;
	}
	
	public void alertaPostDetalle() throws InterruptedException
	{
		Alert alert = driver.switchTo().alert();
		System.out.println("Mensaje: "+alert.getText());
		alert.accept();
		Thread.sleep(2000);
	}
	
//	Función para el manejo de las alertas que no permiten finalizar el flujo
	public void alertaManejoError() throws InterruptedException
	{
		Thread.sleep(2000);
		String mensaje ="";
		try {
			Alert alert = driver.switchTo().alert();
			mensaje = alert.getText();
			switch(mensaje){
				case "Falla en servicio ca4xml":
					System.out.println("Mensaje: "+alert.getText());
					alert.accept();
					Thread.sleep(2000);
					System.out.println("Datos de Boleta mal ingresados, revisar");
					break;
				case "Error en Procesamiento ":
					System.out.println("Mensaje: "+alert.getText());
					alert.accept();
					Thread.sleep(2000);
					System.out.println("Error en sistema que no permite finalizar el flujo");
					break;
				case "Error: El documento referenciado no coincide con el subtipo de documento seleccionado":
					System.out.println("Mensaje: "+alert.getText());
					alert.accept();
					Thread.sleep(2000);
					System.out.println("Error en sistema que no permite finalizar el flujo");
					break;
				case "Error al llamar al motor":
					System.out.println("Mensaje: "+alert.getText());
					alert.accept();
					Thread.sleep(2000);
					System.out.println("Error de sistema, Reintentar");
					break;	
				default:
					System.out.println("Mensaje: "+alert.getText());
					System.out.println("Otro mensaje en Alerta");
					break;
			}
			//Error: El documento referenciado no coincide con el subtipo de documento seleccionado
			//Error en procesamiento
			//Error al llamar al motor
		} catch(Exception e) {
			System.out.println("No aparece alerta de error");
		}
		Thread.sleep(2000);
	}
	
}
