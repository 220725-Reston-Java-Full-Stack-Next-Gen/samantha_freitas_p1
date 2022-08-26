package com.samantha.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.samantha.dao.user_dao_impl;
import com.samantha.models.ers_reimbursement;
import com.samantha.models.ers_user_roles;
import com.samantha.models.ers_users;
import com.samantha.dao.reimbursement_dao_impl;
import com.samantha.service.reimb_service;
import com.samantha.service.reimb_service_impl;
import com.samantha.service.user_service;
import com.samantha.service.user_service_impl;

//extracts out request bodies, performs service calls, makes dynamic service responses
public class request_helper {
	private static Logger LOGGER = Logger.getLogger(request_helper.class);

	private static user_service userService = new user_service_impl(new user_dao_impl());
	private static reimb_service reimbService = new reimb_service_impl(new reimbursement_dao_impl());

	@SuppressWarnings("deprecation")
	public static void processRegistration(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// NOTE: These steps are to be followed by only POST RequestHelper methods!!!
		// Not as GET, PATCH, DELETE, etc.
		// 1. log the event
		LOGGER.info("In RequestHelper - processRegistration() started");
		int id = 0;

		// 2. extract the user information from the HTTP request body
		// a. initialize a BufferReader object and a StringBuilder object

		// bufferedreader is a input stream that reads binary / non-human readable data
		// because it is retrieving data from a stream, java will throw I/OException if
		// it is unable to read the characters in stream
		BufferedReader reader = req.getReader();
		StringBuilder sb = new StringBuilder();

		// now I will transfer our Reader data to our StringBuilder object, line by line
		String line = reader.readLine();

		while (line != null) {
			sb.append(line);
			line = reader.readLine();
		}

		String body = sb.toString();

		// now that we have our body, we are going to 1) log, 2) split the body up based
		// on individual parameters of information
		LOGGER.info("Request body for registration is: " + body);

		// Q: How to split body string up into different info (name, job title,
		// hiredate)

		String[] info = body.replaceAll("\\{", "").replaceAll("\"", "").replaceAll("}", "").split(",");
		List<String> values = new ArrayList<>();

		// each element in the info array, I can calling them pair in this for loop
		// in the loop, appending/adding a string to the values arraylist
		// in that string, we trim the string to look only for the value from the
		// request body

		// ex. You enter the following URL in Postman:
		// http://localhost:9001/HelloServlets/register
		// body would have this format: { "name": "Sam Rose", "jobTitle": "Chef",
		// "hiredate": "2019-11-18"}
		// info[]: ["name: bob", "jobTitle: worker", "hiredate: 2022-08-18"]
		// ex. name=bob
		// result: values["name:bob", etc.]

		for (String pair : info) {
			LOGGER.info("Original body K/V pair: " + pair.trim());
			String valOnly = pair.substring(pair.indexOf(":") + 1).trim();
			LOGGER.info("Going into values arraylist --> " + valOnly);
			values.add(valOnly); // here, I trimmed each string in the body to be just displaying the value
			// aka removed the extra characters and key from the string. Then added it to
			// the values arraylist
		}

		// 3. put that information into a temporary User object before making the
		// service method call
		LOGGER.info("User information extracted is: " + values.toString());

		// a. set the content type of my response to return to the browser
		resp.setContentType("application/json");

		// b. here is where we make the service method call
		String username = values.get(0);
		String password = values.get(1);
		String first_name = values.get(2);
		String last_name = values.get(3);
		String email = values.get(4);

		ers_users target = new ers_users(username, password, first_name, last_name,
				email, null);
		LOGGER.info("Target user: " + target);

		// 4. do the service method call
		id = userService.registerUser(target);

		// convert our response into JSON using Jackson Databind
		PrintWriter pw = resp.getWriter();

		// 5. write the response that is returning to the client
		if (id != 0) {
			target.setErs_users_Id(id);

			// this comes from Jackson Databind
			ObjectMapper om = new ObjectMapper();

			// now converted our User object into a JSON string that will be added to the
			// response
			String json = om.writeValueAsString(target);

			// adding JSON to response
			pw.println(json);

			resp.setStatus(200);
			LOGGER.info("New user info: " + target);
		} else {
			// if userId is 0, that means that request was successful but no new resource
			// was made! (status code of 204)
			resp.setStatus(204, "Failed to add account in RequestHelper");
			pw.println("Sorry, system failure. Please try again later.");
		}

		LOGGER.info("In RequestHelper - processRegistration() ended");

	}

