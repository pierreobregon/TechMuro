<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>

<link href="../css-visor/core.css" rel="stylesheet" type="text/css">
<!-- <script type="text/javascript" src="../js-visor/jquery.tinyscrollbar.js"></script> -->

<script type="text/javascript">

// $(document).ready(function(){
//             var $scrollbar = $("#scrollbar-not");

//             $scrollbar.tinyscrollbar();
//         });
</script>

		<div class="icon-popup-header-visor-notarias"></div>
    	<div class="container-float">
            <article class="container-body-popup popup-padding">
                <h2>Estimado Cliente</h2>
                <p>A continuaci&oacute;n ponemos a su disposici&oacute;n la relaci&oacute;n de Notarios, la misma que se publica de acuerdo a lo establecido por la Resoluci&oacute;n SBS N° 8181-2012 <sup style="font-size: 10px">1</sup></p>
                
                
		<div id="scrollbar-not">
<!-- 			<div class="scrollbar"> -->
<!-- 				<div class="track"> -->
<!-- 					<div class="thumb"> -->
<!-- 						<div class="end"></div> -->
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 			</div> -->
<!-- 		<div class="viewport"> -->

                <table cellpadding="0" cellspacing="0" class="overview table-notarias">
                    <tbody>
                    <tr>
                        <td class="header-grid-popup">Contrato</td>
                        <td class="header-grid-popup">Gastos Notariales</td>
                        <td class="header-grid-popup">Notar&iacute;a</td>
                    </tr>
                    
                    <c:set var="notariaAnt" value="false" scope="page"></c:set>
                    <c:set var="gastosAnt" value="false" scope="page"></c:set>
                    <c:forEach items="${listaContrato}" var="listaContratos" varStatus="i" >
	            		
	            		<c:choose>
	            			<c:when test="${notariaAnt eq listaContratos.contrato.descripcion && gastosAnt eq listaContratos.contrato.gastos}">
	            				<p><a href="#" onclick="return verNotaria('${listaContratos.notaria.idnotaria}');">${listaContratos.notaria.nombre }</a></p>
	            			</c:when>
	            			
	            			<c:when test="${notariaAnt eq 'false'}">
	            				<tr>
			                        <td>${listaContratos.contrato.descripcion }</td>
			                        <td>${listaContratos.contrato.gastos }</td>
			                        <td>
			                        	<p><a href="#" onclick="return verNotaria('${listaContratos.notaria.idnotaria}');">${listaContratos.notaria.nombre }</a></p>
	            			</c:when>
	            			<c:otherwise>
	            					</td>
	            				</tr>
	            				<tr>
			                        <td>${listaContratos.contrato.descripcion }</td>
			                        <td>${listaContratos.contrato.gastos }</td>
			                        <td>
			                        	<p><a href="#" onclick="return verNotaria('${listaContratos.notaria.idnotaria}');">${listaContratos.notaria.nombre }</a></p>
	            			</c:otherwise>
	            		</c:choose>
	            		<%-- <tr>
	                        <td>${listaContratos.contrato.descripcion }</td>
	                        <td>${listaContratos.contrato.gastos }</td> --%>
	                        
	                        <%-- <c:if test="${listRows[i.count-1] > 0 }">
	                        	<td rowspan="${listRows[i.count-1]}">	
		                        	<c:forEach items="${contrato.notariaContratos }" var="notarias">
		                        		<p><a href="#" onclick="return verNotaria('${notarias.notaria.idnotaria}');">${notarias.notaria.nombre }</a></p>
		                        	</c:forEach>
	                        	</td>
	                        </c:if> --%>
	                    <c:set var="notariaAnt" value="${listaContratos.contrato.descripcion }" scope="page"></c:set>
	                    <c:set var="gastosAnt" value="${listaContratos.contrato.gastos }" scope="page"></c:set>
					</c:forEach>
					</tbody>
                </table>
<!-- 			</div>                 -->
		</div>
		
                <p><sup style="font-size: 10px">1</sup> La informaci&oacute;n puede estar sujeta a ajustes previstos por cada Notario. </p>
				<p>No incluye gastos registrales, los cuales son establecidos por la Superintendencia Nacional de los Registros P&uacute;blicos.
</p>
            </article>
		</div>