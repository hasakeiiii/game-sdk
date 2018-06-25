package common;

import org.apache.commons.mail.SimpleEmail;
/**
 * @Author Azul-yuqinghong
 * @Create on:2008-1-8<br>
 * java.lang.NoClassDefFoundError: javax/mail/Message 
 * activation.jar   mail.jar
 */

public class ApachMail {

	public void send(String smtp,String to,String from,String password,String code,String subject,String msg) {
		try {
			SimpleEmail email = new SimpleEmail();
			email.setCharset(code);
			email.setHostName(smtp);
			email.addTo(to);
			email.setAuthentication(from,password); 
			email.setFrom(from);
			email.setSubject(subject);
			email.setMsg(msg);
			email.send();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) {
		ApachMail am = new ApachMail();
		am.send("smtp.sohu.com","yuigazul@sohu.com","yuigazul@sohu.com", "03775933856","gbk","xxx","eee");
		System.out.println("111111");
	}
	
	public String getHtmlBody(){
		StringBuffer sb=new StringBuffer();
		sb.append("<html xmlns=\"http://www.w3.org/1999/xhtml\"><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\" /><title>xxx</title></head>");
		sb.append("<body>");
		sb.append("");
		sb.append("");
		sb.append("</body></html>");
		return sb.toString();
	}
}
