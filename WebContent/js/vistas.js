var HOME = 'inicio';
var REPORTES = 'reportes';
var CHECKIN = 'checkin';
var RESERVAS = 'reservas';
var AJUSTES = 'ajustes';
var PERFIL = 'perfil';
var RESERVAR_VUELO = 'reservarVuelo';
var CANJEAR_MILLAS = 'canjearMillas';
var COMPRAR_RESERVA = 'comprarBoleto';
var ADMINISTRAR = 'administrarMenu';
var LIST_USUARIOS = 'listarUsuarios';
var USUARIO = "usuario";
var CHECKIN = 'checkin';
var CHECKIN2 = 'checkin2';


var init = function(){
	loadSettings();
	mostrar(HOME);
}

var loading = function(value){
	if(value){
		$('#content').html('<div align="center"><p><img src="images/ajax-loader.gif" width="220" height="19" /></p></div>');
	}else{
		$('#content').html('');
	}
}

var verificarUsuarioLoggeado = function(){
	if($('#idUsuarioSesion').val()==""){
		alert("Por favor, inicie sesion para acceder a esta opcion");
		$('#menu a').attr('class','main');
		$('#inicio').attr('class','main current');
		return false;
	}
	return true;
		
}

var mostrar = function(pagina){
	switch (pagina){
		case HOME:
	 		loading(true);
	 		getData(pagina+'.html',null,function(resp){
				if(resp!=undefined) setContenido(resp);
				else				setError();
				setSubMenu('');
				$('#vuelos-ida').hide();
				$('#vuelos-vuelta').hide();
				$('#opciones').hide();
				initDateTimePicker("#fechaSalida");
				initDateTimePicker("#fechaRegreso");		
			},null);
			break;
		case RESERVAS:
			if(!verificarUsuarioLoggeado()) return;
			var opciones='<ul><li><a href="#reservarVuelo" id="reservarVuelo" class="current"><span>Reservar Vuelo</span></a></li><li><a href="#comprarBoleto" id="comprarBoleto"><span>Comprar Boleto</span></a></li><li><a href="#canjearMillas" id="canjearMillas"><span>Canjear Millas</span></a></li></ul>';
			setSubMenu(opciones);
			addOnClick();
			mostrar(RESERVAR_VUELO);
			break;
		case RESERVAR_VUELO:
			loading(true);
	 		getData(pagina+'.html',null,function(resp){
				if(resp!=undefined) setContenido(resp);
				else				setError();
				$('#vuelos-ida').hide();
				$('#vuelos-vuelta').hide();
				$('#opciones').hide();
				initDateTimePicker("#fechaSalida");
				initDateTimePicker("#fechaRegreso");
			},null);
			break;
		case CANJEAR_MILLAS:
			loading(true);
	 		getData(pagina+'.html',null,function(resp){
				if(resp!=undefined) setContenido(resp);
				else				setError();
				var nombre = $('#usuario').html();
				var millas = $('#millasAcumuladas').val();
				$('#nombre').val(nombre);
				$('#millas').val(millas);
				listarReservaPorUsuario('canjear-reserva');		
			},null);
			
			break;
		case COMPRAR_RESERVA:
			loading(true);
			getData(pagina+'.html',null,function(resp){
				if(resp!=undefined) setContenido(resp);
				else				setError();
				listarReservaPorUsuario('reserva');		
			},null);
			break;
		case ADMINISTRAR:
			var opciones='<ul><li><a href="#mantenerUsuarios" id="mantenerUsuarios" class="current"><span>Mantener Usuarios</span></a></li>';
			setSubMenu(opciones);
			addOnClick();
			mostrar(LIST_USUARIOS);
			break;
		case LIST_USUARIOS:
			loading(true);
	 		getData(pagina+'.html',null,function(resp){
				if(resp!=undefined) setContenido(resp);
				else				setError();
				listarUsuarios();	
				initDateTimePicker('#fechaNacimiento');
				$( "#nuevo-usuario-form" ).dialog({
					autoOpen: false,
					height: 600,
					width: 300,
					modal: true,
					buttons: {
						Registrar: function() {
							validarForm('usuario');
						},
						Cancelar: function() {
							$( this ).dialog( "close" );
						}
					},
					close: function() {
						$("#usuario-form").reset();

					}
				});
			},null);
			
			break;
		case PERFIL:
			if(!verificarUsuarioLoggeado()) return;
			$('#menu a').attr('class','main');
			$('#perfil').attr('class','main current');
			loading(true);
			setSubMenu(" ");
			mostrar(LIST_USUARIOS);
			var idUser=$('#idUsuarioSesion').val();
	 		editarUsuario(idUser);

			break;
		case USUARIO:
			loading(true);
	 		getData(pagina+'.html',null,function(resp){
				if(resp!=undefined) setContenido(resp);
				else				setError();
			},null);
			break;
		case CHECKIN:
			if(!verificarUsuarioLoggeado()) return;
			loading(true);
			getData('checkIn.html',null,function(resp){
				if(resp!=undefined){ 
					setContenido(resp);
					setSubMenu('');
				}else{				
					setError();
				};
			},null);
			listarReservaPagadaPorUsuario('reserva');
			break;
		case CHECKIN2:
			if(!verificarUsuarioLoggeado()) return;
			loading(true);
			getData('checkInStp2.html',null,function(resp){
				if(resp!=undefined) setContenido(resp);
				else				setError();
			},null);
			cargarInfoCheckInStp2();
			break;
		default:
			break;
	}
}

