<%@page import="java.util.Map"%>
<%@page import="retrievability.Pagination"%>
<%@page import="java.util.HashMap"%>
<!DOCTYPE html>
<html>
<head>
<style>
div.container {
    width: 100%;
    border: 0px solid gray;
}



nav {
    float: left;
    max-width: 500px;
    margin: 0;
    padding: 1em;
}

nav ul {
    list-style-type: none;
    padding: 0;
}
			
nav ul a {
    text-decoration: none;
}

article {
    margin-left: 700px;
    border-left: 1px solid gray;
    padding: 1em;
    overflow: hidden;
}
</style>
</head>
<body>

<div class="container">

<%  
            HashMap <Integer, String> hashed_values=new HashMap<Integer, String>();
            HashMap <Integer, String> relevant_docs=new HashMap<Integer, String>();
            
            int sub=Pagination.size_temp();
            
            hashed_values=Pagination.ten_at_a_time();
            String []submit = request.getParameterValues("relevant");
           
%>
    
<nav>
  <ul>
    
    <%  
    if((sub>=9))
            {
                out.println("<form name='form1' method='post' action='div.jsp' >");
                 
                for (Map.Entry<Integer, String> entry : hashed_values.entrySet()) {
                
                
                    String temp=(entry.getValue());
                    out.println("<p>");
                    out.println(entry.getKey()+" : "+entry.getValue());
                    
                    out.println("\t");

%>

     <input type=checkbox name=relevant value="<%=temp  %>" />
     
<%
        
            
                    temp="";
                //out.println("</p>");
            }
            out.println("<p>"+"<INPUT TYPE='SUBMIT' NAME='NEXT' VALUE='NEXT'>"+"</p>");
            
            out.println("<form/>");
}
    
  

%>        
    
    
  </ul>
</nav>

<article>
    
  <h1> Relevant selected documents are----- </h1>
  
  <%
  if (submit != null) 
            {
                Pagination.clear_relevant_docs();
                Pagination.set_relevant_docs(submit);
                
                relevant_docs=Pagination.get_relevant_docs();
                
                
                for (int i = 0; i <relevant_docs.size(); i++) 
                {
                    out.println("<br> ");
                    out.println("<b> ");
                    out.println("<div align='center'>");
                    out.println("<b/> ");
                    out.println("<br/> ");
                    
                    out.println (relevant_docs.get(i));
                    out.println();
                }
                
            }

            else
            {
                Pagination.clear_relevant_docs();
                
                relevant_docs=Pagination.get_relevant_docs();
                
                
                
                for (int i = 0; i <relevant_docs.size(); i++) 
                {
                    out.println("<br/> ");
                    out.println("<div align='center'>");
                    out.println("<br/> ");
                    
                    out.println (relevant_docs.get(i));
                    out.println();
                }
                
            }
%>
  
</article>

</div>

</body>
</html>
