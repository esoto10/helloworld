<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="grid_9">
	<h1 id="titulo" class="reservas">Reserva</h1>
</div>
<div class="grid_6" id="eventbox">
	<a href="#" class="inline_calendar">Ud tiene X reservas pendientes! Sus millas acumuladas son XXX</a>
	<div class="hidden_calendar"></div>
</div>
<form id="registro-reserva-form">
<div id="portlets">
 	    <div class="clear"></div>
   		<div class="portlet" id="vuelo-ida"> 
            <div class="portlet-header fixed" id="ruta-ida"><img src="images/icons/airplane.png" width="16" height="16" alt="Comments" />Vuelo de Ida</div>
            <div class="portlet-content nopadding">
                <table align="center" width="100%" border="0" id="box-table-a">      
                    <thead>
                        <tr>
                            <th scope="col">Fecha Ida</th>
                            <th scope="col">Salida</th>
                            <th scope="col">Llegada</th>
                            <th scope="col">Vuelo</th>
                            <th scope="col">Información</th>
                        </tr>
                    </thead>              	
                    <tr align="center">
                        <td>${requestScope.vueloida.fechaSalida}</td>
                        <td>${requestScope.vueloida.origen}</td>
                        <td>${requestScope.vueloida.destino}</td>
                        <td>${requestScope.vueloida.numVuelo}</td>
                        <td>Máximo 2 piezas, Total 23 Kgs</td>
                    </tr>
                </table>
            </div>
        </div>
        <% if(!session.getAttribute("vueloVuelta").equals("")){  %>
        <div id="clear"></div>
        <div class="portlet" id="vuelo-vuelta">
        	<div class="portlet-header fixed" id="ruta-vuelta"><img src="images/icons/airplane.png" width="16" height="16" alt="Comments" />Vuelo de Vuelta</div>
			<div class="portlet-content nopadding"> 
            	<table  id="box-table-a" border="0" align="center" width="100%"> 
                	<thead>
                    	<tr>
                            <th scope="col">Fecha Vuelta</th>
                            <th scope="col">Salida</th>
                            <th scope="col">Llegada</th>
                            <th scope="col">Vuelo</th>
                            <th scope="col">Información</th>
                    	</tr>
                	</thead>              	
                	<tr align="center">
                        <td>${requestScope.vuelovuelta.fechaSalida}</td>
                        <td>${requestScope.vuelovuelta.origen}</td>
                        <td>${requestScope.vuelovuelta.destino}</td>
                        <td>${requestScope.vuelovuelta.numVuelo}</td>
                        <td>Máximo 2 piezas, Total 23 Kgs</td>
                	</tr>  
            	</table>
        	</div>
        </div>
        <% }   %>
	
    <div id="clear"></div>
    <div class="portlet">
    	<div class="portlet-header fixed"><img src="images/icons/user.gif" width="16" height="16" />Pasajeros</div>
		<div class="portlet-content nopadding">
        <table  id="box-table-a" align="center" width="100%">
            <thead>
              <tr>
                  <th scope="col">Cantidad de Pasajeros</th>
                  <th scope="col">Tipo de Pasajeros</th>
                  <th scope="col">Precio</th>
              </tr>
             </thead>       
              <tr align="center">
                  <td>${sessionScope.cantidadAdulto}</td>
                  <td>Pasajero Adulto</td>
                  <td>${sessionScope.precioAdulto}</td>
              </tr>
               <tr align="center">
                  <td>${sessionScope.cantidadNino}</td>
                  <td>Pasajero Niño</td>
                  <td>${sessionScope.precioNino}</td>
              </tr>
               <tr align="center">
                  <td>${sessionScope.cantidadInfante}</td>
                  <td>Pasajero Infante</td>
                  <td>0</td>
              </tr>
               <tr>
                  <td align="right" colspan="2"><b>Total a Pagar</b></td>
                  <td align="center">${sessionScope.precioTotal}</td>
              </tr>
        </table> 
        </div>
    </div>
    <div id="clear"></div>
    <div id="opciones">
            <a class="button_grey" onclick="window.print()"><span>Imprimir</span></a>
            <a class="button" onclick="validarForm('registro-reserva')"><span>Confirmar</span></a>
    </div>
</div>
</form>