<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>::Cambiar Contraseñaa::</title>
<link href="css/estilos.css" rel="stylesheet" type="text/css" />
<script language="javascript" src='js/jquery-1.6.js'></script>
  <script language="javascript" src='js/ajax.util.js'></script>
  <script language="javascript" src='js/util.js'></script>
  <script>
  function onEnter(e) {
	    if (e.keyCode == 13) {
	        validarForm('password');
	    }
	}
  
  </script>

</head>
<body>
		

		<table width="347" align="center">
        <form id="password-form" name="password-form">
			<tr>
				<td colspan="2" align="center"><font size="6"
						color="#000000">Cambiar Password</font></td>
			<tr>
				<td width="140">Email:</td>
				<td width="195" align="right">
				<input type="text" readonly="readonly" name="usuario.email" value='<%=request.getAttribute("usuario.email") %>'/>
				<input type="hidden" readonly="readonly" name="tkn" value='<%=request.getAttribute("tkn") %>'/>
				</td>
			</tr>

			<tr>
				<td>Nueva ContraseÃ±a: </td>
				<td align="right"><input name="usuario.password" type="password" id="password" size="30" />
				</td>
			</tr>

			<tr>
				<td>Re-Ingrese: </td>
				<td align="right"><input name="repassword" type="password" id="repassword" size="30" onKeyPress="onEnter('event')"/></td>
				
			</tr>
			</form>
			<tr>

				<td colspan="2"><div align="center" class ="message" id="password-show-message" style="display:none;"></div></td>
				
			</tr>
			<tr>
			  <td></td>
			  <td align="right"><input type="button" id="button" value="Guardar Cambios" onclick="validarForm('password')" /></td>
			  
		  </tr>

  </table>

	

</body>
</html>