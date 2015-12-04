<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<script>
	var grid;
	var mydata = [];
	
	'<c:forEach items="${subCapituloList}" var="lista">';
		mydata.push({idsubcapitulo:"${lista.idsubcapitulo}", nombre:"${fn:replace(lista.nombre, '\"', '\\\"')}", orden:"${lista.orden}"} );
	'</c:forEach>';
	
	$(document).ready(function() {
		grid = $("#list");
		llenarTabla();
		validaBoton();
	});

	function llenarTabla() {

		grid.jqGrid({
			datatype : 'local',
			data : mydata,
			colNames : [ "ID","Nombre de Sub-Capítulo", "Orden", "Ver<br/>Rubros", "Ver<br/>Notas", "Editar", "Eliminar", "Descripcion" ],
			colModel : [ {name : 'idsubcapitulo',index : 'name',sorttype : "int",align : "center",viewable : false, hidden : true,sortable:false}, 
			             {name : 'nombre',index : 'name',width : 220,sorttype : "string",align : "left",sortable:false},
			             {name : 'orden',index : 'name',width : 80,align : "center",sortable:false},
			             {name : 'rubros',index : 'tax',width : 60,align : "center",sorttype : "string",sortable:false},
			             {name : 'notas',index : 'tax',width : 60,align : "center",sorttype : "string",sortable:false},
			             {name : 'editar',index : 'total',width : 72,align : "center",sorttype : "date",sortable:false},
			             {name : 'eliminar',index : 'total',width : 72,align : "center",sorttype : "float",sortable:false},
			             {name : 'descripcion',index : 'total',width : 72,align : "center",sorttype : "float",viewable : false, hidden : true,sortable:false}
			],
			rowNum : 10,
			rowList : [ 5, 10, 20 ],
			pager : '#pager',
			gridview : true,
			rownumbers : false,
			sortname : 'id',
			viewrecords : false,
			sortorder : 'desc',
			width : 673,
			height : '100%',

			gridComplete : function() {
				var ids = jQuery("#list").jqGrid('getDataIDs');
				for (var i = 0; i < ids.length; i++) {
					var cl = ids[i];
					var rowData = $("#list").getRowData(cl);
					be = "<div id=\"icon-edit\" onclick=\"openEditForm("+rowData.idsubcapitulo+")\"></div>";
					se = "<div id=\"icon-eliminar\" onclick=\"elimina("+rowData.idsubcapitulo+");\"></div>";
					de = 	"<div id=\"icon-order-up\" onclick=\"return up("+rowData.idsubcapitulo+");\"/>"+
							"<div id=\"icon-order-down\" onclick=\"return down("+rowData.idsubcapitulo+");\"/>";
					no = "<div id=\"icon-detail\" onclick=\"return openNotasForm("+rowData.idsubcapitulo+");\"/>";
					ru = "<div id=\"icon-detail\" onclick=\"return verRubro("+rowData.idsubcapitulo+");\"/>";
					
					if(rowData.descripcion == 'NS'){
						$("#container-button-add").attr("onclick", "return alert('No puede agregar más Sub-Capítulos');");
						jQuery("#list").jqGrid('setRowData', ids[i], {rubros : ru});
						jQuery("#list").jqGrid('setRowData', ids[i], {orden : ""});
					}else{
						jQuery("#list").jqGrid('setRowData', ids[i], {editar : be});	
						jQuery("#list").jqGrid('setRowData', ids[i], {orden : de});
						jQuery("#list").jqGrid('setRowData', ids[i], {notas : no});
						jQuery("#list").jqGrid('setRowData', ids[i], {rubros : ru});
					}
					
					jQuery("#list").jqGrid('setRowData', ids[i], {eliminar : se});
					
				}

			}

		});
	}
	
	function up(id){
		$.post("../tarifario/subcapitulo/up.htm", {id:id}, function(data){
			if($.trim(data)=="true"){
				buscar();
			}
		});
		return false;
	}
	
	function down(id){
		$.post("../tarifario/subcapitulo/down.htm", {id:id}, function(data){
			if($.trim(data)=="true"){
				buscar();
			}
		});
		return false;
	}

	function buscar() {
		$.post("../tarifario/subcapitulo/buscar.htm", $("#buscaSubCapitulo").serialize(), function(data) {
			$("#list").jqGrid('clearGridData');
			mydata = eval(data), grid = $("#list");
			
			jQuery("#list").setGridParam({ data : mydata });
			jQuery("#list").trigger("reloadGrid");
			
			if(mydata.length<1){
				alert("No existen resultados a mostrar");
			}
		});
		return false;
	}
	
	function openForm(){
     	$("#subCapituloForm").load("../tarifario/subcapitulo/cargaForm.htm", $("#buscaSubCapitulo").serialize(), function(){
     		$('#overlay').fadeIn('fast',function(){
                $('#box').animate({'top':'25%'},500);
            });
     	});
     	return false;
	}
	
	function validaBoton(){
		
		
		
		if($("#capituloList").val()!=""){
		
			$("#buscarCapitulo").removeAttr("disabled");
		}else{
			
			$("#buscarCapitulo").attr("disabled", "disabled");
		}
		
		$("#container-button-add").attr("onclick", "return openForm();");
	}
	
	function openEditForm(id){
		
		$("#id").val(id);
		$("#subCapituloForm").load("../tarifario/subcapitulo/cargaForm.htm", $("#buscaSubCapitulo").serialize(), function(){
			$("#id").val("");
			$('#overlay').fadeIn('fast',function(){
                $('#box').animate({'top':'25%'},500);
            });
     	});
     	return false;
    }
	
	function elimina(id){
		
		if(confirm("¿Está; seguro que desea eliminar el Sub-Capítulo?")){
			
			$.post("../tarifario/subcapitulo/eliminar.htm", {id:id}, function(data){
				if($.trim(data)=="true"){
					buscar();
				}else{					
					
					if($.trim(data) == "subError01"){
						alert("No se puede agregar Sub Capítulos a este Capítulo");
						return false;
					}else if($.trim(data) == "subError02"){
						alert("Sub-Capítulo ya está relacionado con este Capítulo");
						return false;
					}else if($.trim(data) == "subError03"){
						alert("Error al Insertar Sub-Capítulo");
						return false;
					}else if($.trim(data) == "rubError04"){
						alert("Error al Editar Sub-Capítulo");
						return false;
					}else if($.trim(data) == "subError05"){
						alert("Sub-capítulo tiene rubros asociados");
						return false;
					}else if($.trim(data) == "subError06"){
						alert("Error al Eliminar eliminar SubCapítulo");
						return false;
					}
						
				}
			});
			
		}else{
			return false;
		}
		
	}
	
	function llenaProductos(tipoCliente, comboProducto, comboCapitulo){
		$.post("../tarifario/producto/combo.htm", {tipoCliente:tipoCliente}, function(data) {

			var datos = eval(data);
			$("#"+comboProducto).empty();
			$("#"+comboCapitulo).empty();
             
			var option = $(document.createElement('option'));
            option.text("--Seleccionar opción--");
            option.val("");
            $("#"+comboProducto).append(option);
            var option2 = $(document.createElement('option'));
            option2.text("--Seleccionar opción--");
            option2.val("");
            $("#"+comboCapitulo).append(option2);
            $(datos).each(function () {
                var option = $(document.createElement('option'));

                option.text(this.nombre);
                option.val(this.idproducto);

                $("#"+comboProducto).append(option);
            });
            validaBoton();
		});
	}
	
	function llenaCapitulos(id, combo){
		$.post("../tarifario/capitulo/combo.htm", {id:id}, function(data) {

			var datos = eval(data);
			$("#"+combo).empty();
			var option = $(document.createElement('option'));
            option.text("--Seleccionar opción--");
            option.val("");
            $("#"+combo).append(option);
            $(datos).each(function () {
                var option = $(document.createElement('option'));

                option.text(this.nombre);
                option.val(this.idcapitulo);

                $("#"+combo).append(option);
            });
            validaBoton();
		});
	}
	
	function openNotasForm(id){
     	$("#subCapituloForm").load("../tarifario/subcapitulo/cargaNota.htm", {id:id}, function(){
     		$('#overlay').fadeIn('fast',function(){
                $('#box').animate({'top':'25%'},500);
            });
     	});
     	return false;
	}
	
	function verRubro(id){
		
		$('#body-section').load("../tarifario/rubro/list.htm", {id:id}, function(data){
			
		});

	}
	
