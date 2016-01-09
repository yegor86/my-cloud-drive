create or replace function select_all_files(userId integer) 
    returns table (file_name varchar(255)) as $$
declare
    user_email varchar(255);
begin
	
    for user_email in
	    select 
            u.user_email
        from 
            public.acl
            join public.users u on u.user_id = acl.user_id
        where 
            acl.user_id != userId 
            and acl.group_id in (
              select 
                   group_id
              from 
                   public.acl
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