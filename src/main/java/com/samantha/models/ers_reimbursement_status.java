package com.samantha.models; // A model represents data that is on our database table.

public class ers_reimbursement_status {

	/*So first what we need are the variables involving a user and their identifiers
	 * There needs to be a field for every column that exists in the database table
	 */
	
	//These variables set the state of our class instance
	private Integer reimb_status_Id;  //Primary key
	private String reimb_status;
	
	
	
	/*
	 *Next are the constructors which make new instances of our class 
	 */
	
	public ers_reimbursement_status() {
		super();
		// here is a no args (args = arguments) constructor. 
		//creating a class like this would give us an object with all null values
	}


	public ers_reimbursement_status(Integer reimb_status_Id, String reimb_status) {
		// this is our all args constructor
		// when we are reading from our database we will use this constructor to build our object
		super();
		this.reimb_status_Id = reimb_status_Id;
		this.reimb_status = reimb_status;
	}


	public ers_reimbursement_status(String reimb_status) {
		// here we have our some args constructor because  we do not need the employee id when we are creating an employee - aka inserting into the employee table
		super();
		this.reimb_status = reimb_status;
	}

	
	
	/*
	 *Next we need to generate our getters and setters to actually produce our states (variables) 
	 *
	 *Getters are used to call existing data from an object instance
	 *
	 *Setters are used to set data to an object instance
	 */
	
	public Integer getReimb_status_Id() {
		return reimb_status_Id;
	}


	public void setReimb_status_Id(Integer reimb_status_Id) {
		this.reimb_status_Id = reimb_status_Id;
	}


	public String getReimb_status() {
		return reimb_status;
	}


	public void setReimb_status(String reimb_status) {
		this.reimb_status = reimb_status;
	}


	@Override
	public String toString() {
		return "ers_reimbursement_status [reimb_status_Id=" + reimb_status_Id + ", reimb_status=" + reimb_status + "]";
	}

}
