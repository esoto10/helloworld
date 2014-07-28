jQuery.fn.reset = function () {
	  $(this).each (function() { this.reset(); });
}

var populateSelectBox = function(id, url) {
	//specifying the success function. When the ajax response is successful then the following function will be called
	var successFn=function(resp){
		//getting the select box element
		var selectBox=$('#'+id);
		//setting the content inside as empty
		selectBox.innerHTML = '';
		//getting the data from the response object
		var data=resp.data; 
		//appending the first option as select to the select box
		selectBox.append('<option value="">Seleccionar</option>');
		//adding all other values
		for (var i=0;i<data.length;i++) {
			selectBox.append('<option value="'+data[i].name+'">'+data[i].name+'</option>');
		}
	}
	//calling the getData function with the success function
	getData(url,null,successFn,null);
}

var cerrarVentana=function (ventana) {
	if(confirm('Esta seguro de que desea Salir?')) 
	ventana.close();
}

function getURLParameter( name )
{
  name = name.replace(/[\[]/,"\\\[").replace(/[\]]/,"\\\]");
  var regexS = "[\\?&]"+name+"=([^&#]*)";
  var regex = new RegExp( regexS );
  var results = regex.exec( window.location.href );
  if( results == null )
    return "";
  else
    return results[1];
}

var verificarCampoPersonas=function(numeroPersonas, nombreCampo, mensaje){
	var value = $(numeroPersonas).find(nombreCampo).val();
	if(value == ""){
		showMessage(mensaje, 'fail');
		return false;		
	}
	return true;
}


var verificarCampo = function(nombreCampo,mensaje){
	var value = $(nombreCampo).val();
	if(value == ""){
		showMessage(mensaje, 'fail');
		return false;		
	}
	return true;
}

var obtenerDatosDeForm = function(entity){
	var datos=new Array();
	 var formEleList = $('form#'+entity+'-form').serializeArray();
	 for(var i=0;i<formEleList.length;i++){
		 datos[datos.length]=new param(formEleList[i].name,formEleList[i].value);
	 }
	 return datos;
}


var param=function(name,value){
	this.name=name;
	this.value=value;
}

var showMessage = function(message, tipoMsg){
	$('#show-message').show().html('<p class="message" id="'+tipoMsg+'">'+message+'</p>');
}


var showHideCargando = function(estado){
	if(estado){
		$('#show-message').show().html('<p><img src="images/ajax-loader.gif" width="220" height="19" /></p>');
	}else{
		$('#show-message').show().html('');
		$('#show-message').hide();
	}
}

var validarPersonasReserva=function(){
	var nropersonas = $('#pasajero-reserva-datos tr:last').attr('id');

	var valuegenero = $('#' + nropersonas).find('#genero').val();
	if (valuegenero == "none") {
		showMessage('Por favor, seleccione el sexo del pasajero.', "fail");
		return false;
	}
	
	if(!verificarCampoPersonas("#"+nropersonas,"#nombre","Por favor, ingrese el nombre del pasajero.")) return false;
	if(!verificarCampoPersonas("#"+nropersonas,"#primerapellido","Por favor, ingrese el primer apellido del pasajero.")) return false;
	if(!verificarCampoPersonas("#"+nropersonas,"#segundoapellido","Por favor, ingrese el segundo apellido del pasajero.")) return false;
	if(!verificarCampoPersonas("#"+nropersonas,"#tipodocumento","Por favor, seleccione el tipo de documento del pasajero.")) return false;
	if(!verificarCampoPersonas("#"+nropersonas,"#numdocumento","Por favor, ingrese el n\u00FAmero de documento del pasajero.")) return false;
	if(!verificarCampoPersonas("#"+nropersonas,"#nacionalidad","Por favor, seleccione la nacionalidad del pasajero")) return false;
	if(!verificarCampoPersonas("#"+nropersonas,"#fechanacimiento","Por favor, ingrese la fecha de nacimiento del pasajero.")) return false;
	if(!verificarCampoPersonas("#"+nropersonas,"#tipopersona","Por favor, seleccione el tipo de pasajero")) return false;
	
	return true;
}

