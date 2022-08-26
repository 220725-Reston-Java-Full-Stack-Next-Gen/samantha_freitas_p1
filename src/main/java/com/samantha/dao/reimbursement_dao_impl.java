package com.samantha.dao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.samantha.models.ers_reimbursement;
import com.samantha.models.ers_reimbursement_status;
import com.samantha.util.*;

public class reimbursement_dao_impl implements reimbursement_dao {
	private static Logger logger = Logger.getLogger(user_dao_impl.class);

	@Override
	public int createReimbursement(ers_reimbursement ers_reimbursement, int reimb_id) {
		logger.info("In reimbursement_dao_impl - createReimbursement() started. Adding reimbursement: " + ers_reimbursement);
		int targetId = 0;
		
		//1. open my JDBC connection
				try(Connection conn = JDBCConnectionUtil.getConnection()){
					String sql = "INSERT INTO ers_reimbursement (reimb_amount, reimb_submitted, reimb_description, reimb_author, reimb_status, reimb_type_id) VALUES (?,?,?,?,3,?)";


		            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);


		            pstmt.setInt(1,ers_reimbursement.getReimb_amount());
		            pstmt.setTimestamp(2, Timestamp.valueOf(ers_reimbursement.getReimb_submitted()));
		            pstmt.setString(3, ers_reimbursement.getReimb_description());
		            pstmt.setInt(4, ers_reimbursement.getReimb_author());
		            pstmt.setInt(5, ers_reimbursement.getReimb_type_id());


		            pstmt.executeUpdate();
		            ResultSet rs = pstmt.getGeneratedKeys();
		            rs.next();

		            targetId = rs.getInt("reimb_id");

					//another database call here
					//to update the author of this reimbursement
					sql = "UPDATE ers_reimbursement SET reimb_author = ? WHERE reimb_id = ?";
					
					PreparedStatement psmnt2 = conn.prepareStatement(sql); //the reason why I'm doing this is to not have any broken connections for this new reimbursement to its owner user
					//therefore, this action requires us to open another DB transaction
					
					psmnt2.setInt(1, reimb_id);
					psmnt2.setInt(2, targetId);
					int isSuccessfulChangeOwner = psmnt2.executeUpdate();
					logger.info("Successful update of owner of reimbursement" + targetId + " to DB: 1 FOR YES/0 FOR NO: " + isSuccessfulChangeOwner);
					
				}catch(SQLException e) {
					logger.warn("Unable to add new reimbursement: " + e);
				}
				
