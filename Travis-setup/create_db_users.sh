#!/bin/sh -e

# Edit the following to change the name of the database user that will be created:
APP_DB_USER=mydrive
APP_DB_PASS=mydrive

# Edit the following to change the name of the database that is created (defaults to the user name)
APP_DB_NAME=mydrive

# Edit the following to change the version of PostgreSQL that is installed
PG_VERSION=9.4

cat << EOF | su - postgres -c psql
-- Create the database user:
CREATE USER $APP_DB_USER WITH PASSWORD '$APP_DB_PASS';

-- Create the database:
CREATE DATABASE $APP_DB_NAME WITH OWNER=$APP_DB_USER
                                  LC_COLLATE='en_US.utf8'
                                  LC_CTYPE='en_US.utf8'
                                  ENCODING='UTF8'
                                  TEMPLATE=template0;
EOF
