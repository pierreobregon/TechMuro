<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<link rel="stylesheet" href="../css/validationEngine.jquery.css"
	type="text/css">
<script type="text/javascript">
	var mydataForm = [];

	$(document).ready(function() {

		window.prettyPrint() && prettyPrint();
		$('#categoria').validationEngine({
			onValidationComplete : function(form, status) {
				if (status == true) {
					categoriaAgregar();
				} else {
					//alert("Se requiere llenar todos los campos obligatorios");
				}
			}
		});

	});

	function cerrar() {
		$("#box").animate({
			'top' : '20%'
		}, 500, function() {
			$("#overlay").fadeOut('fast');
			$("#capituloForm").innerHtml = "";
		});
	}

	function categoriaAgregar() {

		var sinCategoria = $("#nombre").val().toLowerCase().trim();
		if (sinCategoria == "sin categoria" || sinCategoria == "sin categoría") {
			alert("El nombre de la Categoría no puede ser 'Sin Categoría'");
			return;
		}
		
		$.post("../tarifario/categoria/agregar.htm", $("#categoria")
				.serialize(), function(data) {
			if ($.trim(data) == "true") {
				alert("Registrado correctamente");
				$("#box").animate({
					"top" : "20%"
				}, 500, function() {
					$("#overlay").fadeOut("fast");
					$("#capituloForm").innerHtml = "";
				});

				$('#body-section').load("../tarifario/categoria/list.htm", {
					id : $("#rubro").val()
				}, function(data) {
					buscar();
					'<c:if test="${empty categoria.idcategoria}">';
					var pageSize = $("#list").getGridParam("rowNum");
					var totalRecords = $("#list").getGridParam('records');
					var totalPages = Math.ceil(totalRecords / pageSize);
					jQuery("#list").setGridParam({
						page : Number(totalPages)
					}).trigger("reloadGrid");
					'</c:if>';
				});

			} else if($.trim(data) == "catError01"){
				alert("No se puede agregar más Categorías a este Rubro");				
				return false;
			} else if($.trim(data) == "catError02"){
				alert("Categoría ya está relacionado con este Rubro");				
				return false;
			} else if($.trim(data) == "catError03"){
				alert("Error al Insertar Categoría");				
				return false;
			} else if($.trim(data) == "catError04"){
				alert("Categoría tiene transacciones asociadas");				
				return false;
			}
		});
		return false;
	}
</script>
</head>
<body>
	<div id="container">
		<div class="overlay" id="overlay" style="display: none;">
			<div class="box" id="box">
				<a class="boxclose" id="boxclose" onclick="cerrar();"></a>
				<c:choose>
					<c:when test="${!empty categoria.idcategoria}">
						<div id="title-header-popup">Editar Categor&iacute;a y
							Denominaci&oacute;n</div>
					</c:when>
					<c:otherwise>
						<div id="title-header-popup">Agregar Categor&iacute;a y
							Denominaci&oacute;n</div>
					</c:otherwise>
				</c:choose>

				<form:form method="post" action="../tarifario/categoria/agregar.htm"
					commandName="categoria" id="categoria" cssClass="scrollbar-2">
					<form:hidden path="idcategoria" />
					<ul id="body-popup">
						<li>Categor&iacute;a:</li>
						<li id="right-li"><form:input path="nombre" id="nombre"
								type="text" class="validate[required] input-popup-2"
								maxlength="100" /></li>
						<li>Denominaci&oacute;n:</li>
						<li id="right-li"><form:input path="denominacion"
								id="denominacion" type="text" class="input-popup-2"
								maxlength="100" /></li>
						<li>Tipo de Cliente:</li>
						<li id="right-li"><form:select
								path="rubro.subcapitulo.capitulo.producto.tipocliente"
								id="tipocliente" cssClass="validate[required] select-box"
								onchange="llenaProductos(this.value, 'producto', 'capitulo', 'subcapitulo', 'rubro');">
								<form:option value="">--Seleccionar opción--</form:option>
								<c:forEach items="${tipoCliente }" var="cliente">
									<form:option value="${cliente.codigo }">${cliente.valor }</form:option>
								</c:forEach>
							</form:select></li>

						<li>Producto:</li>
						<li id="right-li"><form:select
								path="rubro.subcapitulo.capitulo.producto.idproducto"
								name="producto" id="producto"
								onchange="llenaCapitulos(this.value,'capitulo', 'subcapitulo', 'rubro');"
								cssClass="validate[required] select-box">
								<form:option value="">--Seleccionar--opción -</form:option>
								<c:forEach items="${productoList }" var="productos">
									<form:option value="${productos.idproducto }">${productos.nombre }</form:option>
								</c:forEach>
							</form:select></li>

						<li>Cap&iacute;tulo:</li>
						<li id="right-li"><form:select
								path="rubro.subcapitulo.capitulo.idcapitulo" id="capitulo"
								cssClass="validate[required] select-box"
								onchange="llenaSubCapitulos(this.value, 'subcapitulo', 'rubro');">
								<form:option value="">--Seleccionar opción--</form:option>
								<c:forEach items="${capituloList }" var="capitulos">
									<form:option value="${capitulos.idcapitulo }">${capitulos.nombre }</form:option>
								</c:forEach>
							</form:select></li>

						<li>Sub-Cap&iacute;tulo:</li>
						<li id="right-li"><form:select
								path="rubro.subcapitulo.idsubcapitulo" id="subcapitulo"
								cssClass="validate[required] select-box"
								onchange="llenaRubros(this.value, 'rubro');">
								<form:option value="">--Seleccionar opción--</form:option>
								<c:forEach items="${subCapituloList }" var="subCapitulos">
									<form:option value="${subCapitulos.idsubcapitulo }">${subCapitulos.nombre }</form:option>
								</c:forEach>
							</form:select></li>

						<li>Rubro:</li>
						<li id="right-li"><form:select path="rubro.idrubro"
								id="rubro" cssClass="validate[required] select-box">
								<form:option value="">--Seleccionar opción--</form:option>
								<c:forEach items="${rubroList }" var="rubros">
									<form:option value="${rubros.idrubro }">${rubros.nombre }</form:option>
								</c:forEach>
							</form:select></li>

						<li class="no-margin">Fecha de Creaci&oacute;n:</li>
						<li id="right-li" class="no-margin"><fmt:formatDate
								value="${categoria.fechacreacion}" type="date"
								pattern="dd/MM/yyyy" /></li>

						<c:if test="${!empty categoria.idcategoria}">
							<li class="no-margin">Fecha de Actualizaci&oacute;n:</li>
							<li id="right-li" class="no-margin"><fmt:formatDate
									value="${categoria.fechaModificacion}" type="date"
									pattern="dd/MM/yyyy" /></li>
						</c:if>

					</ul>
					<ul id="button-popup">
						<li><input type="submit" value="Guardar"
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