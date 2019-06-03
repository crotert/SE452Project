create table DeptChairs(
  EmployeeID identity not null primary key FOREIGN KEY REFERENCES PROFESSORS(employee_id), 
  Dept Varchar(50) not null);
  
Insert into DeptChairs(EmployeeID, Dept)
Values(890890,'Computer Science');

Insert into DeptChairs(EmployeeID, Dept)
Values(098098,'Design');
