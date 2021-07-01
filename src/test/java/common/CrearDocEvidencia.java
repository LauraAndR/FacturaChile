package common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class CrearDocEvidencia {
	
	public void crearArchivoEvidencia(String caso) throws IOException {
//		DOCUMENTO EN BLANCO
		XWPFDocument doc = new XWPFDocument();
//		ESCRIBIR EL DOCUMENTO EN EL SISTEMA
		FileOutputStream docEvidencia = new FileOutputStream(new File(Configuration.EVIDENCIA_DIR+caso+".docx"));
//		CREAR EL PARRAFO
		XWPFParagraph titulo = doc.createParagraph();
		titulo.setAlignment(ParagraphAlignment.CENTER);
//		EJECUTA ACCIONES DENTRO DEL PARRAFO
		XWPFRun tituloRun = titulo.createRun();
		tituloRun.setBold(true);
		tituloRun.setFontSize(20);
		tituloRun.setText("Evidencia "+caso);
//		ESCRIBE EN ARCHIVO
		doc.write(docEvidencia);
//		CIERRA EL ARCHIVO
		docEvidencia.close();	
		doc.close();
	}
	
	public void modificarArchivoEvidencia(String caso, String paso) throws IOException {
		XWPFDocument doc = new XWPFDocument(new FileInputStream(Configuration.EVIDENCIA_DIR+caso+".docx"));
		FileOutputStream docEvidencia = new FileOutputStream(new File(Configuration.EVIDENCIA_DIR+caso+".docx"));
		XWPFParagraph contenido = doc.createParagraph();
		contenido.setAlignment(ParagraphAlignment.LEFT);
		XWPFRun contenidoRun = contenido.createRun();
		contenidoRun.setBold(false);
		contenidoRun.setFontSize(14);
		contenidoRun.setText(paso);
		doc.write(docEvidencia);
		docEvidencia.close();	
	}
	
/*	Función no implementada, se debe buscar la forma de enviar la variable path de la clase "CapturaPantalla" y esta función la reciba
	como parámetro de entrada
*/
	public void agregarImagenEvidencia(String caso, String imagen) throws IOException, InvalidFormatException {
		XWPFDocument doc = new XWPFDocument(new FileInputStream(Configuration.EVIDENCIA_DIR+caso+".docx"));
		XWPFParagraph contenido = doc.createParagraph();
		contenido = doc.createParagraph();
		contenido.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun contenidoRun = contenido.createRun();
		contenidoRun = contenido.createRun();
		String imgFile = imagen;	
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
