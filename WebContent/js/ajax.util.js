var LOGIN='login';
var REG_USER='usuario';
var REQ_PASS = 'request-password';
var CONFIRMAR_PASS = 'password';
var LST_RESERVAS = 'reserva';
var BUY_BOLETO = 'comprar-boleto';
var EXCH_RESERVA ='canjear-reserva';
var BUSCAR_VUELO = 'buscar-vuelo';
var CONFIRM_FETCH_FLIGHT = 'confirmar-buscar-vuelo';
var PASAJERO_RESERVA = 'pasajero-reserva';
var REGISTRO_RESERVA = 'registro-reserva';
var UPD_USER='actualizar-usuario';
var BUSCAR_RESERVA = 'buscar-reserva';
var SEL_RESERVA = 'sel-reserva';
var CHECKIN_STP2 = 'checkin-stp2';
var CHECKIN_STP3 = 'checkin-stp3';
var CHECKIN_UPT_STP3 = 'checkin-upt-stp3';
var CHECKIN_STP4 = 'checkin-stp4';
var CHECKIN_STP5 = 'checkin-stp5';


var registroReserva = function(datos) {
	var successFn=function(resp){	
		showHideCargando(false);
			if(resp!=undefined){
				$('#portlets').html('<div class="clear"></div><div align="center" class="message" id="show-message"></div>');
				showMessage(resp.mensaje,resp.tipoRpta);
			}
		};
	showHideCargando(true);
	getData('registroReserva', datos, successFn, null);
}

var confirmarHabilitar = function(idUsuario, value){
	if(value=='true'){
		if(confirm('Esta seguro que desea habilitar el usuario?'))
			habilitarUsuario(idUsuario, value);
	}else{
		if(confirm('Esta seguro de que desea deshabilitar el usuario?')) 
			habilitarUsuario(idUsuario,value);
	}	
}

var habilitarUsuario = function(idUsuario,value){
	var successFn=function(resp){
		if(resp!=undefined){
			showHideCargando(false);
			listarUsuarios(resp);
			showMessage(resp.mensaje,resp.tipoRpta);
		}
		
	}
	showHideCargando(true);
	var datos= new Array();
	datos[datos.length]=new param('usuario.idUsuario',idUsuario);
	if(value=='false') value="0";
	else if(value=='true') value="1";
	datos[datos.length]=new param('usuario.estado',value);
	postData('habilitarUsuario',datos,successFn,null);
}

var editarUsuario = function(idUsuario){
	var successFn=function(resp){
		showHideCargando(false);
		if(resp!=undefined){
			var data=resp.usuario;
			$('#idUsuario').val(data.idUsuario);
			$('#nombre').val(data.nombre);
			$('#apellidoPaterno').val(data.apellidoPaterno);
			$('#apellidoMaterno').val(data.apellidoMaterno);
			$('#fechaNacimiento').val(data.fechaNacimiento);
			$('#tipoDocumento').val(data.tipoDocumento);
			$('#numeroDocumento').val(data.numeroDocumento);
			if(data.sexo=='MASCULINO'){
				data.sexo=1;
			}else{
				data.sexo=2;
			}
			$('[id=sexo]').filter('[value="'+data.sexo+'"]').attr('checked','checked');
			$('#email').val(data.email);
		}
		
	}
	getData('usuario.html',null,function(resp){
		$('#portlets').html(resp);
		initDateTimePicker("#fechaNacimiento");	
	},null);
	showHideCargando(true);
	var datos=new Array();
	datos[datos.length]=new param('usuario.idUsuario',idUsuario);
	getData("getUsuario",datos,successFn,null);
}

var actualizarUsuario = function(datos){
	var successFn = function(resp){
		showHideCargando(false);
		if(resp!=undefined){
			showMessage(resp.mensaje,resp.tipoRpta);
		}
	}
	showHideCargando(true);
	postData("actualizarUsuario",datos,successFn,null);
}

var listarUsuarios = function(dat){
	var successFn=function(resp){	
		showHideCargando(false);
		var data='';
			if(resp!=undefined){
				data = resp.usuarios;
				var htm='';
				if(data.length!=0){
					for(var i=0;i<data.length;i++){
						htm+="<tr>";						
						htm+="<td>"+data[i].email+"</td>";
						htm+="<td>"+data[i].nombre+" "+data[i].apellidoPaterno+" "+data[i].apellidoMaterno +"</td>";
						htm+="<td>"+data[i].edad+"</td>";
						htm+="<td>"+data[i].tipoDocumento+"</td>";
						htm+="<td>"+data[i].numeroDocumento+"</td>";
						var eliminar='confirmarHabilitar("'+ data[i].idUsuario;
						var iconHabilitar="'images/icons/";
						if(data[i].estado=='0'){
							eliminar+='","true")';
							iconHabilitar+="action_check.gif'";
						}else{
							eliminar+='","false")';
							iconHabilitar+="action_delete.gif'";
						}
							
						var editar='editarUsuario("'+ data[i].idUsuario + '")';
						var pointer='"pointer"';
						htm+="<td><img onmouseover='this.style.cursor="+pointer+";' src=" + iconHabilitar + " width='16' onClick='"+eliminar+"' height='16'/><img onmouseover='this.style.cursor="+pointer+";' src='images/icons/edit.gif' width='16'   onClick='"+ editar +"' height='16'/></td>";
						htm+="</tr>";	
					}
				}
				if(dat!=undefined)
					showMessage(dat.mensaje,dat.tipoRpta);
				$('#usuarios-tbody').html(htm);
			}
		}
	showHideCargando(true);
	getData('listarUsuario', null, successFn, null);
}


