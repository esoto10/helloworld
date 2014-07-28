package com.dis.service;

import java.util.List;
import java.util.Vector;

import com.dis.bean.CheckinDTO;
import com.dis.bean.NacionalidadDTO;
import com.dis.bean.PersonaDTO;


public interface CheckinService {
	
	public abstract List<PersonaDTO> buscarPasajeros(String[] pasajerosSeleccionados)throws Exception ;
	public abstract List<NacionalidadDTO> buscarNacionalidades()throws Exception ;
	public abstract List<String> buscarAsientosOcupados(int idVuelo, String fechaVuelo)throws Exception ;
	public abstract int recuperarCapacidadAvion(int idVuelo)throws Exception ;
	public abstract boolean registrarCheckin(List<CheckinDTO> listaCheckinDTO)throws Exception ;
	public abstract boolean acualizarInfoPasajero(PersonaDTO personaDTO)throws Exception ;
	public abstract boolean actualizarCheckin(List<CheckinDTO> listaCheckinDTO)throws Exception ;
	public abstract CheckinDTO obtenerEmbarque(List<CheckinDTO> listaCheckinDTO)throws Exception;
	public abstract boolean verificarNumTicket(String numTicket) throws Exception;
}
