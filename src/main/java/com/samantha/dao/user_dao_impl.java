package com.samantha.dao;

import java.sql.*;

import org.apache.log4j.Logger;

import com.samantha.models.ers_user_roles;
import com.samantha.models.ers_users;
import com.samantha.util.JDBCConnectionUtil;

public class user_dao_impl implements user_dao {
	
		//The following can be put here or the connection can be made in the instances
		// When instance of the DAO is created, this connection is created for us. In this case, we are making the connections in the try blocks of each instance. However, it then must be closed at the end.
		//Connection conn; 
		//public user_dao_impl() {
			//conn = JDBCConnectionUtil.getConnection();  
		//}
			
	//we are making a logger here because I want to track the events in this class as it makes SQL calls to the database
	private static Logger logger = Logger.getLogger(user_dao_impl.class);
				
	//connect JDBC to our servlets by providing a static initializer block that points to the Postgres driver
	//without this, you will be unable to make DB calls when Tomcat is running
	static {
		try{
			Class.forName("org.postgresql.Driver");
		}catch(ClassNotFoundException e) {
			logger.warn("Static initializer block failed: " + e);
		}
	}
	
	@Override
	public int createUser(ers_users user) {
		logger.info("In user_dao_impl - createUser() started. Adding user: " + user);
		int targetId = 0;  //This is used to return at the end of the method
		
		// This create method is not true creation, but insertion into a table that has already been made in PostgreSQL
		try(Connection conn = JDBCConnectionUtil.getConnection()) {
		//This string is being created because our database understands SQL, therefore our commands must be written in the format it understands.
		//? are wild cards. They are filled by prepared statements, which are the median between our program and the database.
			String sql = "INSERT INTO ers_users (ers_username, ers_password, user_first_name, user_last_name, user_email, user_role_type) VALUES (?, ?, ?, ?, ?,'Employee')";  			
					
			//prepareStatement() throws an SQLException so we must wrap the code we are trying to execute in a try catch
			PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			// setting the parameters for the ? in the string above, i.e. (What ? it is, getter to be executed)
			pstmt.setString(1, user.getErs_username());
			pstmt.setString(2, user.getErs_password());
			pstmt.setString(3, user.getUser_first_name());
			pstmt.setString(4, user.getUser_last_name());
			pstmt.setString(5, user.getEmail());
					
			// note that when we are inserting, or updating we will be using 
			//executeUpdate();
			// when we are querying the database we will be using 
			//executeQuery()
			// when we are deleting we use execute()
			int isSuccessfulInsert = pstmt.executeUpdate();
			logger.info("Successful registration to DB: 1 FOR YES/0 FOR NO: " + isSuccessfulInsert);
					
			ResultSet rs = pstmt.getGeneratedKeys();		
					
			//user.setUser_role(new ers_user_roles(rs.getInt("ers_user_role_Id"), rs.getString("user_role")));		
			// the cursor is initially placed right before the first element of the result set
			//in order for us to advance into the next item we must use the command rs.next()
			rs.next();
					
			System.out.println("I'm in the user_dao_impl class: " + rs.getInt(1));
			targetId =  rs.getInt("ers_users_Id");
		} 		
		catch(SQLException sqlExc) {
			logger.warn("Unable to add new user: " + sqlExc);
			//System.out.println("This is the user_dao_impl create() " + sqlExc.getMessage());
		}
		logger.info("In user_dao_impl - createUser() ended. New user id is: " + targetId);
		return targetId;
	}

	@Override
	public ers_users readUsername(String ers_username) {
		logger.info("In user_dao_impl - readUsername() started. Searching username: " + ers_username);
		
		//we are creating an instance of our user as we will have to return a user
						
		ers_users user = new ers_users();
		
		try(Connection conn = JDBCConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM ers_users WHERE ers_username=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);  // we do not need to generate keys because we are querying the database so we are already getting a result set
			
			pstmt.setString(1, ers_username);
			
			// by using executeQuery() we get a result set back
			ResultSet rs = pstmt.executeQuery();
			
			// this result set that we get back from our query is what we are iterating through in order to make out Employee
			while(rs.next()) {
				user.setErs_users_Id(rs.getInt("ers_users_Id"));
				user.setErs_username(rs.getString("ers_username"));
				user.setErs_password(rs.getString("ers_password"));
				user.setUser_first_name(rs.getString("user_first_name"));
				user.setUser_last_name(rs.getString("user_last_name"));
				user.setEmail(rs.getString("user_email"));
				user.setUser_role(new ers_user_roles(rs.getString("user_role_type")));
							
			}						
		} 		
		catch(SQLException sqlExc) {
			logger.warn("Unable to read user: " + sqlExc);
			//System.out.println("This is the user_dao_impl create() " + sqlExc.getMessage());
		}
		logger.info("In user_dao_impl - readUsername() ended. User info is: " + user);
		return user;
	}

	@Override
	public void updateUser(ers_users user) {
		// Here is where the database for user can be updated
		logger.info("In user_dao_impl - updateUser() started. Updated user info: " + user);
		
		//Updates each thing
		try (Connection conn = JDBCConnectionUtil.getConnection()){
			String sql = "UPDATE ers_users SET ers_username = ?, ers_password = ?, user_first_name = ?, user_last_name = ?, user_email = ?, user_role_type = ?, user_email = ? WHERE ers_users_Id = ?";
					
			PreparedStatement pstmt = conn.prepareStatement(sql);	
			pstmt.setString(1, user.getErs_username());
			pstmt.setString(2, user.getErs_password());
			pstmt.setString(3, user.getUser_first_name());
			pstmt.setString(4, user.getUser_last_name());
			pstmt.setString(5, user.getEmail());
			pstmt.setString(6,  user.getUser_role().getUser_role());
			pstmt.setInt(7, user.getErs_users_Id());
			
			int isSuccessfulUpdate = pstmt.executeUpdate();
			logger.info("Successful update to DB: 1 FOR YES/0 FOR NO: " + isSuccessfulUpdate);
		}
		catch (SQLException sqlExc) {
			logger.warn("Unable to update user: " + sqlExc);
			//System.out.println("This is the user_dao_impl - update() " +sqlExc.getMessage());
		}
		logger.info("In user_dao_impl - updateUser() ended.");
	}

	@Override
	public void deleteUser(ers_users user) {
		// TODO Auto-generated method stub

	}

}
