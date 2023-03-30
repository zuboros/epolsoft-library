FROM postgres:14

VOLUME [ "/data/postgres-librarydb" ]

COPY ./init-db-sql-scripts/*.sql /docker-entrypoint-initdb.d/
