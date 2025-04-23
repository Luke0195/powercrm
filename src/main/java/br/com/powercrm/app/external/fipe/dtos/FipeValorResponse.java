package br.com.powercrm.app.external.fipe.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
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
