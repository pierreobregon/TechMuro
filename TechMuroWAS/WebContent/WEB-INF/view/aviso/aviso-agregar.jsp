<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
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
	
	.errorAvi{
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
					//$(".contador").each(function(){
					
					jQuery('#grabarAviso').validationEngine({
						onValidationComplete: function(form, status){
							if(status==true){
								grabar();
							    }
							  }
						}
					);
					
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
					
					$(".contador").keyup(function(){
						var longitud = $(this).val().length;
								$(this).parent().find('#longitud_textarea').html('<b>'+longitud+'</b> caracteres');
								$(this).keyup(function(){ 
									var nueva_longitud = $(this).val().length;
									$(this).parent().find('#longitud_textarea').html('<b>'+nueva_longitud+'</b> caracteres');
									if (nueva_longitud == "140") {
										$('#longitud_textarea').css('color', '#ff0000');
									}
								});
							});
					



                if("${aviso.idaviso}" == ""){
				    //jQuery("#grabarAviso").validationEngine({});
				}else{
				
				$("#body-popup").append('<li>Fecha de Actualización:</li> <li id="right-li"><fmt:formatDate value="${aviso.fechaactualizacion}" type="date" pattern="dd/MM/yyyy"/></li>');
				
				}
			    });

									
	function grabar(){
                    	var x = $("#aviso").val();
                    	var b = true;
                    	var desc = true;
                    	var v = true;
                    	//var options = $('#aviso > option:selected');
						
				        /* if(options.length == 0){
				         	$("#errorAvi").attr("style","display:block");
				            b=false;
				         }*/
         $("#text-area-1").val($("#divDesc").html());
						 
         if($("textarea").val().length == 0||$("textarea").val() == "<br>"){
				         	desc = false;
							b=false;
                            $("#errorAvi").attr("style","display:block");
						 }
						  									
						  if(b && v){
			$("body").nimbleLoader("show");
	 		$.post("../addAviso.htm", $("#grabarAviso").serialize(), function(data){
	 		
	 	
					alert("Registrado correctamente");
					
										$('#box').animate({'top':'100px'},500,function(){
				                            $('#overlay').fadeOut('fast');
				                            	tudata = eval(data);
				                            	
				                            	var pageSize = jQuery("#list").getGridParam("rowNum");
									            var totalRecords = jQuery("#list").getGridParam('records');
									            var totalPages = Math.ceil(totalRecords/pageSize);
									            var currentPage = jQuery("#list").getGridParam('page');
									            
				                            if($("#avisoHidden").val() == ""){
				                            
				                            jQuery("#list").setGridParam({data : tudata,page:totalPages}).trigger("reloadGrid");
				                            
				                            }else{
				                            	jQuery("#list").setGridParam({data : tudata,page:currentPage}).trigger("reloadGrid");
				                            }											
			                        	});
                    	
                    	
                    	
                    	
		              		                    });
	 		$("body").nimbleLoader("hide");
						 }else{
						 	return false;
						 }
						 
	}
						 
	function cambiaDiv(){
						 
		if($('#divDesc').html()=="<br>"){

		}else{
			$('#divDesc').val($('#divDesc').html());
		}

			    
	}
			</script>
</head>
<body>


                <div class="overlay" id="overlay" style="display:none;">
                	<div class="box" id="box">
                    <a class="boxclose" id="boxclose"></a>
                    	<div id="title-header-popup"></div>
                        <form:form method="post" id="grabarAviso" class="scrollbar" commandName="aviso">

                           <ul id="body-popup">
                            	<li>T&iacute;tulo</li>
                                <li id="right-li"><form:input type="text" id="titulo" class="validate[required] input-popup-2" path="titulo"/></li>
                              	<li>Circular</li>
                                <li id="right-li"><form:input type="text" id="circular" class="validate[required] input-popup-2" path="circular"/></li>
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
	                              	<div id="divDesc" class="validate[required] contador" style="width: 290px; height: 100px;line-height: 15px;background: #f7f7f7;border: 1px solid #e9e9e9;color: #555;overflow:auto;">${aviso.descripcion}</div>
	                              	<form:textarea cssClass="contador" path="descripcion" id="text-area-1" style="display:none;"></form:textarea>
				
									<div id="errorAvi" class="formError" style="opacity: 0.87; position: absolute; top: 108px; left: 450px; margin-top: -47px; display:none;"><div class="formErrorContent">* Campo requerido<br></div><div class="formErrorArrow"><div class="line10"></div><div class="line9"></div><div class="line8"></div><div class="line7"></div><div class="line6"></div><div class="line5"></div><div class="line4"></div><div class="line3"></div><div class="line2"></div><div class="line1"></div></div></div>
                              	</li>
                                <li>Fecha de Creaci&oacute;n:</li>
                                <li id="right-li"><fmt:formatDate value="${aviso.fechacreacion}" type="date" pattern="dd/MM/yyyy"/></li>
<!--                                 <li>Fecha de Actualizaci&oacute;n:</li> -->
<!--                                 <li id="right-li">28/11/2012</li> -->
								<form:input id="avisoHidden" path="idaviso" type="hidden" />
                            </ul>
                            <ul id="button-popup">
                            	<li><input type="submit" id="guardar"  value="Guardar" class="button-image-complete-gren" onclick="cambiaDiv();" /></li>
                            	<li><input type="button" id="cancel" value="Cancelar" class="button-image-complete" /><li>
                            </ul>
                        </form:form>

                    </div>
                </div>
                
               
  
</body>
</html>