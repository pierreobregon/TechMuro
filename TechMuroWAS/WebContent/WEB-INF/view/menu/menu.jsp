<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>:: Muro Tecnol&oacute;gico ::</title>
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
	<ul id="main-primary">
			<li id="idAficheMenu">
				<a href="../afiche.htm" onclick="return enviarPeticion('../afiche.htm');">
					<div class="afiche-icon"/>Afiches
				</a>
			</li>
			<li id="idTarifarioMenu">
				<a href="#" onclick="return enviarPeticion('#');">
				
				<div class="tarifario-icon"></div>Tarifario</a>
					<ul>
						<li><a href="../tarifario/producto/list.htm" onclick="return enviarPeticion('../tarifario/producto/list.htm');"><div class="producto-icon"></div>Producto/Servicio</a></li>
						<li><a href="../tarifario/capitulo/list.htm" onclick="return enviarPeticion('../tarifario/capitulo/list.htm');"><div class="capitulo-icon"></div>Cap&iacute;tulo</a></li>
						<li><a href="../tarifario/subcapitulo/list.htm" onclick="return enviarPeticion('../tarifario/subcapitulo/list.htm');"><div class="subcapitulo-icon"></div>Sub Cap&iacute;tulo</a></li>
						<li><a href="../tarifario/rubro/list.htm" onclick="return enviarPeticion('../tarifario/rubro/list.htm');"><div class="rubros-icon"></div>Rubros</a></li>
						<li><a href="../tarifario/categoria/list.htm" onclick="return enviarPeticion('../tarifario/categoria/list.htm');"><div class="categorias-icon"></div>Categor&iacute;as</a></li>
						<li><a href="../tarifario/transaccion/list.htm" onclick="return enviarPeticion('../tarifario/transaccion/list.htm');"><div class="transacciones-icon"></div>Transacciones</a></li>
						<li><a href="../listAllAvi.htm" onclick="return enviarPeticion('../listAllAvi.htm');"><div class="rubros-icon"></div>Avisos Importantes</a></li>
					</ul>
			</li>
			<li id="idNotariaMenu">
				<a href="../notaria/list.htm" onclick="return enviarPeticion('../notaria/list.htm');">
					<div class="notarias-icon"></div>Notar&iacute;as
				</a>
			</li>
			<li id="idComunicadoMenu">
				<a href="../comunicado.htm" onclick="return enviarPeticion('../comunicado.htm');">
					<div class="comunicados-icon"></div>Comunicados
				</a>
			</li>
			<li id="idVariableMenu">
				<a href="#" onclick="return enviarPeticion('#');">
					<div class="variablesgen-icon"></div>Variables Generales</a>
					<ul>
						<li><a href="../variables.htm" onclick="return enviarPeticion('../variables.htm');"><div class="variables-icon"></div>Variables</a></li>
						<li><a href="../etiquetas.htm" onclick="return enviarPeticion('../etiquetas.htm');"><div class="etiquetas-icon"></div>Etiquetas</a></li>
					</ul>
			</li>
			<li id="idOficinaMenu">
				<a href="../oficinas.htm" onclick="return enviarPeticion('../oficinas.htm');">
					<div class="oficinas-icon"></div>Oficinas</a>
			</li>
			<li id="idReporteMenu">
				<a href="../listAllemp.htm" onclick="return enviarPeticion('../listAllemp.htm');">
					<div class="reportes-icon"></div>Reportes</a>
			</li>
			<li id="idPerfilMenu">
				<a href="perfiles/perfiles.jsp" onclick="return enviarPeticion('perfiles/perfiles.jsp');">
					<div class="perfiles-icon"></div>Perfiles de Usuario</a>
			</li>
	</ul>
</body>
</html>