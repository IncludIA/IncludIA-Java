package com.fiap.gs2025.IncludIA_Java.service;


import com.fiap.gs2025.IncludIA_Java.dto.auth.LoginRequest;
import com.fiap.gs2025.IncludIA_Java.dto.auth.LoginResponse;
import com.fiap.gs2025.IncludIA_Java.dto.request.CandidateRegistrationRequest;
import com.fiap.gs2025.IncludIA_Java.dto.request.RecruiterRegistrationRequest;
import com.fiap.gs2025.IncludIA_Java.dto.response.CandidateProfileResponse;
import com.fiap.gs2025.IncludIA_Java.dto.response.RecruiterProfileResponse;
import com.fiap.gs2025.IncludIA_Java.exceptions.DuplicateResourceException;
import com.fiap.gs2025.IncludIA_Java.exceptions.ResourceNotFoundException;
import com.fiap.gs2025.IncludIA_Java.models.Candidate;
import com.fiap.gs2025.IncludIA_Java.models.Empresa;
import com.fiap.gs2025.IncludIA_Java.models.Endereco;
import com.fiap.gs2025.IncludIA_Java.models.Recruiter;
import com.fiap.gs2025.IncludIA_Java.repository.CandidateRepository;
import com.fiap.gs2025.IncludIA_Java.repository.EmpresaRepository;
import com.fiap.gs2025.IncludIA_Java.repository.RecruiterRepository;
import com.fiap.gs2025.IncludIA_Java.security.CustomUserDetails;
import com.fiap.gs2025.IncludIA_Java.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private RecruiterRepository recruiterRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public CandidateProfileResponse registerCandidate(CandidateRegistrationRequest request) {
        if (candidateRepository.findByEmail(request.email()).isPresent() || recruiterRepository.findByEmail(request.email()).isPresent()) {
            throw new DuplicateResourceException("Email já cadastrado");
        }

        Candidate candidate = new Candidate();
        candidate.setId(UUID.randomUUID());
        candidate.setNome(request.nome());
        candidate.setCpf(request.cpf());
        candidate.setEmail(request.email());
        candidate.setSenhaHash(passwordEncoder.encode(request.senha()));
        candidate.setResumoPerfil(request.resumoPerfil());
        candidate.setAtive(true);
        candidate.setRaioBuscaKm(request.raioBuscaKm() != null ? request.raioBuscaKm() : 30);

        Endereco endereco = new Endereco();
        endereco.setCep(request.cep());
        endereco.setLogradouro(request.logradouro());
        endereco.setNumero(request.numero());
        endereco.setBairro(request.bairro());
        endereco.setCidade(request.cidade());
        endereco.setEstado(request.estado());
        candidate.setEndereco(endereco);

        Candidate savedCandidate = candidateRepository.save(candidate);
        return new CandidateProfileResponse(savedCandidate);
    }

    @Transactional
    public RecruiterProfileResponse registerRecruiter(RecruiterRegistrationRequest request) {
        if (candidateRepository.findByEmail(request.email()).isPresent() || recruiterRepository.findByEmail(request.email()).isPresent()) {
            throw new DuplicateResourceException("Email já cadastrado");
        }

        Empresa empresa = empresaRepository.findById(request.empresaId())
                .orElseThrow(() -> new ResourceNotFoundException("Empresa não encontrada"));

        Recruiter recruiter = new Recruiter();
        recruiter.setId(UUID.randomUUID());
        recruiter.setNome(request.nome());
        recruiter.setEmail(request.email());
        recruiter.setSenhaHash(passwordEncoder.encode(request.senha()));
        recruiter.setEmpresa(empresa);
        recruiter.setAtive(true);

        Recruiter savedRecruiter = recruiterRepository.save(recruiter);
        return new RecruiterProfileResponse(savedRecruiter);
    }

    public LoginResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.senha())
        );

        String token = tokenService.generateToken(authentication);
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String role = userDetails.getAuthorities().iterator().next().getAuthority();
        String nome = "";

        if ("ROLE_CANDIDATE".equals(role)) {
            nome = candidateRepository.findById(userDetails.getId()).get().getNome();
        } else {
            nome = recruiterRepository.findById(userDetails.getId()).get().getNome();
        }

        return new LoginResponse(token, userDetails.getId(), nome, userDetails.getUsername(), role);
    }
}