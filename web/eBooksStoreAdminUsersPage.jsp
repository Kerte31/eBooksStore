<%-- 
    Document   : eBooksStoreAdminUsersPage
    Created on : 07-Aug-2018, 18:19:40
    Author     : Mihai
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Electronic Books Store Manage Users</title>
        <link rel="stylesheet" type="text/css" href="./css/eBooksStore.css">
    </head>
<body>
        <%-- test if actual user is authenticated and authorized --%>
        <c:choose>
                <c:when test="${validUser == true}">   
                    <!-- include menu -->
                    <%@ include file="./utils/eBooksStoreMenu.jspf" %>
                    <%-- Master view --%>
                        <form action="${pageContext.request.contextPath}/eBooksStoreAdminUsersServlet" method="POST">
                        <sql:setDataSource 
                        var="snapshot" 
                        driver="org.apache.derby.jdbc.ClientDriver"
                        url="jdbc:derby://localhost:1527/ebooksstore;create=true;"
                        user="test"  
                        password="test"/>
                        <sql:query dataSource="${snapshot}" var="result">
                            SELECT SSN, NAME, PASSWORD, ROLE FROM EBOOKS.USERS ORDER BY NAME, ROLE ASC
                        </sql:query>
                        <table border="1" width="100%">
                            <tr>
                                <td width="4%" class="thc"> select </td>   
                                <td width="14%" class="thc"> SSN </td>  
                                <td width="14%" class="thc">USERNAME</td>
                                <td width="14%" class="thc">PASSWORD</td>
                                <td width="14%" class="thc">ROLE <input type="submit" name="admin_userroles_open" value="Admin"></td>
                            </tr> 
                        </table>    
                        <table border="1" width="100%">    
                            </tr>
                            <c:forEach var="row" varStatus="loop" items="${result.rows}">
                            <tr>
                                <td width="4%" class="tdc"><input type="checkbox" name="admin_users_checkbox" value="${row.ssn}"></td>
                                <td width="14%" class="tdc"><c:out value="${row.ssn}"/></td>
                                <td width="14%" class="tdc"><c:out value="${row.name}"/></td>
                                <td width="14%" class="tdc"><c:out value="${row.password}"/></td>
                                <td width="14%" class="tdc"><c:out value="${row.role}"/></td>
                            </tr>
                            </c:forEach>
                        </table>
                        <%-- Details --%>
                        <sql:setDataSource 
                        var="snapshotroles" 
                        driver="org.apache.derby.jdbc.ClientDriver"
                        url="jdbc:derby://localhost:1527/ebooksstore;create=true;"
                        user="test"  
                        password="test"/>
                        <sql:query dataSource="${snapshotroles}" var="resultroles">
                            SELECT ROLE from EBOOKS.ROLES ORDER BY ROLE ASC 
                        </sql:query>
                        <table class="tablecenterdwithborder">
                            <tr>
                                <td>    
                                    <table>
                                        <tr>
                                            <td> SSN: </td>
                                            <td> <input type="text" name="admin_users_ssn"></input></td>
                                        </tr>                                        
                                        <tr>
                                            <td> USERNAME: </td>
                                            <td> <input type="text" name="admin_users_username"></input></td>
                                        </tr>
                                        <tr>
                                            <td> PASSWORD: </td>
                                            <td> <input type="password" name="admin_users_password"></input></td>
                                        </tr>
                                        <tr>
                                            <td> ROLE: </td>
                                            <td>
                                                <select name="admin_user_role" required="true">
                                                    <c:forEach var="rowrole" items="${resultroles.rows}">    
                                                        <option name="admin_users_roles" value="${rowrole.role}">${rowrole.role}</option>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                        </tr>     
                                    </table>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <%-- buttons --%>
                                    <table>

                                            <tr><td class="tdc"><input type="submit" class="ebooksstorebutton" name="admin_users_insert" value="Insert"></td> 
                                                <td class="tdc"><input type="submit" class="ebooksstorebutton" name="admin_users_update" value="Update"></td>
                                                <td class="tdc"><input type="submit" class="ebooksstorebutton" name="admin_users_delete" value="Delete"></td> 
                                                <td class="tdc"><input type="submit" class="ebooksstorebutton" name="admin_users_cancel" value="Cancel"></td>
                                            </tr>  
                                    </table>
                                </td>
                            </tr>
                        </table>    
                        </form>
            </c:when>
            <c:otherwise>
                <c:redirect url="./index.jsp"></c:redirect>
            </c:otherwise>
        </c:choose>
</body>    
</html>
