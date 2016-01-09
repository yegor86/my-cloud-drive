create or replace function select_all_files(userId integer) 
    returns table (file_name varchar(255)) as $$
declare
    user_email varchar(255);
begin
	
    for user_email in
	    select 
            u.user_email
        from 
            "public".users_groups ug
            join "public".users u on u.user_id = ug.user_id
        where 
            ug.user_id != userId 
            and ug.group_id in (
              select 
                   group_id
              from 
                   "public".users_groups
              where 
                   user_id = userId
            ) 
        
        union all
        
        select 'yegor2@mail.com'
        union all
        select 'yegor_mail.com'
	loop	   
	   return query 
	       execute 'select file_name from ' || quote_ident(user_email) || '.files where (permissions & 040) > 0';
	end loop;
    
end;
$$ language plpgsql;