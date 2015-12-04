<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>

<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=8" />

	<script>
	
		grid = $("#list");
	 	var tudata = [];
	
		$(document).ready(function () {
		
			$("#buscarAfi").attr('disabled','true');
			var hh = [];
			
			var listaTamanio = "${listaSize}";
			if(listaTamanio == 0){
				alert("No se tiene archivos cargados al sistema");
			}
			
			'<c:forEach items="${lisAll}" var="a">';
				hh.push({idoficina:'${a.oficina.idoficina}',idafiche:'${a.afiche.idafiche}',imagen:'0',oficina:'${a.oficina.codigo}',descripcion:'${a.afiche.descripcion}',miniatura:'${a.afiche.miniatura}',miniaturaIcon:'${a.afiche.miniatura}'} );
		    '</c:forEach>';
		    tudata = hh;
			llenarGrid();
			
			$("#criterioAfi").keyup(function(){
							if($.trim($("#criterioAfi").val()).length == 0){
									//alert("text: "+ $.trim($("#criterioAfi").val()).length);
									$("#criterioAfi").val($.trim($("#criterioAfi").val()));
									$("#buscarAfi").attr('disabled','true');
									$.post("../buscarAfiche.htm",$("#buscaAfiche").serialize(),function(data){
										$("#list").jqGrid('clearGridData');
										jQuery("#list").setGridParam({
											data : eval(data)
										});
										jQuery("#list").trigger("reloadGrid");
									});
							      //  $("textarea").after('<label class="red">Name is Required</label>');
							    }
							    else{
							    //	$(".red").attr('style','display:none');
							    	//alert("text: "+ $("#criterioAfi").val().length);
							    	$("#buscarAfi").removeAttr('disabled');
							    }
			});
			
			
		});
	
		function myF(t,n,id){
		
			if(confirm("Está seguro que desea eliminar el afiche?")){		
				$.get("../deleteAfi/"+n+"/"+t+".htm",function(data){
			
					if(data == "error"){
						alert("No se puede eliminar Afiche, por validación de Miniaturas Mínimas");
					}else{
						tudata = eval(data);

						var pageSize = jQuery("#list").getGridParam("rowNum");
			            var totalRecords = jQuery("#list").getGridParam('records');
			            var totalPages = Math.ceil(totalRecords/pageSize);
			            var currentPage = jQuery("#list").getGridParam('page');
						
						if(Number(totalRecords)-1<=(currentPage-1)*pageSize){
							$("#list").jqGrid('clearGridData');
							jQuery("#list").setGridParam({data : tudata,page:Number(totalPages)-1}).trigger("reloadGrid");
						}else{
							$("#list").jqGrid('clearGridData');
							jQuery("#list").setGridParam({data : tudata,page:currentPage});
							jQuery("#list").trigger("reloadGrid");
						}

					}
				});
			}else{
				return false;
			}
		}
		
		function buscarAfiche(){
				$("body").nimbleLoader("show");
				$.post("../buscarAfiche.htm",$("#buscaAfiche").serialize(),function(data){
					$("#ResultadoBusqueda p").attr("style","color:#07508f;");
					$("#list").jqGrid('clearGridData');
					jQuery("#list").setGridParam({
						data : eval(data)
					});
					jQuery("#list").trigger("reloadGrid");
					if(data.length == 2){  
						alert("No existen resultados a mostrar");
					}
					 setTimeout(function hideGlobalLoader(){
					      $("body").nimbleLoader("hide");
					    }, 500);
				});
				
				return false;
		}
	        
	    function linkUp(){
	    
	    	
	    	
	     	$("#up").load("../upAgregarAfiche.htm", function(data){
// 	     		alert()
// 	     		var cantidadOficnas = "${cantidadOficnas}";
// 	    		alert("${cantidadOficnas}");
// 				if(cantidadOficnas == 0){
//					alert("No se pueden cargar m&aacute;s afiches");
//					return;
//				}	
	    	
				$('#overlay').fadeIn('fast',function(){
					$('#box').animate({'top':'100px'},500);
					$('#title-header-popup').text("Agregar Afiche");
				});  
	     	});
	     	return false;
	     }
	     
	     function myEdit(t,n){
	     	$("#up").load("../upAgregarAfiche/"+n+"/"+t+".htm", function(){
	     			$('#overlay').fadeIn('fast',function(){
	                            $('#box').animate({'top':'100px'},500);
	                            $('#title-header-popup').text("Editar Afiche");
	                        });
	     	});
	     	return false;
	     }
	        
	     function llenarGrid(){
		
	            grid.jqGrid({
	                datatype:'local',
	                data: tudata,
	               	colNames: ["idoficina","N°", "Afiche", "Oficina", "Descripción", "Miniatura", "MiniaturaIcon", "Editar", "Eliminar"],
					colModel:[ { name : 'idoficina', index : 'name', align : "center", viewable : false, hidden : true, sortable:false},
						{name:'idafiche',index:'nro', width:54, align:"center", sortable:false},
						{name:'afiches',index:'invdate', width:84, align:"center", sortable:false}, 
						{name:'oficina',index:'name', width:84, align:"center", sortable:false},
						{name:'descripcion',index:'name', width:216, align:"center",sortable:false},
						{name:'miniatura',index:'tax', width:79, align:"center", sortable:false},
						{name:'miniaturaIcon',index:'tax', width:79, align:"center", sortable:false, viewable : false, hidden : true},
						{name:'editar',index:'total', width:72,align:"center",  sortable:false},
						{name:'eliminar',index:'total', width:72,align:"center", sortable:false}
						/*{name:'accion',index:'note', width:90, sortable:false}*/
					],
					
	                rowNum:10,
	                rowList:[5,10,20],
	                pager: '#pager',
	                gridview: true,
	                rownumbers:false,
	                sortname: 'idafiche',
	                viewrecords: false,
	                sortorder: 'asc',
					
					width: 673,
	                height: '100%',
					
					gridComplete: function(){
						var ids = jQuery("#list").jqGrid('getDataIDs');
						for(var i=0;i < ids.length;i++){
							var cl = ids[i];
							
							var rowData = $("#list").getRowData(cl);
							
							be = "<div id=\"icon-edit\" onclick=\"myEdit("+$("#"+cl+" td").first().text()+"  ,"+$("#"+cl+" td").first().next().text()+")\"></div>";
							se = "<div id=\"icon-eliminar\" onclick=\"myF("+$("#"+cl+" td").first().text()+"  ,"+$("#"+cl+" td").first().next().text()+","+cl+")\"></div>";
							//alert('img : '  +  "src=\"../pick/"+$("#"+cl+" td").first().next().text()+".htm\");

							de = "<div class=\"afiches\"><img height=\"38\" width=\"25\" src=\"../pick/"+$("#"+cl+" td").first().next().text()+".htm\" /></div>";
			//				alert("<div class=\"afiches\"\"><img height=\"38\" width=\"25\" src=\"../pick/"+$("#"+cl+" td").first().next().text()+".htm\" /></div>");
							ge = "<div id=\"check\"></div>"; 
							me = "";
	
							jQuery("#list").jqGrid('setRowData',ids[i],{editar:be});
							jQuery("#list").jqGrid('setRowData',ids[i],{eliminar:se});
							jQuery("#list").jqGrid('setRowData',ids[i],{afiches:de});
							
							if(rowData.miniaturaIcon != "S"){
								jQuery("#list").jqGrid('setRowData',ids[i],{miniatura:me});
							}else{
								jQuery("#list").jqGrid('setRowData',ids[i],{miniatura:ge});
							}
							
						}
					}

	            });
	
	      }
		
	</script>
	

