package com.dis.service;

import java.util.List;
import java.util.Vector;

import com.dis.bean.CheckinDTO;
import com.dis.bean.NacionalidadDTO;
import com.dis.bean.PersonaDTO;
import com.dis.dao.factory.DAOFactory;
import com.dis.dao.iface.CheckinDAO;


public class CheckinServiceImpl implements CheckinService {
	
	DAOFactory fabrica = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
	CheckinDAO checkinDAO=fabrica.getCheckinDAO();
	
	
	public List<PersonaDTO> buscarPasajeros(String[] pasajerosSeleccionados)throws Exception  {
		
		return checkinDAO.buscarPasajeros(pasajerosSeleccionados);
		
	}

	
	public List<NacionalidadDTO> buscarNacionalidades() throws Exception  {
		
		return checkinDAO.buscarNacionalidades();
	}

	
	public List<String> buscarAsientosOcupados(int idVuelo, String fechaVuelo) throws Exception {
		return checkinDAO.buscarAsientosOcupados(idVuelo,fechaVuelo);
	}


	public int recuperarCapacidadAvion(int idVuelo) throws Exception {
		// TODO Auto-generated method stub
		return checkinDAO.recuperarCapacidadAvion(idVuelo);
	}

	
	public boolean registrarCheckin(List<CheckinDTO> listaCheckinDTO) throws Exception {
		// TODO Auto-generated method stub
		return checkinDAO.registrarCheckin(listaCheckinDTO);
	}


	public boolean acualizarInfoPasajero(PersonaDTO personaDTO)
			throws Exception {

		return checkinDAO.acualizarInfoPasajero(personaDTO);
	}


	public boolean actualizarCheckin(List<CheckinDTO> listaCheckinDTO)
			throws Exception {
		// TODO Auto-generated method stub
		return checkinDAO.actualizarCheckin(listaCheckinDTO);
	}

	
	public CheckinDTO obtenerEmbarque(List<CheckinDTO> listaCheckinDTO)
			throws Exception {
		// TODO Auto-generated method stub
		return checkinDAO.obtenerEmbarque(listaCheckinDTO);
	}


	public boolean verificarNumTicket(String numTicket) throws Exception {
		// TODO Auto-generated method stub
		return checkinDAO.verificarNumTicket(numTicket);
	}
	

	
}
