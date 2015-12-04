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
	
	'<c:forEach items="${rubroList}" var="lista">';
		mydata.push({idrubro:"${lista.idrubro}", nombre:"${fn:replace(lista.nombre, '\"', '\\\"')}", descripcion:"${fn:replace(lista.descripcion, '\"', '\\\"')}", orden:"${lista.orden}"} );
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
			colNames : [ "ID","Nombre de Rubro", "Orden", "Ver<br/>Categorias", "Ver<br/>Transacciones", "Ver<br/>Notas", "Editar", "Eliminar", "Descripcion" ],
			colModel : [ {name : 'idrubro',index : 'name',sorttype : "int",align : "center",viewable : false, hidden : true,sortable:false}, 
			             {name : 'nombre',index : 'name',width : 180,sorttype : "string",align : "left",sortable:false},
			             {name : 'orden',index : 'name',width : 80,align : "center",sortable:false},
			             {name : 'categorias',index : 'tax',width : 90,align : "center",sorttype : "string",sortable:false},
			             {name : 'transacciones',index : 'tax',width : 100,align : "center",sorttype : "string",sortable:false},
			             {name : 'notas',index : 'tax',width : 60,align : "center",sorttype : "string",sortable:false},
			             {name : 'editar',index : 'total',width : 72,align : "center",sorttype : "date",sortable:false},
			             {name : 'eliminar',index : 'total',width : 72,align : "center",sorttype : "float",sortable:false},
			             {name : 'descripcion',index : 'total',width : 72,align : "center",sorttype : "float", viewable : false, hidden : true,sortable:false}
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
					de = 	"<div id=\"icon-order-up\" onclick=\"return up("+$("#"+cl+" td").first().text()+");\"/>"+
							"<div id=\"icon-order-down\" onclick=\"return down("+$("#"+cl+" td").first().text()+");\"/>";
					no = "<div id=\"icon-detail\" onclick=\"return openNotasForm("+$("#"+cl+" td").first().text()+");\"/>";
					ct = "";
					tr = "";
					var rowData = $("#list").getRowData(cl);
					
					if(rowData.descripcion==''){
						ct = "<div id=\"icon-detail\" onclick=\"return verCategoria("+$("#"+cl+" td").first().text()+");\"/>";
						tr = "<div id=\"icon-detail\" onclick=\"return verTransaccion("+$("#"+cl+" td").first().text()+");\"/>";
					}else{
						if(rowData.descripcion=='CA'){
							ct = "<div id=\"icon-detail\" onclick=\"return verCategoria("+$("#"+cl+" td").first().text()+");\"/>";
						}
						if(rowData.descripcion=='TR'){
							tr = "<div id=\"icon-detail\" onclick=\"return verTransaccion("+$("#"+cl+" td").first().text()+");\"/>";
						}
					}
					
					
					
					jQuery("#list").jqGrid('setRowData', ids[i], {editar : be});
					jQuery("#list").jqGrid('setRowData', ids[i], {eliminar : se});
					jQuery("#list").jqGrid('setRowData', ids[i], {orden : de});
					jQuery("#list").jqGrid('setRowData', ids[i], {notas : no});
					jQuery("#list").jqGrid('setRowData', ids[i], {categorias : ct});
					jQuery("#list").jqGrid('setRowData', ids[i], {transacciones : tr});
				}

			}

		});
	}
	
	function up(id){
		$.post("../tarifario/rubro/up.htm", {id:id}, function(data){
			if($.trim(data)=="true"){
				buscar();
			}
		});
		return false;
	}
	
	function down(id){
		$.post("../tarifario/rubro/down.htm", {id:id}, function(data){
			if($.trim(data)=="true"){
				buscar();
			}
		});
		return false;
	}

	function buscar() {
		$.post("../tarifario/rubro/buscar.htm", $("#buscaRubro").serialize(), function(data) {
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
     	$("#rubroForm").load("../tarifario/rubro/cargaForm.htm", $("#buscaRubro").serialize(), function(){
     		$('#overlay').fadeIn('fast',function(){
                $('#box').animate({'top':'5%'},500);
            });
     	});
     	return false;
	}
	
	function validaBoton(){
		if($("#subCapituloList").val()!=""){
			$("#buscarCapitulo").removeAttr("disabled");
		}else{
			$("#buscarCapitulo").attr("disabled", "disabled");
		}
	}
	
	function openEditForm(id){
		$("#id").val(id);
		$("#rubroForm").load("../tarifario/rubro/cargaForm.htm", $("#buscaRubro").serialize(), function(){
			$("#id").val("");
			$('#overlay').fadeIn('fast',function(){
                $('#box').animate({'top':'5%'},500);
            });
     	});
     	return false;
    }
	
	function elimina(id){
		
		if(confirm("¿Está seguro que desea eliminar el Rubro?")){
			
			$.post("../tarifario/rubro/eliminar.htm", {id:id}, function(data){
				if($.trim(data)=="true"){
					buscar();
				}else{
					
					if($.trim(data) == "rubError04"){
						alert("Rubro tiene Categorías asociadas");
						return false;
					}else if($.trim(data) == "rubError05"){
						alert("Error al Eliminar Rubro");
						return false;
					}
					
				}
			});
			
		}else{
			return false;
		}
		
	}
	
	function llenaProductos(tipoCliente, comboProducto, comboCapitulo, comboSubCapitulo){
		$.post("../tarifario/producto/combo.htm", {tipoCliente:tipoCliente}, function(data) {

			var datos = eval(data);
			$("#"+comboProducto).empty();
			$("#"+comboCapitulo).empty();
			$("#"+comboSubCapitulo).empty();
             
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
            $(datos).each(function () {
                var option = $(document.createElement('option'));

                option.text(this.nombre);
                option.val(this.idproducto);

                $("#"+comboProducto).append(option);
            });
            
            validaBoton();
            
		});
	}
	
	function llenaCapitulos(id, comboCapitulo, comboSubCapitulo){
		$.post("../tarifario/capitulo/combo.htm", {id:id}, function(data) {

			var datos = eval(data);
			$("#"+comboCapitulo).empty();
			$("#"+comboSubCapitulo).empty();
             
			var option = $(document.createElement('option'));
            option.text("--Seleccionar opción--");
            option.val("");
            $("#"+comboCapitulo).append(option);
            var option2 = $(document.createElement('option'));
            option2.text("--Seleccionar opción--");
            option2.val("");
            $("#"+comboSubCapitulo).append(option2);
            $(datos).each(function () {
                var option = $(document.createElement('option'));

                option.text(this.nombre);
                option.val(this.idcapitulo);

                $("#"+comboCapitulo).append(option);
            });
            
            validaBoton();
            
		});
	}
	
	function llenaSubCapitulos(id, combo){
		$.post("../tarifario/subcapitulo/combo.htm", {id:id}, function(data) {

			var datos = eval(data);
			$("#"+combo).empty();
			var option = $(document.createElement('option'));
            option.text("--Seleccionar opción--");
            option.val("");
            $("#"+combo).append(option);
            $(datos).each(function () {
                var option = $(document.createElement('option'));

                option.text(this.nombre);
                option.val(this.idsubcapitulo);

                $("#"+combo).append(option);
            });
            
            validaBoton();
            
		});
	}
	
	function openNotasForm(id){
     	$("#rubroForm").load("../tarifario/rubro/cargaNota.htm", {id:id}, function(){
     		$('#overlay').fadeIn('fast',function(){
                $('#box').animate({'top':'5%'},500);
            });
     	});
     	return false;
	}
	
	function verCategoria(id){
		$('#body-section').load("../tarifario/categoria/list.htm", {id:id}, function(data){
			
		});
	}
	function verTransaccion(id){
		
		$('#body-section').load("../tarifario/transaccion/list.htm", {id:id, ct:'NS'}, function(data){
			
		});

	}
	
</script>
</head>
<body>
	<div id="block-grid">
		<div id="line-body-sup"></div>
		<div id="back-title"><h1>Tarifario > Producto/Servicio > Cap&iacute;tulo > Sub-Cap&iacute;tulo > <span>Rubro</span></h1></div>
		<div id="container-search">
			<form:form action="/tarifario/subcapitulo/buscar.htm" id="buscaRubro" onsubmit="return buscar();"
				method="post" modelAttribute="rubro">
				<input type="hidden" id="id" name="id" />
				<ul>
					<li><h2>Tipo de Cliente:</h2></li>
					<li>
						<form:select path="subcapitulo.capitulo.producto.tipocliente" name="tipoCliente" id="tipoClienteList" 
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
						<form:select path="subcapitulo.capitulo.producto.idproducto" id="productoList" onchange="llenaCapitulos(this.value,'capituloList', 'subCapituloList');" cssClass="select-box">
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
						<form:select path="subcapitulo.capitulo.idcapitulo" id="capituloList" onchange="llenaSubCapitulos(this.value, 'subCapituloList');" cssClass="select-box">
							<form:option value="">--Seleccionar opción--</form:option>
							<c:forEach items="${capituloList }" var="capitulos">
								<form:option value="${capitulos.idcapitulo }">${capitulos.nombre }</form:option>
							</c:forEach>
						</form:select>
						<%-- <c:if test="${empty productoList }">
							 - No existe datos de Producto
						</c:if> --%>
					</li>
					<li><input type="submit" value="Buscar" id="buscarCapitulo"
						class="button-image-complete"/></li>
					<li><h2>Sub-Cap&iacute;tulo:</h2></li>
					<li>
						<form:select path="subcapitulo.idsubcapitulo" id="subCapituloList" onchange="validaBoton();" cssClass="select-box">
							<form:option value="">--Seleccionar opción--</form:option>
							<c:forEach items="${subCapituloList }" var="subCapitulos">
								<form:option value="${subCapitulos.idsubcapitulo }">${subCapitulos.nombre }</form:option>
							</c:forEach>
						</form:select>
						<%-- <c:if test="${empty productoList }">
							 - No existe datos de Producto
						</c:if> --%>
					</li>
					<li><span></span></li>
					<li><h2>Rubro:</h2></li>
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
										<span class="icon-plus-button">Agregar Rubro</span>
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
<div id="rubroForm"></div>
</body>
</html>