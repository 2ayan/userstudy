<%-- 
    Document   : try
    Created on : 21 Jul, 2016, 2:27:27 PM
    Author     : AYANAVA
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.io.*" %>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*" %>
<%@ page import="servelet.Jave_serv" %>
<%@page import="retrievability.UserQueryIndex" %>
<%@page import="retrievability.Pagination" %>
<!DOCTYPE html>

    <head>
        <style>
            th, td {
    padding: 15px;
}
            
        </style>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Similar Queries</title>
    </head>
    
        
        
        <%  
            HashMap <Integer, String> hashed_values=new HashMap<Integer, String>();
            HashMap <Integer, String> relevant_docs=new HashMap<Integer, String>();
            
            int sub=Pagination.size_temp();
            
            hashed_values=Pagination.ten_at_a_time();
            String []submit = request.getParameterValues("relevant");
            
            
            if((sub>=9))
            {
                out.println("<form name='form1' method='post' action='try.jsp' >");
                out.println("<div align='center'>");
                out.println("<table>");
            
                out.println("<tr>");
                out.println("<td>");
            
                out.println("<tr/>");
                out.println("<td/>");
            
                               
                for (Map.Entry<Integer, String> entry : hashed_values.entrySet()) {
                
                
                    String temp=(entry.getValue());
                    out.println(entry.getKey()+" : "+entry.getValue());
                
                    out.println("\t");
                %>
                
                
                <input type=checkbox name=relevant value="<%=temp  %>" />
                
                <%
                    out.println("<br> ");
                    temp="";
                
            }
            out.println("<p>"+"<INPUT TYPE='SUBMIT' NAME='NEXT' VALUE='NEXT'>"+"</p>");
            out.println("<table/>");
            out.println("<form/>");
            
            out.println("Relevant selected documents are-----");
            
            if (submit != null) 
            {
                Pagination.clear_relevant_docs();
                Pagination.set_relevant_docs(submit);
                
                relevant_docs=Pagination.get_relevant_docs();
                
                
                out.println("<table>");
                out.println("<tr>");
                for (int i = 0; i <relevant_docs.size(); i++) 
                {
                    out.println("<br/> ");
                    out.println("<div align='center'>");
                    out.println("<br/> ");
                    
                    out.println (relevant_docs.get(i));
                    out.println();
                }
                
            }
            else
            {
                Pagination.clear_relevant_docs();
                
                relevant_docs=Pagination.get_relevant_docs();
                
                
                out.println("<table>");
                out.println("<tr>");
                for (int i = 0; i <relevant_docs.size(); i++) 
                {
                    out.println("<br/> ");
                    out.println("<div align='center'>");
                    out.println("<br/> ");
                    
                    out.println (relevant_docs.get(i));
                    out.println();
                }
                
            }
    }

            else if((sub<9)&&(sub>0))
            {
                out.println("<form name='form2' method='post' action='relevance.jsp'  >");
                out.println("<div align='center'>");
                out.println("<table>");
            
                out.println("<tr>");
                out.println("<td>");
                
                out.println("<tr/>");
                out.println("<td/>");
            
           
                for (Map.Entry<Integer, String> entry : hashed_values.entrySet()) {
                
                
                    String temp=(entry.getValue());
                    out.println(entry.getKey()+" : "+entry.getValue());
                    //out.println(entry.getValue());
                    out.println("\t");
                %>
                
                
                <input type=checkbox name=relevant value="<%=temp  %>" />
                
                <%
                    out.println("<br> ");
                
                
                
               
                    temp="";
                
            }
            
            
            out.println("<p>"+"<INPUT TYPE='SUBMIT' NAME='submit' VALUE='SUBMIT'>"+"</p>");
            
            out.println("<table/>");
            out.println("<form/>");
            
            out.println("Relevant selcted documents are-----");
            
            if (submit != null) 
            {
                Pagination.clear_relevant_docs();
                Pagination.set_relevant_docs(submit);
                
                relevant_docs=Pagination.get_relevant_docs();
                
                
                out.println("<table>");
                out.println("<tr>");
                for (int i = 0; i <relevant_docs.size(); i++) 
                {
                    out.println("<br/> ");
                    out.println("<div align='center'>");
                    out.println("<br/> ");
                    
                    out.println (relevant_docs.get(i));
                    out.println();
                    Pagination.set_counter_to_zero();
                }
                
            }
            else
            {
               

                relevant_docs=Pagination.get_relevant_docs();
                
                
                out.println("<table>");
                out.println("<tr>");
                for (int i = 0; i <relevant_docs.size(); i++) 
                {
                    out.println("<br/> ");
                    out.println("<div align='center'>");
                    out.println("<br/> ");
                    
                    out.println (relevant_docs.get(i));
                    out.println();
                    Pagination.set_counter_to_zero();
                }

                
            }
            
            }

            else if(sub==0)
            {
                out.println("reached zero");

                relevant_docs=Pagination.get_relevant_docs();

                out.println("<form name='form3' method='post' action='relevance.jsp'  >");
                
                
                out.println("<table>");
                out.println("<tr>");
                for (int i = 0; i <relevant_docs.size(); i++) 
                {
                    out.println("<br/> ");
                    out.println("<div align='center'>");
                    out.println("<br/> ");
                    
                    out.println (relevant_docs.get(i));
                    out.println();
                    Pagination.set_counter_to_zero();
                }
                
            
                

                out.println("<p>"+"<INPUT TYPE='SUBMIT' NAME='submit' VALUE='SUBMIT'>"+"</p>");
            }
            %>
            
            
    

