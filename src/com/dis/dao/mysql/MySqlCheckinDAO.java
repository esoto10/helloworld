package com.dis.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import com.dis.bean.CheckinDTO;
import com.dis.bean.NacionalidadDTO;
import com.dis.bean.PersonaDTO;
import com.dis.bean.VueloDTO;
import com.dis.dao.iface.CheckinDAO;
import com.dis.dao.iface.VueloDAO;
import com.dis.util.DataSourceBD;
import com.dis.util.DateHandler;

public class MySqlCheckinDAO implements CheckinDAO {


	public List<PersonaDTO> buscarPasajeros(String[] pasajerosSeleccionados) throws Exception  {
		
		String sql="SELECT IDPERSONA,NOMBRE,APELLIDOPATERNO,APELLIDOMATERNO,TIPODOCUMENTO+0,NUMDOCUMENTO,TIPOPERSONA+0,GENERO+0,NACIONALIDAD FROM T_PERSONA WHERE IDPERSONA=?";
	
		PersonaDTO persona = null;
		List<PersonaDTO> pasajeros = new Vector<PersonaDTO>();
		
		for(int i=0; i<pasajerosSeleccionados.length ;i++){
			
			ResultSet rs=null;
			Connection 	conexion = DataSourceBD.openConnection();
			PreparedStatement pstm;
			
			try {
				pstm = conexion.prepareStatement(sql);
				pstm.setInt(1,Integer.parseInt(pasajerosSeleccionados[i]));
				rs = pstm.executeQuery();
				
				while(rs.next()){
					
					persona = new PersonaDTO();
					
					persona.setIdPersona(rs.getString(1));
					persona.setNombre(rs.getString(2));
					persona.setApellidoPaterno(rs.getString(3));
					persona.setApellidoMaterno(rs.getString(4));
					persona.setTipoDocumento(rs.getString(5));
					persona.setNumDocumento(rs.getString(6));
					persona.setTipoPersona(rs.getString(7));
					persona.setGenero(rs.getString(8));
					persona.setNacionalidad(rs.getString(9));
					
					pasajeros.add(persona);
				}			
				
				pstm.close();
				DataSourceBD.closeConnection(conexion);

			}catch (Exception e) {
				e.printStackTrace();
			}
			
			
		}
		
		return pasajeros;
		
	}

