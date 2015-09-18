<!DOCTYPE html>
<html>
<head>
    <title>Live healthy</title>
    <style type="text/css">
    	<#include "styles.css">
    </style>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"></script>
    <script type="text/javascript"> 
    	<#include "script.js"> 
    </script>
</head>

<body>
<!-- New code starts here -->
	<div id="page">
  		<div id="page-content">
  			<#include "settings.ftl">
  		</div>
  		<div class="top">
    		<div>
      			<div class="logo cookie large">Lively<span class="small">&laquo;</span></div>
      			<div class="content">
        			<div class="user">
         				<span id="name"><a href="#"> Welcome, <span>${username}</span></a></span>
          				<div id="profilepic"></div> 
        			</div>
      			</div>
    		</div>
 		</div>
  		<div class="sidebar dosis medium">
    		<nav id="left">
      			<ul>
      				<br />
        			<li class="active"><a href="#">Healt<nos class="blue">h</nos></a><span></span></li>
        			<li><a href="#">Fitnes<nos class="blue">s</nos></a><span></span></li>
        			<li><a href="#">Profil<nos class="blue">e</nos></a><span></span></li>
        			<li><a href="#">Setting<nos class="blue">s</nos></a><span></span></li>
      			</ul>
    		</nav>  
    		<#include "footer.html">
  		</div>
	</div>

<!-- Ends -->


  </body>
</html>