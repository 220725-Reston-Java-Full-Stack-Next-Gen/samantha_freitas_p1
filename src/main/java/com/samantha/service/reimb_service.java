package com.samantha.service;

import java.time.LocalDateTime;
import java.util.List;

import com.samantha.models.ers_reimbursement;

public interface reimb_service {

	static Integer createReimbursement(ers_reimbursement ers_reimbursement) {
		// TODO Auto-generated method stub
		return null;
	}
	
	Boolean updateReimbursement(int reimb_Id, LocalDateTime reimb_resolved, int reimb_resolver, ers_reimbursement reimb_status);
	
	ers_reimbursement readReimbursement(int reimb_Id);
	//ers_reimbursement read(int reimb_Id);  //reads the primary key integer and returns the user information
	
	List<ers_reimbursement> readAllReimbursements(int reimb_Id);
	List<ers_reimbursement> readPendingReimbursements(int reimb_Id);
	List<ers_reimbursement> readApprovedReimbursements(int reimb_Id);
	List<ers_reimbursement> readDeniedReimbursements(int reimb_Id);
}