var llenarLista = function(datos) {
	var successFn=function(resp){
			showHideCargando(false);
			if(resp!=undefined){
				setContenido(resp);
			}
		}
	showHideCargando(true);
	getData('pasajeroReserva', datos, successFn, null);
}


var confirmarBuscarvuelo = function(datos){
	var successFn=function(resp){
		showHideCargando(false);
			if(resp!=undefined){
				$('#portlets').html(resp);
				initDateTimePicker('#fechanacimiento');
			}
		}
	showHideCargando(true);
	postData("confirmarBuscarVuelo",datos,successFn,null);
}

var listarReservaPorUsuario=function(entity){
	var successFn=function(resp){
		var data='';
		if(resp){
			data=resp.reservas;
		}
		showHideCargando(false);
		//creating the html content
		var htm='';
		if(data.length!=0){
			for (var i=0;i<data.length;i++){
				htm+='<tr>';
				if(entity==LST_RESERVAS){
					htm+='<td>'+data[i].fechaReserva+'</td><td>'+data[i].horaSalida.substr(0,2)+':'+data[i].horaSalida.substr(2)+' horas</td><td>'+data[i].horaLlegada.substr(0,2)+':'+data[i].horaLlegada.substr(2)+' horas</td><td id="idVuelo">'+data[i].vuelo+'</td><td>'+data[i].cabina.substr(0,1)+data[i].cabina.substr(1).toLowerCase()+'</td><td id="totalPagar">$'+data[i].totalPagar+'</td><td><input type="radio" id="reservaSeleccionada" name="reserva.idReserva" value="'+ data[i].idReserva +'"></td>';
				}else if (entity==EXCH_RESERVA){
					var millasDisponibles = $('#millas').val();
					if(millasDisponibles>data[i].millas){
						htm+='<td>'+data[i].fechaReserva+'</td><td>'+data[i].horaSalida.substr(0,2)+':'+data[i].horaSalida.substr(2)+' horas</td><td>'+data[i].horaLlegada.substr(0,2)+':'+data[i].horaLlegada.substr(2)+' horas</td><td id="idVuelo">'+data[i].vuelo+'</td><td>'+data[i].cabina.substr(0,1)+data[i].cabina.substr(1).toLowerCase()+'</td><td id="totalPagar">$'+data[i].totalPagar+'</td><td><input type="radio" id="reservaSeleccionada" name="reserva.idReserva" value="'+ data[i].idReserva +'"></td>';
					}else{
						htm+='<td>'+data[i].fechaReserva+'</td><td>'+data[i].horaSalida.substr(0,2)+':'+data[i].horaSalida.substr(2)+' horas</td><td>'+data[i].horaLlegada.substr(0,2)+':'+data[i].horaLlegada.substr(2)+' horas</td><td id="idVuelo">'+data[i].vuelo+'</td><td>'+data[i].cabina.substr(0,1)+data[i].cabina.substr(1).toLowerCase()+'</td><td id="totalPagar">$'+data[i].totalPagar+'</td><td>----</td>';
					}
				}
					
				htm+='</tr>';
			}
		}else{

			htm+='<tr><td align="center" colspan="7"><p id="fail" class="message">No se encontraron reservas para este usuario</p></td></tr>';
		}
		$('#'+entity+'-list-tbody').html(htm);
	}
	showHideCargando(true);
	var tipoBusqueda = new Array();
	tipoBusqueda[tipoBusqueda.length]=new param('tipoBusqueda','porUsuario');
	getData("buscarReserva",tipoBusqueda,successFn,null);
}

var mostrarBCP = function(datos){
	var successFn=function(resp){
			if(resp!=undefined){
				showHideCargando(false);
				var popup=window.open('','Pago Prescencial','height=400,width=500');
				popup.document.write(resp);
				popup.document.close();
			}
	}
	showHideCargando("reserva",true);
	getData("prescencialReserva",datos,successFn,null);
	
}

