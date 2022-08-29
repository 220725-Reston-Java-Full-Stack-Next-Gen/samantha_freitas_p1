package com.samantha.service;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.log4j.Logger;

import com.samantha.models.ers_users;
import com.samantha.models.ers_reimbursement;
import com.samantha.dao.reimbursement_dao;
import com.samantha.dao.reimbursement_dao_impl;

public class reimb_service_impl implements reimb_service {
	
	private static Logger logger = Logger.getLogger(reimb_service_impl.class);
	
	//because we depend on our dao layer here, we will need an instance of it every time we need a new reimb_service_impl object
	private reimbursement_dao reimbursement_dao;
	
	public reimb_service_impl(reimbursement_dao_impl reimbursement_dao_impl) {
		super();
		this.reimbursement_dao = reimbursement_dao_impl;
	}

	@Override
	public Integer createReimbursement(ers_reimbursement ers_reimbursement, int reimb_Id) {
		//1. log event start
				logger.info("In reimb_service_impl - createReimbursement() started. Reimbursement info for user id# " + reimb_Id +": " + ers_reimbursement);
				
				//2. make my DB call
				int reimb = reimbursement_dao.createReimbursement(ers_reimbursement, reimb_Id);
				
				//3. log event end
				logger.info("In reimb_service_impl - createReimbursement() ended. New reimbursement id is: " + reimb);
				
				//4. return data in return statement
				return reimb;
	}

	@Override
	public Boolean updateReimbursement(int reimb_Id, LocalDateTime reimb_resolved, int reimb_resolver,
			ers_reimbursement reimb_status) {
		Boolean bool = reimbursement_dao.updateReimbursement(reimb_Id, reimb_resolved, reimb_resolver, reimb_status);
		return bool;
	}

	@Override
	public ers_reimbursement readReimbursement(int reimb_Id) {
		return reimbursement_dao.readReimbursement(reimb_Id);
	}

	// The following do not currently do anything and are merely framework for possible expansion of the program in regards to reimbursement viewing
	@Override
	public List<ers_reimbursement> readAllReimbursements(int reimb_Id) {
		return reimbursement_dao.readAllReimbursements(reimb_Id);
	}

	@Override
	public List<ers_reimbursement> readPendingReimbursements(int reimb_Id) {
		return reimbursement_dao.readPendingReimbursements(reimb_Id);
	}

	@Override
	public List<ers_reimbursement> readApprovedReimbursements(int reimb_Id) {
		return reimbursement_dao.readApprovedReimbursements(reimb_Id);
	}

	@Override
	public List<ers_reimbursement> readDeniedReimbursements(int reimb_Id) {
		return reimbursement_dao.readDeniedReimbursements(reimb_Id);
	}

}
