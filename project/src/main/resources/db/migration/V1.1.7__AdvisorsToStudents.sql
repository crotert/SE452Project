Create table Advisors_To_Students(
    employeeid int not null,
    studentid int not null primary key);
   --foreign key (student_id) references STUDENTS(student_id));
    
insert into Advisors_To_Students (employeeid,studentid)
Values(890890,321321);

insert into Advisors_To_Students (employeeid,studentid)
Values(890890,123123);
