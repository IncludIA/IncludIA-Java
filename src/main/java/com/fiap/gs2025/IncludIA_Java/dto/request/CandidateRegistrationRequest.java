package com.fiap.gs2025.IncludIA_Java.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CandidateRegistrationRequest(
        @NotBlank(message = "{nome.notblank}")
        String nome,

        @NotBlank(message = "CPF é obrigatório")
        @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 dígitos")
        String cpf,

        @NotBlank(message = "{email.notblank}")
        @Email(message = "{field.email}")
        String email,

        @NotBlank(message = "{senha.notblank}")
        @Size(min = 8, message = "{field.size.password}")
        String senha,

        String resumoPerfil,

        String cep,
        String logradouro,
        String numero,
        String bairro,
        @NotBlank(message = "Cidade é obrigatória")
        String cidade,
        @NotBlank(message = "Estado é obrigatório")
        String estado,

        Integer raioBuscaKm
) {}