
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.util.*" %>
<%@ page import="coreservlets.ServletUtilities" %>
<%= ServletUtilities.headWithTitle("FlowerShower") %>

<body>
<script type="text/javascript">
function addFlower()
{
	 var e = document.getElementById("Blumen");
     var strUser = e.options[e.selectedIndex].value;
     var textUser = e.options[e.selectedIndex].text;
     var ul = document.getElementById("Blumenstr");
         
     if (!document.getElementById(textUser)) {
    	 
 
    	
    	 var li = document.createElement("li");
    		var label = document.createElement("label");
    		label.setAttribute("for",textUser);
    		label.setAttribute("class","control-label");
    		//label.for = textUser;
    		label.innerHTML=textUser;
    		var input = document.createElement("input");
    		input.setAttribute("class", "trial");
    		input.setAttribute("size","2");
    		input.type =  "text";
    		input.name= textUser;
    		input.value = 1;
    		input.id = textUser;
			
    		var hiinput = document.createElement("input");
    		hiinput.type ="hidden";
    		hiinput.value = strUser;
    		hiinput.name = textUser; 
    	
    	li.appendChild(input);
    	li.appendChild(label);
    	li.appendChild(hiinput);

    	ul.appendChild(li);
     }
     else
    	 {
    	 //alert("Element does exist. Let's add it.");
    	 document.getElementById(textUser).value= parseInt(document.getElementById(textUser).value)+1;
    	 }
	
}
</script>


 <%= ServletUtilities.getNavBar() %>

 <div class="container">
<div class="page-header" id="banner">
        <div class="row">
          <div class="col-lg-6">
            <h1>  Flowershower</h1>
            <p class="lead">Der Florist in Ihrer N�he</p>
          </div>
                      <img alt="Flowershower der Blumenshop in Ihrer N�he" width="200" src="${pageContext.request.contextPath}/bootstrap/blume.jpg">        
        </div>        
      </div>
      
      
  <p>Zur Auswahl stehen:</p>
<div class="row">

      <div class="col-lg-3">
	  <div class="form-group">
        <br>
         <select id="Blumen" size="12" class="form-control">
      <optgroup label="Rosen">
        <option label="Gelbe Rose" value ="Rose,Gelb,2.50">Gelbe Rose</option>
        <option label="Rote Rose" value="Rose,Rot,2.50">Rote Rose</option>
        <option label="Blaue Rose" value="Rose,Blau,2.50">Blaue Rose</option>
        <option label="Wei�e Rose" value="Rose,Weiss,2.50">Wei�e Rose</option>
      </optgroup>
      <optgroup label="Tulpen">
        <option label="Gelbe Tulpe" value="Tulpe,Gelb,2">Gelbe Tulpe</option>
        <option label="Rote Tulpe" value="Tulpe,Rot,2">Rote Tulpe</option>
        <option label="Blaue Tulpe" value="Tulpe,Blau,2">Blaue Tulpe</option>
        <option label="Wei�e Tulpe" value="Tulpe,Weiss,2">Wei�e Tulpe</option>
      </optgroup>
       <optgroup label="Gerbera">
        <option label="Gelbe Gerbera" value ="Gerbera,Gelb,2.50">Gelbe Gerbera</option>
        <option label="Rote Gerbera" value="Gerbera,Rot,2.50">Rote Gerbera</option>
        <option label="Blaue Gerbera" value="Gerbera,Blau,2.50">Blaue Gerbera</option>
         <option label="Wei�e Gerbera" value="Gerbera,Weiss,2.50">Blaue Gerbera</option>
      </optgroup>
       <optgroup label="Lilie">
        <option label="Gelbe Lilie" value="Lilie,Gelb,1.5">Gelbe Lilie</option>
        <option label="Rote Lilie" value="Lilie,Rot,1.5">Rote Lilie</option>
        <option label="Blaue Lilie" value="Lilie,Blau,1.5">Blaue Lilie</option>
          <option label="Wei�e Lilie" value="Lilie,Weiss,1.5">Wei�e Lilie</option>
      </optgroup>
       <optgroup label="Narzisse">
        <option label="Gelbe Narzisse" value="Narzisse,Gelb,1.5">Gelbe Narzisse</option>
          <option label="Wei�e Narzisse" value="Narzisse,Weiss,1.5">Wei�e Narzisse</option>
      </optgroup>
    </select>
      </div>
    </div>
          
      <div class="col-lg-3">
	  <div class="form-group1">
      <br></br>
      <br></br>
<button type="button" name ="add" class="btn btn-primary" onclick="addFlower()">Blume zu Strau� hinzuf�gen</button>
</div>
</div>
          <div class="col-lg-3">
 <div class="trial">
<form action="create" method="post" accept-charset="ISO-8859-1">
  <button type="submit" class="btn btn-primary" >Strau� abschicken</button>
  <ul id="Blumenstr">
 
	</ul>
</form>
          </div>
        </div>
</div>

</div>
</body>



</html>

