create or replace function select_all_files(userId bigint) 
    returns table (file_name varchar(255)) as $$
declare
    user_email varchar(255);
    is_present boolean;
begin
    
    for user_email in
        select 
            u.user_email
        from 
            public.groups g
            join public.users u on u.user_id = g.owner_id
        where 
            g.group_id in (
	            select 
	                group_id
	            from 
	                public.acl
	            where 
	                user_id = userId
            )
        
        union all
        
        select u.user_email from public.users u where u.user_id = userId
    loop       
        select TRUE into is_present from information_schema.schemata where schema_name = quote_ident(user_email);
        if is_present then
	        return query
	           execute 'select file_name from ' || quote_ident(user_email) || '.files f join public.acl on acl.group_id = f.group_id and acl.user_id = ' || userId;
	    end if; 
    end loop;
    
end;
$$ language plpgsql;