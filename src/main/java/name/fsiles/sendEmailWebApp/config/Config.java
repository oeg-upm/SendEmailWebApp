package name.fsiles.sendEmailWebApp.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import name.fsiles.sendEmailWebApp.managers.IPManager;

public class Config {
	
	private static Logger logger = Logger.getLogger(Constants.APP_NAME);
	private static String destinatary=null;
	private static String mailHost=null;
	private static String mailPort=null;
	private static String loginUsername=null;
	private static String loginPassword=null;
	private static String messageHeader=null;
	private static IPManager ipManager= null;
	
	private static Map<Locale,ResourceBundle> allMessages = new HashMap<Locale,ResourceBundle>();
	
	private static void initConfig() {
		Properties prop = new Properties();
		try {
			String relativePath = Constants.CONFIGURATION_FILE_PATH;
			prop.load(Config.class.getClassLoader().getResourceAsStream(relativePath));
			destinatary = prop.getProperty(ParameterNames.DESTINATARY);
			mailHost = prop.getProperty(ParameterNames.MAIL_HOST);
			mailPort = prop.getProperty(ParameterNames.MAIL_PORT);
			loginUsername = prop.getProperty(ParameterNames.MAIL_USER);
			loginPassword = prop.getProperty(ParameterNames.MAIL_PASSWORD);
			messageHeader = prop.getProperty(ParameterNames.MAIL_HEADER);	
			int mailExpiredTimeHours = Integer.valueOf(prop.getProperty(ParameterNames.EXPIRED_TIME_MAILS));
			int limitMails = Integer.valueOf(prop.getProperty(ParameterNames.LIMIT_MAILS));
			int trysExpiredTimeHours = Integer.valueOf(prop.getProperty(ParameterNames.TRYS_INTERVAL));
			int limitTrys = Integer.valueOf(prop.getProperty(ParameterNames.TRYS_TO_SEND_EMAIL));
			ipManager = new IPManager(limitMails, mailExpiredTimeHours, limitTrys, trysExpiredTimeHours);
		} catch (IOException e) {
			logger.log(Level.SEVERE,"Can not load configuration.properties",e);
		}
	}
	
	public static String getErrorsOnParams(){
		if(destinatary == null || mailHost == null || mailPort == null || loginUsername == null ||
				loginPassword == null || messageHeader == null){
			initConfig();
			String errors = "";
			if(destinatary == null){
				errors += "\nProblems with obtain "+ParameterNames.DESTINATARY+" from configuration.properties";
			}
			if(mailHost == null){
				errors += "\nProblems with obtain "+ParameterNames.MAIL_HOST+" from configuration.properties";
			}
			if(mailPort == null){
				errors += "\nProblems with obtain "+ParameterNames.MAIL_PORT+" from configuration.properties";
			}
			if(loginUsername == null){
				errors += "\nProblems with obtain "+ParameterNames.MAIL_USER+" from configuration.properties";
			}
			if(loginPassword== null){
				errors += "\nProblems with obtain "+ParameterNames.MAIL_PASSWORD+" from configuration.properties";
			}
			if(messageHeader == null){
				errors += "\nProblems with obtain "+ParameterNames.MAIL_HEADER+" from configuration.properties";
			}
			if(errors.isEmpty()){
				return null;
			}else{
				return errors;
			}
		}else{
			return null;
		}
	}
	
	public static String getLocaleMessages(String localeString,String key,String ... params){
		String message="";
		try{
			Locale locale = new Locale(localeString);
			ResourceBundle messages = allMessages.get(locale);
			if(messages == null){
				messages =  ResourceBundle.getBundle(Constants.MESSAGES_FILE_CLASS,locale);
				allMessages.put(locale,  messages);
			}
			if(messages.containsKey(key)){
				message = messages.getString(key);
				for(int i=0;i<params.length;i++){
					message = message.replaceAll("%"+i+"%", params[i]);
				}
			}else{
				logger.log(Level.SEVERE, "Exist messages file but not found message for key "+key+" and locale "+locale);
				return "No message for key "+key+" and locale "+locale+". Contact with System Administrator.";
			}
		}catch(Exception e){
			logger.log(Level.SEVERE, "Exception when obtain messages. Not found message for key "+key+" and locale "+localeString,e);
			return "No message for key "+key+" and locale "+localeString+". Contact with System Administrator.";
		}
		return message;
	}
	
