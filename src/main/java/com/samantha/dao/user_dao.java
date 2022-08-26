package com.samantha.dao;//A DAO is a Data Access Object. Used to access the database.
//A DAO is also a contract. We promise to implement the methods described here-in.

import com.samantha.models.ers_users;

//Everything in an interface is public and static implicitly.

public interface user_dao {
	
	// CRUD  methods:

	// Create	
	public int createUser(ers_users user);  //the line below works also for each method
	//Integer create(ers_users user);  //returns an integer, which will be the primary key and creates the user
	
	// Read	
	public ers_users readUsername(String ers_username);
	//ers_users read(int ers_users_Id);  //reads the primary key integer and returns the user information
	
	// Update
	public void updateUser(ers_users user);
	//Boolean update(int ers_users_Id, String ers_username, String ers_password, String user_first_name, String user_last_name, String user_email, int user_role_Id);  //used to update fields for the user. Should maybe be used for everything except user ID which is unique?
	
	// Delete
	public void deleteUser(ers_users user);
	//Boolean delete(int ers_users_Id);  //used to delete users from the database

}
