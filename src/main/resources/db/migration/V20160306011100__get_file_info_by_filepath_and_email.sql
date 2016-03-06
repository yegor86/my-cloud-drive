create or replace function public.get_file_info_by_filepath_and_email(user_email varchar(255), given_path text) 
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
        user_id bigint,
        user_uid varchar(255),
        user_name varchar(255),
        last_name varchar(255)
    ) as $$
declare
begin
    if exists(select 1 from information_schema.schemata where schema_name = user_email) then
        return query
           execute 'select f.file_id, f.file_uid, f.file_name, f.file_path, f.file_size, f.create_date, f.update_date, f.is_folder, f.extension, f.group_id, u.user_id, u.user_uid, u.user_name, u.last_name from ' 
                  || quote_ident(user_email) || '.files f join public.users u on u.user_id = f.owner_id where f.file_path = ' || quote_literal(given_path);
        
    else
        return query
           execute 'select f.file_id, f.file_uid, f.file_name, f.file_path, f.file_size, f.create_date, f.update_date, f.is_folder, f.extension, f.group_id, u.user_id, u.user_uid, u.user_name, u.last_name'
                        || ' from files f join public.users u on u.user_id = f.owner_id where u.user_email = ' || quote_literal(user_email) || ' and f.file_path = ' || quote_literal(given_path);
    end if;
    
end;
$$ language plpgsql;