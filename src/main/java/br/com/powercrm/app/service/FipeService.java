package br.com.powercrm.app.service;

import br.com.powercrm.app.external.fipe.OpenFeignFipeClient;
import br.com.powercrm.app.external.fipe.dtos.*;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class FipeService {

    private final OpenFeignFipeClient fipeClient;

    public FipeService(OpenFeignFipeClient fipeClient) {
        this.fipeClient = fipeClient;
    }

    @Cacheable(value = "fipe-marcas")
    public List<FipeMarcaResponse> getMarcas() {
        return fipeClient.getMarcas();
    }

    @Cacheable(value = "fipe-modelos", key = "#marcaId")
    public FipeModelosResponse getModelos(String marcaId) {
        return fipeClient.getModelos(marcaId);
    }

    @Cacheable(value = "fipe-anos", key = "#marcaId + '-' + #modeloId")
    public List<FipeAnosResponse> getAnos(String marcaId, String modeloId) {
        return fipeClient.getAnos(marcaId, modeloId);
    }

    @Cacheable(value = "fipe-valor", key = "#marcaId + '-' + #modeloId + '-' + #anoId")
    public Map<String, Object> getValor(String marcaId, String modeloId, String anoId) {
        return fipeClient.getValor(marcaId, modeloId, anoId);
    }
}
