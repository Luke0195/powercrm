package br.com.powercrm.app.external.fipe.dtos;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class FipeModeloResponse {
    private String codigo;
    private String nome;
}
