package com.dis.dao.iface;

import java.util.List;

import com.dis.bean.VueloDTO;


public interface VueloDAO {
	
	public abstract List<VueloDTO> buscarVuelos(String origen, String destino, String fecha);
	public abstract String buscarCiudad(String id);

}
