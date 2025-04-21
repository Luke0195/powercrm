package br.com.powercrm.app.external.fipe.dtos;

import lombok.Data;

import java.util.List;

@Data
public class FipeModelosResponse {

    private List<FipeModeloResponse> modelos;
}
