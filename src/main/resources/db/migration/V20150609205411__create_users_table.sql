--------------------------------------------------------
--  DDL for sequence of table users
--------------------------------------------------------
create sequence "user_id_seq";

-------------------------------------------------------
--  DDL for table users
--------------------------------------------------------
create table "users" 
(   "user_id" bigint not null default nextval('user_id_seq'),
    "user_uid" varchar(255),
    "user_name" varchar(255),
    "user_email" varchar(255)
);