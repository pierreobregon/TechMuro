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
						capituloAgregar();
				    }else{
				    	//alert("Se requiere llenar todos los campos obligatorios");
					}
				}
			}
		);
	});
	
	function cerrar() {
		$("#box").animate({'top':'25%'},500,function(){
            $("#overlay").fadeOut('fast');
            $("#capituloForm").innerHtml = "";
        });
	}
	
	function capituloAgregar(){
		
		var sinSubCapitulo = $("#nombre").val().toLowerCase().trim();
		if(sinSubCapitulo == "sin subcapítulo" || 
				sinSubCapitulo == "sin subcapitulo" ||
				sinSubCapitulo == "sin sub-capítulo" ||
				sinSubCapitulo == "sin sub-capitulo" || 
				sinSubCapitulo == "sin sub capitulo" ||
				sinSubCapitulo == "sin sub capitulo"){
			alert("El nombre del Sub Capítulo no puede ser 'Sin Sub Capítulo'");
			return;
		}
		
		
		$.post("../tarifario/subcapitulo/agregar.htm", $("#subCapitulo").serialize(), function(data){
			if($.trim(data)=="true"){
				alert("Registrado correctamente");
				$("#box").animate({"top":"25%"},500,function(){
					$("#overlay").fadeOut("fast"); 
					$("#capituloForm").innerHtml = "";
			    });
				
				
				$('#body-section').load("../tarifario/subcapitulo/list.htm", {id:$("#capitulo").val()}, function(data){
					buscar();
					'<c:if test="${empty subCapitulo.idsubcapitulo}">';
						var pageSize = $("#list").getGridParam("rowNum");
						var totalRecords = $("#list").getGridParam('records');
						var totalPages = Math.ceil(totalRecords/pageSize);
						jQuery("#list").setGridParam({page:Number(totalPages)}).trigger("reloadGrid");
					'</c:if>';
				});
				
				/* $.post("../tarifario/producto/buscar.htm", $("#buscaProducto").serialize(), function(data) {
					$("#list").jqGrid('clearGridData');
					mydata = eval(data), grid = $("#list");
					$("#list").setGridParam({data:mydata}).trigger("reloadGrid");
					
					'<c:if test="${empty capitulo.idcapitulo}">';
						var pageSize = $("#list").getGridParam("rowNum");
						var totalRecords = $("#list").getGridParam('records');
						var totalPages = Math.ceil(totalRecords/pageSize);
						jQuery("#list").setGridParam({page:Number(totalPages)}).trigger("reloadGrid");
					'</c:if>';
				}); */
				
				return false;
			}else if($.trim(data) == "subError01"){
				alert("No se puede agregar Sub Capítulos a este Capítulo");
				return false;
			}else if($.trim(data) == "subError02"){
				alert("Sub-Capítulo ya está relacionado con este Capítulo");
				return false;
			}else if($.trim(data) == "subError03"){
				alert("Error al Insertar Sub-Capítulo");
				return false;
			}else if($.trim(data) == "rubError04"){
				alert("Error al Editar Sub-Capítulo");
				return false;
			}else if($.trim(data) == "subError05"){
				alert("Sub-capítulo tiene rubros asociados");
				return false;
			}else if($.trim(data) == "subError06"){
				alert("Error al Eliminar eliminar SubCapítulo");
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
				    <c:when test="${!empty subCapitulo.idsubcapitulo}">
				        <div id="title-header-popup">Editar Sub-Cap&iacute;tulo</div>
				    </c:when>
				    <c:otherwise>
				        <div id="title-header-popup">Agregar Sub-Cap&iacute;tulo</div>
				    </c:otherwise>
				</c:choose>
				
				<form:form method="post" action="../tarifario/producto/agregar.htm" commandName="subCapitulo" 
					id="subCapitulo" cssClass="scrollbar-2">
					<form:hidden path="idsubcapitulo"/>
					<ul id="body-popup">
						<li>Nombre del Sub-Cap&iacute;tulo:</li>
						<li id="right-li"><form:input path="nombre" id="nombre" type="text"
							class="validate[required] input-popup-2" maxlength="100"/></li>
						<li>Tipo de Cliente:</li>
						<li id="right-li">
							<form:select path="capitulo.producto.tipocliente" id="tipocliente" 
							cssClass="validate[required] select-box" onchange="llenaProductos(this.value, 'producto', 'capitulo');">
								<form:option value="">--Seleccionar opci&oacute;n--</form:option>
								<c:forEach items="${tipoCliente }" var="cliente">
									<form:option value="${cliente.codigo }">${cliente.valor }</form:option>
								</c:forEach>
							</form:select>
						</li>
						<li>Producto:</li>
						<li id="right-li">
							<form:select path="capitulo.producto.idproducto" name="producto" id="producto" 
							 onchange="llenaCapitulos(this.value,'capitulo');" cssClass="validate[required] select-box">
								<form:option value="">--Seleccionar--opci&oacute;n -</form:option>
								<c:forEach items="${productoList }" var="productos">
									<form:option value="${productos.idproducto }">${productos.nombre }</form:option>
								</c:forEach>
							</form:select>
						</li>
						<li>Cap&iacute;tulo:</li>
						<li id="right-li">
							<form:select path="capitulo.idcapitulo" id="capitulo" cssClass="validate[required] select-box">
								<form:option value="">--Seleccionar opci&oacute;n--</form:option>
								<c:forEach items="${capituloList }" var="capitulos">
									<form:option value="${capitulos.idcapitulo }">${capitulos.nombre }</form:option>
								</c:forEach>
							</form:select>
						</li>
						<li class="no-margin">Fecha de Creaci&oacute;n:</li>
						<li id="right-li" class="no-margin"><fmt:formatDate value="${subCapitulo.fechacreacion}" type="date" pattern="dd/MM/yyyy"/></li>
						
						<c:if test="${!empty subCapitulo.idsubcapitulo}">
							<li class="no-margin">Fecha de Actualizaci&oacute;n:</li>
							<li id="right-li" class="no-margin"><fmt:formatDate value="${subCapitulo.fechaModificacion}" type="date" pattern="dd/MM/yyyy"/></li>
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