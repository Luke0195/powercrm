### Cadastrar Veículos

>### Dados de Entrada
 * plate(required, unique)
 * advertised_price(required)
 * year(required)
 * createdAt

> ### Casos de sucesso
*  [X] Recebe uma rota do tipo <b>POST</b> na rota /api/vehicle
*  [X] Valida os campos obrigatórios(plate, advertised_price, year, ).
*  [X] Não permite que o veiculo seja cadastrado com a mesma placa.
*  [X] Não permite que um veiculo seja cadastrado com um usuário invalido..
*  [X] Adiciona um veiculo quando os dados forem validos.
*  [X] Retorna <b>201</b> com os dados do veiculo criado em caso de sucesso.

#
> ### Exceções
* [ ] Retorna erro <b>404</b> se o endpoint <b>/api/veichule</b> não existir.
* [ ] Retorna erro <b>400</b> se o plate do veículo não for informado.
* [ ] Retorna erro <b>400</b> se o email não for informado.
* [ ] Retorna erro <b>400</b> se um email invalido for informado.
* [ ] Retorna erro <b>400</b> se o cpf não for informado.
* [ ] Retorna erro <b>422</b> se o **email informado já estiver sendo utilizado**.
* [ ] Retorna erro <b>422</b> se o **CPF informado já estiver sendo utilizado**.
* [ ] Retorna erro <b>500</b> se ocorrer um erro ao salvar um usuário.
  
