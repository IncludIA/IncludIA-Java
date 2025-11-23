package com.fiap.gs2025.IncludIA_Java;

import com.fiap.gs2025.IncludIA_Java.dto.request.CandidateRegistrationRequest;
import com.fiap.gs2025.IncludIA_Java.dto.response.CandidateProfileResponse;
import com.fiap.gs2025.IncludIA_Java.exceptions.DuplicateResourceException;
import com.fiap.gs2025.IncludIA_Java.models.Candidate;
import com.fiap.gs2025.IncludIA_Java.repository.CandidateRepository;
import com.fiap.gs2025.IncludIA_Java.repository.RecruiterRepository;
import com.fiap.gs2025.IncludIA_Java.service.AuthService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private CandidateRepository candidateRepository;

    @Mock
    private RecruiterRepository recruiterRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("Deve registrar candidato com sucesso quando email não existe")
    void registerCandidate_Success() {
        // Arrange - AGORA COM TODOS OS CAMPOS OBRIGATÓRIOS DO RECORD
        CandidateRegistrationRequest request = new CandidateRegistrationRequest(
                "João Silva",           // nome
                "12345678901",          // cpf (NOVO)
                "joao@email.com",       // email
                "senha123",             // senha
                "Desenvolvedor Java",   // resumoPerfil
                "01310930",             // cep (NOVO)
                "Av Paulista",          // logradouro (NOVO)
                "1000",                 // numero (NOVO)
                "Bela Vista",           // bairro (NOVO)
                "São Paulo",            // cidade (NOVO - Obrigatório)
                "SP",                   // estado (NOVO - Obrigatório)
                30                      // raioBuscaKm (NOVO)
        );

        // Mocks
        when(candidateRepository.findByEmail(request.email())).thenReturn(Optional.empty());
        when(recruiterRepository.findByEmail(request.email())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(request.senha())).thenReturn("encodedPassword");

        // Simular o comportamento do save retornando um objeto com ID gerado
        when(candidateRepository.save(any(Candidate.class))).thenAnswer(invocation -> {
            Candidate c = invocation.getArgument(0);
            c.setId(UUID.randomUUID());
            return c;
        });

        // Act
        CandidateProfileResponse response = authService.registerCandidate(request);

        // Assert
        assertNotNull(response);
        assertEquals(request.nome(), response.nome());
        assertEquals(request.email(), response.email());
        assertNotNull(response.id());

        verify(candidateRepository, times(1)).save(any(Candidate.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar registrar candidato com email duplicado")
    void registerCandidate_DuplicateEmail() {
        // Arrange - AGORA COM TODOS OS CAMPOS OBRIGATÓRIOS DO RECORD
        CandidateRegistrationRequest request = new CandidateRegistrationRequest(
                "Maria Souza",          // nome
                "98765432100",          // cpf (NOVO)
                "maria@email.com",      // email
                "senha123",             // senha
                "QA Engineer",          // resumoPerfil
                "20000000",             // cep (NOVO)
                "Rua Teste",            // logradouro (NOVO)
                "10",                   // numero (NOVO)
                "Centro",               // bairro (NOVO)
                "Rio de Janeiro",       // cidade (NOVO - Obrigatório)
                "RJ",                   // estado (NOVO - Obrigatório)
                50                      // raioBuscaKm (NOVO)
        );

        // Mock: Simula que já existe um candidato com este email
        when(candidateRepository.findByEmail(request.email())).thenReturn(Optional.of(new Candidate()));

        // Act & Assert
        assertThrows(DuplicateResourceException.class, () -> authService.registerCandidate(request));

        // Verifica que o save NUNCA foi chamado
        verify(candidateRepository, never()).save(any(Candidate.class));
    }
}