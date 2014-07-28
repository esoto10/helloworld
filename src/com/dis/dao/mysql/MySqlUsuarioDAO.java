package com.dis.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.dis.bean.PersonaDTO;
import com.dis.bean.UsuarioDTO;
import com.dis.dao.iface.UsuarioDAO;
import com.dis.util.DataSourceBD;
import com.dis.util.DateHandler;
import com.dis.util.Encriptacion;
import com.dis.util.MailUtil;
import com.dis.util.ParseUtil;


public class MySqlUsuarioDAO implements UsuarioDAO {

	
	public UsuarioDTO verificarUsuario(String email,String password, boolean conversionMD5) {
		//@param conversion MD5 si es true, significa que el método convertira a MD5 el valor en password para procesarlo, false no convierte.
		String sql="SELECT p.IDPERSONA, p.NOMBRE, p.APELLIDOPATERNO, p.APELLIDOMATERNO, u.TIPOUSUARIO, u.EMAIL, u.MILLAS, u.ESTADO FROM t_persona p, t_usuario u WHERE p.IDPERSONA = u.IDPERSONA AND u.EMAIL LIKE ? AND u.CLAVE LIKE ?";
		
		if(conversionMD5){
			password=Encriptacion.getStringMessageDigest(password, "MD5");
		}
		UsuarioDTO usuario = null;
		Connection conexion = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			conexion = DataSourceBD.openConnection();
			pstm = conexion.prepareStatement(sql);
			pstm.setString(1, email);
			pstm.setString(2, password);
			
			rs = pstm.executeQuery();
			
			if (rs.next()) {
				usuario = new UsuarioDTO();
				usuario.setIdUsuario(rs.getInt(1));
				usuario.setNombre(rs.getString(2));
				usuario.setApellidoPaterno(rs.getString(3));
				usuario.setApellidoMaterno(rs.getString(4));
				usuario.setTipoUsuario(rs.getString(5));
				usuario.setEmail(rs.getString(6));
				usuario.setMillas(rs.getInt(7));
				usuario.setEstado(rs.getString(8));
			}
			DataSourceBD.closeConnection(conexion);
		} catch (SQLException e) {			
			e.printStackTrace();
		} 
		
