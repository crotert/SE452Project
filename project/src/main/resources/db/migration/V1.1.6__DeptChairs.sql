create table DeptChairs(
  EmployeeID identity not null primary key, 
  Dept Varchar(50) not null,
  foreign key (EmployeeID) references PROFESSORS(employee_id));
  
Insert into DeptChairs(EmployeeID, Dept)
Values(890890,'Computer Science');

Insert into DeptChairs(EmployeeID, Dept)
Values(098098,'Design');
