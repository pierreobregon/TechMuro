<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<html>
<head>
<link href="../css-visor/core.css" rel="stylesheet" type="text/css">
<!-- <script type="text/javascript" src="../js-visor/jquery.tinyscrollbar.js"></script> -->

<script type="text/javascript">

// $(document).ready(function()
//         {
//             var $scrollbar = $("#scrollbar-com");

//             $scrollbar.tinyscrollbar();
//         });

function cerrarPopup(){
	$('#box').animate({'top':'5%'},60,function(){
        $('#overlay').fadeOut('fast');
    });

	return false;	

}

</script>

</head>
<body>
	
    					<div class="icon-popup-header-visor-comunicados"></div>
    					
				<div class="icon-popup-header-visor-comunicados"></div>
					<div id="scrollbar-com">
<!-- 			            <div class="scrollbar"> -->
<!-- 			            	<div class="track"> -->
<!-- 			                	<div class="thumb"> -->
<!-- 			                    	<div class="end"></div> -->
<!-- 			                    </div> -->
<!-- 			                </div> -->
<!-- 						</div> -->
<!-- 			            <div class="viewport">	 -->
			  				<div class="container-comunicados-popup">             
			                    <h3>${comunicado.titulo}</h3>
			                    <!--  <p class="pre-white">-->
			                    <p>
			                    <font face="arial">
			                    ${comunicado.descripcion}
			                    </font>
			                    </p>
<!-- 		               </div> -->
						</div>
					</div>
</body>
</html>