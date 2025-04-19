### Cadastrar Usuário

>### Dados de Entrada
   * name(required)
   * email(required)
   * phone(optional)
   * cpf(required, valid)
   * zipcode(optional)
   * address(optional)
   * number(optional)
   * complement(optional)
   * status
   * createdAt
  

> ### Casos de sucesso
*  [X]  Recebe uma rota do tipo <b>POST</b> na rota /api/user
*  [X]  Valida os campos obrigatórios(name, email,cpf).
*  [X]  Não permite que um usuário seja cadastrado com o mesmo email.
*  [X]  Não permite que um usuário seja cadastrado com o **mesmo CPF**.
*  [X]  Adiciona um usuário quando os dados forem validos.
*  [X]  Retorna <b>201</b> com os dados do usuário criado em caso de sucesso.
  
#
> ### Exceções
* [X] Retorna erro <b>404</b> se o endpoint <b>/user</b> não existir.
* [X] Retorna erro <b>400</b> se o nome do usuário não for informado.
* [X] Retorna erro <b>400</b> se o email não for informado.
* [X] Retorna erro <b>400</b> se um email invalido for informado.
* [X] Retorna erro <b>400</b> se o cpf não for informado.
* [X] Retorna erro <b>422</b> se o **email informado já estiver sendo utilizado**.
* [X] Retorna erro <b>422</b> se o **CPF informado já estiver sendo utilizado**.
* [X] Retorna erro <b>500</b> se ocorrer um erro ao salvar um usuário.
  


#

### Listar Usuários

> ### Casos de Sucesso
* [X] Recebe uma rota do tipo <b>GET</b> na rota <b>/users</b>
* [X] Retorna uma lista de usuários com os usuários cadastrados.
* [X] Retorna <b> 200</b> com a lista de usuários cadastrados.

> ### Exceções
* [X] Retorna <b>404</b> se a api não existir.
* [X] Retorna <b>500</b> caso ocorra um erro ao carregar os usuários.
#

### Atualizar Usuário

> ### Casos Sucesso
* [X] Recebe uma requisição do tipo <b>PUT</b> na rota <b>/users/{id} </b>
* [X] Valida se o id que está sendo passado na rota é um id valido.
* [X] Valida se o id informado existe.
* [X] Atualiza os dados do usuário caso o id seja valido.
* [X] Retorna <b> 200</b> com os dados atualizados.
  
> ### Exceções
* [X] Retorna <b>404</b> se api não existir.
* [X] Retorna <b>404</b> caso o id do usuário não for encontrado.
* [X] Retorna <b>500</b> caso ocorra um erro ao atualizar um usuário.

# 

### Deletar Usuário

> ### Casos de Sucesso

* [X] Recebe uma requisição do tipo <b>DELETE /users/{id}</b>
* [X] Valida se o id que está sendo passado na rota é um id valido.
* [X] Valida se o id informado existe.
* [X] Deleta a informação do usuário.
* [X] Retorna <b>204</b> em caso de sucesso.

> ### Exceções
* [X] Retorna <b>404</b> se api não existir.
* [X] Retorna <b>404</b> caso o id do usuário não for encontrado.
* [X] Retorna <b>500</b> caso ocorra um erro ao deletar um usuário.
  
#
## Listar Usuários por Período
 >### Dados de Entrada
 * startDate (obrigatório, formato ISO: yyyy-MM-dd)
 * endDate (obrigatório, formato ISO: yyyy-MM-dd)

> ### Casos de Sucesso
* [X] Recebe uma requisição do tipo <b>GET</b> na rota <b>/users?startDate=2024-01-01&endDate=2024-01-31</b>
* [X] Filtra os usuários com base no campo <b>createdAt</b> entre as datas informadas
* [X] Retorna <b>200</b> com a lista dos usuários cadastrados nesse período

> ### Exceções
* [X] Retorna <b>400</b> se os parâmetros startDate ou endDate não forem informados
* [X] Retorna <b>400</b> se as datas estiverem em formato inválido
* [X] Retorna <b>400</b> se a data de início for posterior à data de fim
* [X] Retorna <b>500</b> caso ocorra um erro ao processar a consulta