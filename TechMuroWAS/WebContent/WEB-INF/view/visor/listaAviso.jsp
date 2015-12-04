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

		<div class="icon-popup-header-visor-avisos"></div> 
    	<div class="container-float">
            <article class="container-body-popup popup-padding">
                <h3 class="margin-title">AVISOS IMPORTANTES</h3>
                
                <div id="scrollbar-not">
<!-- 					<div class="scrollbar"> -->
<!-- 						<div class="track"> -->
<!-- 							<div class="thumb"> -->
<!-- 								<div class="end"></div> -->
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 					</div> 
				<div class="viewport"> -->
                <table cellpadding="0" cellspacing="0" class="table-avisos overview">
                    <tbody>
					<c:forEach items="${listaAvisos}" var="listaAvisos" varStatus="i" >
                    <tr>
                        <td class="header-grid-popup-avisos">${listaAvisos.titulo}</td>
                        <td class="header-grid-popup-avisos-circular">Circular</td>
                    </tr>
                              
					<tr>	
						<td>	                        	
						   <p>${listaAvisos.descripcion}</p>
						</td>
						<td>${listaAvisos.circular}</td> 
				    </tr>
				   
					</c:forEach>                 
					</tbody>
                </table>
                </div>
<!--                 </div> -->
            </article>
		</div>