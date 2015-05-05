package cis.profiler.mail;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * To send email notifications
 *
 * @author Raminderjeet Singh
 */
public class MailNotification {

	/**
	 * Send Email
	 *
	 * @param mailServer
	 * @param senderEmail
	 * @param senderMailPassword
	 * @param recipients
	 * @param subject
	 * @param message
	 * @param debug
	 * @throws MessagingException
	 */
	public void sendMail(String mailServer, String senderEmail, String senderMailPassword, String recipients[],
			String subject, String message, boolean debug) throws MessagingException {

                log("Subject: " + subject);
                log("MailServer: " + mailServer);
                log("SenderEmail: " + senderEmail);
                log("Message: " + message);

		// Set the host smtp address
		Properties props = new Properties();
		props.put("mail.smtp.host", mailServer);
		props.put("mail.smtp.user", senderEmail);
		props.put("mail.smtp.password", senderMailPassword);
		props.put("mail.smtp.port", "465");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.debug", new Boolean(debug));
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.fallback", "false");
		props.put("mail.smtp.ssl.trust", mailServer);
//		props.put("javax.net.debug","ssl,session");

		// Required to avoid security exception.
		MyAuthenticator authentication = new MyAuthenticator(senderEmail, senderMailPassword);
                Session session = Session.getInstance(props, authentication);
		session.setDebug(debug);
		// create some properties and get the default Session

		// create a message
		MimeMessage msg = new MimeMessage(session);

		// set the from and to address
		InternetAddress addressFrom = new InternetAddress(senderEmail);
		msg.setFrom(addressFrom);

		InternetAddress[] addressTo = new InternetAddress[recipients.length];
		for (int i = 0; i < recipients.length; i++) {
			addressTo[i] = new InternetAddress(recipients[i]);
		}
		msg.setRecipients(Message.RecipientType.TO, addressTo);

		// Optional : You can also set your custom headers in the Email if you
		// Want
		// msg.addHeader("MyHeaderName", "myHeaderValue");

		// Setting the Subject and Content Type
		msg.setSubject(subject);
		msg.setContent(message, "text/plain");
		Transport transport = session.getTransport("smtp");
		transport.connect(mailServer, 465, senderEmail, senderMailPassword);
		transport.sendMessage(msg, msg.getAllRecipients());
		transport.close();
	}

        private static void log(String msg) {
            Logger.getLogger(MailNotification.class.getName()).log(Level.INFO, null, msg);
        }

	public static void main(String[] args) {
		try {
			String[] emails = { "thorsten.vobl@gmail.com" };
			new MailNotification().sendMail("smtp.gmail.com", "cis.profiler@gmail.com", "upe7rub8", emails, "Test", "Test Message", true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static class MyAuthenticator extends javax.mail.Authenticator {
		String User;
		String Password;

		public MyAuthenticator(String user, String password) {
			User = user;
			Password = password;
		}

		@Override
		public PasswordAuthentication getPasswordAuthentication() {
			return new javax.mail.PasswordAuthentication(User, Password);
		}
	}
}
