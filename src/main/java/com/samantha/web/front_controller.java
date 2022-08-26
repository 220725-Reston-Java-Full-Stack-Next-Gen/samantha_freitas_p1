package com.samantha.web;

import java.io.IOException;
import java.sql.Connection;

import com.samantha.util.JDBCConnectionUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

@SuppressWarnings("serial")
public class front_controller extends HttpServlet{
	
	Connection conn = JDBCConnectionUtil.getConnection();
	
	private static Logger logger = Logger.getLogger(front_controller.class);
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//we use URI rewriting to better manage the navigation of our HTTP requests as they come to the Front Controller
		final String URI = req.getRequestURI().replace("/samantha_freitas_p1/", "");
		logger.info("User trying to access endpoint: " + URI);
	
		switch(URI) {
		case "":
			logger.info("Homepage");
            break;

        case "register":
        	logger.info("User is trying to register for new account...");
        	request_helper.processRegistration(req,resp);
            break;

        case "login":
        	logger.info("User is trying to login...");
        	request_helper.processLogin(req,resp);
            break;
        case "newReimbursement":
        	logger.info("User is trying to create new reimbursement...");
        	request_helper.processReimbursement(req,resp);
            break;
        //case "reimburse":
        	//logger.info("Manager is trying to process reimbursement...");
        	//request_helper.processReimburseMngmnt(req, resp);
            //break;
        default:
            logger.warn("No path present: " + URI);
            break;
		}
	}
	
}
