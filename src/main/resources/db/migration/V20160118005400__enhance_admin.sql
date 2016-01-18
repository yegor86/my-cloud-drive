alter table files drop constraint if exists fk_file_user;
alter table files drop constraint if exists fk_file_owner;
alter table files add constraint fk_file_owner foreign key (owner_id)
          references users (user_id) on delete cascade;
          
alter table groups drop constraint if exists fk_group_user;
alter table groups drop constraint if exists fk_group_owner;
alter table groups add constraint fk_group_owner foreign key (owner_id)
          references users (user_id) on delete cascade;
          
-- Restore Admin user group
insert into public.groups (owner_id, group_name)
    select
        (select user_id from public.users where user_email = 'admin@mail.com'),
        'public'
    where
        not exists (
            select 1 from public.groups where group_name = 'public'
    );
          
insert into public.groups (owner_id, group_name)
    select
        (select user_id from public.users where user_email = 'admin@mail.com'),
        'admin@mail.com'
    where
        not exists (
            select 1 from public.groups where group_name = 'admin@mail.com'
    );
    
update public.users 
    set group_id = (select group_id from public.groups where group_name = 'admin@mail.com')
where 
    user_id = (select owner_id from public.groups where group_name = 'admin@mail.com');