var checkFechaRegreso = function(){
	var tipoBusqueda = $('input[name=tipoBusqueda]:checked').val();
	if(tipoBusqueda==0){
		$('#fechaRegreso').show();
		$('#labelFechaRegreso').show();
	}
	if(tipoBusqueda==1){
		$('#fechaRegreso').hide();
		$('#labelFechaRegreso').hide();
	}
}

var loadSettings = function(){
	var usuario = $('#usuario').html();
	var permiso = $('#permiso').val();
	if (usuario!=''){
		$('#login').html('Cerrar Sesion');
		$('#login').attr('href','cerrarSesionUsuario');
	}
	if(permiso=='ADMIN'){
		var adminMenu="'administrarMenu'";
		var html = '<li class="item middle" id="reportesMenu"><a href="#"><span class="outer"><span class="inner reports png">Reportes</span></span></a></li><li class="item last" ><a href="#administrar" id="administrarMenu" onclick="mostrar('+adminMenu+')" class="main"><span class="outer"><span class="inner settings">Administrar</span></span></a></li>';
		$('#miCuentaMenu').attr('class','item middle');
		$('#menu').attr('style','');
		$('#menu_group_main').append(html);
	}
	addOnClickMain();
}

var addOnClickMain = function(){
	$('#menu a').click(function(event) {
		$('#menu a').attr('class','main');
		$('#'+event.currentTarget.id).attr('class','main current');
	});
}

var addOnClick = function(){
	$('#tabs a').click(function(event) {
		$('#tabs a').attr('class',' ');
		$('#'+event.currentTarget.id).attr('class','current');
		mostrar(event.currentTarget.id);
	});
}

var setContenido = function(contenido){
	$('#content').html(contenido);
}

var setSubMenu = function(opciones){
	$('.container').html(opciones);
}

var setError = function(){
	var contenido = '<div align="center" id="fail">No se pudo cargar la página, por favor reintente.</div>';
	setContenido(contenido);
}


function nuevoUsuario(){
	$(".nuevo-usuario").attr('id','show-message');
	$(".login").attr('id','none');
	$(".login").html('');
	$("#login-form").reset();
	$("#email").attr('id','noneEmail');
	$("#emailNuevoUsuario").attr('id','email');
	$("#password").attr('id','nonePassword');
	$("#passwordNuevoUsuario").attr('id','password');
	$( "#nuevo-usuario-form" ).dialog( "open" );
	
}

function nuevoUsuarioSimple(){
	$( "#nuevo-usuario-form" ).dialog( "open" );
}

