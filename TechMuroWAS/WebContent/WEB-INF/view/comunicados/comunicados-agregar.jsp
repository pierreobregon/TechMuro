<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>

<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">

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

	.nicEdit-selected {
		border: 0px solid #0000ff !important;
	}
			 
	.nicEdit-panel {
		background-color: none !important;
	}
	 
	.nicEdit-button {
		background-color: #fff !important;
	}
	


</style>
                      
            <script type="text/javascript">
            
				var k;
				
				
			
				function barrita(){
				var myNicEditor;
				myNicEditor = new nicEditor({buttonList : ['bold','italic','underline','strikeThrough','subscript','superscript','html', 'left', 'right', 'center', 'justify', 'ol', 'ul']});
				myNicEditor.setPanel('panel');
				myNicEditor.addInstance("divDesc");
				}
		
			    $(document).ready(function() {
			    
					window.prettyPrint() && prettyPrint();
					barrita();
					
                    if("${comunicado.idcomunicado}"== ""){
                    
                    	jQuery("#subirAfiche").validationEngine({});
                    	
                    	jQuery("#buttonFile").click(function() {
					         jQuery("#imageInput").trigger('click');
					     });
                    
                    	var notAll = $('#oficinas > option');

						if(notAll.length == 0){
							$('#oficinas').multiselect({
	                        	includeSelectAllOption: false,
	                        	enableCaseInsensitiveFiltering: false, 
	                        	buttonClass: 'validate[required] oficinasJson'
                    		});
						}else{
							$('#oficinas').multiselect({
		                        includeSelectAllOption: true,
					        	enableCaseInsensitiveFiltering: true,
					        	buttonClass: 'validate[required] multiselect dropdown-toggle btn btn-default oficinasJson'
	                    	});
						}
                    	
                    	$('input[type=file]').fileValidator({
						  onInvalid:   function(type, file){ $(this).val(null);$(this).addClass('error '+type);},
						  type:        new RegExp("(\.|\/)(jpg|JPG|jpeg|JPEG)$"),
						  maxSize:     "80m"
						});
                    	

						$("#divDesc").blur(function(){
						
						
							if($.trim($("#divDesc").html()).length == 0){
									$("#imageInput").removeAttr('disabled');
									$("#textisselectedanyfile").removeAttr('disabled');
									$("#buttonFile").removeAttr('disabled');
									$("#textisselectedanyfile").attr('class','validate[requiredFile2] input-file');
							    }
							    else{
							    	$("#imageInput").attr('disabled','disabled');
							    	$("#textisselectedanyfile").attr('disabled', 'disabled');
	                    			$("#buttonFile").attr('disabled', 'disabled');
							    	$("#textisselectedanyfile").removeAttr('class','validate[requiredFile2] input-file');
							    	$("#textisselectedanyfile").attr('class','blur-file');
							    	jQuery("#textisselectedanyfile").val("");
							    	$(".textisselectedanyfileformError ").html("");
							    }
						});
						
						$("#buttonFile").blur(function(){
							if($.trim($("#textisselectedanyfile").val()).length == 0){
// 									$("#url").removeAttr('disabled');
// 									$("#url").css("background","#f7f7f7");
									document.getElementById('divDesc').style.pointerEvents = 'auto';
									$("#divDesc").css("background","#f7f7f7");
									$("#imageInput").removeAttr('class','validate[requiredFile2]');
									
							    }
							    else{
// 							    	$("#url").attr('disabled','disabled');
// 							    	$("#url").css("background","#ccc");
							    	document.getElementById('divDesc').style.pointerEvents = 'none';
							    	$("#text-area-1").css("background","#ccc");
							    	$("#imageInput").attr('class','validate[requiredFile2]');
							    }
						});
						
						

                    }else
	                    {	
	                    	$("#textisselectedanyfile").attr('disabled', 'disabled');
	                    	$("#buttonFile").attr('disabled', 'disabled');
	                    	
	                    	if("${comunicado.tipocomunicado}" == "i"){
	                    		$("#text-area-1").attr('disabled', 'disabled');
	                    	}
	                    	//$("#url").attr('disabled', 'disabled');
	                    	//$("#body-popup").append('<li class="no-margin">Fecha de Actualizaci&oacute;n:</li> <li id="right-li" class="no-margin"><fmt:formatDate value="${comunicado.fechaactualizacion}" type="date" pattern="dd/MM/yyyy"/></li>');
	                    	$("#imageInput").attr('disabled', 'disabled');
	                    	$('#oficinas').multiselect({
		                        includeSelectAllOption: false,
					        	enableCaseInsensitiveFiltering: true
	                    	});
	                    	
	                    	
	                    	k = "${comunicadoOficina.oficina.idoficina}";
	                    	
	                    	if("${comunicado.url}" == ""){
	                    	
	                    	
	                    	}else{
	                    		document.getElementById('divDesc').style.pointerEvents = 'none';
	                    		$("#afichePre").attr('src','../pickComunicado/${comunicado.idcomunicado}.htm');
	                    	}
	                    	
	                    	if(k == "0"){
	                    		
	                    		$el= $("#oficinas");
							    $('option', $el).each(function(element) {
								      $el.multiselect('select', $(this).val());
								});
	                    	}else{
		                      	var valArr = [k];
								var i = 0, size = valArr.length;
								for(i; i < size; i++){
		                       			$("#oficinas option[value='"+valArr[i]+"']").attr('selected', 'selected');
		                       	}
		                       	$("#oficinas").multiselect("rebuild");
	                    	}
	                    	
	                    	$("#oficinas option").attr('disabled', 'disabled');
	                    	$("#oficinas").multiselect("rebuild");
	                    	
	                    }
                    
			    });

				$(function() {
                    $('#boxclose').click(function(){
                        $('#box').animate({'top':'100px'},500,function(){
                            $('#overlay').fadeOut('fast');
                        });
                    });
                    
                    $('#cancel').click(function(){
                        $('#box').animate({'top':'100px'},500,function(){
                            $('#overlay').fadeOut('fast');
                        });
                    });
                 
                 	
						
                    $('#guardar').click(function(){
                    
                    	jQuery("#subirAfiche").validationEngine('validate');
                    	
                    	
                    	
                    	var x = $("#oficinas").val();
                    	var b = true;
                    	var desc = true;
                    	var v = true;
                    	var options = $('#oficinas > option:selected');
				         if(options.length == 0){
				         	$("#errorOfi").attr("style","display:block");
				            b=false;
				         }
				         
				         
				        // $("#url").attr('disabled','disabled');
				          if($("#url").val().length == 0){
				        //  if($("input").val().length == 0){
				             b=false;
				              //$("#tituloError").attr("style","display:block");
				            
				            }
				            
				            
				         $("#text-area-1").val($("#divDesc").html());
				         
				         if($("textarea").val().length == 0){
				         	desc = false;
				         	$("#descError").attr("style","display:block");
				         	//$("#divDesc").attr("style","display:block");
							//$("textarea").after('<label class="red">Name is Required</label>');
						 }
						 
						 
						 
						 
						 
						 //console.log("a las 5pm.  = ", k);
										
						 if(b && desc && v){
							 $("body").nimbleLoader("show");
						 	//cosnsole.log("test idafiche ","${afiche.idafiche}");
					 		$('#subirAfiche').ajaxForm({url:"../addComunicado/"+x+".htm",type:"post", scriptCharset: "utf-8" ,contentType: "application/x-www-form-urlencoded; charset=UTF-8", success:function(data){
					 						
					 				if(data =="errorArchivo"){
					 			 		alert("No se pudo realizar la carga del archivo");
					 			 	}else{
					 			 			
					 				alert("Registrado correctamente");
					 				$("body").nimbleLoader("hide");
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
							 $("body").nimbleLoader("hide");
						 }else{
						 	return false;
						 }
                    });
				
// 				$('#t').click(function(){
				
					
// 					console.log("1 -- > ",$("#divDesc").val());
// 					console.log("2 -- > ",$("#divDesc").html());
					
// 					 $("#text-area-1").val($("#divDesc").html());
// 					 console.log("9999  -- > ", $("#text-area-1").val());
// 				});


                });
                
                
                
                var loadImageFile = (function () {
                
                	//$("#url").attr('disabled', 'disabled');

				    if (window.FileReader) {
				        var    oPreviewImg = null, oFReader = new window.FileReader(),
				            rFilter = /^(?:image\/jpeg|image\/jpeg|image\/jpeg)$/i;
				
				        oFReader.onload = function (oFREvent) {
				            if (!oPreviewImg) {
				                var newPreview = document.getElementById("imagePreview");
				                oPreviewImg = new Image();
				                oPreviewImg.style.width = (newPreview.offsetWidth).toString() + "px";
				                oPreviewImg.style.height = (newPreview.offsetHeight).toString() + "px";
				                newPreview.appendChild(oPreviewImg);
				            }
				            oPreviewImg.src = oFREvent.target.result;
				        };
				
				        return function () {
				        	
				        	
				        	var contentType = 'image/png';
							var b64Data = "/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAkGBwgHBgkIBwgKCgkFBQoFBQUFBQ8ICQUKFBEWFhQRExMYHCggGBolGxMTITEhJSkrLi4uFx8zODMsNygtLisBCgoKBQUFDgUFDisZExkrKysrKysrKysrKysrKysrKysrKysrKysrKysrKysrKysrKysrKysrKysrKysrKysrK//AABEIAKoBKAMBIgACEQEDEQH/xAAVAAEBAAAAAAAAAAAAAAAAAAAAB//EABQQAQAAAAAAAAAAAAAAAAAAAAD/xAAUAQEAAAAAAAAAAAAAAAAAAAAA/8QAFBEBAAAAAAAAAAAAAAAAAAAAAP/aAAwDAQACEQMRAD8AqgAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAP/9k=";
							var byteCharacters = atob(b64Data);
							
							var byteNumbers = new Array(byteCharacters.length);
							for (var i = 0; i < byteCharacters.length; i++) {
							    byteNumbers[i] = byteCharacters.charCodeAt(i);
							}
							
							var byteArray = new Uint8Array(byteNumbers);
							
							var blob = new Blob([byteArray], {type: contentType});
							
				            var aFiles = document.getElementById("imageInput").files;

				            if (aFiles.length === 0) { 
				            jQuery("#textisselectedanyfile").val(jQuery("#imageInput").val());
				            jQuery("#textisselectedanyfile").focus();
				            oFReader.readAsDataURL(blob);
				            //$("#textisselectedanyfile").value() = "Seleccionar Archivo";
				            
				            return; 
				            
				            
				            }
				            if (aFiles[0].type == "video/mp4"){
				            	jQuery("#textisselectedanyfile").val(jQuery("#imageInput").val());
				            	jQuery("#textisselectedanyfile").focus();
				            	oFReader.readAsDataURL(blob);
				            	return; 
// 				            	console.log("jdjd --> ", aFiles[0]);
// 				            	console.log("img --> ", aFil);
				            	//oFReader.readAsDataURL(aFiles[0]);	
				            }
				            if (!rFilter.test(aFiles[0].type)) {
				            oFReader.readAsDataURL(blob);
				            jQuery("#textisselectedanyfile").val("");
				            
				            jQuery("#textisselectedanyfile").focus();
				            return; 
				            }
				            
				            oFReader.readAsDataURL(aFiles[0]);
				           
                	
                			jQuery("#textisselectedanyfile").val(jQuery("#imageInput").val());
                			jQuery("#textisselectedanyfile").focus();
						
				        };
				
				    }
				    if (navigator.appName === "Microsoft Internet Explorer") {
				        return function () {
				            document.getElementById("imagePreview").filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = document.getElementById("imageInput").value;
				
				        };
				    }
				    
				    
				}
				
				
				)();
				
				function h(){
					$("#afichePre").hide();
				}

			    
			</script>
</head>
<body>


                <div class="overlay" id="overlay" style="display:none;">
                	<div class="box" id="box">
                    <a class="boxclose" id="boxclose"></a>
                    	<div id="title-header-popup"></div>
                        <form:form method="post" id="subirAfiche"  class="scrollbar" enctype="multipart/form-data" modelAttribute="uploadedFile" commandName="comunicado">
                            <ul id="body-popup">
                            	<li>T&iacute;tulo</li>
                            	
                                <li id="right-li"><form:input type="text" id="url" class="validate[required] input-popup-2" path="titulo" style="position:relative"/>
                                               
                                </li>
                                <li class="middle-popup">Imagen:</li>
                                <li id="right-li">
                                	<div id="imagePreview" style="margin-right:10px; float:left;">
                               			<img id="afichePre" height='60' width='42' alt="" />
                                	</div>
                                    <div class="middle-popup">
                                    
                                    	<input id="textisselectedanyfile" type="text"  class="validate[requiredFile2] input-file" readonly/>
                                    
                                    	<input class="validate[requiredFile2] input-file" id="imageInput" type="file" name="file" onchange="loadImageFile();" onclick="h()" style="display:none"/>
                                    	
                                    	<input id = "buttonFile" type="button" style ="background: url(../images/input-file.png) no-repeat; width: 84px; height: 27px; cursor: pointer; float: left; border:0" value=""/>
                                    </div>
                                 </li>  
<!--                                 <li>URL:</li> -->
<%--                                 <li id="right-li"><form:input name="" value="" type="text" id="url" class="validate[custom[onlyNumberSp]] input-popup-2" path="video"/></li> --%>
                                <li>Descripci&oacute;n:</li>
                              	<li id="right-li" class="descText" >
	                              	<table id="toolbox" border="0" cellpadding="0" cellspacing="0">
										<tbody>
											<tr>
												<td colspan="5">
						                        	<div id="container-panel">
														<div id="panel" style="width: 226px; height:26px; display:block; margin:0 auto;"></div>
						                            </div>
												</td>
											</tr>
										</tbody>
									</table>
	                              	<div id="divDesc" class="" style="width: 290px; height: 100px;line-height: 15px;background: #f7f7f7;border: 1px solid #e9e9e9;color: #555;overflow:auto;">${comunicado.descripcion}</div>
	                              	<form:textarea class="validate[required]" path="descripcion" id="text-area-1" style="display:none;"></form:textarea>
                              	 	<div id="descError" class="text-area-1formError parentFormsubirAfiche formError" style="display:none; opacity: 0.87; position: absolute; top: 425px; left: 443px; margin-top: -38px;"><div class="formErrorContent">* Campo requerido<br></div><div class="formErrorArrow"><div class="line10"><!-- --></div><div class="line9"><!-- --></div><div class="line8"><!-- --></div><div class="line7"><!-- --></div><div class="line6"><!-- --></div><div class="line5"><!-- --></div><div class="line4"><!-- --></div><div class="line3"><!-- --></div><div class="line2"><!-- --></div><div class="line1"><!-- --></div></div></div>
                              	
                              	</li>
                                <li>Oficina:</li>
                                <li id="right-li" class="active">
                                    <select id="oficinas" class="validate[required] oficinasJson" multiple="multiple" name="requiredOfi" onchange="$('.btn-group button').val(this.value)">
	                                   <c:if  test="${!empty oficinas}">
		                                   <c:forEach items="${oficinas}" var="ofi">
		                                        <option value="${ofi.idoficina}">${ofi.codigo} - ${ofi.nombre}</option>
											</c:forEach>
										</c:if>
                                    </select>
<!--                                     <label id="errorOfi"  class="error " style="display:none;">Escoger Oficina</label> -->
                                    <div id="errorOfi" class=" formError" style="opacity: 0.87; position: absolute; top: 108px; left: 450px; margin-top: -47px; display:none;"><div class="formErrorContent">* Campo requerido<br></div><div class="formErrorArrow"><div class="line10"></div><div class="line9"></div><div class="line8"></div><div class="line7"></div><div class="line6"></div><div class="line5"></div><div class="line4"></div><div class="line3"></div><div class="line2"></div><div class="line1"></div></div></div>
                              	</li>
                                
<!--                                 <li>Mostrar Miniatura:</li> -->
<%--                                 <li id="right-li"><form:checkbox id="checkMiniatura" path="miniatura" value="S" /> --%>
<!--                                 </li> -->
                                <li class="no-margin">Fecha de Creaci&oacute;n:</li>
                                <li id="right-li" class="no-margin"><fmt:formatDate value="${comunicado.fechacreacion}" type="date" pattern="dd/MM/yyyy"/></li>
<!--                                 <li>Fecha de Actualizaci&oacute;n:</li> -->
<!--                                 <li id="right-li">28/11/2012</li> -->
								<c:if test="${!empty comunicado.idcomunicado}">
									<li class="no-margin">Fecha de Actualizaci&oacute;n:</li>
									<li id="right-li" class="no-margin">
										<fmt:formatDate value="${comunicado.fechaactualizacion}" type="date" pattern="dd/MM/yyyy"/>
									</li>
								</c:if>

								<form:input id="comunicadoHidden" path="idcomunicado" type="hidden" />
                            </ul>
                            <ul id="button-popup">
                            	<li><input type="submit" id="guardar"  value="Guardar" class="button-image-complete-gren" ></li>
                            	<li><input type="button" id="cancel" value="Cancelar" class="button-image-complete"><li>
                            </ul>
                        </form:form>

			
<!--                 <input type="submit" id="t"  value="desc" class="button-image-complete-gren" >         -->

                    </div>
                </div>
                
               
  
</body>
</html>