package br.com.powercrm.app.service.validators;

import br.com.powercrm.app.external.fipe.OpenFeignFipeClient;
import br.com.powercrm.app.external.fipe.dtos.*;
import br.com.powercrm.app.service.exceptions.ThirdPartyServiceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class FipeValidationTest {

    @Mock
    private OpenFeignFipeClient openFeignFipeClient;

    @InjectMocks
    private FipeValidation fipeValidation;

    private FipeMarcaResponse fipeMarcaResponse;
    private FipeModeloResponse fipeModeloResponse;
    private FipeModelosResponse fipeModelosResponse;
    private FipeAnosResponse fipeAnosResponse;
    private FipeValorResponse fipeValorResponse;


    @BeforeEach
    void setup(){
        this.fipeMarcaResponse = FipeMarcaResponse.builder().codigo("1").nome("valid_name").build();
        this.fipeModeloResponse = FipeModeloResponse.builder().codigo("valid_id").nome("valid_name").build();
        this.fipeModelosResponse = FipeModelosResponse.builder().modelos(List.of(fipeModeloResponse)).build();
        this.fipeAnosResponse = FipeAnosResponse.builder().codigo("valid_id").nome("valid_name").build();
        this.fipeValorResponse = FipeValorResponse.builder().valor("R$ 96.382,00").tipoVeiculo(1).marca("VW - VolksWagen")
                .modelo("AMAROK High.CD 2.0 16V TDI 4x4 Dies. Aut")
                .anoModelo("2014")
                .combustivel("Diesel")
                .codigofipe("005340-6")
                .mesReferencia("abril de 2025")
                .siglaCombustivel("D")
                .build();
    }

    @DisplayName("getMarcas should throws ThirdPartyExceptionWhenInvalidBrandIdIsProvided")
    @Test
    void getMarcasShouldThrowsThirdPartyExceptionWhenInvalidBrandIdIsProvided(){
        Long invalidBrandId = 1L;
        Assertions.assertThrows(ThirdPartyServiceException.class, () -> {
            fipeValidation.getMarca(invalidBrandId);
        });
    }

    @DisplayName("getMarcas should returns FipeMarcaResponse when valid brandId is provided")
    @Test
    void getMarcasShouldReturnsFipeMarcaResponseWhenValidBrandIdIsProvided(){
        Long existingId = 1L;
        Mockito.when(openFeignFipeClient.getMarcas()).thenReturn(List.of(fipeMarcaResponse));
        FipeMarcaResponse fipeMarcaResponse = fipeValidation.getMarca(existingId);
        Assertions.assertEquals("1", fipeMarcaResponse.getCodigo());
        Assertions.assertEquals("valid_name", fipeMarcaResponse.getNome());
    }
}