var validarFormUptChk = function(entidad){
	
	if(!verificarCampoPasajeros(entidad,'#nombre','Por favor,ingrese el nombre del pasajero')) return;
	if(!verificarCampoPasajeros(entidad,'#apellidoPaterno','Por favor, ingrese el apellido paterno del pasajero')) return;
	if(!verificarCampoPasajeros(entidad,'#apellidoMaterno','Por favor, ingrese el apellido materno del pasajero')) return;
	if(!verificarCampoPasajeros(entidad,'#tipoDocumento','Por favor, seleccione el tipo de documento del pasajero')) return;	
	if(!verificarCampoPasajeros(entidad,'#numDocumento','Por favor, ingrese el numero de documento del pasajero')) return;
	if($('#'+entidad).find('#tipoDocumento').val()==2 || $('#'+entidad).find('#tipoDocumento').val()==3){
		if(!verificarCampoPasajeros(entidad,'#nacionalidad','Por favor, selecione la nacionalidad del documento')) return;
	}
	if(!verificarCampoPasajeros(entidad,'#genero','Por favor,ingrese el genero del pasajero')) return;
	
	if($('#'+entidad).find('#tipoDocumento').val()==1){
		if(!verificarLongitudCampo(entidad,'#numDocumento','<b>Numero de Documento</b>',8,8))return;
	}
	if($('#'+entidad).find('#tipoDocumento').val()==2 || $('#'+entidad).find('#tipoDocumento').val()==3){
		if(!verificarLongitudCampo(entidad,'#numDocumento','<b>Numero de Documento</b>',11,11))return;
	}

	var datos=new Array();
	datos =  obtenerDatosDeForm(entidad);
	actualizarInfoPasajero(datos,entidad);
};

var verificarLongitudCampo = function(entidad,idCampo,nombreCampo,longMin,longMax){
	var value = $('#'+entidad).find(idCampo).val();
	var valueArray = value.split('');
	if(valueArray.length<longMin || valueArray.length>longMax){
		if(valueArray.length<longMin){
			showMessage('La longitud minima del campo '+nombreCampo+' es de '+longMin+ ' caracteres' , 'fail');
		}else if(valueArray.length>longMax){
			showMessage('La longitud maxima del campo '+nombreCampo+' es de '+longMax+ ' caracteres' , 'fail');
		}
		
		return false;		
	}
	return true;
};

var verificarCampoPasajeros = function(entidad,nombreCampo,mensaje){
	var value = $('#'+entidad).find(nombreCampo).val();
	if(value == ""){
		showMessage(mensaje, 'fail');
		return false;		
	}
	return true;
}

