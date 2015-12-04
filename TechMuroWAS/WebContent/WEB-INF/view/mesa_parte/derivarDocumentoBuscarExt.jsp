<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=8" />

<style type="text/css">

	.error, .red{
	   color: red;
	   display:block;
	   font-family: "source-code-pro", Consolas, monospace !important;
	}
	
	.errorOFi{
	   position: absolute;
		top: 300px;
		left: 300px;
		display: block;
		cursor: pointer;
		z-index: 990;
	}
	
	#imagePreview {
	    width: 42px;
	    height:60px;
	    background: #BEE7FB;
		filter: progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale); 
	}
	
	.blur-file {
		width: 154px;
		height: 27px;
		font-family: "Stag Sans Light", Arial, sans-serif;
		font-size: 14px;
		line-height: 27px;
		border-radius: 0 3px 3px 0;
		color: #555;
		background: #f7f7f7;
		border: 1px solid #e9e9e9;
		border-left: none;
		margin: 0;
		padding-left: 10px;
		position: absolute;
		top: 15px;
		right: -10px;
}



.legendStyle
{
    border-style:none;
    background-color: #003366;
    font-family: Tahoma, Arial, Helvetica;
    font-weight: bold;
    font-size: 9.5pt;
    Color: White;
    width:30%;
    padding-left:10px;

}



</style>

<script>
var grid;
var mydata = [];
$(document).ready(function() {		
	mydata = [], grid = $("#list");
	llenarTabla();	
	
});

