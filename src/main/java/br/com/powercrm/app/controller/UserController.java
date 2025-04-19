package br.com.powercrm.app.controller;

import br.com.powercrm.app.dto.request.UserRequestDto;
import br.com.powercrm.app.dto.response.UserResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


public interface UserController {

    @RequestMapping(value = "/user")
    @Operation(summary = "Criar um usuário", description = "Cria um usuário e retorna os dados cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ação realizada com succeso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação dos campos entrada"),
            @ApiResponse(responseCode = "422", description = "Erro ao processar a requisição"),
            @ApiResponse(responseCode = "500", description = "Ocorreu um erro interno ao realizar a operação")
    })

    ResponseEntity<UserResponseDto> handleAddUser(@Valid @RequestBody UserRequestDto userRequestDto);

    @GetMapping(value = "/users" )
    public ResponseEntity<Page<UserResponseDto>>handleLoadUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(value = "start", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam(value = "end", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end);

    @DeleteMapping(value = "/users/{id}")
    public ResponseEntity<Void> handleDeleteUser(@PathVariable String id);

    @PutMapping(value = "/users/{id}")
    public ResponseEntity<UserResponseDto> handleUpdateUser(@PathVariable String id, @RequestBody UserRequestDto userRequestDto);



}
