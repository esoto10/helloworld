package com.dis.action;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.dis.bean.LogDTO;
import com.dis.bean.PersonaDTO;
import com.dis.bean.ReservaDTO;
import com.dis.bean.UsuarioDTO;
import com.dis.bean.VueloDTO;
import com.dis.service.PaqueteBusinessDelegate;
import com.dis.service.ReservaService;
import com.dis.service.UsuarioService;
import com.dis.util.DateHandler;
import com.dis.util.Encriptacion;
import com.dis.util.Logger;
import com.dis.util.MailUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class ReservaAction extends ActionSupport{
	private UsuarioDTO usuario;
	private ReservaDTO reserva;
	private String mensaje;
	private String tipoRpta;
	private String tipoBusqueda;
	private String tipoPago;
	private String tknPago;
	private String millas;
	private String idVuelo;
	private double tarifaIda;
	private double tarifaVuelta;

	private List<ReservaDTO> reservas;
	private double precioAdulto;
	private double precioNino;
	private double precioTotal;
	private int cantidadAdulto;
	private int cantidadInfante;
	private int cantidadNino;
	private PersonaDTO persona;
	private List<PersonaDTO> personas = new ArrayList<PersonaDTO>();
	VueloDTO vueloida;
	VueloDTO vuelovuelta;
	
	private String [] nombre;
	private String [] apellidoPaterno;
	private String [] apellidoMaterno;
	private String [] tipoDocumento;
	private String [] numDocumento;
	private String [] tipoPersona;
	private String [] genero;
	private String [] nacionalidad;
	private String [] fechaNacimiento;
	
	private String idvueloida;
	private String idvuelovuelta;
	private String fechaIda;
	private String fechaVuelta;


	ReservaService reservaService=PaqueteBusinessDelegate.getReservaService();
	UsuarioService usuarioService=PaqueteBusinessDelegate.getUsuarioService();
	
	
	
	public double getTarifaIda() {
		return tarifaIda;
	}
	public void setTarifaIda(double tarifaIda) {
		this.tarifaIda = tarifaIda;
	}
	public double getTarifaVuelta() {
		return tarifaVuelta;
	}
	public void setTarifaVuelta(double tarifaVuelta) {
		this.tarifaVuelta = tarifaVuelta;
	}
	public double getPrecioAdulto() {
		return precioAdulto;
	}
	public void setPrecioAdulto(double precioAdulto) {
		this.precioAdulto = precioAdulto;
	}
	public double getPrecioNino() {
		return precioNino;
	}
	public void setPrecioNino(double precioNino) {
		this.precioNino = precioNino;
	}
	public double getPrecioTotal() {
		return precioTotal;
	}
	public void setPrecioTotal(double precioTotal) {
		this.precioTotal = precioTotal;
	}
	public int getCantidadAdulto() {
		return cantidadAdulto;
	}
	public void setCantidadAdulto(int cantidadAdulto) {
		this.cantidadAdulto = cantidadAdulto;
	}
	public int getCantidadInfante() {
		return cantidadInfante;
	}
	public void setCantidadInfante(int cantidadInfante) {
		this.cantidadInfante = cantidadInfante;
	}
	public int getCantidadNino() {
		return cantidadNino;
	}
	public void setCantidadNino(int cantidadNino) {
		this.cantidadNino = cantidadNino;
	}
	public PersonaDTO getPersona() {
		return persona;
	}
	public void setPersona(PersonaDTO persona) {
		this.persona = persona;
	}
	public List<PersonaDTO> getPersonas() {
		return personas;
	}
	public void setPersonas(List<PersonaDTO> personas) {
		this.personas = personas;
	}
	public VueloDTO getVueloida() {
		return vueloida;
	}
	public void setVueloida(VueloDTO vueloida) {
		this.vueloida = vueloida;
	}
	public VueloDTO getVuelovuelta() {
		return vuelovuelta;
	}
	public void setVuelovuelta(VueloDTO vuelovuelta) {
		this.vuelovuelta = vuelovuelta;
	}
	public String[] getNombre() {
		return nombre;
	}
	public void setNombre(String[] nombre) {
		this.nombre = nombre;
	}
	public String[] getApellidoPaterno() {
		return apellidoPaterno;
	}
	public void setApellidoPaterno(String[] apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}
	public String[] getApellidoMaterno() {
		return apellidoMaterno;
	}
	public void setApellidoMaterno(String[] apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}
	public String[] getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(String[] tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	public String[] getNumDocumento() {
		return numDocumento;
	}
	public void setNumDocumento(String[] numDocumento) {
		this.numDocumento = numDocumento;
	}
	public String[] getTipoPersona() {
		return tipoPersona;
	}
	public void setTipoPersona(String[] tipoPersona) {
		this.tipoPersona = tipoPersona;
	}
	public String[] getGenero() {
		return genero;
	}
	public void setGenero(String[] genero) {
		this.genero = genero;
	}
	public String[] getNacionalidad() {
		return nacionalidad;
	}
	public void setNacionalidad(String[] nacionalidad) {
		this.nacionalidad = nacionalidad;
	}
	public String[] getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(String[] fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	public String getIdvueloida() {
		return idvueloida;
	}
	public void setIdvueloida(String idvueloida) {
		this.idvueloida = idvueloida;
	}
	public String getIdvuelovuelta() {
		return idvuelovuelta;
	}
	public void setIdvuelovuelta(String idvuelovuelta) {
		this.idvuelovuelta = idvuelovuelta;
	}
	public String getFechaIda() {
		return fechaIda;
	}
	public void setFechaIda(String fechaIda) {
		this.fechaIda = fechaIda;
	}
	public String getFechaVuelta() {
		return fechaVuelta;
	}
	public void setFechaVuelta(String fechaVuelta) {
		this.fechaVuelta = fechaVuelta;
	}
	public ReservaService getReservaService() {
		return reservaService;
	}
	public void setReservaService(ReservaService reservaService) {
		this.reservaService = reservaService;
	}
	public UsuarioService getUsuarioService() {
		return usuarioService;
	}
	public void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}
	public String getMillas() {
		return millas;
	}
	public void setMillas(String millas) {
		this.millas = millas;
	}
	public String getIdVuelo() {
		return idVuelo;
	}
	public void setIdVuelo(String idVuelo) {
		this.idVuelo = idVuelo;
	}
	public String getTknPago() {
		return tknPago;
	}
	public void setTknPago(String tknPago) {
		this.tknPago = tknPago;
	}
	public String getTipoPago() {
		return tipoPago;
	}
	public void setTipoPago(String tipoPago) {
		this.tipoPago = tipoPago;
	}
	public String getTipoBusqueda() {
		return tipoBusqueda;
	}
	public void setTipoBusqueda(String tipoBusqueda) {
		this.tipoBusqueda = tipoBusqueda;
	}
	public List<ReservaDTO> getReservas() {
		return reservas;
	}
	public void setReservas(List<ReservaDTO> reservas) {
		this.reservas = reservas;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public String getTipoRpta() {
		return tipoRpta;
	}
	public void setTipoRpta(String tipoRpta) {
		this.tipoRpta = tipoRpta;
	}
	public UsuarioDTO getUsuario() {
		return usuario;
	}
	public void setUsuario(UsuarioDTO usuario) {
		this.usuario = usuario;
	}
	public ReservaDTO getReserva() {
		return reserva;
	}
	public void setReserva(ReservaDTO reserva) {
		this.reserva = reserva;
	}
	
	public String comprar(){
		if(getTipoBusqueda()!=null){
			return buscar();
		}else{
			
			Map<String, Object> session = ActionContext.getContext().getSession();
			if(session.get("tknPago").toString().equals(getTknPago())){
				if(reservaService.comprarBoleto(reserva.getIdReserva(),getTipoPago())){
					MailUtil.enviarMail(session.get("email").toString(), session.get("nombre").toString(), "Compra de Boleto", "Su compra de boleto fue realizada con éxito. Para hacer el respectivo CheckIn, le pedimos que inicie sesión en el sistema y siga los pasos correspondientes. <br><br>LlamitaAirlines");
					setMensaje("Reserva pagada con éxito.");
					setTipoRpta("success");	
					Logger.info(new LogDTO(session.get("id").toString(),"Registro de Pago de Reserva"));
					
				}else{
					setMensaje("Error al actualizar su pago. La transacción no es segura.");
					setTipoRpta("fail");
				}
							
			}else{
				setMensaje("Error al actualizar su pago. Por favor, actualice su explorador.");
				setTipoRpta("fail");
			}
			return "comprar";
		}
	}
	
	public void falloCompra(){
		setTipoBusqueda("porUsuario");
		buscar();
	}
	
	public String buscar(){
		if(getTipoBusqueda().equals("porUsuario")){			
			Map<String, Object> session = ActionContext.getContext().getSession();
			String email=session.get("email").toString();
			setReservas(reservaService.buscarReservaPorUsuario(email));
			return "buscarPorUsuario";	
		}else if(getTipoBusqueda().equals("pagoPayPal")){			
			Map<String, Object> session = ActionContext.getContext().getSession();
			setReserva(reservaService.buscarReserva(getReserva().getIdReserva()));
			session.put("tknPago",Encriptacion.tokenGenerator(12, Encriptacion.MD5));
			if(getReserva()!=null){	
				String cadenaConfirmacion="http://localhost:8080/AgenciaVuelos/confirmarTransaccion.html?tknPago="+session.get("tknPago")+"&idReserva="+getReserva().getIdReserva()+"&tipoPago="+getTipoPago();
				MailUtil.enviarMail(session.get("email").toString(), session.get("nombre").toString(), "Confirmación de Compra", "Se ha realizado una compra a través del sistema, por favor ingrese al siguiente <a href='"+ cadenaConfirmacion +"'>enlace</a> para poder confirmar su transacción.");
				Logger.info(new LogDTO(session.get("id").toString(),"Solicitud Compra de Reserva (crédito)"));
				
				return "payPal";
			}else{
				setMensaje("Error al cargar su reserva, por favor reintente");
				setTipoRpta("fail");
				return "buscarPorUsuario";
			}
		}
		return "buscarPorUsuario";
	}
	
	public String canjearMillas(){
		boolean estado = reservaService.actualizarMillasPasajero(getUsuario(),Integer.parseInt(getReserva().getIdReserva())) ;
		if(estado){
			setMensaje("Su canje ha sido realizado con exito");
			setTipoRpta("success");
			Map<String, Object> session = ActionContext.getContext().getSession();
			Logger.info(new LogDTO(session.get("id").toString(),"Canje Millas"));
			 UsuarioAction objUsuarioAction = new UsuarioAction();
			 objUsuarioAction.actualizarMillasSesion(Integer.parseInt(millas));
		}else{
			setMensaje("Hubo un error al procesar los datos, intente nuevamente");
			setTipoRpta("fail"); 
		}
		return "canjeMillas";
	}
	
	public String prescencial(){
		setIdVuelo(idVuelo);
		setReserva(reserva);
		Map<String, Object> session = ActionContext.getContext().getSession();
		setReserva(reservaService.buscarReserva(getReserva().getIdReserva()));
		session.put("tknPago",Encriptacion.tokenGenerator(12, Encriptacion.MD5));
		if(getReserva()!=null){
			String cadenaConfirmacion ="http://localhost:8080/AgenciaVuelos/confirmarTransaccion.html?tknPago="+session.get("tknPago")+"&idReserva="+getReserva().getIdReserva()+"&tipoPago="+getTipoPago();
			MailUtil.enviarMail(session.get("email").toString(), session.get("nombre").toString(), "Confirmación de Compra", "Se ha realizado una compra a través del sistema, por favor ingrese al siguiente <a href='"+ cadenaConfirmacion +"'>enlace</a> para poder confirmar su transacción.");
			Logger.info(new LogDTO(session.get("id").toString(),"Solicitud Compra de Reserva (presencial)"));
		}
		return "mostrarPrescencial";
	
	
	}
	
	public String pasajero(){
		setCantidadAdulto(0);
		setCantidadInfante(0);
		setCantidadNino(0);
		
		int c = getGenero().length;
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		setTipoPersona(new String [getNombre().length]);
		
		for(int i = 0 ; i<c ; i++){
			Date fechaNac;
			int edad=0;
			try {
				fechaNac = df.parse(fechaNacimiento[i]);
				edad = DateHandler.getEdad(fechaNac);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			if(edad<3)
				tipoPersona[i]="3";
			else if (edad<18)
				tipoPersona[i]="2";
			else
				tipoPersona[i]="1";
			persona= new PersonaDTO();
			persona.setGenero(genero[i]);
			persona.setNombre(nombre[i]);
			persona.setApellidoPaterno(apellidoPaterno[i]);
			persona.setApellidoMaterno(apellidoMaterno[i]);
			persona.setTipoDocumento(tipoDocumento[i]);
			persona.setNumDocumento(numDocumento[i]);
			persona.setTipoPersona(tipoPersona[i]);
			persona.setNacionalidad(nacionalidad[i]);
			persona.setFechaNacimiento(fechaNacimiento[i]);
			personas.add(getPersona());
			
		}
	
		Map<String, Object> session = ActionContext.getContext().getSession();

		setIdvueloida(session.get("vueloIda").toString());
		setFechaIda(session.get("fechaIda").toString());
		
		if(session.get("vueloVuelta")!=null){
			setIdvuelovuelta(session.get("vueloVuelta").toString());
		}
		
		if(session.get("fechaVuelta")!=null){
			setFechaVuelta(session.get("fechaVuelta").toString());
		}
		
	
		setTarifaIda(0);
		setTarifaVuelta(0);
		
		double tIda=reservaService.obtenerTarifa(getIdvueloida());
		setTarifaIda(tIda);

		
		if(getIdvuelovuelta()!=null){
			double tVuelta=reservaService.obtenerTarifa(getIdvuelovuelta());
			double total=(tIda+tVuelta)*0.9; //Descuento por ida y vuelta
			setTarifaIda(total/2);
			setTarifaVuelta(total/2);
		}

		setPrecioAdulto(0);
		setPrecioNino(0);
		
		double precioTotalIda=0;
		double precioTotalVuelta=0;
		
		
		for(int i=0; i<getPersonas().size() ;i++){
			if(personas.get(i).getTipoPersona().equals("1")){
				setCantidadAdulto(getCantidadAdulto()+1);
				setPrecioAdulto(getPrecioAdulto()+getTarifaIda()+getTarifaVuelta());
				precioTotalIda=precioTotalIda+getTarifaIda();
				precioTotalVuelta=precioTotalVuelta+getTarifaVuelta();
			}
			if(personas.get(i).getTipoPersona().equals("2")){
				setCantidadNino(getCantidadNino()+1);
				setPrecioNino(getPrecioNino()+getTarifaIda()+getTarifaVuelta());
				precioTotalIda=precioTotalIda+getTarifaIda();
				precioTotalVuelta=precioTotalVuelta+getTarifaVuelta();
			}
			if(personas.get(i).getTipoPersona().equals("3")){
				setCantidadInfante(getCantidadInfante()+1);
			}
		
		}
		
		setPrecioTotal(getPrecioNino()+getPrecioAdulto());
		
		session.put("cantidadAdulto",getCantidadAdulto());
		session.put("cantidadNino",getCantidadNino());
		session.put("cantidadInfante",getCantidadInfante());
		session.put("precioAdulto", getPrecioAdulto());
		session.put("precioNino", getPrecioNino());
		session.put("precioTotal", getPrecioTotal());
		session.put("precioTotalIda", precioTotalIda);
		session.put("precioTotalVuelta", precioTotalVuelta);
		
		vueloida=new VueloDTO();
		Date fechaVuelo = DateHandler.getDate(getFechaIda());
	    SimpleDateFormat sdf = new SimpleDateFormat("EEEE");	
	    SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
	    String origen = reservaService.obtenerOrigendeVuelo(getIdvueloida());
	    String destino = reservaService.obtenerDestinodeVuelo(getIdvueloida());
	    String horavuelosalida = reservaService.obtenerHoraVuelo(getIdvueloida());
	    String tiempoviaje = reservaService.obtenerTiempoViaje(getIdvueloida());
	    String horavuelollegada = DateHandler.sumarMinutos(horavuelosalida, tiempoviaje);
	    horavuelosalida=DateHandler.showtime(horavuelosalida);
	    horavuelollegada=DateHandler.showtime(horavuelollegada);
	    
	    
		vueloida.setFechaSalida(sdf.format(fechaVuelo).toUpperCase()+" "+sdf1.format(fechaVuelo));
		vueloida.setOrigen(horavuelosalida+" "+origen+" ("+origen.substring(0,3).toUpperCase()+")");
		vueloida.setDestino(horavuelollegada+" "+destino+" ("+destino.substring(0,3).toUpperCase()+")");
		vueloida.setNumVuelo(reservaService.obtenerNumVuelo(getIdvueloida()));
		
		if(!getIdvuelovuelta().equals("")){
			
			
			fechaVuelo = DateHandler.getDate(getFechaVuelta());
			origen = reservaService.obtenerOrigendeVuelo(getIdvuelovuelta());
			destino = reservaService.obtenerDestinodeVuelo(getIdvuelovuelta());
			horavuelosalida = reservaService.obtenerHoraVuelo(getIdvuelovuelta());
			tiempoviaje = reservaService.obtenerTiempoViaje(getIdvuelovuelta());
			horavuelollegada = DateHandler.sumarMinutos(horavuelosalida, tiempoviaje);
			horavuelosalida=DateHandler.showtime(horavuelosalida);
			horavuelollegada=DateHandler.showtime(horavuelollegada);
			
			
			vuelovuelta= new VueloDTO();
			vuelovuelta.setFechaSalida(sdf.format(fechaVuelo).toUpperCase()+" "+sdf1.format(fechaVuelo));
			vuelovuelta.setOrigen(horavuelosalida+" "+origen+" ("+origen.substring(0,3).toUpperCase()+")");
			vuelovuelta.setDestino(horavuelollegada+" "+destino+" ("+destino.substring(0,3).toUpperCase()+")");
			vuelovuelta.setNumVuelo(reservaService.obtenerNumVuelo(getIdvuelovuelta()));
		}
		
		session.put("personas", personas);
		
	
		
		
		return "pasajero";
	}
	
	@SuppressWarnings("unchecked")
	public String registro(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		reserva=new ReservaDTO();
		reserva.setTotalPagar(session.get("precioTotalIda").toString());
		reserva.setFechaVuelo(session.get("fechaIda").toString());
		reserva.setIdUsuario(session.get("id").toString());
		reserva.setVuelo(session.get("vueloIda").toString());
		reservaService.registroReserva(reserva);
		
		setIdvuelovuelta(session.get("vueloVuelta").toString());
		if(!getIdvuelovuelta().equals("")){
			reserva=new ReservaDTO();
			reserva.setTotalPagar(session.get("precioTotalVuelta").toString());
			reserva.setFechaVuelo(session.get("fechaVuelta").toString());
			reserva.setIdUsuario(session.get("id").toString());
			reserva.setVuelo(session.get("vueloVuelta").toString());
			reservaService.registroReserva(reserva);
		}
		
		setPersonas((List<PersonaDTO>)session.get("personas"));
		
		for(int i=0; i<getPersonas().size() ;i++){
			usuarioService.registrarPersona(personas.get(i));
			int ultimareserva= Integer.parseInt(reservaService.obtenerUltimaReserva());
			if(!getIdvuelovuelta().equals("")){
				reservaService.registroPersonaxReserva((ultimareserva-1)+"" ,reservaService.obtenerUltimaPersona(), "	Máximo 2 piezas, Total 23 Kgs");
				reservaService.registroPersonaxReserva(ultimareserva+"" ,reservaService.obtenerUltimaPersona(), "	Máximo 2 piezas, Total 23 Kgs");
			}else{
				reservaService.registroPersonaxReserva(ultimareserva+"" ,reservaService.obtenerUltimaPersona(), "	Máximo 2 piezas, Total 23 Kgs");
			}
		
		}
		mensaje="Registro de Reserva realizado con éxito";
		tipoRpta="success";
		Logger.info(new LogDTO(session.get("id").toString(),"Registró Reserva"));

		return "registro";
	}
	
	public String buscarxcodigo(){
		String idreserva = getReserva().getIdReserva();
		reserva = null;
		reserva = reservaService.buscarReserva(idreserva);
		if(reserva==null){
			setMensaje("El código de reserva ingresado no se encontró");
    		return "fallobuscarreserva";   		
		}else{
			Map<String, Object> session = ActionContext.getContext().getSession();
			
			VueloDTO vuelo=new VueloDTO();
			vuelo=reservaService.obtenerVuelo(Integer.parseInt(idreserva));
			
			List<PersonaDTO> personas = new ArrayList<PersonaDTO>();
			personas = reservaService.obtenerPersonas(Integer.parseInt(idreserva));
			
				session.put("idReserva", getReserva().getIdReserva());
				session.put("vuelo", vuelo);
				session.put("listaPasajerosReserva", personas);


		}	
		
		return "buscarxcodigo";
	}
	
	public String buscarPagadas(){
	
		Map<String, Object> session = ActionContext.getContext().getSession();
		String email=session.get("email").toString();
		reservas = reservaService.buscarReservaPagadaPorUsuario(email);
		
		//Liberando memoria.....si es que existen en sesion
		
		session.remove("listaAsientosOcupados");
		session.remove("capacidadAvion");
		session.remove("asiento");
		session.remove("idReserva");
		session.remove("listaPasajerosReserva");
		session.remove("listaPasajerosSeleccionados");
		session.remove("vuelo");
		
		return "buscarPagadas";
	}
	

}
