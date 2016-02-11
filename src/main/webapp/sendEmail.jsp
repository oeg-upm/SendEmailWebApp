<%@ page import="nl.captcha.Captcha"%>
<%@ page import="javax.mail.*" %>
<%@ page import="java.util.Properties" %>
<%@ page import="javax.mail.internet.*" %>
<%@ page import="name.fsiles.sendEmailWebApp.config.Config" %>
<%@ page import="java.util.logging.Level" %>
<%@ page import="java.util.logging.Logger" %>
<%@ page import="name.fsiles.sendEmailWebApp.messages.MessagesParameterNames" %>
<%@ page import="name.fsiles.sendEmailWebApp.managers.IPManager" %>
<%@ page import="java.util.Locale"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%Locale LOCALE = request.getLocale(); %>
<%
try{
	Captcha captcha = (Captcha) session.getAttribute(Captcha.NAME);
	IPManager ipManager = Config.getIPManager();
	// Or, for an AudioCaptcha:
	// AudioCaptcha captcha = (AudioCaptcha) session.getAttribute(Captcha.NAME);
	request.setCharacterEncoding("UTF-8");
	String answer = request.getParameter("captcha");
	if (answer != null && captcha.isCorrect(answer)) {
		String name = request.getParameter("name");
		String fromEmail = request.getParameter("mail");
		String subject = request.getParameter("subject");
		String message = request.getParameter("message");
		String ip = request.getRemoteAddr();
		boolean error = false;
		if(name==null || name.isEmpty()){
			out.println(Config.getLocaleMessages(LOCALE, MessagesParameterNames.UI_ERROR_INVALID_FULLNAME)+"<br>");
			error=true;
		}
		if(fromEmail==null || fromEmail.isEmpty()){
			out.println(Config.getLocaleMessages(LOCALE, MessagesParameterNames.UI_ERROR_INVALID_FROM_EMAIL)+"<br>");
			error=true;
		}
		if(subject==null || subject.isEmpty()){
			out.println(Config.getLocaleMessages(LOCALE, MessagesParameterNames.UI_ERROR_INVALID_SUBJECT)+"<br>");
			error=true;
		}
		if(message==null || message.isEmpty()){
			out.println(Config.getLocaleMessages(LOCALE, MessagesParameterNames.UI_ERROR_INVALID_MESSAGE)+"<br>");
			error=true;
		}
		if(ip==null || ip.isEmpty()){
			out.println(Config.getLocaleMessages(LOCALE, MessagesParameterNames.UI_ERROR_INVALID_CLIENT_IP)+"<br>");
			error=true;
		}else{
			if(ipManager.isBannedIP(ip)){
				out.println(Config.getLocaleMessages(LOCALE, MessagesParameterNames.SERVER_BANNED_IP)+"<br>");
				error=true;
			}
			if(ipManager.exceedLimitMails(ip)){
				out.println(Config.getLocaleMessages(LOCALE, MessagesParameterNames.SERVER_MAIL_LIMIT_EXCEEDED,String.valueOf(ipManager.getLimitMails()),String.valueOf(ipManager.getMailTimeExpiredHours()))+"<br>");
				error=true;
			}
		}
		if(error){
			return;
		}
		String errorsOnParams = Config.getErrorsOnParams();
		if(errorsOnParams != null){
			out.println(Config.getLocaleMessages(LOCALE, MessagesParameterNames.UI_ERROR_INVALID_CONFIG_PARAMS));
			out.println(errorsOnParams);
			return;
		}

		// Configuramos las propiedades
		Properties propiedades = new Properties();

		propiedades.put("mail.smtp.auth", "true");
		propiedades.put("mail.smtp.starttls.enable", "false");
		propiedades.put("mail.smtp.host", Config.getMailHost());
		propiedades.put("mail.smtp.port", Config.getMailPort());

		// Obtenemos la sesi�n
		Session sessionMail = Session.getInstance(propiedades,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(Config.getLoginUsername(),
								Config.getLoginPassword());
					}
				});

		try {
			// Creamos un objeto mensaje tipo MimeMessage por defecto.
			MimeMessage mensaje = new MimeMessage(sessionMail);

			// Asignamos el emisor del mensaje al header del correo.
			mensaje.setFrom(new InternetAddress(fromEmail));

			// Asignamos el receptor del mensaje al header del correo.
			mensaje.addRecipient(Message.RecipientType.TO,
					new InternetAddress(Config.getDestinatary()));

			// Asignamos el asunto
			mensaje.setSubject(subject);

			// Asignamos el mensaje como tal
			mensaje.setText(Config.getMailMessage(name, message,ip));

			// Enviamos el correo
			Transport.send(mensaje);
			ipManager.addMailSent(ip);
			out.println(Config.getLocaleMessages(LOCALE, MessagesParameterNames.SERVER_MAIL_DONE));
		} catch (MessagingException e) {
			Logger.getLogger("SendEmail").log(Level.SEVERE,"Error when send email.",e);
			out.println(Config.getLocaleMessages(LOCALE, MessagesParameterNames.SERVER_MAIL_ERROR)+"<br>");
			out.println("<a href=\"index.jsp\">"+Config.getLocaleMessages(LOCALE, MessagesParameterNames.UI_RETURN_TO_APP_FORM)+"</a>");
		}
	} else {
		String ip = request.getRemoteAddr();
		if(ip==null || ip.isEmpty()){
			out.println(Config.getLocaleMessages(LOCALE, MessagesParameterNames.UI_ERROR_INVALID_CLIENT_IP)+"<br>");
		}else{
			if(ipManager.exceedLimitTrys(ip)){
				out.println(Config.getLocaleMessages(LOCALE, MessagesParameterNames.SERVER_TRYS_LIMIT_EXCEEDDED,String.valueOf(ipManager.getTrysLimit()),String.valueOf(ipManager.getTrysTimeExpiredHours()))+"<br>");
				return;
			}else{
				ipManager.addTry(ip);
			}
		}
		out.println("<b>"+Config.getLocaleMessages(LOCALE, MessagesParameterNames.UI_ERROR_INVALID_CAPTCHA)+"</b><br>");
		out.println("<a href=\"index.jsp\">"+Config.getLocaleMessages(LOCALE, MessagesParameterNames.UI_RETURN_TO_APP_FORM)+"</a>");
	}
	}catch(Exception e){
		out.println("Internal server error. Contact with system admin");
		e.printStackTrace();
	}
%>