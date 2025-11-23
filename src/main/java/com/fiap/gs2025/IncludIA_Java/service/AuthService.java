package com.fiap.gs2025.IncludIA_Java.service;


import com.fiap.gs2025.IncludIA_Java.dto.auth.LoginRequest;
import com.fiap.gs2025.IncludIA_Java.dto.auth.LoginResponse;
import com.fiap.gs2025.IncludIA_Java.dto.request.CandidateRegistrationRequest;
import com.fiap.gs2025.IncludIA_Java.dto.request.RecruiterRegistrationRequest;
import com.fiap.gs2025.IncludIA_Java.dto.response.CandidateProfileResponse;
import com.fiap.gs2025.IncludIA_Java.dto.response.RecruiterProfileResponse;
import com.fiap.gs2025.IncludIA_Java.exceptions.DuplicateResourceException;
import com.fiap.gs2025.IncludIA_Java.exceptions.InvalidRequestException;
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
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static java.util.Collections.singletonList;

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

    public LoginResponse socialLogin(com.fiap.gs2025.IncludIA_Java.dto.auth.SocialLoginRequest request) {
        String email = request.email();

        var candidateOpt = candidateRepository.findByEmail(email);
        if (candidateOpt.isPresent()) {
            Candidate c = candidateOpt.get();
            return createLoginResponse(c.getId(), c.getNome(), c.getEmail(), "ROLE_CANDIDATE");
        }

        var recruiterOpt = recruiterRepository.findByEmail(email);
        if (recruiterOpt.isPresent()) {
            Recruiter r = recruiterOpt.get();
            return createLoginResponse(r.getId(), r.getNome(), r.getEmail(), "ROLE_RECRUITER");
        }

        Candidate newCandidate = new Candidate();
        newCandidate.setId(UUID.randomUUID());
        newCandidate.setNome(request.nome() != null ? request.nome() : "Usuário Social");
        newCandidate.setEmail(email);
        newCandidate.setCpf(String.valueOf(System.currentTimeMillis()).substring(0, 11));
        newCandidate.setSenhaHash(passwordEncoder.encode(UUID.randomUUID().toString()));
        newCandidate.setAtive(true);
        newCandidate.setResumoPerfil("Perfil criado via " + request.provider());

        candidateRepository.save(newCandidate);

        return createLoginResponse(newCandidate.getId(), newCandidate.getNome(), newCandidate.getEmail(), "ROLE_CANDIDATE");
    }

    private LoginResponse createLoginResponse(UUID id, String nome, String email, String role) {
        UserDetails userDetails =
                new CustomUserDetails(
                        id, email, "", singletonList(new SimpleGrantedAuthority(role))
                );

       UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        String token = tokenService.generateToken(auth);
        return new LoginResponse(token, id, nome, email, role);
    }
}