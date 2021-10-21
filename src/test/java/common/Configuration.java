package common;

import java.io.File;

public class Configuration {
	public static String ROOT_DIR = System.getProperty("user.dir")+ File.separator;
	public static String SCREENSHOT_DIR = ROOT_DIR + "screenshots" +File.separator;
	public static String LOG_DIR = ROOT_DIR + "logs" +File.separator;
	public static String EVIDENCIA_DIR = ROOT_DIR + "Evidencia" +File.separator;
	public static String FILES_DIR = ROOT_DIR + "files" +File.separator;
	public static String DRIVERS_DIR = ROOT_DIR + "drivers" +File.separator;
	
	public static String modifyInWindows(String inPath) { 
		if(System.getProperty("os.name").toLowerCase().contains("windows")) {
			return inPath + ".exe";
		}else {
			return inPath;
		}
	}

	/*
	 * USUARIOS
	 */
	public static String USER_RUTH = "16390156-0";
	public static String PASS_RUTH = "soledad.21";
	
	public static String USER_BENJA = "16390156-0";
	public static String PASS_BENJA = "soledad.21";
	
	public static String USER_NELIDA = "7389172-8";
	public static String PASS_NELIDA = "123456cC.";
	
	public static String USER_RICARDO = "18215678-7";
	public static String PASS_RICARDO = "Verity5.0";
	
}
