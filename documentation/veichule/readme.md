### Cadastrar Veículos

>### Dados de Entrada
 * plate(required, unique)
 * advertised_price(required)
 * year(required)
 * createdAt

> ### Casos de sucesso
*  [X]  Recebe uma rota do tipo <b>POST</b> na rota /api/veichule
*  [X]  Valida os campos obrigatórios(plate, advertised_price).
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
  
