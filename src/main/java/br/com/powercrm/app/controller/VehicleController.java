package br.com.powercrm.app.controller;

import br.com.powercrm.app.dto.request.VehicleRequestDto;
import br.com.powercrm.app.dto.response.VehicleResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.ServletResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "Veículos", description = "Recurso para gerenciar veículos")
public interface VehicleController {

    @PostMapping(value = "/vehicle")
    @Operation(summary = "Método de validação de veiculo", description = "Cria um veículo e retorna os dados cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Aceito com os dados sendo enviados para o processo de validação.", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Erro de validação dos campos de entrada", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "422", description = "Erro ao processar a requisição", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content(mediaType = "application/json"))
    })
    ResponseEntity<Map<String,Object>> handleValidateVehicle(@Valid @RequestBody VehicleRequestDto vehicleRequestDto);

    @GetMapping(value = "/vehicles")
    @Operation(summary = "Listar todos os veículos", description = "Retorna todos os veículos cadastrados no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Veículos listados com sucesso", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content(mediaType = "application/json"))
    })
    ResponseEntity<Page<VehicleResponseDto>> handleLoadVehicles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size);

    @DeleteMapping(value = "/vehicles/{id}")
    @Operation(summary = "Deletar veículo", description = "Deleta o veículo pelo ID informado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Veículo deletado com sucesso", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Veículo não encontrado", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content(mediaType = "application/json"))
    })
    ResponseEntity<Void> handleDeleteVehicle(@PathVariable String id);

    @PutMapping(value = "/vehicles/{id}")
    @Operation(summary = "Atualizar veículo", description = "Atualiza os dados do veículo pelo ID informado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Veículo atualizado com sucesso", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Veículo não encontrado", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content(mediaType = "application/json"))
    })
    ResponseEntity<VehicleResponseDto> handleUpdate(@PathVariable String id, @RequestBody VehicleRequestDto vehicleRequestDto);
}