### Cadastrar Veículos

> #### Dados de Entrada:
* **plate** (required, unique) – Placa do veículo (exemplo: "ABC-1234")
* **advertised_price** (required) – Preço do veículo (exemplo: 35000.00)
* **year** (required) – Ano de fabricação (exemplo: 2018)
* **user_id** (required) – ID do usuário que está cadastrando o veículo (exemplo: 123)
* **createdAt** – Data de criação (formato ISO 8601, ex: "2025-04-20T12:00:00Z")

> #### Casos de Sucesso:
* [ ] Recebe uma requisição POST na rota **/api/vehicle**.
* [ ] Valida que os campos obrigatórios (**plate**, **advertised_price**, **year**, **user_id**) estão presentes.
* [ ] Impede que o veículo seja cadastrado com a mesma **placa**.
* [ ] Impede que um veículo seja cadastrado com um **user_id** inválido.
* [ ] Envia os uma mensagem para fila de validação de dados quando os mesmo estiverem corretos.
* [ ] Retorna uma mensagem de sucesso ao usuário, informando o mesmo que os dados do veículo informado estão sendo válidados.

> #### Exceções:
* [ ] Retorna erro **404 Not Found** se o endpoint **/api/vehicle** não for encontrado, indicando que a URL está incorreta ou foi removida.
* [ ] Retorna erro **400 Bad Request** se o campo **plate** não for informado.
* [ ] Retorna erro **400 Bad Request** se o campo **advertised_price** não for informado.
* [ ] Retorna erro **400 Bad Request** se o campo **year** não for informado.
* [ ] Retorna erro **400 Bad Request** se o campo **user_id** não for informado.
* [ ] Retorna erro **400 Bad Request** se o **plate** não estiver no formato correto.
* [ ] Retorna erro **400 Bad Request** se o **user_id** não for válido (exemplo: não existir no banco de dados).

---


### Listar Veículos

> ### Casos de Sucesso
* [X] Recebe uma requisição do tipo **GET** na rota **/vehicles**.
* [X] Retorna a lista de veículos cadastrados.
* [X] Retorna **200 OK** com a lista de veículos.

> ### Exceções
* [X] Retorna **404 Not Found** se o endpoint **/vehicles** não for encontrado.
* [X] Retorna **500 Internal Server Error** caso ocorra um erro ao carregar os vehicles.

---

### Atualizar Veículo

> ### Casos de Sucesso
* [X] Recebe uma requisição do tipo **PUT** na rota **/vehicles/{id}**.
* [X] Valida se o **id** que está sendo passado na rota é válido.
* [X] Valida se o **id** informado existe.
* [X] Atualiza os dados do veículo caso o **id** seja válido.
* [X] Retorna **200 OK** com os dados atualizados.

> ### Exceções
* [X] Retorna **404 Not Found** se o endpoint **/vehicles/{id}** não for encontrado.
* [X] Retorna **404 Not Found** caso o **id** do veículo não for encontrado.
* [X] Retorna **500 Internal Server Error** caso ocorra um erro ao atualizar o veículo.

---

### Deletar Veículos

> ### Casos de Sucesso
* [X] Recebe uma requisição do tipo **DELETE** na rota **/vehicles/{id}**.
* [X] Valida se o **id** que está sendo passado na rota é válido.
* [X] Valida se o **id** informado existe.
* [X] Deleta a informação do usuário.
* [X] Retorna **204 No Content** em caso de sucesso.

> ### Exceções
* [X] Retorna **404 Not Found** se o endpoint **/vehicles/{id}** não for encontrado.
* [X] Retorna **404 Not Found** caso o **id** do veículo não for encontrado.
* [X] Retorna **500 Internal Server Error** caso ocorra um erro ao deletar o veiculo.

---