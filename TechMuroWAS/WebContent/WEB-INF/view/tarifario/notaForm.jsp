<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<link rel="stylesheet" href="../css/validationEngine.jquery.css" type="text/css">

<script type="text/javascript">
	var mydataForm = [];
	
	function verEditor(){
		var editorSup;
		editorSup = new nicEditor({buttonList : ['bold','italic','underline','strikeThrough','subscript','superscript','html', 'left', 'right', 'center', 'justify', 'ol', 'ul']});
		editorSup.setPanel('panel');
		editorSup.addInstance("desc");
	}
	
	
	$(document).ready(function() {
		window.prettyPrint() && prettyPrint();
		verEditor();
	});
	
	function cerrar() {
		$("#box").animate({'top':'5%'},500,function(){
            $("#overlay").fadeOut('fast');
            //$("#subCapituloForm").innerHtml = "";
        });
	}
	
	
	function validarDatosNotasCompleto(tit,desc){
		
		return ($.trim(tit) != ''  &&  $.trim(desc) != '') ||
		($.trim(tit) == ''  &&  $.trim(desc) == '');
	}
	
	
	function notasAgregar(){
		
		var titulo = $('#nombre');
		var desc = $('#desc');
	
		if(!validarDatosNotasCompleto(titulo.val(), desc.text())){
			alert("Los campos título y descripción deben estar completos o ambos en blanco");
			return;
		}
	
		
		$("#descripcion").val($("#desc").html());
		
		$.post("../tarifario/${opcion}/agregarNota.htm", $("#nota").serialize(), function(data){
			if($.trim(data)=="true"){
			    alert("Registrado correctamente");
				cerrar();
				return false;
			}else{
				alert($.trim(data));
				return false;
			}
		}); 
		return false;
	}
	
</script>
</head>
<body>
	<div id="container">
		<div class="overlay" id="overlay" style="display: none;">
			<div class="box" id="box">
				<a class="boxclose" id="boxclose" onclick="cerrar();"></a>
				
				<div id="title-header-popup">Notas</div>
				
				<form:form method="post" action="../tarifario/${opcion}/agregarNota.htm" commandName="nota" 
					id="nota" cssClass="scrollbar-2" onsubmit="return false;">
					<input type="hidden" name="id" value="${id}" />
					<form:hidden path="idnota"/>
					<ul id="body-popup">
						<li>T&iacute;tulo: </li>
						<li id="right-li">
							<form:input path="titulo" id="nombre" type="text"
							cssClass="validate[required] input-popup-2" maxlength="50"/></li>
						<li>Notas: </li>
						<li id="right-li" class="descText" >
                        	<table id="toolbox" border="0" cellpadding="0" cellspacing="0">
								<tbody>
									<tr>
										<td colspan="5">
				                        	<div id="container-panel">
												<div id="panel" class="panelNotas"></div>
				                            </div>
										</td>
									</tr>
								</tbody>
							</table>
							<div id="desc" class="descNotas">${nota.descripcion}</div>
							<form:textarea path="descripcion" id="descripcion" style="display:none;"></form:textarea>
                        </li>
					</ul>
				</form:form>
				
					<ul id="button-popup">
						<li><input type="button" value="Guardar"
							class="button-image-complete-gren" onclick="notasAgregar();"></li>
						<li><input type="button" value="Cancelar"
							class="button-image-complete" onclick="cerrar();">
						<li>
					</ul>
				
			</div>
		</div>
	</div>
</body>
</html>