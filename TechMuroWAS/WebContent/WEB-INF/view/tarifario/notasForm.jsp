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


(function($){
	  $.isBlank = function(obj){
	    return(!obj || $.trim(obj) === "");
	  };
	})(jQuery);
	
	
	var mydataForm = [];
	
	function verEditor(){
		var editorSup, editorInf;
		editorSup = new nicEditor({buttonList : ['bold','italic','underline','strikeThrough','subscript','superscript','html', 'left', 'right', 'center', 'justify', 'ol', 'ul']});
		editorInf = new nicEditor({buttonList : ['bold','italic','underline','strikeThrough','subscript','superscript','html', 'left', 'right', 'center', 'justify', 'ol', 'ul']});
		editorSup.setPanel('panel');
		editorInf.setPanel('panel-2');
		editorSup.addInstance("descSuperior");
		editorInf.addInstance("descInferior");
	}
	
	
	$(document).ready(function() {
		window.prettyPrint() && prettyPrint();
		verEditor();
	});
	
	function cerrar() {
		$("#box").animate({'top':'5%'},500,function(){
            $("#overlay").fadeOut('fast');
            $("#capituloForm").innerHtml = "";
        });
	}
	
	function validarDatosNotasCompleto(tit,desc){
		
		return ($.trim(tit) != ''  &&  $.trim(desc) != '') ||
		($.trim(tit) == ''  &&  $.trim(desc) == '');
	}
	
	function notasAgregar(){
	
		var tituloSup = $('#tituloIdNotaSuperior');
		var descSup = $('#descSuperior');
		var tituloInf = $('#tituloIdNotaInferior');
		var descInf = $('#descInferior');
		

		if(!validarDatosNotasCompleto(tituloSup.val(), descSup.text())){
			alert("Los campos título superior y descripción superior deben estar completos o ambos en blanco");
			return;
		}
		
		if(!validarDatosNotasCompleto(tituloInf.val(), descInf.text())){
			alert("Los campos título inferior y descripción inferior deben estar completos o ambos en blanco");
			return;
		}

		
		$("#descripcionSup").val($("#descSuperior").html());
		$("#descripcionInf").val($("#descInferior").html());
		
		
		$.post("../tarifario/capitulo/agregarNotaSuperior.htm", $("#notaSuperior").serialize(), function(data){
			if($.trim(data)=="true"){
				$.post("../tarifario/capitulo/agregarNotaInferior.htm", $("#notaInferior").serialize(), function(data){
					if($.trim(data)=="true"){
						alert("Registrado correctamente");
						$("#box").animate({"top":"5%"},500,function(){
							$("#overlay").fadeOut("fast"); 
							$("#capituloForm").innerHtml = "";
					    });
						
						return false;
					}else{
						alert($.trim(data));
						return false;
					}
				}); 
				
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
				
				<div id="title-header-popup">Notas Superiores</div>
				
				<form:form method="post" action="../tarifario/producto/agregar.htm" commandName="notaSuperior" 
					id="notaSuperior" cssClass="scrollbar-2" onsubmit="return false;">
					<input type="hidden" name="idCapitulo" value="${idCapitulo}" />
					<form:hidden path="idnota"/>
					<ul id="body-popup">
						<li>T&iacute;tulo: </li>
						<li id="right-li">
							<form:input path="titulo" id="tituloIdNotaSuperior" type="text"
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
                            <div id="descSuperior" class="descNotas">${notaSuperior.descripcion}</div>
                            <form:textarea path="descripcion" id="descripcionSup" style="display:none;"></form:textarea>
                        </li>
					</ul>
				</form:form>
				<div id="title-header-popup2">Notas Inferiores</div>
				<form:form method="post" action="../tarifario/producto/agregar.htm" commandName="notaInferior" 
					id="notaInferior" cssClass="scrollbar-2"  onsubmit="return false;">
					<input type="hidden" name="idCapitulo" value="${idCapitulo}" />
					<form:hidden path="idnota"/>
					<ul id="body-popup">
						<li>T&iacute;tulo: </li>
						<li id="right-li">
							<form:input path="titulo" id="tituloIdNotaInferior" type="text"
							class="validate[required] input-popup-2" maxlength="50"/></li>
						<li>Notas: </li>
						<li id="right-li" class="descText" >
                        	<table id="toolbox" border="0" cellpadding="0" cellspacing="0">
								<tbody>
									<tr>
										<td colspan="5">
				                        	<div id="container-panel">
												<div id="panel-2" class="panelNotas"></div>
				                            </div>
										</td>
									</tr>
								</tbody>
							</table>
                            <div id="descInferior" class="descNotas">${notaInferior.descripcion}</div>
                            <form:textarea path="descripcion" id="descripcionInf" style="display:none;"></form:textarea>
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