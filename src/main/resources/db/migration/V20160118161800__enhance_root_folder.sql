insert into files (file_name, file_size, owner_id, create_date, update_date, is_folder, parent_file_id, file_path, group_id, file_uid)
    select
        '/', 
        '0', 
        coalesce(
                        (select user_id from public.users where user_email = current_schema()), 
                        (select user_id from public.users where user_email = 'admin@mail.com')
                   ),
        current_timestamp,
        current_timestamp,
        TRUE,
        NULL,
        '/',
        (select group_id from public.groups where group_name = current_schema()),
        'root'
    where
        not exists (
            select file_id from files where file_name = '/'
        );

update files 
    set owner_id = coalesce(
                        (select user_id from public.users where user_email = current_schema()), 
                        (select user_id from public.users where user_email = 'admin@mail.com')
                   )
where 
    file_id = (select file_id from files where file_name = '/');
        
update files 
    set group_id = (select group_id from public.groups where group_name = current_schema())
where 
    file_id = (select file_id from files where file_name = '/');
