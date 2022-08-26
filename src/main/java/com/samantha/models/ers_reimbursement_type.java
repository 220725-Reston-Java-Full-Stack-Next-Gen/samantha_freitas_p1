package com.samantha.models;

public class ers_reimbursement_type {

	/*So first what we need are the variables involving a user and their identifiers
	 * There needs to be a field for every column that exists in the database table
	 */
	
	//These variables set the state of our class instance
	private Integer reimb_type_Id; //Primary Key
	private String reimb_type;
	
	
	
	/*
	 *Next are the constructors which make new instances of our class 
	 */
	
	public ers_reimbursement_type() {
		super();
		// here is a no args (args = arguments) constructor. 
		//creating a class like this would give us an object with all null values
	}
	
	public ers_reimbursement_type(Integer reimb_type_Id, String reimb_type) {
		// this is our all args constructor
		// when we are reading from our database we will use this constructor to build our object
		super();
		this.reimb_type_Id = reimb_type_Id;
		this.reimb_type = reimb_type;
	}
	
	public ers_reimbursement_type(String reimb_type) {
		// here we have our some args constructor because  we do not need the employee id when we are creating an employee - aka inserting into the employee table
		super();
		this.reimb_type = reimb_type;
	}
	
	
	
	/*
	 *Next we need to generate our getters and setters to actually produce our states (variables) 
	 *
	 *Getters are used to call existing data from an object instance
	 *
	 *Setters are used to set data to an object instance
	 */
	
	public Integer getReimb_type_Id() {
		return reimb_type_Id;
	}
	public void setReimb_type_Id(Integer reimb_type_Id) {
		this.reimb_type_Id = reimb_type_Id;
	}
	public String getReimb_type() {
		return reimb_type;
	}
	public void setReimb_type(String reimb_type) {
		this.reimb_type = reimb_type;
	}

	@Override
	public String toString() {
		return "ers_reimbursement_type [reimb_type_Id=" + reimb_type_Id + ", reimb_type=" + reimb_type + "]";
	}
	
	

}
