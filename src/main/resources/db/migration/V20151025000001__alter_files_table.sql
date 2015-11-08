ALTER TABLE files ADD COLUMN "create_date" timestamp;

ALTER TABLE files ADD COLUMN "update_date" timestamp;

ALTER TABLE files ADD COLUMN "is_folder" BOOLEAN DEFAULT FALSE;

ALTER TABLE files ALTER COLUMN "file_path" TYPE varchar(1000);