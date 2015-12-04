<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>

<link rel="stylesheet" href="../css/pikaday.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link href="../css/start/jquery-ui-1.10.4.custom.css" rel="stylesheet" type="text/css">
<!-- <script type="text/javascript" src="../js/calender/jquery-ui-1.10.4.custom.js"></script> -->
<script type="text/javascript" src="../js/jquery.datepicker.es.js"></script>

<script>
$(function() {

	$( "#fechaini" ).datepicker({
		inline: true,
		showOtherMonths: true, 
		dateFormat: 'dd/mm/yy', 
		firstDay: 0, 
		buttonImage: '../images/icon-calendary.png',
		onClose: function( selectedDate ) {
	        $( "#fechafin" ).datepicker( "option", "minDate", selectedDate );
	        if($( "#fechafin" ).val()==''){
	        	$( "#fechafin" ).val(selectedDate);
	        	$( "#fechafin" ).datepicker( "option", "maxDate", "1Y");
	        }else{
	        	$( "#fechafin" ).datepicker( "option", "maxDate", dateToString(stringToDate(selectedDate,1)));
	        }
	        
	    }
	})
	.datepicker('widget').wrap('<div class="ll-skin-lugo"/>');

	$( "#fechafin" ).datepicker({
		inline: true,
		showOtherMonths: true,
		dateFormat: 'dd/mm/yy', 
		firstDay: 0,
		onClose: function( selectedDate ) {
			if($( "#fechaini" ).val()==''){
	        	$( "#fechaini" ).val(selectedDate);
	        }
	        $( "#fechaini" ).datepicker( "option", "minDate", "-1Y" );
	        $( "#fechaini" ).datepicker( "option", "maxDate", selectedDate);
	    }
	})
	.datepicker('widget').wrap('<div class="ll-skin-lugo"/>');
});

 function reporteTarifarioCompletoXLS(formato){
	 	$("body").nimbleLoader("show");
		var id = $("#tipoClienteId").val();	
		document.location.href="../reportes/reportes/tarifarioCompleto.htm?id="+id+"&formato="+formato;
		
		setTimeout(function hideGlobalLoader(){
		      $("body").nimbleLoader("hide");
		    }, 27000);
}


function openFormReporteProductoCapitulo(tipo){
		//alert(tipo);
		
     	$("#popUpReporteProductoCapitulo").load("../tarifario/reportes/productoCapituloForm.htm?tipo="+tipo, function(){
     		$('#overlay').fadeIn('fast',function(){
                $('#box').animate({'top':'20%'},500);
            });
     	});
     	return false;
	}
	
function enviarReporte(formato){
	
	if(validaFechas()){
	var fechaini = $("#fechaini").val().substring(6, 10)+$("#fechaini").val().substring(3,5)+$("#fechaini").val().substring(0, 2);
	var fechafin = $("#fechafin").val().substring(6, 10)+$("#fechafin").val().substring(3,5)+$("#fechafin").val().substring(0, 2);
	
		$("body").nimbleLoader("show");
		if(formato == 'xls'){
			document.location.href="../tarifario/reportes/logXLS.htm?fechaini="+fechaini+"&fechafin="+fechafin;
		}else{
			document.location.href="../tarifario/reportes/logPDF.htm?fechaini="+fechaini+"&fechafin="+fechafin;
		}
	setTimeout(function hideGlobalLoader(){
	      $("body").nimbleLoader("hide");
	    }, 3000);
	}
	
	
}
function validaFechas(){
	
	
	if($.trim($("#fechaini").val()).length!=10&&$.trim($("#fechafin").val()).length!=10){
		alert("Por favor ingrese fechas en el formato correcto");
		return false;
	}
	
	var fechaini = $("#fechaini").val();
	var fechafin = $("#fechafin").val();
	
	var fechai = fechaini.split("/");
	var dayi = fechai[0];
	var monthi = fechai[1];
	var yeari = fechai[2];
	var datei = new Date(yeari, monthi, dayi);

	var fechaf = fechafin.split("/");
	var dayf = fechaf[0];
	var monthf = fechaf[1];
	var yearf = fechaf[2];
	var datef = new Date(yearf, monthf, dayf);
	
	
	if (!isNaN(datei)&&!isNaN(datef)) {
		
		var dif = datef - datei;
		var dias = Math.floor(dif / (1000 * 60 * 60 * 24));
		if(dias > 365){
			alert("Por favor el rango de fechas no debe ser mayor a 1 año");
			return false;
		}else{
			return true;
		}
	} else {
		alert("Por favor ingrese fechas en el formato correcto");
		return false;
	}
}

