<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Inicio | Llamita Airlines</title>
<link rel="stylesheet" type="text/css" href="css/960.css" />
<link rel="stylesheet" type="text/css" href="css/reset.css" />
<link rel="stylesheet" type="text/css" href="css/text.css" />
<link rel="stylesheet" type="text/css" href="css/blue.css" />
<link type="text/css" href="css/ui.css" rel="stylesheet" />
<script type="text/javascript" src="js/jquery-1.6.js"></script>
<script src="js/util.js"></script>
<script src="js/ajax.util.js"></script>
<script src="js/vistas.js"></script>
<script src="js/date.format-1.2.js"></script>
<script>
	$(function(){
		init();
	});
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
      <div id="user_tools"><span>Bienvenido <a href="#" onclick="mostrar('perfil')" id="usuario">${sessionScope.nombre}</a>  |  <a href="login.html" id="login">Iniciar Sesion</a></span></div>
      <input type="hidden" id="idUsuarioSesion" value='${sessionScope.id}' />
      <input type="hidden" id="permiso" value='${sessionScope.permiso}' />
      <input type='hidden' id='millasAcumuladas' value='${sessionScope.millas}' />
      <input type="hidden" id="emailUsuario" value="${sessionScope.email}" />
    </div>
<!-- USER TOOLS END -->    
<div class="grid_16" id="header">
<!-- MENU START -->
<div id="menu" align="center" style="padding-left:110px;">
	<ul class="group" id="menu_group_main">
		<li class="item first" ><a href="#inicio" id="incio" onclick="mostrar('inicio')"class="main current"><span class="outer"><span class="inner dashboard">Inicio</span></span></a></li>
        <li class="item middle"><a href="#reservas" id="reservas" onclick="mostrar('reservas')" class="main"><span class="outer"><span class="inner content">Reservas</span></span></a></li>
        <li class="item middle"><a href="#checkIn" id="checkin" onclick="mostrar('checkin')" class="main"><span class="outer"><span class="inner checkin">Check In</span></span></a></li>
        <li class="item middle"><a href="#" class="main"><span class="outer"><span class="inner event_manager">Buscar Vuelos</span></span></a></li>
        <li class="item middle" ><a href="#" class="main"><span class="outer"><span class="inner newsletter">Promociones</span></span></a></li>        
        <li class="item last" id="miCuentaMenu"><a href="#perfil" id="perfil" onclick="mostrar('perfil')"class="main"><span class="outer"><span class="inner users">Mi Cuenta</span></span></a></li>
    </ul>
</div>
<!-- MENU END -->
</div>
<div class="grid_16">
<!-- TABS START -->
    <div id="tabs">
         <div class="container">
           Hola  a todos
        </div>
    </div>
<!-- TABS END -->    
</div>

<!-- CONTENT START -->
<div class="grid_16" id="content">

    
<!-- END CONTENT-->    
</div>
<div class="clear"></div>

</div>
<!-- WRAPPER END -->
<!-- FOOTER START -->
<div class="container_16" id="footer">
Todos los Derechos Reservados &copy; <a href="index.jsp">Llamita Airlines 2011</a></div>
<!-- FOOTER END -->
</body>
<em></em></html>
