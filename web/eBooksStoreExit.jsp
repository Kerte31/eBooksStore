<%-- 
    Document   : eBooksStoreExit
    Created on : 07-Aug-2018, 17:21:22
    Author     : Mihai
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Recording received deposit</title>
        <link rel="stylesheet" type="text/css" href="./css/eBooksStore.css">
    </head>
    <body>
        <%-- test if a valid user is logged in --%>
        <c:choose>
            <c:when test="${validUser == true}"> 
                <c:set var="validUser" value="false" scope="session"></c:set>
                <c:set var="actualUser" value="" scope="session"></c:set>  
                <c:set var="actualUserRole" value=""scope="session" ></c:set>
                <c:redirect url="./index.jsp"></c:redirect> 
            </c:when>
            <c:otherwise> 
                <c:redirect url="./index.jsp"></c:redirect>
            </c:otherwise>
        </c:choose>
    </body>
</html>
