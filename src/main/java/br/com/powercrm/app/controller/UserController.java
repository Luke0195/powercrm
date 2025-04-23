package br.com.powercrm.app.controller;

import br.com.powercrm.app.dto.request.UserRequestDto;
import br.com.powercrm.app.dto.response.UserResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Tag(name="Usuários", description = "Operações relacionadas a usuários")
public interface UserController {
    @PostMapping(value = "/user")
    @Operation(summary = "Criar um usuário", description = "Cria um usuário e retorna os dados cadastrados",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json"))
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ação realizada com sucesso", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Erro de validação dos campos de entrada", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "422", description = "Erro ao processar a requisição", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Erro interno", content = @Content(mediaType = "application/json"))
    })
    ResponseEntity<UserResponseDto> handleAddUser(@Valid @RequestBody UserRequestDto userRequestDto);


    @GetMapping(value = "/users" )
    @Operation(summary = "", description = "Retorna todos os usuários no sistema")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "Ação realiza com sucesso", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Ocorreu um erro interno ao realizar a operação", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<Page<UserResponseDto>>handleLoadUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(value = "start", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam(value = "end", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end);

    @Operation(summary = "Deletar Usuário", description = "Delete usuário por id")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "204", description = "Ação realiza com sucesso",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Id não encontrado.",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Ocorreu um erro interno ao realizar a operação",
                    content = @Content(mediaType = "application/json"))
    })
    @DeleteMapping(value = "/users/{id}")
    public ResponseEntity<Void> handleDeleteUser(@PathVariable String id);


    @Operation(summary = "Atualizar Usuário", description = "Atualiza usuário por id")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "Ação realiza com sucesso",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Id não encontrado.",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Ocorreu um erro interno ao realizar a operação",
                    content = @Content(mediaType = "application/json"))
    })
    @PutMapping(value = "/users/{id}")
    public ResponseEntity<UserResponseDto> handleUpdateUser(@PathVariable String id, @RequestBody UserRequestDto userRequestDto);



}
