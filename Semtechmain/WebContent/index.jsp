
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.min.css" />

<!-- Optional theme -->
<link rel="stylesheet"  href="${pageContext.request.contextPath}/bootstrap/css/bootstrap-theme.min.css">

<!-- Latest compiled and minified JavaScript -->
<script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js"></script>
<head>
<title>copy Webshop</title>
</head>
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
    		input.setAttribute("class", "form-control");
    		input.setAttribute("size","30");
    		input.type =  "text";
    		input.name= textUser;
    		input.value = 1;
    		input.id = textUser;
			
    		var hiinput = document.createElement("input");
    		hiinput.type ="hidden";
    		hiinput.value = strUser;
    		hiinput.name = textUser; 
    	li.appendChild(label);
    	li.appendChild(input);
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
<div class="navbar navbar-inverse">
  <div class="navbar-header">
    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-inverse-collapse">
      <span class="icon-bar"></span>
      <span class="icon-bar"></span>
      <span class="icon-bar"></span>
    </button>
    <a class="navbar-brand" href="#">Flowershower</a>
  </div>
  </div>

 <div class="container">
<div class="page-header" id="banner">
        <div class="row">
          <div class="col-lg-6">
            <h1>  Flowershower</h1>
            <p class="lead">Der Florist in ihrer Nähe</p>
          </div>
        </div>
      </div>
  <p>Zur Auswahl stehen:</p>
<div class="form-group">
      <div class="col-lg-3">
        <br>
         <select id="Blumen" size="12 "multiple="" class="form-control">
      <optgroup label="Rosen">
        <option label="Gelbe Rose" value ="Rose,Gelb,2.50">Gelbe Rose</option>
        <option label="Rote Rose" value="Rose,Rot,2.50">Rote Rose</option>
        <option label="Blaue Rose" value="Rose,Blau,2.50">Blaue Rose</option>
      </optgroup>
      <optgroup label="Tulpen">
        <option label="Gelbe Tulpe" value="Tulpe,Gelb,2">Gelbe Tulpe</option>
        <option label="Rote Tulpe" value="Tulpe,Rot,2">Rote Tulpe</option>
        <option label="Blaue Tulpe" value="Tulpe,Blau,2">Blaue Tulpe</option>
      </optgroup>
      <optgroup label="Nelke">
        <option label="Gelbe Nelke" value="Nelke,Gelb,1.5">Gelbe Nelke</option>
        <option label="Rote Nelke" value="Nelke,Rot,1.5">Rote Nelke</option>
        <option label="Blaue Nelke" value="Nelke,Blau,1.5">Blaue Nelke</option>
      </optgroup>
    </select>
      </div>
    </div>
<div class="form-group">
      <div class="col-lg-3">
      <br></br>
      <br></br>
<button type="button" name ="add" class="btn btn-primary" onclick="addFlower()">Blume zu Strauß hinzufügen</button>
</div>
</div>
 <div class="bs-docs-section">
        <div class="row">
          <div class="col-lg-12">
            <div class="page-header">
            
            <form action="create" method="post" accept-charset="ISO-8859-1">
  <button type="submit" class="btn btn-primary" >Straus abschicken</button>
   <div class="form-group">
  <ul id="Blumenstr">
 
	</ul>
	</div>


   <div class="col-lg-3">

</div> 
</form>
            
            </div>
          </div>
        </div>

</div>
</body>



</html>

