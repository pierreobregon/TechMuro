<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<link rel="stylesheet" href="../css/validationEngine.jquery.css" type="text/css">

<!--[if lt IE 9]>
<script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->

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
	
		.errorDesc{
	   position: absolute;
		top: 600px;
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

</style>
                      
            <script type="text/javascript">
            
				var k;
			    $(document).ready(function() {
			    
			    $("#descError").attr("style","display:none");
			    
			    
	
					window.prettyPrint() && prettyPrint();

                    if("${afiche.idafiche}"== ""){
                    
                    	jQuery("#subirAfiche").validationEngine({});
                    	
                    	jQuery("#buttonFile").click(function() {
					         jQuery("#imageInput").trigger('click');
					     });
                    
                    	//console.log("exitoooo ", $("#imageInput").val());
                    
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
                    
                    	$("#checkMiniatura").attr('checked','checked');	
                    	$("#checkMiniatura").attr('disabled', 'disabled');
                    	
                    	$('input[type=file]').fileValidator({
						  onInvalid:   function(type, file){ $(this).val(null);$(this).addClass('error '+type);},
						  type:        new RegExp("(\.|\/)(jpg|JPG|jpeg|JPEG|mp4|MP4)$"),
						  maxSize:     "80m"
						});
                    	

						$("#url").blur(function(){
							if($.trim($("#url").val()).length == 0){
									$("#imageInput").removeAttr('disabled');
									$("#textisselectedanyfile").removeAttr('disabled');
									$("#buttonFile").removeAttr('disabled');
									$("#textisselectedanyfile").attr('class','validate[requiredFile] input-file');
							    }
							    else{
							    	$("#imageInput").attr('disabled','disabled');
							    	$("#textisselectedanyfile").attr('disabled', 'disabled');
	                    			$("#buttonFile").attr('disabled', 'disabled');
							    	$("#textisselectedanyfile").removeAttr('class','validate[requiredFile] input-file');
							    	$("#textisselectedanyfile").attr('class','blur-file');
							    	jQuery("#textisselectedanyfile").val("");
							    	$(".textisselectedanyfileformError ").html("");
							    }
						});
						
						$("#buttonFile").blur(function(){
							if($.trim($("#textisselectedanyfile").val()).length == 0){
									$("#url").removeAttr('disabled');
									$("#url").css("background","#f7f7f7");
									//$("#imageInput").removeAttr('class','validate[requiredFile]');
									
							    }
							    else{
							    	$("#url").attr('disabled','disabled');
							    	$("#url").css("background","#ccc");
							    	//$("#imageInput").attr('class','validate[requiredFile]');
							    }
						});
						
						

                    }else
	                    {	
	                    	$("#textisselectedanyfile").attr('disabled', 'disabled');
	                    	$("#buttonFile").attr('disabled', 'disabled');
	                    	$("#url").attr('disabled', 'disabled');
	                    	$("#body-popup").append('<li class="no-margin">Fecha de Actualización:</li> <li id="right-li" class="no-margin"><fmt:formatDate value="${afiche.fechaactualizacion}" type="date" pattern="dd/MM/yyyy"/></li>');
	                    	$("#imageInput").attr('disabled', 'disabled');
	                    	$('#oficinas').multiselect({
		                        includeSelectAllOption: false,
					        	enableCaseInsensitiveFiltering: true
	                    	});
	                    	$("#afichePre").attr('src','../pick/${afiche.idafiche}.htm');
	                    	
	                    	k = "${aficheOficina.oficina.idoficina}";
	                    	
	                    	
	                    	
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
	                    	
	                    	var l = $("#oficinas").val();
	                    	
	                    	//console.log("Valor OFicinas",l);
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
				         if($("textarea").val().length == 0){
				    
							 b=false;
				         	desc = false;
				         	
				         	$("#descError").attr("style","display:block");
				         	
				         	//$("#errorDesc").attr("style","display:block");
							//$("textarea").after('<label class="red">Name is Required</label>');
						 }
						 
						 //console.log("a las 5pm.  = ", k);
										
						 if(b && desc && v){
						 	
						 	//cosnsole.log("test idafiche ","${afiche.idafiche}");
						 	 $("body").nimbleLoader("show");
					 		$('#subirAfiche').ajaxForm({url:"../addAfi/"+k+"/"+x+".htm",type:"post", success:function(data){
					 			 	
					 			 	if(data =="errorArchivo"){
					 			 		alert("No se pudo realizar la carga del archivo");
					 			 	}
					 			 	
					 				else if(data =="error"){
					 					alert("Debe seleccionar la opción \"Mostrar Miniatura\"");
					 					//$("#errorMin").attr("style","display:block");
					 				}else{		
					 				
					 				alert("Registrado correctamente");
					 				
										$('#box').animate({'top':'100px'},500,function(){
				                            $('#overlay').fadeOut('fast');
				                            	tudata = eval(data);
				                            	
				                            	var pageSize = jQuery("#list").getGridParam("rowNum");
									            var totalRecords = jQuery("#list").getGridParam('records');
									            var totalPages = Math.ceil(totalRecords/pageSize);
									            var currentPage = jQuery("#list").getGridParam('page');
									            
									         
									         $("body").nimbleLoader("hide");
									         
				                            if($("#aficheHidden").val() == ""){
				                            
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
                        <form:form method="post" id="subirAfiche"  class="scrollbar" enctype="multipart/form-data" modelAttribute="uploadedFile" commandName="afiche">
                            <ul id="body-popup">
                                <li class="middle-popup">Imagen / Video:</li>
                                <li id="right-li">
                                	<div id="imagePreview" style="margin-right:10px; float:left;">
                               			<img id="afichePre" height='60' width='42' alt="" />
                                	</div>
                                    <div class="middle-popup">
                                    
                                    	<input id="textisselectedanyfile" type="text" class="validate[requiredFile] input-file" readonly/>
                                    
                                    	<input class="validate[requiredFile] input-file" id="imageInput" type="file" name="file" onchange="loadImageFile();" onclick="h()" style="display:none"/>
                                    	
                                    	<input id = "buttonFile" type="button" style ="background: url(../images/input-file.png) no-repeat; width: 84px; height: 27px; cursor: pointer; float: left; border:0" value=""/>
                                    </div>
                                 </li>  
                                <li>URL:</li>
                                <li id="right-li"><form:input type="text" id="url" class="validate[custom[onlyNumberSp]] input-popup-2" path="video"/></li>
                                <li>Oficina:</li>
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
                                <li>Descripci&oacute;n:</li>
                              	<li id="right-li"><form:textarea class="validate[required]" path="descripcion" id="text-area-1" ></form:textarea>
                                      	
                              	
                              	<div id="descError" class="text-area-1formError parentFormsubirAfiche formError" style="opacity: 0.87; position: absolute; top: 325px; left: 443px; margin-top: -38px;"><div class="formErrorContent">* Campo<br> requerido<br></div><div class="formErrorArrow"><div class="line10"><!-- --></div><div class="line9"><!-- --></div><div class="line8"><!-- --></div><div class="line7"><!-- --></div><div class="line6"><!-- --></div><div class="line5"><!-- --></div><div class="line4"><!-- --></div><div class="line3"><!-- --></div><div class="line2"><!-- --></div><div class="line1"><!-- --></div></div></div>
                              	
                              	
                              	</li>
                              	
                                <li class="no-margin">Mostrar Miniatura:</li>
                                <li id="right-li" class="no-margin"><form:checkbox id="checkMiniatura" path="miniatura" value="S"  class="checkbox-middle" ></form:checkbox>
                                </li>
                                <li class="no-margin">Fecha de Creaci&oacute;n:</li>
                                <li id="right-li" class="no-margin"><fmt:formatDate value="${afiche.fechacreacion}" type="date" pattern="dd/MM/yyyy"/></li>
								<form:input id="aficheHidden" path="idafiche" type="hidden" />
                            </ul>
                            <ul id="button-popup">
                            	<li><input type="submit" id="guardar"  value="Guardar" class="button-image-complete-gren" ></li>
                            	<li><input type="button" id="cancel" value="Cancelar" class="button-image-complete"><li>
                            </ul>
                        </form:form>

                    </div>
                </div>
                
               
  
</body>
</html>