//muestra o esconde la nacionalidad dependiendo del tipo de doc
var showHideNacionalidad = function(){

	var tipoDoc1 = $('#pasajero-n1').find("select[id=tipoDocumento]");
	var nacionalidad1 = $('#pasajero-n1').find("select[id=nacionalidad]");
	var lblNacionalidad1 =$('#pasajero-n1').find("label[id=lblNacionalidad]");
		
	tipoDoc1.change(function(){
		
		if(tipoDoc1.val()=="1"){
			nacionalidad1.hide();
			lblNacionalidad1.hide();
		}else if(tipoDoc1.val()=="2" || tipoDoc1.val()=="3"){
			nacionalidad1.show();
			lblNacionalidad1.show();
		};
	});
	
	var tipoDoc2 = $('#pasajero-n2').find("select[id=tipoDocumento]");
	var nacionalidad2 = $('#pasajero-n2').find("select[id=nacionalidad]");
	var lblNacionalidad2 =$('#pasajero-n2').find("label[id=lblNacionalidad]");
	
	tipoDoc2.change(function(){
		
		if(tipoDoc2.val()=="1"){
			nacionalidad2.hide();
			lblNacionalidad2.hide();
		}else if(tipoDoc2.val()=="2"  || tipoDoc2.val()=="3"){
			nacionalidad2.show();
			lblNacionalidad2.show();
		};
	});
	
	var tipoDoc3 = $('#pasajero-n3').find("select[id=tipoDocumento]");
	var nacionalidad3 = $('#pasajero-n3').find("select[id=nacionalidad]");
	var lblNacionalidad3 =$('#pasajero-n3').find("label[id=lblNacionalidad]");
	
	tipoDoc3.change(function(){
		
		if(tipoDoc3.val()=="1"){
			nacionalidad3.hide();
			lblNacionalidad3.hide();
		}else if(tipoDoc3.val()=="2" || tipoDoc1.val()=="3"){
			nacionalidad3.show();
			lblNacionalidad3.show();
		};
	});
	
	var tipoDoc4 = $('#pasajero-n4').find("select[id=tipoDocumento]");
	var nacionalidad4 = $('#pasajero-n4').find("select[id=nacionalidad]");
	var lblNacionalidad4 =$('#pasajero-n4').find("label[id=lblNacionalidad]");
	
	tipoDoc4.change(function(){
		
		if(tipoDoc4.val()=="1"){
			nacionalidad4.hide();
			lblNacionalidad4.hide();
		}else if(tipoDoc4.val()=="2" || tipoDoc1.val()=="3"){
			nacionalidad4.show();
			lblNacionalidad4.show();
		};
	});
	
	var tipoDoc5 = $('#pasajero-n5').find("select[id=tipoDocumento]");
	var nacionalidad5 = $('#pasajero-n5').find("select[id=nacionalidad]");
	var lblNacionalidad5 =$('#pasajero-n5').find("label[id=lblNacionalidad]");
	
	tipoDoc5.change(function(){
		
		if(tipoDoc5.val()=="1"){
			nacionalidad5.hide();
			lblNacionalidad5.hide();
		}else if(tipoDoc5.val()=="2" || tipoDoc1.val()=="3"){
			nacionalidad5.show();
			lblNacionalidad5.show();
		};
	});
	
	var tipoDoc6 = $('#pasajero-n6').find("select[id=tipoDocumento]");
	var nacionalidad6 = $('#pasajero-n6').find("select[id=nacionalidad]");
	var lblNacionalidad6 =$('#pasajero-n6').find("label[id=lblNacionalidad]");
	
	tipoDoc6.change(function(){
		
		if(tipoDoc6.val()=="1"){
			nacionalidad6.hide();
			lblNacionalidad6.hide();
		}else if(tipoDoc6.val()=="2" || tipoDoc1.val()=="3"){
			nacionalidad6.show();
			lblNacionalidad6.show();
		};
	});
};

//carga la informacion de las nacionalidades
var populateSelBoxNacionalidad = function(data,cantSelBox) {
	
	//data: informacion para llenar el selbox
	//cantSelBox: cantidad de selBox a llenar

	for(var i=0;i<cantSelBox;i++){
		//getting the select box element
		var selectBox=$('#pasajero-n'+(i+1)).find("select[id=nacionalidad]");
		//setting the content inside as empty
		selectBox.innerHTML = '';
		//appending the first option as select to the select box
		selectBox.append('<option value="">Seleccionar</option>');
		//adding all other values
		for (var j=0;j<data.length;j++) {
			selectBox.append('<option value="'+data[j].idPais+'">'+data[j].nombre+'</option>');
		};
	};
	
};

//verifica si se selecciono al menos un checkbox del grupo
var verificarCheckboxGroup = function(nombreChkGroup,mensaje){
	var elementosChk = $('input:checkbox[name='+nombreChkGroup+']:checked');
	if(elementosChk.length==0){
		showMessage(mensaje, 'fail');
		return false;
	}
	return true;
};


var abrirHttp = function(pagina, titulo){
	window.open (pagina,titulo,'width=400,height=400');
};

var obtenerEstadoVuelo = function(fechaSalida,horaSalida,listaAsientosOcupados,capacidadAvion){
	var horas = 0;
	var diferencia = 0;
	var strFechaHoraVuelo = fechaSalida.substring(0,4)+","+fechaSalida.substring(5,7)+","+fechaSalida.substring(8)+","+horaSalida.substring(0,2)+":"+horaSalida.substring(2);
	fechaHoraVuelo = new Date(strFechaHoraVuelo);
	hoy = new Date();
	diferencia = fechaHoraVuelo - hoy;
	horas = Math.round(diferencia/(1000*60*60));
	
	if(listaAsientosOcupados.length<capacidadAvion){//hay asientos disponibles
		if(horas>48){//verifica hora de salida
			return "0";//Aun no abierto
		}else if(horas<=48 && horas>=2){
			return "1";//Abierto
		}else{
			return "2";//Cerrado x hora de salida
		};
	}else{//no hay asientos disponibles
		return "3";//Cerrado x disponibilidad
	}
	

};

