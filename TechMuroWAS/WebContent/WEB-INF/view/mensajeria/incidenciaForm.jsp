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
		
		
		
		jQuery('#incidenciaMensajeria').validationEngine({
			onValidationComplete: function(form, status){
				    if(status==true){
				    	incidenciaMensajeAgregar();
				    }
				  }
			}
		);
	});
	
	
	function validar(){
		return jQuery('#notaria').validationEngine('validate');
	}
	
	function cerrar() {
		$('#box').animate({'top':'5%'},500,function(){
            $('#overlay').fadeOut('fast');
            $("#incidenciaForm").innerHtml = "";
        });
	}
	
	function incidenciaMensajeAgregar(){
		$("body").nimbleLoader("show");
		
		$.post("../mensajeria/agregar.htm", $("#incidenciaMensajeria").serialize(), function(data){
			if($.trim(data)=="true"){
				alert("Registrado correctamente");
				$('#box').animate({'top':'5%'},500,function(){
		            $('#overlay').fadeOut('fast');
		            $("#incidenciaForm").innerHtml = "";
		        });
				listarIncidencias();
				
				return false;
			}else{
				alert("Error en la inserción");
				return false;
			}
		});
		
		$("body").nimbleLoader("hide");		
		return false;
	}
	
	function mostrarNotariaContrato(){
		$("#contratoForm").load("../notaria/cargaContratoForm.htm", {id:"0"}, function(){
			
			$("#idContratoOld").val("");
			$("#idContrato").val("0");
			$("#contrato").val("");
			$("#gastos").val("");
			
			$("#overlay-contrato-notaria").fadeIn("fast",function(){
	            $("#box-contrato-notaria").animate({"top":"30%"},500);
	        }); 
			
     	});
	}
	
	
	function editarNotariaContrato(id){
		
		$("#contratoForm").load("../notaria/cargaContratoForm.htm", {id:id}, function(){
			$("#titulo-notariacontrato").html("Editar Contrato");
			
			var idc = $("#listDetalleNotaria").jqGrid('getCell',id, 'idcontrato');
			var desc = $("#listDetalleNotaria").jqGrid('getCell',id, 'descripcion');
			var gas = $("#listDetalleNotaria").jqGrid('getCell',id, 'gastos');
			
			$("#idContratoOld").val(id);
			$("#idContrato").val(idc);
			$("#contrato").val(desc);
			$("#gastos").val(gas);
			
			$('#overlay-contrato-notaria').fadeIn('fast',function(){
	            $('#box-contrato-notaria').animate({'top':'30%'},500);
	        });
     	});
     	return false;
	}
	
	
	function eliminarNotariaContrato(id){
		if(confirm("¿Está seguro que desea eliminar el contrato?")){
			var dataTemp = jQuery("#listDetalleNotaria").getGridParam("data");
			var pageSize = jQuery("#listDetalleNotaria").getGridParam("rowNum");
			var currentPage = jQuery("#listDetalleNotaria").getGridParam("page");
			var totalRecords = jQuery("#listDetalleNotaria").getGridParam('records');
			var totalPages = Math.ceil(totalRecords/pageSize);
			
			dataTemp.splice(((currentPage-1)*pageSize)+Number(id)-1,1);
			
			if(Number(totalRecords)-1<=(currentPage-1)*pageSize){
				jQuery("#listDetalleNotaria").setGridParam({data:dataTemp,page:Number(totalPages)-1}).trigger("reloadGrid");
			}else{
				jQuery("#listDetalleNotaria").setGridParam({data:dataTemp}).trigger("reloadGrid");
			}
		}
	}
	
	
</script>
</head>
<body>
	<div id="container">
		<div class="overlay" id="overlay" style="display: none;">
			<div class="box-2" id="box"  >
				<a class="boxclose" id="boxclose" onclick="cerrar();"></a>
				<c:choose>
				    <c:when test="${!empty incidencia.codIncMensajeria}">
				        <div id="title-header-popup">Editar Incidencia</div>
				    </c:when>
				    <c:otherwise>
				        <div id="title-header-popup">Agregar Incidencia</div>
				    </c:otherwise>
				</c:choose>
				<div class="scrollbar-2" >
					<form:form method="post" action="../mensajeria/agregar.htm" commandName="incidenciaMensajeria" 
						id="incidenciaMensajeria" >
						<form:hidden path="codIncMensajeria" />
						<ul id="body-popup-2">
							<li>Afiche:</li>
							<li id="right-li"><input type="text" value="test" /></li>
							<li>Fecha de Registro</li>
							<li id="right-li"><fmt:formatDate value="${incidenciaMensajeria.fecCreacion}" type="date" pattern="dd/MM/yyyy"/>
							</li>
							<li>Creado por:</li>
							<li id="right-li">${incidenciaMensajeria.codUsuCreacion}</li>
							<li>Numero Documento:</li>
							<li id="right-li">
								<form:hidden path="documento.codDocumento" id="codDocumento" />
								${incidenciaMensajeria.documento.desNumDocumento }
							</li>
							<li>Empresa Courier:</li>
							<li id="right-li">
								<!--<form:hidden path="courier.codCourier" id="codCourier" value="1" />
								<form:input path="courier.desCourier" id="desCourier" type="text"
									class="validate[required] input-popup-2" maxlength="100"/>
								-->
								<form:select path="courier.codCourier" id="codCourier" 
									cssClass="validate[required] select-box" >
									<form:option value="">- Seleccionar opción -</form:option>
									<c:forEach items= 	"${lstCourier }" var="courier">							
										<form:option value="${courier.codCourier }">${courier.desCourier }</form:option>
									</c:forEach>
								</form:select>
							</li>
							<li>Nombre Mensajero:</li>
							<li id="right-li"><form:input path="desNomMensajro" id="desNomMensajro" type="text"
								class="validate[required] input-popup-2" maxlength="100"/></li>
							<li>Estado de Entrega:</li>
							<li id="right-li">
								<form:select path="indEstMensajeria" id="indEstMensajeria" >
									<form:option value="1">Entregado</form:option>
									<form:option value="0">No Entregado</form:option>
								</form:select>
							</li>
							<li>Observaciones:</li>
							<li id="right-li"><form:textarea path="desObservacion" id="desObservacion" 
								class="input-popup-2" maxlength="100"/></li>
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
		
	</div>
</body>
</html>