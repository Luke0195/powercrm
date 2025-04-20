> ### Cadastrar Veículos

> ##### Dados de Entrada:

 * <b>plate</b> (required, unique) – Placa do veículo (exemplo: "ABC-1234")

 * <b>advertised_price</b> (required) – Preço do veículo (exemplo: 35000.00)

 * <b>year</b> (required) – Ano de fabricação (exemplo: 2018)

 * <b>user_id</b> (required) – ID do usuário que está cadastrando o veículo (exemplo: 123)

 * <b>createdAt</b> – Data de criação (formato ISO 8601, ex: "2025-04-20T12:00:00Z")

> ##### Casos de sucesso:

* [ ] Recebe uma requisição POST na rota /api/vehicle.
* [ ] Valida que os campos obrigatórios (plate, advertised_price, year, user_id) estão presentes.
* [ ] Impede que o veículo seja cadastrado com a mesma placa.
* [ ] Impede que um veículo seja cadastrado com um user_id inválido.
* [ ] Adiciona um veículo quando todos os dados são válidos.
* [ ] Retorna status 201 Created com os dados do veículo cadastrado em caso de sucesso.

> #### Exceções:

* [ ] Retorna erro 404 Not Found se o endpoint /api/vehicle não existir.
* [ ] Retorna erro 400 Bad Request se o campo plate não for informado.
* [ ] Retorna erro 400 Bad Request se o campo advertised_price não for informado.
* [ ] Retorna erro 400 Bad Request se o campo year não for informado.
* [ ] Retorna erro 400 Bad Request se o campo user_id não for informado.

Retorna erro 400 Bad Request se o plate não estiver no formato correto.

Retorna erro 400 Bad Request se o user_id não for válido (exemplo: não existir no banco de dados).

Retorna erro 409 Conflict se o plate do veículo já estiver registrado.