var buscarVuelo = function(datos){
		var successFn=function(resp){
				showHideCargando(false);
				
				if(resp){	
					var tipoBusqueda = $('input[name=tipoBusqueda]:checked').val();
					if(tipoBusqueda==0){
						if(resp.vuelosVuelta.length==0 && resp.vuelosIda.length==0){
							showMessage("No se encontraron vuelos de ida ni de vuelta","fail");
						}else{
							if(resp.vuelosVuelta.length==0){
								showMessage("No se encontraron vuelos de vuelta","fail");
							}else{
								var htm='';
								$('#vuelos-vuelta').show();
								$('#opciones').show();
								var data=resp.vuelosVuelta;
								for (var i=0;i<data.length;i++){
										htm+='<tr>';
										htm+='<td>'+data[i].numVuelo+'</td><td>'+data[i].horaSalida.substr(0,2)+':'+data[i].horaSalida.substr(2)+' horas</td><td>'+data[i].horaLlegada.substr(0,2)+':'+data[i].horaLlegada.substr(2)+' horas</td><td>'+data[i].duracion.substr(0,2)+':'+data[i].duracion.substr(2)+' horas</td><td><input type="radio" id="vueloElegidoIda" name="idVueloIda" value="'+ data[i].idVuelo +'"></td>';
								}
								htm+='</tr>';
								$('#vuelos-vuelta-list-tbody').html(htm);
								$('#ruta-vuelta').html('<img src="images/icons/user.gif" width="16" height="16" /> Vuelos de Vuelta ('+data[0].origen+" - "+data[0].destino+')');
							}
							if(resp.vuelosIda.length==0){
								showMessage("No se encontraron vuelos de ida ","fail");
							}else{
								var htm='';
								$('#vuelos-ida').show();
								$('#opciones').show();
								var data=resp.vuelosIda;
								for (var i=0;i<data.length;i++){
										htm+='<tr>';
										htm+='<td>'+data[i].numVuelo+'</td><td>'+data[i].horaSalida.substr(0,2)+':'+data[i].horaSalida.substr(2)+' horas</td><td>'+data[i].horaLlegada.substr(0,2)+':'+data[i].horaLlegada.substr(2)+' horas</td><td>'+data[i].duracion.substr(0,2)+':'+data[i].duracion.substr(2)+' horas</td><td><input type="radio" id="vueloElegidoVuelta" name="idVueloVuelta" value="'+ data[i].idVuelo +'"></td>';
								}
								htm+='</tr>';
								$('#vuelos-ida-list-tbody').html(htm);
								$('#ruta-ida').html('<img src="images/icons/user.gif" width="16" height="16" /> Vuelos de Ida ('+data[0].origen+" - "+data[0].destino+')');
							}
						}
						
					}else if(resp.vuelosIda==""){
						showMessage("No se encontraron vuelos de ida", "fail");
					}else{
						var htm='';
						$('#vuelos-ida').show();
						$('#opciones').show();
						var data=resp.vuelosIda;
						for (var i=0;i<data.length;i++){
								htm+='<tr>';
								htm+='<td>'+data[i].numVuelo+'</td><td>'+data[i].horaSalida.substr(0,2)+':'+data[i].horaSalida.substr(2)+' horas</td><td>'+data[i].horaLlegada.substr(0,2)+':'+data[i].horaLlegada.substr(2)+' horas</td><td>'+data[i].duracion.substr(0,2)+':'+data[i].duracion.substr(2)+' horas</td><td><input type="radio" id="vueloElegidoIda" name="idVueloIda" value="'+ data[i].idVuelo +'"></td>';
						}
						htm+='</tr>';
						$('#vuelos-ida-list-tbody').html(htm);
						$('#ruta-ida').html('<img src="images/icons/user.gif" width="16" height="16" /> Vuelos de Ida ('+data[0].origen+" - "+data[0].destino+')');
					}
				}

			$('#reservar').show();
			}
			showHideCargando(true);
			getData("buscarVuelo",datos,successFn,null);
}

var login = function(datos){	
	var successFn=function(resp){
		if(resp.mensaje==null){
			var url=window.location.toString();
			var parts=url.split("/");
			url="http:/";
			for(var i=1;i<parts.length-1;i++){
				url+=parts[i]+"/";
			}
			window.location=url;
		}else{
			showMessage(resp.mensaje,resp.tipoRpta);
		}
	}
	showHideCargando(true);
	postData('login',datos,successFn,null);
}

var registrarUsuario = function(datos){
	
	var successFn=function(resp){
		showHideCargando(false);
		var mensaje = resp.mensaje;
			if(mensaje!=undefined){
				showMessage(resp.mensaje,"fail");
			}else{
				$( "#nuevo-usuario-form" ).dialog("close");
			}
		}
	 showHideCargando(true);
	 postData('registrarUsuario',datos,successFn,null);
	 var emailUsuario = $('#emailUsuario').val();
	 if(emailUsuario!=undefined){
		 listarUsuarios();
	 }
}

var comprarReserva = function(datos){
	var successFn=function(resp){
		showHideCargando(false);
			if(resp!=undefined){
				var popup=window.open('','Pago On-Line','height=400,width=600');
				popup.document.write(resp);
				popup.document.close();
			}
		}
	 showHideCargando(true);
	 getData('comprarReserva',datos,successFn,null);
}

var cargarUsuarioSesion = function(){
	var successFn=function(resp){
		showHideCargando(false);
		var nombre=resp.usuario.nombres;
		var millas=resp.usuario.millas;
		var email=resp.usuario.email;
			if(nombre!=undefined){
				$('#nombre').val(nombre);
				$('#millas').val(millas);
				$('#email').val(email);
			}
		}
	 showHideCargando(true);
	 getData('obtenerUsuario',null,successFn,null);
}

var canjearBoleto = function(datos){
	var successFn=function(resp){
		showHideCargando(false);
		var mensaje=resp.mensaje;
			if(mensaje!=undefined){
				showMessage(mensaje,resp.tipoRpta);
			}
		}
	 showHideCargando(true);
	 getData('canjearMillasReserva',datos,successFn,null);
}

var confirmarCompraBoleto = function(tknPago, idReserva, tipoPago){
	var successFn=function(resp){
		showHideCargando(false);
		var mensaje = resp.mensaje;
			if(mensaje!=undefined){
				showMessage(mensaje,resp.tipoRpta);
			}
		}
	 var datos = new Array();
	 datos[datos.length] = new param('tknPago',tknPago);
	 datos[datos.length] = new param('reserva.idReserva',idReserva);
	 datos[datos.length] = new param('tipoPago',tipoPago);
	 showHideCargando(true);
	 getData('comprarReserva',datos,successFn,null);
}


