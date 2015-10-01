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
			<p>Username: ${username}</p>
			<p>Email: ${email}</p>
			<hr class="shadow"></hr>
			</div>
			
			<div class="break">
			<div class="form">
			<form method="post">
				<table>        
					<tr><td class="label">Password</td> 
						<td><input type="password" name="password" value="" /></td>          
						<td class="red">${password_error}</td></tr>        
					<tr><td class="label">New Password</td>
						<td><input type="password" name="new_password" value="" /></td>          
						<td class="yellow">${password_success}</td></tr> 
					<tr><td class="label">Verify New Password</td>
						<td><input type="password" name="verify_password" value="" /></td>          
						<td class="red">${verify_error}</td></tr>   
					<tr><td class="red">${empty_field}</td></tr>     
				</table> 
			</div>	   
				<div>
					<input type="submit" class="button small-button primary small-text" />	 
				</div>  
			</form></div>
  			
  		</div>
  		<div class="top">
			<div>
				<div class="logo cookie large">
					Lively<span class="small">&laquo;</span>
				</div>
			</div>
		</div>


		<div class="sidebar dosis medium">
			<div class="content">
				<div class="user">
					<span id="name" class="dosis"><a href="/welcome"
						class="white"> Welcome, <span>${username}</span></a></span>
				</div>

			</div>
			<div class="pic-wrapper">
				<div id="profilepic"></div>
			</div>

			<nav id="left">
				<ul>
					<br />
					<li class="active"><a href="#">Healt<nos class="blue">h</nos></a><span></span></li>
					<li><a href="#">Fitnes<nos class="blue">s</nos></a><span></span></li>
					<li><a href="#">Profil<nos class="blue">e</nos></a><span></span></li>
					<li><a href="/settings">Setting<nos class="blue">s</nos></a><span></span></li>
				</ul>
			</nav>

		</div>
	</div>

  </body>
</html>







