<%-- 
    Document   : accueil
    Created on : 2 avr. 2020, 14:27:52
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
        <h1>Bienvenue <% out.print(request.getAttribute("PRENOM") + " " + request.getAttribute("NOM"));             %></h1>
        <p>Vous avez un prestige de rang <% out.print(request.getAttribute("PRESTIGE"));             %></p>
        <br>
        <table border='4>'
            <tr><th>Nom</th>
            <% 
            if(request.getAttribute("resultSelect") != null) {
                ResultSet rs = (ResultSet) request.getAttribute("resultSelect");
                while (rs.next()){
             %>
                <tr>
                    <td><%out.println(rs.getString("titre"));%></td>
                     <td><%out.println(rs.getString("resume"));%></td>
                      <td><%out.println(rs.getString("uniteLoc"));%></td>
                       <td><%out.println(rs.getString("coutUnitaire"));%></td>
                        <td><%out.println(rs.getString("UserId"));%></td> <td>
                            <%out.println(rs.getString("CategoryId"));%></td>
                        
                </tr>
            <% }} %>
            </table>
        
        
        <br>
        <a href="ServletServiceManager">Gestionnaire des Services</a>
        
    </body>
</html>
