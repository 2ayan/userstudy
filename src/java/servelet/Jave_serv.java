/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servelet;

import retrievability.Try;
import retrievability.RetrievabilityFinder;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;
import java.util.Arrays;
import javax.servlet.RequestDispatcher;


/**
 *
 * @author AYANAVA
 */
public class Jave_serv extends HttpServlet {

    
    static HashMap <Integer, String> hashed_values=new HashMap<Integer, String>();
    
    
    static String query;
    String relevant_queries[];
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            query= request.getParameter("query");           //getting the user-entered query term
            String requestURL = request.getRequestURL().toString();     //getting the URL of the current page
            
            out.println("<center>");
            //out.println(query);
            
            out.println("<center/>");
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Jave_serv</title>");            
            out.println("</head>");
            out.println("<body>");
            
            out.println("</body>");
            out.println("</html>");
           
            /*FileWriter resultsFile = new FileWriter("C:\\Users\\AYANAVA\\Documents\\NetBeansProjects\\Web\\"
	        + System.getProperty("file.separator")
	        + "output" + ".txt", true);
            
            PrintWriter toFile = new PrintWriter(resultsFile);
            
            Enumeration values = request.getParameterNames();
            
            while(values.hasMoreElements()) {
                String name = (String)values.nextElement();
		String value = request.getParameterValues(name)[0];
                if(name.compareTo("submit") != 0) {
                    toFile.println(name + ": " + value);
                }
            }
            

	    
            */
            RetrievabilityFinder func=new RetrievabilityFinder(query);          //sending the query to Retrievality Finder to return a set of matched tweets
            
            hashed_values=(HashMap<Integer, String>) func.Mapping();
            
            
            response.sendRedirect(("display_results.jsp?fibSequence=" ));    //hashed_values passed for displaying the tweets on a jsp page
            
           
            
            
            
        }
        catch(Exception E){}
    }

   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            hashed_values.clear();
            System.out.println(query);
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(Jave_serv.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
                hashed_values.clear();
                System.out.println(query);
                processRequest(request, response);
                
                
        } catch (Exception ex) {
            Logger.getLogger(Jave_serv.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        }
    
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }
   
    public static HashMap<Integer, String> copytofile()
    {
        return hashed_values;
    }
    public static String query_return()
    {
        return query;
    }
    
}


