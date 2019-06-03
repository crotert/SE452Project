Create table AdvisorToStudents(
    EmployeeID int not null Foreign Key references PROFESSORS(employee_id),
    StudentID int not null Foreign Key references STUDENTS(student_id) Primary Key);
    
insert into AdvisorToStudents (EmployeeID,StudentID)
Values(890890,321321);

insert into AdvisorToStudents (EmployeeID,StudentID)
Values(890890,123123);
