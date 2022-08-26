package com.samantha.models;

import java.time.LocalDateTime;

public class ers_reimbursement {
	/*So first what we need are the variables involving a user and their identifiers
	 * There needs to be a field for every column that exists in the database table
	 */
	
	//These variables set the state of our class instance
	private Integer reimb_Id;  //Primary key
	private Integer reimb_amount;
	private LocalDateTime reimb_submitted;
	private LocalDateTime reimb_resolved;
	private String reimb_description;
	private String reimb_receipt;
	private Integer reimb_author;  //Foreign key
	private Integer reimb_resolver;  //Foreign key
	private ers_reimbursement_status reimb_status;  //Foreign key
	private Integer reimb_type_id;  //Foreign key

	/*
	 *Next are the constructors which make new instances of our class 
	 */
	
	// here is a no args (args = arguments) constructor. 
	//creating a class like this would give us an object with all null values	
	public ers_reimbursement() {
		super();
	}
	
	// this is our all args constructor
	// when we are reading from our database we will use this constructor to build our object
	public ers_reimbursement(Integer reimb_Id, Integer reimb_amount, LocalDateTime reimb_submitted,
				LocalDateTime reimb_resolved, String reimb_description, String reimb_receipt, Integer reimb_author,
				Integer reimb_resolver, ers_reimbursement_status reimb_status,
				Integer reimb_type_id) {
			super();
			this.reimb_Id = reimb_Id;
			this.reimb_amount = reimb_amount;
			this.reimb_submitted = reimb_submitted;
			this.reimb_resolved = reimb_resolved;
			this.reimb_description = reimb_description;
			this.reimb_receipt = reimb_receipt;
			this.reimb_author = reimb_author;
			this.reimb_resolver = reimb_resolver;
			this.reimb_status = reimb_status;
			this.reimb_type_id = reimb_type_id;
		}
	
	// here we have our some args constructor because  we do not need the employee id when we are creating an employee - aka inserting into the employee table
	public ers_reimbursement(Integer reimb_amount, LocalDateTime reimb_submitted, LocalDateTime reimb_resolved,
			String reimb_description, String reimb_receipt, Integer reimb_author, Integer reimb_resolver,
			ers_reimbursement_status reimb_status, Integer reimb_type_id) {
		super();
		this.reimb_amount = reimb_amount;
		this.reimb_submitted = reimb_submitted;
		this.reimb_resolved = reimb_resolved;
		this.reimb_description = reimb_description;
		this.reimb_receipt = reimb_receipt;
		this.reimb_author = reimb_author;
		this.reimb_resolver = reimb_resolver;
		this.reimb_status = reimb_status;
		this.reimb_type_id = reimb_type_id;
	}
	
	/*
	 *Next we need to generate our getters and setters to actually produce our states (variables) 
	 *
	 *Getters are used to call existing data from an object instance
	 *
	 *Setters are used to set data to an object instance
	 */

	public Integer getReimb_Id() {
		return reimb_Id;
	}

	public void setReimb_Id(Integer reimb_Id) {
		this.reimb_Id = reimb_Id;
	}

	public Integer getReimb_amount() {
		return reimb_amount;
	}

	public void setReimb_amount(Integer reimb_amount) {
		this.reimb_amount = reimb_amount;
	}

	public LocalDateTime getReimb_submitted() {
		return reimb_submitted;
	}

	public void setReimb_submitted(LocalDateTime reimb_submitted) {
		this.reimb_submitted = reimb_submitted;
	}

	public LocalDateTime getReimb_resolved() {
		return reimb_resolved;
	}

	public void setReimb_resolved(LocalDateTime reimb_resolved) {
		this.reimb_resolved = reimb_resolved;
	}

	public String getReimb_description() {
		return reimb_description;
	}

	public void setReimb_description(String reimb_description) {
		this.reimb_description = reimb_description;
	}

	public String getReimb_receipt() {
		return reimb_receipt;
	}

	public void setReimb_receipt(String reimb_receipt) {
		this.reimb_receipt = reimb_receipt;
	}

	public Integer getReimb_author() {
		return reimb_author;
	}

	public void setReimb_author(Integer reimb_author) {
		this.reimb_author = reimb_author;
	}

	public Integer getReimb_resolver() {
		return reimb_resolver;
	}

	public void setReimb_resolver(Integer reimb_resolver) {
		this.reimb_resolver = reimb_resolver;
	}

	public ers_reimbursement_status getReimb_status() {
		return reimb_status;
	}

	public void setReimb_status(ers_reimbursement_status reimb_status) {
		this.reimb_status = reimb_status;
	}

	public Integer getReimb_type_id() {
		return reimb_type_id;
	}

	public void setReimb_type_id(Integer reimb_type_id) {
		this.reimb_type_id = reimb_type_id;
	}

	@Override
	public String toString() {
		return "ers_reimbursement [reimb_Id=" + reimb_Id + ", reimb_amount=" + reimb_amount + ", reimb_submitted="
				+ reimb_submitted + ", reimb_resolved=" + reimb_resolved + ", reimb_description=" + reimb_description
				+ ", reimb_receipt=" + reimb_receipt + ", reimb_author=" + reimb_author + ", reimb_resolver="
				+ reimb_resolver + ", reimb_status_id=" + reimb_status + ", reimb_type_id=" + reimb_type_id + "]";
	}
	
	
}
