--------------------------------------------------------
--  DDL for sequence of table files_groups
--------------------------------------------------------
create sequence "users_groups_id_seq";

-------------------------------------------------------
--  DDL for table users_groups
--------------------------------------------------------
create table "users_groups"
(   "id" bigint not null default nextval('users_groups_id_seq'),
    "user_id"  bigint not null,
    "group_id"  bigint not null,
    CONSTRAINT files_user_rights_pk PRIMARY KEY (id),
    CONSTRAINT fk_files_groups_user FOREIGN KEY (user_id) REFERENCES users (user_id),
    CONSTRAINT fk_files_groups_group FOREIGN KEY (group_id) REFERENCES groups (group_id)
);