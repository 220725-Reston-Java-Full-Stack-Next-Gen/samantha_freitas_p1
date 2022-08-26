package com.samantha.service;

import com.samantha.models.ers_users;

public interface user_service {
		//THINK of what logic to your app need to perform as business
		Integer registerUser(ers_users user);
		
		public boolean loginUser(String username, String password);
		
		ers_users getUserUsingUsername(String username);
}
