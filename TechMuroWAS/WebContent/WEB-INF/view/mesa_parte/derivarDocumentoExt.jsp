<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<link rel="stylesheet" href="../css/validationEngine.jquery.css" type="text/css">
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
	grid = $("#list");
	grid2 = $("#list2");
	var tudata = [
		            { idDestino:"1", idoudestino: "Secretaria General", idAccion: "Informe" },
		            { idDestino:"2", idoudestino: "Oficina de Planificación y Presupuesto", idAccion: "" },
		            { idDestino:"3", idoudestino: "Oficina de Administración", idAccion: "" }
		        ];
	
	var tudata2 = [
		            { idPersonal:"1", idoupersonal: "Juan Jose Orosco Palomino", idAccion: "Informe" },
		            { idPersonal:"2", idoupersonal: "Jose Perez", idAccion: "" },
		            { idPersonal:"3", idoupersonal: "Pedro Dominguez", idAccion: "" }
		        ];

	$(document).ready(
					function() {
						
						$('#boxclose').click(function(){
				            $('#box').animate({'top':'30%'},500,function(){
				                $('#overlay').fadeOut('fast');
				            });
				        });
				        
				        $('#cancel').click(function(){
				            $('#box').animate({'top':'30%'},500,function(){
				                $('#overlay').fadeOut('fast');
				            });
				        });
				        
						$("#datepicker").datepicker();
						$("#buscarAfi").attr('disabled', 'true');
						$("#button-image-complete-load").attr('disabled',
								'true');

						var hh = [];

						var listaTamanio = "${listaSize}";
						if (listaTamanio == 0) {
							//alert("No se tiene archivos cargados al sistema");
						}

// 						'<c:forEach items="${oficinas}" var="a">';
// 						hh.push({
// 							idplaza : '${a.plaza.idplaza}',
// 							idoficina : '${a.idoficina}',
// 							codigo : '${a.codigo}',
// 							nombre : '${a.nombre}',
// 							plaza : '${a.plaza.nombre}'
// 						});
// 						'</c:forEach>';
						llenarGrid(tudata);
						llenarGridDocumentoInterno(tudata2);
                    	var notAll = $('#oficinas > option');

						if(notAll.length == 0){
							$('#oficinas').multiselect({
	                        	includeSelectAllOption: false,
	                        	enableCaseInsensitiveFiltering: false
                    		});
						}else{
							$('#oficinas').multiselect({
		                        includeSelectAllOption: true,
					        	enableCaseInsensitiveFiltering: true
	                    	});
						}
						
						$el= $("#oficinas");
					    $('option', $el).each(function(element) {
						      $el.multiselect('select', $(this).val());
						});
						$('#subirArchivo')
								.ajaxForm(
										{
											beforeSend : function(data) {
											},
											uploadProgress : function(event,
													position, total,
													percentComplete) {

											},
											success : function(data) {
											},
											complete : function(xhr) {

												if ($.trim(xhr.responseText) != "") {
													alert($
															.trim(xhr.responseText));
													enviarPeticion("../oficinas.htm");

												} else {
													alert("No se realizó carga de archivo por inconsistencia de datos o por no cumplir con el formato");
												}

												$("body").nimbleLoader("hide");
											}
										});

						$("#criterioAfi")
								.keyup(
										function() {
											if ($.trim($("#criterioAfi").val()).length == 0) {
												$("#buscarAfi").attr(
														'disabled', 'true');
												$
														.post(
																"../buscarOficina.htm",
																$(
																		"#buscaAfiche")
																		.serialize(),
																function(data) {
																	$("#list")
																			.jqGrid(
																					'clearGridData');
																	jQuery(
																			"#list")
																			.setGridParam(
																					{
																						data : eval(data)
																					});
																	jQuery(
																			"#list")
																			.trigger(
																					"reloadGrid");
																});
											} else {
												$("#buscarAfi").removeAttr(
														'disabled');
											}
										});
					});

	$('input[type=file]').change(function() {
		var filePath = $(this).val()//;
		$("#textisselectedanyfile").val(filePath);
		$("#button-image-complete-load").removeAttr("disabled");

	});

	function myF(n, id) {

		$
				.get(
						"../validaEliminacionOficina/" + n + ".htm",
						function(data) {

							if ($.trim(data) == "false") {
								alert("Oficina tiene relación con Afiche(s), Comunicado(s) o Variables Generales. No se puede eliminar");
							} else {

								if (confirm("Está seguro que desea eliminar la oficina?")) {

									$
											.get(
													"../deleteOficina/" + n
															+ ".htm",
													function(data) {

														tudata = eval(data);

														var pageSize = jQuery(
																"#list")
																.getGridParam(
																		"rowNum");
														var totalRecords = jQuery(
																"#list")
																.getGridParam(
																		'records');
														var totalPages = Math
																.ceil(totalRecords
																		/ pageSize);
														var currentPage = jQuery(
																"#list")
																.getGridParam(
																		'page');

														if (Number(totalRecords) - 1 <= (currentPage - 1)
																* pageSize) {
															$("#list")
																	.jqGrid(
																			'clearGridData');
															jQuery("#list")
																	.setGridParam(
																			{
																				data : tudata,
																				page : Number(totalPages) - 1
																			})
																	.trigger(
																			"reloadGrid");
														} else {
															$("#list")
																	.jqGrid(
																			'clearGridData');

															jQuery("#list")
																	.setGridParam(
																			{
																				data : tudata,
																				page : currentPage
																			});
															jQuery("#list")
																	.trigger(
																			"reloadGrid");
														}

													});
								} else {
									return false;
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

	function llenarGrid(Json) {
		grid.jqGrid({
			datatype : 'local',
			data : Json,
			colNames : [ "idDestino","UO Destino", "Accióm"],
			colModel : [ {
				name : 'idDestino',
				index : 'name',
				sorttype : "int",
				align : "left",
				viewable : false,
				hidden : true,
				sortable : false
			},{
				name : 'idoudestino',
				index : 'name',
				width : 100,
				align : "left",
				sortable : false
			},{
				name : 'idAccion',
				index : 'name',
				width : 100,
				align : "center",
				sortable : false
			}],

			rowNum : 10,
			rowList : [ 5, 10, 20 ],

			pager : '#pager',
			//pginput : false,

			gridview : true,
			rownumbers : false,
			sortname : 'idDestino',
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
					colNames : [ "idPersonal", "Personal","Acción" ],
					colModel : [ {
						name : 'idPersonal',
						index : 'name',
						sorttype : "int",
						align : "left",
						viewable : false,
						hidden : true,
						sortable : false
					}, {
						name : 'idoupersonal',
						index : 'left',
						width : 100,
						align : "left",
						sortable : false
					}, {
						name : 'idAccion',
						index : 'name',
						width : 100,
						align : "left",
						sortable : false
					} ],

					rowNum : 10,
					rowList : [ 5, 10, 20 ],

					pager : '#pager2',
					//pginput : false,

					gridview : true,
					rownumbers : false,
					sortname : 'idPersonal',
					viewrecords : false,
					multiselect:true,
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
	<legend class="scheduler-border">Datos del Documento</legend>		
		<fieldset class="scheduler-border">
					<legend class="scheduler-border"></legend>
		<div id="container-search" style="padding: 0px 20px 0px 0px;">
			<form id="form1">
					<ul>
						<table>
							<tbody>
								<tr>
									<td></td>
									<td></td>
									<td >Fecha de Ingreso:</td>
									<td><input id="datepicker" name="datepicker"
										style="width: 80px;" type="text" /></td>
									<td>Creado Por:</td>
									<td><input id="creadPor" name="creadPor"
										style="width: 80px;" type="text" class="input-border"></td>
								</tr>
								<tr>
									<td >Enviado Por:</td>
									<td colspan="2"><input id="txtDocumento"
										name="txtDocumento" style="width: 200px;" type="text"
										class="validate[required] input-border"></td>
									<td>Entidad de Procedencia:</td>
									<td colspan="2"><input id="txtDocumento"
										name="txtDocumento" style="width: 200px;" type="text"
										class="validate[required] input-border"></td>
								</tr>
								
								<tr>
									<td>Dirigido A:</td>									
									<td colspan="5"><select id="expCombda" style="width: 150px;"
										name="expCombda" class="select-box">
											<option value="01">Por UO</option>
											<option value="02">Por Persona</option>
											
									</select></td>									
								</tr>							
								

							</tbody>
						</table>

					</ul>
				
			</form>
		</div>
</fieldset>
<fieldset class="scheduler-border">
		<legend class="scheduler-border"> UO´S</legend>
		<div id="container-grid">
			<div class="table-grid" style="margin: 0 0 0 0;">													
				<div class="tr-grid">
					<div class="td-grid" style="width: 240px;">
						<ul id="block-grid-right">
                               <li>
                                   <a onclick = "return openDetalleForm()"  id="container-button-add" style="margin: 1px;"><div id="button-add-left"></div><div id="button-add-center"><span class="icon-plus-button">Accion</span></div><div id="button-add-right"></div></a>
                               </li>
                                                              <li id="right-li" class="active">
                                    <select id="oficinas" class ="validate[required] oficinasJson" multiple="multiple" name="requiredOfi">
	                                   <c:if  test="${!empty oficinas}">
		                                   <c:forEach items="${oficinas}" var="ofi">
		                                        <option value="${ofi.idoficina}">${ofi.codigo} - ${ofi.nombre}</option>
											</c:forEach>
										</c:if>
                                    </select>
<!--                                     <label id="errorOfi"  class="error " style="display:none;">Escoger Oficina</label> -->
                                    <div id="errorOfi" class=" formError" style="opacity: 0.87; position: absolute; top: 108px; left: 450px; margin-top: -47px; display:none;"><div class="formErrorContent">* Campo requerido<br></div><div class="formErrorArrow"><div class="line10"></div><div class="line9"></div><div class="line8"></div><div class="line7"></div><div class="line6"></div><div class="line5"></div><div class="line4"></div><div class="line3"></div><div class="line2"></div><div class="line1"></div></div></div>
                              	</li>
                           </ul>
					</div>						
				</div>
			</div>
		</div>
		<table id="list"></table>
		<div id="pager"></div>
		<!-- Find Grilla -->
		<div id="container-grid">
			<div class="table-grid" style="margin: 0 0 0 0;">
				<div class="tr-grid">
					<div id="ResultadoBusqueda" class="td-grid" >
						<p style="color: #f7f7f7">Resultados de la b&uacute;squeda...</p>
					</div>
					<div class="td-grid" ></div>
				</div>													
			</div>
		</div>
	</fieldset>
	
	<fieldset class="scheduler-border">
		<legend class="scheduler-border">Personal de la UO´S </legend>
		<div id="container-grid">
			<div class="table-grid" style="margin: 0 0 0 0;">													
				<div class="tr-grid">
					<div class="td-grid" style="width: 300px;">
						<ul id="block-grid-right">
                               <li>
                                   <a onclick = "return openDetalleForm()"  id="container-button-add" style="margin: 1px;"><div id="button-add-left"></div><div id="button-add-center"><span class="icon-plus-button">Agregar</span></div><div id="button-add-right"></div></a>
                               	
                               </li>
                               
                           </ul>
                           
					</div>	
					<div class="td-grid" style="width: 300px;">
						<ul id="block-grid-right">
                               <li>
                                   <select id="expCombag" style="width: 200px;"
									name="expCombag" class="select-box">
										<option value="01">Juan Jose Osorco Paliomino</option>
										<option value="02">Jose Perez Madonado</option>
										
								</select>
                               </li>
                               
                           </ul>
                           
					</div>	
										
				</div>
			</div>
		</div>
		<table id="list2"></table>
		<div id="pager2"></div>

		<div id="container-grid">
			<div class="table-grid">
				<div class="tr-grid">
					<div id="ResultadoBusqueda" class="td-grid">
						<p style="color: #f7f7f7">Resultados de la b&uacute;squeda...</p>
					</div>
				</div>
			</div>

		</div>

	</fieldset>	
</fieldset>
<fieldset class="scheduler-border">
					<legend class="scheduler-border">Descripción</legend>
		<div id="container-search" style="padding: 0px 175px 0px 0px;">
			<form id="form1">
					<ul>
						<table>
							<tbody>								
								
								<tr>									
									<td><input id="idche" type="checkbox">Este documento se tiene que enviar a una Entidad Externa:</td>									
									
								</tr>
								<tr>
									
									<td>
										<table>
											<tr>
												
												<td>
													<input path="" id="buscarExpediente" type="button" value="..." style="margin: 1px;" class="button-image-complete-rexp">
												</td>
												<td><input id="txtRemitente" name="txtRemitente"
													style="width: 300px;" type="text" class="input-border">
												</td>
											</tr>
										</table>

									</td>
									
								</tr>
								<tr>
									
									<td ><textarea id="txtaDescripcion" rows="5" cols="10"></textarea></td>
									
								</tr>

							</tbody>
						</table>

					</ul>
				
			</form>
		</div>
</fieldset>
</div>

	<div id="up" class="up"></div>
	<div id="categoriaForm"></div>

</body>
</html>

