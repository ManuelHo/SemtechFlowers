package coreservlets;


import javax.servlet.http.*;

/** Some simple time savers. Static methods. */

public class ServletUtilities {
  public static String headWithTitle(String title) {
	  

StringBuilder sb = new StringBuilder();
sb.append("<html>");
sb.append("<link rel=\"stylesheet\" href=\"/Semtechmain/bootstrap/css/bootstrap.min.css\" />");
//sb.append("<link rel=\"stylesheet\"  href=\"${pageContext.request.contextPath}/bootstrap/css/bootstrap-theme.min.css\">");
		sb.append("<script src=\"${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js\"></script>");
				sb.append("<head>");
						sb.append("<title>" +  title + "</title></head>");


						return(sb.toString());
	 

  }
  
  public static String getNavBar(){
	  StringBuilder nav = new StringBuilder();
	  nav.append("<div class=\"navbar navbar-default\">");
	  nav.append("<div class=\"navbar-header\">");
	  nav.append("  <button type=\"button\" class=\"navbar-toggle\" data-toggle=\"collapse\" data-target=\".navbar-inverse-collapse\">");
	  nav.append("   <span class=\"icon-bar\"></span>");
	  nav.append("    <span class=\"icon-bar\"></span>");
	  nav.append("   <span class=\"icon-bar\"></span>");
	  nav.append(" </button>");
	  nav.append(" <a class=\"navbar-brand\" href=\"index\">Flowershower</a>");
	  nav.append("</div>");
	  
	  nav.append("<div class=\"navbar-collapse collapse navbar-inverse-collapse\">");
	  nav.append("<ul class=\"nav navbar-nav\">");
	  nav.append("<li><a href=\"add.jsp\">Create Strauﬂ</a></li>");
			 // nav.append("   <button type=\"button\" class=\"navbar-toggle\" data-toggle=\"collapse\" data-target=\".navbar-inverse-collapse\">");
	  nav.append("</ul> </div>");

	  
	  
	  nav.append("");
	  nav.append(" </div>");

	  return nav.toString();
  }
  


  /** Read a parameter with the specified name, convert it
   *  to an int, and return it. Return the designated default
   *  value if the parameter doesn't exist or if it is an
   *  illegal integer format.
   */
  
  public static int getIntParameter(HttpServletRequest request,
                                    String paramName,
                                    int defaultValue) {
    String paramString = request.getParameter(paramName);
    int paramValue;
    try {
      paramValue = Integer.parseInt(paramString);
    } catch(Exception nfe) { // null or bad format
      paramValue = defaultValue;
    }
    return(paramValue);
  }
  
  
  
  public static String getStringParameter(HttpServletRequest request,
          String paramName,
          String defaultValue) {
String paramString = request.getParameter(paramName);
String paramValue;
try {
paramValue = paramString.toString();
} catch(Exception nfe) { // null or bad format
paramValue = defaultValue;
}
return(paramValue);
}


  /** Reads param and converts to double. Default if problem. */
  
  public static double getDoubleParameter(HttpServletRequest request,
                                          String paramName,
                                          double defaultValue) {
    String paramString = request.getParameter(paramName);
    double paramValue;
    try {
      paramValue = Double.parseDouble(paramString);
    } catch(Exception nfe) { // null or bad format
      paramValue = defaultValue;
    }
    return(paramValue);
  }

  /** Replaces characters that have special HTML meanings
   *  with their corresponding HTML character entities.
   *  Specifically, given a string, this method replaces all 
   *  occurrences of  
   *  {@literal
   *  '<' with '&lt;', all occurrences of '>' with
   *  '&gt;', and (to handle cases that occur inside attribute
   *  values), all occurrences of double quotes with
   *  '&quot;' and all occurrences of '&' with '&amp;'.
   *  Without such filtering, an arbitrary string
   *  could not safely be inserted in a Web page.
   *  }
   */

  public static String filter(String input) {
    if (!hasSpecialChars(input)) {
      return(input);
    }
    StringBuilder filtered = new StringBuilder(input.length());
    char c;
    for(int i=0; i<input.length(); i++) {
      c = input.charAt(i);
      switch(c) {
        case '<': filtered.append("&lt;"); break;
        case '>': filtered.append("&gt;"); break;
        case '"': filtered.append("&quot;"); break;
        case '&': filtered.append("&amp;"); break;
        default: filtered.append(c);
      }
    }
    return(filtered.toString());
  }

  private static boolean hasSpecialChars(String input) {
    boolean flag = false;
    if ((input != null) && (input.length() > 0)) {
      char c;
      for(int i=0; i<input.length(); i++) {
        c = input.charAt(i);
        switch(c) {
          case '<': flag = true; break;
          case '>': flag = true; break;
          case '"': flag = true; break;
          case '&': flag = true; break;
        }
      }
    }
    return(flag);
  }
  
  private ServletUtilities() {} // Uninstantiatable class
}
