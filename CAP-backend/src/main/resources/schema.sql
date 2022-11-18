CREATE TABLE IF NOT EXISTS users
(
    id          SERIAL PRIMARY KEY,
    login       TEXT UNIQUE NOT NULL,
    password    TEXT NOT NULL,
    role        INT NOT NULL,
    question    TEXT,
    answer      TEXT
);

CREATE TABLE IF NOT EXISTS log_in_date
(
    id          SERIAL PRIMARY KEY,
    user_id     TEXT UNIQUE REFERENCES users (login) ON DELETE CASCADE NOT NULL,
    date        TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS categories
(
    id          SERIAL PRIMARY KEY,
    name        TEXT UNIQUE NOT NULL,
    type        INT NOT NULL,
    range       INT NOT NULL,
    user_id     INT UNIQUE REFERENCES users (id) ON DELETE CASCADE NOT NULL
);

INSERT INTO users (login, role, password,question,answer)
VALUES ('Empty', 3,
        '$2a$10$RnvJ13zcdIzxzgqrz0nB8.m8zuY9h8VhaEkRiqc478ws79oq9vGQG',
        0,0);