var formatearFecha = function(fecha){
	var fechastr = fecha.substring(0,4)+","+fecha.substring(5,7)+","+fecha.substring(8) ;
	var date = new Date(fechastr);
	return date.format('dd "de" mmmm yyyy');
};


//validaciones
var validarForm = function(entity){
	switch(entity){
		case LOGIN:
			if(!verificarCampo('#email','Por favor,ingrese su email')) return;
			if(!verificarCampo('#password','Por favor, ingrese su password.')) return;
			break;
		case REG_USER:
			if(!verificarCampo('#nombre','Por favor, ingrese su nombre.')) return;			
			if(!verificarCampo('#apellidoPaterno','Por favor, ingrese su apellido Paterno.')) return;
			if(!verificarCampo('#apellidoMaterno','Por favor, ingrese su apellido Materno.')) return;	
			if(!verificarCampo('#email','Por favor, ingrese su E-mail.')) return;	
			if(!verificarCampo('#password','Por favor, ingrese su Password.')) return;	
			if(!verificarCampo('#confirmarPassword','Por favor, confirme su password.')) return;	
			if(!verificarCampo('#fechaNacimiento','Por favor, ingrese su Fecha de Nacimiento.')) return;	
			var valueTipoPago = $('#tipoDocumento').val();
			if(valueTipoPago=='none'){
				showMessage('Por favor seleccione un tipo de Documento', 'fail');
				return;
			}
			if(!verificarCampo('#numeroDocumento','Por favor, ingrese su Numero de Documento.')) return;
			var valuePassword= $('#password').val();
			var valueConfirmPassword = $('#confirmarPassword').val();
			if(valuePassword != valueConfirmPassword){
				showMessage('Por favor, los passwords ingresados deben coincidir.', 'fail');
				return;
			}
			break;
		case REQ_PASS:
			if(!verificarCampo('#email','Por favor, ingrese su Email.')) return;	
			break;
		case CONFIRMAR_PASS:
			if(!verificarCampo('#password','Por favor, ingrese su password')) return;	
			if(!verificarCampo('#repassword','Por favor, re-ingrese su password.')) return;	
			var valuePassword = $('#password').val();
			var valueRepassword=$('#repassword').val();
			if(valuePassword != valueRepassword){
				showMessage('Contraseñas no coinciden. Por favor reintente', "fail");
				return;
			}		
			if(valuePassword.length <= 5){
				showMessage('Ingrese como mínimo 6 caracteres para la contraseña nueva', "fail");
				return;
			}
			break;
		case BUY_BOLETO:
			var valueReservaElegida = $('input[type=radio]:checked', 'form#comprar-boleto-form').val();
			if(valueReservaElegida == undefined){
				showMessage('Por favor, seleccione una reserva.', "fail");
				return;
			}
			var valueTipoPago = $('#tipoPago').val();
			if(valueTipoPago=='none'){
				showMessage('Por favor seleccione un tipo de pago', 'fail');
				return;
			}
			break;
		case EXCH_RESERVA:
			var valueReservaElegida = $('input[type=radio]:checked', 'form#canjear-reserva-form').val();
			if(valueReservaElegida == undefined){
				showMessage('Por favor, seleccione una reserva.', "fail");
				return;
			}
			break;
		case BUSCAR_VUELO:
			$('#vuelos-ida').hide();
			$('#vuelos-vuelta').hide();
			if(!verificarCampo("#origen","Por favor, ingrese una ciudad de origen")) return;
			if(!verificarCampo("#destino","Por favor ingrese una ciudad de destino")) return;
			if(!verificarCampo("#fechaSalida","Por favor seleccione una fecha de Salida")) return;
			var tipoBusqueda = $('input[name=tipoBusqueda]:checked').val();
			if(tipoBusqueda==0)
				if(!verificarCampo("#fechaRegreso", "Por favor, seleccione una fecha de regreso")) return;
			break;
		case CONFIRM_FETCH_FLIGHT:
			var tipoBusqueda = $('input[name=tipoBusqueda]:checked').val();
			var vueloIda = $('input[name=idVueloIda]:checked').val();
			
			if(vueloIda==undefined){
				showMessage('Por favor seleccione un vuelo de ida','fail');
				return;
			}
			if(tipoBusqueda==0){
				var vueloVuelta = $('input[name=idVueloVuelta]:checked').val();
				if(vueloVuelta==undefined){
					showMessage('Por favor seleccione un vuelo de Vuelta','fail');
					return;
				}
			}
			break;
		case PASAJERO_RESERVA:
			var value=validarPersonasReserva();
			if(!value)return;
			break;
		case UPD_USER:
			if(!verificarCampo('#nombre','Por favor, ingrese su nombre.')) return;			
			if(!verificarCampo('#apellidoPaterno','Por favor, ingrese su apellido Paterno.')) return;
			if(!verificarCampo('#apellidoMaterno','Por favor, ingrese su apellido Materno.')) return;	
			if(!verificarCampo('#email','Por favor, ingrese su E-mail.')) return;	
			if(!verificarCampo('#fechaNacimiento','Por favor, ingrese su Fecha de Nacimiento.')) return;	
			if(!verificarCampo('#numeroDocumento','Por favor, ingrese su Numero de Documento.')) return;
			var valuePassword= $('#password').val();
			var valueConfirmPassword = $('#confirmarPassword').val();
			if(valuePassword!=""){
			if(valuePassword != valueConfirmPassword){
				showMessage('Por favor, los passwords ingresados deben coincidir.', 'fail');
				return;
			}
			}
			break;
		case BUSCAR_RESERVA:
			if(!verificarCampo("#idReserva","Por favor ingrese un codigo de reserva")) return;
			break;
		case SEL_RESERVA:
			var valueReservaElegida = $('input[type=radio]:checked', 'form#sel-reserva-form').val();
			if(valueReservaElegida == undefined){
				showMessage('Por favor, seleccione una reserva.', "fail");
				return;
			}
			break;	
		case CHECKIN_STP2:
			if(!verificarCheckboxGroup("pasajerosElegidos","Por favor, seleccione al menos un pasajero de la lista")) return;
			break;
		case CHECKIN_STP3:
			if(!verificarCheckboxGroup('declaracion','Por favor, Acepte la declaracion necesaria para viajar')) return;
			break;
		case CHECKIN_STP4:
			var flag = false;
			var formEleList = $('#seleccion-asientos-form').find('#idPersona').serializeArray();
			
			for(var i=0;i<formEleList.length;i++){
			    var asiento = $('#seleccion-asientos-form').find('#asientoEle-'+formEleList[i].value).html();
			if(asiento=="---"){
			    flag=true;
			    break;
			};
			};
			
			if(flag){
				showMessage('Por favor, seleccione el asiento a todos los pasajeros', 'fail');
				return;
			};
			break;
		default :
			break;
	}
	if(entity==LOGIN){
		var datos=obtenerDatosDeForm(entity);
		 login(datos);	
	}else if(entity==REG_USER){
		var datos=obtenerDatosDeForm(entity);
		
		 registrarUsuario(datos);
	}else if(entity==REQ_PASS){
		var datos=obtenerDatosDeForm(entity);
		 datos[datos.length]=new param('urlContext',window.location);
		 cambioPass(datos);
		 $('form#'+entity+"-form").reset();
	}else if(entity==CONFIRMAR_PASS){		
		var datos=obtenerDatosDeForm(entity);
		 confirmarPass(datos);	 
	}else if(entity==BUY_BOLETO){
		showMessage(' ',' ');
		var datos=new Array();
		var reserva =$('input[type=radio]:checked', 'form#'+entity+'-form').val();
		var tipoPago = $('#tipoPago').val();
		datos[datos.length]= new param('reserva.idReserva',reserva);		
		if(tipoPago=='tarjetaCredito'){
			datos[datos.length]= new param('tipoBusqueda','pagoPayPal');
			datos[datos.length]= new param('tipoPago',tipoPago);
			comprarReserva(datos);
		}if(tipoPago=='prescencial'){
			datos[datos.length]= new param('tipoBusqueda','pagoPayPal');
			datos[datos.length]= new param('tipoPago',tipoPago);
			datos[datos.length]=new param('reserva.totalPagar',$('#totalPagar').html());
			datos[datos.length]=new param('idVuelo',$('#idVuelo').html());
			mostrarBCP(datos);
		}
	}else if(entity==EXCH_RESERVA){
		showMessage(' ','fail');
		var datos=new Array();
		var reserva =$('input[type=radio]:checked', 'form#'+'canjear-reserva'+'-form').val();
		var email = $("#emailUsuario").val();
		var millas = $('#millas').val();
		datos[datos.length]= new param('reserva.idReserva',reserva);
		datos[datos.length]= new param('usuario.email',email);
		datos[datos.length]= new param('usuario.millas',millas);
		canjearBoleto(datos);

	}else if(entity==BUSCAR_VUELO){
		showMessage(' ',' ');
		var datos=new Array();
		datos=obtenerDatosDeForm(entity);
		buscarVuelo(datos);
	}else if (entity==CONFIRM_FETCH_FLIGHT){
		showMessage('','buscar-vuelo','fail');
		var datos=new Array();
		datos=obtenerDatosDeForm('confirmar-buscar-vuelo');
		datos[datos.length] = new param('fechaIda',$('#fechaSalida').val());
		datos[datos.length] = new param('fechaVuelta',$('#fechaRegreso').val());
		confirmarBuscarvuelo(datos);
	}else if(entity==PASAJERO_RESERVA){
		showMessage(' ',' ');
		var datos=new Array();
		datos=obtenerDatosDeForm(entity);
		llenarLista(datos);
	}else if(entity==REGISTRO_RESERVA){
		showMessage(' ',' ');
		var datos=new Array();
		datos=obtenerDatosDeForm(entity);
		registroReserva(datos);
	}else if(entity==UPD_USER){
		showMessage(' ',' ');
		var datos=new Array();
		datos=obtenerDatosDeForm(entity);
		actualizarUsuario(datos);
	}else if(entity==BUSCAR_RESERVA){
		var datos=new Array();
		datos=obtenerDatosDeForm(entity);
		buscarReserva(datos);
	}else if(entity==SEL_RESERVA){	
		var datos=new Array();
		datos=obtenerDatosDeForm(entity);
		buscarReserva(datos);
	}else if (entity==CHECKIN_STP2){
		showMessage('','');
		var datos=new Array();
		var elementosChk = $('input:checkbox[name=pasajerosElegidos]:checked');
		var listPasajerosChk = new Array();
		elementosChk.each(function(index){
			listPasajerosChk[listPasajerosChk.length] = $(this).val();
		});
		datos[datos.length] = new param('pasajerosSeleccionados',listPasajerosChk);
		checkinStp2(datos);
		
	}else if(entity==CHECKIN_STP3){
		showMessage('','');
		var datos=new Array();
		var cantPasajeros = $('#cont-pasajeros-chkinstp3').find('form:visible').length;
		for(var i=0;i<cantPasajeros;i++){
			var elementosForm = obtenerDatosDeForm('#pasajero-n'+(i+1));
			//une los arrays
			Array.prototype.push.apply(datos, elementosForm);
		}
		checkinStp3(datos);	
	}else if(entity==CHECKIN_STP4){
		showMessage('','');
		var datos = new Array();
		var asiento='';
		var formEleList = $('#seleccion-asientos-form').find('#idPersona').serializeArray();
		for(var i=0;i<formEleList.length;i++){

			asiento = $('#seleccion-asientos-form').find('#asientoEle-'+formEleList[i].value).html();
			datos[datos.length] = new param('asiento',asiento);
		}

		checkinStp4(datos);	
	};
	
}