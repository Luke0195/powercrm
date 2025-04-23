package br.com.powercrm.app.external.fipe.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FipeAnosResponse {
    private String codigo;
    private String nome;
}