var confirmarPass=function(datos){
	var successFn=function(resp){
		showHideCargando(false);
		var mensaje = resp.mensaje;
		var tipoRpta = resp.tipoRpta;
			if(mensaje!=undefined){
				showMessage(mensaje,tipoRpta);
			}
		}
	 showHideCargando(true);
	 postData('comfirmarPassUsuario',datos,successFn,null);
	 
	 
}

var cambioPass=function(datos){
	var successFn=function(resp){
		showHideCargando(false);
		var mensaje = resp.mensaje;
		var tipoRpta = resp.tipoRpta;
			if(mensaje!=undefined){
				showMessage(mensaje,tipoRpta);
			}else{
				showMessage("Hubo un error al comunicarse con el servidor. Por favor, reintente.",'fail');
			}
		}
	 showHideCargando(true);
	 postData('solicitarCambioPasswordUsuario',datos,successFn,null);
}

var postData=function(url,data,successFn,errorFn){
	
	// making the ajax call
	$.ajax({
		url : url,
		type : "POST",
		data:data,
		success : function(resp) {
			//calling the user defined success function
			if(successFn)
			successFn(resp);	
		},
		error:function(e){
		//calling the user defined error function
		if(errorFn)
		 errorFn(e);
	}
	});
}

var buscarReserva = function(datos){
	
	var successFn=function(resp){
		showHideCargando(false);
		var mensaje = resp.mensaje;
			if(mensaje!=undefined){
				showMessage(resp.mensaje,"reserva","fail");
			}else{
				setContenido(resp);
				cargarInfoCheckInStp2();
			};
		};
	showHideCargando(true);
	postData("buscarxcodigoReserva",datos,successFn,null);	
};

var listarReservaPagadaPorUsuario=function(entity){
	var successFn=function(resp){
		var data='';
		if(resp){
			data=resp.reservas;
		};
		showHideCargando(false);
		//creating the html content
		var htm='';
		if(data!=null){
			for (var i=0;i<data.length;i++){
				htm+='<tr>';
				htm+='<td>'+data[i].fechaReserva+'</td>';
				htm+='<td>'+data[i].fechaPago+'</td>';
				htm+='<td id="idVuelo">'+data[i].vuelo+'</td>';
				htm+='<td>'+data[i].fechaVuelo+'</td>';
				htm+='<td>'+data[i].horaSalida.substr(0,2)+':'+data[i].horaSalida.substr(2)+' horas</td>';
				htm+='<td>'+data[i].horaLlegada.substr(0,2)+':'+data[i].horaLlegada.substr(2)+' horas</td>';
				htm+='<td>'+data[i].cabina.substr(0,1)+data[i].cabina.substr(1).toLowerCase()+'</td>';
				htm+='<td><input type="radio" id="reservaSeleccionada" name="reserva.idReserva" value="'+ data[i].idReserva +'"></td>';
				htm+='</tr>';
			};
		}else{
			var thElesLength=$('#'+entity+'-list-ctr table thead th').length;
			htm+='<tr><td align="center"colspan="'+thElesLength+'"><div class="message"><p class="message" id="fail">No se encontraron reservas pagadas</p></div></td></tr>';
		};
		$('#'+entity+'-list-tbody').html(htm);
	};
	showHideCargando(true);
	getData("buscarPagadasReserva",null,successFn,null);
};

