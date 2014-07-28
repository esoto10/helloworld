package com.dis.bean;

public class CheckinDTO {
	
	private int idEmbarque;
	private String asiento;
	private int idPersona;
	private int idReserva;
	private String numTicket;
	
	
	public String getNumTicket() {
		return numTicket;
	}
	public void setNumTicket(String numTicket) {
		this.numTicket = numTicket;
	}
	public int getIdEmbarque() {
		return idEmbarque;
	}
	public void setIdEmbarque(int idEmbarque) {
		this.idEmbarque = idEmbarque;
	}
	public String getAsiento() {
		return asiento;
	}
	public void setAsiento(String asiento) {
		this.asiento = asiento;
	}
	public int getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(int idPersona) {
		this.idPersona = idPersona;
	}
	public int getIdReserva() {
		return idReserva;
	}
	public void setIdReserva(int idReserva) {
		this.idReserva = idReserva;
	}

	
}