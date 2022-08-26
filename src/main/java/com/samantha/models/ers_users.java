package com.samantha.models; // A model represents data that is on our database table.

import java.util.ArrayList;
import java.util.List;

public class ers_users {

	/*So first what we need are the variables involving a user and their identifiers
	 * There needs to be a field for every column that exists in the database table
	 */
	
	
	//These variables set the state of our class instance
	private Integer ers_users_Id;  //This is the primary key in the database. It is the ID number assigned to the user
	private String ers_username;  //This is unique in the database. It is the user name chosen or assigned to the user
	private String ers_password;  //This is the password for the user name specified
	private String user_first_name;  //This is the users first name
	private String user_last_name;  //This is the users last name
	private String email;  //This is the users email address. It must be unique
	private ers_user_roles user_role;  // This is a foreign key pointing to the ID in the table urs_user_roles in the database
	
	private List<ers_reimbursement> reimbursement = new ArrayList<ers_reimbursement>();
	
	/*
	 *Next are the constructors which make new instances of our class 
	 */
	
	// here is a no args (args = arguments) constructor. 
	//creating a class like this would give us an object with all null values
	public ers_users() {
		super(); // remember: all classes are subclasses of super
	}
	
	// this is our all args constructor
	// when we are reading from our database we will use this constructor to build our object
	public ers_users(Integer ers_users_Id, String ers_username, String ers_password, String user_first_name,
			String user_last_name, String email, ers_user_roles user_role, List<ers_reimbursement> reimbursement) {
		super();
		this.ers_users_Id = ers_users_Id;
		this.ers_username = ers_username;
		this.ers_password = ers_password;
		this.user_first_name = user_first_name;
		this.user_last_name = user_last_name;
		this.email = email;
		this.user_role = user_role;
		this.reimbursement = reimbursement;
	}
	
	// here we have our some args constructor because  we do not need the employee id when we are creating an employee - aka inserting into the employee table
	public ers_users(String ers_username, String ers_password, String user_first_name, String user_last_name,
			String email, ers_user_roles user_role) {
		super();
		this.ers_username = ers_username;
		this.ers_password = ers_password;
		this.user_first_name = user_first_name;
		this.user_last_name = user_last_name;
		this.email = email;
		this.user_role = user_role;
		//this.reimbursement = reimbursement;
	}
	
	
	
	/*
	 *Next we need to generate our getters and setters to actually produce our states (variables) 
	 *
	 *Getters are used to call existing data from an object instance
	 *
	 *Setters are used to set data to an object instance
	 */

	public Integer getErs_users_Id() {
		return ers_users_Id;
	}

	public void setErs_users_Id(Integer ers_users_Id) {
		this.ers_users_Id = ers_users_Id;
	}

	public String getErs_username() {
		return ers_username;
	}

	public void setErs_username(String ers_username) {
		this.ers_username = ers_username;
	}

	public String getErs_password() {
		return ers_password;
	}

	public void setErs_password(String ers_password) {
		this.ers_password = ers_password;
	}

	public String getUser_first_name() {
		return user_first_name;
	}

	public void setUser_first_name(String user_first_name) {
		this.user_first_name = user_first_name;
	}

	public String getUser_last_name() {
		return user_last_name;
	}

	public void setUser_last_name(String user_last_name) {
		this.user_last_name = user_last_name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public ers_user_roles getUser_role() {
		return user_role;
	}

	public void setUser_role(ers_user_roles user_role) {
		this.user_role = user_role;
	}

	public List<ers_reimbursement> getReimbursement() {
		return reimbursement;
	}

	public void setReimbursement(List<ers_reimbursement> reimbursement) {
		this.reimbursement = reimbursement;
	}

	@Override
	public String toString() {
		return "ers_users [ers_users_Id=" + ers_users_Id + ", ers_username=" + ers_username + ", ers_password="
				+ ers_password + ", user_first_name=" + user_first_name + ", user_last_name=" + user_last_name
				+ ", email=" + email + ", user_role=" + user_role + ", reimbursement=" + reimbursement + "]";
	}
	
}