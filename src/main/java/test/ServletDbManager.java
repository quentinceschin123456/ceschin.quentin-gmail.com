/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

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
@WebServlet(name = "ServletDbManager", urlPatterns = {"/frontOffice/dbmanager"},asyncSupported = true,loadOnStartup = 1)
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
           Connection con = createConnexion();
            /*dropTableUser(con);
            deleteCategoryTable(con);
            deleteServiceTable(con);*/
           // on doit test pour l'insert
           if (! isUserTableExist(con)){
                createTableUser(con);
                generateUser(con);
           
           } 
           // on doit test pour l'insert
            if (!isCategoryTableExist(con)){
                createCategoryTable(con);
                insertCategoryTable(con);
            }
            // ne fait rien si existe d�j�
            createServiceTable(con);
                    
            
            getServletContext().getRequestDispatcher("/frontOffice/logForm.jsp").forward(request, response);  
            
            
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
            query += "id INT GENERATED ALWAYS as IDENTITY  PRIMARY KEY,";
            query += "nom VARCHAR(100),";
            query += "prenom VARCHAR(100),";
            query += "email VARCHAR(255),";          
            query += "mdp VARCHAR(255),";         
            query += "privilege INT)";


            ps.executeUpdate(query);    
            } catch (Exception e) {
                System.out.println("create user error");
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
    
    public static ResultSet selectListUser(Connection con){
      
        String query = "SELECT nom,prenom,email,mdp,privilege from USER";
        ResultSet rs = null;
        try {
            Statement ps = con.createStatement();
            rs = ps.executeQuery(query);    
            } catch (Exception e) {
                System.out.println("select user error");
            }
        finally{
            return rs;
        }
    }
    
    public static ResultSet selectUserByEmail(String email, Connection con){
         String query = "SELECT id from User where email=?";
        ResultSet rs = null;
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, email);
            rs = ps.executeQuery();    
            } catch (Exception e) {
                System.out.println("select user error");
            }
        finally{
            return rs;
        }
    }
    
    public void generateUser(Connection con) {
        String query = "INSERT INTO USER values ";
        query += "(null,'Bonbeure','Jean','jean.bonbeure@test.mail','azerty',1),";
        query += "(null,'Colique','Al','al.colique@test.mail','azerty',1),";
        query += "(null,'Jean','Damien','damien.jean@test.mail','azerty',1)";
        try {
         Statement ps = con.createStatement();
         ps.executeUpdate(query);    
            } catch (Exception e) {
                 System.out.println("erreur insert");
            }
    }
    
    public void deleteUser(Connection con, String name) {
         
         String query = "DELETE from USER where nom='"+name+"'";
        try {
            Statement ps = con.createStatement();
         ps.executeUpdate(query);    
            } catch (Exception e) {
                System.out.println("delete user");
            }
    }
    
    public void createServiceTable(Connection con){
        
        try {
            Statement ps = con.createStatement();
            String query = "CREATE TABLE SERVICE( ";
            query += "id INT  GENERATED ALWAYS as IDENTITY  PRIMARY KEY,";
            query += "titre VARCHAR(100),";
            query += "resume VARCHAR(255),";
            query += "uniteLoc VARCHAR(100),";
            query += "coutUnitaire FLOAT,";
            query += "UserId INT REFERENCES User(id),";
            query += "CategoryId INT REFERENCES Category(id)";
            query+=")";


            ps.executeUpdate(query);    
        } catch (Exception e) {
            System.out.println("error createServiceTable");
            System.out.println(e);
        }
    }
    
    public static ResultSet selectServiceByUser(String email){
        try {
            PreparedStatement ps = ServletDbManager.createConnexion().prepareStatement("select s.id as id,s.titre as titre,s.resume as resume,s.uniteLoc as uniteLoc,s.coutUnitaire as coutUnitaire ,s.UserId as UserId,s.CategoryId as CategoryId from service s,user u  where u.id = s.UserId and u.email=?");
            
            ps.setString(1, email);
                        
             
         ResultSet rs = ps.executeQuery();
            //System.out.println("test.ServletDbManager.selectServiceByUser()" + rs.next() + rs.getString("id"));
         return rs;
            } catch (Exception e) {
                 System.out.println("erreur selectServiceByUser ");
            }
        return null;
    }
    
    public static ResultSet selectServiceByCategory(String idCat){
         try {
            PreparedStatement ps = ServletDbManager.createConnexion().prepareStatement("select * from service where CategoryId=?");
            
            ps.setInt(1, Integer.parseInt(idCat.trim()));
                        
             
         ResultSet rs = ps.executeQuery();  
         return rs;
            } catch (Exception e) {
                 System.out.println("erreur SEELECT  service by cat");
            }
        return null;
                 
    }
    
    public static ResultSet selectServiceByTitre(String titre){
         try {
            PreparedStatement ps = ServletDbManager.createConnexion().prepareStatement("select * from service where titre=?");
            
            ps.setString(1, titre);
                         
         ResultSet rs = ps.executeQuery();  
         return rs;
            } catch (Exception e) {
                 System.out.println("erreur selectServiceByTitre");
            }
         return null;
    }
    
    public static boolean insertService(String titre, String resume,String uniteLoc, String coutUnitaire,String email,String idCat ){
        // retrive from email
            try {
             Connection con = ServletDbManager.createConnexion();   
            ResultSet res = selectUserByEmail(email,con);
            res.next();
            String idUser =res.getString("id");
            PreparedStatement ps = con.prepareStatement("INSERT INTO SERVICE values (null,?,?,?,?,?,?)");
            
            ps.setString(1, titre);
            ps.setString(2, resume);
            ps.setString(3, uniteLoc);
            ps.setString(4,coutUnitaire);
            ps.setString(5,idUser);
            ps.setInt(6,Integer.parseInt(idCat.trim()));
                        
             
         ps.executeUpdate();  
         return true;
            } catch (Exception e) {
                 System.out.println(e);
                 return false;
            }
        
        
    } 
    
    
    
    public void updateServiceTable(Connection con){
        
    }
    public void deleteServiceTable(Connection con){
                 String query = "DROP table SERVICES";
        try {
            Statement ps = con.createStatement();
         ps.executeUpdate(query);    
            } catch (Exception e) {
                System.out.println("delete user");
            }
    }
    public void insertServiceTable(Connection con){
        
    }
    public static ResultSet selectAllService(){
        Connection con = ServletDbManager.createConnexion();
        String query = "SELECT * from Service";
        ResultSet rs = null;
        try {
            Statement ps = con.createStatement();
            rs = ps.executeQuery(query);    
            } catch (Exception e) {
                System.out.println("select service list error");
            }
        finally{
            return rs;
        }
    }
    
     public void createCategoryTable(Connection con){
        
        try {
            Statement ps = con.createStatement();
            String query = "CREATE TABLE CATEGORY( ";
            query += "id INT GENERATED ALWAYS as IDENTITY  PRIMARY KEY,";
            query += "nom VARCHAR(100),";
            query += "resume VARCHAR(255))";



            ps.executeUpdate(query);    
        } catch (Exception e) {
            System.out.println("create cat errror");
        }
    }
    public void updateCategoryTable(Connection con){
        
    }
    public void deleteCategoryTable(Connection con){
        String query = "drop table CATEGORY";
        try {
            Statement ps = con.createStatement();
         ps.executeUpdate(query);    
            } catch (Exception e) {
                System.out.println("delete user");
            }
    }
    public void insertCategoryTable(Connection con){
        String query = "INSERT INTO Category (nom,resume) values ";
        query += "('Cours','Enseigment de connaissance en informatique',),";
        query += "('Jardinage','Culture du jardin'),";
        query += "('Bricolage','Petit travaux')";
        try {
         Statement ps = con.createStatement();
         ps.executeUpdate(query);    
            } catch (Exception e) {
                System.out.println("erreur insert");
            }
    }
    public static ResultSet selectAllCategory(){
         Connection con = ServletDbManager.createConnexion();
        String query = "SELECT * from Category";
        ResultSet rs = null;
        try {
            Statement ps = con.createStatement();
            rs = ps.executeQuery(query);    
            } catch (Exception e) {
                System.out.println("select cat list error");
            }
        finally{
            return rs;
        }
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
    
    //TODO Ajouter les connexion close
    public static boolean isUserReconized(String email,String pwd){
        try {
            PreparedStatement ps = ServletDbManager.createConnexion().prepareStatement("Select id from user where email=? and mdp=?");
            
            ps.setString(1, email);
            ps.setString(2, pwd);
            
            ResultSet rs = ps.executeQuery();
            rs.next();
            try {
                if (rs.getString("id")!=null){
                    System.out.println("ici");
                    return true;
                }else {
                    System.out.println("ici");

                    return false;
                }    
            }catch (Exception e){
                System.out.println("error");
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(ServletDbManager.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        return false;
        
    }

    public boolean isUserTableExist(Connection con) {
        

        String query = "SELECT Count(id) from USER";
        ResultSet rs = null;
        try {
            Statement ps = con.createStatement();
            rs = ps.executeQuery(query); 
            rs.next();
            if (rs != null){
                return  Integer.parseInt(rs.getString("Count(id)"))> 0 ;
            }
        } catch (Exception e) {
                System.out.println("error count");
                return false;
            }
       
       
        
       return false;
    }

    public boolean isCategoryTableExist(Connection con) {
        
         String query = "SELECT Count(id) from Category";
        ResultSet rs = null;
        try {
            Statement ps = con.createStatement();
            rs = ps.executeQuery(query); 
            rs.next();
            if (rs != null){
                return  Integer.parseInt(rs.getString("Count(id)"))> 0 ;
            }
        } catch (Exception e) {
                System.out.println("error count");
                return false;
            }
        return false;
    }
    
    //selection user return string prenom,nom,prestige,
    public static String getUserInfo(String email,String password){
        try {
            PreparedStatement ps = ServletDbManager.createConnexion().prepareStatement("Select * from user where email=? and mdp=?");
            
            ps.setString(1, email);
            ps.setString(2, password);
            
            ResultSet rs = ps.executeQuery();
            rs.next();
            try {
                System.out.println("QQQQQQQQQQQQQQQQQQQQQ" + rs.getString("id"));
                if (rs.getString("id")!=null){
                    System.out.println("MERDE");
                    System.out.println(rs.getString("prenom"));
                    String retour = rs.getString("prenom")+";"+rs.getString("nom")+";"+rs.getString("privilege");
                    return retour;
                }else {
                    return "";
                }    
            }catch (Exception e){
                return  "";
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(ServletDbManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
     //selection user return string prenom,nom,prestige,
    public static String getUserInfo(String email){
        try {
            PreparedStatement ps = ServletDbManager.createConnexion().prepareStatement("Select * from user where email=?");
            
            ps.setString(1, email);
            
            ResultSet rs = ps.executeQuery();
            rs.next();
            try {
                if (rs.getString("id")!=null){
                    String retour = rs.getString("prenom")+";"+rs.getString("nom")+";"+rs.getString("privilege");
                    return retour;
                }else {
                    return "";
                }    
            }catch (Exception e){
                return  "";
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(ServletDbManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    public static boolean insertNewUser(String nom,String prenom,String email,String mdp){
         try {
            PreparedStatement ps = ServletDbManager.createConnexion().prepareStatement("INSERT INTO USER values (null,?,?,?,?,0)");
            
            ps.setString(1, nom);
            ps.setString(2, prenom);
            ps.setString(3, email);
            ps.setString(4, mdp);
                        
             
         ps.executeUpdate();  
         return true;
            } catch (Exception e) {
                 System.out.println("erreur insert");
                 return false;
            }
    }
}