var cargarInfoCheckInStp2=function(){
	var successFn=function(resp){
		showHideCargando(false);
		var listaPasajeros='';
		var vuelo='';
		var estado = '0';
		var psjChequeados = '0';
		var listaAsientosOcupados='';
		var capacidadAvion='';
	
		if(resp){
			listaPasajeros=resp.listaPasajerosReserva;
			vuelo=resp.vuelo;
			listaAsientosOcupados=resp.listaAsientosOcupados;
			capacidadAvion=resp.capacidadAvion;

		}
		//cargando informacion de pasajeros
		if(listaPasajeros!=null){
			var htm='';
			for (var i=0;i<listaPasajeros.length;i++){	
				
				htm+='<tr>';
				htm+='<td class="campo_tbody_form_proceso">';
				
				if(listaPasajeros[i].estadoCheckin!='PENDIENTE' || listaPasajeros[i].tipoPersona=='INFANTE'){
					htm+='<input type="checkbox" disabled="disabled" name="pasajerosElegidos" value="'+listaPasajeros[i].idPersona+'" id="pasajeroElegido'+(i+1)+'">';
					psjChequeados++;//suma uno a pasajeros chequeados
				}else{
					htm+='<input type="checkbox" name="pasajerosElegidos" value="'+listaPasajeros[i].idPersona+'" id="pasajeroElegido'+(i+1)+'">';		
				};
				
				htm+='</td>';
				htm+='<td class="campo_tbody_form_proceso">'+listaPasajeros[i].nombre+'</td>';
				htm+='<td class="campo_tbody_form_proceso">'+listaPasajeros[i].apellidoPaterno+' '+listaPasajeros[i].apellidoMaterno+'</td>';
				htm+='<td class="campo_tbody_form_proceso">'+listaPasajeros[i].genero+'</td>';
				htm+='<td class="campo_tbody_form_proceso">'+listaPasajeros[i].tipoPersona+'</td>';
				htm+='<td class="campo_tbody_form_proceso">'+listaPasajeros[i].estadoCheckin+'</td>';
				htm+='</tr>';
									
			};
			$('#pasajeros-list-tbody').html(htm);
			
			
		//cargando informacion de vuelos
		if(vuelo!=null){
			var htm='';
			htm+='<tr>';
			htm+='<td class="campo_tbody_form_proceso">'+formatearFecha(vuelo.fechaSalida)+'</td>';
			htm+='<td class="campo_tbody_form_proceso">'+vuelo.origen+'</td>';
			htm+='<td class="campo_tbody_form_proceso">'+vuelo.horaSalida.substr(0,2)+':'+vuelo.horaSalida.substr(2)+'</td>';
			htm+='<td class="campo_tbody_form_proceso">'+vuelo.destino+'</td>';
			htm+='<td class="campo_tbody_form_proceso">'+vuelo.horaLlegada.substr(0,2)+':'+vuelo.horaLlegada.substr(2)+'</td>';
			htm+='<td class="campo_tbody_form_proceso">'+vuelo.numVuelo+'</td>';
			
			//verifica el estado del vuelo(con relacion a la hora de salida y a los asientos disponibles)
			estado = obtenerEstadoVuelo(vuelo.fechaSalida,vuelo.horaSalida,listaAsientosOcupados,capacidadAvion);
			
			switch (estado) {
			case "0"://Aun no abierto
				htm+='<td class="campo_tbody_form_proceso"><span class="vueloCerrado"></span><span>Aun no abierto</span></td>';
				showMessage("El vuelo aun no esta abierto para check-in ",'fail');
				$('#btn_continuar').hide();
				$('#btn_atras').hide();
				break;
			case "1"://Abierto
				htm+='<td class="campo_tbody_form_proceso"><span class="vueloAbierto"></span><span>Abierto</span></td>';
				
				if(psjChequeados==listaPasajeros.length){//todos los pasjeros fueron chequeados
					$('#btn_continuar').hide();
					$('#btn_atras').hide();
					showMessage("Ya se realizo el chequeo de todos los pasajeros de esta reserva", 'fail');
				}else{//faltan pasajeros x chequear
					$('#btn_home').hide();
				};
				
				break;
			case "2"://Cerrado x hora de salida
				htm+='<td class="campo_tbody_form_proceso"><span class="vueloCerrado"></span><span>Cerrado</span></td>';
				showMessage("El vuelo esta cerrado para check-in ", 'fail');
				$('#btn_continuar').hide();
				$('#btn_atras').hide();
				break;
			case "3"://Cerrado x disponibilidad
				htm+='<td class="campo_tbody_form_proceso"><span class="vueloCerrado"></span><span>Cerrado</span></td>';
				showMessage("El vuelo esta cerrado para check-in, por falta de disponibilidad de asientos", 'fail');
				$('#btn_continuar').hide();
				$('#btn_atras').hide();
				break;	
			default:
				break;
			};
			
			htm+='</tr>';
			
			$('#vuelo-list-tbody').html(htm);
		};
		
		};	
	
	};
	showHideCargando(true);
	getData("cargarInfoStp2Checkin",null,successFn,null);
};

var checkinStp2=function(datos){
	var successFn=function(resp){
		showHideCargando(false);
		var mensaje = resp.mensaje;
			if(mensaje!=undefined){
				showMessage(resp.mensaje,"fail");
			}else{
				setContenido(resp);
				cargarInfoPasajerosCheckInStp3();
		    	showHideNacionalidad();
			}
		};
	showHideCargando(true);
	postData("seleccionarPasajerosCheckin",datos,successFn,null);	
	
};

var cargarInfoPasajerosCheckInStp3=function(){
	var successFn=function(resp){
		showHideCargando(false);
		var listaPasajeros='';
		var nacionalidades='';
		if(resp){
			listaPasajeros=resp.listaPasajerosSeleccionados;
			nacionalidades=resp.listaNacionalidades;
		}

		//cargando select box de nacionalidades
		if(nacionalidades!=null){
			populateSelBoxNacionalidad(nacionalidades,listaPasajeros.length);
		}
	
		//cargando informacion de pasajeros
		if(listaPasajeros!=null){
			
			for (var i=0;i<listaPasajeros.length;i++){
				
				//setear el titulo del div pasajero-n 
				var htm='Pasajero: '+listaPasajeros[i].nombre+" "+listaPasajeros[i].apellidoPaterno+" "+listaPasajeros[i].apellidoMaterno;
				$('#pasajero-n'+(i+1)).find("#titulo").html(htm);
				
				$('#pasajero-n'+(i+1)).find("input[id=idPersona]").val(listaPasajeros[i].idPersona);
				$('#pasajero-n'+(i+1)).find("input[id=nombre]").val(listaPasajeros[i].nombre);
				$('#pasajero-n'+(i+1)).find("input[id=apellidoPaterno]").val(listaPasajeros[i].apellidoPaterno);
				$('#pasajero-n'+(i+1)).find("input[id=apellidoMaterno]").val(listaPasajeros[i].apellidoMaterno);
				$('#pasajero-n'+(i+1)).find("select[id=tipoDocumento]").val(listaPasajeros[i].tipoDocumento);
				$('#pasajero-n'+(i+1)).find("input[id=numDocumento]").val(listaPasajeros[i].numDocumento);
				$('#pasajero-n'+(i+1)).find("select[id=genero]").val(listaPasajeros[i].genero);
				$('#pasajero-n'+(i+1)).find("select[id=tipoPersona]").val(listaPasajeros[i].tipoPersona);

				$('#pasajero-n'+(i+1)).find("select[id=tipoPersona]").attr('disabled','disabled');
				
				//verificando si el tipo de doc es pasaporte para ocultar o mostrar la nacionalidad
				if($('#pasajero-n'+(i+1)).find("select[id=tipoDocumento]").val()=="2" || $('#pasajero-n'+(i+1)).find("select[id=tipoDocumento]").val()=="3"){//tipoDoc passaporte
					$('#pasajero-n'+(i+1)).find("select[id=nacionalidad]").val(listaPasajeros[i].nacionalidad);	
					
				}else{
					$('#pasajero-n'+(i+1)).find("label[id=lblNacionalidad]").hide();
					$('#pasajero-n'+(i+1)).find("select[id=nacionalidad]").hide();
				};
				//haciendo visible el div de pasajero n
				$('#pasajero-n'+(i+1)).show();
				//haciendo visible las opciones
				$('#opciones-chkinstp3').show();
				
			};
		};
		
	};
	showHideCargando(true);
	getData("cargarInfoPasajerosStp3Checkin",null,successFn,null);
};

