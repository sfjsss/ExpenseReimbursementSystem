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
	reimbursement_amount numeric,
	reimbursement_description varchar,
	receipt_name varchar,
	receipt_path varchar,
	reimbursement_status varchar,
	requester_id integer references employee(employee_id),
	processor_id integer references employee(employee_id)
);

--testing
insert into employee (email, employee_type, first_name, last_name, pass) values ('alan@l.com', 'associate', 'alan', 'li', 'password');
insert into employee (email, employee_type, first_name, last_name, pass) values ('dean@h.com', 'manager', 'dean', 'huang', 'password');
insert into reimbursement (reimbursement_type, reimbursement_time, reimbursement_description, receipt_path, reimbursement_status, requester_id, processor_id) values ('certification', '2020-02-01 00:00:00', 'test', '/test', 'pending', 1, 2);
select * from reimbursement inner join employee as requester on reimbursement.requester_id = requester.employee_id inner join employee as processor on reimbursement.processor_id = processor.employee_id where requester.employee_id = 1;

--reset testing
truncate table employee, reimbursement;
drop table reimbursement;

--dev
delete from employee where employee_id != 3;





