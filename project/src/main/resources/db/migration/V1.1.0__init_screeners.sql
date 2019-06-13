create table screeners (
    id identity not null primary key,
    description varchar(200),
    published boolean
);

create table questions (
	id identity not null primary key,
	screener_id long not null,
	prompt varchar(200) not null,
	answer_type varchar(20) not null,
	
	foreign key (screener_id) references screeners(id)
);
