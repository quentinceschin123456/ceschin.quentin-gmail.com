/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Xenol
 */
@WebServlet(name = "ServletUserManager", urlPatterns = {"/frontOffice/ServletUserManager"})
public class ServletUserManager extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            
            System.out.print(request.getRequestURI());
            
            String email = request.getParameter("EMAIL");
            String password = request.getParameter("PASSWORD");
            String error = "";
            
            if(email != null && password != null) {                             
                if(authentification(email, password)) {
                    HttpSession session = request.getSession();
                    session.setAttribute("identifiant", email);
                    String userInfos = ServletDbManager.getUserInfo(email, password);
                    System.out.println("BITE " + userInfos);
                    String[] user = userInfos.split(";");
                    request.setAttribute("PRENOM", user[0]);
                    request.setAttribute("NOM", user[1]);
                    request.setAttribute("PRESTIGE", user[2]);
                    getServletContext().getRequestDispatcher("/frontOffice/accueil.jsp").forward(request, response);
                } else {
                    error = "Authentification failed";
                    request.setAttribute("ERROR", error);
                    getServletContext().getRequestDispatcher("/frontOffice/logForm.jsp").forward(request, response);
                }   
            } else {
                
                String newPrenom = request.getParameter("NEWPRENOM");
                String newNom = request.getParameter("NEWNOM");
                String newEmail = request.getParameter("NEWEMAIL");
                String newPassword = request.getParameter("NEWPASSWORD");
                
                if(newEmail != null && newPassword != null) {
                    if(creationUser(newPrenom, newNom, newEmail, newPassword)) {
                        HttpSession session = request.getSession();
                        session.setAttribute("identifiant", email);
                        request.setAttribute("PRENOM", newPrenom);
                        request.setAttribute("NOM", newNom);
                        request.setAttribute("PRESTIGE", 0);
                        getServletContext().getRequestDispatcher("/frontOffice/accueil.jsp").forward(request, response);
                    } else {
                        error = "Record failed";
                        request.setAttribute("ERROR", error);
                        getServletContext().getRequestDispatcher("/frontOffice/creationCompte.jsp").forward(request, response);                       
                    }
                } else {
                    error = "Record failed";
                    request.setAttribute("ERROR", error);
                    getServletContext().getRequestDispatcher("/frontOffice/creationCompte.jsp").forward(request, response); 
                }
            }

        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
    public boolean authentification(String email, String password) {
        
        if(email.trim().isEmpty() || password.trim().isEmpty()) {
            return false;
        } else {
            if(ServletDbManager.isUserReconized(email, password)) {
                return true;
            } else {
                return false;
            }          
        }
    }    
    
    public boolean creationUser(String newPrenom, String newNom, String email, String password) {
        
        if(email.trim().isEmpty() || password.trim().isEmpty()) {
            return false;
        } else {
            if(ServletDbManager.insertNewUser(newNom, newPrenom, email, password)) {
                return true;
            } else {
                return false;
            }       
        }
    }
}
