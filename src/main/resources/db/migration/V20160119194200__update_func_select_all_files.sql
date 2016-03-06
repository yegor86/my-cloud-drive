drop function if exists public.select_all_files(integer);
drop function if exists public.select_all_files(bigint);
drop function if exists public.select_all_files(bigint, bigint);

create or replace function public.select_all_files(userId bigint, parentFileId bigint) 
    returns table (
        file_id bigint,
        file_uid varchar(255),
        file_name varchar(255),
        file_path varchar(1000),
        file_size bigint,
        create_date timestamp,
        update_date timestamp,
        is_folder boolean,
        extension varchar(10),
        group_id bigint,
        user_id bigint
--        user_uid varchar(255),
--        user_name varchar(255),
--        last_name varchar(255)
    ) as $$
declare
    user_email varchar(255);
begin
    
	for user_email in
        select distinct
            u.user_email
        from 
            public.groups g
            join public.users u on u.user_id = g.owner_id
        where 
            g.group_id in (
                select 
                    acl.group_id
                from 
                    public.acl acl
                where 
                    acl.user_id = userId
            )
    loop       
        if exists(select 1 from information_schema.schemata where schema_name = user_email) then
            return query
               execute 'select f.file_id, f.file_uid, f.file_name, f.file_path, f.file_size, f.create_date, f.update_date, f.is_folder, f.extension, f.group_id, f.owner_id as user_id from ' 
                || quote_ident(user_email) || '.files f join public.acl on acl.group_id = f.group_id and acl.user_id = ' || userId;
        end if; 
    end loop;
    
    select u.user_email into user_email from public.users u where u.user_id = userId;
    if exists(select 1 from information_schema.schemata where schema_name = user_email) then
        return query
           execute 'select f.file_id, f.file_uid, f.file_name, f.file_path, f.file_size, f.create_date, f.update_date, f.is_folder, f.extension, f.group_id, f.owner_id as user_id from ' 
                  || quote_ident(user_email) || '.files f join public.users u on u.user_id = f.owner_id where f.parent_file_id = ' || parentFileId;
        
    else
        return query
           execute 'select f.file_id, f.file_uid, f.file_name, f.file_path, f.file_size, f.create_date, f.update_date, f.is_folder, f.extension, f.group_id, f.owner_id as user_id from ' 
                  'public.files f join public.users u on u.user_id = f.owner_id where f.parent_file_id = ' || parentFileId;
    end if;
    
end;
$$ language plpgsql;