package com.dis.dao.iface;

import java.util.List;

import com.dis.bean.PersonaDTO;
import com.dis.bean.UsuarioDTO;

public interface UsuarioDAO {

	public abstract UsuarioDTO verificarUsuario(String email, String password, boolean conversionMD5);	
	public abstract boolean registrarUsuario(UsuarioDTO entidad);
	public abstract boolean registrarPersona(PersonaDTO persona);
	public abstract boolean cambiarPassword(String tkn, String mail,String nuevopass);
	public abstract boolean solicitarCambioPassword(String email, String url);
	public abstract List<UsuarioDTO> listarUsuarios();
	public abstract boolean deshabilitarUsuario(String idUsuario, String estado);
	public abstract UsuarioDTO getUsuario(String idUsuario);
	public abstract boolean actualizarUsuario(UsuarioDTO usuario);
}
