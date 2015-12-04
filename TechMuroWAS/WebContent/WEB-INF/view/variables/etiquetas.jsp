<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=8" />

<!-- Bordes para IE7
	<script>
        DD_roundies.addRule('input', '3px');
        DD_roundies.addRule('#container-search', '5px');
    </script>  
Fin Bordes para IE7 -->


	<script>
	
		grid = $("#list");
	 	var tudata = [];
	
		$(document).ready(function () {
		
	
		});
		
		function actualizarEtiquetas(){
				
				$.post("../actualizarEtiquetas.htm",$("#subirAfiche").serialize(),function(data){
					console.log(data);
					if(data == "go"){
						alert("Registrado correctamente");
						enviarPeticion("../etiquetas.htm");
					}else{
						alert("Se requiere que cada sección tenga una etiqueta");
						enviarPeticion("../etiquetas.htm");
					}
				});
				return false;
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
                <h1>Etiquetas</h1>
            </div>
            <div id="box-eti" style=" padding-bottom:0;">	
	            <form method="post" id="subirAfiche" onsubmit="return actualizarEtiquetas();">
                        <ul id="body-eti">
                        	<li class="header-var-tasas-tarifas">
                        		<div class="sprit-icon-var-tarifario"></div>
                        		<input class="input-var-1" name="tt" value="${tt}" type="text" id="url"/>
                        	</li>
                            <li class="header-var-tasas-natural">
                        		<input class="input-var-2" name="pn" value="${pn}" type="text" id="url" />
                        	</li>
                        	<li class="header-var-tasas-juridica">
                        		<input class="input-var-2" name="pj" value="${pj}" type="text" id="url" />
                        	</li>
                        	<li>
                        		<div class="sprit-icon-var-notarias"></div>
                        		<input class="input-var-3" name="no" value="${no}" type="text" id="url" />
                        	</li>
                        	<li>
                        		<div class="sprit-icon-var-comunicados"></div>
                        		<input class="input-var-3" name="co" value="${co}" type="text" id="url" />
                        	</li>
                        	<li>
                        		<div class="sprit-icon-var-canalescercanos"></div>
                        		<input class="input-var-3" name="cmc" value="${cmc}" type="text" id="url" />
                        	</li>
                        </ul>
                        
                        <ul id="button-popup" style="margin-top:25px; margin-left: 65px;" >
                        	<li><input type="submit" id="guardar"  value="Guardar" class="button-image-complete-gren" ></li>
                        </ul>
				</form>
            </div>
            
            
            
                
        </div>
        
        
        


</body>
</html>




