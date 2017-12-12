<%-- 
    Document   : RegistrationStatus
    Created on : Mar 23, 2017, 12:31:51 PM
    Author     : Derian
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registration Status</title>
        <% String status = (String)request.getAttribute("data"); %>
    </head>
    <body>
        <h1>
            Registration Status: <br>
            <%= status %><br>
            <div>
                <br>
                <a href="Registration.jsp" class="buttonBar">Return to Registration Page</a>
                <br>
                <a href="index.jsp" class="buttonBar">Return to Login page</a>
            </div>           
        </h1>
    </body>
</html>
