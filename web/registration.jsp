<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%-- 
    Document   : registration
    Created on : 12 Jul, 2016, 1:36:12 PM
    Author     : AYANAVA
--%>

<%@ page import ="java.sql.*" %>
<%
    String user = request.getParameter("uname");    
    String pwd = request.getParameter("pass");
    String fname = request.getParameter("fname");
    String lname = request.getParameter("lname");
    String email = request.getParameter("email");
  //  System.err.println(user+" "+pwd);
    Class.forName("com.ibm.db2.jcc.DB2Driver");
    Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/User_Information",
            "root", "root");
    Statement st = con.createStatement();
    //ResultSet rs;
    int i = st.executeUpdate("insert into User_Information(fname, lname, email, user_id, pwd) values ('" + fname + "','" + lname + "','" + email + "','" + user + "','" + pwd + "')");
    if (i > 0) {
        //session.setAttribute("userid", user);
        response.sendRedirect("index_start.jsp");
       // out.print("Registration Successfull!"+"<a href='index.jsp'>Go to Login</a>");
    } else {
        response.sendRedirect("index_start.jsp");
    }
%>