package com.dis.dao.factory;

import com.dis.dao.iface.CheckinDAO;
import com.dis.dao.iface.ReservaDAO;
import com.dis.dao.iface.UsuarioDAO;
import com.dis.dao.iface.VueloDAO;
import com.dis.dao.mysql.MySqlCheckinDAO;
import com.dis.dao.mysql.MySqlReservaDAO;
import com.dis.dao.mysql.MySqlUsuarioDAO;
import com.dis.dao.mysql.MySqlVueloDAO;


public class MySqlDAOFactory extends DAOFactory {
	
	@Override
	public UsuarioDAO getUsuarioDAO() {
		return new MySqlUsuarioDAO();
	}

	@Override
	public ReservaDAO getReservaDAO() {
		return new MySqlReservaDAO();
	}

	@Override
	public VueloDAO getVueloDAO() {
		return new MySqlVueloDAO();
	}

	@Override
	public CheckinDAO getCheckinDAO() {
		return new MySqlCheckinDAO();
	}

	
	

	
	
	

	

}
