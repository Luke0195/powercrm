CREATE TABLE tb_users(
   id UUID NOT NULL PRIMARY KEY,
   name VARCHAR(255) NOT NULL,
   email VARCHAR(255) NOT NULL UNIQUE,
   phone VARCHAR(45),
   cpf VARCHAR(14) NOT NULL UNIQUE,
   zipcode VARCHAR(9),
   address VARCHAR(255),
   number SMALLINT,
   complement VARCHAR(20),
   status SMALLINT,
   created_at TIMESTAMP WITHOUT TIME ZONE
);

