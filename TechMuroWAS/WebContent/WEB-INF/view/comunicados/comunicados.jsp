<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>


<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=8" />

	<script>
	
		grid = $("#list");
	 	var tudata = [];
	
		$(document).ready(function () {
		
			$("#buscarAfi").attr('disabled','true');
			var hh = [];
			
			
			var listaTamanio = "${listaSize}";
			if(listaTamanio == 0){
				alert("No existe información de comunicados");
			}
			
			
			'<c:forEach items="${lisAll}" var="a">';
				var a = "${a.comunicado.fechacreacion}".substring(8,10) ;
				var b = "${a.comunicado.fechacreacion}".substring(5,7) ;
				var c = "${a.comunicado.fechacreacion}".substring(0,4) ;
			    var fechita = (a+"/"+b+"/"+c);
				hh.push({idoficina:"${a.oficina.idoficina}",idcomunicado:'${a.comunicado.idcomunicado}',fecha:fechita , titulo:'${a.comunicado.titulo}', oficina:'${a.oficina.codigo}',tipocomunicado:'${a.comunicado.tipocomunicado}'} );
		    '</c:forEach>';
			llenarGrid(hh);
					
			$("#criterioAfi").keyup(function(){
							if($.trim($("#criterioAfi").val()).length == 0){
									$("#buscarAfi").attr('disabled','true');
									$.post("../buscarComunicado.htm",$("#buscaAfiche").serialize(),function(data){
										$("#list").jqGrid('clearGridData');
										jQuery("#list").setGridParam({
											data : eval(data)
										});
										jQuery("#list").trigger("reloadGrid");
									});
							    }
							    else{
							    	$("#buscarAfi").removeAttr('disabled');
							    }
			});
			
			
		});
	
		function myF(t,n,id){
		
			if(confirm("Está seguro que desea eliminar el comunicado?")){
				
				$.get("../deleteComunicado/"+n+"/"+t+".htm",function(data){

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
					
				});
			}else{
				return false;
			}
		}
		
		function buscarComunicado(){
				
				$.post("../buscarComunicado.htm",$("#buscaAfiche").serialize(),function(data){
					$("#ResultadoBusqueda p").attr("style","color:#07508f;");
					$("#list").jqGrid('clearGridData');
					jQuery("#list").setGridParam({
						data : eval(data)
					});
					jQuery("#list").trigger("reloadGrid");
					if(data.length == 2){
						alert("No existen resultados a mostrar");
					}
				});
				
				return false;
		}
	        
	    function linkUp(){
	     	$("#up").load("../upAgregarComunicado.htm", function(){
				$('#overlay').fadeIn('fast',function(){
					$('#box').animate({'top':'100px'},500);
					$('#title-header-popup').text("Agregar Comunicado");
				});  
	     	});
	     	return false;
	     }
	     
	     function myEdit(t,n){
	     	$("#up").load("../upEditarComunicado/"+n+"/"+t+".htm", function(){
	     			$('#overlay').fadeIn('fast',function(){
	                            $('#box').animate({'top':'100px'},500);
	                            $('#title-header-popup').text("Editar Comunicado");
	                        });
	     	});
	     	return false;
	     }
	        
	     function llenarGrid(Json){
		
	            grid.jqGrid({
	                datatype:'local',
	                data: Json,
	               	colNames: ["idoficina","idcomunicado","Fecha", "Titulo", "Tipo Comunicado","Oficina","Editar", "Eliminar"],
					colModel:[ { name : 'idoficina', index : 'name', sorttype : "int", align : "center", viewable : false, hidden : true,sortable:false},
						{ name : 'idcomunicado', index : 'name', sorttype : "int", align : "center", viewable : false, hidden : true,sortable:false},
						{name:'fecha',index:'nro', width:100, sorttype:"int", align:"center",sortable:false},
						{name:'titulo',index:'invdate', width:150, sorttype:"int", align:"center",sortable:false}, 
						{name:'tipocomunicado',index:'name', width:216, align:"center",sortable:false},
						{name:'oficina',index:'name', width:72, align:"center",sortable:false},
						{name:'editar',index:'total', width:72,align:"center", sorttype:"date",sortable:false},
						{name:'eliminar',index:'total', width:72,align:"center",sorttype:"float",sortable:false},
						/*{name:'accion',index:'note', width:90, sortable:false}*/
					],
					
	                rowNum:10,
	                rowList:[5,10,20],
	                pager: '#pager',
	                gridview: true,
	                rownumbers:false,
	                sortname: 'idcomunicado',
	                viewrecords: false,
	                sortorder: 'asc',
					
					width: 673,
	                height: '100%',
					
					gridComplete: function(){
						var ids = jQuery("#list").jqGrid('getDataIDs');
						for(var i=0;i < ids.length;i++){
							var cl = ids[i];
							be = "<div id=\"icon-edit\" onclick=\"myEdit("+$("#"+cl+" td").first().text()+"  ,"+$("#"+cl+" td").first().next().text()+")\"></div>";							
							se = "<div id=\"icon-eliminar\" onclick=\"myF("+$("#"+cl+" td").first().text()+"  ,"+$("#"+cl+" td").first().next().text()+","+cl+")\"></div>";

							

							//de = "<div class='afiches'\"><img height='38' width='25' src=\"../pick/"+$("#"+cl+" td").first().next().text()+".htm\" ></div>";
							//ge = "<div id='check'\"></div>"; 
							//me = "<div></div>";
	
							jQuery("#list").jqGrid('setRowData',ids[i],{editar:be});
							jQuery("#list").jqGrid('setRowData',ids[i],{eliminar:se});
							//jQuery("#list").jqGrid('setRowData',ids[i],{afiches:de});
							
// 							if($("#"+cl+" td").first().next().next().next().next().next().text() != "S"){
// 								jQuery("#list").jqGrid('setRowData',ids[i],{miniatura:me});
// 							}else{
// 								jQuery("#list").jqGrid('setRowData',ids[i],{miniatura:ge});
// 							}
							
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
                <h1>Comunicados</h1>
            </div>
            <div id="container-search">
	            <form action="" id="buscaAfiche" onsubmit="return buscarComunicado();" method="post">
	            	<ul style="width: 525px;">
	                	<li><h2>Titulo:</h2></li>
	                    <li><input id="criterioAfi" name="criterio" type="text" class="input-border"></li>
	                    <li><input id="buscarAfi" type="submit" value="Buscar" class="button-image-complete">
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
                                    <a  onclick = "linkUp() " id="container-button-add"><div id="button-add-left"></div><div id="button-add-center"><span class="icon-plus-button">Agregar Comunicado</span></div><div id="button-add-right"></div></a>
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
