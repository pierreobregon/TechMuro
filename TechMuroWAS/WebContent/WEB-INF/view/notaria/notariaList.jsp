<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<script>
	var grid;
	var mydata = [];
	$(document).ready(function() {
	
	$("#buscarNot").attr('disabled','true');
	$("#button-image-complete-load").attr('disabled','true');
	
	
		mydata = eval('${notariaList}');
		grid = $("#gridnotarias");
		
		var listaTamanio = "${listaSize}";
			if(listaTamanio == 0){
				alert("No existen resultados a mostrar");
			}
		
		
		llenarTabla();
		
		$('#subirArchivo').ajaxForm({
		    beforeSend: function(data) {
		    },
		    uploadProgress: function(event, position, total, percentComplete) {
		    	
		    },
		    success: function(data) {
		    },
			complete: function(xhr) {
			
			
				if($.trim(xhr.responseText)!=""){
				
				var cantidadOficnas = "${xhr.result}";
					//alert("Se subi&oacute; el archivo correctamente");
		    		alert($.trim(xhr.responseText));
		    		enviarPeticion("../notaria/list.htm");
				}else{
					alert("No se realizó carga de archivo por inconsistencia de datos o por no cumplir con el formato");
				}
				
				setTimeout(function hideGlobalLoader(){
				      $("body").nimbleLoader("hide");
				    }, 500);
			}
		});
		
		
		
			$("#criterioNot").keyup(function(){
							if($.trim($("#criterioNot").val()).length == 0){
									$("#buscarNot").attr('disabled','true');
									$.post("../notaria/buscar.htm", $("#buscaNotaria").serialize(), function(data) {
									$("#gridnotarias").jqGrid('clearGridData');
										mydata = eval(data), grid = $("#list");
										jQuery("#gridnotarias").setGridParam({ data : mydata });
										jQuery("#gridnotarias").trigger("reloadGrid");
									});
							    }
							    else{
							    	   $("#buscarNot").removeAttr('disabled');
							    }
			});
			
			
		
			
	});

	function llenarTabla() {
		
		grid.jqGrid({
			datatype : 'local',
			data : mydata,
			colNames : [ "ID","Nombre de la Notaría", "Zona Notaría", "Ver Detalle", "Editar", "Eliminar" ],
			colModel : [ { name : 'idnotaria', index : 'name', sorttype : "int", align : "center", viewable : false, hidden : true,sortable:false}, 
					{name : 'nombre', index : 'name', width : 214, align : "left" ,sortable:false}, 
					{name : 'plaza.nombre', index : 'name', width : 124, align : "center" ,sortable:false},
					{name : 'detalle', index:'total', width:72, align:"center",sorttype:"string",sortable:false},
					{name : 'editar', index:'total', width:72,align:"center", sorttype:"date",sortable:false},
					{name : 'eliminar', index:'total', width:72,align:"center",sorttype:"float",sortable:false}
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
            height: '100%',

			gridComplete : function() {
				var ids = $("#gridnotarias").jqGrid('getDataIDs');
				for (var i = 0; i < ids.length; i++) {
					
					var cl = ids[i];
					ed = "<div id=\"icon-edit\" onclick=\"openEditForm("+$("#"+cl+" td").first().text()+");\"></div>";
					el = "<div id=\"icon-eliminar\" onclick=\"return eliminaNotaria("+$("#"+cl+" td").first().text()+");\" />";
					de = "<div id=\"icon-detail\" onclick=\"return openDetalle("+$("#"+cl+" td").first().text()+");\" />";

					jQuery("#gridnotarias").jqGrid('setRowData', ids[i], {
						editar : ed
					});
					jQuery("#gridnotarias").jqGrid('setRowData', ids[i], {
						eliminar : el
					});
					jQuery("#gridnotarias").jqGrid('setRowData', ids[i], {
						detalle : de
					});
				}
			}

		});
		
	}
	

	function upload() {
		$.post("../notaria/upload.htm", $("#buscaProducto").serialize(), function(data) {
			$("#gridnotarias").jqGrid('clearGridData');
			mydata = eval(data), grid = $("#list");
			jQuery("#gridnotarias").setGridParam({ data : mydata });
			jQuery("#gridnotarias").trigger("reloadGrid");
		});
		return false;
	}
	
	function buscar() {
		$.post("../notaria/buscar.htm", $("#buscaNotaria").serialize(), function(data) {
			$("#gridnotarias").jqGrid('clearGridData');
			mydata = eval(data), grid = $("#list");
			jQuery("#gridnotarias").setGridParam({ data : mydata });
			jQuery("#gridnotarias").trigger("reloadGrid");
			
			if(mydata.length==0){
				alert("No existen resultados a mostrar");
			}
			
			$("#resultadosNotarias").html("Resultados de la búsqueda...");
		});
		return false;
	}
	
	function eliminaNotaria(id){
		
		if(confirm("¿Está seguro que desea eliminar la notaría?")){
			
			$.post("../notaria/eliminar.htm", {id:id}, function(data){
				if($.trim(data)=="true"){
					buscar();
				}else{
					alert("no eliminó");
				}
			});
			
		}else{
			return false;
		}
		
	}
	
	function openForm(){
     	$("#notariaForm").load("../notaria/cargaForm.htm", function(){
     		$('#overlay').fadeIn('fast',function(){
                $('#box').animate({'top':'5%'},500);
            });
     	});
     	return false;
     }
     
     function openDetalle(id){
     
     	$("#notariaForm").load("../notaria/cargaDetalle.htm", {idNotaria:id}, function(){
     		$('#overlay').fadeIn('fast',function(){
                $('#box').animate({'top':'5%'},500);
            });
     	});
     	return false;
     }
     
     function openEditForm(id){
      	$("#notariaForm").load("../notaria/cargaEditarForm.htm", {id:id}, function(){
      		$('#overlay').fadeIn('fast',function(){
                 $('#box').animate({'top':'5%'},500);
             });
      	});
      	return false;
      }
     
     function subirArchivo(){
    	 $("body").nimbleLoader("show");
    	 $("#subirArchivo").ajaxForm({url:"../notaria/subirArchivo.htm", type:"post", success:function(data){
    		 $("body").nimbleLoader("hide");
			if($.trim(data)=="true"){
	    		 alert("Se subió el archivo correctamente");
	    		 enviarPeticion("../notaria/list.htm");
	    	}else{
	    		 alert("No se realizó carga de archivo por inconsistencia de datos o por no cumplir con el formato");
	    	}
	   	 }});
    	return false;
     }
     
     function updateMessage() {
         jQuery("#textisselectedanyfile").val(jQuery("#file").val());
         if (jQuery("#file").val() == "") {
             jQuery("#textisselectedanyfile").val("No ha seleccionado ningún archivo");
         }else{
        	 $("#button-image-complete-load").removeAttr("disabled");
         }
     }
</script>
</head>
<body>
	<div id="block-grid">
            <div id="line-body-sup"></div>
            <div id="back-title">
                <h1>Notar&iacute;as</h1>
            </div>
            <div id="container-upload">
            	<form:form action="../notaria/subirArchivo.htm" method="post" id="subirArchivo" cssClass="list-margin-center" 
            		enctype="multipart/form-data" modelAttribute="uploadedFile">
                    <ul>
                        <li><h2>Archivo :</h2></li> 
                        <li class="container-input-file">
                        	<div id="loadtextbotton-search" onclick="$('#file').click()"></div>
                        	<input id="textisselectedanyfile" type="text" value="Seleccionar archivo" class="input-file-2" readonly/>
                        	<input name="file" id="file" type="file" style="display: none;" onchange="updateMessage()"/>
                        </li>
                        <li><input type="submit" value="Subir archivo" id="button-image-complete-load" onclick="$('body').nimbleLoader('show');"/></li>
                    </ul>
                    
                    <p><span>*</span> Solo formatos excel (extensi&oacute;n .xls o .xlsx).</p>
                </form:form>
                
            </div>
            <div id="container-search">
            	<form action="" id="buscaNotaria" onsubmit="return buscar();" method="post">
                    <ul>
                        <li><h2>Nombre :</h2></li>
                        <li><input id="criterioNot" name="criterio" type="text" class="input-border"></li>
                        <li><input id="buscarNot" type="submit" value="Buscar" class="button-image-complete">
                        </li>
                    </ul>
                </form>
            </div>
            
            <div id="container-grid">    
                <div class="table-grid">
                    <div class="tr-grid">
                        <div class="td-grid" >
                            <p id="resultadosNotarias" ></p>
                        </div>
                        <div class="td-grid">
                            <ul id="block-grid-right">
                                <li>
                                    <a href="../notaria/cargaDetalle.htm" id="container-button-add" onclick="return openForm();"><div id="button-add-left"></div><div id="button-add-center"><span class="icon-plus-button">Agregar Notaria</span></div><div id="button-add-right"></div></a>
                                    <!--<a href="#" id="container-button-add"><div class="icon-plus-button"></div>Agregar Afiche</a>-->
                                    
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            
                <!-- Grilla -->
                <table id="gridnotarias"></table>
                <div id="pager"></div>
            <!-- Find Grilla -->
            </div>
                
        </div>

	<div id="notariaForm"></div>
</body>
</html>