/*Samantha Freitas
*
 *Description: This is the script that creates all the tables for Project 1.
 *Last Updated: 8/19/2022
 */

-- First we must drop all tables if they currently exist in order to create new tables if desired.
drop table if exists ers_reimbursement;         --reimbursement data table
drop table if exists ers_reimbursement_status;  --reimbursement status table
drop table if exists ers_reimbursement_type;    --reimbursement type table
drop table if exists ers_user_roles;            --user roles table
drop table if exists ers_users;                 --user data table

-- Next we want to create our tables in order of dependency

create table ers_users(
	ers_users_id serial constraint ers_users_pk primary key,
	ers_username varchar(50) unique,
	ers_password varchar(50),
	user_first_name varchar(100),
	user_last_name varchar(100),
	user_email varchar(150) not null unique,
	
	user_role_type varchar(30)
);

create table ers_user_roles(
	ers_user_role_id serial constraint ers_user_roles_pk primary key,
	user_role varchar(10),
	
	constraint user_roles_fk foreign key(ers_user_role_id) references ers_users(ers_users_id)
);

create table ers_reimbursement_status(
	reimb_status_id serial constraint reimb_status_pk primary key,
	reimb_status varchar(10)
);

create table ers_reimbursement_type(
	reimb_type_id serial constraint reimb_type_id primary key,
	reimb_type varchar(10)
);

create table ers_reimbursement (
	-- And we want to create our desired columns
	reimb_id serial constraint ers_reimbursement_pk primary key,
	reimb_amount int,
	reimb_submitted timestamp,
	reimb_resolved timestamp,
	reimb_description varchar(250),
	reimb_receipt bytea,
	
	reimb_author int,
	constraint ers_users_fk_auth foreign key (reimb_author) references ers_users,
	reimb_resolver int,
	constraint ers_users_fk_reslvr foreign key (reimb_resolver) references ers_users,
	reimb_status_id int,
	constraint ers_reimbursement_status_fk foreign key (reimb_status_id) references ers_reimbursement_status,
	reimb_type_id int,
	constraint ers_reimbursement_type_fk foreign key (reimb_type_id) references ers_reimbursement_type
);

insert into ers_users (ers_username, ers_password, user_first_name, user_last_name, user_email, user_role_type) 
	VALUES('smfreitas','password', 'Samantha', 'Freitas', 'smfreitas1944@gmail.com', 'Manager');
insert into ers_user_roles (user_role)
    values('Manager');
    
   insert into ers_reimbursement_status(reimb_status) values('Approved'), ('Denied'), ('Pending');
   
-- select data from the tables

select * from ers_users;
select * from ers_user_roles;
select * from ers_reimbursement;
select * from ers_reimbursement_status;
select * from ers_reimbursement_type;