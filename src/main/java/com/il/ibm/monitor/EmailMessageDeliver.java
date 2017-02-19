package com.il.ibm.monitor;

import com.il.ibm.monitor.common.ConfigReader;
import com.il.ibm.monitor.common.IMessageDeliver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

/**
 * Created by cadano on 1/16/17.
 *
 * Implement @{@link IMessageDeliver} for sending messages using smtp serer.
 */
public class EmailMessageDeliver implements IMessageDeliver {
    private ConfigReader configReader;

    final static Logger logger = LogManager.getLogger(EmailMessageDeliver.class);

    public ConfigReader getConfigReader() {
        return configReader;
    }

    public void setConfigReader(ConfigReader configReader) {
        this.configReader = configReader;
    }

    @Override
    public void sendMessage(String subject, String content) {

        logger.info(String.format("Sending message with subject: %s and content: %s", subject, content));
        // Get system properties
        Properties properties = System.getProperties();


        String host = configReader.getSmtpServer();

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");

        // Get the Session object.
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(configReader.getSmtpUserName(), configReader.getSmtpPassword());
                    }
                });

        try {

            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(configReader.getFrom()));

            // Set To: header field of the header.
            for (String t : configReader.getTo()) {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(t));
            }

            // Set Subject: header field
            message.setSubject(subject);

            // Now set the actual message
            message.setText(content);

            // Send message
            Transport.send(message);
            logger.info("Message was sent successfully...");
        } catch (MessagingException mex) {
            logger.error(mex.getMessage());
        }
    }
}
