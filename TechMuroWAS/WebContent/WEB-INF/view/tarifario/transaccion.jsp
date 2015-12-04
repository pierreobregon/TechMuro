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
	
	'<c:forEach items="${transaccionList}" var="lista">';
		mydata.push({idtransaccion:"${lista.idtransaccion}", nombre:"${fn:replace(lista.nombre, '\"', '\\\"')}", orden:"${lista.orden}"} );
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
			colNames : [ "ID","Nombre de Transaccion", "Orden", "Ver<br/>Detalle", "Editar", "Eliminar" ],
			colModel : [ {name : 'idtransaccion',index : 'name',sorttype : "int",align : "center",viewable : false, hidden : true,sortable:false},
			             {name : 'nombre',index : 'name',width : 260,sorttype : "string",align : "left",sortable:false},
			             {name : 'orden',index : 'tax',width : 72,align : "center",sorttype : "string",sortable:false},
			             {name : 'detalle',index : 'tax',width : 100,align : "center",sorttype : "string",sortable:false},
			             {name : 'editar',index : 'total',width : 72,align : "center",sorttype : "date",sortable:false},
			             {name : 'eliminar',index : 'total',width : 72,align : "center",sorttype : "float",sortable:false}
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
					be = "<div id=\"icon-edit\" onclick=\"openEditForm("+$("#"+cl+" td").first().text()+")\"></div>";
					se = "<div id=\"icon-eliminar\" onclick=\"elimina("+$("#"+cl+" td").first().text()+");\"></div>";
					or = 	"<div id=\"icon-order-up\" onclick=\"return up("+$("#"+cl+" td").first().text()+");\"/>"+
							"<div id=\"icon-order-down\" onclick=\"return down("+$("#"+cl+" td").first().text()+");\"/>";
					no = "<div id=\"icon-detail\" onclick=\"return openNotasForm("+$("#"+cl+" td").first().text()+");\"/>";
					de = "<div id=\"icon-detail\" onclick=\"return openDetalleForm("+$("#"+cl+" td").first().text()+");\"/>";
					
					jQuery("#list").jqGrid('setRowData', ids[i], {editar : be});
					jQuery("#list").jqGrid('setRowData', ids[i], {eliminar : se});
					jQuery("#list").jqGrid('setRowData', ids[i], {orden : or});
					jQuery("#list").jqGrid('setRowData', ids[i], {notas : no});
					jQuery("#list").jqGrid('setRowData', ids[i], {detalle : de});
				}

			}

		});
	}
	
	function up(id){
		$.post("../tarifario/transaccion/up.htm", {id:id}, function(data){
			if($.trim(data)=="true"){
				buscar();
			}
		});
		return false;
	}
	
	function down(id){
		$.post("../tarifario/transaccion/down.htm", {id:id}, function(data){
			if($.trim(data)=="true"){
				buscar();
			}
		});
		return false;
	}

	function buscar() {
		$.post("../tarifario/transaccion/buscar.htm", $("#buscaTransaccion").serialize(), function(data) {
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
     	$("#transaccionForm").load("../tarifario/transaccion/cargaForm.htm", $("#buscaTransaccion").serialize(), function(){
     		$('#overlay').fadeIn('fast',function(){
                $('#box').animate({'top':'20%'},500);
            });
     	});
     	return false;
	}
	
	function openDetalleForm(id){
     	$("#transaccionForm").load("../tarifario/transaccion/openDetalleForm.htm", {idTransaccion:id}, function(){
     		$('#overlay').fadeIn('fast',function(){
                $('#box').animate({'top':'10%'},500);
            });
     	});
     	return false;
	}
	
	function validaBoton(){
		if($("#categoriaList").val()!=""){
			$("#buscarTransaccion").removeAttr("disabled");
		}else{
			$("#buscarTransaccion").attr("disabled", "disabled");
		}
	}
	
	function openEditForm(id){
		$("#id").val(id);
		$("#transaccionForm").load("../tarifario/transaccion/cargaForm.htm", $("#buscaTransaccion").serialize(), function(){
			$("#id").val("");
			$('#overlay').fadeIn('fast',function(){
                $('#box').animate({'top':'20%'},500);
            });
     	});
     	return false;
    }
	
	function elimina(id){
		
		if(confirm("¿Está seguro que desea eliminar la Transacción?")){
			
			$.post("../tarifario/transaccion/eliminar.htm", {id:id}, function(data){
				if($.trim(data)=="true"){
					buscar();
				}else{
								
					if($.trim(data) == "traError01"){
						alert("Transacción ya está relacionada con este Rubro o Categoría");
						return false;
					}else if($.trim(data) == "traError02"){
						alert("Error al Insertar Transacción");
						return false;
					}else if($.trim(data) == "traError03"){
						alert("Error al editar transacción");
						return false;
					}else if($.trim(data) == "traError04"){
						alert("Error al Eliminar Transacción");
						return false;
					}
			
				}
			});
			
		}else{
			return false;
		}
		
	}
	
	function llenaProductos(tipoCliente, comboProducto, comboCapitulo, comboSubCapitulo, comboRubro, comboCategoria){
		$.post("../tarifario/producto/combo.htm", {tipoCliente:tipoCliente}, function(data) {

			var datos = eval(data);
			$("#"+comboProducto).empty();
			$("#"+comboCapitulo).empty();
			$("#"+comboSubCapitulo).empty();
			$("#"+comboRubro).empty();
			$("#"+comboCategoria).empty();
            
			var option = $(document.createElement('option'));
            option.text("--Seleccionar opción--");
            option.val("");
            $("#"+comboProducto).append(option);
            var option2 = $(document.createElement('option'));
            option2.text("--Seleccionar opción--");
            option2.val("");
            $("#"+comboCapitulo).append(option2);
            var option3 = $(document.createElement('option'));
            option3.text("--Seleccionar opción--");
            option3.val("");
            $("#"+comboSubCapitulo).append(option3);
            var option4 = $(document.createElement('option'));
            option4.text("--Seleccionar opción--");
            option4.val("");
            $("#"+comboRubro).append(option4);
            var option5 = $(document.createElement('option'));
            option5.text("--Seleccionar opción--");
            option5.val("");
            $("#"+comboCategoria).append(option5);
            $(datos).each(function () {
                var option = $(document.createElement('option'));

                option.text(this.nombre);
                option.val(this.idproducto);

                $("#"+comboProducto).append(option);
            });
            
            validaBoton();
            
		});
	}
	
	function llenaCapitulos(id, comboCapitulo, comboSubCapitulo, comboRubro, comboCategoria){
		$.post("../tarifario/capitulo/combo.htm", {id:id}, function(data) {

			var datos = eval(data);
			$("#"+comboCapitulo).empty();
			$("#"+comboSubCapitulo).empty();
			$("#"+comboRubro).empty();
			$("#"+comboCategoria).empty();
			
			var option = $(document.createElement('option'));
            option.text("--Seleccionar opción--");
            option.val("");
            $("#"+comboCapitulo).append(option);
            var option2 = $(document.createElement('option'));
            option2.text("--Seleccionar opción--");
            option2.val("");
            $("#"+comboSubCapitulo).append(option2);
            var option3 = $(document.createElement('option'));
            option3.text("--Seleccionar opción--");
            option3.val("");
            $("#"+comboRubro).append(option3);
            var option4 = $(document.createElement('option'));
            option4.text("--Seleccionar opción--");
            option4.val("");
            $("#"+comboCategoria).append(option4);
            $(datos).each(function () {
                var option = $(document.createElement('option'));

                option.text(this.nombre);
                option.val(this.idcapitulo);

                $("#"+comboCapitulo).append(option);
            });
            
            validaBoton();
            
		});
	}
	
	function llenaSubCapitulos(id, comboSubCapitulo, comboRubro, comboCategoria){
		$.post("../tarifario/subcapitulo/combo.htm", {id:id}, function(data) {

			var datos = eval(data);
			$("#"+comboSubCapitulo).empty();
			$("#"+comboRubro).empty();
			$("#"+comboCategoria).empty();
			
			var option = $(document.createElement('option'));
            option.text("--Seleccionar opción--");
            option.val("");
            $("#"+comboSubCapitulo).append(option);
            var option2 = $(document.createElement('option'));
            option2.text("--Seleccionar opción--");
            option2.val("");
            $("#"+comboRubro).append(option2);
            var option3 = $(document.createElement('option'));
            option3.text("--Seleccionar opción--");
            option3.val("");
            $("#"+comboCategoria).append(option3);
            $(datos).each(function () {
                var option = $(document.createElement('option'));

                option.text(this.nombre);
                option.val(this.idsubcapitulo);

                $("#"+comboSubCapitulo).append(option);
            });
            
            validaBoton();
            
		});
	}
	
	function llenaRubros(id, comboRubro, comboCategoria){
		$.post("../tarifario/rubro/combo.htm", {id:id}, function(data) {

			var datos = eval(data);
			$("#"+comboRubro).empty();
			$("#"+comboCategoria).empty();
			
			var option = $(document.createElement('option'));
            option.text("--Seleccionar opción--");
            option.val("");
            $("#"+comboRubro).append(option);
            var option2 = $(document.createElement('option'));
            option2.text("--Seleccionar opción--");
            option2.val("");
            $("#"+comboCategoria).append(option2);
            $(datos).each(function () {
                var option = $(document.createElement('option'));

                option.text(this.nombre);
                option.val(this.idrubro);

                $("#"+comboRubro).append(option);
            });
            
            validaBoton();
		});
	}
	
	function llenaCategorias(id, combo){
		$.post("../tarifario/categoria/combo.htm", {id:id}, function(data) {

			var datos = eval(data);
			$("#"+combo).empty();
			var option = $(document.createElement('option'));
            option.text("--Seleccionar opción--");
            option.val("");
            $("#"+combo).append(option);
            $(datos).each(function () {
                var option = $(document.createElement('option'));

                option.text(this.nombre);
                option.val(this.idcategoria);

                $("#"+combo).append(option);
            });
            
            validaBoton();
		});
	}
	
	function verTransaccion(id){
		
		$('#body-section').load("../tarifario/rubro/list.htm", {id:id}, function(data){
			
		});

	}
	
</script>
</head>
<body>
	<div id="block-grid">
		<div id="line-body-sup"></div>
		<div id="back-title"><h1>Tarifario > Producto/Servicio > Cap&iacute;tulo > Sub-Cap&iacute;tulo > Rubro > Categor&iacute;a ><span>Transacciones</span></h1></div>
		<div id="container-search">
			<form:form action="/tarifario/transaccion/buscar.htm" id="buscaTransaccion" onsubmit="return buscar();"
				method="post" modelAttribute="transaccion">
				<input type="hidden" id="id" name="id" />
				<ul>
					<li><h2>Tipo de Cliente:</h2></li>
					<li>
						<form:select path="categoria.rubro.subcapitulo.capitulo.producto.tipocliente" name="tipoCliente" id="tipoClienteList" 
						onchange="llenaProductos(this.value, 'productoList', 'capituloList', 'subCapituloList', 'rubroList', 'categoriaList');" cssClass="select-box">
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
						<form:select path="categoria.rubro.subcapitulo.capitulo.producto.idproducto" id="productoList" 
						onchange="llenaCapitulos(this.value,'capituloList', 'subCapituloList', 'rubroList', 'categoriaList');" cssClass="select-box">
							<form:option value="">--Seleccionar opción--</form:option>
							<c:forEach items="${productoList }" var="productos">
								<form:option value="${productos.idproducto }">${productos.nombre }</form:option>
							</c:forEach>
						</form:select>
					</li>
					<li><span></span></li>
					<li><h2>Cap&iacute;tulo:</h2></li>
					<li>
						<form:select path="categoria.rubro.subcapitulo.capitulo.idcapitulo" id="capituloList" 
						onchange="llenaSubCapitulos(this.value, 'subCapituloList', 'rubroList', 'categoriaList');" cssClass="select-box">
							<form:option value="">--Seleccionar opción--</form:option>
							<c:forEach items="${capituloList }" var="capitulos">
								<form:option value="${capitulos.idcapitulo }">${capitulos.nombre }</form:option>
							</c:forEach>
						</form:select>
					</li>
					<li><span></span></li>
					<li><h2>Sub-Cap&iacute;tulo:</h2></li>
					<li>
						<form:select path="categoria.rubro.subcapitulo.idsubcapitulo" id="subCapituloList" 
						onchange="llenaRubros(this.value, 'rubroList', 'categoriaList');" cssClass="select-box">
							<form:option value="">--Seleccionar opción--</form:option>
							<c:forEach items="${subCapituloList }" var="subCapitulos">
								<form:option value="${subCapitulos.idsubcapitulo }">${subCapitulos.nombre }</form:option>
							</c:forEach>
						</form:select>
					</li>
					<li><input type="submit" value="Buscar" id="buscarTransaccion"
						class="button-image-complete"/></li>
					<li><h2>Rubro:</h2></li>
					<li>
						<form:select path="categoria.rubro.idrubro" id="rubroList" onchange="llenaCategorias(this.value, 'categoriaList');" cssClass="select-box">
							<form:option value="">--Seleccionar opción--</form:option>
							<c:forEach items="${rubroList }" var="rubros">
								<form:option value="${rubros.idrubro}">${rubros.nombre}</form:option>
							</c:forEach>
						</form:select>
					</li>
					<li><span></span></li>
					<li><h2>Categoria:</h2></li>
					<li>
						<form:select path="categoria.idcategoria" id="categoriaList" onchange="validaBoton();" cssClass="select-box">
							<form:option value="">--Seleccionar opción--</form:option>
							<c:forEach items="${categoriaList }" var="categorias">
								<form:option value="${categorias.idcategoria}">${categorias.nombre}</form:option>
							</c:forEach>
						</form:select>
					</li>
					<li><span></span></li>
					<li><h2>Transacci&oacute;n:</h2></li>
					<li><form:input path="nombre" id="criterio" type="text" class="input-border"/></li>
					<li><span></span></li>
						
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
										<span class="icon-plus-button">Agregar Transacci&oacute;n</span>
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
<div id="transaccionForm"></div>
</body>
</html>