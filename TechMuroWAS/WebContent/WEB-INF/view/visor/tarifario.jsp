<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="pe.conadis.tradoc.entity.Transaccion"%>
<%@page import="pe.conadis.tradoc.entity.TransaccionDetalle"%>
<%@page import="java.io.OutputStream"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>



<!DOCTYPE>

<html>
<head>
<link href="../css-visor/core.css" rel="stylesheet" type="text/css">
<!-- <script type="text/javascript" src="../js-visor/jquery.tinyscrollbar.js"></script> -->

<script type="text/javascript">

// $(document).ready(function(){
//             var $scrollbar = $("#scrollbar-com");

//             $scrollbar.tinyscrollbar();
//         });

</script>

</head>
<body>
	<div class="icon-popup-header-visor-tarifas"></div>
<!-- 		<div id="scrollbar-com"> -->
<!-- 			<div class="scrollbar"> -->
<!-- 				<div class="track"> -->
<!-- 					<div class="thumb"> -->
<!-- 						<div class="end"></div> -->
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 			</div> -->
<!-- 		<div class="viewport">	 -->
			<div class="container-tarifario-popup">	        
		        <table cellspacing="0" cellpadding="0" border="0" class="table-tarifario">
		        	<tr>
		        		<td colspan="2"><img src="../images-visor/logo-plantillas-tarifario.jpg" style="border:0;"/></td>
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
					        						<td colspan ="5" height="6" ></td>
					        					</tr>
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
							    	<td colspan="5" class="td-capitulo">${capitulo.nombre}</td>
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
					        		<td colspan="5">
						        		<table width="100%">
							        		<c:set var="rowAnt" value="0" scope="page"></c:set>
											<tr>
											<c:forEach items="${rubros.columnas}" var="columna" varStatus="i">
												<c:if test="${columna.posicionx != rowAnt }">
													</tr>
													<tr>
												</c:if>
												<td class="td-rubros<c:if test='${i.count==1 }'> td-rubros-index</c:if>" style="min-width:${columna.width}px;max-width:${columna.width}px" rowspan="${columna.rowspan}" colspan="${columna.colspan}">${columna.titulo }</td>
												<c:set var="rowAnt" value="${columna.posicionx}" scope="page"></c:set>
											</c:forEach>
											</tr>
											
											<c:forEach items="${rubros.categorias}" var="categoria">
								        		<c:choose>
												    <c:when test="${categoria.nombre eq 'Sin Categoría'}">
												    </c:when>
												    <c:otherwise>
												        <tr>
												        
												        <c:choose>
												        <c:when  test="${!empty categoria.denominacion}">
												        		<td colspan="5" class="td-categorias">${categoria.nombre } <span>- ${categoria.denominacion }</span></td>
												        	
												        </c:when>
												        <c:otherwise>
												        			<td colspan="5" class="td-categorias">${categoria.nombre }</td>
												        			
												        </c:otherwise>
											        	</c:choose>	
											        												        	
											       		</tr>
												    </c:otherwise>
												</c:choose>
												
												
						        				
						        				<c:forEach items="${categoria.transaccions}" var="transaccion" >
						        				
						        			<%
						        			List<List<TransaccionDetalle>> listaFilas = new ArrayList<List<TransaccionDetalle>>();
						        			List<TransaccionDetalle> listaDetalle = ((Transaccion)pageContext.getAttribute("transaccion")).getTransaccionDetalles();
						        			
											int max_rows = 0;
											for(TransaccionDetalle tx_det : listaDetalle){
												if(tx_det.getPosicionx()>max_rows){
													max_rows = tx_det.getPosicionx();
												}
											}

											for(int i = 1; i <= max_rows; i++){
												List<TransaccionDetalle> lista_tx_det = new ArrayList<TransaccionDetalle>();
												for(TransaccionDetalle tx_det : listaDetalle){
													if(tx_det.getPosicionx()==i){
														lista_tx_det.add(tx_det);
													}
												}
												listaFilas.add(lista_tx_det);
											}						        			
						        			pageContext.setAttribute("listaFilas", listaFilas);
						        			%>	
											<c:forEach items="${listaFilas}" var="fila" varStatus="i">
												<c:set var="listaDetalle" value="${fila}"></c:set>
												<c:if test="${not empty listaDetalle}">
												<tr class="table-transacciones td-transacciones">
													<c:forEach items="${listaDetalle}" var="columna" varStatus="j">
													<c:if test="${not empty columna}">
													<td class="<c:if test='${i.count !=0 }'> td-left</c:if>"  id="${columna.iddetalle}" rowspan="${columna.rowspan}" colspan="${columna.colspan}"><c:choose><c:when test="${i.count==1 && j.count==1 && not empty transaccion.idtransaccion }">${transaccion.nombre}</c:when> <c:otherwise>${columna.contenido}</c:otherwise></c:choose></td>
													</c:if>
													</c:forEach>
												</tr>
												</c:if>
											</c:forEach>

						        				
						        				</c:forEach>
								        	</c:forEach>
										</table>
					        		</td>
					        	</tr>
					        	<tr>
					        		<td colspan="5">
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
								        				<tr>
					        								<td colspan ="5" height="6"  style="border-top:1px solid;"></td>
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
				        		<td colspan="5"  >
				        		
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
							        				<tr>
					        							<td colspan ="5" height="6"  style="border-top:1px solid;"></td>
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
					        				<tr>
					        					<td colspan ="5" height="6"  style="border-top:1px solid;"></td>
					        				</tr>
					        			</table>
					        		</td>
					        	</tr>
						    </c:otherwise>
						</c:choose>
				  	</c:forEach>
				  	
		        </table>
			</div>  
<!-- 		</div> -->
<!-- 	</div>       -->
</body>
</html>