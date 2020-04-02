<%-- 
    Document   : logForm
    Created on : 1 avr. 2020, 21:29:04
    Author     : Xenol
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>TODO supply a title</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <h1>Authentification</h1>
        <form action="ServletUserManager" method="POST">
            <label id="labelEmail" for="EMAIL">Email</label>
            <input id="EMAIL" type="email" name="EMAIL">
            <label id="labelPassword" for="PASSWORD">Password</label>
            <input id="PASSWORD" type="password" name="PASSWORD">
            <br>
            <input id="submit" type="submit" name="SUBMIT">
        </form>
        <a href="./creationCompte.jsp">Créer un compte</a>
        <div id="ERROR"><% if(request.getAttribute("ERROR") != null) out.println(request.getAttribute("ERROR")); %></div>
    </body>
</html>
