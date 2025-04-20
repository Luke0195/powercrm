package br.com.powercrm.app.external.fipe;

import br.com.powercrm.app.config.feign.FeignCustomConfig;
import br.com.powercrm.app.external.fipe.dtos.FipeAnosResponse;
import br.com.powercrm.app.external.fipe.dtos.FipeMarcaResponse;
import br.com.powercrm.app.external.fipe.dtos.FipeModeloResponse;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

@FeignClient(name = "fipe-client", url = "https://parallelum.com.br/fipe/api/v1/carros", configuration = FeignCustomConfig.class)
public interface FipeClient {


    @GetMapping("/marcas")
    List<FipeMarcaResponse> getMarcas();

    @GetMapping("/marcas/{marcaId}/modelos")
    FipeModeloResponse getModelos(@PathVariable("marcaId") String marcaId);

    @GetMapping("/marcas/{marcaId}/modelos/{modeloId}/anos")
    List<FipeAnosResponse> getAnos(@PathVariable("marcaId") String marcaId,
                                   @PathVariable("modeloId") String modeloId);

    @GetMapping("/marcas/{marcaId}/modelos/{modeloId}/anos/{anoId}")
    Map<String,Object> getValor(@PathVariable("marcaId") String marcaId,
                               @PathVariable("modeloId") String modeloId,
                               @PathVariable("anoId") String anoId);

}
