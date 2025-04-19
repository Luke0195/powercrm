CREATE TABLE tb_vehicles(
  id UUID NOT NULL PRIMARY KEY,
  plate VARCHAR(10) NOT NULL UNIQUE,
  advertised_plate DECIMAL(10, 2) NOT NULL,
  vehicle_year SMALLINT NOT NULL,
  user_id UUID,
  created_at TIMESTAMP WITHOUT TIME ZONE,
  foreign key (user_id) references tb_users(id) ON UPDATE CASCADE ON DELETE SET NULL

);