	public List<NacionalidadDTO> buscarNacionalidades() throws Exception {
		String sql="SELECT IDPAIS,NOMBRE FROM T_NACIONALIDAD";
		
		NacionalidadDTO nacionalidad = null;
		List<NacionalidadDTO> nacionalidades = new Vector<NacionalidadDTO>();
			
		ResultSet rs=null;
		Connection 	conexion = DataSourceBD.openConnection();
		PreparedStatement pstm;
		
		try {
			pstm = conexion.prepareStatement(sql);
			rs = pstm.executeQuery();
			
			while(rs.next()){
				
				nacionalidad = new NacionalidadDTO();
				
				nacionalidad.setIdPais(rs.getInt(1));
				nacionalidad.setNombre(rs.getString(2));
				
				nacionalidades.add(nacionalidad);
			}			
			
			pstm.close();
			DataSourceBD.closeConnection(conexion);

		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return nacionalidades;
	}


	public List<String> buscarAsientosOcupados(int idVuelo, String fechaVuelo) throws Exception {
		String sql="select asiento from t_embarque where idreserva in (select idreserva from t_reserva where idvuelo=? AND fechavuelo=?)";
		
		String asiento="";
		List<String> asientosOcupados = new Vector<String>();

		ResultSet rs=null;
		Connection 	conexion = DataSourceBD.openConnection();
		PreparedStatement pstm;
		
		try {
			pstm = conexion.prepareStatement(sql);
			pstm.setInt(1,idVuelo);
			pstm.setString(2,fechaVuelo);
			rs = pstm.executeQuery();
			
			while(rs.next()){
				
				asiento = rs.getString(1);
				asientosOcupados.add(asiento);
				
			}		
			
			pstm.close();
			DataSourceBD.closeConnection(conexion);

		}catch (Exception e) {
			e.printStackTrace();
		}

		
		return asientosOcupados;
	}

	
	public int recuperarCapacidadAvion(int idVuelo) throws Exception {
		String sql="select (select capacidad from t_avion where idavion=(select idavion from t_itinerario where iditinerario=v.iditinerario)) from t_vuelo v where v.idvuelo=?";
		
		int capacidad=0;
		
		ResultSet rs=null;
		Connection 	conexion = DataSourceBD.openConnection();
		PreparedStatement pstm;
		
		try {
			pstm = conexion.prepareStatement(sql);
			pstm.setInt(1,idVuelo);
			rs = pstm.executeQuery();
			
			while(rs.next()){
				
				capacidad = rs.getInt(1);
				
			}		
			
			pstm.close();
			DataSourceBD.closeConnection(conexion);

		}catch (Exception e) {
			e.printStackTrace();
		}

		
		return capacidad;
	}


	public boolean registrarCheckin(List<CheckinDTO> listaCheckinDTO) throws Exception {
		
		String sql="INSERT INTO T_EMBARQUE(ASIENTO,IDPERSONA,IDRESERVA,NUMTICKET) VALUES(?,?,?,?)";
		String sql1="UPDATE T_PERSONAXRESERVA SET ESTADO=2 WHERE IDPERSONA=? AND IDRESERVA=?";
		Connection 	conexion = DataSourceBD.openConnection();	
		
		try {
			
			conexion.setAutoCommit(false);
			
			for(int i=0; i<listaCheckinDTO.size() ;i++){
				
				CheckinDTO checkinDTO = listaCheckinDTO.get(i);
				
				List<Integer> listaIdPersona = buscarInfanteEstadoPendiente(checkinDTO.getIdReserva());
				
				PreparedStatement pstm;
				PreparedStatement pstm1;
				PreparedStatement pstm2 = null;
				
				pstm = conexion.prepareStatement(sql);
				pstm1 = conexion.prepareStatement(sql1);
				
				if(!listaIdPersona.isEmpty()){
					pstm2 = conexion.prepareStatement(sql1);
				}

				pstm.setString(1,checkinDTO.getAsiento());
				pstm.setInt(2,checkinDTO.getIdPersona());
				pstm.setInt(3,checkinDTO.getIdReserva());
				pstm.setString(4,checkinDTO.getNumTicket());
				int fila = pstm.executeUpdate();
				pstm.close();
				
				if(fila!=1){
					conexion.rollback();
					return false;
				}else{
					pstm1.setInt(1,checkinDTO.getIdPersona());
					pstm1.setInt(2,checkinDTO.getIdReserva());
					int fila1 = pstm1.executeUpdate();
					pstm.close();
					
					if(fila1!=1){
						conexion.rollback();
						return false;
					}else{
									
						if(!listaIdPersona.isEmpty()){
							
							pstm2.setInt(1,listaIdPersona.get(0));
							pstm2.setInt(2,checkinDTO.getIdReserva());
							int fila2 = pstm2.executeUpdate();
							pstm.close();
							
							if(fila2!=1){
								conexion.rollback();
								return false;
							}
							
						}
						
					}
					
					
				}
					
			}
			
			conexion.commit();

		}catch (Exception e) {
			conexion.rollback();
			e.printStackTrace();
			return false;
		}
		
		DataSourceBD.closeConnection(conexion);
		return true;
	}


	public boolean acualizarInfoPasajero(PersonaDTO personaDTO)
			throws Exception {
		
		String sql="UPDATE T_PERSONA SET NOMBRE=?, APELLIDOPATERNO=?, APELLIDOMATERNO=?, TIPODOCUMENTO=?, NUMDOCUMENTO=?, NACIONALIDAD=?, GENERO=? WHERE IDPERSONA=?";
		Connection 	conexion = DataSourceBD.openConnection();
		PreparedStatement pstm;
		try {

			pstm = conexion.prepareStatement(sql);
			pstm.setString(1,personaDTO.getNombre());
			pstm.setString(2,personaDTO.getApellidoPaterno());
			pstm.setString(3,personaDTO.getApellidoMaterno());
			pstm.setInt(4,Integer.parseInt(personaDTO.getTipoDocumento()));
			pstm.setString(5,personaDTO.getNumDocumento());
			if(personaDTO.getNacionalidad().equals("")){
				pstm.setNull(6, java.sql.Types.NULL);
			}else{
				pstm.setInt(6,Integer.parseInt(personaDTO.getNacionalidad()));
			}
			
			pstm.setInt(7,Integer.parseInt(personaDTO.getGenero()));
			pstm.setInt(8,Integer.parseInt(personaDTO.getIdPersona()));
			int c = pstm.executeUpdate();
			pstm.close();
			DataSourceBD.closeConnection(conexion);
			if(c!=0){
				return true;
			}else{
				return false;
			} 
		} catch (SQLException e) {			
			e.printStackTrace();
			return false;
		}

	}


	public boolean actualizarCheckin(List<CheckinDTO> listaCheckinDTO)
			throws Exception {
		
		CheckinDTO checkinDTO = listaCheckinDTO.get(0);
		
		String sql="UPDATE T_EMBARQUE SET ASIENTO=? WHERE IDPERSONA=? AND IDRESERVA=?";
		Connection 	conexion = DataSourceBD.openConnection();
		PreparedStatement pstm;
		try {

			pstm = conexion.prepareStatement(sql);
			pstm.setString(1,checkinDTO.getAsiento());
			pstm.setInt(2,checkinDTO.getIdPersona());
			pstm.setInt(3,checkinDTO.getIdReserva());
			
			int c = pstm.executeUpdate();
			pstm.close();
			DataSourceBD.closeConnection(conexion);
			if(c!=0){
				return true;
			}else{
				return false;
			} 
		} catch (SQLException e) {			
			e.printStackTrace();
			return false;
		}
	}
	

	public CheckinDTO obtenerEmbarque(List<CheckinDTO> listaCheckinDTO)throws Exception {
		
		CheckinDTO checkinDTO = listaCheckinDTO.get(0);
		
		String sql="SELECT * FROM T_EMBARQUE WHERE IDPERSONA=? AND IDRESERVA=?";
		Connection 	conexion = DataSourceBD.openConnection();
		PreparedStatement pstm;
		ResultSet rs=null;
		try {

			pstm = conexion.prepareStatement(sql);
			pstm.setInt(1,checkinDTO.getIdPersona());
			pstm.setInt(2,checkinDTO.getIdReserva());
			
			rs = pstm.executeQuery();

			while(rs.next()){
				
				checkinDTO.setIdEmbarque(rs.getInt(1));
				checkinDTO.setAsiento(rs.getString(2));
				checkinDTO.setIdPersona(rs.getInt(3));
				checkinDTO.setIdReserva(rs.getInt(4));
				checkinDTO.setNumTicket(rs.getString(5));
				
				return checkinDTO;
				
			}	
			
			pstm.close();
			DataSourceBD.closeConnection(conexion);
			
		} catch (SQLException e) {			
			e.printStackTrace();
			return null;
		}
		return null;
	}

	
	public boolean verificarNumTicket(String numTicket) throws Exception {
			
		String sql="SELECT IDEMBARQUE FROM T_EMBARQUE WHERE NUMTICKET=?";
		Connection 	conexion = DataSourceBD.openConnection();
		PreparedStatement pstm;
		ResultSet rs=null;
		try {

			pstm = conexion.prepareStatement(sql);
			pstm.setString(1,numTicket);
			
			rs = pstm.executeQuery();

			while(rs.next()){
				return true;
				
			}	
			
			pstm.close();
			DataSourceBD.closeConnection(conexion);
			
		} catch (SQLException e) {			
			e.printStackTrace();
			return false;
		}
		return false;
	}

	public List<Integer> buscarInfanteEstadoPendiente(int idReserva)
			throws Exception {
		String sql="SELECT P.IDPERSONA FROM T_PERSONAXRESERVA PXR INNER JOIN T_PERSONA P ON PXR.IDPERSONA=P.IDPERSONA WHERE PXR.IDRESERVA=? AND PXR.ESTADO=1 AND P.TIPOPERSONA=3";
			
		ResultSet rs=null;
		Connection 	conexion = DataSourceBD.openConnection();
		PreparedStatement pstm;
		
		List<Integer> listaIdPersona = new ArrayList<Integer>();

		try {
			pstm = conexion.prepareStatement(sql);
			pstm.setInt(1,idReserva);
			rs = pstm.executeQuery();
			
			while(rs.next()){	
				listaIdPersona.add(rs.getInt(1));
			
			}			
			
			pstm.close();
			DataSourceBD.closeConnection(conexion);

		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return listaIdPersona;
	}
}