<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <style type="text/css">
    <#include "styles.css">
    </style>
</head>
<body id="front-page">

<#include "homepage.html">

<a href="/" class="overlay" id="login_form"></a> 
<div class="popup">
	<div class="text-center">
		<span class="blue bold">Need to Create an account?</span>
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
		<input type="submit" class="button small-button submit small-text" />	 
	</div>  
</form>
<a class="close" href="#close"></a> 
</div>



</body>
</html>