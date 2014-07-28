package com.dis.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.dis.action.UsuarioAction;
import com.dis.bean.PersonaDTO;
import com.dis.bean.ReservaDTO;
import com.dis.bean.UsuarioDTO;
import com.dis.bean.VueloDTO;
import com.dis.dao.iface.ReservaDAO;
import com.dis.util.DataSourceBD;
import com.dis.util.DateHandler;
import com.dis.util.Encriptacion;

public class MySqlReservaDAO implements ReservaDAO {
	
	
	public int buscarMillas(int idReserva) {
		int millasRequeridas=0;
		String sql="SELECT ru.DISTANCIA FROM t_ruta ru, t_reserva r, t_vuelo v, t_itinerario it WHERE v.IDVUELO=r.IDVUELO AND  v.IDITINERARIO=it.IDITINERARIO AND ru.IDRUTA = it.IDRUTA AND r.IDRESERVA LIKE ?";
		Connection 	conexion = DataSourceBD.openConnection();
		PreparedStatement pstm;
		ResultSet rs=null;
		try {
			pstm = conexion.prepareStatement(sql);
			pstm.setInt(1,idReserva);
			rs = pstm.executeQuery();
			if(rs.next()){ millasRequeridas=rs.getInt(1);}		
			pstm.close();
			DataSourceBD.closeConnection(conexion);
			
			return millasRequeridas;
		} catch (SQLException e) {			
			e.printStackTrace();
			return 0;
		}
		
	}

