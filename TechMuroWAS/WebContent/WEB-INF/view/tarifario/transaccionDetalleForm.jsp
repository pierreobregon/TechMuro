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
		$("#box").animate({'top':'10%'},500,function(){
            $("#overlay").fadeOut('fast');
            $("#transaccionForm").innerHtml = "";
        });
	}
	
	function transaccionAgregar(){

		$.post("../tarifario/transaccion/agregar.htm", $("#transaccion").serialize(), function(data){
			
			var elem = $.trim(data).split('|');
			
		
			if(!isNaN(elem[0])){

				
				alert("Registrado correctamente");
				$("#box").animate({"top":"10%"},500,function(){
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
				<div id="title-header-popup">Ver Detalle</div>
				<iframe src="../cargaDetalleTransaccion.htm?id=${idTransaccion}" style="min-height:450px;" id="columnaRubro" width="860px" marginWidth="0" marginHeight="0" frameBorder="0">
				</iframe>
			</div>
		</div>
	</div>
</body>
</html>