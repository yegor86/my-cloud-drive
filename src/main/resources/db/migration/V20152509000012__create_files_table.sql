--------------------------------------------------------
--  DDL for sequence of table files
--------------------------------------------------------
create sequence "file_id_seq";

-------------------------------------------------------
--  DDL for table files
--------------------------------------------------------
create table "files"
(   "file_id" bigint not null default nextval('file_id_seq'),
    "file_name" varchar(255),
    "file_path" varchar(255),
    "file_size" bigint,
    "owner_id"  bigint not null,
    CONSTRAINT files_pk PRIMARY KEY (file_id),
    CONSTRAINT fk_file_user FOREIGN KEY (owner_id)
      REFERENCES users (user_id)
);