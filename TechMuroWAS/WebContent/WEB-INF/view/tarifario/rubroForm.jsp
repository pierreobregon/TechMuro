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
<link rel="stylesheet" href="../css/style-wi.css" type="text/css" media="screen" />
<script type="text/javascript">
	var mydataForm = [];

	$(document).ready(function() {
		$("#mainTable").colResizable({
		liveDrag:true, 
		gripInnerHtml:"<div class='grip'></div>", 
		draggingClass:"dragging"});
		
		window.prettyPrint() && prettyPrint();
		$('#rubro').validationEngine({
			onValidationComplete: function(form, status){
					if(status==true){
						rubroAgregar();
				    }else{
				    	//alert("Se requiere llenar todos los campos obligatorios");
					}
				}
			}
		);
		
		$("#columnaRubro").attr("src", "../cargaColumnasRubro.htm?id=${rubro.idrubro}");
	});
	
	
	
	function cerrar() {
		$("#box").animate({'top':'5%'},500,function(){
            $("#overlay").fadeOut('fast');
            $("#capituloForm").innerHtml = "";
        });
	}
	
	function rubroAgregar(){
		
		$.post("../tarifario/rubro/agregar.htm", $("#rubro").serialize(), function(data){
			
			var elem = $.trim(data).split('|');
			
			
			if(!isNaN(elem[0])){
				
				$.post("../tarifario/rubro/deleteByRubro.htm", {idRubro:elem[0]});
				
				$("#columnaRubro").contents().find("#idRubro").val(elem[0]);
				$("#columnaRubro").contents().find("#enviarForm").click();
				
				alert("Registrado correctamente");
				$("#box").animate({"top":"5%"},500,function(){
					$("#overlay").fadeOut("fast");
					$("#capituloForm").innerHtml = "";
			    });
				
				
				$('#body-section').load("../tarifario/rubro/list.htm", {id:elem[1]}, function(data){
					buscar();
					'<c:if test="${empty rubro.idrubro}">';
						var pageSize = $("#list").getGridParam("rowNum");
						var totalRecords = $("#list").getGridParam('records');
						var totalPages = Math.ceil(totalRecords/pageSize);
						jQuery("#list").setGridParam({page:Number(totalPages)}).trigger("reloadGrid");
					'</c:if>';
				});
				
			}else if($.trim(data) == "rubError01"){
				alert("Rubro ya está relacionado con este Sub-Capítulo");
				return false;
			}else if($.trim(data) == "rubError02"){
				alert("Error al Insertar Rubro");
				return false;
			}else if($.trim(data) == "rubError03"){
				alert("Error al Editar Rubro");
				return false;
			}else if($.trim(data) == "rubError04"){
				alert("Rubro tiene categorías asociadas");
				return false;
			}else if($.trim(data) == "rubError05"){
				alert("Error al Eliminar Rubro");
				return false;
			}
		});	
		return false;
	}
	
	function cambiaRubro(valor){
		$("#columnaRubro").contents().find(".td-rubros-index").html("<div style='text-align:left;'><b>"+valor+"</b></div>");
	}
	
