<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="js/jquery-1.6.js"></script>
<script>
	function redirect(){
		$('form#paypalform').submit();
	}

</script>
</head>
<body onload="redirect()">
<div style="font-family:Calibri;"align="center">Cargando... Por favor espere...<br/><img src="images/ajax-loader.gif" width="220" height="19"></div>
<form id="paypalform" action="https://www.paypal.com/cgi-bin/webscr" method="post">
<input type="hidden" name="business" value="agenciadevuelos@gmail.com">
<input type="hidden" name="tknPago" value="<%=session.getAttribute("tknPago")%>">
<input type="hidden" name="return" value="<%=request.getRequestURI()%>/comprarReserva">
<input type="hidden" name="cancel_ return" value="<%=request.getRequestURI()%>/falloCompraReserva">
<input type="hidden" name="cmd" value="_xclick">
<input type="hidden" name="item_name" value="Reserva de Boleto Aereo: <%=request.getAttribute("reserva.idReserva")%> Vuelo: <%=request.getAttribute("reserva.vuelo")%>">
<input type="hidden" name="amount" value="<%=request.getAttribute("reserva.totalPagar")%>">
</form>
</body>
</html>