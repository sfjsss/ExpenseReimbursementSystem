-- create tables
create table employee (
	employee_id serial constraint pk_employee_id primary key,
	email varchar,
	employee_type varchar,
	first_name varchar,
	last_name varchar,
	pass varchar
);

create table reimbursement (
	reimbursement_id serial constraint pk_reimbursement_id primary key,
	reimbursement_type varchar,
	reimbursement_time timestamp,
	reimbursement_description varchar,
	receipt_path varchar,
	reimbursement_status varchar,
	requester_id integer references employee(employee_id),
	processor_id integer references employee(employee_id)
);





