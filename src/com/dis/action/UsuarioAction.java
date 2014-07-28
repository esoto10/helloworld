package com.dis.action;

import java.util.List;
import java.util.Map;

import com.dis.bean.LogDTO;
import com.dis.bean.UsuarioDTO;
import com.dis.service.PaqueteBusinessDelegate;
import com.dis.service.UsuarioService;
import com.dis.util.Encriptacion;
import com.dis.util.Logger;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class UsuarioAction extends ActionSupport{
	
	private String mensaje;	
	private UsuarioDTO usuario;
    UsuarioService usuarioService=PaqueteBusinessDelegate.getUsuarioService();
    private String urlContext;
    private String tkn;
    private String tipoRpta; 
    private List<UsuarioDTO> usuarios;
    
	public List<UsuarioDTO> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<UsuarioDTO> usuarios) {
		this.usuarios = usuarios;
	}

	public String getTipoRpta() {
		return tipoRpta;
	}

	public void setTipoRpta(String tipoRpta) {
		this.tipoRpta = tipoRpta;
	}

	public String getTkn() {
		return tkn;
	}

	public void setTkn(String tkn) {
		this.tkn = tkn;
	}
    
    public String getUrlContext() {
		return urlContext;
	}

	public void setUrlContext(String urlContext) {
		this.urlContext = urlContext;
	}

	public UsuarioDTO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDTO usuario) {
		this.usuario = usuario;
	}
	
	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	//-----------------------Soy una barra separadora-----------------------//


	public String login(){

    	UsuarioDTO usuario=null;
    	usuario=usuarioService.verificarUsuario(getUsuario().getEmail(), getUsuario().getPassword(),true);
    	   	
    	if(usuario==null){
    		setMensaje("El usuario y/o password ingresado son incorrectos");
    		setTipoRpta("fail");
    		return "fallologin";   		
    	}else{
    		if(usuario.getEstado().equals("0")){
    			setMensaje("El usuario ingresado está deshabilitado");
        		setTipoRpta("fail");
        		return "fallologin";
    		}
    		Map<String, Object> session = ActionContext.getContext().getSession();
    		session.put("nombre",usuario.getNombre()+" "+usuario.getApellidoPaterno()+" "+usuario.getApellidoMaterno());
    		session.put("email", usuario.getEmail());
    		session.put("permiso",usuario.getTipoUsuario());
    		session.put("millas", usuario.getMillas());
    		session.put("id", usuario.getIdUsuario());
    		System.out.println(usuario.getEmail()+" ha iniciado sesión en el sistema.");
    		Logger.info(new LogDTO(session.get("id").toString(),"Inicio de Sesión"));
    		return "success";
    	}
		
		
	}
	
	public String cerrarSesion(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		if(session!=null){
			Logger.info(new LogDTO(session.get("id").toString(),"Cerró Sesión"));
			String usuario = session.get("email").toString();
			ActionContext.getContext().getSession().clear();
			ActionContext.getContext().setSession(null);
			System.out.println(usuario+" ha cerrado sesión del sistema.");
			
		}
		return "cerrarSesion";
	}
	
	public void actualizarMillasSesion(int millas){
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("millas", millas);
	}
	
	
	public String registrar() {
		UsuarioDTO usuarioDTO=getUsuario();
		try{
			if (usuarioService.registrar(usuarioDTO)){
				mensaje= "El registro del usuario fue satisfactorio";
				Logger.info(new LogDTO("null","Registro de Nuevo Usuario"));
				System.out.println("Registro de nuevo usuario.");
				return "registrar";
			}else{
				mensaje="El DNI ingresado ya existe, por favor verifique sus datos.";
				System.out.println("Error de registro de Usuario");
				return "falloregistrar";
			}
			
		}catch (Exception e) {
			mensaje= "Hubo un error registrando sus datos, por favor reintente.";
			System.out.println("Error de registro de Usuario");
			e.printStackTrace();
			return "falloregistrar";
		}	
			
		
	}

	public String comfirmarPass(){
		//El usuario ingresa su nuevo password
		boolean estado= usuarioService.cambiarPassword(getTkn(),getUsuario().getEmail(),Encriptacion.getStringMessageDigest(getUsuario().getPassword(), "MD5"));
		if(estado){
			setMensaje("Su Contraseña ha sido cambiada con éxito");
			setTipoRpta("success");
			Map<String, Object> session = ActionContext.getContext().getSession();
			Logger.info(new LogDTO(session.get("id").toString(),"Cambio de Password"));
		}else{
			setMensaje("Hubo un error al procesar los datos, intente nuevamente");
			setTipoRpta("fail");
		}	
		return "updPassword";
	}
	
	public String solicitarCambioPassword(){
		//El usuario ingresa su mail para que se le envï¿½e un correo con un enlace para cambiar su password
		System.out.println("El usuario "+getUsuario().getEmail()+" ha solicitado cambio de Password");
		String url = getUrlContext();
		boolean estado = usuarioService.solicitarCambioPassword(getUsuario().getEmail(),url);
		if(estado){
			Map<String, Object> session = ActionContext.getContext().getSession();
			setMensaje("Un mail ha sido enviado a su dirección de correo para cambiar su password");
			setTipoRpta("success");
			Logger.info(new LogDTO(session.get("id").toString(),"Solicitó Cambio de Password"));
		}else{
			setMensaje("No se encontró el mail ingresado. Por favor, reintente!");
			setTipoRpta("fail"); 
		}
		return "cambioPassword";
	}
	
	public String cambiarPass(){
		//El usuario accede a la aplicación para cambiar su usuario y contraseña
    	setUsuario(usuarioService.verificarUsuario(getUsuario().getEmail(),getTkn(),false));	
    	if(getUsuario()!=null){
    		return "mostrarCambiarPass";
    	}else{
    		return "falloMostrarCambiarPass";
    	}
	}
	
	public String obtener(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		usuario = new UsuarioDTO();
		usuario.setNombre(session.get("nombre")+"");
		usuario.setMillas(Integer.parseInt(session.get("millas")+""));
		usuario.setEmail(session.get("email")+"");
		return "obtener";
	}
	
	public String listar(){
		setUsuarios(usuarioService.listarUsuarios());
		return "listar";
	}
	
	public String get(){
		setUsuario(usuarioService.getUsuario(usuario.getIdUsuario()+""));
		return "get";
	}
	
	public String habilitar(){
		if(usuarioService.deshabilitarUsuario(usuario.getIdUsuario()+"",usuario.getEstado())){
			String accion="";
			if(usuario.getEstado().equals("0"))
				accion="deshabilitado";
			else
				accion="habilitado";
			setMensaje("Usuario "+ accion + " correctamente");
			Map<String, Object> session = ActionContext.getContext().getSession();
			Logger.info(new LogDTO(session.get("id").toString(),"Usuario "+usuario.getIdUsuario()+" "+accion));
			setTipoRpta("success");
		}else{
			setMensaje("Error al deshabilitar usuario");
			setTipoRpta("fail");
		}
			
		return "habilitar";
	}
	
	public String actualizar(){
		if(usuarioService.actualizarUsuario(usuario)){
			setMensaje("Usuario actualizado correctamente");
			setTipoRpta("success");
			Map<String, Object> session = ActionContext.getContext().getSession();
			Logger.info(new LogDTO(session.get("id").toString(),"Usuario "+usuario.getEmail()+" modificado"));
		}else{
			setMensaje("Error al actualizar usuario");
			setTipoRpta("fail");
		}
		return "actualizar";
	}

	
}
