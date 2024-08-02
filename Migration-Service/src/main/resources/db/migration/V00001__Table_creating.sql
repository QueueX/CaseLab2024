CREATE TABLE IF NOT EXISTS "file_storage"(
    "id" bigint GENERATED ALWAYS AS IDENTITY NOT NULL UNIQUE,
    "title" varchar(255) NOT NULL UNIQUE,
    "creation_date" timestamp NOT NULL,
    "description" text NOT NULL,
    "file" bytea NOT NULL,
    PRIMARY KEY ("id")
);