var actualizarInfoPasajero = function(datos){
	var successFn=function(resp){
		showHideCargando(false);
		var mensaje='';
		if(resp){
			mensaje=resp.mensaje;	
		}
		if(mensaje!=undefined){
			showMessage(mensaje,'fail');
		}else{
			showMessage("Pasajero actualizado con exito",'success');
		}
		
		
		};
	showHideCargando(true);
	postData("actualizarInfoPasajerosCheckin",datos,successFn,null);	
}

var checkinStp3=function(datos){
	var successFn=function(resp){
		showHideCargando(false);
		var mensaje = resp.mensaje;
			if(mensaje!=undefined){
				showMessage(resp.mensaje,"fail");
			}else{
				setContenido(resp);
				cargarInfoPasajerosCheckInStp4();
			}
		};
	showHideCargando(true);
	postData("verificarInfoPasajerosCheckin",datos,successFn,null);	
	
};

var cargarInfoPasajerosCheckInStp4=function(){
	var successFn=function(resp){
		showHideCargando(false);
		var listaPasajeros='';
		if(resp){
			listaPasajeros=resp.listaPasajerosSeleccionados;
		}
		//cargando informacion de pasajeros
		if(listaPasajeros!=null){
			var htm='';
			var pointer='"pointer"';
			for (var i=0;i<listaPasajeros.length;i++){	

				htm+='<tr>';
				htm+='<td>';
				htm+=(i+1);
				htm+='<input type="hidden" value="'+listaPasajeros[i].idPersona+'" name="idPersona" id="idPersona" />';
				htm+='</td>';
				htm+='<td>'+listaPasajeros[i].nombre+'</td>';
				htm+='<td>'+listaPasajeros[i].apellidoPaterno+' '+listaPasajeros[i].apellidoMaterno+'</td>';
				htm+='<td><span name="asientos" id="asientoEle-'+listaPasajeros[i].idPersona+'">---</span></td>';
				htm+='<td>';
				htm+='<a href="javascript:void(0)" id="selAsiento-'+listaPasajeros[i].idPersona+'" onclick="cargarAsientosDisponibles(\''+listaPasajeros[i].idPersona+'\',\'0\')">Seleccionar Asiento</a>';
				htm+='<a href="javascript:void(0)" style="display:none;" id="cambAsiento-'+listaPasajeros[i].idPersona+'"  onclick="cargarAsientosDisponibles(\''+listaPasajeros[i].idPersona+'\',\'1\')">Cambiar Asiento</a>';
				htm+='</td>';
				htm+='</tr>';
				
				
			};
			$('#pasajeros-list-tbody').html(htm);
		};
		
	};
	showHideCargando(true);
	getData("cargarInfoPasajerosStp4Checkin",null,successFn,null);
};

