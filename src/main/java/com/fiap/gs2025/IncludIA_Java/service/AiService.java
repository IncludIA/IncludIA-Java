package com.fiap.gs2025.IncludIA_Java.service;

import com.fiap.gs2025.IncludIA_Java.models.Candidate;
import com.fiap.gs2025.IncludIA_Java.models.Skill;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AiService {

    private final RestTemplate restTemplate = new RestTemplate();
    @Value("${api.iot.url}")
    private String BASE_URL;

    public String gerarDescricaoInclusiva(String titulo, String descricaoOriginal) {
        String url = BASE_URL + "/api/v1/vagas/inclusiva";

        VagaRequest request = new VagaRequest(titulo, descricaoOriginal);

        try {
            VagaResponse response = restTemplate.postForObject(url, request, VagaResponse.class);
            return response != null ? response.texto_inclusivo() : descricaoOriginal;
        } catch (Exception e) {
            e.printStackTrace();
            return descricaoOriginal;
        }
    }

    public String gerarResumoInclusivo(Candidate candidate) {
        String url = BASE_URL + "/api/v1/candidatos/anonimizar";

        StringBuilder textoCompleto = new StringBuilder();
        textoCompleto.append("Nome: ").append(candidate.getNome()).append(". ");
        textoCompleto.append("Resumo: ").append(candidate.getResumoPerfil()).append(". ");

        String skills = candidate.getSkills().stream().map(Skill::getNome).collect(Collectors.joining(", "));
        textoCompleto.append("Habilidades: ").append(skills).append(". ");

        CandidateRequest request = new CandidateRequest(textoCompleto.toString());

        try {
            CandidateResponse response = restTemplate.postForObject(url, request, CandidateResponse.class);
            return response != null ? response.resumo_profissional() : candidate.getResumoPerfil();
        } catch (Exception e) {
            e.printStackTrace();
            return candidate.getResumoPerfil();
        }
    }

    public record VagaRequest(String cargo, String descricao_original) {}
    public record VagaResponse(String texto_inclusivo, List<String> alteracoes) {}

    public record CandidateRequest(String texto_curriculo) {}
    public record CandidateResponse(String resumo_profissional, List<String> hard_skills, List<String> soft_skills) {}
}