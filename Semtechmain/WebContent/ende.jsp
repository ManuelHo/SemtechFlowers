<%@ page language="java" contentType="text/html; charset=ISO-8859-15"
    pageEncoding="ISO-8859-1"%>
    <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    <%@ page import="java.util.*" %>
<%@ page import="coreservlets.ServletUtilities" %>
<%= ServletUtilities.headWithTitle("FlowerShower") %>

<body>
<%= ServletUtilities.getNavBar() %>

 <div class="container">
<div class="page-header" id="banner">
        <div class="row">
          <div class="col-lg-6">
            <h1>  Flowershower</h1>
            <p class="lead">Der Florist in ihrer Nähe</p>
          </div>
                      <img alt="Flowershower der Blumenshop in ihrer Nähe" width="200" src="${pageContext.request.contextPath}/bootstrap/blume.jpg">        
        </div>        
      </div>
 
 <%
 String[] added = request.getParameterValues("extra");
     %>    
      <p>Vielen Dank für den Einkauf ihres Straußes und  
      <ul>
      <% for(int i = 0; i < added.length; i++)
      {
    	  out.println("<li>" + added[i] + "</li>");
      }
    	 %>
    	 </ul></p>
      </div>
</body>
</html>