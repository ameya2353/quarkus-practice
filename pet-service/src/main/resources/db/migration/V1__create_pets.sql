CREATE TABLE pets (
    id SERIAL PRIMARY KEY,
    owner_id VARCHAR(50) NOT NULL,
    pet_type VARCHAR(50) NOT NULL,
    pet_breed VARCHAR(50) NOT NULL
);
