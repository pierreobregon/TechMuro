<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<link rel="stylesheet" href="../css/validationEngine.jquery.css" type="text/css">

<script type="text/javascript">
	var mydataForm = [];
	
		$(document).ready(function() {
		window.prettyPrint() && prettyPrint();
		
		$('#subCapitulo').validationEngine({
			onValidationComplete: function(form, status){
					if(status==true){
						reporteProductoCapitulo();
				    }else{
				    	//alert("Se requiere llenar todos los campos obligatorios");
					}
				}
			}
		);
	});
	
	
	function reporteProductoCapitulo(){
		
		


$("body").nimbleLoader("show");
		document.forms["subCapitulo"].submit();
		cerrar();
	setTimeout(function hideGlobalLoader(){
	      $("body").nimbleLoader("hide");
	    }, 3000);
}
	
	
		//return false;

	
	
	
	function cerrar() {
		$("#box").animate({'top':'25%'},500,function(){
            $("#overlay").fadeOut('fast');
            $("#capituloForm").innerHtml = "";
        });
	}
	
	
	function llenaProductos(tipoCliente, comboProducto, comboCapitulo, comboSubCapitulo, comboRubro){
		$.post("../tarifario/producto/combo.htm", {tipoCliente:tipoCliente}, function(data) {

			var datos = eval(data);
			$("#"+comboProducto).empty();
			$("#"+comboCapitulo).empty();
			$("#"+comboSubCapitulo).empty();
			$("#"+comboRubro).empty();
            
			var option = $(document.createElement('option'));
            option.text("--Seleccionar opción--");
            option.val("");
            $("#"+comboProducto).append(option);
            var option2 = $(document.createElement('option'));
            option2.text("--Seleccionar opción--");
            option2.val("");
            $("#"+comboCapitulo).append(option2);
            var option3 = $(document.createElement('option'));
            option3.text("--Seleccionar opción--");
            option3.val("");
            $("#"+comboSubCapitulo).append(option3);
            var option4 = $(document.createElement('option'));
            option4.text("--Seleccionar opción--");
            option4.val("");
            $("#"+comboRubro).append(option4);
            $(datos).each(function () {
                var option = $(document.createElement('option'));

                option.text(this.nombre);
                option.val(this.idproducto);

                $("#"+comboProducto).append(option);
            });
            
            validaBoton();
            
		});
	}
	
	function llenaCapitulos(id, comboCapitulo, comboSubCapitulo, comboRubro){
		$.post("../tarifario/capitulo/combo.htm", {id:id}, function(data) {

			var datos = eval(data);
			$("#"+comboCapitulo).empty();
			$("#"+comboSubCapitulo).empty();
			$("#"+comboRubro).empty();
			
			var option = $(document.createElement('option'));
            option.text("--Seleccionar opción--");
            option.val("");
            $("#"+comboCapitulo).append(option);
            var option2 = $(document.createElement('option'));
            option2.text("--Seleccionar opción--");
            option2.val("");
            $("#"+comboSubCapitulo).append(option2);
            var option3 = $(document.createElement('option'));
            option3.text("--Seleccionar opción--");
            option3.val("");
            $("#"+comboRubro).append(option3);
            $(datos).each(function () {
                var option = $(document.createElement('option'));

                option.text(this.nombre);
                option.val(this.idcapitulo);

                $("#"+comboCapitulo).append(option);
            });
            
            validaBoton();
            
		});
	}
	
	
</script>
</head>
<body>
	<div id="container">
		<div class="overlay" id="overlay" style="display: none;">
			<div class="box" id="box">
				<a class="boxclose" id="boxclose" onclick="cerrar();"></a>
			
				        <div id="title-header-popup">Seleccionar Producto Capitulo</div>
				  
				 <input type="hidden" id="idFormato" value="">
				
				<form:form method="post" action="../reporte/reportes/generarReporte.htm" commandName="subCapitulo" 
					id="subCapitulo" cssClass="scrollbar-2">
					<form:hidden path="idsubcapitulo"/>
					<form:hidden path="descripcion" />
					<ul id="body-popup">
						
						<li>Tipo de Cliente:</li>
						<li id="right-li">
							<form:select path="capitulo.producto.tipocliente" id="tipocliente" 
							cssClass="validate[required] select-box" onchange="llenaProductos(this.value, 'producto', 'capitulo');">
								<form:option value="">--Seleccionar opción--</form:option>
								<c:forEach items="${tipoCliente }" var="cliente">
									<form:option value="${cliente.codigo }">${cliente.valor }</form:option>
								</c:forEach>
							</form:select>
						</li>
						<li>Producto:</li>
						<li id="right-li">
							<form:select path="capitulo.producto.idproducto" name="producto" id="producto" 
							 onchange="llenaCapitulos(this.value,'capitulo');" cssClass="validate[required] select-box">
								<form:option value="">--Seleccionar opción--</form:option>
								<c:forEach items="${productoList }" var="productos">
									<form:option value="${productos.idproducto }">${productos.nombre }</form:option>
								</c:forEach>
							</form:select>
						</li>
						<li>Cap&iacute;tulo:</li>
						<li id="right-li">
							<form:select path="capitulo.idcapitulo" id="capitulo" cssClass="select-box">
								<form:option value="">--Seleccionar opción--</form:option>
								<c:forEach items="${capituloList }" var="capitulos">
									<form:option value="${capitulos.idcapitulo }">${capitulos.nombre }</form:option>
								</c:forEach>
							</form:select>
						</li>
						

					</ul>
					<ul id="button-popup">
						<li><input type="submit" value="Generar"
							class="button-image-complete-gren"></li>
						<li><input type="button" value="Cancelar"
							class="button-image-complete" onclick="cerrar();">
						<li>
					</ul>
				</form:form>
			</div>
		</div>
	</div>
</body>
</html>