<!--[if lt IE 9]>
<script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->

</head>

<body>

    	<div id="block-grid">
            <div id="line-body-sup"></div>
            <div id="back-title">
                <h1>Afiches</h1>
            </div>
            <div id="container-search">
	            <form action="" id="buscaAfiche" onsubmit="return buscarAfiche();" method="post">
	            	<ul style="width: 525px;">
	                	<li><h2>Descripci&oacute;n :</h2></li>
	                    <li><input id="criterioAfi" name="criterio" type="text" class="input-border"></li>
	                    <li><input id="buscarAfi" type="submit" value="Buscar" class="button-image-complete search-clear">
	                    </li>
	                </ul>
	             </form>
            </div>
            
            
            <div id="container-grid">    
                <div class="table-grid">
                    <div class="tr-grid">
                        <div id="ResultadoBusqueda" class="td-grid" >
                            <p style="color:#f7f7f7">Resultados de la b&uacute;squeda...</p>
                        </div>
                        <div class="td-grid">
                            <ul id="block-grid-right">
                                <li>
                                    <a  onclick = "linkUp() " id="container-button-add"><div id="button-add-left"></div><div id="button-add-center"><span class="icon-plus-button">Agregar Afiche</span></div><div id="button-add-right"></div></a>
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
        
        <div id="up" class="up"></div>
        


</body>
</html>
