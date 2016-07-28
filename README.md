[![Code HTML](https://img.shields.io/badge/code-HTML-blue.svg)](https://www.w3.org/TR/html/)
[![Code CSS](https://img.shields.io/badge/code-CSS-blue.svg)](https://www.w3.org/Style/CSS/)
[![Code JS](https://img.shields.io/badge/code-JS-blue.svg)](http://www.w3schools.com/js/)
[![Desgin Responsive](https://img.shields.io/badge/design-Responsive-blue.svg)](http://www.w3schools.com/html/html_responsive.asp)  
[![lib jQuery](https://img.shields.io/badge/lib-jQuery-green.svg)](https://jquery.com/)
[![i18n API? yes](https://img.shields.io/badge/i18n%20API%3F-yes-green.svg)](https://www.w3.org/standards/webdesign/i18n)  
[![Java 1.7 or later](https://img.shields.io/badge/Java-1.7%20or%20later-40c4ff.svg)](https://www.java.com/es/download/help/index_installing.xml?j=7)
[![Java Mail](https://img.shields.io/badge/Java%20Mail-1.5.5-40c4ff.svg)](www.oracle.com/technetwork/java/javamail/) 
[![Simple Captcha](https://img.shields.io/badge/Simple%20Captcha-1.2.1-40c4ff.svg)](simplecaptcha.sourceforge.net/)  
[![Build Maven](https://img.shields.io/badge/build-Maven-lightgrey.svg)](https://maven.apache.org/)
[![Version 0.1](https://img.shields.io/badge/Version-0.1%20Beta-lightgrey.svg)](#version)  

# SendEmailWebApp

SendEmailWebApp is a web application with a formulary to send emails to a specified destinatary.  
This page is designed to be added at other page and the people can send emails at specified email adress.

### Pre-requisites
* Maven 3.0 or later.
* Java 1.7 or later.
* Tomcat 1.7 or later.

### Compile
Type in a cmd or console:
```sh
mvn clean install
```

### Usage
1) Configure webapp.  
2) (Optional) add your own language pack.  
3) (Optional) add banned ips.  
4) Deploy the webapp in a tomcat server.  

##### 1) Configure Webapp.
Go to target/SendEmailWebApp/WEB-INF folder.  
Edit configuration.properties and change the properties.  
Example of configuration.properties:  
> mail.destinatary=TO_CHANGE  
> mail.host.url=TO_CHANGE  
> mail.host.port=TO_CHANGE  
> mail.host.user=TO_CHANGE  
> mail.host.password=TO_CHANGE  
> mail.ttls.enable=false  
> mail.message.header=%FULLNAME% sent you, from Web=TO_CHANGE and IP=%IP%, the next message: %MESSAGE%  
> server.mail.limit=5  
> server.mail.limit.interval.hours=24  
> server.mail.trys.limit=5  
> server.mail.trys.interval.hours=1  

Properties description: 
- mail.destinatary: Is the destinatary of email formulary.  
- mail.host.url: Is the URL of smtp server.  
- mail.host.port: Is the port of smtp server.  
- mail.host.user: Is the user of smtp server.  
- mail.host.password: Is the user of smtp server.  
- mail.ttls.enable: If smtp server have TTLs then this parameter is "true". Values "false", "true".   
- mail.message.header: Is the header added to message for more info to destinatary. This param have reserved words (Replaced by webapp data):  
-- %FULLNAME%: Fullname specified in formulary.  
-- %IP%: The client ip that send the email.  
-- %MESSAGE%: The specified message in formulary.  
- server.mail.limit: Limit of messages sent by same ip in a interval.  
- server.mail.limit.interval.hours: Time interval that clear count of emails sent.  
- server.mail.trys.limit: Limit of captcha trys by same ip in a interval.  
- server.mail.trys.interval.hours:  Time interval that clear count of captcha trys.  

##### 2) (Optional) add your own language pack.
Go to target/SendEmailWebApp/WEB-INF/classes/resources/messages folder.  
Copy ui.properties and rename it to ui_LANGUAGE-CODE_COUNTRY-CODE.properties  
--Language-code is your language code, see the next list for search your code (ISO_639-1): https://en.wikipedia.org/wiki/List_of_ISO_639-1_codes  
--Country-code is your country code, see the next list for search your code: https://es.wikipedia.org/wiki/ISO_3166-1   
Edit your new file and translate it (Not translate the keys).  
Example lines of languaje file:  
> key=value to translate  
> not.change=This is a example  

##### 3) (Optional) add banned ips.
Go to target/SendEmailWebApp/WEB-INF/bannedIPs.txt  
Each line is a banned ip. If you want to add comments put "#" character and line start.    
Example file:
> \#This is a comment.  
> \#Each line is a banned IP.  
> \#Ip hacker attacks:  
> 1.12.123.123    

##### 4) Deploy the webapp in a tomcat server.
Copy the target/SendEmailWebApp folder to a tomcat webapps folder.  
Or compress it on a war file and deploy it by tomcat manager.  

### Version
0.0.1
