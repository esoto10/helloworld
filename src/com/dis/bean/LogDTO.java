package com.dis.bean;

public class LogDTO {
	private String usuario;
	private String log;
	
	public LogDTO(String usuario, String log) {
		super();
		this.usuario = usuario;
		this.log = log;
	}
	
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getLog() {
		return log;
	}
	public void setLog(String log) {
		this.log = log;
	}
	

}
