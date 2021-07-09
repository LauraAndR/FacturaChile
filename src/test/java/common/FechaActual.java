package common;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class FechaActual {
	public String FechaHoy () {
		Calendar calendario = new GregorianCalendar();
		int anio = calendario.get(Calendar.YEAR);
		int mes = calendario.get(Calendar.MONTH);
		mes=mes+1;
		
		if(mes>12) {
			mes=1;
		}
		int dia = calendario.get(Calendar.DAY_OF_MONTH);
		String fecha=null;
		if(dia<10) {
			fecha="0"+String.valueOf(dia);
		}
		else {
			fecha=String.valueOf(dia);
		}
		
		if(mes<10) {
			fecha=fecha+"0"+String.valueOf(mes);
		}
		else {
			fecha=fecha+String.valueOf(mes);
		}
		
		fecha=fecha+String.valueOf(anio);
		
		return fecha;
	}
	
	
	public String FechaVencimiento () {
		Calendar calendario = new GregorianCalendar();
		int anio = calendario.get(Calendar.YEAR);
		int mes = calendario.get(Calendar.MONTH);
		mes=mes+1;
		if(mes>12) {
			mes=1;
		}
		int dia = calendario.get(Calendar.DAY_OF_MONTH);
		String fecha=null;
		if(dia<10) {
			fecha="0"+String.valueOf(dia);
		}
		else {
			fecha=String.valueOf(dia);
		}
		
		if(mes<10) {
			fecha=fecha+"0"+String.valueOf(mes);
		}
		else {
			fecha=fecha+String.valueOf(mes);
		}
		
		fecha=fecha+String.valueOf(anio);
		
		return fecha;
	}
}
