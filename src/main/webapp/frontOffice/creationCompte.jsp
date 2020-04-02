<%-- 
    Document   : creationCompte
    Created on : 2 avr. 2020, 14:34:23
    Author     : Xenol
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Création de compte</h1>
        <form action="ServletUserManager" method="POST">
            <label id="labelPrenom" for="NEWPRENOM">Prénom</label>
            <input id="NEWPRENOM" type="text" name="NEWPRENOM">
            <br>
            <label id="labelNom" for="NEWNOM">Nom</label>
            <input id="NEWNOM" type="text" name="NEWNOM">
            <br>
            <label id="labelEmail" for="NEWEMAIL">Email</label>
            <input id="NEWEMAIL" type="email" name="NEWEMAIL">
            <br>
            <label id="labelPassword" for="NEWPASSWORD">Password</label>
            <input id="NEWPASSWORD" type="password" name="NEWPASSWORD">
            <br>
            <input id="submit" type="submit" name="SUBMIT">
            <div id="ERROR"><% if(request.getAttribute("ERROR") != null) out.println(request.getAttribute("ERROR")); %></div>
        </form>
    </body>
</html>
