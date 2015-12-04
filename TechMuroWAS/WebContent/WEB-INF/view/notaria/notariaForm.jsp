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
	'<c:if  test="${!empty notaria.notariaContratos}">';
		<c:forEach items="${notaria.notariaContratos}" var="lista">
			mydataForm.push({idcontrato:"${lista.contrato.idcontrato}", descripcion:"${fn:replace(lista.contrato.descripcion, '\"', '\\\"')}",gastos:"${fn:replace(lista.contrato.gastos, '\"', '\\\"')}"} );
	    </c:forEach>
	'</c:if>';
	
	$(document).ready(function() {
		window.prettyPrint() && prettyPrint();
		$('#plaza').multiselect({
		    includeSelectAllOption: true,
			enableCaseInsensitiveFiltering: true, 
			buttonClass: 'validate[required] multiselect dropdown-toggle btn btn-default'
		});
		
		$('.btn-group button').val($('#plaza').val());
		
		grid = $("#listDetalleNotaria");

	            grid.jqGrid({
	                datatype:'local',
	                data: mydataForm,
	               	colNames: ["ID", "Contrato", "Gasto", "Editar", "Eliminar"],
					colModel:[
					    {name : 'idcontrato', index : 'name', sorttype : "int", align : "center", viewable : false, hidden : true,sortable:false},
						{name:'descripcion',index:'descripcion', width:280, sorttype:"int", align:"left",sortable:false},
						{name:'gastos',index:'gastos', width:200, sorttype:"int", align:"center",sortable:false},
						{name:'editar',index:'total', width:72,align:"center", sorttype:"date",sortable:false},
						{name:'eliminar',index:'total', width:72,align:"center",sorttype:"float",sortable:false}
						/*{name:'accion',index:'note', width:90, sortable:false}*/
					],
					
	                rowNum:5,
	                rowList:[5,10,20],
	                pager: '#pager-form',
	                gridview:true,
	                rownumbers:false,
	                sortname: 'id',
	                viewrecords: false,
	                sortorder: 'desc',
					
					width: 624,
	                height: '100%',
					
					gridComplete: function(){
						var ids = $("#listDetalleNotaria").jqGrid('getDataIDs');
						for(var i=0;i < ids.length;i++){
							var rowId = ids[i];
							var rowData = jQuery('#listDetalleNotaria').jqGrid ('getRowData', rowId);
							
							be = "<div id=\"icon-edit\" onclick=\"editarNotariaContrato('"+rowId+"');\"></div>";
							se = "<div id=\"icon-eliminar\" onclick=\"eliminarNotariaContrato('"+rowId+"');\"></div>";

							jQuery("#listDetalleNotaria").jqGrid('setRowData',ids[i],{editar:be});
							jQuery("#listDetalleNotaria").jqGrid('setRowData',ids[i],{eliminar:se});
						}
					}
	     	});
		jQuery('#notaria').validationEngine({
			onValidationComplete: function(form, status){
				    if(status==true){
				    	notariaAgregar();
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
            $("#notariaForm").innerHtml = "";
        });
	}
	
	function notariaAgregar(){
			
		$("#contratoList").val(JSON.stringify(jQuery("#listDetalleNotaria").getGridParam("data")));
		
		if($("#contratoList").val().length>2){
		
		$("body").nimbleLoader("show");
		
			$.post("../notaria/agregar.htm", $("#notaria").serialize(), function(data){
			
			
				if($.trim(data)=="true"){
					alert("Registrado correctamente");
					$('#box').animate({'top':'5%'},500,function(){
			            $('#overlay').fadeOut('fast');
			            $("#notariaForm").innerHtml = "";
			        });
					buscar();
					return false;
				}else{
					alert("Error en la inserción");
					return false;
				}
			});
		}else{
			alert("Debe ingresar al menos un contrato");
		}
		
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
			<div class="box-2" id="box">
				<a class="boxclose" id="boxclose" onclick="cerrar();"></a>
				<c:choose>
				    <c:when test="${!empty notaria.idnotaria}">
				        <div id="title-header-popup">Editar Notar&iacute;a</div>
				    </c:when>
				    <c:otherwise>
				        <div id="title-header-popup">Agregar Notar&iacute;a</div>
				    </c:otherwise>
				</c:choose>
				
				<form:form method="post" action="../notaria/agregar.htm" commandName="notaria" 
					id="notaria" class="scrollbar-2">
					<form:hidden path="idnotaria"/>
					<ul id="body-popup-2">
						<li>Nombre:</li>
						<li id="right-li"><form:input path="nombre" id="nombre" type="text"
							class="validate[required] input-popup-2" maxlength="50"/></li>
						<li>Direcci&oacute;n:</li>
						<li id="right-li"><form:input path="direccion" id="direccion" type="text"
							class="validate[required] input-popup-2" maxlength="200"/></li>
						<li>Tel&eacute;fono:</li>
						<li id="right-li"><form:input path="telefono1" id="telefono1" type="text"
							class="input-popup-2" maxlength="30"/></li>
						<li>P&aacute;gina Web:</li>
						<li id="right-li"><form:input path="paginaweb" id="paginaweb" type="url"
							class="validate[custom[url]] input-popup-2" maxlength="200"/></li>
						<li>Email:</li>
						<li id="right-li"><form:input path="email" id="email" type="text"
							class="input-popup-2" maxlength="100"/></li>
						<li>Zona Notar&iacute;a:</li>
						<li id="right-li">
							<form:select path="plaza.idplaza" id="plaza" onchange="$('.btn-group button').val(this.value)">
								<form:option value="">-- Seleccionar Zona Notaría --</form:option>
								<c:forEach items="${plazaList}" var="plazaList">
									<form:option value="${plazaList.idplaza }">${plazaList.nombre}</form:option>
								</c:forEach>
							</form:select>
						</li>
						<ul id="block-grid-right">
							<li><a href="#" id="container-button-add" onclick="mostrarNotariaContrato();"><div
										id="button-add-left"></div>
									<div id="button-add-center">
										<span class="icon-plus-button">Agregar Contrato</span>
									</div>
									<div id="button-add-right"></div></a></li>
						</ul>
						
						<li><input type="hidden" class="validate[required]" name="contratoList" id="contratoList" value="" /></li>
						<div id="container-list">
							<table id="listDetalleNotaria"></table>
							<div id="pager-form"></div>
						</div>

						<li class="no-margin">Fecha de Creaci&oacute;n:</li>
						<li id="right-li" class="no-margin"><fmt:formatDate value="${notaria.fechacreacion}" type="date" pattern="dd/MM/yyyy"/></li>
						
						<c:if test="${!empty notaria.idnotaria}">
						<li class="no-margin">Fecha de Actualizaci&oacute;n:</li>
						<li id="right-li" class="no-margin"><fmt:formatDate value="${notaria.fechaactualizacion}" type="date" pattern="dd/MM/yyyy"/></li>
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
		<div id="contratoForm"></div>
	</div>
</body>
</html>