</script>
</head>
<body>
	<div id="block-grid">
		<div id="line-body-sup"></div>
		<div id="back-title"><h1>Tarifario > Producto/Servicio > Cap&iacute;tulo > <span>Sub-Cap&iacute;tulo</span></h1></div>
		<div id="container-search">
			<form:form action="/tarifario/subcapitulo/buscar.htm" id="buscaSubCapitulo" onsubmit="return buscar();"
				method="post" modelAttribute="subCapitulo">
				<input type="hidden" id="id" name="id" />
				<ul>
					<li><h2>Tipo de Cliente:</h2></li>
					<li>
						<form:select path="capitulo.producto.tipocliente" name="tipoCliente" id="tipoClienteList" 
						onchange="llenaProductos(this.value, 'productoList', 'capituloList', 'subCapituloList');" cssClass="select-box">
							<form:option value="">--Seleccionar opción--</form:option>
							<c:forEach items="${tipoCliente }" var="cliente">
								<form:option value="${cliente.codigo }">${cliente.valor }</form:option>
							</c:forEach>
						</form:select>
						<c:if test="${empty tipoCliente }">
							 - No existe datos de Tipo de Cliente
						</c:if>
					</li>
					<li><span></span></li>
					<li><h2>Producto/Servicio:</h2></li>
					<li>
						<form:select path="capitulo.producto.idproducto" id="productoList" onchange="llenaCapitulos(this.value,'capituloList');" cssClass="select-box">
							<form:option value="">--Seleccionar opción--</form:option>
							<c:forEach items="${productoList }" var="productos">
								<form:option value="${productos.idproducto }">${productos.nombre }</form:option>
							</c:forEach>
						</form:select>
						<%-- <c:if test="${empty productoList }">
							 - No existe datos de Producto
						</c:if> --%>
					</li>
					<li><span></span></li>
					<li><h2>Cap&iacute;tulo:</h2></li>
					<li>
						<form:select path="capitulo.idcapitulo" id="capituloList" onchange="validaBoton();" cssClass="select-box">
							<form:option value="">--Seleccionar opción--</form:option>
							<c:forEach items="${capituloList }" var="capitulos">
								<form:option value="${capitulos.idcapitulo }">${capitulos.nombre }</form:option>
							</c:forEach>
						</form:select>
						<%-- <c:if test="${empty productoList }">
							 - No existe datos de Producto
						</c:if> --%>
					</li>
					<li><span></span></li>
					<li><h2>Sub-Cap&iacute;tulo:</h2></li>
					<li><form:input path="nombre" id="criterio" type="text" class="input-border"/></li>
					<li><span></span></li>
					<div class="middle-align-button"><input type="submit" value="Buscar" id="buscarCapitulo"
						class="button-image-complete"/></div>
						
				</ul>
				
			</form:form>
		</div>

		<div id="container-grid">
			<div class="table-grid">
				<div class="tr-grid">
					<div class="td-grid">
						<p>Resultados de la b&uacute;squeda...</p>
					</div>
					<div class="td-grid">
						<ul id="block-grid-right">
							<li>
								<a href="#" id="container-button-add" onclick="return openForm();">
									<div id="button-add-left"></div>
									<div id="button-add-center">
										<span class="icon-plus-button">Agregar Sub-Cap&iacute;tulo</span>
									</div>
									<div id="button-add-right"></div>
								</a>
							</li>
						</ul>
					</div>
				</div>
			</div>
			<!-- Grilla -->
			<table id="list"></table>
			<div id="pager"></div>
			<!-- Find Grilla -->
		</div>
	</div>
<div id="subCapituloForm"></div>
</body>
</html>