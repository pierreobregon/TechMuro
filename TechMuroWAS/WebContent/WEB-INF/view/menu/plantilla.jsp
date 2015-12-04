<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>:: Muro Tecnol&oacute;gico ::</title>
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<link href="../css/core.css" rel="stylesheet" type="text/css">
<link href="../css/style_wiziwig.css" rel="stylesheet" type="text/css">

<script type="text/javascript" src="../js/redips-table.js"></script>
<script type="text/javascript" src="../js/script.js"></script>
<script type="text/javascript" src="../js/nicEdit-latest.js"></script>

<script type="text/javascript" src="../js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="../js/calender/jquery-ui-1.10.4.custom.js"></script>
<!-- <script src="../js/jquery-ui-1.10.3.custom.min.js"></script> -->

<script type="text/javascript" src="../js/jquery.jqGrid.src.js"></script>
<script type="text/javascript" src="../js/grid.locale-es.js"></script>
<script type="text/javascript" src="../js/jquery.form.js"></script>
<script type="text/javascript" src="../js/file-validator.js"></script>

<script type="text/javascript" src="../js/bootstrap-3.0.3.min.js"></script>
<script type="text/javascript" src="../js/bootstrap-multiselect.js"></script>
<script type="text/javascript" src="../js/prettify.js"></script>

<script type="text/javascript" src="../js/colResizable-1.3.min.js"></script>

<script src="../js/jquery.validationEngine-en.js" type="text/javascript" charset="utf-8"></script>
<script src="../js/jquery.validationEngine.js" type="text/javascript"
charset="utf-8"></script>

<script type="text/javascript" src="../js/load/js/demo.js"></script>
<script type="text/javascript" src="../js/load/js/jquery.nimble.loader.js"></script>
<link rel="stylesheet" href="../js/load/style/loader.css" media="screen" type="text/css" />


<!-- <script type="text/javascript" src="../js/jquery.datepicker.es.js"></script> -->

<script src="../js/portBox.slimscroll.min.js"></script>

<script src="../js/URL_DD_roundies.js"></script>

<!--[if lt IE 9]>
<script src="../js/html5.js"></script>
<script src="../js/json3.js"></script>
<![endif]-->

<script type="text/javascript">
	$(document).ready(function() {
		$("#header").load("../login/header.htm", function() {
			$("#body-section").load("../login/bienvenida.htm");
		});
		
		
		// INIT MENU ACORDION
		
			$('#main-primary > div > a').click(function() {
				$('#main-primary div a').removeClass('menu_opc_selected');
				$('#main-primary div div a').removeClass('menu_subOpc_selected');
				$(this).addClass('menu_opc_selected');
			});
			
			$('#main-primary > div > div > a').click(function() {
				$('#main-primary div a').removeClass('menu_opc_selected');
				$('#main-primary div div a').removeClass('menu_subOpc_selected');
				$(this).addClass('menu_subOpc_selected');
			});
		
		// END MENU ACORDION
		
		
	});

	function enviarPeticion(url) {
		$("body").nimbleLoader("show", {
		      position             : "fixed",
		      loaderClass          : "loading_bar_body",
		      debug                : true,
		      speed                : 700,
		      hasBackground        : true,
		      zIndex               : 999,
		      backgroundColor      : "#34383e",
		      backgroundOpacity    : 0.5
		    });
		
		$('#body-section').load(url);
		setTimeout(function hideGlobalLoader(){
		      $("body").nimbleLoader("hide");
		    }, 500);
		
		return false;
	}
	function validaSoloNumeros() {
		 if ((event.keyCode < 48) || (event.keyCode > 57)) 
		  event.returnValue = false;
		}
		
	function validaSoloNumerosYPuntos() {
		if (event.keyCode != 46 && (event.keyCode < 48) || (event.keyCode > 57)) 
		  event.returnValue = false;
		}	
		
		function prueba(evt) {
  evt = evt || window.event;
  var charCode = evt.which || evt.keyCode;
  var charStr = String.fromCharCode(charCode);
  alert(evt.keyCode);
}

</script>
<style>
	.example:target {
		color:#000 !important";
	}	
</style>

</head>

