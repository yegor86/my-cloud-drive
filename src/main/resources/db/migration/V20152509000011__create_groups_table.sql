--------------------------------------------------------
--  DDL for sequence of table groups
--------------------------------------------------------
create sequence "group_id_seq";

-------------------------------------------------------
--  DDL for table groups
--------------------------------------------------------
create table "groups"
(   "group_id" bigint not null default nextval('group_id_seq'),
    "description" varchar(255),
    "owner_id"  bigint not null,
    CONSTRAINT groups_pk PRIMARY KEY (group_id),
    CONSTRAINT fk_group_user FOREIGN KEY (owner_id)
      REFERENCES users (user_id)
);