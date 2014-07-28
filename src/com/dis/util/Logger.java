package com.dis.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.dis.bean.LogDTO;

/**
 * @author Llamita Airlines
 * 
 * Clase para grabar en la tabla Log, todos las acciones realizadas por un usuario.
 *
 */
public class Logger {
	
	/**
	 * @param log : Objeto Log que contiene el usuario y la acción realizada.
	 * 
	 * El método registra en la base de datos la acción realizada por algún usuario.
	 *
	 */
	public static void info(LogDTO log) {
		String sql="";
		if(log.getUsuario().equals("null"))
			 sql="insert into t_log(evento,fecha) values(?,now())";		
		else
			sql="insert into t_log(evento,idusuario,fecha) values(?,?,now())";	
		
		Connection 	conexion = DataSourceBD.openConnection();
		PreparedStatement pstm;
		try {
			pstm = conexion.prepareStatement(sql);
			if(!log.getUsuario().equals("null")){
				pstm.setString(2,log.getUsuario());		
			}	
			pstm.setString(1,log.getLog());
			pstm.executeUpdate();
			pstm.close();
			DataSourceBD.closeConnection(conexion);	
		} catch (SQLException e) {			
			e.printStackTrace();
		}	
	}

}