function llenarTabla() {

	grid.jqGrid({
		datatype : 'local',
		data : mydata,
		colNames : [ "codDocumento","Expediente", "Tipo Doc", "Nro Doc", "Enviado Por"],
		colModel : [ {name : 'codDocumento',index : 'name',align : "center",viewable : false, hidden : true}, 
		             {name : 'nroExpediente',index : 'name',width : '15%',align : "left", sortable:true}, 
		             {name : 'tipoDocumento',index : 'name',width : '20%',align : "center", sortable:false},  
		             {name : 'nroDocumento',index : 'tax',width : '30%',align : "center", sortable:false}, 
		             {name : 'enviadoPor',index : 'tax',width : '35%',align : "center", sortable:false}
		],
		rowNum : 10,
		rowList : [ 5, 10, 20 ],
		pager : '#pager',
		gridview : true,
		rownumbers : false,
		viewrecords : false,
		width : 673,
		multiselect:true,
		height : '100%',
		gridComplete : function() {
			var ids = jQuery("#list").jqGrid('getDataIDs');
			for (var i = 0; i < ids.length; i++) {
				var cl = ids[i];
	
			    var rowData = $("#list").getRowData(cl);
			    
			    
				be = "<div id='icon-edit' onclick=\"openEditForm("+$("#"+cl+" td").first().text()+")\"></div>";
				se = "<div id='icon-eliminar' onclick=\"eliminaProducto("+$("#"+cl+" td").first().text()+")\"></div>";
				de = 	"<div id='icon-order-up' onclick=\"return up("+$("#"+cl+" td").first().text()+");\"/>"+
						"<div id='icon-order-down' onclick=\"return down("+$("#"+cl+" td").first().text()+");\"/>";
				ver= "<div id='icon-detail' onclick=\"return verCapitulos("+$("#"+cl+" td").first().text()+");\"/>";
				r = "<div id='icon-detail' onclick=\"return verRubros("+$("#"+cl+" td").first().text()+");\"/>";
				
				
				jQuery("#list").jqGrid('setRowData', ids[i], {editar : be});
				jQuery("#list").jqGrid('setRowData', ids[i], {eliminar : se});
				jQuery("#list").jqGrid('setRowData', ids[i], {orden : de});
				
				
				if(rowData.descripcion=='CAP,RU'){
					jQuery("#list").jqGrid('setRowData', ids[i], {cap : ver});
					jQuery("#list").jqGrid('setRowData', ids[i], {rubros : r});
				}else if(rowData.descripcion=='RU'){
					jQuery("#list").jqGrid('setRowData', ids[i], {rubros : r});
				}else{
					jQuery("#list").jqGrid('setRowData', ids[i], {cap : ver});
				}
				
			}
		}
	});
}

	function buscarOficina() {

		$.post("../buscarOficina.htm", $("#buscaAfiche").serialize(), function(
				data) {
			$("#ResultadoBusqueda p").attr("style", "color:#07508f;");
			$("#list").jqGrid('clearGridData');
			jQuery("#list").setGridParam({
				data : eval(data)
			});
			jQuery("#list").trigger("reloadGrid");
			if (data.length == 2) {
				alert("No existen resultados a mostrar");
			}
		});

		return false;
	}

	function cerrar() {
		$("#box").animate({'top':'25%'},500,function(){
            $("#overlay").fadeOut('fast');
            $("#capituloForm").innerHtml = "";
        });
	}
	
	function linkUp() {
		$("#up").load("../upAgregarOficina.htm", function() {
			$('#overlay').fadeIn('fast', function() {
				$('#box').animate({
					'top' : '30%'
				}, 500);
				$('#title-header-popup').text("Agregar Oficina");
			});
		});
		return false;
	}

	function myEdit(t, n) {
		$("#up").load("../upEditarOficina/" + n + ".htm", function() {
			$('#overlay').fadeIn('fast', function() {
				$('#box').animate({
					'top' : '30%'
				}, 500);
				$('#title-header-popup').text("Editar Oficina");
			});
		});
		return false;
	}
	
	function openDetalleForm(id){

//      	$("#Form1").load("../mesa_parte/openDerivarDocumentoForm.htm", {idDocumento:id}, function(){
//      		$('#overlay').fadeIn('fast',function(){
//                 $('#box').animate({'top':'10%'},500);
//             });
//      	});
     	
     	return enviarPeticion("../mesa_parte/openDerivarDocumentoForm.htm");
     	 
	}

	function llenarGrid(Json) {

		grid.jqGrid({
			datatype : 'local',
			data : Json,
			colNames : [ "idDocDeriv","Nro. Expediente", "Tipo Documento", "Nro. de Doc.",
					"Enviado por", "Entidad de Procedencia" ],
			colModel : [ {
				name : 'idDocDeriv',
				index : 'name',
				sorttype : "int",
				align : "center",
				viewable : false,
				hidden : true,
				sortable : false
			},{
				name : 'idNroExpediente',
				index : 'name',
				width : 100,
				align : "center",
				sortable : false
			},{
				name : 'idTipoDocumento',
				index : 'name',
				width : 100,
				align : "center",
				sortable : false
			}, {
				name : 'idNroDocumento',
				index : 'name',
				width : 100,
				align : "center",
				sortable : false
			}, {
				name : 'idEnviadoPor',
				index : 'name',
				width : 100,
				align : "center",
				sortable : false
			}, {
				name : 'idEntidadProcedencia',
				index : 'total',
				width : 72,
				align : "center",
				sorttype : "date",
				sortable : false
			} ],

			rowNum : 10,
			rowList : [ 5, 10, 20 ],

			pager : '#pager',
			//pginput : false,

			gridview : true,
			rownumbers : false,
			sortname : 'idDocDeriv',
			viewrecords : false,
			multiselect:true,
			sortorder : 'asc',

			width : 673,
			height : '100%',

			gridComplete : function() {
				var ids = jQuery("#list").jqGrid('getDataIDs');
				for (var i = 0; i < ids.length; i++) {
					var cl = ids[i];
					be = "<div id=\"icon-edit\" onclick=\"myEdit("
							+ $("#" + cl + " td").first().text() + "  ,"
							+ $("#" + cl + " td").first().next().text()
							+ ")\"></div>";
					se = "<div id=\"icon-eliminar\" onclick=\"myF("
							+ $("#" + cl + " td").first().next().text() + ","
							+ cl + ")\"></div>";

					jQuery("#list").jqGrid('setRowData', ids[i], {
						editar : be
					});
					jQuery("#list").jqGrid('setRowData', ids[i], {
						eliminar : se
					});

				}
			}

		});

	}
	
	function agregarExpediente(){
     	$("#categoriaForm").load("../upAgregarExpediente.htm", $("#buscaCategoria").serialize(), function(){
     		$('#overlay').fadeIn('fast',function(){
                $('#box').animate({'top':'20%'},500);
            });
     	});
     	return false;
	}	
	

	function llenarGridDocumentoInterno(Json) {

		grid2.jqGrid({
					datatype : 'local',
					data : Json,
					colNames : [ "idDocInterno", "Tipo de Documento",
							"Nro. de Documento", "Fec. de Registro", "Estado",
							"Asunto" ],
					colModel : [ {
						name : 'idInDocInterno',
						index : 'name',
						sorttype : "int",
						align : "center",
						viewable : false,
						hidden : true,
						sortable : false
					}, {
						name : 'idInTipoDocumento',
						index : 'name',
						width : 100,
						align : "center",
						sortable : false
					}, {
						name : 'idInNroDocumento',
						index : 'name',
						width : 100,
						align : "center",
						sortable : false
					}, {
						name : 'idInFecRegistro',
						index : 'name',
						width : 100,
						align : "center",
						sortable : false
					}, {
						name : 'idInEstado',
						index : 'total',
						width : 72,
						align : "center",
						sorttype : "date",
						sortable : false
					}, {
						name : 'idInAsunto',
						index : 'total',
						width : 150,
						align : "center",
						sorttype : "float",
						sortable : false
					}, ],

					rowNum : 10,
					rowList : [ 5, 10, 20 ],

					pager : '#pager2',
					//pginput : false,

					gridview : true,
					rownumbers : false,
					sortname : 'idInDocInterno',
					viewrecords : false,
					sortorder : 'asc',

					width : 673,
					height : '100%',

					gridComplete : function() {
						var ids = jQuery("#list2").jqGrid('getDataIDs');
						for (var i = 0; i < ids.length; i++) {
							var cl = ids[i];
							be = "<div id=\"icon-edit\" onclick=\"myEdit("
									+ $("#" + cl + " td").first().text()
									+ "  ,"
									+ $("#" + cl + " td").first().next().text()
									+ ")\"></div>";
							se = "<div id=\"icon-eliminar\" onclick=\"myF("
									+ $("#" + cl + " td").first().next().text()
									+ "," + cl + ")\"></div>";

							jQuery("#list2").jqGrid('setRowData', ids[i], {
								editar : be
							});
							jQuery("#list2").jqGrid('setRowData', ids[i], {
								eliminar : se
							});

						}
					}

				});

	}
	
	$(function() {
        

        $('#guardar').click(function(){
        
        	jQuery("#subirAfiche").validationEngine('validate');
        	var b = true;
        	var v = true;
							
			 if(b && v){
			 	//cosnsole.log("test idafiche ","${afiche.idafiche}");
		 		$('#subirAfiche').ajaxForm({url:"../addOficina.htm",type:"post", success:function(data){
		 						
		 					if(data =='false'){
		 						alert("Existe otra oficina con el mismo código");
		 					}else{
		 						alert("Registrado correctamente");
							$('#box').animate({'top':'100px'},500,function(){
	                            $('#overlay').fadeOut('fast');
	                            	tudata = eval(data);
	                            	
	                            	var pageSize = jQuery("#list").getGridParam("rowNum");
						            var totalRecords = jQuery("#list").getGridParam('records');
						            var totalPages = Math.ceil(totalRecords/pageSize);
						            var currentPage = jQuery("#list").getGridParam('page');
						            
	                            if($("#comunicadoHidden").val() == ""){
	                            
	                            jQuery("#list").setGridParam({data : tudata,page:totalPages}).trigger("reloadGrid");
	                            
	                            }else{
	                            	jQuery("#list").setGridParam({data : tudata,page:currentPage}).trigger("reloadGrid");
	                            }
                        	});
                	}
                	}
                
                });
				
			 }else{
			 	return false;
			 }
        });



    });
	
	
	
	function buscar() {
		$.post("../mesa_parte/listaDocumentoDerivar.htm", $("#buscarProducto").serialize(), function(data) {
			$("#list").jqGrid('clearGridData');
			mydata = eval(data), grid = $("#list");
			jQuery("#list").setGridParam({ data : mydata });
			jQuery("#list").trigger("reloadGrid");
			
			$("#resultadosProductos").html("Resultados de la búsqueda...");
			
			if(mydata.length<1){
				alert("No existen resultados a mostrar");
			}
			
		});
		return false;
	}
	
