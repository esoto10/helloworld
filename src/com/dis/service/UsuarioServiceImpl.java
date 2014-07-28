package com.dis.service;


import java.util.List;

import com.dis.bean.PersonaDTO;
import com.dis.bean.UsuarioDTO;
import com.dis.dao.factory.DAOFactory;
import com.dis.dao.iface.UsuarioDAO;

public class UsuarioServiceImpl implements UsuarioService {

	DAOFactory fabrica = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
	UsuarioDAO usuarioDAO=fabrica.getUsuarioDAO();
	
	
	public UsuarioDTO verificarUsuario(String email, String password, boolean conversionMD5) {
		return usuarioDAO.verificarUsuario(email, password, conversionMD5);
	}
	
	public boolean registrar(UsuarioDTO usuarioDTO){
		return usuarioDAO.registrarUsuario(usuarioDTO);
	}

	public boolean cambiarPassword(String tkn, String mail, String nuevopass) {	
		return usuarioDAO.cambiarPassword( tkn,  mail , nuevopass);
	}


	public boolean solicitarCambioPassword(String email, String url) {
		return usuarioDAO.solicitarCambioPassword(email, url);
	}


	public boolean registrarPersona(PersonaDTO persona) {
		return usuarioDAO.registrarPersona(persona);
	}

	
	public List<UsuarioDTO> listarUsuarios() {
		return usuarioDAO.listarUsuarios();
	}


	public boolean deshabilitarUsuario(String idUsuario, String estado) {
		return usuarioDAO.deshabilitarUsuario(idUsuario, estado);
	}

	public UsuarioDTO getUsuario(String idUsuario) {
		return usuarioDAO.getUsuario(idUsuario);
	}

	public boolean actualizarUsuario(UsuarioDTO usuario) {
		return usuarioDAO.actualizarUsuario(usuario);
	}
}
