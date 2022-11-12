CREATE TABLE IF NOT EXISTS users
(
    id          SERIAL PRIMARY KEY,
    login       TEXT UNIQUE NOT NULL,
    password    TEXT NOT NULL,
    role        INT NOT NULL,
    question    TEXT,
    answer      TEXT
);

INSERT INTO users (login, role, password,question,answer)
VALUES ('Empty', 3,
        '$2a$10$RnvJ13zcdIzxzgqrz0nB8.m8zuY9h8VhaEkRiqc478ws79oq9vGQG',
        0,0);