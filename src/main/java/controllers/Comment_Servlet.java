package controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.mail.*;
import javax.mail.internet.*;

import dao.Comments_Dao;
import models.Comments_Model;
import models.Config_Model;
import utilities.Messages;
import validators.Comments_Validator;

@WebServlet("/comment")

public class Comment_Servlet extends HttpServlet {
	
	private static final String MODULE = "page";
	
	private static final long serialVersionUID = 1L;
       
    public Comment_Servlet() {
    	
        super();
    }

    private Comments_Dao setData(HttpServletRequest request, Comments_Dao comment) {
    	
    	if (request.getParameter("page_id").isEmpty()) {
    		
    		return comment;
    	}
    	comment.setPage_id(Integer.parseInt(request.getParameter("page_id")));
    	comment.setNick(request.getParameter("nick"));
    	comment.setEmail(request.getParameter("email"));
    	comment.setIp(request.getRemoteAddr());
    	comment.setComment(request.getParameter("comment"));
    	comment.setVisible(request.getParameter("visible").equals("active"));  
    	
    	return comment;
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.sendRedirect("/home");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int result = 0;
		boolean validated = false;
		
		Comments_Model modelObject;
		Messages message = new Messages(request);
		
		Comments_Dao comment = new Comments_Dao();
		
		comment = setData(request, comment);
		
		modelObject = new Comments_Model(comment);
		
		Comments_Validator validatorObject = new Comments_Validator();
		
		validated = validatorObject.validate(comment);

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
							msg.setText("Użytkownik {" + comment.getNick() + "} e-mail {" + comment.getEmail() + "} wysłał z adresu {" + comment.getIp() + "} komentarz:\n\n" + comment.getComment() + "\n\n" + service_name);
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
				response.sendRedirect("/" + MODULE + "?id=" + request.getParameter("page_id") + "#comment");
			}
			else {
				
				response.sendRedirect("/" + MODULE + "?id=" + request.getParameter("page_id") + "#comment");
			}
		}
		else {
			
			response.sendRedirect("/home");
		}
		message.setMessage("comment", result);
		request = message.show();
	}
}
