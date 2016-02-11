package name.fsiles.sendEmailWebApp.managers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import name.fsiles.sendEmailWebApp.config.Constants;

public class IPManager {
	
	private final int limitMails;
	private final int mailExpiredTimeHours;
	private final int limitTrys;
	private final int trysExpiredTimeHours; 
	
	private Map<String,LimitMailInfo> mailsSent;
	private Map<String,LimitTrysInfo> trys;
	private Set<String> bannedIPs;
	
	private static Logger logger = Logger.getLogger(Constants.APP_NAME);
	
	public IPManager(int limitMails,int mailExpiredTimeHours, int trysLimit, int trysExpiredTimeHours){
		this.limitMails = limitMails;
		this.mailExpiredTimeHours = mailExpiredTimeHours;
		this.limitTrys=trysLimit;
		this.trysExpiredTimeHours = trysExpiredTimeHours;
		this.mailsSent = new HashMap<String, IPManager.LimitMailInfo>();
		this.trys = new HashMap<String, IPManager.LimitTrysInfo>();
		this.bannedIPs = new HashSet<String>();
		initBannedIPs();
	}
	
	
	public int getLimitMails(){
		return this.limitMails;
	}
	
	public int getMailTimeExpiredHours(){
		return this.mailExpiredTimeHours;
	}
	
	public int getTrysLimit(){
		return this.limitTrys;
	}
	
	public int getTrysTimeExpiredHours(){
		return this.trysExpiredTimeHours;
	}
	
	public boolean exceedLimitMails(String ip){
		if(mailsSent.containsKey(ip)){
			return mailsSent.get(ip).getMailsSent()>=limitMails;
		}else{
			return false;
		}
	}
	
	public boolean exceedLimitTrys(String ip){
		if(trys.containsKey(ip)){
			return trys.get(ip).getTrys()>=limitTrys;
		}else{
			return false;
		}
	}
	
	public boolean isBannedIP(String ip){
		return bannedIPs.contains(ip);
	}
	
	public void addMailSent(String ip){
		if(!mailsSent.containsKey(ip)){
			LimitMailInfo mailsInfo = new LimitMailInfo();
			mailsInfo.incrementMailsSent();
			mailsSent.put(ip, mailsInfo);
			Timer timer = new Timer();
			timer.schedule(new MailSentTimerTask(ip,timer), getNewDatePlusHours(mailExpiredTimeHours));
		}else{
			mailsSent.get(ip).incrementMailsSent();
		}
	}
	
	public void addTry( String ip){
		if(!trys.containsKey(ip)){
			LimitTrysInfo trysInfo = new LimitTrysInfo();
			trysInfo.incrementTrys();
			trys.put(ip, trysInfo);
			Timer timer = new Timer();
			timer.schedule(new TrysTimerTask(ip,timer), getNewDatePlusHours(trysExpiredTimeHours));
		}else{
			trys.get(ip).incrementTrys();
		}
	}
	
	private void initBannedIPs(){
		try{
			BufferedReader reader = new BufferedReader(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(Constants.BANNED_IP_FILE_PATH)));
			String line ="";
			while((line = reader.readLine()) != null){
				if(!line.trim().startsWith("#")){
					bannedIPs.add(line);
				}
			}
		}catch(Exception e){
			logger.log(Level.SEVERE, "Error when try to obtaine banned ips.", e);
		}
	}
	
	private Date getNewDatePlusHours(int hours){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR, hours);
		return cal.getTime();
	}
	
	private class TrysTimerTask extends TimerTask{
		
		private final String ip;
		private final Timer timer;
		
		public TrysTimerTask(String ip, Timer timer) {
			this.ip = ip;
			this.timer = timer;
		}
		
		@Override
		public void run() {
			trys.remove(ip);
			timer.cancel();
		}
		
	}
	
	private class MailSentTimerTask extends TimerTask{
		
		private final String ip;
		private final Timer timer;
		
		public MailSentTimerTask(String ip, Timer timer) {
			this.ip = ip;
			this.timer = timer;
		}
		
		@Override
		public void run() {
			mailsSent.remove(ip);
			timer.cancel();
		}
		
	}
	
	private class LimitMailInfo{
		private int mailsSent = 0;
		public int getMailsSent(){
			return mailsSent;
		}
		public void incrementMailsSent(){
			mailsSent++;
		}
	}
	
	private class LimitTrysInfo{
		private int trys = 0;
		public int getTrys(){
			return trys;
		}
		public void incrementTrys(){
			trys++;
		}
	}
}
