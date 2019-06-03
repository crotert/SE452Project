create table COURSES (
	id int primary key auto_increment,
	subject varchar(50) not null,
	course_number int,
	course_description varchar(150) not null,
	course_credits double, 
	prereq_course_id int,
	concurrent_course_id int, 
	screener_id long
	);
	
insert into courses (subject, course_number, course_description, course_credits, prereq_course_id, concurrent_course_id, screener_id)
values ('Software Engineering', 452, 'Enterprise Computing', 4.0, -1, 2, 1);

insert into courses (subject, course_number, course_description, course_credits, prereq_course_id, concurrent_course_id, screener_id)
values ('Computer Science', 480, 'Machine Learning Programming', 4.0, 0, 1, 2);
