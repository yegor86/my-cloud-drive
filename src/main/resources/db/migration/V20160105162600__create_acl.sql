--------------------------------------------------------
--  DDL for sequence of table files_user_rights
--------------------------------------------------------
create sequence "acl_id_seq";

-------------------------------------------------------
--  DDL for table acl
--------------------------------------------------------
create table "acl"
(   "id" bigint not null default nextval('acl_id_seq'),
    "user_id" bigint not null,
    "group_id"  bigint not null,
    "permissions" int not null,
    constraint acl_pk primary key (id),
    constraint fk_acl_user foreign key (user_id)
      references users (user_id),
    constraint fk_acl_group foreign key (group_id)
      references groups (group_id),
    constraint u_user_group unique (user_id, group_id)
);