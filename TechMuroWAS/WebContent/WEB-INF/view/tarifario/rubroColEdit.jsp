<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
	<head>
		<link rel="stylesheet" href="css/style-wi.css" type="text/css" media="screen" />
		<style type="text/css">
			.table-tarifario {
				font-family:"Arial Narrow", Verdana, sans-serif;
				max-width:716px;
				min-width:716px;
				border-spacing: 0;
			}
		</style>
		<script type="text/javascript" src="js/redips-table.js"></script>
		<script type="text/javascript" src="js/script.js"></script>
		<script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
		<script type="text/javascript" src="js/colResizable-1.3.min.js"></script>
		<script type="text/javascript" src="js/nicEdit-latest-2.js"></script>
		<script type="text/javascript">
		var myNicEditor;

	//$( "*" ).not(".testClass").click(function() {
	//	alert('hola');
	//	});
	
	
	$( "input[type = button]" ).click(function() {
		alert('hola');
	});
		
		
		function abreTabla(){
			myNicEditor = new nicEditor({buttonList : ['bold','italic','underline','strikeThrough','subscript','superscript','html', 'left', 'right', 'center', 'justify', 'ol', 'ul']});
			myNicEditor.setPanel('panel');
		}
		
		
		var isDivEditable = true;
		
		
		function makeEditable(){
			// variable declaration
			
		if(isDivEditable){
			var tr,			// number of rows in a table
				c,			// current cell
				cl,			// cell list
				cols,		// maximum number of columns that table contains
				i, j, t;	// loop variables
			// open loop for each table inside container
			tables = redips.get_table("mainTable");
			for (t = 0; t < tables.length; t++) {
				// define row number in current table
				tr = tables[t].rows;
				// define maximum number of columns (table row may contain merged table cells)
				cols = redips.max_cols(tables[t]);
				// define cell list
				cl = redips.cell_list(tables[t]);
				// open loop for each row
				for (i = 0; i < tr.length; i++) {
					// open loop for every TD element in current row
					for (j = 0; j < cols; j++) {
						// if cell exists then display cell index
						if (cl[i + '-' + j]) {
							// set reference to the current cell
							c = cl[i + '-' + j];
							// set innerHTML with cellIndex property
							if(c.innerHTML==''){
				
								c.innerHTML = "<div id='ta"+i+j+"' style='font-size: 16px; background-color: #ffffff;"+
											"padding: 3px; width: 100%; height: 90px;resize:none;overflow:auto;'/>";
								myNicEditor.addInstance('ta'+i+j);
								
								if($(c ).has( "div").length){
									isDivEditable = false;
								}
								
							}else{
				
								c.innerHTML = "<div id='ta"+i+j+"' style='font-size: 16px; background-color: #ffffff;"+
											"padding: 3px; width: 100%; height: 90px;resize:none;overflow:auto;'>"+
											""+c.innerHTML+
											"</div>";
								myNicEditor.addInstance('ta'+i+j);
								
								
								if($(c ).has( "div").length){
									isDivEditable = false;
								}
							
							}
							//alert(c.rowSpan + ' - ' + c.colSpan + ' - ' + i + j + ' ->'+$("#ta"+i+j).html());
						}else{
						}
					}
				}
			}
			
			document.getElementById("unir").disabled = true;
			document.getElementById("unir2").disabled = true;
			document.getElementById("unir3").disabled = true;
			document.getElementById("unir4").disabled = true;
			document.getElementById("unir5").disabled = true;
			document.getElementById("unir6").disabled = true;
			document.getElementById("unir7").disabled = true;
		
		}else{
			
		}}
		
		function makeNormal(){
			
			isDivEditable = true;
			// variable declaration
			var tr,			// number of rows in a table
				c,			// current cell
				cl,			// cell list
				cols,		// maximum number of columns that table contains
				i, j, t;	// loop variables
			// open loop for each table inside container
			tables = redips.get_table("mainTable");
			for (t = 0; t < tables.length; t++) {
				// define row number in current table
				tr = tables[t].rows;
				// define maximum number of columns (table row may contain merged table cells)
				cols = redips.max_cols(tables[t]);
				// define cell list
				cl = redips.cell_list(tables[t]);
				// open loop for each row
				for (i = 0; i < tr.length; i++) {
					// open loop for every TD element in current row
					for (j = 0; j < cols; j++) {
						// if cell exists then display cell index
						if (cl[i + '-' + j]) {
							// set reference to the current cell
							c = cl[i + '-' + j];
							// set innerHTML with cellIndex property
							if(c.innerHTML==''){
								//c.innerHTML = "<div id='ta"+i+j+"' style='font-size: 16px; background-color: #ffffff;"+
								//			"padding: 3px; width: 120px; height: 80px;resize:none;'/>";
							}else{
								c.innerHTML = document.getElementById("ta"+i+j).innerHTML;
								myNicEditor.removeInstance('ta'+i+j);
							}
							
							//alert(c.rowSpan + ' - ' + c.colSpan + ' - ' + i + j + ' ->'+cl[i + '-' + j]);
						}else{
							//alert(c.rowSpan + ' - ' + c.colSpan + ' - ' + i + j + ' ->'+cl[i + '-' + j]);
						}
					}
				}
			}
			document.getElementById("unir").disabled = false;
			document.getElementById("unir2").disabled = false;
			document.getElementById("unir3").disabled = false;
			document.getElementById("unir4").disabled = false;
			document.getElementById("unir5").disabled = false;
			document.getElementById("unir6").disabled = false;
			document.getElementById("unir7").disabled = false;
		}
		
		$(document).ready(function(){
		  $("#mainTable").colResizable({
			liveDrag:true, 
			draggingClass:"dragging",
			onResize:onSampleResized});
			abreTabla();
		});
		
		var onSampleResized = function(e){
			var columns = $(e.currentTarget).find("td");
			var msg = "columns widths: ";
			columns.each(function(){ msg += $(this).width() + "px; "; })
			$("#sample2Txt").html(msg);
			
		};
	
		
		function cambiar(){
			elimina();
			$("#mainTable").colResizable({
				liveDrag:true, 
				draggingClass:"dragging",
				onResize:onSampleResized
			});
		}
	
		function elimina(){
			$("#mainTable").colResizable({
				disable:true});
		}
		
		function enviar(){
			
			$('#mainTable tr td').each(function(){
				
				var id = $(this).attr("id");
				var tam = $(this).width();
			    var val = $(this).html();			   
			    var cspan = $(this).attr("colspan");
			    var rspan = $(this).attr("rowspan");
			    var column = $(this).parent().children().index(this);
				var row = $(this).parent().parent().children().index(this.parentNode);
				//alert(column + " - " + row);
				var form = document.createElement('form');
				form.setAttribute('action', '../tarifario/rubro/addColumnas.htm');
				form.setAttribute('method', 'POST');
			   
				var idRubro = document.createElement('input');
				idRubro.setAttribute('type', 'text');
				idRubro.setAttribute('name', 'idRubro');
				idRubro.setAttribute('value', $("#idRubro").val());
		   
				var idColumna = document.createElement('input');
				idColumna.setAttribute('type', 'text');
				idColumna.setAttribute('name', 'idColumna');
				idColumna.setAttribute('value', id);
				
				var posicionX = document.createElement('input');
				posicionX.setAttribute('type', 'text');
				posicionX.setAttribute('name', 'posicionX');
				posicionX.setAttribute('value', row);
				
				var posicionY = document.createElement('input');
				posicionY.setAttribute('type', 'text');
				posicionY.setAttribute('name', 'posicionY');
				posicionY.setAttribute('value', column);
				
				var width = document.createElement('input');
				width.setAttribute('type', 'text');
				width.setAttribute('name', 'width');
				width.setAttribute('value', tam);
				
				var titulo = document.createElement('input');
				titulo.setAttribute('type', 'text');
				titulo.setAttribute('name', 'titulo');
				titulo.setAttribute('value', val);
				
				var colspan = document.createElement('input');
				colspan.setAttribute('type', 'text');
				colspan.setAttribute('name', 'colspan');
				colspan.setAttribute('value', cspan);
				
				var rowspan = document.createElement('input');
				rowspan.setAttribute('type', 'text');
				rowspan.setAttribute('name', 'rowspan');
				rowspan.setAttribute('value', rspan);
				
				form.appendChild(idRubro);
				form.appendChild(idColumna);
				form.appendChild(posicionX);
				form.appendChild(posicionY);
				form.appendChild(width);
				form.appendChild(titulo);
				form.appendChild(colspan);
				form.appendChild(rowspan);
				
				$.post("tarifario/rubro/addColumnas.htm", $(form).serialize(), function(data){
					//alert(data);
				});
			});
		}
		
		</script>
	</head>
	<body>
		<input type="hidden" name="idRubro" id="idRubro" value="${idRubro}"/>
		<!-- container -->
		<div id="myContainer">
			<!-- toolbox -->
			<div class="container-edit">
			<div class="testClass" id="title-general">Editor de Columnas</div>
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
			<!-- main table -->
			<table id="mainTable" class="table-tarifario">
				<c:set var="rowAnt" value="0" scope="page"></c:set>
				<tr>
				
				<c:if test="${empty columnaList}">
					<td class="td-rubros td-rubros-index"></td>
					<td class="td-rubros"></td>
					<td class="td-rubros"></td>
					<td class="td-rubros"></td>
					<td class="td-rubros"></td>
				</c:if>
				
				<c:forEach items="${columnaList}" var="lista">
					<c:if test="${lista.posicionx != rowAnt }">
						</tr>
						<tr>
					</c:if>
					<td class="td-rubros" style="width:${lista.width}px" rowspan="${lista.rowspan}" colspan="${lista.colspan}">${lista.titulo }</td>
					<c:set var="rowAnt" value="${lista.posicionx}" scope="page"></c:set>
				</c:forEach>
				</tr>
			</table>
			</div>
			<input type="button" id="enviarForm" onclick="enviar();" style="display: none;"></input>
			<div id="sample2Txt"></div>
		</div>
	</body>
</html>