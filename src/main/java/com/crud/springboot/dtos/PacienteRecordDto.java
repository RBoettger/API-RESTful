package com.crud.springboot.dtos;

import jakarta.validation.constraints.NotNull;

public record PacienteRecordDto(@NotNull String nome, @NotNull String cpf, @NotNull Integer idade, @NotNull String sexo ) {

}
