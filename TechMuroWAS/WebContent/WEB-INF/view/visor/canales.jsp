<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<html>
<head>
<script type="text/javascript">

function cerrarPopup(){
	$('#box').animate({'top':'30%'},60,function(){
        $('#overlay').fadeOut('fast');
    });
	
	
	return false;	
}

</script>

</head>
<body>
<div class="box-map" id="box">
        <a class="boxclose" id="boxclose" onclick="return cerrarPopup();"></a>
            <div class="container-float">
                <ul id="header-popup-visor">
                    <li id="header-popup-canales">Canales m&aacute;s cercanos</li>
                    <li id="header-color-2"></li>
                    <li id="header-color-3"></li>
                    <li id="header-color-4"></li>
                    <li id="header-color-5"></li>
                    <li id="header-color-6"></li>
                </ul>
                <article class="container-body-popup-2">
                 <iframe src="${url}"  height="530" width="1186" >
          
        </iframe>
                </article>
                <div id="footer-popup-detalle"></div>
            </div>
		</div>
</body>
</html>


