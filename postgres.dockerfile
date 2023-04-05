FROM postgres:14

COPY ./init-db-sql-scripts/*.sql /docker-entrypoint-initdb.d/
