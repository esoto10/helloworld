package com.dis.util;

import java.util.ArrayList;
import java.util.List;

import com.dis.bean.PersonaDTO;
import com.dis.bean.UsuarioDTO;

public class ParseUtil {
	
	public static String diaToBoolean(String dia){
		int diaBoolean=0;
		String cadena="";
			if		(dia.equals("LUNES"))		diaBoolean+=1;
			else if (dia.equals("MARTES"))		diaBoolean+=10;
			else if (dia.equals("MIERCOLES"))	diaBoolean+=100;
			else if (dia.equals("JUEVES"))		diaBoolean+=1000;
			else if (dia.equals("VIERNES"))		diaBoolean+=10000;
			else if (dia.equals("SABADO"))		diaBoolean+=100000;
			else if (dia.equals("DOMINGO"))		diaBoolean+=1000000;
			else								diaBoolean+=0;
			cadena=diaBoolean+"";
			int resta=7-cadena.length();

			for(int i=0;i<resta;i++){
				cadena="0"+cadena;
			}
		return cadena;
	}
	
	private static String atrasAdelante(String cadena){
		String cadenaInvertida="";
		for (int i=cadena.length()-1;i>=0;i--) cadenaInvertida+=cadena.charAt(i);
		return cadenaInvertida;
	}
	
	public static List<String> booleanToDia(String booleano){
		List<String> dias=new ArrayList<String> ();
		booleano=atrasAdelante(booleano);
		for(int i =0; i<booleano.length();i++){
			int value = Integer.parseInt(booleano.charAt(i)+"");
			if(value==1){
				switch(i){
				case 0:
					dias.add("LUNES");
					break;
				case 1:
					dias.add("MARTES");
					break;
				case 2:
					dias.add("MIERCOLES");
					break;
				case 3:
					dias.add("JUEVES");
					break;
				case 4:
					dias.add("VIERNES");
					break;
				case 5:
					dias.add("SABADO");
					break;
				case 6:
					dias.add("DOMINGO");
					break;
				default:
					break;
				}
			}
		}
		return dias;
	}
	
	public static String primeraMayuscula(String cadena){
		String conversion="";
		String [] palabras=cadena.split(" ");
		for(int i=0; i<palabras.length;i++){
			String primeraLetra = palabras[i].substring(0,1).toUpperCase();
			String resto = palabras[i].substring(1).toLowerCase();
			conversion+=primeraLetra+resto+" ";
		}
		return conversion;
	}
	
	public static PersonaDTO parsePersonaDTO(UsuarioDTO usuario){
		PersonaDTO persona = new PersonaDTO();
		persona.setNombre(usuario.getNombre());
		persona.setApellidoPaterno(usuario.getApellidoPaterno());
		persona.setApellidoMaterno(usuario.getApellidoMaterno());
		persona.setFechaNacimiento(usuario.getFechaNacimiento());
		persona.setGenero(usuario.getSexo());
		if(usuario.getNacionalidad()==null)
			persona.setNacionalidad("136");
		else
			persona.setNacionalidad(usuario.getNacionalidad());
		persona.setNumDocumento(usuario.getNumeroDocumento());
		persona.setTipoDocumento(usuario.getTipoDocumento());
		if(usuario.getTipoUsuario()==null)
			persona.setTipoPersona("1");
		else
			persona.setTipoPersona(usuario.getTipoUsuario());
		return persona;
	}
	
	public static String parseBooleano(String booleano){
		return booleano.replace('0', '_');
	}

}
