insert into screeners (description, published)
values ('Please answer the following questions honestly. Your answers will help tailor class lectures.',
		false
);

insert into screeners (description, published)
values ('Tell me about yourself and what you hope to get out of this class.',
		false
);

insert into questions (screener_id, prompt, answer_type)
values (
	1,
	'What topic from the class syllabus are you most interested in learning about?',
	'STRING'
);

insert into questions (screener_id, prompt, answer_type)
values (
	1,
	'What topic from the class syllabus are you least interested in learning about?',
	'STRING'
);

insert into questions (screener_id, prompt, answer_type)
values (
	1,
	'How many years of experience do you have with software development (school included)?',
	'NUMBER'
);

insert into questions (screener_id, prompt, answer_type)
values (
	1,
	'How comfortable are you with Java programming?',
	'SINGLESELECT'
);

insert into questions (screener_id, prompt, answer_type)
values (
	1,
	'How comfortable are you with SQL Databases?',
	'SINGLESELECT'
);

insert into questions (screener_id, prompt, answer_type)
values (
	1,
	'How comfortable are you with Mongo Databases?',
	'SINGLESELECT'
);

insert into questions (screener_id, prompt, answer_type)
values (
	1,
	'With which of the following technologies do you have experience (anything counts)? Choose all that apply.',
	'MULTISELECT'
);

insert into questions (screener_id, prompt, answer_type)
values (
	2,
	'first test question for second screener',
	'STRING'
);

insert into questions (screener_id, prompt, answer_type)
values (
	2,
	'second test question for second screener',
	'NUMBER'
);

alter table courses add foreign key (screener_id) references screeners(id);
