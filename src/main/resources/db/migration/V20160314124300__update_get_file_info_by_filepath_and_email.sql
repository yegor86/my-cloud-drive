drop function if exists public.get_file_info_by_filepath_and_email(varchar(255), text);

create or replace function public.get_file_info_by_filepath_and_email(email_to_share_with varchar(255), given_path text) 
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
        parent_file_id bigint,
        user_id bigint,
        user_email varchar(255),
        user_name varchar(255),
        last_name varchar(255)
    ) as $$
declare
    email_which_shares varchar(255);
begin
    for email_which_shares in
        select distinct
            u.user_email
        from 
            public.groups g
            join public.users u on u.user_id = g.owner_id
        where 
            u.user_email != email_to_share_with
            and g.group_id in (
                select 
                    acl.group_id
                from 
                    public.acl acl
                    join public.users u2 on u2.user_id = acl.user_id
                where 
                    u2.user_email = email_to_share_with
            )
    loop       
        if exists(select 1 from information_schema.schemata where schema_name = email_which_shares) then
            return query
               execute 'select f.file_id, f.file_uid, f.file_name, f.file_path, f.file_size, f.create_date, f.update_date, f.is_folder, f.extension, f.group_id, f.parent_file_id, u.user_id, u.user_email, u.user_name, u.last_name from ' 
                || quote_ident(email_which_shares) || '.files f join public.users u on u.user_id = f.owner_id where f.file_path != ''/'' and f.file_path = ' || quote_literal(given_path);
        end if; 
    end loop;
    
    if exists(select 1 from information_schema.schemata where schema_name = email_to_share_with) then
        return query
           execute 'select f.file_id, f.file_uid, f.file_name, f.file_path, f.file_size, f.create_date, f.update_date, f.is_folder, f.extension, f.group_id, f.parent_file_id, u.user_id, u.user_email, u.user_name, u.last_name from ' 
                  || quote_ident(email_to_share_with) || '.files f join public.users u on u.user_id = f.owner_id where f.file_path = ' || quote_literal(given_path);
        
    else
        return query
           execute 'select f.file_id, f.file_uid, f.file_name, f.file_path, f.file_size, f.create_date, f.update_date, f.is_folder, f.extension, f.group_id, f.parent_file_id, u.user_id, u.user_uid, u.user_name, u.last_name'
                        || ' from files f join public.users u on u.user_id = f.owner_id where u.user_email = ' || quote_literal(email_to_share_with) || ' and f.file_path = ' || quote_literal(given_path);
    end if;
    
end;
$$ language plpgsql;