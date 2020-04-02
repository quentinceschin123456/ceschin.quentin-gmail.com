<%-- 
    Document   : creation_service
    Created on : 2 avr. 2020, 18:51:38
    Author     : Xenol
--%>

<%@page import="java.sql.ResultSet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Gestionnaire de Services</h1>
        <form action="ServletServiceManager" method="POST">
            <label id="titre" for="NEWTITRE">Titre</label>
            <input id="NEWTITRE" type="text" name="NEWTITRE">
            <br>
            <label id="resume" for="NEWRESUME">Résumé</label>
            <input id="NEWRESUME" type="text" name="NEWRESUME">
            <br>
            <label id="categorie" for="NEWCATEGORIE">Catégorie</label>
            <select id="NEWCATEGORIE" name="NEWCATEGORIE">
                <% 
                    ResultSet rs = (ResultSet) request.getAttribute("CATEGORIES");
                    while (rs.next()){
                %>
                <option value="<% out.println(rs.getString("ID")); %>"><% out.println(rs.getString("NOM")); %></option>
                <% } %>
            </select>
            <br>
            <label id="unitelocation" for="NEWULOCATION">Unité de location</label>
            <select id="NEWULOCATION" name="NEWULOCATION">
                <option value="h">heure</option>
                <option value="d">demi-journée</option> 
                <option value="j">jour</option> 
                <option value="s">semaine</option> 
            </select>
            <br>
            <label id="cout" for="NEWCOUT">Coût à l'unité</label>
            <input id="NEWCOUT" type="text" name="NEWCOUT">
            <br>
            <input id="submit" type="submit" name="SUBMIT">
            <div id="ERROR"><% if(request.getAttribute("ERROR") != null) out.println(request.getAttribute("ERROR")); %></div>
        </form>
    </body>
</html>
