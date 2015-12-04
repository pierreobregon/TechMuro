<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
				    	alert("Se requiere llenar todos los campos obligatorios");
					}
				}
			}
		);
	});
	
	function cerrar() {
		$("#box").animate({'top':'5%'},500,function(){
            $("#overlay").fadeOut('fast');
            $("#capituloForm").innerHtml = "";
        });
	}
	
	function capituloAgregar(){
		
		$.post("../tarifario/capitulo/agregar.htm", $("#capitulo").serialize(), function(data){
			if($.trim(data)=="true"){
				alert("insertado correctamente");
				$("#box").animate({"top":"5%"},500,function(){
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
			}else{
				alert($.trim(data));
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
			<div class="box-3" id="box">
				<a class="boxclose" id="boxclose" onclick="cerrar();"></a>
				<c:choose>
				    <c:when test="${!empty capitulo.idcapitulo}">
				        <div id="title-header-popup">Editar Rubro</div>
				    </c:when>
				    <c:otherwise>
				        <div id="title-header-popup">Agregar Rubro</div>
				    </c:otherwise>
				</c:choose>
				
				<form:form method="post" action="../tarifario/producto/agregar.htm" commandName="capitulo" 
					id="capitulo" cssClass="scrollbar-2">
					<form:hidden path="idcapitulo"/>
					<ul id="body-popup-2">
						<li>Nombre de Rubro:</li>
						<li id="right-li"><form:input path="nombre" id="nombre" type="text"
							class="validate[required] input-popup-2" maxlength="50"/></li>
						<li>Tipo de Cliente:</li>
						<li id="right-li">
							<form:select path="producto.tipocliente" id="tipocliente" 
							cssClass="validate[required] select-box" onchange="llenaProductos(this.value, 'producto');">
								<form:option value="">- Seleccionar opción -</form:option>
								<c:forEach items="${tipoCliente }" var="cliente">
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
						<li>Cap&iacute;tulo:</li>
						<li id="right-li">
							<form:select path="producto.idproducto" name="producto" id="producto" onchange="validaBoton();" cssClass="validate[required] select-box">
								<form:option value="">- Seleccionar opción -</form:option>
								<c:forEach items="${productoList }" var="productos">
									<form:option value="${productos.idproducto }">${productos.nombre }</form:option>
								</c:forEach>
							</form:select>
						</li>
						<li>Sub-Cap&iacute;tulo:</li>
						<li id="right-li">
							<form:select path="producto.idproducto" name="producto" id="producto" onchange="validaBoton();" cssClass="validate[required] select-box">
								<form:option value="">- Seleccionar opción -</form:option>
								<c:forEach items="${productoList }" var="productos">
									<form:option value="${productos.idproducto }">${productos.nombre }</form:option>
								</c:forEach>
							</form:select>
						</li>
						<li>Fecha de Creaci&oacute;n:</li>
						<li id="right-li"><fmt:formatDate value="${capitulo.fechacreacion}" type="date" pattern="dd/MM/yyyy"/></li>
						
						<c:if test="${!empty capitulo.idcapitulo}">
							<li>Fecha de Actualizaci&oacute;n:</li>
							<li id="right-li"><fmt:formatDate value="${null}" type="date" pattern="dd/MM/yyyy"/></li>
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