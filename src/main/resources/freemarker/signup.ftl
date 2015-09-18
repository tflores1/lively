<!DOCTYPE html>

<html>
  <head>
    <title>Sign Up</title>
    <style type="text/css">
    	<#include "styles.css">
    </style>
  </head>

  <body id="front-page">
  <#include "homepage.html">
  <a href="/" class="overlay" id="login_form"></a> 
<div class="popup">
	<div><h2 class="blue text-center">Sign-Up</h2></div>
	<div>   
	<form method="post">      
		<table>
        <tr>
          <td class="label">
            Username
          </td>
          <td>
            <input type="text" name="username" value="${username}">
          </td>
          <td class="error">
	    ${username_error!""}
            
          </td>
        </tr>

        <tr>
          <td class="label">
            Password
          </td>
          <td>
            <input type="password" name="password" value="">
          </td>
          <td class="error">
	    ${password_error!""}
            
          </td>
        </tr>

        <tr>
          <td class="label">
            Verify Password
          </td>
          <td>
            <input type="password" name="verify" value="">
          </td>
          <td class="error">
	    ${verify_error!""}
            
          </td>
        </tr>

        <tr>
          <td class="label">
            Email (optional)
          </td>
          <td>
            <input type="text" name="email" value="${email}">
          </td>
          <td class="error">
	    ${email_error!""}
            
          </td>
        </tr>
      </table>		   
	</div>
	<div class="text-center">
		<input type="submit" class="button small-button submit small-text" />	 
	</div>  
	<div class="text-center">
		<span class="yellow bold">Already a user? </span>
		<form style="display:inline-block"><input type="button" class="button secondary small-button" onClick="parent.location='/login'" value="Login" /></form> 
	</div>
</form>
<a class="close" href="#close"></a> 
</div>
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  </body>

</html>