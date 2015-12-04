<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
	<head>
		<link rel="stylesheet" href="css/style-wi.css" type="text/css" media="screen" />
		<style type="text/css">

		
			.table-tarifario {
				font-family: "Arial Narrow", Verdana, sans-serif;
				/*border: 1px solid #cdcdcd;*/
				max-width: 716px;
				min-width: 716px;
				border-collapse: collapse;
				border-spacing: 0;
			}
				
			.td-producto {
				height: 53px;
				background: #2280f2;
				font-size: 18px;
				color: #fff;
				font-weight: bold;
				text-align: center;
			}
			
			.td-capitulo,.td-sub-capitulo {
				width: 100%;
				height: 33px;
				background: #073b7b;
				color: #fff;
				font-weight: bold;
				font-size: 12px;
				border-top: 1px solid #fff;
			}
			
			.td-rubros {
				height: 24px;
				background: #2280f2;
				color: #fff;
				text-align: center;
				font-weight: bold;
				font-size: 11px;
				/*border-top: 1px solid #333;
				border-bottom: 1px solid #333;*/
			}
			
			.td-rubros-index {
				width: 130px;
				background: #003399;
				text-align: left;
				font-weight: bold;
			}
			
			.td-rubros-2 {
				width: 99px;
				border-left: 1px solid #fff;
			}
			
			.td-rubros-3 {
				width: 130px;
				border-left: 1px solid #fff;
			}
			
			.td-rubros-4 {
				width: 130px;
				border-left: 1px solid #fff;
			}
			
			.td-rubros-5 {
				width: 223px;
				border-left: 1px solid #fff;
			}
			
			.td-categorias {
				width: 100%;
				height: 25px;
				background: #ccecff;
				font-weight: bold;
				font-size: 11px;
			}
			
			.td-categorias span {
				font-weight: normal;
			}
			
			.td-transacciones {
				width: 100%;
				height: auto;
				background: #fff;
				font-size: 11px;
				word-wrap:break-word;
				white-space:pre-wrap;
			}
			
			.table-transacciones {
				margin-bottom: 21px;
				border-bottom: 1px solid #333;
			}
			
			.td-transacciones td {
				height: auto;
				font-size: 11px;
				/*padding: 10px 0;*/
				padding: 0px;
				/*border-top: 1px dashed #333;
				border-left: 1px solid #333;
				border-right: 1px solid #333;
				border-bottom: 1px solid #333;*/
			}
			
			.td-transacciones td .div_edit{
				height:100px;
				max-height:100px;
				overflow-x:auto;
				width:100%;
				box-sizing:border-box;
				
			}
			
			.center-trans {
				text-align: center;
			}
			
			.td-notas {
				border-top: 1px solid #333;
			}
			
			.td-notas .header-notas {
				width: 100%;
				height: 22px;
				background: #003399;
				/* font-weight: bold; */
				color: #fff;
				text-align: center;
				font-size: 11px;
				padding: 0;
			}
			
			.td-notas td {
				font-size: 11px;
				font-weight: bold;
				padding: 5px 0;
			}
			
			.td-notas td span {
				font-weight: normal;
			}
			
			.button-image-complete-gren {
				width: 89px;
				height: 27px;
				font-family: "Stag Sans Book", Arial, sans-serif;
				font-size: 14px;
				background: url(images/button-form-green.png);
				color: #fff;
				text-decoration: none;
				text-align: center;
				line-height: 26px;
				*line-height: 25px;
				display: block;
				float: right;
				cursor: pointer;
				margin: 0;
				border: 0;
			}
			
			.button-image-complete {
				width: 89px;
				height: 27px;
				font-family: "Stag Sans Book", Arial, sans-serif;
				font-size: 15px;
				background: url(images/button-search.png);
				color: #fff;
				text-decoration: none;
				text-align: center;
				line-height: 26px;
				*line-height: 25px;
				display: block;
				float: left;
				cursor: pointer;
				margin: 0;
				border: 0;
			}
		</style>
		<script type="text/javascript" src="js/redips-table.js"></script>
		<script type="text/javascript" src="js/script.js"></script>
		<script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
		<script type="text/javascript" src="js/colResizable-1.3.min.js"></script>
		<script type="text/javascript" src="js/nicEdit-latest-2.js"></script>
		<script type="text/javascript" src="js/jquery.bpopup.min.js"></script>
		
		<script type="text/javascript">
		var myNicEditor;
		
		function abreTabla(){
			myNicEditor = new nicEditor({buttonList : ['bold','italic','underline','strikeThrough','subscript','superscript','html', 'left', 'right', 'center', 'justify', 'ol', 'ul']});
			myNicEditor.setPanel('panel');
		}
		
		function toggleEnableButtons(valor){
			document.getElementById("unir").disabled = valor;
			document.getElementById("unir2").disabled = valor;
			document.getElementById("unir3").disabled = valor;
			document.getElementById("unir4").disabled = valor;
			document.getElementById("unir5").disabled = valor;
			//document.getElementById("unir6").disabled = valor;
			//document.getElementById("unir7").disabled = valor;			
		}		
		
		var isDivEditable = true;
		
		function makeEditable(){
			
			var $tr = $('#mainTable tr');
			$.each($tr, function(itr, etr){
				var $td = $(etr).find('td');
				
				$.each($td, function(itd, etd){
					
					if(!$(etd).hasClass('td-rubros')){
						
						if($.trim($(etd).html()) == ''){
							$(etd).html("<div id='ta"+itr+''+itd+"' style='font-size: 12px; background-color: #ffffff;"+
										"padding: 1px; width: 100%; height: 90px;resize:none;overflow:auto;text-align:left;'></div>");
							myNicEditor.addInstance('ta'+itr+''+itd);
						///////
						//	$(etd).html('<div id="ta'+itr+''+itd+'" class="div_edit"></div>');
						//	myNicEditor.addInstance('ta'+itr+''+itd);							
						}else{
							var contenido = $(etd).html();
							$(etd).html("<div id='ta"+itr+''+itd+"' style='font-size: 12px; background-color: #ffffff;"+
										"padding: 3px; width: 100%; height: 90px;resize:none;overflow:auto;text-align:left;'>"+
										""+$.trim(contenido)+
										"</div>");
							myNicEditor.addInstance('ta'+itr+''+itd);						
						
						////////////
						//	var contenido = $(etd).html();
						//	$(etd).html('<div id="ta'+itr+''+itd+'" class="div_edit">'+contenido+'</div>');
						//	myNicEditor.addInstance('ta'+itr+''+itd);							
						}
					}
				});
			});
			
			toggleEnableButtons(true);
		}

		
		function makeNormal(){
			
			isDivEditable = true;
			
			var $tr = $('#mainTable tr');
			$.each($tr, function(itr, etr){
				var $td = $(etr).find('td');
				
				$.each($td, function(itd, etd){
					
					if(!$(etd).hasClass('td-rubros')){
						
						if($.trim($(etd).html()) == ''){
							
						}else{
							if($.trim($(etd).attr('id'))==''&&itd!=0){
								$(etd).html("<div style='text-align:left;'>"+$.trim(document.getElementById("ta"+itr+''+itd).innerHTML)+"</div>");
							}else{
								$(etd).html($.trim(""+document.getElementById("ta"+itr+''+itd).innerHTML+""));
							}
							myNicEditor.removeInstance('ta'+itr+itd);
									
							//////						
							//$(etd).removeAttr('style');
							//$('#ta'+itr+''+itd).hide();
							//$('#ta'+itr+''+itd).before($('#ta'+itr+''+itd).html());
							//myNicEditor.removeInstance('ta'+itr+''+itd);
							//$('#ta'+itr+''+itd).remove();
						}
					}
				});
			});			
			

			toggleEnableButtons(false);
		}
		
		$(document).ready(function(){
			abreTabla();
			//noSeleccionar();
		});
		
		function cambiar(){
			elimina();
		}
	
		function elimina(){
			
		}
		
		function noSeleccionar(a){
			var flag = false;
			if (flag === true) {
				// remember old color
				a.redips.background_old = a.style.backgroundColor;
				// set background color
				a.style.backgroundColor = REDIPS.table.color.cell;
			}
			// umark table cell
			else {
				// return original background color and reset selected flag
				a.style.backgroundColor = a.redips.background_old;
			}
			a.redips.selected = flag;

		}
		
		function enviar(){
			//makeNormal();
			$.post(
				"tarifario/transaccion/deleteByTransaccion.htm", 
				{
					idTransaccion:$("#idTransaccion").val()
				},
				function(data){}
			);
			
			var $tr = $('#mainTable tr');
			$.each($tr,function(itr,etr){
				var $td = $(etr).find('td');

				$.each($td,function(itd, etd){
					if(!$(etd).hasClass('td-rubros')){
							
						 if($(etd).attr("style")){

						 }else{
								var e = new Date().getTime() + (50);
								while (new Date().getTime() <= e) {}

								$.ajax({
									url:'tarifario/transaccion/addDetalle.htm',
									type:'POST',
									data:{
										idTransaccion:$("#idTransaccion").val(),
										idDetalle:$(etd).attr("id"),
										posicionX:itd,
										posicionY:itr,
										width:$(etd).width(),
										contenido:$.trim($(etd).html()),
										colspan:$(etd).attr("colspan"),
										rowspan:$(etd).attr("rowspan")
									},
									success:function(html){
										//console.log($(etd),etd,'registro');
									}
								});
						 }
						
					}
				});	
			});
			
			alert("Registrado correctamente");	
			parent.cerrar();
			
		}		
		</script>
		
	</head>
	<body>
		<input type="hidden" name="idTransaccion" id="idTransaccion" value="${transaccion.idtransaccion}"/>
		<!-- container -->
		<div id="myContainer">
			<!-- toolbox -->
			<div class="container-edit">
			<div id="title-general">Editor de Columnas</div>
            <div id="title-1">Vista</div>
            <div id="title-2">Edici&#243;n de columnas</div>
			<table id="toolbox" width="100%" border="0" cellpadding="0" cellspacing="0">
				<tbody>
					<tr>
						<td width="130" style="background:#fff; border:1px solid #dcdcdc; border-radius:5px 0 0 5px;">
							<a href="#" class="button-green" onclick="makeEditable();cambiar();" title="Muestra Datos"><span id="green"></span>Editar Texto</a>
                            
							<a href="#" class="button-fuchsia" onclick="makeNormal();cambiar();" title="Muestra Datos"><span id="fuchsia"></span>Visualizar</a>
						</td>
						<td width="130" style="background:#fff; border-top:1px solid #dcdcdc; border-bottom:1px solid #dcdcdc;">
							<a href="#" id="unir" class="button-orange" onclick="redips.merge();cambiar();" title="Merge marked table cells horizontally and verically"><span id="orange"></span>Combinar</a>
						</td>
						<td width="176" style="background:#fff; border-top:1px solid #dcdcdc; border-bottom:1px solid #dcdcdc; border-left:1px solid #dcdcdc;">
							<a href="#" id="unir2" class="button-blue-large" onclick="redips.split('h');cambiar();" title="Split marked table cell horizontally"><span id="separate-h"></span>Dividir columna</a>
							<a href="#" id="unir3" class="button-blue-large" onclick="redips.split('v');cambiar();" title="Split marked table cell vertically"><span id="separate-v"></span>Dividir fila</a>
						</td>
						<td width="137" style="background:#fff; border-top:1px solid #dcdcdc; border-bottom:1px solid #dcdcdc; border-right:1px solid #dcdcdc;">
							<a href="#" id="unir4" class="button" onclick="redips.row('insert');cambiar();" title="Add table row"><span id="plus-fila"></span>Fila</a>
							<a href="#" id="unir5" class="button" onclick="redips.row('delete');cambiar();" title="Delete table row"><span id="less-fila"></span>Fila</a>
						</td>
						
					</tr>
					<tr>
						<td colspan="5" style="background:#f7f7f7; padding-left:0;">
                        	<div id="container-panel">
								<div id="panel" style="width: 225px; height:26px; display:block; margin:0 auto;"></div>
                            </div>
						</td>
					</tr>
				</tbody>
			</table>
			
			<div class="container-tarifario-admin">
				<table id="mainTable" class="table-tarifario">
					<c:set var="rowAnt" value="0" scope="page"></c:set>
					<tr>
					
					<c:if test="${empty listaColumna}">
						<td class="td-rubros td-rubros-index"></td>
						<td class="td-rubros"></td>
						<td class="td-rubros"></td>
						<td class="td-rubros"></td>
						<td class="td-rubros"></td>
					</c:if>
					
					<c:forEach items="${listaColumna}" var="listaColumna" varStatus="i">
						<c:if test="${listaColumna.posicionx != rowAnt }">
							</tr>
							<tr>
						</c:if>
						<td onclick="noSeleccionar(this);" class="td-rubros<c:if test='${i.count==1 }'> td-rubros-index</c:if>"
						 style="width:${listaColumna.width}px !important;max-width:${listaColumna.width}px !important;" 
						 rowspan="${listaColumna.rowspan}" colspan="${listaColumna.colspan}" >${listaColumna.titulo}</td>
						<c:set var="rowAnt" value="${listaColumna.posicionx}" scope="page"></c:set>
					</c:forEach>
					</tr>
					<c:if test="${empty listaFilas}">
						<tr class="table-transacciones td-transacciones">
							<td>${transaccion.nombre}</td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
						</tr>
					</c:if>
					
					<c:forEach items="${listaFilas}" var="fila" varStatus="i">
						<c:set var="listaDetalle" value="${fila}"></c:set>
						<c:if test="${not empty listaDetalle}">
						<tr class="table-transacciones td-transacciones">
							<c:forEach items="${listaDetalle}" var="columna" varStatus="j">
							<td id="${columna.iddetalle}" rowspan="${columna.rowspan}" colspan="${columna.colspan}"><c:choose><c:when test="${i.count==1 && j.count==1 && not empty transaccion.idtransaccion }">${transaccion.nombre}</c:when> <c:otherwise>${columna.contenido}</c:otherwise></c:choose></td>
							</c:forEach>
						</tr>
						</c:if>
					</c:forEach>

				</table>
			</div>	
			</div>
			
			<div style="width:500px; height:25px; display:block; margin:15px 0 15px 10px; color:#0a589a; font-family:'Stag Sans Book', Arial, sans-serif; font-size:16px;">Se debe presionar el bot&oacute;n <span style="font-weight: bold;">[Visualizar]</span> antes de guardar el cambio.</div>
			
			<table style="text-align:center;width:100%; margin-top:20px;">
				<tr>
					<td style="padding-right:5px;"><input type="button" id="enviarForm" value="Guardar" onclick="enviar();" class="button-image-complete-gren" /></td>
					<td style="padding-left:5px;"><input type="button" value="Cancelar" class="button-image-complete" onclick="parent.cerrar();"></td>
				</tr>
			</table>
			
		</div>
	</body>
</html>