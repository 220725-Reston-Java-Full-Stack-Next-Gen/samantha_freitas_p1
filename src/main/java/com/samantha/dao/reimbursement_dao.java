package com.samantha.dao;//A DAO is a Data Access Object. Used to access the database.
//A DAO is also a contract. We promise to implement the methods described here-in.

import com.samantha.models.ers_reimbursement;

import java.time.LocalDateTime;
import java.util.List;

//Everything in an interface is public and static implicitly.

public interface reimbursement_dao {
	
	// CRUD  methods:
	// Create
	public int createReimbursement(ers_reimbursement ers_reimbursement, int reimb_id);
	//Integer create(ers_reimbursement user);  //returns an integer, which will be the primary key and creates the user
	
	// Read
	public ers_reimbursement readReimbursement(int reimb_Id);
	//ers_reimbursement read(int reimb_Id);  //reads the primary key integer and returns the user information
	
	List<ers_reimbursement> readAllReimbursements(int reimb_Id);
	List<ers_reimbursement> readPendingReimbursements(int reimb_Id);
	List<ers_reimbursement> readApprovedReimbursements(int reimb_Id);
	List<ers_reimbursement> readDeniedReimbursements(int reimb_Id);
	
	
	// Update
	public void updateReimbursement(int reimb_Id, LocalDateTime reimb_resolved, int reimb_resolver, ers_reimbursement reimb_status);
	//Boolean update(int reimb_Id, int reimb_amount,  LocalDateTime reimb_submitted, LocalDateTime reimb_resolved, String reimb_description, String reimb_receipt, int reimb_author, int reimb_resolver, int reimb_status_id, int reimb_type_id);  //used to update fields
	
	// Delete
	public void deleteReimbursement();
	//Boolean delete(int reimb_Id);  //used to delete users from the database
}