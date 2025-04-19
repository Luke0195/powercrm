### Cadastrar Veículos

>### Dados de Entrada
 * plate(required, unique)
 * advertised_price(required)
 * year(required)
 * createdAt

> ### Casos de sucesso
*  [ ] Recebe uma rota do tipo <b>POST</b> na rota /api/vehicle
*  [ ] Valida os campos obrigatórios(plate, advertised_price, year, ).
*  [ ] Não permite que o veiculo seja cadastrado com a mesma placa.
*  [ ] Não permite que um veiculo seja cadastrado com um usuário invalido..
*  [ ] Adiciona um veiculo quando os dados forem validos.
*  [ ] Retorna <b>201</b> com os dados do veiculo criado em caso de sucesso.

#
> ### Exceções
* [ ] Retorna erro <b>404</b> se o endpoint <b>/api/veichule</b> não existir.
* [ ] Retorna erro <b>400</b> se o nome do usuário não for informado.
* [ ] Retorna erro <b>400</b> se o email não for informado.
* [ ] Retorna erro <b>400</b> se um email invalido for informado.
* [ ] Retorna erro <b>400</b> se o cpf não for informado.
* [ ] Retorna erro <b>422</b> se o **email informado já estiver sendo utilizado**.
* [ ] Retorna erro <b>422</b> se o **CPF informado já estiver sendo utilizado**.
* [ ] Retorna erro <b>500</b> se ocorrer um erro ao salvar um usuário.
  
