package br.com.powercrm.app.service.validators;

import br.com.powercrm.app.external.fipe.OpenFeignFipeClient;
import br.com.powercrm.app.external.fipe.dtos.*;
import br.com.powercrm.app.service.exceptions.ThirdPartyServiceException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.transform.TransformerException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
class FipeValidationTest {

    @Mock
    private OpenFeignFipeClient openFeignFipeClient;

    private ObjectMapper objectMapper = new ObjectMapper();

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
        this.fipeModeloResponse = FipeModeloResponse.builder().codigo("1").nome("valid_name").build();
        this.fipeModelosResponse = FipeModelosResponse.builder().modelos(List.of(fipeModeloResponse)).build();
        this.fipeAnosResponse = FipeAnosResponse.builder().codigo("2014-4").nome("2014 Diesel").build();
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

    @DisplayName("getModelo should throws ThirdPartyExceptionWhenValidModelIdIsProvided")
    @Test
    void getModeloShouldThrowsThirdPartyExceptionWhenValidModelIdIsProvided(){
        Mockito.when(openFeignFipeClient.getModelos("invalid_id")).thenThrow(ThirdPartyServiceException.class);
        Assertions.assertThrows(ThirdPartyServiceException.class, () -> {
            fipeValidation.getModelo("invalid_id", 1L);
        });
    }

    @DisplayName("getModelo should return FipeModeloResponse when valid params are provided")
    @Test
    void getModeloShouldReturnsFipeModeloResponseWhenValidIdIsProvided(){
        Mockito.when(openFeignFipeClient.getModelos("1")).thenReturn(fipeModelosResponse);
        FipeModeloResponse fipeModeloResponse = fipeValidation.getModelo(fipeMarcaResponse.getCodigo(), 1L);
        Assertions.assertNotNull(fipeModeloResponse);
        Assertions.assertNotNull("1", fipeMarcaResponse.getCodigo());
        Assertions.assertNotNull("valid_name", fipeMarcaResponse.getNome());
    }

    @DisplayName("getAnos should throws ThirdPartyException when invalid params are provided")
    @Test
    void getAnosShouldThrowsThirdPartyExceptionWhenInvalidParamsAreProvided(){
        Mockito.when(openFeignFipeClient.getAnos("1L", "invalid_id")).thenThrow(ThirdPartyServiceException.class);
        Assertions.assertThrows(ThirdPartyServiceException.class, () -> {
            fipeValidation.getYears("1L", "invalid_id");
        });
    }

    @DisplayName("getAnos should a returns a list of modelos when valid params are provided ")
    @Test
    void getAnosShouldAListOfModelosWhenValidParamsAreProvided(){
        Mockito.when(openFeignFipeClient.getAnos(fipeMarcaResponse.getCodigo(), fipeModeloResponse.getCodigo())).thenReturn(List.of(fipeAnosResponse));
        List<FipeAnosResponse> anosResponses = fipeValidation.getYears(fipeMarcaResponse.getCodigo(),
                fipeModeloResponse.getCodigo());
        Assertions.assertEquals(1, anosResponses.size());
    }

    @DisplayName("getCodeYear should throws ThirdPartyException when invalid params are provided")
    @Test
    void getCodeYearsShouldThrowsThirdPartyExceptionWhenInvalidParamsAreProvided(){
        Assertions.assertThrows(ThirdPartyServiceException.class, () -> {
            fipeValidation.getCodeYear(List.of(fipeAnosResponse), "any_year");
        });
    }

    @DisplayName("getCodeYear should return code when valid year is provided")
    @Test
    void getCodeYearShouldReturnCodeWhenValidYearIsProvided(){
        String result = fipeValidation.getCodeYear(List.of(fipeAnosResponse), "2014");
        Assertions.assertEquals("2014-4", result);
    }

    @DisplayName("getPrice should throws ThirdPartyException when invalid params are provided")
    @Test
    void getPriceShouldThrowsThirdPartyExceptionWhenInvalidParamsAreProvided(){
        Mockito.when(openFeignFipeClient.getValor("invalid_id", "invalid_id", "2023"))
                .thenThrow(ThirdPartyServiceException.class);
        Assertions.assertThrows(ThirdPartyServiceException.class, () ->{
            fipeValidation.getPrice("invalid_id", "invalid_id", "2023");
        });
    }

    @DisplayName("getPrice should returns responseData when valid is provided")
    @Test
    void getPriceShouldReturnsResponseDataWhenValidDataIsProvided(){
        Map<String,Object> parseObject = objectMapper.convertValue(fipeValorResponse, new TypeReference<Map<String, Object>>() {
        });
        Mockito.when(openFeignFipeClient.getValor(fipeMarcaResponse.getCodigo(), fipeModeloResponse.getCodigo(),
                "2014")).thenReturn(parseObject);
        Map<String,Object> payload = fipeValidation.getPrice(fipeMarcaResponse.getCodigo(), fipeModeloResponse.getCodigo(),
                "2014");
        Assertions.assertNotNull(payload);
        Assertions.assertEquals("R$ 96.382,00", payload.get("valor"));
    }
}