	public static String getLocaleMessages(String localeString,String key){
		String message="";
		try{
			Locale locale = new Locale(localeString);
			ResourceBundle messages = allMessages.get(locale);
			if(messages == null){
				messages =  ResourceBundle.getBundle(Constants.MESSAGES_FILE_CLASS,locale);
				allMessages.put(locale,  messages);
			}
			if(messages.containsKey(key)){
				message = messages.getString(key);
			}else{
				logger.log(Level.SEVERE, "Exist messages file but not found message for key "+key+" and locale "+locale);
				return "No message for key "+key+" and locale "+localeString+". Contact with System Administrator.";
			}
		}catch(Exception e){
			logger.log(Level.SEVERE, "Not found message for key "+key+" and locale "+localeString,e);
			return "No message for key "+key+" and locale "+localeString+". Contact with System Administrator.";
		}
		return message;
	}
	
	public static String getLocaleMessages(Locale locale,String key,String ... params){
		String message="";
		try{
			ResourceBundle messages = allMessages.get(locale);
			if(messages == null){
				messages =  ResourceBundle.getBundle(Constants.MESSAGES_FILE_CLASS,locale);
				allMessages.put(locale,  messages);
			}
			if(messages.containsKey(key)){
				message = messages.getString(key);
				for(int i=0;i<params.length;i++){
					message = message.replaceAll("%"+i+"%", params[i]);
				}
			}else{
				logger.log(Level.SEVERE, "Exist messages file but not found message for key "+key+" and locale "+locale);
				return "No message for key "+key+" and locale "+locale+". Contact with System Administrator.";
			}
		}catch(Exception e){
			logger.log(Level.SEVERE, "Not found message for key "+key+" and locale "+locale,e);
			return "No message for key "+key+" and locale "+locale+". Contact with System Administrator.";
		}
		return message;
	}
	
	public static String getLocaleMessages(Locale locale,String key){
		String message="";
		try{
			ResourceBundle messages = allMessages.get(locale);
			if(messages == null){
				messages =  ResourceBundle.getBundle(Constants.MESSAGES_FILE_CLASS,locale);
				allMessages.put(locale,  messages);
			}
			if(messages.containsKey(key)){
				message = messages.getString(key);
			}else{
				logger.log(Level.SEVERE, "Exist messages file but not found message for key "+key+" and locale "+locale);
				return "No message for key "+key+" and locale "+locale+". Contact with System Administrator.";
			}
		}catch(Exception e){
			logger.log(Level.SEVERE, "Not found message for key "+key+" and locale "+locale,e);
			return "No message for key "+key+" and locale "+locale+". Contact with System Administrator.";
		}
		return message;
	}
	
	public static IPManager getIPManager(){
		if(ipManager == null){initConfig();}
		return ipManager;
	}
	
	public static String getDestinatary() {
		if(destinatary == null){initConfig();}
		return destinatary;
	}
	public static String getMailHost() {
		if(mailHost == null){initConfig();}
		return mailHost;
	}
	public static String getMailPort() {
		if(mailPort == null){initConfig();}
		return mailPort;
	}
	public static String getLoginUsername() {
		if(loginUsername == null){initConfig();}
		return loginUsername;
	}
	public static String getLoginPassword() {
		if(loginPassword== null){initConfig();}
		return loginPassword;
	}
	public static String getMailMessage(String name,String message) {
		if(messageHeader == null){initConfig();}
		if(messageHeader != null){
			return messageHeader.replace("%FULLNAME%", name).replace("%MESSAGE%","\n"+message);
		}else{
			return null;
		}
	}
	public static String getMailMessage(String name,String message,String ip) {
		if(messageHeader == null){initConfig();}
		if(messageHeader != null){
			return messageHeader.replace("%FULLNAME%", name).replace("%IP%", ip).replace("%MESSAGE%","\n"+message);
		}else{
			return null;
		}
	}
	
	
	
}
