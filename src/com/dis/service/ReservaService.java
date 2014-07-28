package com.dis.service;

import java.util.List;

import com.dis.bean.PersonaDTO;
import com.dis.bean.ReservaDTO;
import com.dis.bean.UsuarioDTO;
import com.dis.bean.VueloDTO;

public interface ReservaService {
	
	public abstract boolean comprarBoleto(String idReserva, String tipoPago);
	public abstract boolean actualizarMillasPasajero(UsuarioDTO usuario, int idReserva);
	public abstract ReservaDTO buscarReserva(String idReserva);
	public abstract List<ReservaDTO> buscarReservaPorUsuario(String email);
	
	public abstract double obtenerTarifa(String idvuelo);
	public abstract String obtenerDestinodeVuelo(String idvuelo);
	public abstract String obtenerOrigendeVuelo(String idvuelo);
	public abstract String obtenerNumVuelo(String idvuelo);
	public abstract String obtenerHoraVuelo(String idvuelo);
	public abstract String obtenerTiempoViaje(String idvuelo);
	public abstract boolean registroReserva(ReservaDTO reserva);
	public abstract boolean registroPersonaxReserva(String idreserva,String idpersona,String descripcion);
	public abstract String obtenerUltimaReserva(); 
	public abstract String obtenerUltimaPersona();
	public abstract VueloDTO obtenerVuelo(int idReserva);
	public abstract List<PersonaDTO> obtenerPersonas(int idReserva);
	public abstract List<ReservaDTO> buscarReservaPagadaPorUsuario(String email);

}
