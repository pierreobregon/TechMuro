<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset='UTF-8' />

<!--[if lt IE 9]>
<script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->
<title>:: Muro Tecnol&oacute;gico ::</title>
  
<!-- must have -->
<link href="../css-visor/core.css" rel="stylesheet" type="text/css">
<link href="../css-visor/allinone_carousel.css" rel="stylesheet" type="text/css">


<script type="text/javascript" src="../js-visor/jquery/all.js" ></script>
<!-- <script type="text/javascript" src="../js-visor/jquery.min.js" ></script> -->
<script type="text/javascript" src="../js-visor/jquery-ui.js"></script>
<script type="text/javascript" src="../js-visor/jquery.ui.touch-punch.min.js" ></script>
<script type="text/javascript" src="../js-visor/allinone_carousel.js" ></script>
<!-- <script type="text/javascript" src="../js-visor/jquery.colorbox.js" ></script> -->

<!-- <script src="../js-visor/portBox.slimscroll.min.js"></script> -->
<script type="text/javascript" src="../js-visor/top_up-min.js"></script>
<script type="text/javascript" src="../js-visor/jquery.scrollbox.js"></script>

<script>
	$(document).ready(function(){
		
		console.log("Aviso --> ","${avisos}");
		var time = new Date().getTime();
		var setTime;
		$(document.body).bind("mousemove keypress", function(e) {
		    time = new Date().getTime();
		    clearTimeout(setTime);
			
			$("li[data-title^='']").bind("click", function() {
            var myid = $(this).attr("data-title");
             alert("video" + myid);
            });


		    setTime = setTimeout("TopUp.close();", parseInt("${tiempoActualizacionPopUp}")*1000*60);
			
		});
		
		function auto_refresh() {
			window.location.reload(true);
		}
		
		$("li[data-title^='']").bind("click", function() {
		var myid = $(this).attr("data-title");
		 alert("video" + myid);
		});
		
		setTime = setTimeout("TopUp.close();", parseInt("${tiempoActualizacionPopUp}")*1000*60);
		
		setTimeout(auto_refresh, parseInt("${tiempoActualizacion}")*1000*60);
		
		var text = "";
		
		var textMin = [];
		
		var textMinTodas = [];
		
		'<c:forEach items="${oficinaVisor}" var="a">';
		if("${a.afiche.tipoafiche}"=="v"){
			text += '<li data-title="${a.afiche.descripcion}"><a href="${a.afiche.video}"  toptions="title = Visualizador de Afiches, width = 853, height = 505, layout = quicklook, shaded = 1, group = 1, readAltText: 1"><img src="../pick/${a.afiche.idafiche}.htm" alt="" /></a></li>';
		}else{
			if("${a.afiche.tipoafiche}"=="y"){
				
				text += '<li data-title="${a.afiche.descripcion}"><a href="${a.afiche.video}"  toptions="title = Visualizador de Afiches, width = 853, height = 505, type = flash, layout = quicklook, shaded = 1, group = 1, readAltText: 1"><img src="../pick/${a.afiche.idafiche}.htm" alt="" /></a></li>';
			}else{
				text += '<li data-title="" ><a href="../visor/big/${a.afiche.idafiche}.htm" toptions="title = Visualizador de Afiches, width = auto, height = auto, effect = clip, layout = quicklook, shaded = 1, group = 1"><img src="../pick/${a.afiche.idafiche}.htm" alt="" /></a></li>';
			}
		}
		'</c:forEach>';
		
		
		
		'<c:forEach items="${todas}" var="t">';
		if("${t.afiche.tipoafiche}"=="v"){
			text += '<li data-title="${t.afiche.descripcion}" ><a href="${t.afiche.video}" toptions="title = Visualizador de Afiches, width = 853, height = 505, layout = quicklook, shaded = 1, shaded = 1, group = 1, readAltText: 1"><img src="../pick/${t.afiche.idafiche}.htm" alt="" /></a></li>';
		}else{
			if("${t.afiche.tipoafiche}"=="y"){
				text += '<li data-title="${t.afiche.descripcion}"><a href="${t.afiche.video}"  toptions="title = Visualizador de Afiches, width = 853, height = 505, type = flash, layout = quicklook, shaded = 1, group = 1, readAltText: 1"><img src="../pick/${t.afiche.idafiche}.htm" alt="" /></a></li>';
			}else{
				text += '<li data-title=""><a href="../visor/big/${t.afiche.idafiche}.htm" toptions="title = Visualizador de Afiches, width = auto, height = auto, effect = clip, layout = quicklook, shaded = 1, group = 1"><img src="../pick/${t.afiche.idafiche}.htm" alt="" /></a></li>';
			}
		}	
		'</c:forEach>';
		
		var countMin = 0;
		
		var countMinTodas = 0;
		
		'<c:forEach items="${miniaturaTodas}" var="a">';
		if("${a.afiche.tipoafiche}"=="v"){
			textMinTodas.push('<li data-title="${a.afiche.descripcion}"><a href="${a.afiche.video}"  toptions="title = Visualizador de Afiches, width = 853, height = 505, layout = quicklook, shaded = 1, group = 2, readAltText: 1"><img src="../pick/${a.afiche.idafiche}.htm" alt="" /></a></li>');
		}else{
			if("${a.afiche.tipoafiche}"=="y"){
				textMinTodas.push('<li data-title="${a.afiche.descripcion}"><a href="${a.afiche.video}"  toptions="title = Visualizador de Afiches, width = 853, height = 505, type = flash, layout = quicklook, shaded = 1, group = 2, readAltText: 1"><img src="../pick/${a.afiche.idafiche}.htm" alt="" /></a></li>');
			}else{
				textMinTodas.push('<li data-title="" ><a href="../visor/big/${a.afiche.idafiche}.htm" toptions="title = Visualizador de Afiches, width = auto, height = auto, effect = clip, layout = quicklook, shaded = 1, group = 2"><img src="../pick/${a.afiche.idafiche}.htm" alt="" /></a></li>');
			}
		}
		countMinTodas++;
		'</c:forEach>';
		
		
		'<c:forEach items="${miniatura}" var="t">';
		if("${t.afiche.tipoafiche}"=="v"){
			textMin.push('<li data-title="${t.afiche.descripcion}" ><a href="${t.afiche.video}" toptions="title = Visualizador de Afiches, width = 853, height = 505, layout = quicklook, shaded = 1, group = 2, readAltText: 1"><img src="../pick/${t.afiche.idafiche}.htm" alt="" /></a></li>');
		}else{
			if("${t.afiche.tipoafiche}"=="y"){
				textMin.push('<li data-title="${t.afiche.descripcion}"><a href="${t.afiche.video}"  toptions="title = Visualizador de Afiches, width = 853, height = 505, type = flash, layout = quicklook, shaded = 1, group = 2, readAltText: 1"><img src="../pick/${t.afiche.idafiche}.htm" alt="" /></a></li>');
			}else{
				textMin.push('<li data-title=""><a href="../visor/big/${t.afiche.idafiche}.htm" toptions="title = Visualizador de Afiches, width = auto, height = auto, effect = clip, layout = quicklook, shaded = 1, group = 2"><img src="../pick/${t.afiche.idafiche}.htm" alt="" /></a></li>');
			}
		}
		countMin++;
		'</c:forEach>';
		
		if((countMin + countMinTodas) <= 4){
			
			for (var i = 0; i < textMin.length; i++) {
				$("#container-nav-min-left #container-min-center").append(textMin[i]);
			}
			for (var j = 0; j < textMinTodas.length; j++) {
				$("#container-nav-min-left #container-min-center").append(textMinTodas[j]);
			}
			$("#container-nav-min-right").attr("style","display:none");
		}else{
			var iM=0;
			
			var iMT=0;
			
			for (var i = 0; i < textMin.length; i++) {
				$("#container-nav-min-left #container-min-center").append(textMin[i]);
				iM++;
				if(iM==4){
					break;
				}
			}
			
			if(countMin==4){
				for (var j = 0; j < textMinTodas.length; j++) {
					$("#container-nav-min-right #container-min-center").append(textMinTodas[j]);
				}
			}else{
				if(countMin>4){
					for (var i = 4; i < textMin.length; i++) {
						$("#container-nav-min-right #container-min-center").append(textMin[i]);
					}
					for (var j = 0; j < textMinTodas.length; j++) {
						$("#container-nav-min-right #container-min-center").append(textMinTodas[j]);
					}
				}else{
				
					for (var j = 0; j < textMinTodas.length; j++) {
						$("#container-nav-min-left #container-min-center").append(textMinTodas[j]);
						iMT++;
						if(iMT==(4-iM)){
							break;
						}
					}
					for (var j = 4-iM; j < textMinTodas.length; j++) {
						$("#container-nav-min-right #container-min-center").append(textMinTodas[j]);
					}
					
				}
			}
		}
		
		$(".allinone_carousel_list").append(text);
		
		var textComunicados = [];
		var countComunicado = 0;

		'<c:forEach items="${comunicados}" var="a">';
			countComunicado++;
			if(countComunicado % 2 == 0){
				if("${a.comunicado.tipocomunicado}" == "Imagen"){
					textComunicados.push('<li><a href="../visor/comunicadoImagen/${a.comunicado.idcomunicado}.htm" id="activator" class="clear-right " toptions="title = Comunicado, effect = clip, layout = quicklook, shaded = 1"><p>${a.comunicado.titulo}</p></a></li>');
				}else{
					textComunicados.push('<li><a href="../visor/comunicado/${a.comunicado.idcomunicado}.htm" id="activator" class="clear-right " toptions="title = Comunicado, effect = clip, layout = quicklook, shaded = 1"><p>${a.comunicado.titulo}</p></a></li>');
				}
			}else{
				if("${a.comunicado.tipocomunicado}" == "Imagen"){
					textComunicados.push('<li><a href="../visor/comunicadoImagen/${a.comunicado.idcomunicado}.htm" class=""  toptions="title = Comunicado, effect = clip, layout = quicklook, shaded = 1"><p>${a.comunicado.titulo}</p></a></li>');
				}else{
					textComunicados.push('<li><a href="../visor/comunicado/${a.comunicado.idcomunicado}.htm" class="" toptions="title = Comunicado, effect = clip, layout = quicklook, shaded = 1"><p> ${a.comunicado.titulo}</p></a></li>');
				}
			}
	 	'</c:forEach>';
	 	
	 	for (var d = 0; d < textComunicados.length; d++) {
			$("#marqueeComunicados").append(textComunicados[d]);
		}
	 	
		
		jQuery('#allinone_carousel_sweet').allinone_carousel({
		  skin: 'sweet',
		  width: 1920,
		  height: 458,
		  autoPlay: 5,
		  responsive:true,
		  resizeImages:true,
		  autoHideBottomNav:false,
		  //easing:'easeOutBounce',
		  numberOfVisibleItems:5,
		  elementsHorizontalSpacing:150,	
		  elementsVerticalSpacing:15,
		  verticalAdjustment:140,
		  animationTime:0.5,
		  circleLeftPositionCorrection:10,
		  circleTopPositionCorrection:10,
		  nextPrevMarginTop:30,
		  playMovieMarginTop:-15,
		  bottomNavMarginBottom:-25,
		  
		  showCircleTimer:false,
		  showPreviewThumbs:false,
		  showBottomNav:false
	  	});
		
		$('#boxclose').click(function(){
            $('#box').animate({'top':'5%'},60,function(){
                $('#overlay').fadeOut('fast');
            });
        });
		
	});
	
	function verComunicado(id){
		$("#overlay").load("../visor/comunicado/"+id+".htm", function(){
			$('#overlay').fadeIn('fast',function(){
				   //$('#box').animate({'top':'30%'},100);
				});
		});
		return false;
	}
	
	function verNotaria(id){
		$("#overlay").load("../visor/notaria/"+id+".htm", function(){
			$('#overlay').fadeIn('fast',function(){
                //$('#box').animate({'top':'30%'},100);
             });
		});
		return false;
	}
	
	function verAviso(id){
		$("#overlay").load("../visor/aviso/"+id+".htm", function(){
			$('#overlay').fadeIn('fast',function(){
				//$('#box').animate({'top':'30%'},100);
			 });
		});
	    return false;	
	}
	
