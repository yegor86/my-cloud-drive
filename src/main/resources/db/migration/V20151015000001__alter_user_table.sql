ALTER TABLE users ADD CONSTRAINT user_email_constraint UNIQUE (user_email);

ALTER TABLE users ADD COLUMN "create_date" timestamp;

ALTER TABLE users ADD COLUMN "update_date" timestamp;

ALTER TABLE users ADD COLUMN "last_name" varchar(255);