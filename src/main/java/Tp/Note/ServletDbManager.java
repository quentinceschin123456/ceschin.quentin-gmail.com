/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tp.Note;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 *
 * @author AdminEtu
 */
@WebServlet(name = "ServletDbManager", urlPatterns = {"/ServletDbManager"})
public class ServletDbManager extends HttpServlet {

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
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ServletDbManager</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ServletDbManager at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
 public void createTableUser(Connection con){
         
        try {
            Statement ps = con.createStatement();
            String query = "CREATE TABLE USER( ";
            query += "id INT PRIMARY KEY NOT NULL,";
            query += "nom VARCHAR(100),";
            query += "prenom VARCHAR(100),";
            query += "email VARCHAR(255),";          
            query += "mdp VARCHAR(255),";         
            query += "privilege INT)";


            ps.executeUpdate(query);    
            } catch (Exception e) {
                System.out.println(e);
            }
    }
    public void dropTableUser(Connection con){
        
        try {
             Statement ps = con.createStatement();
            ps.executeUpdate("DROP TABLE USER");    
            } catch (Exception e) {
                System.out.println(e);
            }
    }
    public void resetTableUser(Connection con) throws SQLException{
        dropTableUser(con);
        createTableUser(con);
        generateUser(con);
        // create cate
        // insert cate
        
        // create service
        
    }
    
    public ResultSet selectListUser(Connection con){
      
        String query = "SELECT nom,prenom,email,privilege from USER";
        ResultSet rs = null;
        try {
            Statement ps = con.createStatement();
            rs = ps.executeQuery(query);    
            } catch (Exception e) {
                System.out.println(e);
            }
        finally{
            return rs;
        }
    }
    
    public void generateUser(Connection con) {
        String query = "INSERT INTO USER (nom,prenom,email,privilege) values ";
        query += "('Bonbeure','Jean','jean.bonbeure@test.mail,1'),";
        query += "('Colique','Al','al.colique@test.mail,1'),";
        query += "('Jean','Damien','damien.jean@test.mail,1')";
        try {
         Statement ps = con.createStatement();
         ps.executeUpdate(query);    
            } catch (Exception e) {
                System.out.println(e);
            }
    }
    
    public void deleteUser(Connection con, String name) {
         
         String query = "DELETE from USER where nom='"+name+"'";
        try {
            Statement ps = con.createStatement();
         ps.executeUpdate(query);    
            } catch (Exception e) {
                System.out.println(e);
            }
    }
    
    public void createServiceTable(Connection con){
        
        try {
            Statement ps = con.createStatement();
            String query = "CREATE TABLE SERVICE( ";
            query += "id INT PRIMARY KEY NOT NULL,";
            query += "titre VARCHAR(100),";
            query += "uniteLoc VARCHAR(100),";
            query += "coutUnitaire FLOAT )";



            ps.executeUpdate(query);    
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void updateServiceTable(Connection con){
        
    }
    public void deleteServiceTable(Connection con){
        
    }
    public void insertServiceTable(Connection con){
        
    }
    public ResultSet selectAllService(Connection con){
        ResultSet rs = null;
        
        return rs;
    }
     public void createCategoryTable(Connection con){
        
        try {
            Statement ps = con.createStatement();
            String query = "CREATE TABLE SERVICE( ";
            query += "id INT PRIMARY KEY NOT NULL,";
            query += "titre VARCHAR(100),";
            query += "uniteLoc VARCHAR(100),";
            query += "coutUnitaire FLOAT )";



            ps.executeUpdate(query);    
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void updateCategoryTable(Connection con){
        
    }
    public void deleteCategoryTable(Connection con){
        
    }
    public void insertCategoryTable(Connection con){
        
    }
    public ResultSet selectAllCategory(Connection con){
        ResultSet rs = null;
        
        return rs;
    }
    
    
    public static Connection createConnexion(){
        Context initCtx=null;
        try {
            initCtx = new InitialContext();
        } catch (NamingException ex) {
            System.out.println("Erreur de chargement du service de nommage");
        } 

        // Connexion ? la base de donn?es enregistr?e dans le serveur de nom sous le nom "sample"
        Object refRecherchee = null;
        try {
            refRecherchee = initCtx.lookup("jdbc/__default");
        } catch (NamingException ex) {
            Logger.getLogger(ServletDbManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        DataSource ds = (DataSource)refRecherchee;
        Connection con = null;
        try {
            con = ds.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(ServletDbManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }
    
    public static boolean isUserReconized(String email,String pwd){
        try {
            PreparedStatement ps = ServletDbManager.createConnexion().prepareStatement("Select id from user where email=? and mdp=?");
            
            ps.setString(1, email);
            ps.setString(2, pwd);
            
            ResultSet rs = ps.executeQuery();
            rs.next();
            try {
                if (rs.getString("id")!=null){
                    return true;
                }else {
                    return false;
                }    
            }catch (Exception e){
                
            }finally {
                return  false;
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(ServletDbManager.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        return false;
        
    }
}
