package com.dis.service;

import java.util.List;

import com.dis.bean.PersonaDTO;
import com.dis.bean.UsuarioDTO;


public interface UsuarioService {

	public abstract UsuarioDTO verificarUsuario(String email,String password, boolean conversionMD5);	
	public abstract boolean registrar(UsuarioDTO usuarioDTO);
	public abstract boolean cambiarPassword(String tkn ,String mail,String nuevopass);
	public abstract boolean solicitarCambioPassword(String email, String url);
	public abstract boolean registrarPersona(PersonaDTO persona);
	public abstract List<UsuarioDTO> listarUsuarios();
	public abstract boolean deshabilitarUsuario(String idUsuario, String estado);
	public abstract UsuarioDTO getUsuario (String idUsuario);
	public abstract boolean actualizarUsuario(UsuarioDTO usuario);
}
