alter table files add column "permissions" int;
update files set permissions = 600;