</script>
<script type="text/javascript">
	$(function() {
		//$('#datetimepicker1').datetimepicker();
	});
</script>

<!--[if lt IE 9]>
<script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->

</head>

<body>
	<div id="block-grid">					
		
		<fieldset class="scheduler-border">
					<legend class="scheduler-border">Derivación Interno de Documentos</legend>
		<div id="container-search" style="padding: 0px 175px 0px 0px;">
			<form:form action="/mesa_partes/derivarForm.htm" id="buscarProducto" onsubmit="return buscar();"
				method="post" modelAttribute="documento">			
					<ul>
						<table>
							<tbody>
								<tr>
									<td>Expediente: </td>
									<td>
										<table>
											<tr>
	                                            <td>
                                                  <form:input type="number" value="2015" path="expediente.numeracionDocumento.anioDocumento" 
                                                    style="width: 60px;" id="txtExpAnioBus"  min="2015" max="2040"/>
                                                </td>
                                                <td><form:input id="txtExpNumeBus" style="width: 40px;"  type="text" class="input-border" 
                                                    path="expediente.numeracionDocumento.numDocumento" />
                                               </td>
                                                <td><form:select id="cmbTipoExpBus" path="expediente.numeracionDocumento.indExpediente"  style="width: 40px;"  class="select-box">
                                                        <form:option value="">-</form:option>
                                                        <form:option value="E" label="E" />
                                                        <form:option value="I" label="I" />
                                                </form:select></td>											
												
											</tr>
										</table>
									</td>
									<td>Tipo Documento:</td>
									<td>
									<form:select path="tipoDocumento.codTipDocumento" id="tipoDocumentoList"  style="width:80px" cssClass="select-box">
										<form:option value="">- Seleccionar -</form:option>
										<c:forEach items="${tipoDocumentoLista}" var="tipoDocumento">
											<form:option value="${tipoDocumento.codTipDocumento }">${tipoDocumento.desTipDocumento }</form:option>
										</c:forEach>
									</form:select>										
										
									</td>
									<td>Numero de Documento:</td>
									<td>
										<table>
											<tr>
                                                <td><form:input id="txtExpNumeBus" style="width: 40px;"  type="text" class="input-border" 
                                                    path="numeracionDocumento.numDocumento" />
                                               </td>
	                                            <td>
                                                  <form:input type="number" value="2015" path="numeracionDocumento.anioDocumento" 
                                                    style="width: 60px;" id="txtExpAnioBus"  min="2015" max="2040"/>
                                                </td>                                               
												
												<td> 
																								
												</td>						
											</tr>
										</table>
									</td>
								</tr>	
								<tr>
									<td>
									    <input id="buscarDerivarDocumento" type="submit" value="Buscar" class="button-image-complete">
									</td>
									<td colspan="5"></td>
								</tr>							

							</tbody>
						</table>

					</ul>
				
			</form:form>
		</div>
</fieldset>
		<!-- Grilla -->
		<div id="container-grid">
			<div class="table-grid">
				<div class="tr-grid">
					<div class="td-grid">
						<p id="resultadosProductos"> </p>
					</div>
					<div class="td-grid">
						<ul id="block-grid-right">
							<li>
								<a href="#" id="container-button-add" onclick="return openDetalleForm();">
									<div id="button-add-left"></div>
									<div id="button-add-center">
										<span class="icon-plus-button" >Derivar</span>
									</div>
									<div id="button-add-right"></div>
								</a> 
							</li>
						</ul>
					</div>
				</div>
			</div>

			<!-- Grilla -->
			<table id="list"></table>
			<div id="pager"></div>
			<!-- Find Grilla -->
		</div>
		
	</div>
<div id="derivarForm"></div>
</body>
</html>

