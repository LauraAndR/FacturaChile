package common;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Log {
	public void crearArchivoLog(String caso) {
		FileWriter flwriter = null;
		try {
			//crea el flujo para escribir en el archivo
			flwriter = new FileWriter(Configuration.LOG_DIR+caso+".txt");
			//crea un buffer o flujo intermedio antes de escribir directamente en el archivo
			BufferedWriter bfwriter = new BufferedWriter(flwriter);
			bfwriter.write("Log de Caso de Prueba "+caso+ "\n");
			//cierra el buffer intermedio
			bfwriter.close();
			System.out.println("Archivo creado satisfactoriamente..");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (flwriter != null) {
				try {//cierra el flujo principal
					flwriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void modificarArchivoLog(String caso, String paso) {
		FileWriter flwriter = null;
		try {
			//además de la ruta del archivo recibe un parámetro de tipo boolean, que le indican que se va añadir más registros 
			flwriter = new FileWriter(Configuration.LOG_DIR+caso+".txt", true);
			BufferedWriter bfwriter = new BufferedWriter(flwriter);
			bfwriter.write(paso+ "\n");
			bfwriter.close();
			System.out.println("Archivo modificado satisfactoriamente..");
 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (flwriter != null) {
				try {
					flwriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}	
}
