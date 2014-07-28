package com.dis.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import com.dis.bean.VueloDTO;
import com.dis.dao.iface.VueloDAO;
import com.dis.util.DataSourceBD;
import com.dis.util.DateHandler;
import com.dis.util.ParseUtil;

public class MySqlVueloDAO implements VueloDAO {


	public List<VueloDTO> buscarVuelos(String origen, String destino, String fecha) {
		String sql="SELECT v.IDVUELO, v.NUMVUELO, v.HORASALIDA, ru.DURACION, (SELECT NOMBRE FROM t_ciudad WHERE IDCIUDAD=ru.ORIGEN) , (SELECT NOMBRE FROM t_ciudad WHERE IDCIUDAD=ru.DESTINO) FROM t_ruta ru, t_vuelo v, t_itinerario it WHERE it.IDITINERARIO = v.IDITINERARIO AND it.IDRUTA = ru.IDRUTA AND ru.ORIGEN=? AND ru.DESTINO=? AND v.DIAVUELO LIKE ?";

		Date fechaSal = DateHandler.getDate(fecha);
		String strDateFormat = "EEEE";
	    SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);

		ResultSet rs=null;
		Connection 	conexion = DataSourceBD.openConnection();
		PreparedStatement pstm;
		
		VueloDTO vuelo = null;
		List<VueloDTO> vuelos = new Vector<VueloDTO>();

	    String fechaVuelo = sdf.format(fechaSal).toUpperCase();
	    String booleano=ParseUtil.diaToBoolean(fechaVuelo);
	    String fechaQuery = ParseUtil.parseBooleano(booleano);


		try {
			pstm = conexion.prepareStatement(sql);
			pstm.setString(1,buscarCiudad(origen));
			pstm.setString(2,buscarCiudad(destino));
			pstm.setString(3,fechaQuery);
			rs = pstm.executeQuery();
			
			while(rs.next()){
				
				vuelo = new VueloDTO();
				
				vuelo.setIdVuelo(rs.getInt(1));
				vuelo.setNumVuelo(rs.getString(2));
				vuelo.setFechaSalida(fecha);
				vuelo.setHoraSalida(rs.getString(3));
				vuelo.setDuracion(rs.getString(4));
				vuelo.setHoraLlegada(DateHandler.sumarMinutos(vuelo.getHoraSalida(), vuelo.getDuracion()));
				vuelo.setOrigen(rs.getString(5));
				vuelo.setDestino(rs.getString(6));
				
				vuelos.add(vuelo);
			}			
			pstm.close();
			DataSourceBD.closeConnection(conexion);

		}catch (Exception e) {
			e.printStackTrace();
		}

		return vuelos;
	}

	
	public String buscarCiudad(String nombreCiudad) {
		String idCiudad=null;
		String sql="SELECT IDCIUDAD FROM t_ciudad WHERE NOMBRE LIKE ?";
		ResultSet rs=null;
		Connection 	conexion = DataSourceBD.openConnection();
		PreparedStatement pstm;
		try {
			pstm = conexion.prepareStatement(sql);
			pstm.setString(1,nombreCiudad);
			rs = pstm.executeQuery();	
			if(rs.next()){idCiudad=rs.getString(1);}	
			pstm.close();
			DataSourceBD.closeConnection(conexion);	
		}catch (Exception e) {
			e.printStackTrace();
		}
		return idCiudad;
	}

}