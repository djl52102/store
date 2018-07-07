package com.itheima.store.utils;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

public class MailUtil {

	public static void sendMail(String email, String emailMsg)
			throws AddressException, MessagingException {
		// 1.鍒涘缓涓�釜绋嬪簭涓庨偖浠舵湇鍔″櫒浼氳瘽瀵硅薄 Session

		Properties props = new Properties();
		//璁剧疆鍙戦�鐨勫崗璁�
		props.setProperty("mail.transport.protocol", "SMTP");
		
		//璁剧疆鍙戦�閭欢鐨勬湇鍔″櫒
		props.setProperty("mail.host", "localhost");
		props.setProperty("mail.smtp.auth", "true");// 鎸囧畾楠岃瘉涓簍rue

		// 鍒涘缓楠岃瘉鍣�
		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				//璁剧疆鍙戦�浜虹殑甯愬彿鍜屽瘑鐮�
				return new PasswordAuthentication("service", "123");
			}
		};

		Session session = Session.getInstance(props, auth);

		// 2.鍒涘缓涓�釜Message锛屽畠鐩稿綋浜庢槸閭欢鍐呭
		Message message = new MimeMessage(session);

		//璁剧疆鍙戦�鑰�
		message.setFrom(new InternetAddress("service@yahoo.com"));

		//璁剧疆鍙戦�鏂瑰紡涓庢帴鏀惰�
		message.setRecipient(RecipientType.TO, new InternetAddress(email)); 

		//璁剧疆閭欢涓婚
		message.setSubject("激活验证");
		// message.setText("杩欐槸涓�皝婵�椿閭欢锛岃<a href='#'>鐐瑰嚮</a>");

		//璁剧疆閭欢鍐呭
		message.setContent(emailMsg, "text/html;charset=utf-8");

		// 3.鍒涘缓 Transport鐢ㄤ簬灏嗛偖浠跺彂閫�
		Transport.send(message);
	}
}
