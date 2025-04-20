### Cadastrar Usuário

> ### Dados de Entrada
* **name** (required) – Nome completo do usuário (exemplo: "João Silva").
* **email** (required) – Endereço de e-mail (exemplo: "joao.silva@email.com").
* **phone** (optional) – Número de telefone (exemplo: "(11) 98765-4321").
* **cpf** (required, valid) – CPF do usuário (exemplo: "123.456.789-00").
* **zipcode** (optional) – CEP do usuário (exemplo: "12345-678").
* **address** (optional) – Endereço completo (exemplo: "Rua X").
* **number** (optional) – Número da residência (exemplo: "123").
* **complement** (optional) – Complemento do endereço (exemplo: "Apartamento 45").
* **status** – Status do usuário (exemplo: "Ativo").
* **createdAt** – Data de criação do cadastro (formato ISO 8601, ex: "2025-04-20T12:00:00Z").

> ### Casos de Sucesso
* [X] Recebe uma requisição do tipo **POST** na rota **/api/user**.
* [X] Valida que os campos obrigatórios (**name**, **email**, **cpf**) estão presentes.
* [X] Não permite que um usuário seja cadastrado com o mesmo **e-mail**.
* [X] Não permite que um usuário seja cadastrado com o **mesmo CPF**.
* [X] Adiciona um usuário quando todos os dados são válidos.
* [X] Retorna **201 Created** com os dados do usuário criado em caso de sucesso.

> ### Exceções
* [X] Retorna erro **404 Not Found** se o endpoint **/api/user** não for encontrado.
* [X] Retorna erro **400 Bad Request** se o **nome** não for informado.
* [X] Retorna erro **400 Bad Request** se o **email** não for informado.
* [X] Retorna erro **400 Bad Request** se o **email** informado for inválido.
* [X] Retorna erro **400 Bad Request** se o **cpf** não for informado.
* [X] Retorna erro **422 Unprocessable Entity** se o **email** informado já estiver sendo utilizado.
* [X] Retorna erro **422 Unprocessable Entity** se o **CPF** informado já estiver sendo utilizado.
* [X] Retorna erro **500 Internal Server Error** caso ocorra um erro ao salvar o usuário.

---

### Listar Usuários

> ### Casos de Sucesso
* [X] Recebe uma requisição do tipo **GET** na rota **/users**.
* [X] Retorna a lista de usuários cadastrados.
* [X] Retorna **200 OK** com a lista de usuários.

> ### Exceções
* [X] Retorna **404 Not Found** se o endpoint **/users** não for encontrado.
* [X] Retorna **500 Internal Server Error** caso ocorra um erro ao carregar os usuários.

---

### Atualizar Usuário

> ### Casos de Sucesso
* [X] Recebe uma requisição do tipo **PUT** na rota **/users/{id}**.
* [X] Valida se o **id** que está sendo passado na rota é válido.
* [X] Valida se o **id** informado existe.
* [X] Atualiza os dados do usuário caso o **id** seja válido.
* [X] Retorna **200 OK** com os dados atualizados.

> ### Exceções
* [X] Retorna **404 Not Found** se o endpoint **/users/{id}** não for encontrado.
* [X] Retorna **404 Not Found** caso o **id** do usuário não for encontrado.
* [X] Retorna **500 Internal Server Error** caso ocorra um erro ao atualizar o usuário.

---

### Deletar Usuário

> ### Casos de Sucesso
* [X] Recebe uma requisição do tipo **DELETE** na rota **/users/{id}**.
* [X] Valida se o **id** que está sendo passado na rota é válido.
* [X] Valida se o **id** informado existe.
* [X] Deleta a informação do usuário.
* [X] Retorna **204 No Content** em caso de sucesso.

> ### Exceções
* [X] Retorna **404 Not Found** se o endpoint **/users/{id}** não for encontrado.
* [X] Retorna **404 Not Found** caso o **id** do usuário não for encontrado.
* [X] Retorna **500 Internal Server Error** caso ocorra um erro ao deletar o usuário.

---

### Listar Usuários por Período

> ### Dados de Entrada
* **startDate** (obrigatório, formato ISO: yyyy-MM-dd) – Data de início do período (exemplo: "2024-01-01").
* **endDate** (obrigatório, formato ISO: yyyy-MM-dd) – Data de fim do período (exemplo: "2024-01-31").

> ### Casos de Sucesso
* [X] Recebe uma requisição do tipo **GET** na rota **/users?startDate=2024-01-01&endDate=2024-01-31**.
* [X] Filtra os usuários com base no campo **createdAt** entre as datas informadas.
* [X] Retorna **200 OK** com a lista dos usuários cadastrados nesse período.

> ### Exceções
* [X] Retorna **400 Bad Request** se os parâmetros **startDate** ou **endDate** não forem informados.
* [X] Retorna **400 Bad Request** se as **datas** estiverem em formato inválido.
* [X] Retorna **400 Bad Request** se a **data de início** for posterior à **data de fim**.
* [X] Retorna **500 Internal Server Error** caso ocorra um erro ao processar a consulta.
