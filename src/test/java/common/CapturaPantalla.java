package common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;


public class CapturaPantalla {
	
	public void takeScreenShotTest(WebDriver driver, String imageName, String caso) throws FileNotFoundException, IOException, InvalidFormatException, InterruptedException {
		TakesScreenshot captura = (TakesScreenshot) driver; //castear de un WebDriver a TakesScreenshot
		File archivoCaptura = captura.getScreenshotAs(OutputType.FILE);
		String path = String.format("%s%s-%s-%s.jpg", Configuration.SCREENSHOT_DIR, caso,"Captura",imageName);
		archivoCaptura.renameTo(new File(path));
		Thread.sleep(1000);
		
//		return path;
//		Codigo que permite tomar el nombre del archivo (variable path) y luego insertarla en el documento de evidencias
		XWPFDocument doc = new XWPFDocument(new FileInputStream(Configuration.EVIDENCIA_DIR+caso+".docx"));
		XWPFParagraph contenido = doc.createParagraph();
		contenido = doc.createParagraph();
		contenido.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun contenidoRun = contenido.createRun();
		contenidoRun = contenido.createRun();
		String imgFile = path;	
		System.out.println(imgFile);
		InputStream img = new FileInputStream(imgFile);
		contenidoRun.addBreak();
		contenidoRun.addPicture(img, XWPFDocument.PICTURE_TYPE_JPEG, imgFile, Units.toEMU(400), Units.toEMU(400));
		FileOutputStream docEvidencia = new FileOutputStream(new File(Configuration.EVIDENCIA_DIR+caso+".docx"));
		doc.write(docEvidencia);
		docEvidencia.close();
		img.close();
		System.out.println("Imagen Agregada");
		
	}
}



//Actions builder = new Actions(this.getAutomator().getDriver()); // verificar?
////Actions builder = new Actions(this.getDriver());
//
//builder.moveToElement((WebElement) menuBar)  // museOver (Mueve el mouse al elemento especificado)
//	.pause(1000) // pausa x segundos en el elemento
//	.click((WebElement)this.getAutomator().find(By.id("wp-admin-bar-view-site")))  // Verificar?
//	.build() // build y perfom siempre van
//	.perform(); //todos esos métodos son propios de la clase Actions