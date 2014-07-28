<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Reservas | Llamita Airlines</title>
<link rel="stylesheet" type="text/css" href="css/960.css" />
<link rel="stylesheet" type="text/css" href="css/reset.css" />
<link rel="stylesheet" type="text/css" href="css/text.css" />
<link rel="stylesheet" type="text/css" href="css/blue.css" />
<link type="text/css" href="css/ui.css" rel="stylesheet" />
<script type="text/javascript" src="js/jquery-1.6.js"></script>
<script src="js/util.js"></script>
<script src="js/ajax.util.js"></script>
<script>
	$(function(){
		$('#vuelos-ida').hide();
		$('#vuelos-vuelta').hide();
		$('#opciones').hide();
		initDateTimePicker("#fechaSalida");
		initDateTimePicker("#fechaRegreso");
		loginLink();
	});
	
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
</script>
<script type="text/javascript" src="js/jquery.blend.js"></script>
<script type="text/javascript" src="js/jquery.ui.core.js"></script>
<script type="text/javascript" src="js/jquery.ui.datepicker.js"></script>

<link rel="shortcut icon" href="favicon.ico" />
    <!--[if IE]>
    <script language="javascript" type="text/javascript" src="js/flot/excanvas.pack.js"></script>
    <![endif]-->
	<!--[if IE 6]>
	<link rel="stylesheet" type="text/css" href="css/iefix.css" />
	<script src="js/pngfix.js"></script>
    <script>
        DD_belatedPNG.fix('#menu ul li a span span');
    </script>        
    <![endif]-->
	
</head>

<body>

<!-- WRAPPER START -->
<div class="container_16" id="wrapper">	
<!-- HIDDEN COLOR CHANGER -->      
      <div style="position:relative;">
      </div>
  	<!--LOGO-->
	<div class="grid_8" id="logo">Llamita Airlines</div>
    <div class="grid_8">
<!-- USER TOOLS START -->
      <div id="user_tools"><span>Bienvenido <a href="#" id="usuario">${sessionScope.nombre}</a>  |  <a href="login.html" id="login">Iniciar Sesion</a></span></div>
    </div>
<!-- USER TOOLS END -->    
<div class="grid_16" id="header">
<!-- MENU START -->
<div id="menu" align="center" style="padding-left:55px;">
	<ul class="group" id="menu_group_main">
		<li class="item first" id="one"><a href="#" class="main"><span class="outer"><span class="inner dashboard">Inicio</span></span></a></li>
        <li class="item middle" id="two"><a href="forms.html" class="main current"><span class="outer"><span class="inner content">Reservas</span></span></a></li>
        <li class="item middle" id="three"><a href="#"><span class="outer"><span class="inner reports png">Reportes</span></span></a></li>
        <li class="item middle" id="four"><a href="#" class="main"><span class="outer"><span class="inner users">Mi Cuenta</span></span></a></li>
		<li class="item middle" id="five"><a href="#" class="main"><span class="outer"><span class="inner event_manager">Buscar Vuelos</span></span></a></li>        
		<li class="item middle" id="six"><a href="#" class="main"><span class="outer"><span class="inner settings">Administrar</span></span></a></li>        
		<li class="item last" id="seven"><a href="#" class="main"><span class="outer"><span class="inner newsletter">Promociones</span></span></a></li>        
    </ul>
</div>
<!-- MENU END -->
</div>
<div class="grid_16">
<!-- TABS START -->
    <div id="tabs">
         <div class="container">
           
        </div>
    </div>
<!-- TABS END -->    
</div>

<!-- CONTENT START -->
    <div class="grid_16" id="content">
    <!--  TITLE START  --> 
    <div class="grid_9">
    <h1 class="reservas">Reservas</h1>
    </div>
    <!--RIGHT TEXT/CALENDAR-->
    <div class="grid_6" id="eventbox"><a href="#canjearMillas" on click="mostrar('canjearMillas')"class="inline_calendar">Usted tiene millas acumuladas</a>
   	  <div class="hidden_calendar"></div>
    </div>
    
    <!--  TITLE END  -->    
    <!-- #PORTLETS START -->
    <div id="portlets">
    <!-- FIRST SORTABLE COLUMN START -->

      <!--THIS IS A PORTLET-->
      <div class="clear"></div>
		<div class="portlet">
		<div class="portlet-header fixed"><img src="images/comprar.png" width="16" height="16" alt="Comments" />Comprar Boleto</div>

		<div class="portlet-content nopadding">
		  <form id="comprar-boleto-form" name="comprar-boleto-form">
          
        <table  width="100%" cellpadding="0" cellspacing="0" id="box-table-a">
            <thead>
              <tr>
                <th width="17%" scope="col">Fecha de Reserva</th>
                    <th width="15%" scope="col">Salida</th>
                    <th width="14%" scope="col">Llegada</th>
                    <th width="13%" scope="col">Vuelo</th>
                    <th width="13%" scope="col">Cabina</th>
                    <th width="16%" scope="col">Total a Pagar</th>
                    <th width="12%" scope="col">Selección</th>
              </tr>
            </thead>
            <tbody  align="center"  id="canjear-reserva-list-tbody"></tbody>
          </table>
        </form>
        <div align="center" class="message" id="show-message"></div>
        <div class="clear"></div>
        <table width="100%">
        	<tr>
            	<td>
                	<a class="button" onclick="validarForm('comprar-boleto')"><span>Comprar</span></a>
                </td>
                <td valign="middle" align="right">
                	<select id="tipoPago">
                          <option value="none" selected="selected">[Seleccione]</option>
                          <option value="tarjetaCredito">Tarjeta de Crédito</option>
                          <option value="prescencial">Prescencial</option>
            		</select>
                </td>
            </tr>
        
        </table>
        	
          	</div>
		 
      </div>    
      <!--THIS IS A PORTLET-->
        



    <!--  END #PORTLETS -->  
   </div>
    <div class="clear"> </div>
<!-- END CONTENT-->    
  </div>
<div class="clear"></div>

</div>
<!-- WRAPPER END -->
<!-- FOOTER START -->
<div class="container_16" id="footer">
Todos los Derechos Reservados &copy; <a href="../index.htm">Llamita Airlines 2011</a></div>
<!-- FOOTER END -->
</body>
<em></em></html>
