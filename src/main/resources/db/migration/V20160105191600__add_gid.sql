alter table users add column "group_id" bigint not null default -1;

-- Add group for admin
insert into groups (owner_id, group_name) values
    ((select user_id from users where user_email = 'admin@mail.com'),
     'admin@mail.com'
    );
    
update users set group_id = (select group_id from groups where group_name = 'admin@mail.com')
where user_id = (select user_id from users where user_email = 'admin@mail.com');