function validarFormatoFecha(c) {
	var fecha = c.value;
	var fechaf = fecha.split("/");
	
	if (fechaf.length > 2) {
	var day = fechaf[0];
	var month = fechaf[1];
	var year = fechaf[2];
	var date = new Date(year, month, day);
	
	

	if (parseInt(day)<32&&parseInt(month)<13&&parseInt(year)>1900) {
	return true;
	} else {
	c.value = "";
	return false;
	}
}
	c.value = "";
	return false;

	}

function seleccionarTipoCliente(){
	var tipoClienteId = $("#tipoClienteId").val();
	if(tipoClienteId == 'AM'){
		$("#pdfRtc").hide();
	}else{
		$("#pdfRtc").show();
	}
}

function stringToDate(fecha, tam){
	var fechaf = fecha.split("/");
	if (fechaf.length > 2) {
	var day = fechaf[0];
	var month = fechaf[1];
	var year = fechaf[2];
	var date = new Date((parseInt(year)+parseInt(tam)), month, day);
	
	return date;
	}else{
		return false;
	}
}

function dateToString(myDate){
	
	return myDate.getDate()+ "/" + (myDate.getMonth()) + "/" + myDate.getFullYear();
}

</script>


</head>

<body>
    
    
                
    <!-- Body -->
    <section id="body-section">
    	<div id="block-grid">
            <div id="line-body-sup"></div>
            <div id="back-title">
                <h1>Reportes</h1>
            </div>
            <div id="container-grid">    
                <div class="table-grid">
                    <div class="tr-grid">
                        <div class="td-grid">
                            <p class="no-detail-searh">Seleccionar tipo de reporte a generar</p>
                        </div>
                    </div>
                </div>
                
              
                <style>
					.table-base-reportes {
						height:auto;
						display:block;
						margin-top:10px;
						}
						
					.table-base-reportes td {
						height:46px;
						background:url(../images/header-grid.png) repeat-x;
						font-family:"Stag Sans Book", Arial, sans-serif;
						font-size:15px;
						text-align:left;
						color:#fff;
						padding:0 15px 0 15px;
						border-right:1px solid #0056a3;
						}	
					
					.table-base-reportes td.border-table-reportes-left {	
						-webkit-border-radius:5px 0 0 0;
						-moz-border-radius:5px 0 0 0;
						border-radius:5px 0 0 0;
						}
						
					.table-base-reportes td.border-table-reportes-right {	
						-webkit-border-radius:0 5px 0 0;
						-moz-border-radius:0 5px 0 0;
						border-radius:0 5px 0 0;
						border-right:none;
						}	
						
					.table-base-reportes .container-reportes {
						background:#fff;
						font-family:"Stag Sans Light", Arial, sans-serif;
						color:#000;
						border:1px solid #e9e9e9;
						padding:0 20px 24px 20px;
						}	
						
					.container-table-reportes {
						list-style:none;
						margin:0;
						padding:0;
						overflow:hidden;
						}	
						
					.container-table-reportes li {
						line-height:50px;
						float:left;
						}	
						
					.container-table-reportes ul {
						list-style:none;
						margin:0;
						padding:0;
						border-top:1px solid #e9e9e9;
						}
						
					.container-table-reportes ul li {
						width:632px;
						background:#f9f9f9;
						border:1px solid #e9e9e9;
						border-top:none;
						}
						
					.container-table-reportes ul li div {
						min-width:62px;
						float:left;
						}
						
					.td-resportes-nombre {
						width:286px;
						padding:0 15px 0 15px !important;
						}	
						
					.td-resportes-desde {
						width:120px;
						padding:0 15px 0 15px !important;
						}
						
					.td-reportes-archive-excel {
						text-align:center !important;
						padding:0 10px 0 10px !important;
						}	
						
					.td-reportes-archive-pdf {
						padding:0 10px 0 10px !important;
						}	
						
					.container-table-reportes ul li .td-resportes-nombre-2,
					.td-resportes-nombre-2 {
						width:264px;
						}
						
					.container-table-reportes ul li .td-resportes-desde-2,
					.td-resportes-desde-2 {
						width:120px;
						height:52px;
						display:block;
						position:relative;
						}
						
					.container-table-reportes ul li div {
						padding:0 10px;
						}	
						
					.container-table-reportes ul li div > img {
						margin-left:22px;
						cursor:pointer;
						}	
					
					.container-table-reportes ul li div > img:hover {	
						-webkit-box-shadow:0px 0px 10px #4173bb;
						-moz-box-shadow:0px 0px 10px #4173bb;
						box-shadow:0px 0px 10px #4173bb;
						}
						
					.container-table-reportes input {
						width:105px;
						font-size:14px;
						color:#555;
						line-height:20px;
						border:1px solid #e9e9e9;
						margin-top:13px;
						padding-right:26px;
						}
						
					.icon-calendary {
						width:25px;
						height:25px;
						background:url(../images/icon-calendary.png) no-repeat;
						margin-top:12px;
						display:block;
						cursor:pointer;
						position:absolute;
						top:0;
						right:6px;
						}	
						
					.icon-calendary:hover {
						background-position:-24px;
						}
						
				</style>
            
                <!-- Grilla -->
                <table class="table-base-reportes">
                	<tr>
                    	<td class="td-resportes-nombre border-table-reportes-left">Nombre</td>
                        <td class="td-resportes-desde">Desde</td>
                        <td class="td-resportes-desde">Hasta</td>
                        <td class="td-reportes-archive-excel">Excel</td>
                        <td class="td-reportes-archive-pdf border-table-reportes-right">PDF</td>
                    </tr>
                    <tr>
                    	<td colspan="5" class="container-reportes">
                        	<ul class="container-table-reportes">
                            	<li>Reporte de Tarifario
                                	<ul>
                                    	<li>
                                        	<div class="td-resportes-nombre-2">- Tarifario Completo</div>
                                            <div class="td-resportes-desde-2">
                                            
                                            
                                            <select id="tipoClienteId" class="select-box-white" onchange="seleccionarTipoCliente()">
                                              <c:forEach items="${tipoCliente }" var="cliente">
              										<option value="${cliente.codigo }">${cliente.valor }</option>
   											  </c:forEach>
  											<option value="AM">Ambos</option>
											</select>
                                            
                                            
                                            </div>
                                            <div class="td-resportes-desde-2"></div>
                                            <div id="excelRtc"><img src="../images/EXCEL.png"   onClick="reporteTarifarioCompletoXLS('xls')"/></div>
                                            <div id="pdfRtc" ><img src="../images/PDF.png"  onClick="reporteTarifarioCompletoXLS('pdf')"/></div>
                                        </li>
                                        <li>
                                        	<div class="td-resportes-nombre-2">- Tarifario por Producto Cap&iacute;tulo</div>
                                            <div class="td-resportes-desde-2"></div>
                                            <div class="td-resportes-desde-2"></div>
                                            <div><img src="../images/EXCEL.png"   onClick="openFormReporteProductoCapitulo('xls')"/></div>
                                            <div><img src="../images/PDF.png"   onClick="openFormReporteProductoCapitulo('pdf')"/>  </div>
                                        </li>
                                    </ul>
                                </li>
                                <li>Reporte Control de cambios en el Muro
                                	<ul>
                                    	<li>
                                        	<div class="td-resportes-nombre-2">- Modificaciones Realizadas</div>
                                            <div class="td-resportes-desde-2">
                                           		<input type="text" id="fechaini" onchange="validarFormatoFecha(this);"  size="15" maxlength="10"  />
                                           	</div> 
											<div class="td-resportes-desde-2"> 
                                           		<input type="text" id="fechafin" size="15" maxlength="10"  />
                                            </div>    
                                           	<div><img src="../images/EXCEL.png" onclick="return enviarReporte('xls');" /></div>
                                           	<div><img src="../images/PDF.png" onclick="return enviarReporte('pdf');" /></div>
                                        </li>
                                    </ul>
                                </li>
                            </ul>
                        </td>
                    </tr>
                </table>
                
            <!-- Find Grilla -->
            </div>
                
        </div>
    </section>
    
    <div id="popUpReporteProductoCapitulo"></div>
    <iframe id="openFile" style="width: 0px;height: 0px;"></iframe>
</body>
</html>




