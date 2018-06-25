package azul;

import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import dao.LinkManageDao;

public class MailSendUtils {

	public static void sendHtmlMail(MailInfo info) throws Exception {
		Message message = getMessage(info);
		message.setContent(info.getContent(), "text/html;charset=utf-8");
		Transport.send(message);
	}

	public static void sendTextMail(MailInfo info) throws Exception {
		Message message = getMessage(info);
		message.setText(info.getContent());
		Transport.send(message);
	}

	private static Message getMessage(MailInfo info) throws Exception {
		final Properties p = System.getProperties();
		p.setProperty("mail.smtp.host", info.getHost());
		p.setProperty("mail.smtp.auth", "true");
		p.setProperty("mail.smtp.user", info.getFormName());
		p.setProperty("mail.smtp.pass", info.getFormPassword());

		Session session = Session.getInstance(p, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(p
						.getProperty("mail.smtp.user"), p
						.getProperty("mail.smtp.pass"));
			}
		});
		session.setDebug(true);
		Message message = new MimeMessage(session);
		message.setSubject(info.getSubject());
		message.setReplyTo(InternetAddress.parse(info.getReplayAddress()));
		message.setFrom(new InternetAddress(p.getProperty("mail.smtp.user"),
				"网站管理员"));
		message.setRecipient(RecipientType.TO,
				new InternetAddress(info.getToAddress()));

		return message;
	}

	// 邮件发送类
	public boolean sendMail(ArrayList<String> url , String title ,String type,String address) throws Exception {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.sina.com");
		props.put("mail.smtp.auth", "true");
		Session s = Session.getInstance(props);
		s.setDebug(true);
		MimeMessage message = new MimeMessage(s);
		// 给消息对象设置发件人/收件人/主题/发信时间
		try {
			InternetAddress from = new InternetAddress(
					"muzhiyouwan@sina.com");
			message.setFrom(from);
//			InternetAddress to = new InternetAddress("2736688465@qq.com");
			InternetAddress to = new InternetAddress(address);
			message.setRecipient(Message.RecipientType.TO, to);
			message.setSubject(title);
			message.setSentDate(new Date());
			// 给消息对象设置内容
			BodyPart mdp = new MimeBodyPart();// 新建一个存放信件内容的BodyPart对象
			mdp.setContent(mailContent(url,type), "text/html;charset=utf-8");// 给BodyPart对象设置内容和格式/编码方式
			Multipart mm = new MimeMultipart();// 新建一个MimeMultipart对象用来存放BodyPart对象(事实上可以存放多个)
			mm.addBodyPart(mdp);// 将BodyPart加入到MimeMultipart对象中(可以加入多个BodyPart)
			message.setContent(mm);// 把mm作为消息对象的内容
			message.saveChanges();
			Transport transport = s.getTransport("smtp");
			transport.connect("smtp.sina.com", "muzhiyouwan@sina.com",
					"muzhiyouwan");
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			System.out.println("发送成功!");
		} catch (Exception e) {
			e.printStackTrace();

		}
		return true;
	}

	// 编辑html格式邮件
	public String mailContent(ArrayList<String> url ,String type) {
		 String res ="<body align='center'>" +
		 		"<div align='center'>" +
			 		"<table width='1400px' style='font-size:12px;'>" +
			 				"<td align='center' style='border-top:#DDDDDD 1px solid;border-bottom:#DDDDDD 1px solid;height:38px'>" +
				 				"<a href='http://www.91muzhi.com'>拇指游玩首页</a>" +"&nbsp;&nbsp;" +
					 			"<a href='#'>拇指游玩客户端</a>" +"&nbsp;&nbsp;" +
					 			"<a href='#'>帮助中心</a>" +"&nbsp;&nbsp;" +
					 			"<a href='#'>用户反馈</a>" +"&nbsp;&nbsp;" +
			 				"</td>" +
			 			"</tr>" +
			 			"<tr>" +
			 				"<td align='left'>" +
				 				"<b>" +
				 					"<br>" + "尊敬的用户，您好：" + "<br>" +
				 				"</b>" +
			 				"</td>" +
		 				"</tr>" +
						"<tr>" +
							"<td align='left'>" + getErr(url,type) +
							"</td>" +
						"</tr>" +
						
			 		"</table>" +
		 		"</div>"+
		 		"</body>";
		return res;
	}
	
	
	//在邮件中遍历所有链接
	public String getErr(ArrayList<String> url,String type){
		String err ="";
		LinkManageDao lmd = new LinkManageDao();
		for(int i = 0 ; i<url.size();i++){
			if(type.equals("cdnUrl")){
				err = err + (lmd.getGameName(lmd.getGameCdnId(url.get(i)))+"<a href='" + url.get(i) + "'>" + url.get(i) + "</a>    此链接失效!<br>");
			}else if(type.equals("webUrl")){
				err = err + (lmd.getGameName(lmd.getGameWebId(url.get(i)))+"<a href='" + url.get(i) + "'>" + url.get(i) + "</a>    此链接失效!<br>");
			}else{
				
				
			}
		}
		return err;
	}
	
	
	
}
