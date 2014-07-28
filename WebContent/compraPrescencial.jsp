<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/estilos.css" rel="stylesheet" type="text/css" />
<script src="js/jquery-1.6.js"></script>
<script src="js/jquery.ui.core.js"></script>
<script src="js/jquery.ui.widget.js"></script>
<script src="js/jquery.ui.datepicker.js"></script>
<script src="js/util.js"></script>
<script src="js/ajax.util.js"></script>	
</head>

<body">
<table align="center" border="0" cellspacing="5">
  <tr>
    <td colspan="2" align="center"><img src="images/bcp.gif" width="328" height="135" /></td>
  </tr>
  <tr>
    <td colspan="2" align="center"><div class="message" id="prescencial-show-message"></div></td>
  </tr>
  <tr>
    <td width="50%"><strong>Código de Reserva</strong></td>
    <td width="50%"><div id="idReserva">${requestScope.reserva.idReserva }</div></td>
  </tr>
  <tr>
    <td><strong>Precio a Pagar</strong></td>
    <td><div id="totalPagar">${requestScope.reserva.totalPagar }</div></td>
  </tr>
  <tr>
    <td><strong>Número de Vuelo</strong></td>
    <td><div id="numeroVuelo">${requestScope.idVuelo }</div></td>
  </tr>
  <tr>
    <td><strong>Número de Cuenta</strong></td>
    <td>0215-15568-054985</td>
  </tr>
  <tr>
    <td><strong>Nombre de la Cuenta</strong></td>
    <td>Llamita Airlines SAC</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td align="right">
    	<input type="button" value="Imprimir" onClick="window.print()"></input>
    	<input type="button" value="Salir" onclick="javascript:if(confirm('Esta seguro que desea salir?'))self.close();"></button>
    </td>
  </tr>
</table>
</body>
</html>
