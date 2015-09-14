<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <style type="text/css">
    <#include "styles.css">
    </style>
</head>
<body>

<h2>Live<span class="slogan"> adventurous </span>ly</h2>

<a href="#x" class="overlay" id="login_form"></a> 
<div class="popup">
	<div class="text-center">
		<span class="yellow">Need to Create an account?</span>
		<form style="display:inline-block"><input type="button" class="button secondary small-button" onClick="parent.location='/signup'" value="Sign Up" /></form> 
	</div>
	<div><p>Enter your username and password.</div>
	<div>   
	<form method="post">      
		<table>        
		<tr><td class="label">Username</td> 
			<td><input type="text" name="username" value="${username}" /></td>          
			<td class="red"></td></tr>        
		<tr><td class="label">Password</td>
			<td><input type="password" name="password" value="" /></td>          
			<td class="red">${login_error}</td></tr>     
		</table> 	   
	</div>
	<div class="text-center">
		<input type="submit" class="button submit small-button no-hover" />	 
	</div>  
</form>
<a class="close" href="#close"></a> 
</div>
</body>
</html>