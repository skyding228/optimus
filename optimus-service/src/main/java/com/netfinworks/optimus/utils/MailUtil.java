package com.netfinworks.optimus.utils;

import java.util.Properties;

import javax.net.ssl.SSLSocketFactory;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

public class MailUtil {
	public static void sendMail() {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setFrom("weichunhe@netfinworks.com");
		msg.setTo("1329555958@qq.com");
		msg.setText("hello test mail");
		msg.setSubject("wch");

		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		sender.setHost("smtp.exmail.qq.com");
		sender.setPort(465);
		sender.setUsername("weichunhe@netfinworks.com");
		sender.setPassword("wid228");
		Properties properties = new Properties();
		properties.put("mail.smtp.ssl.enable", true);
//		properties.put("mail.smtp.auth", "true");
//		properties.put("mail.smtp.socketFactory.class",
//				SSLSocketFactory.class.getName());
		sender.setJavaMailProperties(properties);
		sender.send(msg);

	}

	public static void main(String[] args) {
		System.out.println(SSLSocketFactory.class.getName());
		System.out.println("start");
		sendMail();
		System.out.println("success");
	}
}