		logger.info("In reimbursement_dao_impl - createReimbursement() ended. New reimbursement id is: " + targetId);
		return targetId;
	}

	@Override
	public ers_reimbursement readReimbursement(int reimb_Id) {
		try(Connection conn = JDBCConnectionUtil.getConnection()){
			String sql = "SELECT * FROM ers_reimbursements WHERE reimb_id=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);  // we do not need to generate keys because we are querying the database so we are already getting a result set
			
			pstmt.setInt(1, reimb_Id);
			
			// by using executeQuery() we get a result set back
			ResultSet rs = pstmt.executeQuery();
						
			//we are creating an instance of our user as we will have to return a user
						
			ers_reimbursement reimb = new ers_reimbursement();
			
			// this result set that we get back from our query is what we are iterating through in order to make out Employee
			while(rs.next()) {
				reimb.setReimb_Id(rs.getInt("reimb_Id"));
				reimb.setReimb_amount(rs.getInt("reimb_amount"));
				reimb.setReimb_submitted(rs.getTimestamp("reimb_submitted").toLocalDateTime());
				if(rs.getTimestamp("reimb_resolved")!=null){
					reimb.setReimb_resolved(rs.getTimestamp("reimb_resolved").toLocalDateTime());
                }
				reimb.setReimb_description(rs.getString("reimb_description"));
				reimb.setReimb_receipt(rs.getString("reimb_receipt"));
				reimb.setReimb_author(rs.getInt("reimb_author"));
				reimb.setReimb_resolver(rs.getInt("reimb_resolver"));
				reimb.setReimb_status(new ers_reimbursement_status(rs.getString("reimb_status")));
				reimb.setReimb_type_id(rs.getInt("reimb_type"));
				
			}						
			return reimb;
		}
		catch (SQLException sqlExc) {
			System.out.println("This is the reimbursement_dao_impl read() " + sqlExc.getMessage());
		}
		return null;
	}
	
	@Override
	public List<ers_reimbursement> readAllReimbursements(int reimb_id) {
		//Make a list to store all reimbursements
		List<ers_reimbursement> allReimbs = new ArrayList<ers_reimbursement>();
		
		//Begin iteration
		try (Connection conn = JDBCConnectionUtil.getConnection()){
			String sql = "select * from ers_reimbursements where reimb_id=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			ResultSet rs = pstmt.executeQuery();
			ers_reimbursement reimb = new ers_reimbursement();
			
			while(rs.next()) {
				reimb.setReimb_Id(rs.getInt("reimb_Id"));
				reimb.setReimb_amount(rs.getInt("reimb_amount"));
				reimb.setReimb_submitted(rs.getTimestamp("reimb_submitted").toLocalDateTime());
				if(rs.getTimestamp("reimb_resolved")!=null){
					reimb.setReimb_resolved(rs.getTimestamp("reimb_resolved").toLocalDateTime());
                }
				reimb.setReimb_description(rs.getString("reimb_description"));
				reimb.setReimb_receipt(rs.getString("reimb_receipt"));
				reimb.setReimb_author(rs.getInt("reimb_author"));
				reimb.setReimb_resolver(rs.getInt("reimb_resolver"));
				reimb.setReimb_status(new ers_reimbursement_status(rs.getString("reimb_status")));
				reimb.setReimb_type_id(rs.getInt("reimb_type"));
				
				//While in this iteration, store the reimbursement data
				allReimbs.add(reimb);
				
			}
			
		} catch (SQLException sqlExc) {
			sqlExc.printStackTrace();
		}
		return allReimbs;
	}
	
	@Override
	public List<ers_reimbursement> readPendingReimbursements(int reimb_Id) {
		//Make a list to store all reimbursements
				List<ers_reimbursement> pendingReimbs = new ArrayList<ers_reimbursement>();
				
				//Begin iteration
				try (Connection conn = JDBCConnectionUtil.getConnection()){
					String sql = "select * from ers_reimbursements where reimb_status_id = 3";
					PreparedStatement pstmt = conn.prepareStatement(sql);
					
					ResultSet rs = pstmt.executeQuery();
					ers_reimbursement reimb = new ers_reimbursement();
					
					while(rs.next()) {
						reimb.setReimb_Id(rs.getInt("reimb_Id"));
						reimb.setReimb_amount(rs.getInt("reimb_amount"));
						reimb.setReimb_submitted(rs.getTimestamp("reimb_submitted").toLocalDateTime());
						if(rs.getTimestamp("reimb_resolved")!=null){
							reimb.setReimb_resolved(rs.getTimestamp("reimb_resolved").toLocalDateTime());
		                }
						reimb.setReimb_description(rs.getString("reimb_description"));
						reimb.setReimb_receipt(rs.getString("reimb_receipt"));
						reimb.setReimb_author(rs.getInt("reimb_author"));
						reimb.setReimb_resolver(rs.getInt("reimb_resolver"));
						reimb.setReimb_status(new ers_reimbursement_status(rs.getString("reimb_status")));
						reimb.setReimb_type_id(rs.getInt("reimb_type"));
						
						//While in this iteration, store the reimbursement data
						pendingReimbs.add(reimb);
						
					}
					
				} catch (SQLException sqlExc) {
					sqlExc.printStackTrace();
				}
				return pendingReimbs;

	}
	
	@Override
	public List<ers_reimbursement> readApprovedReimbursements(int reimb_Id) {
		//Make a list to store all reimbursements
				List<ers_reimbursement> approvedReimbs = new ArrayList<ers_reimbursement>();
				
				//Begin iteration
				try (Connection conn = JDBCConnectionUtil.getConnection()){
					String sql = "select * from ers_reimbursements where reimb_status= 1";
					PreparedStatement pstmt = conn.prepareStatement(sql);
					
					ResultSet rs = pstmt.executeQuery();
					ers_reimbursement reimb = new ers_reimbursement();
					
					while(rs.next()) {
						reimb.setReimb_Id(rs.getInt("reimb_Id"));
						reimb.setReimb_amount(rs.getInt("reimb_amount"));
						reimb.setReimb_submitted(rs.getTimestamp("reimb_submitted").toLocalDateTime());
						if(rs.getTimestamp("reimb_resolved")!=null){
							reimb.setReimb_resolved(rs.getTimestamp("reimb_resolved").toLocalDateTime());
		                }
						reimb.setReimb_description(rs.getString("reimb_description"));
						reimb.setReimb_receipt(rs.getString("reimb_receipt"));
						reimb.setReimb_author(rs.getInt("reimb_author"));
						reimb.setReimb_resolver(rs.getInt("reimb_resolver"));
						reimb.setReimb_status(new ers_reimbursement_status(rs.getString("reimb_status")));
						reimb.setReimb_type_id(rs.getInt("reimb_type"));
						
						//While in this iteration, store the reimbursement data
						approvedReimbs.add(reimb);
						
					}
					
				} catch (SQLException sqlExc) {
					sqlExc.printStackTrace();
				}
				return approvedReimbs;

	}
	
	@Override
	public List<ers_reimbursement> readDeniedReimbursements(int reimb_Id) {
		//Make a list to store all reimbursements
				List<ers_reimbursement> deniedReimbs = new ArrayList<ers_reimbursement>();
				
				//Begin iteration
				try (Connection conn = JDBCConnectionUtil.getConnection()){
					String sql = "select * from ers_reimbursements where reimb_status=2";
					PreparedStatement pstmt = conn.prepareStatement(sql);
					
					ResultSet rs = pstmt.executeQuery();
					ers_reimbursement reimb = new ers_reimbursement();
					
					while(rs.next()) {
						reimb.setReimb_Id(rs.getInt("reimb_Id"));
						reimb.setReimb_amount(rs.getInt("reimb_amount"));
						reimb.setReimb_submitted(rs.getTimestamp("reimb_submitted").toLocalDateTime());
						if(rs.getTimestamp("reimb_resolved")!=null){
							reimb.setReimb_resolved(rs.getTimestamp("reimb_resolved").toLocalDateTime());
		                }
						reimb.setReimb_description(rs.getString("reimb_description"));
						reimb.setReimb_receipt(rs.getString("reimb_receipt"));
						reimb.setReimb_author(rs.getInt("reimb_author"));
						reimb.setReimb_resolver(rs.getInt("reimb_resolver"));
						reimb.setReimb_status(new ers_reimbursement_status(rs.getString("reimb_status")));
						reimb.setReimb_type_id(rs.getInt("reimb_type"));
						
						//While in this iteration, store the reimbursement data
						deniedReimbs.add(reimb);
						
					}
					
				} catch (SQLException sqlExc) {
					sqlExc.printStackTrace();
				}
				return deniedReimbs;

	}
	

	@Override
	public Boolean updateReimbursement(int reimb_Id, LocalDateTime reimb_resolved, int reimb_resolver, ers_reimbursement reimb_status) {
		// Here is where the database for reimbursement can be updated
				logger.info("In reimbursement_dao_impl - updateUser() started. Updated user info: " + reimb_Id);
				
				//Updates each thing
				try (Connection conn = JDBCConnectionUtil.getConnection()){
					String sql = "UPDATE ers_reimbursement SET reimb_resolved = ?, reimb_resolver = ?, reimb_status_id = ? WHERE ers_users_Id = ?";
							
					PreparedStatement pstmt = conn.prepareStatement(sql);	
					pstmt.setTimestamp(1, Timestamp.valueOf(reimb_resolved));
					pstmt.setInt(2, reimb_resolver);
					pstmt.setString(3,reimb_status.getReimb_status().getReimb_status());
					
					int isSuccessfulUpdate = pstmt.executeUpdate();
					logger.info("Successful update to DB: 1 FOR YES/0 FOR NO: " + isSuccessfulUpdate);
					return true;
				}
				catch (SQLException sqlExc) {
					logger.warn("Unable to update reimbursement: " + sqlExc);
					//System.out.println("This is the user_dao_impl - update() " +sqlExc.getMessage());
				}
				logger.info("In reimbursement_dao_impl - updateReimbursement() ended.");
				return false;
		
	}
	
	@Override
	public void deleteReimbursement() {
		// TODO Auto-generated method stub

	}



}
