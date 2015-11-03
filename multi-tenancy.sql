-- 
SELECT nspname FROM pg_namespace WHERE nspname !~ '^pg_.*' and nspname != 'information_schema';
SELECT count(*) FROM pg_namespace WHERE nspname = 'yfadeev';