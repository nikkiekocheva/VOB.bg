package util.validation;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail {
	public static void sendMailForRegister(String receiver,int verifyCode) {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("vobbgsystem@gmail.com","vobbg123");
				}
			});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("vobbgsystem@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(receiver));
			message.setSubject("Register in VOBbg");
			message.setText("Hello, this is the verification code to complete your registration in VOB.bg:  " + verifyCode);

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			System.out.println("Invalid mail to send");
			throw new RuntimeException(e);
		}
	}
}
