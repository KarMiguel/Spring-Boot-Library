CREATE TABLE reset_password (
    id SERIAL PRIMARY KEY,
    id_cliente BIGINT REFERENCES users(id),
    status VARCHAR(25),
    code VARCHAR(25),
    new_password VARCHAR(255),
    date_created TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);
