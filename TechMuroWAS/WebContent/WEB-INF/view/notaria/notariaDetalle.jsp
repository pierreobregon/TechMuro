<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">

<script type="text/javascript">

$(document).ready(function() {
	var gridDetalle;
	var listaDetalle = [];
	
		<c:forEach items="${notaria.notariaContratos}" var="lista">
		listaDetalle.push({descripcion:"${fn:replace(lista.contrato.descripcion, '\"', '\\\"')}",gastos:"${fn:replace(lista.contrato.gastos, '\"', '\\\"')}"} );
	    </c:forEach>
	
	gridDetalle = $("#listDetalle");

	gridDetalle.jqGrid({
		datatype : 'local',
		data : listaDetalle,
		colNames : [ "ID","Contrato", "Gasto" ],
		colModel : [ {name : 'idcontrato', index : 'name', sorttype : "int", align : "center", viewable : false, hidden : true}, 
		             {name : 'descripcion', index : 'name', width : 350, align : "left" }, 
		             {name : 'gastos', index : 'name', width : 230, align : "center" }
		],

		rowNum : 5,
		rowList : [ 5, 10, 20 ],
		pager : '#pager-detalle',
		gridview : true,
		rownumbers : false,
		sortname : 'id',
		viewrecords : false,
		sortorder : 'desc',
		width : 580,
		height : '100%'
	});
	
	
});

function cerrar(){
	
	$('#box').animate({'top':'5%'},500,function(){
		$('#overlay').fadeOut('fast');
        $("#listDetalle").html("");
        $("#notariaForm").innerHtml="";
    });
	
}
</script>

</head>
<body>
<div id="container">
	<div class="overlay" id="overlay" style="display: none;">
		<div class="box-2" id="box">
			
			<a class="boxclose" id="boxclose" onclick="cerrar();"></a>
			<div id="title-header-popup">Detalle Notar&iacute;a</div>
			<form method="post" class="scrollbar-2">
				<ul id="body-popup-2">
					<li>Nombre:</li>
					<li id="right-li"><c:out value="${notaria.nombre }" /></li>
					<li>Direcci&oacute;n:</li>
					<li id="right-li"><c:out value="${notaria.direccion }" /></li>
					<li>Tel&eacute;fonos:</li>
					<li id="right-li"><c:out value="${notaria.telefono1 }" />&nbsp; </li>
					<li>Web:</li>
					<li id="right-li"><c:out value="${notaria.paginaweb }" />&nbsp;</li>
					<li>Email:</li>
					<li id="right-li"><c:out value="${notaria.email }" />&nbsp;</li>
					<li>Zona Notar&iacute;a:</li>
					<li id="right-li"><c:out value="${notaria.plaza.nombre }" />&nbsp;</li>
				</ul>
			</form>
			
			<div id="container-list">
				<table id="listDetalle"></table>
			<div id="pager-detalle"></div>
			</div>
			<ul id="body-popup-2">
				<li>Fecha de Creaci&oacute;n:</li>
					<li id="right-li"><fmt:formatDate value="${notaria.fechacreacion}" type="date" pattern="dd/MM/yyyy"/></li>
				<li>Fecha de Actualizaci&oacute;n:</li>
					<li id="right-li"><fmt:formatDate value="${notaria.fechaactualizacion}" type="date" pattern="dd/MM/yyyy"/></li>
				</ul>
		</div>
	</div>
</div>	
	

</body>
</html>