var cargarAsientosDisponibles = function(idPersona,tipoCarga){
	var successFn=function(resp){
		showHideCargando(false);
		var listaAsientosOcupados='';
		var capacidadAvion='';
		if(resp){
			listaAsientosOcupados=resp.listaAsientosOcupados;
			capacidadAvion=resp.capacidadAvion;
		}

		//cargando asientos
		var htm='';
		//variable para posicionar las alas del avion
		var posAlas = Math.floor(capacidadAvion/12)-5;
		if(listaAsientosOcupados!=null){
			
			//llenando los asientos como libre
			for(var i=0;i<capacidadAvion/6;i++){
				htm+='<tr>';
				if(i==posAlas){
					htm+='<td rowspan="10" align="left" style="vertical-align:top;"><img src="images/ala_izq.png"/></td>';
				}else if(i<posAlas || i>posAlas+9){
					htm+='<td style="vertical-align:top;">&nbsp;</td>';
				}
				htm+='<td style="vertical-align:top;">'+(i+1)+'</td>';
				htm+='<td><input style="font-size: xx-small;cursor:pointer;vertical-align:top;" id="'+(i+1)+'A" type="button" class="asientoLibre" value="" onclick="seleccionarAsiento(\''+(i+1)+'A\',\''+idPersona+'\',\''+tipoCarga+'\')"/></td>';
				htm+='<td><input style="font-size: xx-small;cursor:pointer;vertical-align:top;" id="'+(i+1)+'B" type="button" class="asientoLibre" value="" onclick="seleccionarAsiento(\''+(i+1)+'B\',\''+idPersona+'\',\''+tipoCarga+'\')"/></td>';
				htm+='<td><input style="font-size: xx-small;cursor:pointer;vertical-align:top;" id="'+(i+1)+'C" type="button" class="asientoLibre" value="" onclick="seleccionarAsiento(\''+(i+1)+'C\',\''+idPersona+'\',\''+tipoCarga+'\')"/></td>';
				htm+='<td style="vertical-align:top;">&nbsp;&nbsp;</td>';
				htm+='<td><input style="font-size: xx-small;cursor:pointer;vertical-align:top;" id="'+(i+1)+'D" type="button" class="asientoLibre" value="" onclick="seleccionarAsiento(\''+(i+1)+'D\',\''+idPersona+'\',\''+tipoCarga+'\')"/></td>';
				htm+='<td><input style="font-size: xx-small;cursor:pointer;vertical-align:top;" id="'+(i+1)+'E" type="button" class="asientoLibre" value="" onclick="seleccionarAsiento(\''+(i+1)+'E\',\''+idPersona+'\',\''+tipoCarga+'\')"/></td>';
				htm+='<td><input style="font-size: xx-small;cursor:pointer;vertical-align:top;" id="'+(i+1)+'F" type="button" class="asientoLibre" value="" onclick="seleccionarAsiento(\''+(i+1)+'F\',\''+idPersona+'\',\''+tipoCarga+'\')"/></td>';
				if(i==posAlas){
					htm+='<td rowspan="10" align="left" style="vertical-align:top;"><img src="images/ala_der.png"/></td>';
				}else if(i<posAlas || i>posAlas+9){
					htm+='<td style="vertical-align:top;">&nbsp;</td>';
				}
				htm+='</tr>';
			};
			$('#asientos-list-tbody').html(htm);
	
			//llenando asientos ocupados
			for (var j=0;j<listaAsientosOcupados.length;j++){	
				
				var asiento = $('#mostrar-asientos-form').find('#'+listaAsientosOcupados[j]);
				
				if(asiento!=null){
					$('#mostrar-asientos-form').find('#'+listaAsientosOcupados[j]).attr('class','asientoOcupado');
					$('#mostrar-asientos-form').find('#'+listaAsientosOcupados[j]).attr('disabled','disabled');
				}
				
			};
									
		}else{
			//si no hay asientos ocupados, llena asientos como libre
			for(var i=0;i<capacidadAvion/6;i++){
				htm+='<tr>';
				if(i==posAlas){
					htm+='<td rowspan="9" align="left"><img src="images/ala_izq.png"/></td>';
				}else if(i<posAlas || i>posAlas+8){
					htm+='<td>&nbsp;</td>';
				}
				htm+='<td>'+(i+1)+'</td>';
				htm+='<td><input style="font-size: xx-small;cursor:pointer;" id="'+(i+1)+'A" type="button" class="asientoLibre" value="" onclick="seleccionarAsiento(\''+(i+1)+'A\',\''+idPersona+'\',\''+tipoCarga+'\')"/></td>';
				htm+='<td><input style="font-size: xx-small;cursor:pointer;" id="'+(i+1)+'B" type="button" class="asientoLibre" value="" onclick="seleccionarAsiento(\''+(i+1)+'B\',\''+idPersona+'\',\''+tipoCarga+'\')"/></td>';
				htm+='<td><input style="font-size: xx-small;cursor:pointer;" id="'+(i+1)+'C" type="button" class="asientoLibre" value="" onclick="seleccionarAsiento(\''+(i+1)+'C\',\''+idPersona+'\',\''+tipoCarga+'\')"/></td>';
				htm+='<td>&nbsp;&nbsp;</td>';
				htm+='<td><input style="font-size: xx-small;cursor:pointer;" id="'+(i+1)+'D" type="button" class="asientoLibre" value="" onclick="seleccionarAsiento(\''+(i+1)+'D\',\''+idPersona+'\',\''+tipoCarga+'\')"/></td>';
				htm+='<td><input style="font-size: xx-small;cursor:pointer;" id="'+(i+1)+'E" type="button" class="asientoLibre" value="" onclick="seleccionarAsiento(\''+(i+1)+'E\',\''+idPersona+'\',\''+tipoCarga+'\')"/></td>';
				htm+='<td><input style="font-size: xx-small;cursor:pointer;" id="'+(i+1)+'F" type="button" class="asientoLibre" value="" onclick="seleccionarAsiento(\''+(i+1)+'F\',\''+idPersona+'\',\''+tipoCarga+'\')"/></td>';
				if(i==5){
					htm+='<td rowspan="9" align="left"><img src="images/ala_der.png"/></td>';
				}else if(i<posAlas || i>posAlas+8){
					htm+='<td>&nbsp;</td>';
				}
				htm+='</tr>';
			};
			
		};
		
		//si es un cambio de asiento mostrar el asiento actualmente elegido
		if(tipoCarga==1){
			var asientoElegido = $('#asientoEle-'+idPersona).html();
			$('#mostrar-asientos-form').find('#'+asientoElegido).attr('class','asientoElegido');
		}
		
		//mostrando lista de asientos
		$('#cont-asientos-chkinstp4').show();
		showMessage('','checkin-stp4','fail');
			
	};
	
	//validando tipo carga
	var datos = new Array();
	datos[datos.length]= new param('tipoCarga',tipoCarga);
	
	if(tipoCarga==1){
		datos[datos.length] = new param('asientoElegido',$('#asientoEle-'+idPersona).html());
	}
	
	showHideCargando(true);
	getData("buscarAsientosOcupadosCheckin",datos,successFn,null);	
	
};

