> ## Integração Api Fipe

> ## Dados de Entrada
* plate (required, format XXX-XXXX)
* advertisedPlate (required)
* year (required),
* userId (required)
* brandId (required),
* modelId (required)
* 
> ### Casos de sucesso
* [ ] Salva um veículo quando todos os dados forem válidos.

> #### Exceções
* [ ] Retorna <b> ThirdPartyException</b> se ocorrer um erro ao carregar as marcas.
* [ ] Retorna <b> ThirdPartyException</b> se o modelo não for encontrado.
* [ ] Retorna <b> ThirdPartyException</b> se o ano do veiculo não for encontrado.
* [ ] Retorna <b> ThirdPartyException</b> se o anoCodigo não for encontrado.
* [ ] Retorna <b> ThirdPartyException </b> se o Valor não for encontrado.
* [ ] Retorna <b> ResourceAlreadyExistsException </b> se o usuário já existir.
* [ ] Retorna <b> ResourceAlreadyExistsException </b> se o veĩculo já existir.