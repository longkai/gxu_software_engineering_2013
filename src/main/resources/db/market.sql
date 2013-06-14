-- REMEMBER: auto_increment(like mysql) => id bigint generated by default as identity (start with 1)
CREATE TABLE users (
	id bigint generated by default as identity (start with 1),
	real_name varchar(4) NOT NULL,
	nick varchar(7) NOT NULL,
	account varchar(18) NOT NULL,
	password varchar(32) NOT NULL,
	register_time timestamp,
	last_login_time timestamp,
	login_times integer DEFAULT 0,
	blocked boolean DEFAULT FALSE,
	CONSTRAINT user_pk PRIMARY KEY (id)
);