	public static void processLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// extracting info from request body
		LOGGER.info("In RequestHelper - processLogin() started");

		BufferedReader reader = req.getReader();
		StringBuilder sb = new StringBuilder();

		String line = reader.readLine();

		while (line != null) {
			sb.append(line);
			line = reader.readLine();
		}

		String body = sb.toString();

		LOGGER.info("Request body for login is: " + body);

		String[] info = body.replaceAll("\\{", "").replaceAll("\"", "").replaceAll("}", "").split(",");
		List<String> values = new ArrayList<>();

		for (String pair : info) {
			LOGGER.info("Original body K/V pair: " + pair.trim());
			String valOnly = pair.substring(pair.indexOf(":") + 1).trim();
			LOGGER.info("Going into values arraylist --> " + valOnly);
			values.add(valOnly);
		}

		LOGGER.info("User information extracted is: " + values.toString());

		// make my temp user for before the service call
		// NOTE: this step is not needed if you do not need the full Java object

		// make the service method call
		boolean isLoggedIn = userService.loginUser(values.get(0), values.get(1));

		PrintWriter pw = resp.getWriter();
		ObjectMapper om = new ObjectMapper();

		// create the response
		if (isLoggedIn == true) {
			resp.setContentType("application/json");
			resp.setStatus(200);

			// now that we have a successfully logged in user, we must keep track on their
			// session requests
			// therefore we will be adding a HTTP cookie as a response header

			// This cookie can then be used with future, subquent requests as it will hold
			// the user's information within its
			// header info
			ers_users target = user_service.getUserUsingUsername(values.get(0));
			resp.addCookie(new Cookie("Current-User", target.getErs_username()));

			// adding JSON to response
			String json = "User was successfully able to log into application.";
			om.writeValueAsString(json);

			resp.setStatus(200);
			LOGGER.info("Login successful - returning cookie in response");
		} else {
			resp.setStatus(401); // UNAUTHORIZED STATUS CODE = 401
			pw.println("Username and/or password didn't match what was on file. Please try again.");
		}
		LOGGER.info("In RequestHelper - processLogin() ended");

	}
	
	 public static void processReimbursement(HttpServletRequest req, HttpServletResponse resp) throws IOException {
	        LOGGER.info("In RequestHelper - processReimbursement() started");
	        PrintWriter pw = resp.getWriter();
	        int targetId = 0;
	        ers_users currentUser = new ers_users();
	        Cookie[] cookies = req.getCookies();
	        if (cookies != null) {
	            for (Cookie cookie : cookies) {
	                if (cookie.getName().equals("Current-User")) {
	                    LOGGER.info("Current logged in user is: " + cookie.getValue());
	                    currentUser = user_service.getUserUsingUsername(cookie.getValue());
	                }
	            }
	        }

	       ers_reimbursement reimbursement = new ers_reimbursement();
	        reimbursement.setReimb_amount(Integer.valueOf(req.getParameter("reimb_amount")));
	        reimbursement.setReimb_description(req.getParameter("reimb_description"));
	        reimbursement.setReimb_submitted(LocalDateTime.now());
	        reimbursement.setReimb_author(currentUser.getErs_users_Id());
	        //reimbursement.setReimbStatusId(1);
	        reimbursement.setReimb_type_id(1);

	        targetId = reimb_service.createReimbursement(reimbursement);

	    }
}
