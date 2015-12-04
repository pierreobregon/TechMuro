<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>:: Muro Tecnol&oacute;gico ::</title>
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<title>Header</title>
</head>
<body>

<div id="logo"></div>
                
        <div class="table-header-title">
            <div class="tr-header-title">
                <div class="td-header-title" style="height: 30px;">
                    <ul id="block-header-left">
                        <li>
                            <a href="#" id="container-button-user"><div id="button-user-left"></div><div id="button-user-center">Usuario : <c:out value="${sessionScope['usuarioNombreCabecera']}" /> [UO: <c:out value="${sessionScope['UONombre']}" />]</div><div id="button-user-right"></div></a>
                        </li>
                    </ul>
                </div>
                <div class="td-header-title" style="height: 30px;">
                    <ul id="block-header-right">
                        <li>
                            <a href="https://de-espper.peru.igrupobbva/pkmslogout" id="container-button-close"><div id="button-close-left"></div><div id="button-close-center">Salir</div><div id="button-close-right"></div></a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
</body>
</html>