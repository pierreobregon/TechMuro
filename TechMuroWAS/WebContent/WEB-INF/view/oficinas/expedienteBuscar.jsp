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
	grid = $("#list");
	grid2 = $("#list2");
	var tudata = [];

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

						'<c:forEach items="${oficinas}" var="a">';
						hh.push({
							idplaza : '${a.plaza.idplaza}',
							idoficina : '${a.idoficina}',
							codigo : '${a.codigo}',
							nombre : '${a.nombre}',
							plaza : '${a.plaza.nombre}'
						});
						'</c:forEach>';
						llenarGrid(hh);
						llenarGridDocumentoInterno(hh);

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
			colNames : [ "idDocExterno", "Editado Por", "Dirigido a",
					"Nro. de Documento", "Fec. de Registro", "Asunto" ],
			colModel : [ {
				name : 'idDocExterno',
				index : 'name',
				sorttype : "int",
				align : "center",
				viewable : false,
				hidden : true,
				sortable : false
			}, {
				name : 'idEditadoPor',
				index : 'name',
				width : 100,
				align : "center",
				sortable : false
			}, {
				name : 'idDirigidoa',
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
				name : 'idFecRegistro',
				index : 'total',
				width : 72,
				align : "center",
				sorttype : "date",
				sortable : false
			}, {
				name : 'idAsunto',
				index : 'total',
				width : 150,
				align : "center",
				sorttype : "float",
				sortable : false
			}, ],

			rowNum : 10,
			rowList : [ 5, 10, 20 ],

			pager : '#pager',
			//pginput : false,

			gridview : true,
			rownumbers : false,
			sortname : 'idDocExterno',
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
	
</script>
<script type="text/javascript">
	$(function() {
		$('#datetimepicker1').datetimepicker();
	});
</script>

<!--[if lt IE 9]>
<script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->

</head>

