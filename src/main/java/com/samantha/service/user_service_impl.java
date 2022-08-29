package com.samantha.service;

import org.apache.log4j.Logger;

import com.samantha.models.ers_users;
import com.samantha.dao.user_dao;
import com.samantha.dao.user_dao_impl;

public class user_service_impl implements user_service {
	private static Logger logger = Logger.getLogger(user_service_impl.class);
	
	//because we depend on our dao layer here, we will need an instance of it every time we need a new user_service_impl object
	private user_dao user_dao;
	
	//we need access to our methods in the database, thus the following
	public user_service_impl(user_dao_impl user_dao) {
		super();
		this.user_dao = user_dao;
	}
	
	@Override
	public Integer registerUser(ers_users user) {
		// log event start
		logger.info("in UserServiceImpl - register() started. New user: " + user);
		
		// make database call
		int id = user_dao.createUser(user);
		// log event end
		logger.info("In UserServiceImpl - register() ended. New user id: " + id);
		// return data if necessary
		return id;
	}

	@Override
	public boolean loginUser(String username, String password) {
		// log event start
				logger.info("In UserServiceImpl - login() started. Credentials: Username=" + username + ", Password=" + password);
		
		// make database call
		ers_users user_wanted = user_dao.readUsername(username);
		
		//3. check if password & user name matches DB info
		if(username.equalsIgnoreCase(user_wanted.getErs_username()) && password.equalsIgnoreCase(user_wanted.getErs_password())) {
			//3. log event end
			logger.info("In UserServiceImpl - login() ended. Logged In");
			return true;
		}
		//4. return data in return statement
		logger.warn("In UserServiceImpl - login() ended. Username and/or password do not match DB information.");
		return false;
	}

	@Override
	public ers_users getUserUsingUsername(String username) {
		//1. log event start
		logger.info("In UserServiceImpl - getUserByUsername() started. Username: " + username);
		
		//2. make my DB call
		ers_users target = user_dao.readUsername(username);
		
		//3. log event end
		logger.info("In UserServiceImpl - getUserUsingUsername() ended. Found user: " + target);
		
		//4. return data in return statement
		return target;
	}

}
