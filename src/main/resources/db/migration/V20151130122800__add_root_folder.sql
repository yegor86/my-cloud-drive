insert into files (file_name, file_size, owner_id, create_date, update_date, is_folder, parent_file_id, file_path) values
    ('/', 
     '0', 
     (select user_id from users where user_email = 'admin@mail.com'),
     current_timestamp,
     current_timestamp,
     TRUE,
     NULL,
     '/'
    );