<body>
	<div id="block-grid">
		<div class="table-grid">
					<div class="tr-grid">
						<div class="td-grid" style="width: 120px">
							<input id="buscarExpediente" type="button" value="Buscar" style="margin: 19px;" class="button-image-complete">
						</div>
						<div class="td-grid">
							<ul id="block-grid-right">
                                <li>
                                    <a href="#" onclick="return agregarExpediente();" id="container-button-add" style="margin: 19px;"><div id="button-add-left"></div><div id="button-add-center"><span class="icon-plus-button">Nuevo</span></div><div id="button-add-right"></div></a>
                                </li>
                                 <li>
                                    <a onclick = "linkUp() " id="container-button-add"><div id="button-add-left"></div><div id="button-add-center"><span class="icon-plus-button">Agregar Oficina</span></div><div id="button-add-right"></div></a>
                                </li>
                            </ul>
						</div>
						                        <div class="td-grid">
                            <ul id="block-grid-right">
                                <li>
                                    <a onclick = "linkUp() " id="container-button-add"><div id="button-add-left"></div><div id="button-add-center"><span class="icon-plus-button">Agregar Oficina</span></div><div id="button-add-right"></div></a>
                                </li>
                            </ul>
                        </div>
					</div>
					<div class="tr-grid">
						<div class="td-grid">
							<span></span>
						</div>
						<div class="td-grid">
							<span></span>
						</div>
					</div>
		</div>			
		
		<fieldset class="scheduler-border">
					<legend class="scheduler-border">Buscar Expediente</legend>
		<div id="container-search" style="padding: 0px 175px 0px 0px;">
			<form id="form1">
					<ul>
						<table>
							<tbody>
								<tr>
									<td>Expediente :</td>
									<td>
										<table>
											<tr>
												<td><input type="number" value="2015"
													style="width: 60px;" id="expAnio" name="expAnio" min="2015"
													max="2040"></td>
												<td><input id="expNume" style="width: 40px;"
													name="expNume" type="text" class="input-border" /></td>
												<td><select id="expComb" style="width: 40px;"
													name="expComb" class="select-box">
														<option value="E">E</option>
														<option value="F">F</option>
														<option value="A">A</option>
														<option value="D">D</option>
												</select></td>
												<td><input id="expLibr" style="width: 60px;"
													name="expLibr" type="text" class="input-border" /></td>
											</tr>
										</table>
									</td>
									<td>Fecha de Ingreso:</td>
									<td><input id="datepicker" name="datepicker"
										style="width: 80px;" type="text" /></td>
									<td>Creado Por:</td>
									<td><input id="creadPor" name="creadPor"
										style="width: 80px;" type="text" class="input-border"></td>
								</tr>
								<tr>
									<td>Documento:</td>
									<td colspan="5"><input id="txtDocumento"
										name="txtDocumento" style="width: 500px;" type="text"
										class="validate[required] input-border"></td>
								</tr>
								<tr>
									<td>Remitentes:</td>
									<td>
										<table>
											<tr>
												<td><input id="txtRemitente" name="txtRemitente"
													style="width: 200px;" type="text" class="input-border"></td>
												<td>
													<input path="" id="buscarExpediente" type="button" value="..." style="margin: 1px;" class="button-image-complete-rexp">
												</td>
											</tr>
										</table>

									</td>

									<td colspan="4"><label id="lblRemitente"
										style="width: 200px;" class="input-border"></label></td>
								</tr>
								<tr>
									<td>Asunto:</td>
									<td><input id="txtAsunto" style="width: 200px;"
										name="txtAsunto" type="text" class="input-border"></td>
									<td>Interesado:</td>
									<td colspan="3"><input id="txtInteresado"
										name="txtInteresado" style="width: 200px;" type="text"
										class="input-border"></td>
								</tr>
								<tr>
									<td>UTD:</td>
									<td><input id="txtUtd" name="txtUtd" style="width: 200px;"
										type="text" class="input-border"></td>
									<td>Detalle:</td>
									<td colspan="3"><input id="txtDetalle" name="txtDetalle"
										style="width: 200px;" type="text" class="input-border"></td>
								</tr>
								<tr>
									<td>Descripción:</td>
									<td><textarea id="txtaDescripcion" rows="4" cols="20"></textarea></td>
									<td>Nro.Folios:</td>
									<td><input type="number" value="1" style="width: 60px;"
										id="expAnio" name="expAnio" min="1" max="100"></td>
									<td>Dias:</td>
									<td><input id="txtUtd" name="txtUtd" style="width: 70px;"
										type="text" class="input-border"></td>
								</tr>

							</tbody>
						</table>

					</ul>
				
			</form>
		</div>
</fieldset>
		<!-- Grilla -->
		<fieldset class="scheduler-border">
			<legend class="scheduler-border"> Documentos Externos </legend>
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
					<div class="tr-grid">
						<div class="td-grid" style="width: 240px;">
							<ul id="block-grid-right">
                                <li>
                                    <a onclick = "linkUp() " id="container-button-add" style="margin: 1px;"><div id="button-add-left"></div><div id="button-add-center"><span class="icon-plus-button">Añadir Documento</span></div><div id="button-add-right"></div></a>
                                </li>
                            </ul>
						</div>
						<div class="td-grid" style="width: 250px;">
							<ul id="block-grid-right">
                                <li>
                                    <a onclick = "linkUp() " id="container-button-add" style="margin: 1px;"><div id="button-add-left"></div><div id="button-add-center"><span class="icon-plus-button">Eliminar Documento</span></div><div id="button-add-right"></div></a>
                                </li>
                            </ul>
						</div>
					</div>
				</div>
			</div>
		</fieldset>




		<fieldset class="scheduler-border">
			<legend class="scheduler-border">Documentos Internos</legend>
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
	</div>

	<div id="up" class="up"></div>
	<div id="categoriaForm"></div>

</body>
</html>

