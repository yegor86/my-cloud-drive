ALTER TABLE files DROP COLUMN file_path;
ALTER TABLE files ADD COLUMN parent_file_id bigint REFERENCES files(file_id);