		return usuario;
	}
	
	private boolean existeUsuario(String numDocumento){
		Connection 	conexion = DataSourceBD.openConnection();
		PreparedStatement pstm;
		ResultSet rs = null;
		String sql="SELECT IDPERSONA FROM t_persona WHERE NUMDOCUMENTO LIKE ?";
		
		try {
			pstm = conexion.prepareStatement(sql);
			pstm.setString(1, numDocumento);
			rs = pstm.executeQuery();
			if(rs.next()){
				DataSourceBD.closeConnection(conexion);
				return true;
			}else{
				DataSourceBD.closeConnection(conexion);
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	public boolean registrarPersona(PersonaDTO persona){
		if(existeUsuario(persona.getNumDocumento())) return false;
		ResultSet rs = null;
		String sql="insert into t_persona(NOMBRE,APELLIDOPATERNO,APELLIDOMATERNO,TIPODOCUMENTO,NUMDOCUMENTO,FECHANACIMIENTO,TIPOPERSONA,GENERO,NACIONALIDAD) values (?,?,?,?,?,?,?,?,?)";
		java.sql.Date fecFormatoDate=null;
		try {
			SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");			
			fecFormatoDate = new java.sql.Date(sdf.parse(persona.getFechaNacimiento()).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Connection 	conexion = DataSourceBD.openConnection();
		PreparedStatement pstm;
		try {
			pstm = conexion.prepareStatement(sql);
			pstm.setString(1,persona.getNombre());
			pstm.setString(2,persona.getApellidoPaterno());
			pstm.setString(3,persona.getApellidoMaterno());
			pstm.setString(4, persona.getTipoDocumento());
			pstm.setString(5, persona.getNumDocumento());
			pstm.setDate(6,fecFormatoDate);
			pstm.setString(7, persona.getTipoPersona());
			pstm.setString(8,persona.getGenero());
			if(persona.getNacionalidad()!=null)
				pstm.setString(9,persona.getNacionalidad());
			else
				pstm.setString(9,"163");
			
			int c = pstm.executeUpdate();
			pstm.close();
			sql="SELECT IDPERSONA FROM t_persona WHERE NUMDOCUMENTO LIKE ?";
			pstm = conexion.prepareStatement(sql);
			pstm.setString(1, persona.getNumDocumento());
			rs = pstm.executeQuery();
			if(rs.next()) persona.setIdPersona(rs.getString(1));
			DataSourceBD.closeConnection(conexion);
			if(c!=0) return true;
			else return false;
		} catch (SQLException e) {			
			e.printStackTrace();
			return false;
		}		
	}
	
	
	public boolean registrarUsuario(UsuarioDTO usuario){
		PersonaDTO persona = ParseUtil.parsePersonaDTO(usuario);
		if(registrarPersona(persona)){
			usuario.setIdUsuario(Integer.parseInt(persona.getIdPersona()));
			String sql="insert into t_usuario(IDPERSONA,TIPOUSUARIO,EMAIL,CLAVE,FECHAREGISTRO) values (?,?,?,?,now())";
			if(usuario.getTipoUsuario()!=null){
				if(usuario.getTipoUsuario().equals("1") || usuario.getTipoUsuario().equals("2") || usuario.getTipoUsuario().equals("3")){
					usuario.setTipoUsuario("USER");
				}
			}else{
				usuario.setTipoUsuario("USER");
			}
	
			Connection 	conexion = DataSourceBD.openConnection();
			PreparedStatement pstm;
			try {
				pstm = conexion.prepareStatement(sql);
				pstm.setInt(1,usuario.getIdUsuario());
				pstm.setString(2, usuario.getTipoUsuario());
				pstm.setString(3,usuario.getEmail());
				pstm.setString(4, Encriptacion.getStringMessageDigest(usuario.getPassword(), "MD5"));
				
				int c = pstm.executeUpdate();
				pstm.close();
				DataSourceBD.closeConnection(conexion);
				if(c!=0) return true;
				else return false;
			} catch (SQLException e) {			
				e.printStackTrace();
				return false;
			}	
		}else{
			return false;
		}
	}
	

	public boolean solicitarCambioPassword(String email,String url) {
		
		String sql="SELECT u.CLAVE, p.NOMBRE, p.APELLIDOPATERNO, p.APELLIDOMATERNO FROM t_persona p, t_usuario u WHERE p.IDPERSONA = u.IDPERSONA AND u.EMAIL LIKE ?";
		ResultSet rs=null;
		
		Connection 	conexion = DataSourceBD.openConnection();
		PreparedStatement pstm;
		try {
			pstm = conexion.prepareStatement(sql);
			pstm.setString(1,email);
			rs = pstm.executeQuery();
			UsuarioDTO usuario=null;
			
			while(rs.next()){
				usuario = new UsuarioDTO();
				usuario.setEmail(email);
				usuario.setPassword(rs.getString(1));
				usuario.setNombre(rs.getString(2));
				usuario.setApellidoPaterno(rs.getString(3));
				usuario.setApellidoMaterno(rs.getString(4));
			}
			pstm.close();
			DataSourceBD.closeConnection(conexion);
			if(usuario!=null){
				String urlSplitted [] = url.split("/");
				url="";
				for(int i=0;i<urlSplitted.length-1;i++) url+=urlSplitted[i]+"/";
				url+="cambiarPassUsuario?tkn="+usuario.getPassword()+"&usuario.email="+email;
				try{
					if(usuario!=null){
						MailUtil.enviarMail(email,usuario.getNombre()+" "+usuario.getApellidoPaterno()+" "+usuario.getApellidoMaterno(), "Recuperación de Contraseña", "Ud. ha solicitado un cambio de contraseña. Por favor ingrese al siguiente enlance para realizar dicho cambio: <br><br>Ingrese a este <a href='"+url+"'>enlace</a> para actualizar su password.<br><br>Si ud. no solicitó el cambio de contraseña, haga caso omiso a este email.");
						return true;
					}
					return false;
				}catch(Exception e){
					return false;
				}
			}else{
				return false;
			}
		} catch (SQLException e) {			
			e.printStackTrace();
			return false;
		}	
	}
	
	public boolean cambiarPassword(String tkn, String mail, String nuevopass) {
		String sql="UPDATE t_usuario SET CLAVE=? WHERE CLAVE LIKE ? AND EMAIL LIKE ?";
		
		Connection 	conexion = DataSourceBD.openConnection();
		PreparedStatement pstm;
		try {
			pstm = conexion.prepareStatement(sql);
			pstm.setString(1,nuevopass);
			pstm.setString(2,tkn);
			pstm.setString(3,mail);
			
			int c = pstm.executeUpdate();
			pstm.close();
			DataSourceBD.closeConnection(conexion);
			if(c!=0) return true;
			else return false;
		} catch (SQLException e) {			
			e.printStackTrace();
			return false;
		}		
		
		
		
	}
	
	public boolean actualizarPassword(UsuarioDTO usuario){
		String sql="UPDATE t_usuario SET CLAVE=? WHERE IDPERSONA= ?";
		
		Connection 	conexion = DataSourceBD.openConnection();
		PreparedStatement pstm;
		try {
			pstm = conexion.prepareStatement(sql);
			pstm.setString(1,Encriptacion.getStringMessageDigest(usuario.getPassword(), "MD5"));
			pstm.setInt(2,usuario.getIdUsuario());
			int c = pstm.executeUpdate();
			pstm.close();
			DataSourceBD.closeConnection(conexion);
			if(c!=0) return true;
			else return false;
		} catch (SQLException e) {			
			e.printStackTrace();
			return false;
		}
	}


	public List<UsuarioDTO> listarUsuarios() {
		String sql="SELECT P.IDPERSONA, P.NOMBRE, P.APELLIDOPATERNO, P.APELLIDOMATERNO, P.FECHANACIMIENTO, P.TIPODOCUMENTO, P.NUMDOCUMENTO, U.ESTADO, U.EMAIL from t_persona P, t_usuario U WHERE P.IDPERSONA = U.IDPERSONA";
		Connection 	conexion = DataSourceBD.openConnection();
		PreparedStatement pstm;
		ResultSet rs=null;
		List<UsuarioDTO> usuarios= new ArrayList<UsuarioDTO>();
		try {
			pstm = conexion.prepareStatement(sql);			
			rs = pstm.executeQuery();			
			while(rs.next()){
				UsuarioDTO usuario = new UsuarioDTO();
				usuario.setIdUsuario(rs.getInt(1));
				usuario.setNombre(rs.getString(2));
				usuario.setApellidoPaterno(rs.getString(3));
				usuario.setApellidoMaterno(rs.getString(4));
				usuario.setEdad(DateHandler.getEdad(rs.getDate(5))+"");  
				usuario.setTipoDocumento(rs.getString(6));
				usuario.setNumeroDocumento(rs.getString(7));
				usuario.setEstado(rs.getString(8));
				usuario.setEmail(rs.getString(9));
				usuarios.add(usuario);
			}
			DataSourceBD.closeConnection(conexion);
			pstm.close();
			return usuarios;
		} catch (SQLException e) {			
			e.printStackTrace();
			return null;
		}	
	}


	public boolean deshabilitarUsuario(String idUsuario, String estado) {
		String sql="UPDATE t_usuario SET estado=? WHERE IDPERSONA = ?";
		Connection 	conexion = DataSourceBD.openConnection();
		PreparedStatement pstm;
		try{
			pstm = conexion.prepareStatement(sql);
			pstm.setString(1, estado);
			pstm.setString(2, idUsuario);
			int c = pstm.executeUpdate();
			if(c!=0) return true;
			else 	 return false;
		} catch (SQLException e) {			
			e.printStackTrace();
			return false;
		}	
	}

	
	public UsuarioDTO getUsuario(String idUsuario) {
		String sql="SELECT P.IDPERSONA, P.NOMBRE, P.APELLIDOPATERNO, P.APELLIDOMATERNO, P.FECHANACIMIENTO, P.TIPODOCUMENTO, P.NUMDOCUMENTO, U.EMAIL, P.GENERO from t_persona P, t_usuario U WHERE P.IDPERSONA = U.IDPERSONA AND P.IDPERSONA = ?";
		Connection 	conexion = DataSourceBD.openConnection();
		PreparedStatement pstm;
		ResultSet rs=null;
		UsuarioDTO usuario = new UsuarioDTO();
		try {
			pstm = conexion.prepareStatement(sql);	
			pstm.setString(1, idUsuario);
			rs = pstm.executeQuery();			
			while(rs.next()){
				usuario.setIdUsuario(rs.getInt(1));
				usuario.setNombre(rs.getString(2));
				usuario.setApellidoPaterno(rs.getString(3));
				usuario.setApellidoMaterno(rs.getString(4));
				usuario.setFechaNacimiento(rs.getString(5));
				usuario.setTipoDocumento(rs.getString(6));
				usuario.setNumeroDocumento(rs.getString(7));
				usuario.setEmail(rs.getString(8));
				usuario.setSexo(rs.getString(9));
			}
			DataSourceBD.closeConnection(conexion);
			pstm.close();
			return usuario;
		} catch (SQLException e) {			
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean actualizarPersona(UsuarioDTO usuario){
		String sql="UPDATE t_usuario SET EMAIL=? WHERE IDPERSONA=?";
		if(!usuario.getPassword().equals("")) actualizarPassword(usuario);
		Connection 	conexion = DataSourceBD.openConnection();
		PreparedStatement pstm;
		try {
			pstm = conexion.prepareStatement(sql);
			pstm.setString(1,usuario.getEmail());
			pstm.setInt(2,usuario.getIdUsuario());

			int c = pstm.executeUpdate();
			pstm.close();
			DataSourceBD.closeConnection(conexion);
			if(c!=0) return true;
			else return false;
		} catch (SQLException e) {			
			e.printStackTrace();
			return false;
		}
	}

	public boolean actualizarUsuario(UsuarioDTO usuario) {
		String sql="UPDATE t_persona SET NOMBRE=?, APELLIDOPATERNO=?, APELLIDOMATERNO=?, FECHANACIMIENTO=?, TIPODOCUMENTO=?, GENERO=?, NUMDOCUMENTO=? WHERE IDPERSONA=?";
		
		Connection 	conexion = DataSourceBD.openConnection();
		PreparedStatement pstm;
		try {
			pstm = conexion.prepareStatement(sql);
			pstm.setString(1,usuario.getNombre());
			pstm.setString(2,usuario.getApellidoPaterno());
			pstm.setString(3,usuario.getApellidoMaterno());
			pstm.setString(4,usuario.getFechaNacimiento());
			pstm.setString(5,usuario.getTipoDocumento());
			pstm.setString(6,usuario.getSexo());
			pstm.setString(7,usuario.getNumeroDocumento());
			pstm.setInt(8,usuario.getIdUsuario());
			
			int c = pstm.executeUpdate();
			pstm.close();
			DataSourceBD.closeConnection(conexion);
			if(c!=0) {
				if(!actualizarPersona(usuario)) return false;
				return true;
			}
			else return false;
		} catch (SQLException e) {			
			e.printStackTrace();
			return false;
		}	
	}
	
	
}
