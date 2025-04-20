package br.com.powercrm.app.external.fipe.dtos;

public record FipeValorResponse(
        Integer tipoVeiculo,
        String valor,
        String marca,
        String modelo,
        String anoModelo,
        String combustivel,
        String codigofipe,
        String mesReferencia,
        String siglaCombustivel) {

    @Override
    public Integer tipoVeiculo() {
        return tipoVeiculo;
    }

    @Override
    public String valor() {
        return valor;
    }

    @Override
    public String marca() {
        return marca;
    }

    @Override
    public String modelo() {
        return modelo;
    }

    @Override
    public String anoModelo() {
        return anoModelo;
    }

    @Override
    public String combustivel() {
        return combustivel;
    }

    @Override
    public String codigofipe() {
        return codigofipe;
    }

    @Override
    public String mesReferencia() {
        return mesReferencia;
    }

    @Override
    public String siglaCombustivel() {
        return siglaCombustivel;
    }
}