var seleccionarAsiento = function(asiento,idPersona,tipoCarga){
	var successFn=function(resp){
		showHideCargando(false);
		var asientoElegido = '';
		if(resp){
			asientoElegido = resp.asientoElegido;	
		}
		
		$('#asientoEle-'+idPersona).html(asientoElegido);
		$('#selAsiento-'+idPersona).hide();
		$('#cambAsiento-'+idPersona).show();
		$('#cont-asientos-chkinstp4').hide();
		
	};
	
	var datos = new Array();
	datos[datos.length] = new param("asientoElegido",asiento);
	datos[datos.length] = new param("idPersona",idPersona);
	datos[datos.length] = new param("tipoCarga",tipoCarga);
	showHideCargando(true);
	getData("seleccionarAsientoCheckin",datos,successFn,null);	
	
	
};

var checkinStp4=function(datos){
	var successFn=function(resp){
		showHideCargando(false);
		var mensaje = resp.mensaje;
			if(mensaje!=undefined){
				showMessage(resp.mensaje,"fail");
			}else{
				setContenido(resp);
				cargarInfoPasajerosCheckInStp5();
			}
		};
	showHideCargando(true);
	getData("verificarCheckin",datos,successFn,null);	
	
};




var cargarInfoPasajerosCheckInStp5=function(){
	var successFn=function(resp){
		showHideCargando(false);
		var listaPasajeros='';
		var asientos='';
		var vuelo='';
		if(resp){
			listaPasajeros=resp.listaPasajerosSeleccionados;
			asientos = resp.asiento;
			vuelo = resp.vuelo;

		}
		//cargando informacion de pasajeros
		if(listaPasajeros!=null && asientos!=null && vuelo!=null){
			var htm='';
			for (var i=0;i<listaPasajeros.length;i++){	

				htm+='<tr>';
				htm+='<td>';
				htm+=(i+1);
				htm+='<input type="hidden" value="'+listaPasajeros[i].idPersona+'" name="idPersona" id="idPersona" />';
				htm+='</td>';
				htm+='<td>'+listaPasajeros[i].nombre+'</td>';
				htm+='<td>'+listaPasajeros[i].apellidoPaterno+' '+listaPasajeros[i].apellidoMaterno+'</td>';
				htm+='<td><span id="asientoEle-'+listaPasajeros[i].idPersona+'">'+asientos[i]+'</span></td>';
				htm+='<td>Chequeado</td>';
				//htm+='<td><input id="embarque-'+listaPasajeros[i].idPersona+'" type="button" value="Tarjeta de Embarque" onclick="cargarInfoTarjetaEmbarque(\''+listaPasajeros[i].idPersona+'\',\''+asientos[i]+'\')"/></td>';
				htm+='<td>';
				htm+='<form name="form'+listaPasajeros[i].idPersona+'" action="cargarInfoTarjetaEmbarqueCheckin" method="post">';
				htm+='<input type="hidden" name="idPersona" value="'+listaPasajeros[i].idPersona+'"/>';
				htm+='<input type="hidden" name="asientoElegido" value="'+asientos[i]+'"/>';
				htm+='<a href="#" onclick="javascript:document[\'form'+listaPasajeros[i].idPersona+'\'].submit()" id="embarque-'+listaPasajeros[i].idPersona+'">Tarjeta de Embarque</a>';
				//htm+='<input id="embarque-'+listaPasajeros[i].idPersona+'" type="submit" value="Tarjeta de Embarque"/>';
				htm+='</form>';
				htm+='</td>';				
				htm+='</tr>';
				
				
			};
			$('#pasajeros-list-tbody').html(htm);
			
			//cargar info de vuelo
			var htm1='';
			
			//htm1+='<span>';
			htm1+=formatearFecha(vuelo.fechaSalida)+' , '+vuelo.origen+'-'+vuelo.destino+' , '+vuelo.numVuelo;
			//htm1+='</span>';
			
			$('#infovuelo').html(htm1);
			
			showMessage('Su check-in se realizó exitosamente. Para finalizar, imprima dos copias de su(s) tarjeta(s) de embarque.','success');
			
		};
		
	};
	showHideCargando(true);
	getData("cargarInfoPasajerosStp5Checkin",null,successFn,null);
};
/*
var cargarInfoTarjetaEmbarque=function(idPersona,asiento){
	var successFn=function(resp){
		showHideCargando(false);				
	};
	
	var datos = new Array();
	datos[datos.length] = new param('idPersona',idPersona);
	datos[datos.length] = new param('asientoElegido',asiento);
	
	showHideCargando(true);
	getData("cargarInfoTarjetaEmbarqueCheckin",datos,successFn,null);
};
*/
var resetSession=function(){
	var successFn=function(resp){
		showHideCargando(false);
		mostrar('checkin');
		/*var mensaje = resp.mensaje;
		if(mensaje!=undefined){
			showMessage(resp.mensaje,"fail");
		}else{
			document.open();
			document.write(resp);
			document.close();	
		}*/
	};
		
	showHideCargando(true);
	getData("resetSessionCheckin",null,successFn,null);
};

var getData=function(url,filterData,successFn,errorFn){
	// making the ajax call
	$.ajax({
		url : url,
		type : "GET",
		data:filterData,
		success : function(resp) {
			//calling the user defined success function
			if(successFn)
			successFn(resp);	
		},
		error:function(e){
		//calling the user defined error function
		if(errorFn)
		 errorFn(e);
	}
	});
}


