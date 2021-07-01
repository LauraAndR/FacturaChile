package pages;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PageEmisionBuscarCpe {
	private WebDriver driver;
	public PageEmisionBuscarCpe(WebDriver driver) {
		this.driver=driver;
	}
	
	public void buscarCpeEmitido () throws InterruptedException {
//		Ciclo para realizar la búsqueda exacta del CPE creado
		Calendar calendario = new GregorianCalendar();
		int i=0;
		int hora = calendario.get(Calendar.HOUR_OF_DAY);
		int min = calendario.get(Calendar.MINUTE);
		hora=hora+3;
		String fecha;
		String hh;
		String mm;
		String horaSistema;
		String horaDocumento;
		
		do{
			try {
				driver.findElement(By.name("buscar2")).click();
				
				fecha=driver.findElement(By.xpath("//*[@id=\"0\"]/td[12]")).getText();
				hh=fecha.substring(11,13);
				mm=fecha.substring(14,16);
				horaDocumento=hh+":"+mm;
				
	//			IF-ELSE, ESTA CONDICION PERMITE ANTEPONER UN 0 CUANDO EL VALOR DE LA VARIABLE min SEA INFERIOR A 10.
				if(min<10) {
					horaSistema=hora+":0"+min;
				}
				else {
					horaSistema=hora+":"+min;
				}
				
				System.out.println(horaDocumento);
				System.out.println(horaSistema);
				
	//			SI LAS HORAS DEL SISTEMA Y DEL DOCUMENTO SON IGUALES, FINALIZA EL CICLO.
				if(horaDocumento.equals(horaSistema)){
					i=1;			
					System.out.println(horaDocumento);
					System.out.println(horaSistema);
				}
	//			SI LAS HORAS DEL SISTEMA Y DEL DOCUMENTO NO SON IGUALES, PASA A OTRA CONDICIÓN.
				else {
					
	//				EN OCASIONES, LA HORA DEL SISTEMA ES DISTINTA POR UN MINUTO DE DIFERENCIA, ASI QUE SE AGREA 1 AL VALOR DE min
					min=min+1;
	//				ESTE IF COMPARA EL VALOR DE min, SI ES IGUAL A 60, LO DEJA EN 0 Y A hora LE SUMA 1.
					if(min==60) {
						min=0;
						hora=hora+1;
						horaSistema=hora+":0"+min;
						System.out.println(horaSistema);
						
						if(horaDocumento.equals(horaSistema)) {
							i=1;			
							System.out.println(horaDocumento);
							System.out.println(horaSistema);
						}
						else {
							hora=hora-1;
						}
					}
					
	//				IF-ELSE, ESTA CONDICION PERMITE ANTEPONER UN 0 CUANDO EL VALOR DE LA VARIABLE min SEA INFERIOR A 10.
					if(min<10) {
						horaSistema=hora+":0"+min;
					}
					else {
						horaSistema=hora+":"+min;
					}
					
	//				SI LAS HORAS DEL SISTEMA Y DEL DOCUMENTO SON IGUALES, FINALIZA EL CICLO.
					if(horaDocumento.equals(horaSistema)) {
						i=1;			
						System.out.println(horaDocumento);
						System.out.println(horaSistema);
					}
	
	//				SI NO SON IGUALES LE RESTA 1 A LA VARIBLE min PARA VOLVER AL VALOR ORIGINAL				
					else {
						min=min-2;
						if(min<10) {
							horaSistema=hora+":0"+min;
						}
						else {
							horaSistema=hora+":"+min;
						}
						if(horaDocumento.equals(horaSistema)) {
							i=1;			
							System.out.println(horaDocumento);
							System.out.println(horaSistema);
						}
						else {
							i=0;
							min=min+1;
						}
					}
				}
			} catch (Exception e){
				PageAlerta pageAlert = new PageAlerta(driver);
				pageAlert.alertaManejoError();
			}
		}while(i==0);
	}
}