</script>
</head>
<body>
	<div id="container">
		<div class="overlay" id="overlay" style="display: none;">
			<div class="box-3" id="box">
				<a class="boxclose" id="boxclose" onclick="cerrar();"></a>
				<c:choose>
				    <c:when test="${!empty rubro.idrubro}">
				        <div id="title-header-popup">Editar Rubro</div>
				    </c:when>
				    <c:otherwise>
				        <div id="title-header-popup">Agregar Rubro</div>
				    </c:otherwise>
				</c:choose>
				
				<form:form method="post" action="../tarifario/rubro/agregar.htm" commandName="rubro" id="rubro" cssClass="scrollbar-2">
					<form:hidden path="idrubro"/>
					<ul id="body-popup-3">
						<li>Nombre de Rubro:</li>
						<li id="right-li"><form:input path="nombre" id="nombre" type="text"
							class="validate[required] input-popup-2" maxlength="50" onblur="cambiaRubro(this.value);"/></li>
						<li>Tipo de Cliente:</li>
						<li id="right-li">
							<form:select path="subcapitulo.capitulo.producto.tipocliente" id="tipocliente" 
							cssClass="validate[required] select-box" onchange="llenaProductos(this.value, 'producto', 'capitulo', 'subcapitulo');">
								<form:option value="">--Seleccionar opción--</form:option>
								<c:forEach items="${tipoCliente }" var="cliente">
									<form:option value="${cliente.codigo }">${cliente.valor }</form:option>
								</c:forEach>
							</form:select>
						</li>
						<li>Producto:</li>
						<li id="right-li">
							<form:select path="subcapitulo.capitulo.producto.idproducto" name="producto" id="producto" 
							 onchange="llenaCapitulos(this.value,'capitulo', 'subcapitulo');" cssClass="validate[required] select-box">
								<form:option value="">--Seleccionar--opción -</form:option>
								<c:forEach items="${productoList }" var="productos">
									<form:option value="${productos.idproducto }">${productos.nombre }</form:option>
								</c:forEach>
							</form:select>
						</li>
						<li>Cap&iacute;tulo:</li>
						<li id="right-li">
							<form:select path="subcapitulo.capitulo.idcapitulo" id="capitulo" cssClass="validate[required] select-box"
								onchange="llenaSubCapitulos(this.value, 'subcapitulo');" >
								<form:option value="">--Seleccionar opción--</form:option>
								<c:forEach items="${capituloList }" var="capitulos">
									<form:option value="${capitulos.idcapitulo }">${capitulos.nombre }</form:option>
								</c:forEach>
							</form:select>
						</li>
						<li>Sub-Cap&iacute;tulo:</li>
						<li id="right-li">
							<form:select path="subcapitulo.idsubcapitulo" id="subcapitulo" cssClass="validate[required] select-box">
								<form:option value="">--Seleccionar opción--</form:option>
								<c:forEach items="${subCapituloList }" var="subCapitulos">
									<form:option value="${subCapitulos.idsubcapitulo }">${subCapitulos.nombre }</form:option>
								</c:forEach>
							</form:select>
						</li>
						<li class="no-margin">
							<iframe style="overflow:inherit; height:292px;" id="columnaRubro" width="800px" marginWidth="0" marginHeight="0" frameBorder="0">
							</iframe>
						</li>
						<li style="position:relative;" class="no-margin">
							<div style="width:490px; height:25px; display:block; position:abosolte; top:0; margin-bottom:15px; color:#0a589a; font-family:'Stag Sans Book', Arial, sans-serif; font-size:16px;">Se debe presionar el bot&oacute;n <span style="font-weight: bold;">[Visualizar]</span> antes de guardar el cambio.</div>
						</li>
						
						
						<li class="no-margin">Fecha de Creaci&oacute;n:</li>
						<li id="right-li" class="no-margin"><fmt:formatDate value="${rubro.fechacreacion}" type="date" pattern="dd/MM/yyyy"/></li>
						
						<c:if test="${!empty rubro.idrubro}">
							<li class="no-margin">Fecha de Actualizaci&oacute;n:</li>
							<li id="right-li" class="no-margin"><fmt:formatDate value="${rubro.fechaModificacion}" type="date" pattern="dd/MM/yyyy"/></li>
						</c:if>

					</ul>
					<ul id="button-popup">
						<li><input type="submit" value="Guardar"
							class="button-image-complete-gren"></li>
						<li><input type="button" value="Cancelar" id="idCerrar"
							class="button-image-complete" onclick="cerrar();">
						<li>
					</ul>
				</form:form>
				
				
			</div>
			
		</div>
		
	</div>
	
	
</body>
</html>