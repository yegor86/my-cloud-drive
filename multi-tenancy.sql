-- 
SELECT nspname FROM pg_namespace WHERE nspname !~ '^pg_.*'