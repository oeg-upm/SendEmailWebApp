<%@ page import="name.fsiles.sendEmailWebApp.config.Config" %>
<%@ page import="name.fsiles.sendEmailWebApp.messages.MessagesParameterNames" %>
<%@ page import="java.util.Locale"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%Locale LOCALE = request.getLocale(); %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//ES">

<html>

<head>

<META http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>Send Email</title>

<link href="style.css" type="text/css" rel="stylesheet" />

<script src="js/jquery-2.2.0.min.js"></script>

</head>

<body>

<form id="sendEmail" action="sendEmail.jsp" method="post">
<p id="fullnameT" ><%=Config.getLocaleMessages(LOCALE, MessagesParameterNames.UI_FULLNAME)%>*:</p>
<input id="fullnameInput" type="text" name="name" value="" size="50"><br>

<p id="emailT"><%=Config.getLocaleMessages(LOCALE, MessagesParameterNames.UI_FROM_EMAIL)%>*:</p>
<input id="emailInput" type="text" name="mail" value="" size="50"><br>

<p id="subjectT"><%=Config.getLocaleMessages(LOCALE, MessagesParameterNames.UI_SUBJECT)%>*:</p>
<input id="subjectInput" type="text" name="subject" value="" size="50"><br>

<p id="messageT"><%=Config.getLocaleMessages(LOCALE, MessagesParameterNames.UI_MESSAGE)%>*:</p>
<textarea id="messageInput" name="message" cols="50" rows="20"></textarea>


<p id="captchaT"><%=Config.getLocaleMessages(LOCALE, MessagesParameterNames.UI_CAPTCHA)%>*:</p>
<div class="captcha">
<img id="captchaImg" src="./simpleImg" width="250">
<p><%=Config.getLocaleMessages(LOCALE, MessagesParameterNames.UI_INSERT_CODE)%></p>
<input id="captchaInput" type="text" name="captcha" /><br><br>
</div>
<p id="requiredFieldText">* <%=Config.getLocaleMessages(LOCALE, MessagesParameterNames.UI_REQUIRED_FIELDS)%></p>
<input type="submit" value="Enviar">

<script>
jQuery( "#sendEmail" ).submit(function( event ) {
	var toCheack = ["fullname","email","subject","message","captcha"];
	jQuery.each(toCheack, function(i,value){
		var string = "#"+value+"T";
		jQuery(string).removeClass("invalidField");	
	});
	jQuery("#requiredFieldText").removeClass("invalidField");
	var errors = false;
	jQuery.each(toCheack, function(i,value){
		var textString = "#"+value+"T";
		var inputString = "#"+value+"Input";
		if(jQuery.trim(jQuery(inputString).val())<=0){
			jQuery(textString).addClass("invalidField");
			errors = true;
		}
	});
	if(errors){
		jQuery("#requiredFieldText").addClass("invalidField");
		event.preventDefault();
	}else{
		/*$(this).append($.map(params, function (param) {
	        return   $('<input>', {
	            type: 'hidden',
	            web: window.frameElement
	        })
	    }))*/
	}
});
</script>
</form>

</body>

</html>
