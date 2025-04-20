package br.com.powercrm.app.external.fipe.dtos;

public record FipeAnosResponse(String codigo, String nome) {

    @Override
    public String codigo() {
        return codigo;
    }

    @Override
    public String nome() {
        return nome;
    }


}
