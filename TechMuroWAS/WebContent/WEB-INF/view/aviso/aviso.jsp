<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta charset="utf-8">
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=8" />

<!-- Bordes para IE7
	<script>
        DD_roundies.addRule('input', '3px');
        DD_roundies.addRule('#container-search', '5px');
    </script>  
Fin Bordes para IE7 -->


	<script>
	
	
	
		grid = $("#list");
	 	var tudata = [];
	
		$(document).ready(function () {


		
			$("#buscarAvi").attr('disabled','true');
			var hh = [];
			'<c:forEach items="${lisAllavi}" var="avi">';
				
				var a = "${avi.fechacreacion}".substring(8,10) ;
				var b = "${avi.fechacreacion}".substring(5,7) ;
				var c = "${avi.fechacreacion}".substring(0,4) ;
			    var fechita = (a+"/"+b+"/"+c);
				hh.push({idaviso:'${avi.idaviso}',fecha:fechita, titulo:'${avi.titulo}', circular:'${avi.circular}'} ); 
		    '</c:forEach>';
			llenarGrid(hh);
					
			$("#criterioAvi").keyup(function(){
							if($.trim($("#criterioAvi").val()).length == 0){
									$("#buscarAvi").attr('disabled','true');
									$.post("../buscarAviso.htm",$("#buscarAviso").serialize(),function(data){
										$("#list").jqGrid('clearGridData');
										jQuery("#list").setGridParam({
											data : eval(data)
										});
										jQuery("#list").trigger("reloadGrid");
									});
							    }
							    else{
							    	   $("#buscarAvi").removeAttr('disabled');
							    }
			});
			
			
		});
	
		function myF(t,id){
		
			if(confirm("Está seguro que desea eliminar el aviso?")){
				
				$.get("../deleteAviso/"+t+".htm",function(data){

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
		
		function buscarAviso(){
				
				$.post("../buscarAviso.htm",$("#buscarAviso").serialize(),function(data){
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
	     	$("#up").load("../upAgregarAviso.htm", function(){
				$('#overlay').fadeIn('fast',function(){
					$('#box').animate({'top':'100px'},500);
					$('#title-header-popup').text("Agregar Aviso");
				});  
	     	});
	     	return false;
	     }
	     
	     function myEdit(t){
	     	$("#up").load("../upEditarAviso/"+t+".htm", function(){
	     			$('#overlay').fadeIn('fast',function(){
	                            $('#box').animate({'top':'100px'},500);
	                            $('#title-header-popup').text("Editar Aviso");
	                        });
	     	});
	     	return false;
	     }
	        
	     function llenarGrid(Json){
		
	            grid.jqGrid({
	                datatype:'local',
	                data: Json,
	               	colNames: ["idaviso","Fecha","Titulo","Circular","Editar","Eliminar"],
					colModel:[ { name : 'idaviso', index : 'name', sorttype : "int", align : "center", viewable : false, hidden : true,sortable:false},
						{name:'fecha',index:'nro', width:84, align:"center",sortable:false},
						{name:'titulo',index:'invdate', width:84, align:"center",sortable:false},
						{name:'circular',index:'name', width:84, align:"center",sortable:false},
						//{name:'descripcion',index:'descripcion', width:216, align:"center"},	
						{name:'editar',index:'total', width:72,align:"center",sortable:false},
						{name:'eliminar',index:'total', width:72,align:"center",sortable:false},
						
						/*
						{name:'accion',index:'note', width:90, sortable:false}*/
					],
					
	                rowNum:10,
	                rowList:[5,10,20],
	                pager: '#pager',
	                gridview: true,
	                rownumbers:false,
	                viewrecords: false,
	                sortorder: 'asc',					
					width: 673,
	                height: '100%',
					
					gridComplete: function(){
						var ids = jQuery("#list").jqGrid('getDataIDs');
						for(var i=0;i < ids.length;i++){
							var cl = ids[i];
							be = "<div id=\"icon-edit\" onclick=\"myEdit("+$("#"+cl+" td").first().text()+")\"></div>";
							se = "<div id=\"icon-eliminar\" onclick=\"myF("+$("#"+cl+" td").first().text()+" ,"+cl+")\"></div>";
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
		  
      
      	function carga(){
 	       alert("cargar");
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
                <h1>Tarifario > Avisos Importantes</h1>
            </div>
            
               <div id="container-search">
	            <form action="" id="buscarAviso" onsubmit="return buscarAviso();" method="post">
	            	<ul style="width: 525px;">
	                	<li><h2>Titulo:</h2></li>
	                    <li><input id="criterioAvi" name="criterio" type="text" class="input-border"></li>
	                    <li><input id="buscarAvi" type="submit" value="Buscar" class="button-image-complete">
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
									<a  onclick = "linkUp() " id="container-button-add"><div id="button-add-left"></div><div id="button-add-center"><span class="icon-plus-button">Agregar Aviso</span></div><div id="button-add-right"></div></a>
								</li>
							</ul>
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
