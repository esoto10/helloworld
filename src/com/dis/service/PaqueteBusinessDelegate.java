package com.dis.service;

public class PaqueteBusinessDelegate {

	private PaqueteBusinessDelegate() {
		
	}

	public static UsuarioService getUsuarioService() {
		return new UsuarioServiceImpl();
	}
	
	public static ReservaService getReservaService() {
		return new ReservaServiceImpl();
	}
	
	public static VueloService getVueloService() {
		return new VueloServiceImpl();
	}
	
	public static CheckinService getCheckinService(){
		return new CheckinServiceImpl();
	}

}
