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
		$('#transaccion').validationEngine({
			onValidationComplete: function(form, status){
					if(status==true){
						transaccionAgregar();
				    }else{
				    	//alert("Se requiere llenar todos los campos obligatorios");
					}
				}
			}
		);
		
	});
	
	
	
	function cerrar() {
		$("#box").animate({'top':'20%'},500,function(){
            $("#overlay").fadeOut('fast');
            $("#transaccionForm").innerHtml = "";
        });
	}
	
	function transaccionAgregar(){
		
	
		$.post("../tarifario/transaccion/agregar.htm", $("#transaccion").serialize(), function(data){
			
			var elem = $.trim(data).split('|');
			
			
			if(!isNaN(elem[0])){
				alert("Registrado correctamente");
				$("#box").animate({"top":"20%"},500,function(){
					$("#overlay").fadeOut("fast");
					$("#transaccionForm").innerHtml = "";
			    });
				
				$('#body-section').load("../tarifario/transaccion/list.htm", {id:elem[1]}, function(data){
					buscar();
					'<c:if test="${empty transaccion.idtransaccion}">';
						var pageSize = $("#list").getGridParam("rowNum");
						var totalRecords = $("#list").getGridParam('records');
						var totalPages = Math.ceil(totalRecords/pageSize);
						jQuery("#list").setGridParam({page:Number(totalPages)}).trigger("reloadGrid");
					'</c:if>';
				});
				
			}else if($.trim(data) == "traError01"){
				alert("Transacción ya está relacionada con este Rubro o Categoría");
				return false;
			}else if($.trim(data) == "traError02"){
				alert("Error al Insertar Transacción");
				return false;
			}else if($.trim(data) == "traError03"){
				alert("Error al editar transacción");
				return false;
			}else if($.trim(data) == "traError04"){
				alert("Error al Eliminar Transacción");
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
				    <c:when test="${!empty transaccion.idtransaccion}">
				        <div id="title-header-popup">Editar Transacci&oacute;n</div>
				    </c:when>
				    <c:otherwise>
				        <div id="title-header-popup">Agregar Transacci&oacute;n</div>
				    </c:otherwise>
				</c:choose>
				
				<form:form method="post" action="../tarifario/transaccion/agregar.htm" commandName="transaccion" 
					id="transaccion" cssClass="scrollbar-2">
					<form:hidden path="idtransaccion"/>
					<ul id="body-popup">
						<li>Transacci&oacute;n:</li>
						<li id="right-li"><form:input path="nombre" id="nombre" type="text"
							class="validate[required] input-popup-2" maxlength="200"/></li>
						
						<li>Tipo de Cliente:</li>
						<li id="right-li">
							<form:select path="categoria.rubro.subcapitulo.capitulo.producto.tipocliente" id="tipocliente" 
							cssClass="validate[required] select-box" 
							onchange="llenaProductos(this.value, 'producto', 'capitulo', 'subcapitulo', 'rubro');">
								<form:option value="">--Seleccionar opción--</form:option>
								<c:forEach items="${tipoCliente }" var="cliente">
									<form:option value="${cliente.codigo }">${cliente.valor }</form:option>
								</c:forEach>
							</form:select>
						</li>
						
						<li>Producto:</li>
						<li id="right-li">
							<form:select path="categoria.rubro.subcapitulo.capitulo.producto.idproducto" name="producto" id="producto" 
							 onchange="llenaCapitulos(this.value,'capitulo', 'subcapitulo', 'rubro');" cssClass="validate[required] select-box">
								<form:option value="">--Seleccionar--opción -</form:option>
								<c:forEach items="${productoList }" var="productos">
									<form:option value="${productos.idproducto }">${productos.nombre }</form:option>
								</c:forEach>
							</form:select>
						</li>
						
						<li>Cap&iacute;tulo:</li>
						<li id="right-li">
							<form:select path="categoria.rubro.subcapitulo.capitulo.idcapitulo" id="capitulo" cssClass="validate[required] select-box"
								onchange="llenaSubCapitulos(this.value, 'subcapitulo', 'rubro');" >
								<form:option value="">--Seleccionar opción--</form:option>
								<c:forEach items="${capituloList }" var="capitulos">
									<form:option value="${capitulos.idcapitulo }">${capitulos.nombre }</form:option>
								</c:forEach>
							</form:select>
						</li>
						
						<li>Sub-Cap&iacute;tulo:</li>
						<li id="right-li">
							<form:select path="categoria.rubro.subcapitulo.idsubcapitulo" id="subcapitulo"
							 cssClass="validate[required] select-box" onchange="llenaRubros(this.value, 'rubro');">
								<form:option value="">--Seleccionar opción--</form:option>
								<c:forEach items="${subCapituloList }" var="subCapitulos">
									<form:option value="${subCapitulos.idsubcapitulo }">${subCapitulos.nombre }</form:option>
								</c:forEach>
							</form:select>
						</li>
						
						<li>Rubro:</li>
						<li id="right-li">
							<form:select path="categoria.rubro.idrubro" id="rubro" cssClass="validate[required] select-box" 
							 onchange="llenaCategorias(this.value, 'categoria');">
								<form:option value="">--Seleccionar opción--</form:option>
								<c:forEach items="${rubroList }" var="rubros">
									<form:option value="${rubros.idrubro }">${rubros.nombre }</form:option>
								</c:forEach>
							</form:select>
						</li>
						
						<li>Categor&iacute;a:</li>
						<li id="right-li">
							<form:select path="categoria.idcategoria" id="categoria" cssClass="validate[required] select-box">
							<form:option value="">--Seleccionar opción--</form:option>
							<c:forEach items="${categoriaList }" var="categorias">
								<form:option value="${categorias.idcategoria}">${categorias.nombre}</form:option>
							</c:forEach>
						</form:select>
						</li>
						
						
						<li class="no-margin">Fecha de Creaci&oacute;n:</li>
						<li id="right-li" class="no-margin"><fmt:formatDate value="${transaccion.fechacreacion}" type="date" pattern="dd/MM/yyyy"/></li>
						
						<c:if test="${!empty transaccion.idtransaccion}">
							<li class="no-margin">Fecha de Actualizaci&oacute;n:</li>
							<li id="right-li" class="no-margin"><fmt:formatDate value="${transaccion.fechaModificacion}" type="date" pattern="dd/MM/yyyy"/></li>
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