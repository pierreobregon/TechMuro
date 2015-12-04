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
		
		$('#capitulo').validationEngine({
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
				
		var sinCapitulo = $("#nombre").val().toLowerCase().trim();
		if(sinCapitulo == "sin capítulo" || sinCapitulo == "sin capitulo" ){
			alert("El nombre del Capítulo no puede ser 'Sin Capítulo'");
			return;
		}
			
			
		
		$.post("../tarifario/capitulo/agregar.htm", $("#capitulo").serialize(), function(data){

	 
			if($.trim(data)=="true"){
				alert("Registado correctamente");
				$("#box").animate({"top":"25%"},500,function(){
					$("#overlay").fadeOut("fast"); 
					$("#capituloForm").innerHtml = "";
			    });
				
				
				$('#body-section').load("../tarifario/capitulo/list.htm", {id:$("#producto").val()}, function(data){
					buscar();
					'<c:if test="${empty capitulo.idcapitulo}">';
						var pageSize = $("#list").getGridParam("rowNum");
						var totalRecords = $("#list").getGridParam('records');
						var totalPages = Math.ceil(totalRecords/pageSize);
						jQuery("#list").setGridParam({page:Number(totalPages)}).trigger("reloadGrid");
					'</c:if>';
				});
				
				
				return false;
			}else if($.trim(data) == "capError01"){
				alert("No se puede agregar mas Capítulos a este Producto");
				return false;
			}else if($.trim(data) == "capError02"){
				alert("Capítulo ya está relacionado con este Producto");
				return false;
			}else if($.trim(data) == "capError03"){
				alert("Error al Insertar Capítulo");
				return false;
			}else if($.trim(data) == "capError04"){
				alert("Error al Editar Capítulo");
				return false;
			}else if($.trim(data) == "capError05"){
				alert("Error al Eliminar Capítulo");
				return false;
			}else if($.trim(data) == "capError06"){
				alert("Capítulo tiene sub-capítulos asociados");
				return false;
			}
		});	
		return false;
	}
	
	
	function llenaProductosForm(tipoCliente, combo){
		$.post("../tarifario/producto/combo.htm", {tipoCliente:tipoCliente}, function(data) {

			var datos = eval(data);
			$("#"+combo).empty();
			var option = $(document.createElement('option'));
            option.text("--Seleccionar opción--");
            option.val("");
            $("#"+combo).append(option);
            $(datos).each(function () {
                var option = $(document.createElement('option'));

                option.text(this.nombre);
                option.val(this.idproducto);

                $("#"+combo).append(option);
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
				<c:choose>
				    <c:when test="${!empty capitulo.idcapitulo}">
				        <div id="title-header-popup">Editar Cap&iacute;tulo</div>
				    </c:when>
				    <c:otherwise>
				        <div id="title-header-popup">Agregar Cap&iacute;tulo</div>
				    </c:otherwise>
				</c:choose>
				
				<form:form method="post" action="../tarifario/producto/agregar.htm" commandName="capitulo" 
					id="capitulo" cssClass="scrollbar-2">
					<form:hidden path="idcapitulo"/>
					<ul id="body-popup">
						<li>Nombre del Cap&iacute;tulo:</li>
						<li id="right-li"><form:input path="nombre" id="nombre" type="text"
							class="validate[required] input-popup-2" maxlength="200"/></li>
						<li>Tipo de Cliente:</li>
						<li id="right-li">
							<form:select path="producto.tipocliente" id="tipocliente" 
							cssClass="validate[required] select-box" onchange="llenaProductosForm(this.value, 'producto');">
								<form:option value="">- Seleccionar opción -</form:option>
								<c:forEach items= 	"${tipoCliente }" var="cliente">							
									<form:option value="${cliente.codigo }">${cliente.valor }</form:option>
								</c:forEach>
							</form:select>
						</li>
						<li>Producto:</li>
						<li id="right-li">
							<form:select path="producto.idproducto" name="producto" id="producto" onchange="validaBoton();" cssClass="validate[required] select-box">
								<form:option value="">- Seleccionar opción -</form:option>
								<c:forEach items="${productoList }" var="productos">
									<form:option value="${productos.idproducto }">${productos.nombre }</form:option>
								</c:forEach>
							</form:select>
						</li>
						<li class="no-margin">Fecha de Creaci&oacute;n:</li>
						<li id="right-li" class="no-margin"><fmt:formatDate value="${capitulo.fechacreacion}" type="date" pattern="dd/MM/yyyy"/></li>
						
						<c:if test="${!empty capitulo.idcapitulo}">
							<li class="no-margin">Fecha de Actualización:</li>
							<li id="right-li" class="no-margin"><fmt:formatDate value="${capitulo.fechaModificacion}" type="date" pattern="dd/MM/yyyy"/></li>
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