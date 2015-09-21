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
  			<p class="responsive dosis large blue">User Preferences</p>
<div class="settings"> 

<div id="tabs">
	<nav class="tabs">
		<a href="#" class="active">Account</a>
		<section class="active tabs-content">
			<p>Username: ${username}</p>
			<p>Email: ${email}</p>

			<form method="post">
				<input type="password" name="password" value="" />
				<input type="password" name="new_password" value="" />
				<input type="password" name="verify" value="" />
			</form>
		</section>
		<a href="#">Devices</a>
		<section class="tabs-content">
			This will list my devices.
		</section>
		<a href="#">Alerts</a>
		<section class="tabs-content">
			This will let you configure different alerts.
		</section>
		<a href="#">Record</a>
		<section class="tabs-content">
			Information that you can store in your profile?
		</section>
	</nav>
</div>

</div>
  			
  		</div>
  		<div class="top">
    		<div>
      			<div class="logo cookie large">Lively<span class="small">&laquo;</span></div>
      			<div class="content">
        			<div class="user">
         				<span id="name"><a href="#"> Welcome, <span>${username}</span></a></span>
						<#include "footer.html">
          				<div id="profilepic"></div> 
        			</div>
      			</div>
    		</div>
 		</div>
  		<div class="sidebar dosis medium">
    		<nav id="left">
      			<ul>
      				<br />
        			<li><a href="#">Healt<nos class="blue">h</nos></a><span></span></li>
        			<li><a href="#">Fitnes<nos class="blue">s</nos></a><span></span></li>
        			<li><a href="#">Profil<nos class="blue">e</nos></a><span></span></li>
        			<li class="active"><a href="/settings">Setting<nos class="blue">s</nos></a><span></span></li>
      			</ul>
    		</nav>  
    		
  		</div>
	</div>

<!-- Ends -->


  </body>
</html>







