<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>:: Muro Tecnol&oacute;gico ::</title>
<link href="../css/core.css" rel="stylesheet" type="text/css">
<script src="../js/URL_DD_roundies.js"></script>
<!-- Bordes para IE7
	<script>
        DD_roundies.addRule('input', '3px');
        DD_roundies.addRule('#container-search', '5px');
    </script>  
Fin Bordes para IE7 -->
<script type="text/javascript" src="../js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="../js/grid.locale-es.js"></script>
<script type="text/javascript" src="../js/jquery.jqGrid.min.js" ></script>

<script type="text/javascript" src="../js/bootstrap-3.0.3.min.js"></script>

<!--[if lt IE 9]>
<script src="js/html5.js"></script>s
<![endif]-->

</head>
<script>
	function submitLogin(){
		
		$.post("../login/login.htm", $("#login").serialize(), function(data) {
			if ($.trim(data) == 'SUCCESS'){
				   url_redirect({url: "../login/index.htm",
						          method: "post"
						         });
			}else{
				alert(data);
			}
		});
	}
	
	function url_redirect(options){
         var $form = $("<form />");
         
         $form.attr("action",options.url);
         $form.attr("method",options.method);

         //for (var data in options.data)
         //$form.append('<input type="hidden" name="'+data+'" value="'+options.data[data]+'" />');
          
         $("body").append($form);
         $form.submit();
    }
  
</script>
<body >
<form:form method="post" action="/login/login.htm" commandName="usuario" id="login"  >
<div id="container-popup-login">
	<div id="container-login">
    	
        <div id="block-center">
        		<div id="icon-muro-login"></div>
                <p>Muro Tecnol&oacute;gico Administrativo</p> 
                
            <ul id="register-user">
                <li>Usuario :
                </li>
                <li><form:input path="codUsuario" />
                </li>
                <li>Contraseña :
                </li>
                <li><form:password path="password" />
                </li>
            </ul>
            <input type="submit" id="button-image-complete-login" value="Acceder" onclick="javascript:submitLogin();return false;" >
		</div>
    </div>
</div>

</form:form>
</body>
</html>