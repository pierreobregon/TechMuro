<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="X-UA-Compatible" content="IE=8" />
<link rel="stylesheet" href="../css/validationEngine.jquery.css" type="text/css">

<!--[if lt IE 9]>
<script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->


                      
            <script type="text/javascript">
            
				var k;
				grid3 = $("#list3");
			    $(document).ready(function() {
			    
			    	jQuery("#frmAgregarExpediente").validationEngine({});
			    	
					window.prettyPrint() && prettyPrint();
					
					$('#plaza').multiselect({
						    includeSelectAllOption: true,
							enableCaseInsensitiveFiltering: true, 
							buttonClass: 'validate[required] multiselect dropdown-toggle btn btn-default'
						});
						
					$('.btn-group button').val($('#plaza').val());

					hh.push({
						idplaza : '${a.plaza.idplaza}',
						idoficina : '${a.idoficina}',
						codigo : '${a.codigo}',
						nombre : '${a.nombre}',
						plaza : '${a.plaza.nombre}'
					});
					llenarGridDocumentoInternoExpediente(hh);
					
                    if("${oficina.idoficina}"== ""){
                    
                    	
                    	
                    	jQuery("#buttonFile").click(function() {
					         jQuery("#imageInput").trigger('click');
					     });

						

                    }else
	                    {	
	                    	//$("#url").attr('disabled', 'disabled');
	                    	$("#body-popup").append('<li class="no-margin">Fecha de Actualización:</li> <li id="right-li" class="no-margin"><fmt:formatDate value="${oficina.fechaactualizacion}" type="date" pattern="dd/MM/yyyy"/></li>');
	
	                    }
                    
                    
			    });

				$(function() {
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
					
                    
	                    alert("CARGANDO...");
	                    $('#guardar').click(function(){
	                		
	                    	jQuery("#frmAgregarExpediente").validationEngine('validate');
	                    	var b = true;
	                    	var v = true;
								alert("entrando...");			
							 if(b && v){
							 	//cosnsole.log("test idafiche ","${afiche.idafiche}");
						 		$('#frmAgregarExpediente').ajaxForm({url:"../mesa_parte/addExpediente.htm",type:"post", success:function(data){
						 					alert("termino de enviar...");	
						 					if(data =='false'){
						 						alert("Ya existe el expediente, consulte con el administrador");
						 					}else{
						 						alert("Expediente registrado satisfactoriamente");
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
				
				function llenarGridDocumentoInternoExpediente(Json) {

					grid3.jqGrid({
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

								pager : '#pager3',
								//pginput : false,

								gridview : true,
								rownumbers : false,
								sortname : 'idInDocInterno',
								viewrecords : false,
								sortorder : 'asc',

								width : 673,
								height : '100%',

								gridComplete : function() {
									var ids = jQuery("#list3").jqGrid('getDataIDs');
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

										jQuery("#list3").jqGrid('setRowData', ids[i], {
											editar : be
										});
										jQuery("#list3").jqGrid('setRowData', ids[i], {
											eliminar : se
										});

									}
								}

							});

				}
                
			
			</script>
</head>
<body>


                <div  class="overlay" id="overlay" style="display:none;">
                	<div class="box" id="box" style="width:800px;top: 18%;">
                    <a class="boxclose" id="boxclose" onclick="cerrar();"></a>
					<c:choose>
					    <c:when test="${!empty expediente.codCorrelativo}">
					        <div id="title-header-popup">Editar Expediente</div>
					    </c:when>
					    <c:otherwise>
					        <div id="title-header-popup">Registrar Expediente</div>
					    </c:otherwise>
					</c:choose>
                        <form:form id="frmAgregarExpediente" method="post" class="scrollbar"  modelAttribute="expedienteVO">

                            <ul id="body-popup">
                             <fieldset  class="fieldset">
    						<legend>Registro Expediente:</legend>
                              <table align="center"  width="100%" border="0" cellpadding="3" cellspacing="1">
                                <tr>
                            	   <td>Expediente:</td>
                            	   <td>
                            	     <c:choose>
                               			<c:when test="${tipo == 'agregar'}">
											<table>
												<tr>
													<td><form:input type="number" value="2015"
																    style="width: 60px;" id="expAnio" path="anioDocumento" class="validate[custom[integer],required] input-popup-2"
																	onkeypress="validaSoloNumeros();" maxlength="4" min="2015"
																	max="2040"/></td>
													<td><form:input id="expNume" path="numDocumento" style="width: 40px;" type="text" class="validate[custom[integer],required] input-popup-2" 
														onkeypress="validaSoloNumeros();" maxlength="3"/></td>
													<td><form:select id="expComb" path="indEstado" style="width: 40px;"
														class="select-box">
															<option value="E">E</option>
															<option value="F">F</option>
															<option value="A">A</option>
															<option value="D">D</option>
													</form:select></td>
													<td><form:input id="expLibr" style="width: 60px;"
														path="numDocumento" type="text" class="validate[custom[integer],required] input-popup-2" /></td>
												</tr>
											</table>
									
		                             	</c:when>
		                               	<c:otherwise>
		                              		  <table>
												<tr>
													<td><form:input type="number" value="2015"
																    style="width: 60px;" id="expAnio" path="anioDocumento" class="validate[custom[integer],required] input-popup-2"
																	onkeypress="validaSoloNumeros();" maxlength="4" min="2015" 
																	max="2040"/></td>
													<td><form:input id="expNume" path="numDocumento" 
														style="width: 40px;" type="text" class="validate[custom[integer],required] input-popup-2" 
														onkeypress="validaSoloNumeros();" maxlength="3" /></td>
													<td><form:select id="expComb" path="indEstado" style="width: 40px;" disabled="false"
														class="select-box">
															<option value="E">E</option>
															<option value="F">F</option>
															<option value="A">A</option>
															<option value="D">D</option>
													</form:select></td>
													<td><form:input id="expLibr" style="width: 60px;" 
														path="numDocumento" type="text" class="validate[custom[integer],required] input-popup-2" /></td>
												</tr>
											</table>
		                               </c:otherwise>
                               		</c:choose>
                            	   </td>
                            	   <td>Fecha de Ingreso:</td>
									<td><form:input id="fdatepicker" path="fecCreacion" 
										style="width: 80px;" type="text" /></td>
									<td>Creado Por:</td>
									<td><input id="creadPor" path="codUsuario" readonly="true"
										style="width: 80px;" type="text" class="input-border"></td>
                                </tr>
                                <tr>
                            	   <td colspan="1">Documento:</td>
                            	   <td colspan="5">
                                     <form:input type="text" id="nombre" class="validate[required] input-popup-3" path="desDocumento"  maxlength="60"/>
                            	   </td>                         	   
                                </tr>                                
                                <tr>
                            	   <td>Remitente:</td>
                            	   <td >
                                     <table>
											<tr>
												<td><form:input id="txtRemitentep" path="codEntidad"
													style="width: 200px;" type="text" class="validate[required] input-popup-2" maxlength="60"/></td>
												<td>
													<form:input id="buscarExpediente" path="" type="button" value="..." style="margin: 1px;" class="button-image-complete-rexp"/>
												</td>
											</tr>
										</table>
										
                            	   </td>
                            	   <td colspan="4"><form:label id="lblRemitentep"  path="codEntidad"
										style="width: 200px;" class="input-border"></form:label></td>
										
                                </tr>   
                                <tr> 
                            	   <td>Asunto:</td>
									<td><form:input id="txtAsuntop" path="desAsunto" style="width: 200px;" type="text" class="validate[required] input-popup-2" maxlength="60"/></td>
									<td>Interesado:</td>
									<td colspan="3"><form:input id="txtInteresadop" path="interesado" style="width: 200px;" type="text"
										class="validate[required] input-popup-2" maxlength="60"/></td>
											                        	   
                                </tr>          
                     			<tr>
                     				<td>UTD:</td>
									<td><form:input id="txtUtdp"  path="desUtd" style="width: 200px;"
										type="text" class="validate[required] input-popup-2" maxlength="60"/></td>
									<td>Detalle:</td>
									<td colspan="3"><form:input id="txtDetallep" path="detalle" name="txtDetallep"
										style="width: 200px;" type="text" class="input-border"/></td>
                                </tr>   
                     			<tr>
                     			
                     				<td>Descripci&oacute;n:</td>
									<td><form:textarea id="txtDescripcionExp" path="descripcion" class="validate[required]"  style="resize: none;" rows="4" cols="20"></form:textarea></td>
									<td>Nro.Folios:</td>
									<td><form:input type="number" class="validate[required] input-popup-2" value="1" style="width: 60px;"
										id="txtAnioExp" path="numFolios" min="1" max="100"/></td>
									<td>Dias:</td>
									<td><form:input id="txtDiasExp" class="validate[required] input-popup-2" path="canDias"  style="width: 70px;"
										type="text" /></td>
										
                                </tr>                                                                                                                       
                                                             
                              </table>
					</fieldset>
								
                            </ul>
                            
        				   
        				<div id="block-grid" style="min-height: 200px; padding-bottom: 10px">
        				<fieldset class="scheduler-border">
			<legend class="scheduler-border">Documentos Internos</legend>               
			<div id="container-grid">
				<div class="table-grid" style="margin: 0 0 0 0;">
					<div class="tr-grid">
						<div id="ResultadoBusqueda" class="td-grid">
							<p style="color: #f7f7f7">Resultados de la b&uacute;squeda...</p>
						</div>
					</div>
				</div>

			</div>
			</fieldset>
</div>

                            <ul id="button-popup">
                            	<li><input type="submit" id="guardar"  value="Guardar" class="button-image-complete-gren" ></li>
                            	<li><input type="button" id="cancel" onclick="cerrar();" value="Cancelar" class="button-image-complete"><li>
                            </ul>
                        </form:form>


                    </div>
                </div>
                
                <table id="list3"></table>
			<div id="pager3"></div>

</body>
</html>