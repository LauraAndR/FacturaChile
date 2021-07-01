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
}
