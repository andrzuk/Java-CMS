package controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Messages_Model;
import models.Config_Model;
import utilities.Messages;
import validators.Messages_Validator;
import dao.Messages_Dao;

@WebServlet("/message")

public class Message_Servlet extends HttpServlet {
	
	private static final String MODULE = "contact";
	
	private static final long serialVersionUID = 1L;
       
    public Message_Servlet() {

    	super();
    }

    private Messages_Dao setData(HttpServletRequest request, Messages_Dao user_message) {
    	
    	user_message.setNick(request.getParameter("nick"));
    	user_message.setEmail(request.getParameter("email"));
    	user_message.setIp(request.getRemoteAddr());
    	user_message.setMessage(request.getParameter("message"));
    	user_message.setVisible(request.getParameter("visible").equals("active"));  
    	
    	return user_message;
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.sendRedirect("/contact");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int result = 0;
		boolean validated = false;
		
		Messages_Model modelObject;
		Messages message = new Messages(request);
		
		Messages_Dao user_message = new Messages_Dao();
		
		user_message = setData(request, user_message);
		
		modelObject = new Messages_Model(user_message);
		
		Messages_Validator validatorObject = new Messages_Validator();
		
		validated = validatorObject.validate(user_message);

		if (request.getParameter("send") != null) {
			
			if (validated) {
				
				try {
					
					result = modelObject.save();
					
					Config_Model config = new Config_Model();
					
					Boolean send_report = Boolean.parseBoolean(config.getConfig("send_message_report"));
					
					if (send_report) {
						
						String service_name = config.getConfig("service_name");
						String to = config.getConfig("email_admin_address");
						String from = config.getConfig("email_sender_address");
						String host = config.getConfig("email_sender_host");
						String user = config.getConfig("email_sender_user");
						String password = config.getConfig("email_sender_password");
						String subject = config.getConfig("email_subject");

						Properties properties = System.getProperties();

						properties.setProperty("mail.smtp.host", host);
						properties.setProperty("mail.user", user);
						properties.setProperty("mail.password", password);
						
						Session session = Session.getDefaultInstance(properties);

						try {

							MimeMessage msg = new MimeMessage(session);
							msg.setFrom(new InternetAddress(from));
							msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
							msg.setSubject(subject);
							msg.setText("Użytkownik {" + user_message.getNick() + "} e-mail {" + user_message.getEmail() + "} wysłał z adresu {" + user_message.getIp() + "} wiadomość:\n\n" + user_message.getMessage() + "\n\n" + service_name);
							Transport.send(msg);
						} 
						catch (MessagingException mex) {
							
							mex.printStackTrace();
						}					
					}
				} 
				catch (SQLException e) {
					
					e.printStackTrace();
				} 
				catch (ParseException e) {

					e.printStackTrace();
				}
				response.sendRedirect("/" + MODULE + "#user_message");
			}
			else {
				
				response.sendRedirect("/" + MODULE + "#user_message");
			}
		}
		else {
			
			response.sendRedirect("/home");
		}
		message.setMessage("user_message", result);
		request = message.show();
	}
}
