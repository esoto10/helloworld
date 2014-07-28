package com.dis.service;

import java.util.List;

import com.dis.bean.VueloDTO;

public interface VueloService {
	
	public abstract List<VueloDTO> buscarVuelos(String origen, String destino, String fecha);

}
