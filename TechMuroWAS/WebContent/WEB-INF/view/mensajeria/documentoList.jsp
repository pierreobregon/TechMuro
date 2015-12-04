<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<script>
	var grid;
	var mydata = [];
	$(document).ready(function() {
		mydata = eval('${lstDocumento}');
		dataGridIncidencia = eval('${lstIncidencias}');
		gridDocumentos = $("#gridDocumentos");
		gridIncidencias = $("#gridIncidencias");
		var listaTamanio = "${listaSize}";
		if(listaTamanio == 0){
			alert("No existen resultados a mostrar");
		}
		llenarTablaDocumentos();
		llenarGridIncidencias();
		
	});
	
	function llenarTablaDocumentos() {
		
		gridDocumentos.jqGrid({
			datatype : 'local',
			data : mydata,
			colNames : [ "","","Tipo Documento","Nro de Documento"],
			colModel : [ {name: 'codDocumento', width: 30, fixed: true, align: 'center', resizable: false, sortable: false,
						                        formatter: function (cellValue, option) {
						                        	return '<input type="radio" name="radio_' + option.gid + '" onclick="filtrarIncidencias(\''+cellValue+'\');" />';
						                        }
						 },
                    { name : 'codDocumento', index : 'name', sorttype : "int", align : "center", viewable : false, hidden : true,sortable:false},
					{name : 'codTipDocumento', index : 'name', width : 124, align : "center" ,sortable:false},
					{name : 'desNumDocumento', index : 'name', width : 214, align : "left" ,sortable:false}
			],

			 rowNum:10,
             rowList:[5,10,20],
             pager: '#pager',
             gridview:true,
             rownumbers:false,
             sortname: 'id',
             viewrecords: false,
             sortorder: 'desc',
				
			width: 672,
            height: '100%'

		});
		
	}
	
	function filtrarIncidencias(codDocumento){
		$("#hdnCodDocumentoSeleccionado").val(codDocumento);
		listarIncidencias();
	}
	
	function listarIncidencias(){
		codDocumento = $("#hdnCodDocumentoSeleccionado").val();
		$.post("../mensajeria/listIncidencias.htm",{ prmCodDocumento: codDocumento }, function(data) {
			dataGridIncidencia = eval(data);
			
			var listaTamanio = eval('${listaIncidenciasSize}');
			if(listaTamanio == 0){
				alert("No existen resultados a mostrar");
			}
			$("#gridIncidencias").jqGrid('clearGridData');
			jQuery("#gridIncidencias").setGridParam({
				data : eval(dataGridIncidencia)
			});
			jQuery("#gridIncidencias").trigger("reloadGrid");
			
		});
	}
	
	function llenarGridIncidencias() {
		gridIncidencias.jqGrid({
			datatype : 'local',
			data : dataGridIncidencia,
			colNames : [ "","Empresa Courier","Nombre Mensajero","Estado","Fecha", "Editar"],
			colModel : [ { name : 'codIncMensajeria', index : 'name', align : "center",  hidden : true, sortable:false},
                    {name : 'courier.desCourier', index : 'name', width : 150, align : "center" ,sortable:false},
					{name : 'desNomMensajro', index : 'name', width : 250, align : "center" ,sortable:false},
					{name : 'indEstMensajeria', index : 'name', width : 150, align : "left" ,sortable:false,
								formatter: function (cellValue, option) {
		                            return (cellValue==1)?'Entregado':'No se entregó';
		                        }
					},
					{name : 'fecCreacionFormato', index : 'name', width : 150, align : "left" ,sortable:false},
					{name:'editar',index:'total', width:72,align:"center",  sortable:false}
			],

			 rowNum:10,
             rowList:[5,10,20],
             pager: '#pagerIncidencia',
             gridview:true,
             rownumbers:false,
             sortname: 'id',
             viewrecords: false,
             sortorder: 'desc',
				
			width: 672,
            height: '100%',
            gridComplete : function() {
				var ids = $("#gridIncidencias").jqGrid('getDataIDs');
				for (var i = 0; i < ids.length; i++) {
					
					var cl = ids[i];
					var idMensajeIncidencia = jQuery("#gridIncidencias").jqGrid('getRowData', ids[i]).codIncMensajeria;
					ed = "<div id=\"icon-edit\" onclick=\"openRegistroIncidenciaForm("+idMensajeIncidencia+");\"></div>";
					jQuery("#gridIncidencias").jqGrid('setRowData', ids[i], {
						editar : ed
					});
				}
			}
		});
		
	}
	
	function openRegistroIncidenciaForm(codIncidencia){
		codDocumento = $("#hdnCodDocumentoSeleccionado").val();
		if(codDocumento == ''){
			alert('Seleccione un Documento antes de registrar una Incidencia');
			return false;
		}
		$("#incidenciaForm").load("../mensajeria/cargaFormIncidencia.htm",{prmCodIncMensajeria:codIncidencia, prmCodDocumento:codDocumento}, function(){
     		$('#overlay').fadeIn('fast',function(){
                $('#box').animate({'top':'5%'},500);
            });
     	});
     	return false;
     }
</script>
</head>
<body>
	<div id="block-grid">
            <div id="line-body-sup"></div>
            <div id="back-title">
                <h1>B&uacute;squeda de Documentos para registro de Mensajer&iacute;a</h1>
            </div>

            <div id="container-search">
            	<form action="" id="buscaNotaria" onsubmit="return buscar();" method="post">
                    <ul>
                        <li><h2>Nombre :</h2></li>
                        <li><input id="criterioNot" name="criterio" type="text" class="input-border"></li>
                        <li><input id="buscarDocumentos" type="submit" value="Buscar" class="button-image-complete">
                        </li>
                    </ul>
                </form>
            </div>
            
            <div id="container-grid">    
            	<input type="hidden" id="hdnCodDocumentoSeleccionado" />
                <!-- Grilla -->
                <h3>Documentos</h3>
                <table id="gridDocumentos"></table>
                <div id="pager"></div>
            <!-- Find Grilla -->
            	<br/><br/>
            	<h3>Mensajes Incidencias</h3>
            	<br/>
            	<table id="gridIncidencias"></table>
	            <div id="pagerIncidencia"></div>
	             
	            <div class="table-grid">
                    <div class="tr-grid">
                        <div class="td-grid" >
                            <p id="resultadosDocumentos" ></p>
                        </div>
                        <div class="td-grid">
                            <ul id="block-grid-right">
                                <li>
                                    <a href="../mensajeria/cargaFormIncidencia.htm" id="container-button-add" onclick="return openRegistroIncidenciaForm();" ><div id="button-add-left"></div><div id="button-add-center"><span class="icon-plus-button">Registrar Incidencia</span></div><div id="button-add-right"></div></a>
                                    <!--<a href="#" id="container-button-add"><div class="icon-plus-button"></div>Agregar Afiche</a>-->
                                    
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
                
        </div>

	<div id="incidenciaForm"></div>
</body>
</html>