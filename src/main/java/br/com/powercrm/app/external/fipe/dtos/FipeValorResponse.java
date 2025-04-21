package br.com.powercrm.app.external.fipe.dtos;

import lombok.Data;

@Data
public class FipeValorResponse{

    private Integer tipoVeiculo;
    private String valor;
    private String marca;
    private String modelo;
    private String anoModelo;
    private String combustivel;
    private String codigofipe;
    private String mesReferencia;
    private String siglaCombustivel;
}
