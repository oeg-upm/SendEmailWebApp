package name.fsiles.sendEmailWebApp.messages;

public class MessagesParameterNames {
	//Server messages
	public static final String SERVER_MAIL_DONE = "server.mailDone";
	public static final String SERVER_MAIL_ERROR = "server.mailError";
	public static final String SERVER_BANNED_IP = "server.bannedIP";
	public static final String SERVER_MAIL_LIMIT_EXCEEDED = "server.mailLimitExceeded";
	public static final String SERVER_TRYS_LIMIT_EXCEEDDED = "server.trysLimitExceeded";

	//UI error messages
	public static final String UI_ERROR_INVALID_FULLNAME = "ui.error.invalidFullName"; 
	public static final String UI_ERROR_INVALID_FROM_EMAIL = "ui.error.invalidFromEmail";
	public static final String UI_ERROR_INVALID_SUBJECT = "ui.error.invalidSubject";
	public static final String UI_ERROR_INVALID_MESSAGE = "ui.error.invalidMessage";
	public static final String UI_ERROR_INVALID_CLIENT_IP = "ui.error.invalidClientIP";
	public static final String UI_ERROR_INVALID_CONFIG_PARAMS = "ui.error.invalidConfigParams";  
	public static final String UI_ERROR_INVALID_CAPTCHA = "ui.error.invalidCaptcha";
	public static final String UI_RETURN_TO_APP_FORM = "ui.error.returnToAppForm";

	//UI messages
	public static final String UI_FULLNAME  = "ui.fullname";
	public static final String UI_FROM_EMAIL = "ui.fromEmail";
	public static final String UI_SUBJECT = "ui.subject";
	public static final String UI_MESSAGE = "ui.message";
	public static final String UI_CAPTCHA = "ui.captcha";
	public static final String UI_INSERT_CODE = "ui.captcha.insertCode";
	public static final String UI_REQUIRED_FIELDS = "ui.requiredFields";
}
