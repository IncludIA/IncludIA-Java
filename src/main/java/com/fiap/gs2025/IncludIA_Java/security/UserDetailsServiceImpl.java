package com.fiap.gs2025.IncludIA_Java.security;

import com.fiap.gs2025.IncludIA_Java.exceptions.ResourceNotFoundException;
import com.fiap.gs2025.IncludIA_Java.models.Candidate;
import com.fiap.gs2025.IncludIA_Java.models.Recruiter;
import com.fiap.gs2025.IncludIA_Java.repository.CandidateRepository;
import com.fiap.gs2025.IncludIA_Java.repository.RecruiterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private RecruiterRepository recruiterRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Candidate> candidateOpt = candidateRepository.findByEmail(email);
        if (candidateOpt.isPresent()) {
            Candidate candidate = candidateOpt.get();
            return new CustomUserDetails(
                    candidate.getId(),
                    candidate.getEmail(),
                    candidate.getSenhaHash(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_CANDIDATE"))
            );
        }

        Optional<Recruiter> recruiterOpt = recruiterRepository.findByEmail(email);
        if (recruiterOpt.isPresent()) {
            Recruiter recruiter = recruiterOpt.get();
            return new CustomUserDetails(
                    recruiter.getId(),
                    recruiter.getEmail(),
                    recruiter.getSenhaHash(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_RECRUITER"))
            );
        }

        throw new UsernameNotFoundException("Usuário não encontrado com o email: " + email);
    }
}