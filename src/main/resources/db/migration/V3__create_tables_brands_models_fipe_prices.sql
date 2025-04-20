CREATE TABLE tb_brands(
  id SMALLINT NOT NULL PRIMARY KEY,
  name VARCHAR(45) NOT NULL
);

CREATE TABLE tb_models(
    id SMALLINT NOT NULL PRIMARY KEY,
    name VARCHAR(45) NOT NULL
);

CREATE TABLE tb_fipe_prices(
    id SMALLINT NOT NULL PRIMARY KEY ,
    brand_id SMALLINT NOT NULL,
    model_id SMALLINT NOT NULL,
    "year" int NOT NULL,
    fipe_price NUMERIC NOT NULL,
    FOREIGN KEY (brand_id) REFERENCES  tb_brands(id) ON UPDATE CASCADE ON DELETE SET NULL,
    FOREIGN KEY (model_id) REFERENCES  tb_models(id) ON UPDATE CASCADE ON DELETE SET NULL
);

ALTER TABLE tb_vehicles ADD COLUMN  brand_id SMALLINT;
ALTER TABLE tb_vehicles ADD COLUMN  model_id SMALLINT;
ALTER TABLE tb_vehicles ADD COLUMN fipe_price NUMERIC;
ALTER TABLE tb_vehicles ADD CONSTRAINT fk_brand_id FOREIGN KEY(brand_id) REFERENCES tb_brands(id);
ALTER TABLE tb_vehicles ADD CONSTRAINT fk_model_id FOREIGN KEY (model_id) REFERENCES tb_models(id);
