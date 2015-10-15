--------------------------------------------------------
--  DDL for sequence of table files_user_rights
--------------------------------------------------------
create sequence "file_ur_id_seq";

-------------------------------------------------------
--  DDL for table files_user_rights
--------------------------------------------------------
create table "files_user_rights"
(   "id" bigint not null default nextval('file_ur_id_seq'),
    "permission"  bigint not null,
    "owner_id"  bigint not null,
    "group_id"  bigint not null,
    "file_id"  bigint not null,
    CONSTRAINT files_user_rights_pk PRIMARY KEY (id),
    CONSTRAINT fk_file_ur_user FOREIGN KEY (owner_id)
      REFERENCES users (user_id),
    CONSTRAINT fk_file_ur_group FOREIGN KEY (group_id)
      REFERENCES groups (group_id),
    CONSTRAINT fk_file_ur_file FOREIGN KEY (file_id)
      REFERENCES files (file_id)
);