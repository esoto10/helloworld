package com.dis.service;

import java.util.List;


import com.dis.bean.VueloDTO;
import com.dis.dao.factory.DAOFactory;
import com.dis.dao.iface.VueloDAO;

public class VueloServiceImpl implements VueloService {
	
	DAOFactory fabrica = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
	VueloDAO vueloDAO=fabrica.getVueloDAO();
	
	public List<VueloDTO> buscarVuelos(String origen, String destino,String fecha) {
		
		return vueloDAO.buscarVuelos(origen, destino, fecha);
	}

	
}
