package br.com.powercrm.app.service;

import br.com.powercrm.app.external.fipe.OpenFeignFipeClient;
import br.com.powercrm.app.external.fipe.dtos.*;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FipeService {
    private final OpenFeignFipeClient openFeignFipeClient;

    @Cacheable("marcas")
    public List<FipeMarcaResponse> getMarcas() {
        return openFeignFipeClient.getMarcas();
    }

    @Cacheable(value = "modelos", key = "#marcaId")
    public List<FipeModeloResponse> getModelos(String marcaId) {
        return openFeignFipeClient.getModelos(marcaId).getModelos();
    }

    @Cacheable(value = "anos", key = "#marcaId + '-' + #modeloId")
    public List<FipeAnosResponse> getAnos(String marcaId, String modeloId) {
        return openFeignFipeClient.getAnos(marcaId, modeloId);
    }


}
