package com.dis.service;

import java.util.List;

import com.dis.bean.PersonaDTO;
import com.dis.bean.ReservaDTO;
import com.dis.bean.UsuarioDTO;
import com.dis.bean.VueloDTO;
import com.dis.dao.factory.DAOFactory;
import com.dis.dao.iface.ReservaDAO;

public class ReservaServiceImpl implements ReservaService {
	
	DAOFactory fabrica = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
	ReservaDAO reservaDAO=fabrica.getReservaDAO();	

	
	public boolean comprarBoleto(String idReserva, String tipoPago) {
		return reservaDAO.comprarBoleto(idReserva,tipoPago);
	}


	public ReservaDTO buscarReserva(String idReserva) {
		return reservaDAO.buscarReserva(idReserva);
	}


	public List<ReservaDTO> buscarReservaPorUsuario(String email) {
		return reservaDAO.buscarReservaPorUsuario(email);
	}


	public boolean actualizarMillasPasajero(UsuarioDTO usuario, int idReserva) {
		return reservaDAO.actualizarMillasPasajero(usuario, idReserva);
	}

	public double obtenerTarifa(String idvuelo) {
		
		return reservaDAO.obtenerTarifa(idvuelo);
	}

	
	public String obtenerOrigendeVuelo(String idvuelo) {
		
		return reservaDAO.obtenerOrigendeVuelo(idvuelo);
	}


	public String obtenerDestinodeVuelo(String idvuelo) {

		return reservaDAO.obtenerDestinodeVuelo(idvuelo);
	}


	public String obtenerNumVuelo(String idvuelo) {
		return reservaDAO.obtenerNumVuelo(idvuelo);
	}

	
	public boolean registroReserva(ReservaDTO reserva) {
		
		return reservaDAO.registroReserva(reserva);
	}



	public String obtenerHoraVuelo(String idvuelo) {
		return reservaDAO.obtenerHoraVuelo(idvuelo);
	}

	
	public String obtenerTiempoViaje(String idvuelo) {
		return reservaDAO.obtenerTiempoViaje(idvuelo);
	}


	public boolean registroPersonaxReserva(String idreserva, String idpersona,String descripcion) {
		return reservaDAO.registroPersonaxReserva(idreserva, idpersona, descripcion);
	}


	public String obtenerUltimaReserva() {
		return reservaDAO.obtenerUltimaReserva();
	}


	public String obtenerUltimaPersona() {
		return reservaDAO.obtenerUltimaPersona();
	}

	
	public VueloDTO obtenerVuelo(int idReserva) {
		return reservaDAO.obtenerVuelo(idReserva);
	}
	
	
	public List<PersonaDTO> obtenerPersonas(int idReserva) {
		return reservaDAO.obtenerPersonas(idReserva);
	}


	public List<ReservaDTO> buscarReservaPagadaPorUsuario(String email) {
		return reservaDAO.buscarReservaPagadaPorUsuario(email);
	}

}
