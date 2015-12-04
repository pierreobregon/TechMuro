<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<link rel="stylesheet" href="../css/flick/jquery-ui-1.10.4.custom.css">
<script src="../js/jquery-ui-1.10.4.custom.min.js"></script>
<script type="text/javascript">

var data = [];
'<c:forEach items="${contratoList}" var="lista">';
	data.push({id:"${lista.idcontrato}", label:"${fn:replace(lista.descripcion, '\"', '\\\"')}", contrato:"${fn:replace(lista.descripcion, '\"', '\\\"')}", gastos:"${fn:replace(lista.gastos, '\"', '\\\"')}"} );
'</c:forEach>';

$(document).ready(function() {
	$("#contrato").autocomplete({
		source:data,
		select: function( event, ui ) {
			$("#contrato").val( ui.item.contrato);
			$("#gastos").val( ui.item.gastos );
			$("#idcontrato").val( ui.item.id );
			
			return false;
		}
	}).data("ui-autocomplete")._renderItem = function( ul, item ) {
		return $("<li></li>")
		.data("item.autocomplete", item)
		.append("<a><strong>"+item.contrato+"</strong> / "+item.gastos+"</a>")
		.appendTo(ul);
	    };

	    jQuery('#formNotariacontrato').validationEngine({
			onValidationComplete: function(form, status){
				    if(status==true){
				    	agregarContrato();
				    }
				  }
			}
		);
});

function cerrarContrato() {
	$('#box-contrato-notaria').animate({'top':'30%'},500,function(){
        $('#overlay-contrato-notaria').fadeOut('fast');
        $("#contratoForm").innerHtml = "";
    });
}


function agregarContrato(){

	$("body").nimbleLoader("show");
	
	var dataTemp = jQuery("#listDetalleNotaria").getGridParam("data");
	
	for(var x=0;x<dataTemp.length;x++){
		var idx = dataTemp[x].idcontrato;
		if(idx==null){
			dataTemp.splice(x,1);
			x--;
		}
	}
	
	if($("#idContratoOld").val()!=""){
		var pageSize = jQuery("#listDetalleNotaria").getGridParam("rowNum");
		var totalRecords = jQuery("#listDetalleNotaria").getGridParam('records');
		var totalPages = Math.ceil(totalRecords/pageSize);
		var currentPage = jQuery("#listDetalleNotaria").getGridParam('page');
		var id = ((currentPage-1)*pageSize)+Number($("#idContratoOld").val())-1;
		dataTemp[id] = {idcontrato:$("#idcontrato").val(), descripcion: $("#contrato").val(), gastos: $("#gastos").val()};
		jQuery("#listDetalleNotaria").setGridParam({data:dataTemp, page:currentPage}).trigger("reloadGrid");
	}else{
		dataTemp.push({idcontrato:$("#idcontrato").val(), descripcion: $("#contrato").val(), gastos: $("#gastos").val()});
		var pageSize = jQuery("#listDetalleNotaria").getGridParam("rowNum");
		var totalRecords = jQuery("#listDetalleNotaria").getGridParam('records');
		totalRecords = Number(totalRecords)+1;
		var totalPages = Math.ceil(totalRecords/pageSize);
		jQuery("#listDetalleNotaria").setGridParam({data:dataTemp, page:totalPages}).trigger("reloadGrid");
	}
	
		$("body").nimbleLoader("hide");
		
	$("#idContratoOld").val("");
	cerrarContrato();
}
</script>
</head>
<body>
<div id="container">
		<div class="overlay" id="overlay-contrato-notaria" style="display:none;">
			<div class="box" id="box-contrato-notaria">
				<a class="boxclose" id="boxclose" onclick="cerrarContrato();"></a>
                <div class="title-header-popup" id="titulo-notariacontrato" style="background:url(../images/header-popup.png) repeat-x;width:auto;height:50px;line-height:50px;text-align:center;font-size:16px;color:#fff;margin-bottom:5px;display:block;border-radius:5px 5px 0 0;-webkit-border-radius:5px 5px 0 0;-moz-border-radius:5px 5px 0 0;-ms-border-radius:5px 5px 0 0;">Agregar Contrato</div>
                <form method="post" id="formNotariacontrato">
                	<input type="hidden" id="idContratoOld"/>
                        	<input type="hidden" id="idcontrato" name="idcontrato" value="0" />
                            <ul id="body-popup">
                                <li>Contrato:</li>
                                <li id="right-li"><input name="" value="" type="text" id="contrato" class="validate[required] input-popup" maxlength="200" /></li>
                                <li>Gasto:</li>
                                <li id="right-li"><input name="" value="" type="text" id="gastos" class="validate[required] input-popup" maxlength="150"/></li>
                            </ul>
                        
                            <ul id="button-popup">
                            	<li><input type="submit" value="Guardar" class="button-image-complete-gren"/></li>
                            	<li><input type="button" value="Cancelar" class="button-image-complete" onclick="cerrarContrato();"/></li>
                            </ul>
				</form>
        	</div>
		</div>
</div>
</body>
</html>