	public boolean canjearMillas(int idReserva) {
			String sql="UPDATE t_reserva SET ESTADO='PAGADO', TIPOPAGO='CANJE', FECHAPAGO = now() WHERE IDRESERVA LIKE ?";
			Connection 	conexion = DataSourceBD.openConnection();
			PreparedStatement pstm;
			try {
				pstm = conexion.prepareStatement(sql);
				pstm.setInt(1,idReserva);
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
	
	public boolean actualizarMillasPasajero(UsuarioDTO usuario, int idReserva){
		
		if(canjearMillas(idReserva)){
			int millasDeReserva=buscarMillas(idReserva);
			String sql="UPDATE t_usuario SET MILLAS=? WHERE EMAIL LIKE ?";
			
			Connection 	conexion = DataSourceBD.openConnection();
			PreparedStatement pstm;
			try {
				pstm = conexion.prepareStatement(sql);
				pstm.setInt(1,usuario.getMillas()-millasDeReserva);
				UsuarioAction obj = new UsuarioAction();
				obj.actualizarMillasSesion(usuario.getMillas()-millasDeReserva);
				pstm.setString(2,usuario.getEmail());
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


	public boolean comprarBoleto(String idReserva,String tipoPago) {
		String sql="UPDATE t_reserva SET ESTADO = 'PAGADO', TIPOPAGO = ?, FECHAPAGO = now() WHERE IDRESERVA LIKE ?";
		try {
			Connection conexion = DataSourceBD.openConnection();
			PreparedStatement pstm = conexion.prepareStatement(sql);
			pstm.setString(2, idReserva);	
			if(tipoPago.equals("prescencial"))
				pstm.setString(1, "CONTADO");
			else
				pstm.setString(1, "CREDITO");
			int i = pstm.executeUpdate();

			if(i>0){
				System.out.println("Se registró correctamente la compra de la reserva N° "+idReserva);
				return true;
			}
			else return false;
		} catch (SQLException e) {	
			e.printStackTrace();
			return false;
		} 
	}


	public ReservaDTO buscarReserva(String idReserva) {
		ResultSet rs = null;
		ReservaDTO reserva = new ReservaDTO();
		String sql="SELECT r.IDRESERVA, v.NUMVUELO, r.MONTOTOTAL, ru.DISTANCIA FROM t_reserva r, t_vuelo v, t_ruta ru , t_itinerario it WHERE r.IDVUELO = v.IDVUELO AND v.IDITINERARIO = it.IDITINERARIO AND it.IDRUTA = ru.IDRUTA AND r.IDRESERVA LIKE ?";
		try {
			Connection conexion = DataSourceBD.openConnection();
			PreparedStatement pstm = conexion.prepareStatement(sql);
			pstm.setString(1, idReserva);			
			rs = pstm.executeQuery();
			if (rs.next()) {		
				reserva.setIdReserva(rs.getString(1));			
				reserva.setVuelo(rs.getString(2));
				reserva.setTotalPagar(rs.getString(3));
				reserva.setMillas(rs.getInt(4));
			}else{
				reserva=null;
			}
			DataSourceBD.closeConnection(conexion);
		} catch (SQLException e) {	
			e.printStackTrace();
			return null;
		} 
		return reserva;
	}

	
	public List<ReservaDTO> buscarReservaPorUsuario(String email) {
		Format formatter = new SimpleDateFormat("dd/MM/yyyy");
		ResultSet rs = null;
		List<ReservaDTO> reservas = new ArrayList<ReservaDTO>();
		String sql="SELECT r.IDRESERVA, r.FECHAVUELO, v.HORASALIDA, ru.DURACION, v.NUMVUELO, r.MONTOTOTAL, ru.DISTANCIA FROM t_usuario u, t_reserva r, t_vuelo v, t_ruta ru, t_itinerario it WHERE u.IDPERSONA = r.IDPERSONA AND r.IDVUELO = v.IDVUELO AND v.IDITINERARIO = it.IDITINERARIO AND ru.IDRUTA = it.IDRUTA AND u.EMAIL LIKE ? AND r.ESTADO LIKE 'PENDIENTE'";
		try {
			Connection conexion = DataSourceBD.openConnection();
			PreparedStatement pstm = conexion.prepareStatement(sql);
			pstm.setString(1, email);			
			rs = pstm.executeQuery();
			while (rs.next()) {
				ReservaDTO reserva = new ReservaDTO();
				reserva.setIdReserva(rs.getString(1));			
				reserva.setFechaReserva(formatter.format(rs.getDate(2)));
				reserva.setHoraSalida(rs.getString(3));
				reserva.setHoraLlegada(DateHandler.sumarMinutos(reserva.getHoraSalida(), rs.getString(4)));	
				reserva.setVuelo(rs.getString(5));
				reserva.setCabina("ECONOMICA");
				reserva.setTotalPagar(rs.getString(6));
				reserva.setMillas(rs.getInt(7));
				reservas.add(reserva);
			}
			DataSourceBD.closeConnection(conexion);
		} catch (SQLException e) {	
			e.printStackTrace();
			return null;
		} 
		return reservas;
	}
	

	public double obtenerTarifa(String idvuelo) {
		double tarifa=0;
		ResultSet rs = null;
		String sql="select tarifa from t_vuelo where idvuelo=?;";
			try {
				Connection conexion = DataSourceBD.openConnection();
				PreparedStatement pstm = conexion.prepareStatement(sql);
				pstm.setString(1, idvuelo);			
				rs = pstm.executeQuery();
				if (rs.next()) {		
					tarifa=rs.getDouble(1);		
				}
				DataSourceBD.closeConnection(conexion);
			} catch (SQLException e) {	
				e.printStackTrace();
				return -1;
			} 
			return tarifa;
			
		
	}


	public String obtenerOrigendeVuelo(String idvuelo) {
		String origen="";
		ResultSet rs = null;
		String sql="select (select nombre from t_ciudad where idciudad=origen) from t_ruta where idruta=(select idruta from t_itinerario where iditinerario=(select iditinerario from t_vuelo where idvuelo=?))";
			try {
				Connection conexion = DataSourceBD.openConnection();
				PreparedStatement pstm = conexion.prepareStatement(sql);
				pstm.setString(1, idvuelo);			
				rs = pstm.executeQuery();
				if (rs.next()) {		
					origen=rs.getString(1);		
				}
				DataSourceBD.closeConnection(conexion);
			} catch (SQLException e) {	
				e.printStackTrace();
				return "";
			} 
			
			
			return origen;
	}

	public String obtenerDestinodeVuelo(String idvuelo) {
		String destino="";
		ResultSet rs = null;
		String sql="select (select nombre from t_ciudad where idciudad=destino) from t_ruta  where idruta=(select idruta from t_itinerario where iditinerario=(select iditinerario from t_vuelo where idvuelo=?))";
			try {
				Connection conexion = DataSourceBD.openConnection();
				PreparedStatement pstm = conexion.prepareStatement(sql);
				pstm.setString(1, idvuelo);			
				rs = pstm.executeQuery();
				if (rs.next()) {		
					destino=rs.getString(1);		
				}
				DataSourceBD.closeConnection(conexion);
			} catch (SQLException e) {	
				e.printStackTrace();
				return "";
			} 
			
			
			return destino;
	}


	public String obtenerNumVuelo(String idvuelo) {

		String numvuelo="";
		ResultSet rs = null;
		String sql="select numvuelo from  t_vuelo where idvuelo=?";
			try {
				Connection conexion = DataSourceBD.openConnection();
				PreparedStatement pstm = conexion.prepareStatement(sql);
				pstm.setString(1, idvuelo);			
				rs = pstm.executeQuery();
				if (rs.next()) {		
					numvuelo=rs.getString(1);		
				}
				DataSourceBD.closeConnection(conexion);
			} catch (SQLException e) {	
				e.printStackTrace();
				return "";
			} 
			return numvuelo;
	}
	
	public boolean validarIdReserva(String idReserva){
		String sql="SELECT COUNT(IDRESERVA) FROM t_reserva WHERE IDRESERVA = ?";
		ResultSet rs = null;
		int value=0;
		try {
			Connection conexion = DataSourceBD.openConnection();
			PreparedStatement pstm = conexion.prepareStatement(sql);
			pstm.setString(1, idReserva);			
			rs = pstm.executeQuery();
			if (rs.next()) {		
				value=rs.getInt(1);		
			}
			DataSourceBD.closeConnection(conexion);
		} catch (SQLException e) {	
			e.printStackTrace();
			return false;
		}
		if(value==1) return true;
		else return false;
	}
	

	public boolean registroReserva(ReservaDTO reserva) {
		
		String sql="insert into t_reserva(idreserva,fechareserva,estado,montototal,fechavuelo,idpersona,idvuelo) values(?,now(),1,?,?,?,?)";
		java.sql.Date fecVueFormatoDate=null;
		try {
			SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");			
			fecVueFormatoDate = new java.sql.Date(sdf.parse(reserva.getFechaVuelo()).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int idReserva = 0;
		do{
			idReserva=Encriptacion.randomNumbers(10000000, 99999999);
			System.out.println("Reserva registrada: "+idReserva);
		}while(validarIdReserva(idReserva+""));
		
		Connection 	conexion = DataSourceBD.openConnection();
		PreparedStatement pstm;
		try {
			pstm = conexion.prepareStatement(sql);
			pstm.setInt(1, idReserva);
			pstm.setString(2,reserva.getTotalPagar());		
			pstm.setDate(3,fecVueFormatoDate);
			pstm.setString(4,reserva.getIdUsuario());
			pstm.setString(5, reserva.getVuelo());
			pstm.executeUpdate();
			pstm.close();
			DataSourceBD.closeConnection(conexion);
			
		} catch (SQLException e) {			
			e.printStackTrace();
			return false;
		}
		return false;		
	}


	public String obtenerHoraVuelo(String idvuelo) {
		String horasalida="";
		ResultSet rs = null;
		String sql="select horasalida from t_vuelo where idvuelo=?";
			try {
				Connection conexion = DataSourceBD.openConnection();
				PreparedStatement pstm = conexion.prepareStatement(sql);
				pstm.setString(1, idvuelo);			
				rs = pstm.executeQuery();
				if (rs.next()) {		
					horasalida=rs.getString(1);		
				}
				DataSourceBD.closeConnection(conexion);
			} catch (SQLException e) {	
				e.printStackTrace();
				return "";
			} 
			
			
			return horasalida;
	}

	public String obtenerTiempoViaje(String idvuelo) {
		String tiempoviaje="";
		ResultSet rs = null;
		String sql="select (select duracion from t_ruta where idruta=(select idruta from t_itinerario where iditinerario=(select iditinerario from t_vuelo where idvuelo=?)))";
			try {
				Connection conexion = DataSourceBD.openConnection();
				PreparedStatement pstm = conexion.prepareStatement(sql);
				pstm.setString(1, idvuelo);			
				rs = pstm.executeQuery();
				if (rs.next()) {		
					tiempoviaje=rs.getString(1);		
				}
				DataSourceBD.closeConnection(conexion);
			} catch (SQLException e) {	
				e.printStackTrace();
				return "";
			} 
			
			
			return tiempoviaje;
	}

	public boolean registroPersonaxReserva(String idreserva, String idpersona,
			String descripcion) {
		String sql="insert into t_personaxreserva(idreserva,idpersona,descripcion,estado) values(?,?,?,'1')";
	
	Connection 	conexion = DataSourceBD.openConnection();
	PreparedStatement pstm;
	try {
		pstm = conexion.prepareStatement(sql);
		pstm.setString(1,idreserva);
		pstm.setString(2,idpersona);
		pstm.setString(3,descripcion);
		pstm.executeUpdate();
		pstm.close();
		DataSourceBD.closeConnection(conexion);
		return false;
		
	} catch (SQLException e) {			
		e.printStackTrace();
		return false;
	}
	
	}

	public String obtenerUltimaReserva() {
		String ultimaReserva="";
		ResultSet rs = null;
		String sql="select idreserva from t_reserva where fechareserva=(select max(fechareserva) from t_reserva )";
			try {
				Connection conexion = DataSourceBD.openConnection();
				PreparedStatement pstm = conexion.prepareStatement(sql);		
				rs = pstm.executeQuery();
				if (rs.next()) {		
					ultimaReserva=rs.getString(1);		
				}
				DataSourceBD.closeConnection(conexion);
				
			} catch (SQLException e) {	
				e.printStackTrace();
				return "";
				
			} 
			return ultimaReserva;
	}


	public String obtenerUltimaPersona() {
		String ultimaPersona="";
		ResultSet rs = null;
		String sql="select max(idpersona) from t_persona";
			try {
				Connection conexion = DataSourceBD.openConnection();
				PreparedStatement pstm = conexion.prepareStatement(sql);		
				rs = pstm.executeQuery();
				if (rs.next()) {		
					ultimaPersona=rs.getString(1);		
				}
				DataSourceBD.closeConnection(conexion);
				
			} catch (SQLException e) {	
				e.printStackTrace();
				return "";
				
			} 
			
			
			System.out.println(ultimaPersona);
			return ultimaPersona;
	}


	public VueloDTO obtenerVuelo(int idReserva) {
		VueloDTO vuelo=new VueloDTO();
		String sql="SELECT V.IDVUELO,V.NUMVUELO,R.FECHAVUELO,C.NOMBRE ORIGEN,V.HORASALIDA,C1.NOMBRE DESTINO,RU.DURACION,C.AEROPUERTO,C1.AEROPUERTO FROM T_RESERVA R INNER JOIN (T_VUELO V INNER JOIN (T_ITINERARIO I INNER JOIN (T_RUTA RU INNER JOIN T_CIUDAD C ON RU.ORIGEN=C.IDCIUDAD INNER JOIN T_CIUDAD C1 ON RU.DESTINO=C1.IDCIUDAD) ON I.IDRUTA=RU.IDRUTA) ON V.IDITINERARIO=I.IDITINERARIO) ON R.IDVUELO=V.IDVUELO WHERE R.IDRESERVA=?";
		Connection 	conexion = DataSourceBD.openConnection();
		PreparedStatement pstm;
		ResultSet rs=null;
		try {
			pstm = conexion.prepareStatement(sql);
			pstm.setInt(1,idReserva);
			rs = pstm.executeQuery();
			if(rs.next()){ 
				
				vuelo.setIdVuelo(rs.getInt(1));			
				vuelo.setNumVuelo(rs.getString(2));
				vuelo.setFechaSalida(rs.getString(3));
				vuelo.setOrigen(rs.getString(4));
				vuelo.setHoraSalida(rs.getString(5));
				vuelo.setDestino(rs.getString(6));
				vuelo.setDuracion(rs.getString(7));
				vuelo.setHoraLlegada(DateHandler.sumarMinutos(vuelo.getHoraSalida(), vuelo.getDuracion()));
				vuelo.setOrigenAeropuerto(rs.getString(8));
				vuelo.setDestinoAeropuerto(rs.getString(9));
				
				}else{
					vuelo=null;
				}
			pstm.close();
			DataSourceBD.closeConnection(conexion);
			return vuelo;
		} catch (SQLException e) {			
			e.printStackTrace();
			return null;
		}
	
	}
	

	public List<PersonaDTO> obtenerPersonas(int idReserva) {
		ResultSet rs = null;
		List<PersonaDTO> personas = new ArrayList<PersonaDTO>();
		String sql="select p.idpersona,p.nombre,p.apellidopaterno,p.apellidomaterno,p.tipodocumento,p.numdocumento,p.fechanacimiento,p.tipopersona,p.genero,p.nacionalidad,pxr.estado from t_persona p inner join t_personaxreserva pxr on p.idpersona=pxr.idpersona where pxr.idreserva=?";
		
		try {
			Connection 	conexion = DataSourceBD.openConnection();
			PreparedStatement pstm;
			pstm = conexion.prepareStatement(sql);
			pstm.setInt(1,idReserva);
			rs = pstm.executeQuery();
			
			while(rs.next()){ 
				PersonaDTO persona = new PersonaDTO();
				persona.setIdPersona(rs.getString(1));		
				persona.setNombre(rs.getString(2));
				persona.setApellidoPaterno(rs.getString(3));
				persona.setApellidoMaterno(rs.getString(4));
				persona.setTipoDocumento(rs.getString(5));
				persona.setNumDocumento(rs.getString(6));
				persona.setFechaNacimiento(rs.getString(7));
				persona.setTipoPersona(rs.getString(8));
				persona.setGenero(rs.getString(9));
				persona.setNacionalidad(rs.getString(10));
				persona.setEstadoCheckin(rs.getString(11));
				personas.add(persona);
				}
			pstm.close();
			DataSourceBD.closeConnection(conexion);
			return personas;
		} catch (SQLException e) {			
			e.printStackTrace();
			return null;
		}
	}

	
	public List<ReservaDTO> buscarReservaPagadaPorUsuario(String email) {
		Format formatter = new SimpleDateFormat("dd/MM/yyyy");
		ResultSet rs = null;
		List<ReservaDTO> reservas = new ArrayList<ReservaDTO>();
		String sql="SELECT r.IDRESERVA, r.FECHARESERVA, v.HORASALIDA, ru.DURACION, v.NUMVUELO, r.MONTOTOTAL, ru.DISTANCIA, r.FECHAPAGO, r.FECHAVUELO FROM t_usuario u, t_reserva r, t_vuelo v INNER JOIN (T_ITINERARIO I INNER JOIN T_RUTA RU ON I.IDRUTA=RU.IDRUTA) ON V.IDITINERARIO=I.IDITINERARIO WHERE u.IDPERSONA = r.IDPERSONA AND r.IDVUELO = v.IDVUELO AND u.EMAIL LIKE ? AND r.ESTADO LIKE 'PAGADO' AND r.FECHAVUELO >= CURDATE()";
		try {
			Connection conexion = DataSourceBD.openConnection();
			PreparedStatement pstm = conexion.prepareStatement(sql);
			pstm.setString(1, email);			
			rs = pstm.executeQuery();
			while (rs.next()) {
				ReservaDTO reserva = new ReservaDTO();
				reserva.setIdReserva(rs.getString(1));			
				reserva.setFechaReserva(formatter.format(rs.getDate(2)));
				reserva.setHoraSalida(rs.getString(3));
				reserva.setHoraLlegada(DateHandler.sumarMinutos(reserva.getHoraSalida(), rs.getString(4)));	
				reserva.setVuelo(rs.getString(5));
				reserva.setCabina("ECONOMICA");
				reserva.setTotalPagar(rs.getString(6));
				reserva.setMillas(rs.getInt(7));
				reserva.setFechaPago(formatter.format(rs.getDate(8)));
				reserva.setFechaVuelo(formatter.format(rs.getDate(9)));
				reservas.add(reserva);
			}
			DataSourceBD.closeConnection(conexion);
		} catch (SQLException e) {	
			e.printStackTrace();
			return null;
		} 
		if(reservas.size()!=0){
			return reservas;
		}else{
			return null;
		}
		
	}

	

}
