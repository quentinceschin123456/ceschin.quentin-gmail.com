<%-- 
    Document   : accueil
    Created on : 2 avr. 2020, 14:27:52
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
        <h1>Bienvenue <% out.print(request.getAttribute("PRENOM") + " " + request.getAttribute("NOM"));             %></h1>
        <p>Vous avez un prestige de rang <% out.print(request.getAttribute("PRESTIGE"));             %></p>
        <br>
        <a href="creation_service.jsp">Gestionnaire des Services</a>
    </body>
</html>
