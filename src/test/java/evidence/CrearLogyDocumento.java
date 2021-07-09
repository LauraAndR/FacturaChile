package evidence;

import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.WebDriver;

import common.CapturaPantalla;
import common.CrearDocEvidencia;
import common.Log;

public class CrearLogyDocumento {
	private WebDriver driver;
	public CrearLogyDocumento(WebDriver driver) {
		this.driver=driver;
	}
	CrearDocEvidencia crearDocEvidencia = new CrearDocEvidencia();
	Log log = new Log();
	CapturaPantalla capturaPantalla = new CapturaPantalla();
	
	public void CrearEvidencias(String cp) throws InterruptedException, IOException
	{
		crearDocEvidencia.crearArchivoEvidencia(cp);
		log.crearArchivoLog(cp);
	}
	
	public void CasoOk(String caso) throws IOException, InvalidFormatException, InterruptedException {
		log.modificarArchivoLog(caso, "Caso OK");
		crearDocEvidencia.modificarArchivoEvidencia(caso, "Caso OK");
		capturaPantalla.takeScreenShotTest(driver, "Caso_OK", caso);
	}
	
	public void CasoNok(String caso) throws IOException, InvalidFormatException, InterruptedException {
		log.modificarArchivoLog(caso, "Caso NOK");
		crearDocEvidencia.modificarArchivoEvidencia(caso, "Caso NOK");
		capturaPantalla.takeScreenShotTest(driver, "Caso_NOK", caso);
	}
	
	public void AgregarRegistroLog (String caso, String texto) throws IOException, InvalidFormatException, InterruptedException {
		log.modificarArchivoLog(caso, texto);
		crearDocEvidencia.modificarArchivoEvidencia(caso, texto);
		texto = texto.replace(" ","_");
		capturaPantalla.takeScreenShotTest(driver, texto, caso);
	}
}