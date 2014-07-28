package com.dis.action;


import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.dis.bean.CheckinDTO;
import com.dis.bean.EmbarqueDTO;
import com.dis.bean.NacionalidadDTO;
import com.dis.bean.PersonaDTO;
import com.dis.bean.VueloDTO;
import com.dis.service.CheckinService;
import com.dis.service.PaqueteBusinessDelegate;
import com.dis.util.DateHandler;
import com.dis.util.Encriptacion;
import com.dis.util.GenerarTarjetaEmbarque;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class CheckinAction extends ActionSupport {

	private CheckinDTO checkin;
	private PersonaDTO persona;
	private String mensaje;
	private List<PersonaDTO> listaPasajerosReserva;
	private List<PersonaDTO> listaPasajerosSeleccionados;
	private int idReserva;
	private VueloDTO vuelo;
	private int capacidadAvion;
	private String[] pasajerosSeleccionados;
	private List<NacionalidadDTO> listaNacionalidades;
	private String idPersona[];
	private String nombre[];
	private String apellidoPaterno[];
	private String apellidoMaterno[];
	private String tipoDocumento[];
	private String numDocumento[];
	private String fechaNacimiento[];
	private String tipoPersona[];
	private String genero[];
	private String nacionalidad[];
	private String asiento[];
	private List<String> listaAsientosOcupados;
	private String asientoElegido;
	private List<CheckinDTO> listaCheckin;
	private String tipoCarga;
	private InputStream inputStream;
	//private ContentDisposition contentDisposition;
	
	CheckinService checkinService = PaqueteBusinessDelegate.getCheckinService();
	/*
	public ContentDisposition getContentDisposition() {
		return contentDisposition;
	}
	public void setContentDisposition(ContentDisposition contentDisposition) {
		this.contentDisposition = contentDisposition;
	}
	*/
	public InputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	public List<PersonaDTO> getListaPasajerosReserva() {
		return listaPasajerosReserva;
	}
	public void setListaPasajerosReserva(List<PersonaDTO> listaPasajerosReserva) {
		this.listaPasajerosReserva = listaPasajerosReserva;
	}
	public String getTipoCarga() {
		return tipoCarga;
	}
	public void setTipoCarga(String tipoCarga) {
		this.tipoCarga = tipoCarga;
	}
	public List<CheckinDTO> getListaCheckin() {
		return listaCheckin;
	}
	public void setListaCheckin(List<CheckinDTO> listaCheckin) {
		this.listaCheckin = listaCheckin;
	}
	public String[] getAsiento() {
		return asiento;
	}
	public void setAsiento(String[] asiento) {
		this.asiento = asiento;
	}
	public String getAsientoElegido() {
		return asientoElegido;
	}
	public void setAsientoElegido(String asientoElegido) {
		this.asientoElegido = asientoElegido;
	}
	public int getCapacidadAvion() {
		return capacidadAvion;
	}
	public void setCapacidadAvion(int capacidadAvion) {
		this.capacidadAvion = capacidadAvion;
	}
	public VueloDTO getVuelo() {
		return vuelo;
	}
	public void setVuelo(VueloDTO vuelo) {
		this.vuelo = vuelo;
	}
	public List<String> getListaAsientosOcupados() {
		return listaAsientosOcupados;
	}
	public void setListaAsientosOcupados(List<String> listaAsientosOcupados) {
		this.listaAsientosOcupados = listaAsientosOcupados;
	}
	public String[] getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(String[] idPersona) {
		this.idPersona = idPersona;
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
	public String[] getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(String[] fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
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
	public CheckinDTO getCheckin() {
		return checkin;
	}
	public void setCheckin(CheckinDTO checkin) {
		this.checkin = checkin;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}		
	public PersonaDTO getPersona() {
		return persona;
	}
	public void setPersona(PersonaDTO persona) {
		this.persona = persona;
	}
	public List<PersonaDTO> getListaPasajerosSeleccionados() {
		return listaPasajerosSeleccionados;
	}
	public void setListaPasajerosSeleccionados(
			List<PersonaDTO> listaPasajerosSeleccionados) {
		this.listaPasajerosSeleccionados = listaPasajerosSeleccionados;
	}
	public int getIdReserva() {
		return idReserva;
	}
	public void setIdReserva(int idReserva) {
		this.idReserva = idReserva;
	}
	public String[] getPasajerosSeleccionados() {
		return pasajerosSeleccionados;
	}
	public void setPasajerosSeleccionados(String[] pasajerosSeleccionados) {
		this.pasajerosSeleccionados = pasajerosSeleccionados;
	}

	public List<NacionalidadDTO> getListaNacionalidades() {
		return listaNacionalidades;
	}
	public void setListaNacionalidades(List<NacionalidadDTO> listaNacionalidades) {
		this.listaNacionalidades = listaNacionalidades;
	}
	//-----------------------Soy una barra separadora-----------------------//
	

	

	public String cargarInfoStp2(){

		Map<String, Object> session = ActionContext.getContext().getSession();
		
		setListaPasajerosReserva((List<PersonaDTO>)session.get("listaPasajerosReserva"));
		setVuelo((VueloDTO)session.get("vuelo"));
		
		//verificando la capacidad y la disponibilidad de asientos en el vuelo
		
		try {
			
			if(session.get("listaAsientosOcupados")==null ||session.get("capacidadAvion")==null){
				
				listaAsientosOcupados = checkinService.buscarAsientosOcupados(getVuelo().getIdVuelo(),getVuelo().getFechaSalida());				
				
				/*if(listaAsientosOcupados.size()==0){
					listaAsientosOcupados=null;
				}*/
				
				capacidadAvion = checkinService.recuperarCapacidadAvion(getVuelo().getIdVuelo());
				
				session.put("listaAsientosOcupados", listaAsientosOcupados);
				session.put("capacidadAvion", capacidadAvion);
			}else{
				
				listaAsientosOcupados = (List<String>)session.get("listaAsientosOcupados");
				capacidadAvion = Integer.parseInt(session.get("capacidadAvion").toString());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		

		
		return "cargarInfoStp2";
	}
	
	public String seleccionarPasajeros(){
		
		//separando string de pasajeros
		pasajerosSeleccionados=pasajerosSeleccionados[0].split(",");
		
		Map<String, Object> session = ActionContext.getContext().getSession();
		
		listaAsientosOcupados = (List<String>)session.get("listaAsientosOcupados");
		capacidadAvion = Integer.parseInt(session.get("capacidadAvion").toString());
		
		int cantAsientosDisp = capacidadAvion-listaAsientosOcupados.size();
		//verificando cuantos desponibilidad de asientos
		if(pasajerosSeleccionados.length>cantAsientosDisp){
			
			mensaje = "El vuelo tiene solo "+cantAsientosDisp+" asiento(s) disponible(s)";
			
			return "falloSeleccion";
		}else{
			
			try {
				listaPasajerosSeleccionados = checkinService.buscarPasajeros(pasajerosSeleccionados);
			} catch (Exception e) {
				e.printStackTrace();
			}
					
			session.put("listaPasajerosSeleccionados", listaPasajerosSeleccionados);
			return "seleccionarPasajeros";
		}
		
	}

	public String cargarInfoPasajerosStp3(){
		
		try {
			setListaNacionalidades(checkinService.buscarNacionalidades());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Map<String, Object> session = ActionContext.getContext().getSession();
		setListaPasajerosSeleccionados((List<PersonaDTO>)session.get("listaPasajerosSeleccionados"));
		return "cargarInfoPasajerosStp3";
	}

	public String actualizarInfoPasajeros(){
		

		try {
			
			if(checkinService.acualizarInfoPasajero(getPersona())){
				System.out.println("Pasajero actualizado con exito");
				
				Map<String, Object> session = ActionContext.getContext().getSession();				
				setListaPasajerosSeleccionados((List<PersonaDTO>)session.get("listaPasajerosSeleccionados"));
				
				pasajerosSeleccionados = new String[listaPasajerosSeleccionados.size()];
				
				for(int i=0;i<listaPasajerosSeleccionados.size();i++){
					pasajerosSeleccionados[i] = String.valueOf(listaPasajerosSeleccionados.get(i).getIdPersona());
				}
				
				listaPasajerosSeleccionados = checkinService.buscarPasajeros(pasajerosSeleccionados);
				
				session.put("listaPasajerosSeleccionados", listaPasajerosSeleccionados);

			}else{
				mensaje = "Número de documento ya existe, porfavor ingrese otro número de documento";
				System.out.println("Error al actualizar pasajero, Numero de doc ya existe");

			}
		} catch (Exception e) {
			mensaje = "Error al actualizar pasajero, porfavor reintente";
			System.out.println("Error al actualizar pasajero");
			e.printStackTrace();

		}
		
		return "actualizarInfoPasajeros";
	}
	
	public String verificarInfoPasajeros(){
		
	
		return "verificarInfoPasajeros";
	}
	public String cargarInfoPasajerosStp4(){
	
		Map<String, Object> session = ActionContext.getContext().getSession();
		setListaPasajerosSeleccionados((List<PersonaDTO>)session.get("listaPasajerosSeleccionados"));
		return "cargarInfoPasajerosStp4";
	}
	
	public String buscarAsientosOcupados(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		setVuelo((VueloDTO)session.get("vuelo"));
		
		try {
			
			if(session.get("listaAsientosOcupados")==null ||session.get("capacidadAvion")==null){
				
				listaAsientosOcupados =checkinService.buscarAsientosOcupados(getVuelo().getIdVuelo(),getVuelo().getFechaSalida());
				
				/*if(listaAsientosOcupados.size()==0){
					listaAsientosOcupados=null;
				}*/
				
				capacidadAvion = checkinService.recuperarCapacidadAvion(getVuelo().getIdVuelo());
				
				session.put("listaAsientosOcupados", listaAsientosOcupados);
				session.put("capacidadAvion", capacidadAvion);
				
			}else{
				
				if(tipoCarga.equals("0")){//primera vez que elige asiento
					
					listaAsientosOcupados = (List<String>)session.get("listaAsientosOcupados");
					capacidadAvion = Integer.parseInt(session.get("capacidadAvion").toString());
				
				}else if(tipoCarga.equals("1")){//cambiar asiento
					
					listaAsientosOcupados = (List<String>)session.get("listaAsientosOcupados");
					
					for(int i=0;i<listaAsientosOcupados.size();i++){

						if(listaAsientosOcupados.get(i).toString().equals(getAsientoElegido())){
							
							listaAsientosOcupados.remove(i);
							break;
						}
						
					}
					session.put("listaAsientosOcupados", listaAsientosOcupados);
					capacidadAvion = Integer.parseInt(session.get("capacidadAvion").toString());
				}
				
			}	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "buscarAsientosOcupados";
	}
	
	public String seleccionarAsiento(){
		
		Map<String, Object> session = ActionContext.getContext().getSession();
		
		List<CheckinDTO> listaCheckin = new Vector<CheckinDTO>();
		
		CheckinDTO checkinDTO = new CheckinDTO();
		checkinDTO.setIdReserva(Integer.parseInt(session.get("idReserva").toString()));
		checkinDTO.setIdPersona(Integer.parseInt(idPersona[0]));
		checkinDTO.setAsiento(asientoElegido);
		
		//creaando el num de ticket aleatorio
		String numTicket="";
		try {

			do {
				int num1 = Encriptacion.randomNumbers(100, 999);
				int num2 = Encriptacion.randomNumbers(1000000, 9999999);
				numTicket = String.valueOf(num1)+"-"+String.valueOf(num2);

			} while (checkinService.verificarNumTicket(numTicket));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		checkinDTO.setNumTicket(numTicket);
		
		listaCheckin.add(checkinDTO);
		
		if(tipoCarga.equals("0")){//registra
			
			//registrando checkin
			try {
				
				if(checkinService.registrarCheckin(listaCheckin)){
					
					System.out.println("Check-in realizado con exito");
				}else{

					System.out.println("Error al registrar check-in");
		
				}
			} catch (Exception e) {
	
				System.out.println("Error al registrar check-in");
				e.printStackTrace();
				
			}
			
		}else if(tipoCarga.equals("1")){//update
		
			//update checkin
			try {
				
				if(checkinService.actualizarCheckin(listaCheckin)){
					System.out.println("Check-in actualizado con exito");
				}else{
					
					System.out.println("Error al actualizar check-in");
		
				}
			} catch (Exception e) {
				
				System.out.println("Error al actualizar check-in1");
				e.printStackTrace();
				
			}	
			
		}
		
		
		listaAsientosOcupados = (List<String>)session.get("listaAsientosOcupados");
		
		//agrega asiento a la lista de asientos ocupados
		listaAsientosOcupados.add(asientoElegido);
		
		session.put("listaAsientosOcupados", listaAsientosOcupados);
		
		return "seleccionarAsiento";
	}

	public String verificar(){
		
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("asiento", asiento);
		return "verificar";
	}
	public String cargarInfoPasajerosStp5(){
		
		Map<String, Object> session = ActionContext.getContext().getSession();
		setAsiento((String[])session.get("asiento"));
		setListaPasajerosSeleccionados((List<PersonaDTO>)session.get("listaPasajerosSeleccionados"));
		setVuelo((VueloDTO)session.get("vuelo"));

		return "cargarInfoPasajerosStp5";
	}
	
	
	public String cargarInfoTarjetaEmbarque(){
		
		Map<String, Object> session = ActionContext.getContext().getSession();
		
		setVuelo((VueloDTO)session.get("vuelo"));
		setListaPasajerosSeleccionados((List<PersonaDTO>)session.get("listaPasajerosSeleccionados"));
		persona = new PersonaDTO();
		for(int i=0;i<listaPasajerosSeleccionados.size();i++){
			if(listaPasajerosSeleccionados.get(i).getIdPersona().equals(idPersona[0])){
				
				persona = listaPasajerosSeleccionados.get(i);
				break;
			}
			
		}
		
		List<CheckinDTO> listaCheckin = new Vector<CheckinDTO>();
		
		CheckinDTO checkinDTO = new CheckinDTO();
		checkinDTO.setIdReserva(Integer.parseInt(session.get("idReserva").toString()));
		checkinDTO.setIdPersona(Integer.parseInt(persona.getIdPersona()));
		
		listaCheckin.add(checkinDTO);
		
		checkinDTO = null;
		//obteniendo idembarque
		try {
			
			checkinDTO = checkinService.obtenerEmbarque(listaCheckin);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		EmbarqueDTO embarqueDTO = new EmbarqueDTO();
		
		embarqueDTO.setNombre(persona.getNombre().toUpperCase());
		embarqueDTO.setApellidoPaterno(persona.getApellidoPaterno().toUpperCase());
		embarqueDTO.setApellidoMaterno(persona.getApellidoMaterno().toUpperCase());
		embarqueDTO.setOrigen(vuelo.getOrigen().toUpperCase());
		embarqueDTO.setOrigenAeropuerto(vuelo.getOrigenAeropuerto());
		embarqueDTO.setOrigenAeropuertoTerminal("ÚNICO");
		embarqueDTO.setDestino(vuelo.getDestino().toUpperCase());
		embarqueDTO.setDestinoAeropuerto(vuelo.getDestinoAeropuerto());
		embarqueDTO.setDestinoAeropuertoTerminal("ÚNICO");
		embarqueDTO.setVuelo(vuelo.getNumVuelo().toUpperCase());
		embarqueDTO.setHoraSalida(DateHandler.showtime(vuelo.getHoraSalida()));
		embarqueDTO.setHoraEmbarque(DateHandler.obtenerHoraEmbarque(vuelo.getFechaSalida(), vuelo.getHoraSalida()));
		embarqueDTO.setHoraAeropuerto(DateHandler.obtenerHoraAeropuerto(vuelo.getFechaSalida(), vuelo.getHoraSalida()));
		if(asientoElegido.length()==3){
			embarqueDTO.setAsiento(asientoElegido.substring(0,2)+"/"+asientoElegido.substring(2));
		}else{
			embarqueDTO.setAsiento(asientoElegido.substring(0,1)+"/"+asientoElegido.substring(1));
		}
		embarqueDTO.setFechaSalida("("+DateHandler.obtenerDia(DateHandler.getDate(vuelo.getFechaSalida()))+"/"+DateHandler.obtenerMes(DateHandler.getDate(vuelo.getFechaSalida()))+")");
		embarqueDTO.setNumTarjetaEmbarque(String.valueOf(checkinDTO.getIdEmbarque()));
		embarqueDTO.setClase("ECONOMY");
		embarqueDTO.setNumTicket(checkinDTO.getNumTicket());
		
		
		try {
			
			inputStream = GenerarTarjetaEmbarque.generarPdf(embarqueDTO);
			
			if(inputStream!=null){
				String fileName= "BPASS_"+persona.getApellidoPaterno()+"_"+persona.getNombre()+".pdf";
			//	contentDisposition =  new ContentDisposition("attachment;filename=\""+fileName+"\"");
				System.out.println("Boarding pass generado exitosamente");
			}
						
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		
		
		return "cargarInfoTarjetaEmbarque";
	}
	
	public String resetSession(){
		
		Map<String, Object> session = ActionContext.getContext().getSession();
		
		//Liberando memoria.....
		
		session.remove("listaAsientosOcupados");
		session.remove("capacidadAvion");
		session.remove("asiento");
		session.remove("idReserva");
		session.remove("listaPasajerosReserva");
		session.remove("listaPasajerosSeleccionados");
		session.remove("vuelo");
		
		return "resetSession";
	}
	
}
