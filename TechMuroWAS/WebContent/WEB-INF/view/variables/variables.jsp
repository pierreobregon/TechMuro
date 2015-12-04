<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<link rel="stylesheet" href="../css/validationEngine.jquery.css" type="text/css">
<!-- Bordes para IE7
	<script>
        DD_roundies.addRule('input', '3px');
        DD_roundies.addRule('#container-search', '5px');
    </script>  
Fin Bordes para IE7 -->

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
	<script>
	
		grid = $("#list");
	 	var tudata = [];
	
		$(document).ready(function () {
		
			cargarDropDownHora();
			cargarDropDownMinuto();

			
			var haa = "${haa}";
			var maa = "${maa}";
			$("#horaActualizacion").val(haa);
			$("#minutoActualizacion").val(maa);
			
			if("${mpoa}" == "si"){
				$("#ejemplo-1").attr('checked','checked');
			}
				
			$('#subirAfiche').validationEngine({
				onValidationComplete: function(form, status){
						if(status==true){
							actualizarVariables();
					    }else{
					    	//alert("Se requiere llenar todos los campos obligatorios");
						}
					}
				}
			);
				
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
						
					
			
				
					               var  k = "${oficina.idoficina}";
	                    	
	                    	
	                    	
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
	                    	//$("#oficinas option").attr('disabled', 'disabled');
	                    	//$("#oficinas").multiselect("rebuild");
	                    	
		


		
		
		
		});
		
		function actualizarVariables(){
				
				if($("#ejemplo-1").is(':checked')){
					$("#ejemplo-1").val("si");
				}else{
					$("#ejemplo-1").val("no");
				}
			
				$.post("../actualizarVariables.htm",$("#subirAfiche").serialize(),function(data){
					console.log(data);
					if(data == "go"){
						alert("Registrado correctamente");
						enviarPeticion("../variables.htm");
					}else{
						alert("Todos los datos deben ser ingresados");
						enviarPeticion("../variables.htm");
					}
				});
				return false;
		}
	
		
	function cargarDropDownHora(){
		
		var i = 0;
		var inicio = 0;
		var fin = 23;
		var select = '';
		var horaFormateada = '';
		for (i=inicio;i<=fin;i++){
			
			if(i<10){
			 horaFormateada = '0' + i;
			}else{
			  horaFormateada = '' + i;
			}
			
    		select += '<option val=' + horaFormateada + '>' + horaFormateada + '</option>';
			
			}
			$('#horaActualizacion').html(select);

		}
		
		
		
		
	function cargarDropDownMinuto(){
		
		var i = 0;
		var inicio = 0;
		var fin = 59;
		var select = '';
		var minutoFormateado = '';
		for (i=inicio;i<=fin;i++){
			
			if(i<10){
			 minutoFormateado = '0' + i;
			}else{
			  minutoFormateado = '' + i;
			}
			
    		select += '<option val=' + minutoFormateado + '>' + minutoFormateado + '</option>';
			
			}
			$('#minutoActualizacion').html(select);

		}
		
			
		
		
	
	</script>

<!--[if lt IE 9]>
<script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->

</head>

