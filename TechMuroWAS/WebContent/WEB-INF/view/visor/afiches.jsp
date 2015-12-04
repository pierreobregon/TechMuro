<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>

<!DOCTYPE>

<html>
<head>
</head>
<body>
        <div id="allinone_carousel_sweet">
            <div class="myloader"></div>
            <!-- CONTENT -->
            <ul class="allinone_carousel_list">
            
            	<c:forEach items="${oficinaVisor.aficheOficinas}" var="a">
            		<li ><a href="../visor/big/${a.afiche.idafiche}.htm" rel="gallery" class="group4"><img src="../pick/${a.afiche.idafiche}.htm" alt="" /></a></li>
				</c:forEach>
				
				<c:forEach items="${todas.aficheOficinas}" var="t">
            		<li ><a href="../visor/big/${t.afiche.idafiche}.htm" rel="gallery" class="group4"><img src="../pick/${t.afiche.idafiche}.htm" alt="" /></a></li>
				</c:forEach>
           </ul>
        </div>
        
        <div id="container-nav-min">
        	<div id="container-min-left"></div>
                <ul id="container-min-center">
                
                	<c:forEach items="${miniaturaTodas}" var="a">
            			<li><a href="../visor/big/${a.afiche.idafiche}.htm" rel="gallery" class="group4"><img src="../pick/${a.afiche.idafiche}.htm" /></a></li>
					</c:forEach>
					
					<c:forEach items="${miniatura}" var="t">
            			<li><a href="../visor/big/${t.afiche.idafiche}.htm" rel="gallery" class="group4"><img src="../pick/${t.afiche.idafiche}.htm" /></a></li>
					</c:forEach>
                </ul>
            <div id="container-min-right"></div>
        </div>
</body>
</html>