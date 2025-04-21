CREATE TABLE tb_brands(
  id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
  name VARCHAR(45) NOT NULL,
  brand_code VARCHAR(45) NOT NULL
);

CREATE TABLE tb_models(
    id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    name VARCHAR(45) NOT NULL,
    model_code VARCHAR(45) NOT NULL
);


ALTER TABLE tb_vehicles ADD COLUMN  brand_id SMALLINT;
ALTER TABLE tb_vehicles ADD COLUMN  model_id SMALLINT;
ALTER TABLE tb_vehicles ADD COLUMN fipe_price NUMERIC;
ALTER TABLE tb_vehicles ADD CONSTRAINT fk_brand_id FOREIGN KEY(brand_id) REFERENCES tb_brands(id);
ALTER TABLE tb_vehicles ADD CONSTRAINT fk_model_id FOREIGN KEY (model_id) REFERENCES tb_models(id);
