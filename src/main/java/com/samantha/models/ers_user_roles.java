package com.samantha.models;

public class ers_user_roles {
	/*So first what we need are the variables involving a user and their identifiers
	 * There needs to be a field for every column that exists in the database table
	 */
	
	//These variables set the state of our class instance
	private Integer ers_user_role_Id;  //Primary Key
	private String user_role;
	
	
	
	/*
	 *Next are the constructors which make new instances of our class 
	 */
	
	public ers_user_roles() {
		super(); // remember: all classes are subclasses of super
		// here is a no args (args = arguments) constructor. 
		//creating a class like this would give us an object with all null values
	}
	
	public ers_user_roles(Integer ers_user_role_Id, String user_role) {
		// this is our all args constructor
		// when we are reading from our database we will use this constructor to build our object
		super();
		this.ers_user_role_Id = ers_user_role_Id;
		this.user_role = user_role;
	}

	public ers_user_roles(String user_role) {
		// here we have our some args constructor because  we do not need the employee id when we are creating an employee - aka inserting into the employee table
		super();
		this.user_role = user_role;
	}
	
	
	
	/*
	 *Next we need to generate our getters and setters to actually produce our states (variables) 
	 *
	 *Getters are used to call existing data from an object instance
	 *
	 *Setters are used to set data to an object instance
	 */
	
	public Integer getErs_user_role_Id() {
		return ers_user_role_Id;
	}

	public void setErs_user_role_Id(Integer ers_user_role_Id) {
		this.ers_user_role_Id = ers_user_role_Id;
	}

	public String getUser_role() {
		return user_role;
	}

	public void setUser_role(String user_role) {
		this.user_role = user_role;
	}

	@Override
	public String toString() {
		return "ers_user_roles [ers_user_role_Id=" + ers_user_role_Id + ", user_role=" + user_role + "]";
	}
	
}
