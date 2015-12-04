<!DOCTYPE>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta charset="UTF-8"/>
<title>:: Muro Tecnol&oacute;gico ::</title>

<link href="../css-visor/core.css" rel="stylesheet" type="text/css">
<link href="../css-visor/allinone_carousel.css" rel="stylesheet" type="text/css">

<script type="text/javascript" src="../js-visor/jquery.min.js" ></script>
<script type="text/javascript" src="../js-visor/jquery-ui.min.js"></script>
<script type="text/javascript" src="../js-visor/jquery.ui.touch-punch.min.js" ></script>
<script type="text/javascript" src="../js-visor/allinone_carousel.js" ></script>
<script type="text/javascript" src="../js-visor/jquery.colorbox.js" ></script>

<script src="../js-visor/jquery-ui-1.10.3.custom.min.js"></script>
<script src="../js-visor/portBox.slimscroll.min.js"></script>


<script type="text/javascript">
	$(document).ready(function() {
		carga();
	});
	
	function carga(){
		//$("#banner-container").load("../visor/afiche/${codigoOficina}.htm");
		$("#details-notarias").load("../visor/notarias/${codigoOficina}.htm");
	}
	
	function verNotaria(id){
		$("#overlay").load("../visor/notaria/"+id+".htm", function(){
			$('#overlay').fadeIn('fast',function(){
                //$('#box').animate({'top':'30%'},100);
             });
		});
	}
	
</script>

</head>
<body>
<div id="container">
	
    <header id="header">
    	<img src="../images-visor/logo-header.jpg" alt="" />
        <figure id="logo-responsive"></figure>
    </header>
               
	<div id="banner-container"></div>
	
	<div id="footer" class="clearBoth"></div>
	
    <section id="body">
		<article id="block-tasas-tarifas">
        	<div id="head-title-tasas-tarifas">
            	<h1>Tasas y Tarifas</h1>
            </div>
            <article id="shadow-tasas-tarifas"></article>
            <ul id="block-body-personas">
                <li id="border-left">
                	<div id="head-title-personas">
                        <h1>Persona Natural</h1>
                    </div>
                </li>
                <li id="border-right">
                	<div id="head-title-personas">
                        <h1>Persona Jur&iacute;dica</h1>
                    </div>
                </li>
            </ul>
            <article id="shadow-body-tasas-tarifas"></article>
        </article>
        
        <article id="block-sub">
            <a href="#" data-display="details-notarias" class="button"><div id="block-body-notarias">
              <h1>Lista de Notarios</h1></div></a>
            <div id="block-body-comunicados">
                <div id="head-title-comunicados">
                    <h1>Comunicados</h1>
                </div>
			</div> 
            <div id="block-body-canales">
                <div id="head-title-canales">
                    <h1>Comunicados</h1>
                </div>
			</div>
            <article id="shadow-body-notarias"></article>
            <article id="shadow-body-comunicados"></article>
            <article id="shadow-body-canales"></article>
        </article>
    </section>

	<div id="details-notarias" class="portBox"></div>
	
	</div>
</body>

</html>