function recuperarPassword(){
	$(".recuperar-password").attr('id','show-message');
	$(".login").attr('id','none');
	$(".login").html('');
	$("#login-form").reset();
	$("#email").attr('id','noneEmail');
	$("#emailRecuperarPassword").attr('id','email');
	$("#recuperar-password-form").dialog("open");
}

function initLogin(){
	$("#recuperar-password-form").dialog({
		autoOpen: false,
		height:200,
		width:300,
		modal:true,
		buttons:{
			Solicitar: function(){
				validarForm('request-password');
			},
			Salir: function(){
				$( this ).dialog( "close" );
			}
		},
		close: function(){
			$(".recuperar-password").attr('id','none');
			$(".login").attr('id','show-message');
			$(".login").show();
			$(".recuperar-password").html('');
			$("#recuperar-password").reset();
			$("#email").attr('id','emailRecuperarPassword');
			$("#noneEmail").attr('id','email');
		}
	});
	
	
	$( "#nuevo-usuario-form" ).dialog({
		autoOpen: false,
		height: 600,
		width: 300,
		modal: true,
		buttons: {
			Registrar: function() {
				validarForm('usuario');
			},
			Cancelar: function() {
				$( this ).dialog( "close" );
			}
		},
		close: function() {
			$(".nuevo-usuario").attr('id','none');
			$(".login").attr('id','show-message');
			$(".nuevo-usuario").html('');
			$("#nuevo-usuario").reset();
			$("#password").attr('id','passwordNuevoUsuario');
			$("#nonePassword").attr('id','password');
			$("#email").attr('id','emailNuevoUsuario');
			$("#noneEmail").attr('id','email');
		}
	});
}

var initDateTimePicker=function(idDateTimePicker){
	$(idDateTimePicker).datepicker({
		changeMonth: true,
		changeYear: true
	});
	$(idDateTimePicker).datepicker( "option", "dayNamesMin", ['Do', 'Lu', 'Ma', 'Mi', 'Ju', 'Vi', 'Sa'] );
	$(idDateTimePicker).datepicker( "option", "dateFormat", 'yy-mm-dd' );
	$(idDateTimePicker).datepicker( "option", "monthNames", ['Enero','Febrero','Marzo','Abril','Mayo','Junio','Julio','Agosto','Septiembre','Octubre','Noviembre','Diciembres'] );
}

var initDateTimePickerObj=function(idDateTimePicker){
	$(idDateTimePicker).datepicker({
		create: function(event,ui){},
		changeMonth: true,
		changeYear: true
	});
	$(idDateTimePicker).datepicker( "option", "dayNamesMin", ['Do', 'Lu', 'Ma', 'Mi', 'Ju', 'Vi', 'Sa'] );
	$(idDateTimePicker).datepicker( "option", "dateFormat", 'yy-mm-dd' );
	$(idDateTimePicker).datepicker( "option", "monthNames", ['Enero','Febrero','Marzo','Abril','Mayo','Junio','Julio','Agosto','Septiembre','Octubre','Noviembre','Diciembres'] );
	$(idDateTimePicker).datepicker('show');
	$(idDateTimePicker).attr('class','smallInput');
}

var agregarFila = function() {
	
	if(validarPersonasReserva()){
		showMessage(' ', " ");
		var contador = $('#pasajero-reserva-datos tr:last').attr('id');
		var tds = $("#pasajero-reserva-datos tr:last").clone();
		$("#pasajero-reserva-datos").append(tds);
		contador = parseInt(contador) + 1;
		$('#pasajero-reserva-datos tr:last').attr('id', contador);
		$('#pasajero-reserva-datos tr:last').find('#nombre').val("");
		$('#pasajero-reserva-datos tr:last').find('#primerapellido').val("");
		$('#pasajero-reserva-datos tr:last').find('#segundoapellido').val("");
		$('#pasajero-reserva-datos tr:last').find('#numdocumento').val("");
		$('#pasajero-reserva-datos tr:last').find('#fechanacimiento').val("");
		$('#pasajero-reserva-datos tr:last').find('#fechanacimiento').attr('id','fechanacimiento'+contador);
		initDateTimePickerObj("#fechanacimiento"+contador);
	}
}