<body>

    	<div id="block-grid">
            <div id="line-body-sup"></div>
            <div id="back-title">
                <h1>Variables Generales</h1>
            </div>
            <div id="box-var">
	            <form method="post" id="subirAfiche"  class="scrollbar">
	            	<h1>Variables Configuraci&oacute;n de Afiches</h1>
					<div class="formu">
						<ul id="body-var">
                            <li>Tiempo de espera de Pop Up de Imagen (min):</li>
                            <li id="right-li"><input class="validate[required,custom[integer]] input-white" name="tpu" value="${tpu}" type="text" id="tpu" maxlength="60" /></li>
							<div></div>
                            <li>Tiempo de espera de Pop Up de Video (min):</li>
                            <li id="right-li"><input class="validate[required,custom[integer]] input-white" name="tdv" value="${tdv}" type="text" id="tdv" maxlength="60" /></li>
							<div></div>
							<li>Ruta de im&aacute;genes para Afiches:</li>
                            <li id="right-li"><input class="validate[required] input-white" name="dia" value="${dia}" type="text" id="dia" maxlength="60"  /></li>
                            <div></div>
                            <li>Ruta de im&aacute;genes para Comunicados:</li>
                            <li id="right-li"><input class="validate[required] input-white" name="dic" value="${dic}" type="text" id="dic" maxlength="60"  /></li>
                            <div></div>
							<li>Ruta de Videos:</li>
                            <li id="right-li"><input class="validate[required] input-white" name="dv" value="${dv}" type="text" id="dv" maxlength="60" /></li>
 
                            </li>
                       		
                       
                        </ul>
					</div>
		 			
		 			<h1>Variables Configuraci&oacute;n de Visor</h1>
					<div class="formu">
						<div class="titulo-left"v>Hora de actualizaci&oacute;n autom&aacute;tica (hh:mm):</div>
						<div class="titulo-right">
							<select id="horaActualizacion" name="horaActualizacion" class="select-box-white-min"></select> :
                            <select id="minutoActualizacion" name="minutoActualizacion" class="select-box-white-min"></select>
						</div>
						<div class="line-bottom"></div>
						<div class="titulo-left">C&oacute;digo de Oficina por defecto:</div>
						<div class="titulo-right">
                            <select id="oficinas" class ="oficinasJson"  name="cop" >
	                                   <c:if  test="${!empty oficinas}">
		                                   <c:forEach items="${oficinas}" var="ofi">
		                                        <option value="${ofi.idoficina}">${ofi.codigo} - ${ofi.nombre}</option>
											</c:forEach>
										</c:if>
                                    </select>
						</div>
						<div class="line-bottom"></div>
						<div class="titulo-left">URL Canales m&aacute;s cercanos:</div>
						<div class="titulo-right">
							<input class="validate[required] input-white-2" name="urc" value="${urc}" type="text" id="urc" maxlength="60"  />
							
						</div>
						<div class="line-bottom"></div>
						<div class="titulo-left">URL Visor de Producci&oacute;n:</div>
						<div class="titulo-right">
							<input class="validate[required] input-white-2" name="urv" value="${urv}" type="text" id="urv" maxlength="60"  />
					</div>
					
					
					</div>
					<style type="text/css">
					
							.formu .btn-default {
								background:#fff url(../images/icon-select.png) no-repeat center right;
							}
							
							.formu .btn-default:hover, .btn-default:focus, .btn-default:active, .btn-default.active, .open .dropdown-toggle.btn-default {
								background:#fff url(../images/icon-select.png) no-repeat center right;
							}
						
						.formu .titulo-left {
							width:auto;
							font-family:"Stag Sans Light", Arial, sans-serif;
							font-size:14px;
							color:#333;
							margin:30px 0 30px 30px;
							float:left;
						}
						
						.formu .titulo-right {
							width:auto;
							font-family:"Stag Sans Light", Arial, sans-serif;
							font-size:14px;
							color:#333;
							margin:25px 30px 25px 0;
							float:right;
						}
                       		
					</style>
                       
		 					
					<h1>Variables Configuraci&oacute;n de Tasas y Tarifas</h1>
					<div class="formu">
                         <ul id="body-var"> 
                          	<li class="li-margin-bottom">
	                    		<div class="checkbox-style">
		                            <input type="checkbox" id="ejemplo-1" value="${mpoa}" name="mpoa" />
		                            <label for="ejemplo-1" ></label>
								</div>
                    		</li>
                    		<li class="li-margin-left">Mostrar Productos en orden alfab&eacute;tico</li>
						</ul>
					</div>
					<div class="center-imput">
	                    	<input type="submit" id="guardar" value="Guardar" class="button-image-complete-gren" >
					</div>
				</form>
			</div>
		</div>
		
</body>
</html>




