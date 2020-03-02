-- create tables
create table employee (
	employee_id serial constraint pk_employee_id primary key,
	email varchar unique not null,
	employee_type varchar not null,
	first_name varchar not null,
	last_name varchar not null,
	pass varchar not null
);

create table reimbursement (
	reimbursement_id serial constraint pk_reimbursement_id primary key,
	reimbursement_type varchar not null,
	reimbursement_time timestamp not null,
	reimbursement_amount numeric not null,
	reimbursement_description varchar not null,
	receipt_name varchar,
	receipt_path varchar,
	reimbursement_status varchar not null,
	requester_id integer references employee(employee_id),
	processor_id integer references employee(employee_id)
);

--function
create or replace function updateReimbursement(manager_id employee.employee_id%type, reimbursement_id_input reimbursement.reimbursement_id%type, operation reimbursement.reimbursement_status%type)
returns varchar
language plpgsql
as $$
declare
	result varchar;
begin 
	update reimbursement set reimbursement_status = operation, processor_id = manager_id where reimbursement_id = reimbursement_id_input;
	select requester.email into result
	from reimbursement join employee as requester on reimbursement.requester_id = requester.employee_id where reimbursement_id = reimbursement_id_input;
	return result;
end
$$

--testing
insert into employee (email, employee_type, first_name, last_name, pass) values ('alan@l.com', 'associate', 'alan', 'li', 'password');
insert into employee (email, employee_type, first_name, last_name, pass) values ('dean@h.com', 'manager', 'dean', 'huang', 'password');
insert into reimbursement (reimbursement_type, reimbursement_time, reimbursement_description, receipt_path, reimbursement_status, requester_id, processor_id) values ('certification', '2020-02-01 00:00:00', 'test', '/test', 'pending', 1, 2);
select * from reimbursement inner join employee as requester on reimbursement.requester_id = requester.employee_id inner join employee as processor on reimbursement.processor_id = processor.employee_id where requester.employee_id = 1;

--reset testing
truncate table employee, reimbursement;
drop table reimbursement;
drop table employee;

--dev
delete from employee where employee_id != 3;
truncate table reimbursement;
delete from employee where employee_id != 3 and employee_id != 5;
select * from reimbursement inner join employee as requester on reimbursement.requester_id = requester.employee_id full join employee as processor on reimbursement.processor_id = processor.employee_id where reimbursement_status = 'pending' and requester.first_name = 'tianyu' and requester.last_name = 'li' order by reimbursement_id desc;
select * from reimbursement inner join employee as requester on reimbursement.requester_id = requester.employee_id full join employee as processor on reimbursement.processor_id = processor.employee_id where requester.employee_id = 5 and reimbursement_status = 'pending';
select updateReimbursement(3, 27, 'approved');





