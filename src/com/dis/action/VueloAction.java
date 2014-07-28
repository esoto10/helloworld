package com.dis.action;

import java.util.List;
import java.util.Map;

import com.dis.bean.VueloDTO;
import com.dis.service.PaqueteBusinessDelegate;
import com.dis.service.VueloService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class VueloAction extends ActionSupport{
	
	private String tipoBusqueda;// 0== ida y vuelta   1== solo ida
	private String fechaRegreso;
	private VueloDTO vuelo; 
	private List<VueloDTO> vuelosIda;
	private List<VueloDTO> vuelosVuelta;
	private String idVueloIda="";
	private String idVueloVuelta="";
	private String fechaIda="";
	private String fechaVuelta="";
    
	VueloService vueloService=PaqueteBusinessDelegate.getVueloService();

	
	public String getIdVueloIda() {
		return idVueloIda;
	}

	public void setIdVueloIda(String idVueloIda) {
		this.idVueloIda = idVueloIda;
	}

	public String getIdVueloVuelta() {
		return idVueloVuelta;
	}

	public void setIdVueloVuelta(String idVueloVuelta) {
		this.idVueloVuelta = idVueloVuelta;
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


	public String getFechaRegreso() {
		return fechaRegreso;
	}

	public void setFechaRegreso(String fechaRegreso) {
		this.fechaRegreso = fechaRegreso;
	}

	public VueloDTO getVuelo() {
		return vuelo;
	}

	public void setVuelo(VueloDTO vuelo) {
		this.vuelo = vuelo;
	}

	public String getTipoBusqueda() {
		return tipoBusqueda;
	}

	public void setTipoBusqueda(String tipoBusqueda) {
		this.tipoBusqueda = tipoBusqueda;
	}

	public List<VueloDTO> getVuelosIda() {
		return vuelosIda;
	}

	public void setVuelosIda(List<VueloDTO> vuelosIda) {
		this.vuelosIda = vuelosIda;
	}

	public List<VueloDTO> getVuelosVuelta() {
		return vuelosVuelta;
	}

	public void setVuelosVuelta(List<VueloDTO> vuelosVuelta) {
		this.vuelosVuelta = vuelosVuelta;
	}

	//-----------------------Soy una barra separadora-----------------------//
	
	public String buscar(){	
		vuelosIda=vueloService.buscarVuelos(getVuelo().getOrigen(), getVuelo().getDestino(), getVuelo().getFechaSalida().toString());
		if(getTipoBusqueda().equals("0"))
			vuelosVuelta=vueloService.buscarVuelos(getVuelo().getDestino(), getVuelo().getOrigen(), getFechaRegreso());
		return "buscar";
	}
	
	public String confirmarBuscar(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("vueloIda", idVueloIda);
		session.put("vueloVuelta", idVueloVuelta);
		session.put("fechaIda", fechaIda);
		session.put("fechaVuelta", fechaVuelta);
		return "confirmarBuscar";
	}

	
}
