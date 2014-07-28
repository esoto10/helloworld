package com.dis.dao.factory;

import com.dis.dao.iface.CheckinDAO;
import com.dis.dao.iface.ReservaDAO;
import com.dis.dao.iface.UsuarioDAO;
import com.dis.dao.iface.VueloDAO;


public class XmlDAOFactory extends DAOFactory{

	@Override
	public UsuarioDAO getUsuarioDAO() {
		return null;
	}

	@Override
	public ReservaDAO getReservaDAO() {
		return null;
	}

	@Override
	public VueloDAO getVueloDAO() {
		return null;
	}

	@Override
	public CheckinDAO getCheckinDAO() {
		return null;
	}

	

    
    
}
