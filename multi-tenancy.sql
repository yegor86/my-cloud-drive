-- 
SELECT nspname FROM pg_namespace WHERE nspname !~ '^pg_.*' and nspname != 'information_schema';
SELECT count(*) FROM pg_namespace WHERE nspname = 'yfadeev';

-- Fetch all own files and folders
select * from files;
union all
select * 
from 
    groups g
join 
        files_user_rights fur on fur.group_id = g.group_id
where
    g.owner_id = 1
    and fyr.permissions >= READ_ONLY



