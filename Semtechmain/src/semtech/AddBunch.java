package semtech;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import coreservlets.ServletUtilities;

@WebServlet("/add1")
public class AddBunch extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String title = "Flowershower";
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		StringBuilder sb = new StringBuilder();
		sb.append(ServletUtilities.headWithTitle(title));
		sb.append(" <body> <script type=\"text/javascript\">");
		sb.append("function addFlower(){ var e = document.getElementById(\"Blumen\");");
		sb.append("var strUser = e.options[e.selectedIndex].value; var textUser = e.options[e.selectedIndex].text;");
		sb.append(" var ul = document.getElementById(\"Blumenstr\"); ");
		sb.append("if (!document.getElementById(textUser)) { var li = document.createElement(\"li\");");
		sb.append(" var label = document.createElement(\"label\"); 		label.setAttribute(\"for\",textUser);");
		sb.append("	label.setAttribute(\"class\",\"control-label\"); label.innerHTML=textUser;");
		sb.append("	var input = document.createElement(\"input\"); 		input.setAttribute(\"class\", \"trial\");");
		sb.append("	input.setAttribute(\"size\",\"2\"); 		input.type = \"text\";");
		sb.append("	input.name= textUser; 		input.value = 1;");
		sb.append("	input.id = textUser;	var hiinput = document.createElement(\"input\");");
		sb.append("	hiinput.type =\"hidden\"; 		hiinput.value = strUser; 		hiinput.name = textUser; ");
		sb.append("	li.appendChild(input); 	li.appendChild(label); 	li.appendChild(hiinput);");
		sb.append(" 	ul.appendChild(li); }");
		sb.append("else 	 { 	 document.getElementById(textUser).value= parseInt(document.getElementById(textUser).value)+1; 	 } ");
		sb.append("} </script> ");
		sb.append(ServletUtilities.getNavBar());
		sb.append("<div class=\"container\"> <div class=\"page-header\" id=\"banner\"> <div class=\"row\"> <div class=\"col-lg-6\">");
		sb.append("<h1> Flowershower</h1> <p class=\"lead\">Der Florist in ihrer N‰he</p> </div>"
				+ "<img alt=\"Flowershower der Blumenshop in ihrer N‰he\" width=\"200\""
				+ " src=\"bootstrap/blume.jpg\"> </div> </div> ");

		sb.append("<p>Zur Auswahl stehen: </p> <div class=\"row\"> <div class=\"col-lg-3\"> 	 <div class=\"form-group\">");
		sb.append(" <br> <select id=\"Blumen\" size=\"12\"multiple=\"\" class=\"form-control\"> ");
		sb.append("<optgroup label=\"Rosen\"> <option label=\"Gelbe Rose\" value =\"Rose,Gelb,2.50\">Gelbe Rose</option>"
				+ "<option label=\"Rote Rose\" value=\"Rose,Rot,2.50\">Rote Rose</option>"
				+ "<option label=\"Blaue Rose\" value=\"Rose,Blau,2.50\">Blaue Rose</option>"
				+ "<option label=\"Weiﬂe Rose\" value=\"Rose,Weiss,2.50\">Weiﬂe Rose</option></optgroup>");
		sb.append(" <optgroup label=\"Tulpen\">"
				+ "<option label=\"Gelbe Tulpe\" value=\"Tulpe,Gelb,2\">Gelbe Tulpe</option>"
				+ " <option label=\"Rote Tulpe\" value=\"Tulpe,Rot,2\">Rote Tulpe</option>"
				+ " <option label=\"Blaue Tulpe\" value=\"Tulpe,Blau,2\">Blaue Tulpe</option>"
				+ " <option label=\"Weiﬂe Tulpe\" value=\"Tulpe,Weiss,2\">Weiﬂe Tulpe</option>"
				+ " </optgroup>");
		sb.append(" <optgroup label=\"Nelke\"> "
				+ " <option label=\"Gelbe Nelke\" value=\"Nelke,Gelb,1.5\">Gelbe Nelke</option>"
				+ " <option label=\"Rote Nelke\" value=\"Nelke,Rot,1.5\">Rote Nelke</option>"
				+ " <option label=\"Blaue Nelke\" value=\"Nelke,Blau,1.5\">Blaue Nelke</option>"
				+ " <option label=\"Weiﬂe Nelke\" value=\"Nelke,Weiss,1.5\">Weiﬂe Nelke</option>"
				+ " </optgroup>)");
		sb.append("<optgroup label=\"Gerbera\">"
				+ " <option label=\"Gelbe Gerbera\" value =\"Gerbera,Gelb,2.50\">Gelbe Gerbera</option>"
				+ " <option label=\"Rote Gerbera\" value=\"Gerbera,Rot,2.50\">Rote Gerbera</option>"
				+ " <option label=\"Blaue Gerbera\" value=\"Gerbera,Blau,2.50\">Blaue Gerbera</option>"
				+ " <option label=\"Weiﬂe Gerbera\" value=\"Gerbera,Weiss,2.50\">Weiﬂe Gerbera</option>"
				+ " </optgroup>");
		sb.append("<optgroup label=\"Lilie\"> "
				+ " <option label=\"Gelbe Lilie\" value=\"Lilie,Gelb,1.5\">Gelbe Lilie</option>"
				+ " <option label=\"Rote Lilie\" value=\"Lilie,Rot,1.5\">Rote Lilie</option>"
				+ " <option label=\"Blaue Lilie\" value=\"Lilie,Blau,1.5\">Blaue Lilie</option>"
				+ " <option label=\"Weiﬂe Lilie\" value=\"Lilie,Weiss,1.5\">Weisse Lilie</option>"
				+ " </optgroup>");
		sb.append(" <optgroup label=\"Narzisse\">"
				+ " <option label=\"Gelbe Narzisse\" value=\"Narzisse,Gelb,1.5\">Gelbe Narzisse</option>"
				+ " <option label=\"Weiﬂe Narzisse\" value=\"Narzisse,Weiss,1.5\">Weiss Narzisse</option>"
				+ " </optgroup>");
		sb.append(" </select> </div> </div><div class=\"col-lg-3\"> 		 <div class=\"form-group1\"> 	 <br></br> 	 <br></br>");
		sb.append("<button type=\"button\" name =\"add\" class=\"btn btn-primary\" onclick=\"addFlower()\">Blume zu Strauﬂ hinzuf¸gen</button>");
		sb.append("</div> </div> 	 <div class=\"col-lg-3\"> 	 <div class=\"trial\">");
		sb.append(" <form action=\"create\" method=\"post\" accept-charset=\"ISO-8859-1\">"
				+ " <button type=\"submit\" class=\"btn btn-primary\" >Strauﬂ abschicken</button>"
				+ "  <ul id=\"Blumenstr\">");
		sb.append("</ul> </form> </div> 	 </div> 	</div> 	</div> 	</body> 	</html>");

		out.println(sb.toString());

	}
}
