package br.com.powercrm.app.external.fipe.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FipeModelosResponse {

    private List<FipeModeloResponse> modelos;
}
