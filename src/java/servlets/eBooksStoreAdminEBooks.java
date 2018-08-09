/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Mihai
 */
public class eBooksStoreAdminEBooks extends HttpServlet {

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
        // declare specific DB info
        String user = "test";
        String password = "test";
        String url = "jdbc:derby://localhost:1527/ebooksstore;create=true;";
        String driver = "org.apache.derby.jdbc.ClientDriver";
        // check push on Insert button
        if (request.getParameter("admin_ebooks_insert") != null) { // insert values from fields
            // set connection paramters to the DB
            // read values from page fields
            String isbn = request.getParameter("admin_ebooks_isbn");
            String denumire = request.getParameter("admin_ebooks_denumire");
            String pages = request.getParameter("admin_ebooks_pages");
            String price = request.getParameter("admin_ebooks_price");
            String id_type = request.getParameter("admin_ebooks_id_type");
            String id_quality = request.getParameter("admin_ebooks_id_paper_qualities");
            String id_genre = request.getParameter("admin_ebooks_id_genres");
            
            // declare specific DBMS operations variables
            ResultSet resultSet = null;
            Statement statement = null;
            Connection connection = null;
            PreparedStatement pstmnt = null;
            try {
                //check driver and create connection
                Class driverClass = Class.forName(driver);
                connection = DriverManager.getConnection(url, user, password);
                // insert into EBOOKS Table
                String DML = "INSERT INTO EBOOKS.EBOOKS VALUES (?, ?, ?, ?, ?, ?, ?)";
                pstmnt = connection.prepareStatement(DML);
                pstmnt.setString(1, isbn);
                pstmnt.setString(2, denumire);
                pstmnt.setString(3, id_type);
                pstmnt.setString(4, id_quality);
                pstmnt.setString(5, pages);
                pstmnt.setString(6, id_genre);
                pstmnt.setString(7, price);
                pstmnt.execute();

            } catch (ClassNotFoundException | SQLException ex) {
                // display a message for NOT OK
                Logger.getLogger(eBooksStoreAdminEBooks.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (resultSet != null) {
                    try {
                        resultSet.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(eBooksStoreAdminEBooks.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (statement != null) {
                    try {
                        statement.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(eBooksStoreAdminEBooks.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (pstmnt != null) {
                    try {
                        pstmnt.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(eBooksStoreAdminEBooks.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(eBooksStoreAdminEBooks.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                // redirect page to its JSP as view
                request.getRequestDispatcher("./eBooksStoreAdminEBooks.jsp").forward(request, response);
            } // check push on Update button
        } else if (request.getParameter("admin_ebooks_update") != null) { // update
            // declare specific variables
            ResultSet resultSet = null;
            Statement statement = null;
            PreparedStatement pstmnt = null;
            Connection connection = null;
            try {
                //check driver and create connection
                Class driverClass = Class.forName(driver);
                connection = DriverManager.getConnection(url, user, password);
                // identify selected checkbox and for each execute the update operation
                String[] selectedCheckboxes = request.getParameterValues("admin_ebooks_checkbox");
                String denumire = request.getParameter("admin_ebooks_denumire");
                String pages = request.getParameter("admin_ebooks_pages");
                String price = request.getParameter("admin_ebooks_price");
                String id_type = request.getParameter("admin_ebooks_id_type");
                String id_quality = request.getParameter("admin_ebooks_id_paper_qualities");
                String id_genre = request.getParameter("admin_ebooks_id_genres");
                // with values from "" field
                if (!(("".equals(denumire)) && ("".equals(pages) && ("".equals(price))))) {
                    // operate updates for all selected rows
                    for (String i : selectedCheckboxes) {
                        // realize update of all selected rows
                        String isbn = i;
                        // one type compination
                        if (("".equals(pages))&&("".equals(price))) {// denumire should be updated
                            // WORKS
                            String DML = "UPDATE EBOOKS.EBOOKS SET ISBN=?, DENUMIRE=? WHERE ISBN=?";
                            pstmnt = connection.prepareStatement(DML);
                            pstmnt.setString(1, isbn);
                            pstmnt.setString(2, denumire);
                            pstmnt.setString(3, isbn);
                        } else if (("".equals(denumire)&&("".equals(price)))) {// pages should be updated
                            // WORKS
                            String DML = "UPDATE EBOOKS.EBOOKS SET ISBN=?, PAGES=? WHERE ISBN=?";
                            pstmnt = connection.prepareStatement(DML);
                            pstmnt.setString(1, isbn);
                            pstmnt.setString(2, pages);
                            pstmnt.setString(3, isbn);
                        } else if (("".equals(denumire)&&("".equals(pages)))) {// price should be updated
                            // WORKS
                            String DML = "UPDATE EBOOKS.EBOOKS SET ISBN=?, PRET=? WHERE ISBN=?";
                            pstmnt = connection.prepareStatement(DML);
                            pstmnt.setString(1, isbn);
                            pstmnt.setString(2, price);
                            pstmnt.setString(3, isbn);
                        // two type compination
                        } else if ("".equals(price)) {// both denumire and pages should be updated 
                            // WORKS
                            String DML = "UPDATE EBOOKS.EBOOKS SET ISBN=?, DENUMIRE=?, PAGES=? WHERE ISBN=?";
                            pstmnt = connection.prepareStatement(DML);
                            pstmnt.setString(1, isbn);
                            pstmnt.setString(2, denumire);
                            pstmnt.setString(3, pages);
                            pstmnt.setString(4, isbn);
                        } else if ("".equals(pages)) {// both denumire and price should be updated
                            // WORKS
                            String DML = "UPDATE EBOOKS.EBOOKS SET ISBN=?, DENUMIRE=?, PRET=? WHERE ISBN=?";
                            pstmnt = connection.prepareStatement(DML);
                            pstmnt.setString(1, isbn);
                            pstmnt.setString(2, denumire);
                            pstmnt.setString(3, price);
                            pstmnt.setString(4, isbn);
                        } else if ("".equals(denumire)) {// both pages and price should be updated
                            // WORKS
                            String DML = "UPDATE EBOOKS.EBOOKS SET ISBN=?, PAGES=?, PRET=? WHERE ISBN=?";
                            pstmnt = connection.prepareStatement(DML);
                            pstmnt.setString(1, isbn);
                            pstmnt.setString(2, pages);
                            pstmnt.setString(3, price);
                            pstmnt.setString(4, isbn);
                        // three type compination   
                        } else {// denumire, pages and price should be updated
                            // WORKS
                            String DML = "UPDATE EBOOKS.EBOOKS SET ISBN=?, DENUMIRE=?, PAGES=?, PRET=? WHERE ISBN=?";
                            pstmnt = connection.prepareStatement(DML);
                            pstmnt.setString(1, isbn);
                            pstmnt.setString(2, denumire);
                            pstmnt.setString(3, pages);
                            pstmnt.setString(4, price);
                            pstmnt.setString(5, isbn);
                        }
                        boolean execute = pstmnt.execute();
                    }
                } else { 
                    // update one or more id_types, id_quality, id_genre for one or more books
                    for (String i : selectedCheckboxes) {
                        // realize update of all selected rows
                        String isbn = i;
                        String DML = "UPDATE EBOOKS.EBOOKS SET ID_TYPE=?,ID_QUALITY=?,ID_GENRE=? WHERE ISBN=?";
                        pstmnt = connection.prepareStatement(DML);
                        pstmnt.setString(1, id_type);
                        pstmnt.setString(2, id_quality);
                        pstmnt.setString(3, id_genre);
                        pstmnt.setString(4, isbn);
                        boolean execute = pstmnt.execute();
                    }
                }
            } catch (ClassNotFoundException | SQLException ex) {
                // display a message for NOT OK
                Logger.getLogger(eBooksStoreAdminEBooks.class.getName()).log(Level.SEVERE, null, ex);

            } finally {
                if (resultSet != null) {
                    try {
                        resultSet.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(eBooksStoreAdminEBooks.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (pstmnt != null) {
                    try {
                        pstmnt.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(eBooksStoreAdminEBooks.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(eBooksStoreAdminEBooks.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                // redirect page to its JSP as view
                request.getRequestDispatcher("./eBooksStoreAdminEBooks.jsp").forward(request, response);
            }
            // check push on Delete button
        } else if (request.getParameter("admin_ebooks_delete") != null) { // delete 
            // declare specific variables
            ResultSet resultSet = null;
            PreparedStatement pstmnt = null;
            Connection connection = null;
            try {
                //check driver and create connection
                Class driverClass = Class.forName(driver);
                connection = DriverManager.getConnection(url, user, password);
                // identify selected checkbox and for each execute the delete operation
                String[] selectedCheckboxes = request.getParameterValues("admin_ebooks_checkbox");
                // more critical DB operations should be made into a transaction
                connection.setAutoCommit(false);
                for (String i : selectedCheckboxes) {
                    // realize delete of all selected rows
                    String isbn = i;
                    String DML = "DELETE FROM EBOOKS.EBOOKS WHERE ISBN=?";
                    pstmnt = connection.prepareStatement(DML);
                    pstmnt.setString(1, isbn);
                    pstmnt.execute();
                }
                connection.commit();
                connection.setAutoCommit(true);
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(eBooksStoreAdminEBooks.class.getName()).log(Level.SEVERE, null, ex);
                if (connection != null) {
                    try {
                        connection.rollback();
                    } catch (SQLException ex1) {
                        Logger.getLogger(eBooksStoreAdminEBooks.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                }
            } finally {
                if (resultSet != null) {
                    try {
                        resultSet.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(eBooksStoreAdminEBooks.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (pstmnt != null) {
                    try {
                        pstmnt.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(eBooksStoreAdminEBooks.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (pstmnt != null) {
                    try {
                        pstmnt.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(eBooksStoreAdminEBooks.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (connection != null) {
                    try {
                        connection.setAutoCommit(true);
                    } catch (SQLException ex) {
                        Logger.getLogger(eBooksStoreAdminEBooks.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        try {
                            connection.close();
                        } catch (SQLException ex) {
                            Logger.getLogger(eBooksStoreAdminEBooks.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                // redirect page to its JSP as view
                request.getRequestDispatcher("./eBooksStoreAdminEBooks.jsp").forward(request, response);
            }
        } // check push on Cancel button
        else if (request.getParameter("admin_ebooks_cancel") != null) { // cancel
            request.getRequestDispatcher("./eBooksStoreAdminEBooks.jsp").forward(request, response);
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
        return "Servlet serves eBooksSoreAdminEBooks.JSP page";
    }// </editor-fold>

}
