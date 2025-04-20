package br.com.powercrm.app.service;

import br.com.powercrm.app.external.fipe.FipeClient;
import br.com.powercrm.app.external.fipe.dtos.FipeAnosResponse;
import br.com.powercrm.app.external.fipe.dtos.FipeMarcaResponse;
import br.com.powercrm.app.external.fipe.dtos.FipeModeloResponse;
import br.com.powercrm.app.external.fipe.dtos.FipeValorResponse;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class FipeService {
    private final FipeClient fipeClient;



    @Cacheable("marcas")
    public List<FipeMarcaResponse> getMarcas() {
        return fipeClient.getMarcas();
    }

    @Cacheable(value = "modelos", key = "#marcaId")
    public FipeModeloResponse getModelos(String marcaId) {
        return fipeClient.getModelos(marcaId);
    }

    @Cacheable(value = "anos", key = "#marcaId + '-' + #modeloId")
    public List<FipeAnosResponse> getAnos(String marcaId, String modeloId) {
        return fipeClient.getAnos(marcaId, modeloId);
    }

    @Cacheable(value = "valores", key = "#marcaId + '-' + #modeloId + '-' + #anoId")
    public Map<String,Object> getValor(String marcaId, String modeloId, String anoId) {
        return fipeClient.getValor(marcaId, modeloId, anoId);
    }

}
