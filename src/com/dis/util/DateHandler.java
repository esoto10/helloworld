package com.dis.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateHandler {
	
	
public static String sumarMinutos(String horaSalida, String duracion){
		
		//transformando en minutos la hora de llegada y la duracion
		int minutosDeHora = (Integer.parseInt((horaSalida.substring(0, 2)))*60)+Integer.parseInt(horaSalida.substring(2));
		int minutosDeDuracion = (Integer.parseInt((duracion.substring(0, 2)))*60)+Integer.parseInt(duracion.substring(2));
		
		
		//calculando los minutos totales horasalida+duracion
		int minutosTotales = minutosDeHora+minutosDeDuracion;
		
		//calculando la hora
		
		double horas = 0.0;
		
		if((double)minutosTotales/60 >= 24){
			horas = ((double)minutosTotales/60)-24;//si pasa las 24 horas
		}else{
			horas = ((double)minutosTotales/60);//si no pasa las 24
		}
		
		
		//separando laa horas de los minutos
		int hora = Math.abs((int)horas);
	 
		//calculando los minutos con el decimal
		int minutos = Math.abs((int)Math.rint(((horas-hora)*60)));
		
		String horaStr = String.valueOf(hora);//convirtiendo la hora a cadena de caracteres 
		String minutosStr = String.valueOf(minutos);//convirtiendo los minutos a cadena de caracteres 
		
		if(horaStr.length()==1){
			
			horaStr = "0"+horaStr;//agregando el cero antes a la hora
		}
		if(minutosStr.length()==1){
			
			minutosStr = "0"+minutosStr;//agregando el cero a minutos
		}
		
		String horaLlegada = horaStr+minutosStr;//armando la hora
		
		return horaLlegada;
		
	}
	
	
	public static Date getDate(String fecha) {
	    
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    String strDate = fecha;
	    Date date = null;
	    try {
	    
	    	date = dateFormat.parse(strDate);
	    
	   } catch (ParseException ex) {
	    
		   ex.printStackTrace();
	    
	   }
	    
	    return date;
	}
	
	public static String showtime(String hora) {
		hora= hora.substring(0,2)+":"+hora.substring(2,4);
		return hora;
	}
	
	public static int getEdad(Date fechaNacimiento){
		Calendar dob = Calendar.getInstance();  
		dob.setTime(fechaNacimiento);  
		Calendar today = Calendar.getInstance();  
		int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);  
		if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR))  
		age--; 
		return age;
	}
	
	public static String obtenerAnio(Date date){

		String formato="yyyy";//2011
		SimpleDateFormat dateFormat = new SimpleDateFormat(formato);
		return dateFormat.format(date);


	}

	public static String obtenerMes(Date date){

		String formato="MMM";//OCT
		SimpleDateFormat dateFormat = new SimpleDateFormat(formato);
		return dateFormat.format(date).toUpperCase();

	}

	public static String obtenerDia(Date date){

		String formato="dd";//25
		SimpleDateFormat dateFormat = new SimpleDateFormat(formato);
		return dateFormat.format(date);
	
	}
	
	public static String obtenerHoraEmbarque(String fecha,String horaSalida){
		
		int dia = Integer.parseInt(fecha.substring(8));
		int mes = Integer.parseInt(fecha.substring(5,7));
		int anio = Integer.parseInt(fecha.substring(0,4));
		
		int horas = Integer.parseInt(horaSalida.substring(0,2));
		int minutos = Integer.parseInt(horaSalida.substring(2));
		
		Calendar cal = Calendar.getInstance();
		cal.set(anio,mes,dia,horas,minutos,0);
		cal.add(Calendar.MINUTE,-35);


		return String.valueOf(cal.getTime().toString().substring(11,16));

		
	}
	
	public static String obtenerHoraAeropuerto(String fechaSalida,String horaSalida){
		
		int dia = Integer.parseInt(fechaSalida.substring(8));
		int mes = Integer.parseInt(fechaSalida.substring(5,7));
		int anio = Integer.parseInt(fechaSalida.substring(0,4));
		
		int horas = Integer.parseInt(horaSalida.substring(0,2));
		int minutos = Integer.parseInt(horaSalida.substring(2));
		
		Calendar cal = Calendar.getInstance();
		cal.set(anio,mes,dia,horas,minutos,0);
		cal.add(Calendar.MINUTE,-120);


		return String.valueOf(cal.getTime().toString().substring(11,16));

		
	}

}
