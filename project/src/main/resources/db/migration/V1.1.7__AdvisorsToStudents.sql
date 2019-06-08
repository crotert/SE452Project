Create table AdvisorToStudents(
    employee_id int not null,
    student_id int not null primary key,
   foreign key (student_id) references STUDENTS(student_id));
    
insert into AdvisorToStudents (employee_id,student_id)
Values(890890,321321);

insert into AdvisorToStudents (employee_id,student_id)
Values(890890,123123);