<body>
	<div id="container">

		<!-- Header -->
		<header id="header"> </header>

		<!-- Main -->
		<nav id="nav-main">
			<div id="main-primary">
				<div class="list-group panel">
					<c:forEach items="${__MENU_SISTEMA__}" var="menu" >
						<a href="#grupo${menu.codMenu}" data-toggle="collapse" >
							<div class="${menu.urlIcon}"></div>${menu.desMenu}<span class="spanCollapseMenu glyphicon glyphicon-chevron-down"></span>
						</a>
						<div class="collapse" id="grupo${menu.codMenu}">
							<c:forEach items="${menu.opcionMenus}" var="opcion" >
								<a href="#" 
								onclick="${opcion.url}"><div class="${opcion.urlIcon}"></div>${opcion.desOpcMenu}</a>
							</c:forEach>
					    </div>
					</c:forEach>
				</div>
				<li id="idAficheMenu">
					<a href="../afiche.htm" onclick="return enviarPeticion('../afiche.htm');" class="example">
						<div class="afiche-icon"></div> Afiches </a>
				</li>
				<li id="idTarifarioMenu" class="no-hover"><a>
						<div class="tarifario-icon"></div>Tarifario
				</a>
					<ul>
						<li><a href="../tarifario/producto/list.htm"
							onclick="return enviarPeticion('../tarifario/producto/list.htm');"><div
									class="producto-icon"></div>Producto/Servicio</a></li>
						<li><a href="../tarifario/capitulo/list.htm"
							onclick="return enviarPeticion('../tarifario/capitulo/list.htm');"><div
									class="capitulo-icon"></div>Cap&iacute;tulo</a></li>
						<li><a href="../tarifario/subcapitulo/list.htm"
							onclick="return enviarPeticion('../tarifario/subcapitulo/list.htm');"><div
									class="subcapitulo-icon"></div>Sub Cap&iacute;tulo</a></li>
						<li><a href="../tarifario/rubro/list.htm"
							onclick="return enviarPeticion('../tarifario/rubro/list.htm');"><div
									class="rubros-icon"></div>Rubros</a></li>
						<li><a href="../tarifario/categoria/list.htm"
							onclick="return enviarPeticion('../tarifario/categoria/list.htm');"><div
									class="categorias-icon"></div>Categor&iacute;as</a></li>
						<li><a href="../tarifario/transaccion/list.htm"
							onclick="return enviarPeticion('../tarifario/transaccion/list.htm');"><div
									class="transacciones-icon"></div>Transacciones</a></li>
						<li><a href="../listAllAvi.htm" onclick="return enviarPeticion('../listAllAvi.htm');"><div class="rubros-icon"></div>Avisos Importantes</a></li>
					</ul></li>
				<li id="idNotariaMenu"><a href="../notaria/list.htm"
					onclick="return enviarPeticion('../notaria/list.htm');">
						<div class="notarias-icon"></div>Notar&iacute;as
				</a></li>
				<li id="idComunicadoMenu"><a href="../comunicado.htm"
					onclick="return enviarPeticion('../comunicado.htm');">
						<div class="comunicados-icon"></div>Comunicados
				</a></li>
				<li id="idVariableMenu"  class="no-hover"><a>
						<div class="variablesgen-icon"></div>Variables Generales
				</a>
					<ul>
						<li><a href="../variables.htm"
							onclick="return enviarPeticion('../variables.htm');"><div
									class="variables-icon"></div>Variables</a></li>
						<li><a href="../etiquetas.htm"
							onclick="return enviarPeticion('../etiquetas.htm');"><div
									class="etiquetas-icon"></div>Etiquetas</a></li>
					</ul></li>
				<li id="idOficinaMenu"><a href="../oficinas.htm"
					onclick="return enviarPeticion('../oficinas.htm');">
						<div class="oficinas-icon"></div>Oficinas
				</a></li>
				<li id="idReporteMenu"><a href="../reporte.htm"
					onclick="return enviarPeticion('../reporte.htm');">
						<div class="reportes-icon"></div>Reportes
				</a></li>
			</div>
		</nav>
		
	     
	
		

	

		<!-- Body -->
		<section id="body-section"></section>

	</div>

</body>
</html>