</script>
<script type="text/javascript" src="../js-visor/jquery.tinyscrollbar.js"></script>
<script type="text/javascript">
$(document).ready(function()
{
    var $scrollbar = $("#scrollbar1");

    $scrollbar.tinyscrollbar();
});
</script>


</head>
<body>

<!--<span><fmt:formatDate value="${a.comunicado.fechacreacion}" type="date" pattern="dd/MM/yyyy"/></span>
<span><fmt:formatDate value="${a.comunicado.fechacreacion}" type="date" pattern="dd/MM/yyyy"/></span>
<span><fmt:formatDate value="${a.comunicado.fechacreacion}" type="date" pattern="dd/MM/yyyy"/></span>
<span><fmt:formatDate value="${a.comunicado.fechacreacion}" type="date" pattern="dd/MM/yyyy"/></span>-->
<!------------------------------------------------------------------------------------>
<div id="container">  
	
    <header id="header">
    	<img src="../images-visor/logo-header.jpg" alt="" />
        <figure id="logo-responsive"></figure>
    </header>
	
	<div id="banner-container">
		<div id="allinone_carousel_sweet">
		            <div class="myloader"></div>
		            <!-- CONTENT -->
		            <ul class="allinone_carousel_list">
		           </ul>
        </div>  
		        
        <div id="container-nav-min-right">
        	<div id="container-min-left"></div>
                <ul id="container-min-center">
                </ul>
            <div id="container-min-right"></div>
        </div>
        
        <div id="container-nav-min-left">
        	<div id="container-min-left"></div>
                <ul id="container-min-center">
                </ul>
            <div id="container-min-right"></div>
        </div>
        
        
	</div>
	

	<!-------------------------------------------------------------------------------------> 	        

	<!--end Banner-->

	<div id="footer" class="clearBoth"></div>
	
    <section id="body">
		<article id="block-tasas-tarifas">
        	<div id="head-title-tasas-tarifas">
            	<h1>${tt}</h1>
            </div>
            <article id="shadow-tasas-tarifas"></article>
            <div class="container-description-par">
            	<div class="description-par">
                    <p>El tarifario completo y las f&oacute;rmulas de nuestros productos se encuentran a su disposici&oacute;n en la p&aacute;g. web: www.bbvacontinental.pe y en el sitio del subgerente de esta oficina.</p>
                    <a href="../visor/avisos.htm" class="button" toptions="title = Avisos Importantes, effect = clip, layout = quicklook, shaded = 1">
                    	<span class="icon-avisos-importantes"></span>
                        <div class="par-avisos">Ver Avisos importantes</div>
					</a>
                </div>
            </div>
            <ul id="block-body-personas">
                <li id="border-left">
                	<div id="head-title-personas">
                        <h1>${pn}</h1>
                    </div>
                    <div id="scrollbar1">
			            <div class="scrollbar">
			            	<div class="track">
			                	<div class="thumb">
			                    	<div class="end"></div>
			                    </div>
			                </div>
						</div>
			            <div class="viewport">
			                 <div class="overview">
				                    <ul class="list-persona-natural">
				                    	<c:forEach items="${productoListPN}" var="productoPN">
				                    		<li><a href="../visor/tarifario/${productoPN.idproducto}.htm" 
				                    			toptions="title = Tarifario, effect = clip, layout = quicklook, shaded = 1">${productoPN.nombre}</a></li>
				                    	</c:forEach>
				                    </ul>
			                </div>
			            </div>
			        </div> 
                </li>
                <li id="border-right">
                	<div id="head-title-personas">
                        <h1>${pj}</h1>
                    </div>
                    <div id="scrollbar1">
			            <div class="scrollbar">
			            	<div class="track">
			                	<div class="thumb">
			                    	<div class="end"></div>
			                    </div>
			                </div>
						</div>
			            <div class="viewport">
			                 <div class="overview">
			                    <ul class="list-persona-natural">
			                    	<c:forEach items="${productoListPJ}" var="productoPJ">
			                    		<li><a href="../visor/tarifario/${productoPJ.idproducto}.htm" 
			                    			toptions="title = Tarifario, effect = clip, layout = quicklook, shaded = 1">${productoPJ.nombre}</a></li>
			                    	</c:forEach>
			                    </ul>
                    		</div>
			            </div>
			        </div> 
                </li>
            </ul>
            <article id="shadow-body-tasas-tarifas"></article>
        </article>
        
        <article id="block-sub">
            <a href="../visor/notarias/${codigoOficina}.htm" class="button" toptions="title = Lista de Notar&iacute;as, effect = clip, layout = quicklook, shaded = 1">
            	<div id="block-body-notarias">
            		<h1>${no}</h1>
                </div>
			</a>
            <div id="block-body-comunicados">
                <div id="head-title-comunicados">
                    <h1>${co}</h1>
                </div>
                <div id="list-marquee" class="scroll-text">
	                <ul id="marqueeComunicados">
						<!--  LISTA  DE COMUNICADOS  -->
	                </ul>
                </div>
                
			</div> 
			<div id="block-body-canales">
                 <div id="head-title-canales">
                     <h1>${cmc}</h1>
                 </div>
                 <div class="block-left-canales">
                 	<figure class="icon-map-canales"></figure>
                 	<p>Ubica los canales mas cercanos</p>
                 	<a href="#" class="button-class">Ingresar</a>
                 </div>
                 <div class="block-center-canales"></div>
                 <ul class="block-right-canales">
                 	<li><a>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam tempus urna porttitor.</a></li>
                 	<li><a>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam tempus urna porttitor.</a></li>
                 </ul>
                
 			</div>
            <article id="shadow-body-notarias"></article>
            <!--<article id="shadow-body-comunicados-max"></article>-->
            <article id="shadow-body-comunicados"></article>
            <article id="shadow-body-canales"></article>
        </article>
    </section>
    <!-- POPUP DETAILS -->
    <div id="details-notarias" class="portBox">
    </div>
        <div class="overlay" id="overlay">
		        
    	</div>
	<!-- FIN - POPUP DETAILS -->
</div>
<script>
$(function () {
  $('#list-marquee').scrollbox();
  $('#list-marquee-contain').scrollbox({
    linear: true,
    step: 1,
    delay: 0,
    speed: 100
  });
});
</script>

</body>

</html>