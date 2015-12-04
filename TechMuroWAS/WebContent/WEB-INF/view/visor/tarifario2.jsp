<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE>

<html>
<head>
	<link rel="stylesheet" href="../css-visor/core.css" type="text/css">

</head>
<script type="text/javascript">



var tableToExcel = (function() {
	  var uri = 'data:application/vnd.ms-excel;base64,'
	    , template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>{table}</table></body></html>'
	    , base64 = function(s) { return window.btoa(unescape(encodeURIComponent(s))) }
	    , format = function(s, c) { return s.replace(/{(\w+)}/g, function(m, p) { return c[p]; }) }
	  return function(table, name) {
	    if (!table.nodeType) table = document.getElementById(table)
	    var ctx = {worksheet: name || 'Worksheet', table: table.innerHTML}
	    window.location.href = uri + base64(format(template, ctx))
	  }
	})()


</script>
<body>
<input type="button" onclick="tableToExcel('tarifarioTabla', 'W3C Example Table')" value="Export to Excel">
	<div class="icon-popup-header-visor-tarifas"></div>
	<div class="container-tarifario-popup">	        
        <table cellspacing="0" cellpadding="0" border="0" class="table-tarifario" id="tarifarioTabla">
        	<tr>
        		<td colspan="2"><img src="../images-visor/logo-plantillas-tarifario.png" style="border:0;"/></td>
			    <td>&nbsp;</td>
			    <td>&nbsp;</td>
			    <td class="td-producto"><c:out value="${producto.nombre }"></c:out> </td>
        	</tr>
        	<tr>
		    	<td colspan="5" height="18"></td>
		  	</tr>
		  	
		  	<c:forEach items="${capituloList}" var="capitulo">
		  		<c:choose>
				    <c:when test="${empty capitulo.notaByIdnotainicial.titulo.trim()}">
				    
				    </c:when>
				    <c:otherwise>
				        <tr>
			        		<td colspan="5" class="td-notas">
			        			<table cellspacing="0" cellpadding="0" border="0" width="100%">
			        				
			        					<tr>
			        						<td class="header-notas">${capitulo.notaByIdnotainicial.titulo }</td>
			        					</tr>
			        					<tr>
			        						<td>${capitulo.notaByIdnotainicial.descripcion }</td>
			        					</tr>
			       
			        				
			        			</table>
			        		</td>
			        	</tr>
				    </c:otherwise>
				</c:choose>
			  	
			  	<c:choose>
				    <c:when test="${capitulo.nombre eq 'Sin Capítulo'}">
				    </c:when>
				    <c:otherwise>
				        <tr>
					    	<td colspan="5" style='width:100%;
		height:33px;
		background:#073b7b;
		color:#fff;
		font-weight:bold;
		font-size:12px;
		border-top:1px solid #fff;'>${capitulo.nombre}</td>
					  	</tr>
				    </c:otherwise>
				</c:choose>
			  	
		  	
			  	<c:forEach items="${capitulo.subcapitulos }" var="subcapitulo">
			  		
			  		<c:choose>
					    <c:when test="${subcapitulo.nombre eq 'Sin Sub-Capítulo'}">
					    </c:when>
					    <c:otherwise>
					        <tr>
						    	<td colspan="5" class="td-sub-capitulo">${subcapitulo.nombre}</td>
						  	</tr>
					    </c:otherwise>
					</c:choose>
			  		
			  		
			        <c:forEach items="${subcapitulo.rubros}" var="rubros">
			        	<tr>
			        		<td colspan="5" height="8">
				        		<table width="100%">
					        		<c:set var="rowAnt" value="0" scope="page"></c:set>
									<tr>
									<c:forEach items="${rubros.columnas}" var="columna" varStatus="i">
										<c:if test="${columna.posicionx != rowAnt }">
											</tr>
											<tr>
										</c:if>
										<td style='height:24px;
		background:#2280f2;
		color:#fff;
		text-align:center;
		font-weight:bold;
		font-size:11px;
		border-top:1px solid #333;
		border-bottom:1px solid #333;
		word-wrap:break-word;
		white-space:pre-wrap;'<c:if test='${i.count==1 }'> td-rubros-index</c:if>" style="min-width:${columna.width}px;max-width:${columna.width}px" rowspan="${columna.rowspan}" colspan="${columna.colspan}">${columna.titulo }</td>
										<c:set var="rowAnt" value="${columna.posicionx}" scope="page"></c:set>
									</c:forEach>
									</tr>
									
									<c:forEach items="${rubros.categorias}" var="categoria">
						        		<c:choose>
										    <c:when test="${categoria.nombre eq 'Sin Categoría'}">
										    </c:when>
										    <c:otherwise>
										        <tr>
									        		<td colspan="20" class="td-categorias">${categoria.nombre } <span>- ${categoria.denominacion }</span></td>
									        	</tr>
										    </c:otherwise>
										</c:choose>
										
										
				        				
				        				<c:forEach items="${categoria.transaccions}" var="transaccion" >
				        				
					        				<c:set var="rowAnt" value="0" scope="page"></c:set>
											<tr class="table-transacciones td-transacciones">
												<c:forEach items="${transaccion.transaccionDetalles}" var="detalle" varStatus="i">
													<c:if test="${detalle.posicionx != rowAnt }">
														</tr>
														<tr class="table-transacciones td-transacciones">
													</c:if>
													<td class="<c:if test='${i.count!=1 }'> td-left</c:if>" rowspan="${detalle.rowspan}" colspan="${detalle.colspan}">${detalle.contenido }</td>
													<c:set var="rowAnt" value="${detalle.posicionx}" scope="page"></c:set>
												</c:forEach>
											</tr>
				        				
				        				</c:forEach>
						        	</c:forEach>
								</table>
			        		</td>
			        	</tr>
			        	<tr>
			        		<td colspan="5" height="8">
			        		<c:choose>
							    <c:when test="${empty rubros.nota.titulo.trim()}">
							    </c:when>
							    <c:otherwise>
							        <tr>
						        		<td colspan="5" class="td-notas">
						        			<table cellspacing="0" cellpadding="0" border="0" width="100%">
						        				<tr>
						        					<td class="header-notas">${rubros.nota.titulo }</td>
						        				</tr>
						        				<tr>
						        					<td>${rubros.nota.descripcion }</td>
						        				</tr>
						        			</table>
						        		</td>
						        	</tr>
							    </c:otherwise>
							</c:choose>
			        		</td>
			        	</tr>
			        </c:forEach>
		        	<tr>
		        		<td colspan="5" height="8">
		        		
		        		<c:choose>
						    <c:when test="${empty subcapitulo.nota.titulo.trim()}">
						    
						    </c:when>
						    <c:otherwise>
						        <tr>
					        		<td colspan="5" class="td-notas">
					        			<table cellspacing="0" cellpadding="0" border="0" width="100%">
					        				<tr>
					        					<td class="header-notas">${subcapitulo.nota.titulo }</td>
					        				</tr>
					        				<tr>
					        					<td>${subcapitulo.nota.descripcion }</td>
					        				</tr>
					        			</table>
					        		</td>
					        	</tr>
						    </c:otherwise>
						</c:choose>
		        		
		        		
		        		</td>
		        	</tr>
			  	</c:forEach>
		  		<c:choose>
				    <c:when test="${empty capitulo.notaByIdnotafinal.titulo.trim()}">
				    
				    </c:when>
				    <c:otherwise>
				        <tr>
			        		<td colspan="5" class="td-notas">
			        			<table cellspacing="0" cellpadding="0" border="0" width="100%">
			        				<tr>
			        					<td class="header-notas">${capitulo.notaByIdnotafinal.titulo }</td>
			        				</tr>
			        				<tr>
			        					<td>${capitulo.notaByIdnotafinal.descripcion }</td>
			        				</tr>
			        			</table>
			        		</td>
			        	</tr>
				    </c:otherwise>
				</c:choose>
		  	</c:forEach>
		  	
		  	
		  	
		  	
		  	<!-- <tr>
		    	<td colspan="5" class="td-capitulo">1. POR EMISI&oacute;N, CERTIFICACI&oacute;N, DEP&oacute;SITOS Y COBROS</td>
		  	</tr>
		  	<tr>
		    	<td colspan="5" class="td-sub-capitulo">1.1. SUB-CAP&Iacute;TULO</td>
		  	</tr>
        	<tr>
        		<td colspan="5" height="8"></td>
        	</tr>
        	
        	<tr>
        		<td class="td-rubros td-rubros-index" rowspan="2">COMISIONES</td>
			    <td class="td-rubros td-rubros-2" colspan="2">Porcentaje</td>
			    <td class="td-rubros td-rubros-4" rowspan="2">ME</td>
			    <td class="td-rubros td-rubros-5" rowspan="2">Observaci&oacute;n y Vigencia</td>
        	</tr>
        	<tr>
        		
			    <td class="td-rubros td-rubros-2">Porcentaje</td>
			    <td class="td-rubros td-rubros-3">MN</td>
        	</tr>
        	
        	
        	
        	<tr>
        		<td colspan="5" class="td-categorias">Cargos asociados a cheques u &oacute;rdenes de pago <span>- Operaciones asociadas a cheques u &oacute;rdenes de pago</span></td>
        	</tr>
        	<tr>
        		<td colspan="5" class="td-transacciones">
        			<table cellspacing="0" cellpadding="0" border="0" width="100%" class="table-transacciones">
        				<tr>
        					<td class="td-transacciones-1">Por Certificaci&oacute;n de cheque girado contra la cuenta</td>
        					<td class="center-trans td-transacciones-2">&nbsp;</td>
        					<td class="center-trans td-transacciones-3">S/10.50</td>
        					<td class="center-trans td-transacciones-4">$3.00</td>
        					<td class="td-transacciones-5">Por la certificaci&oacute;n de los fondos al que asciende un cheque.<br>
Vigente desde 23/05/2012</td>
        				</tr>
        				<tr>
        					<td class="td-transacciones-1">Por Certificaci&oacute;n de cheque girado contra la cuenta</td>
        					<td class="center-trans td-transacciones-2">&nbsp;</td>
        					<td class="center-trans td-transacciones-3">S/10.50</td>
        					<td class="center-trans td-transacciones-4">$3.00</td>
        					<td class="td-transacciones-5">Por la certificaci&oacute;n de los fondos al que asciende un cheque.<br>
Vigente desde 23/05/2012</td>
        				</tr>
        			</table>
        		</td>
        	</tr>
        	
        	<tr>
        		<td colspan="5" class="td-notas">
        			<table cellspacing="0" cellpadding="0" border="0" width="100%">
        				<tr>
        					<td class="header-notas">NOTAS:</td>
        				</tr>
        				<tr>
        					<td>IMPORTANTE: <span>La cuenta no permite retiros parciales en ventanilla ni traspasos entre cuentas en Cajeros autom&aacute;ticos, Banca por Internet y Banca por Tel&eacute;fono.</span></td>
        				</tr>
        			</table>
        		</td>
        	</tr> -->
        </table>
	</div>        
</body>
</html>