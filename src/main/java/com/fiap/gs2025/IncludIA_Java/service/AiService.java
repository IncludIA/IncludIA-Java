package com.fiap.gs2025.IncludIA_Java.service;

import com.fiap.gs2025.IncludIA_Java.models.Candidate;
import com.fiap.gs2025.IncludIA_Java.models.Skill;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AiService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String BASE_URL = "https://app-includia-iot-2771.azurewebsites.net";

    // --- Integração para Vagas ---
    public String gerarDescricaoInclusiva(String titulo, String descricaoOriginal) {
        String url = BASE_URL + "/api/v1/vagas/inclusiva";

        // Cria o objeto da requisição (Body)
        VagaRequest request = new VagaRequest(titulo, descricaoOriginal);

        try {
            // Faz o POST e pega a resposta
            VagaResponse response = restTemplate.postForObject(url, request, VagaResponse.class);
            return response != null ? response.texto_inclusivo() : descricaoOriginal;
        } catch (Exception e) {
            e.printStackTrace();
            // Em caso de erro na API externa, retorna o texto original para não travar o sistema
            return descricaoOriginal;
        }
    }

    // --- Integração para Candidatos ---
    public String gerarResumoInclusivo(Candidate candidate) {
        String url = BASE_URL + "/api/v1/candidatos/anonimizar";

        // Monta um texto único com os dados do candidato para a API processar
        StringBuilder textoCompleto = new StringBuilder();
        textoCompleto.append("Nome: ").append(candidate.getNome()).append(". ");
        textoCompleto.append("Resumo: ").append(candidate.getResumoPerfil()).append(". ");

        String skills = candidate.getSkills().stream().map(Skill::getNome).collect(Collectors.joining(", "));
        textoCompleto.append("Habilidades: ").append(skills).append(". ");

        // Cria o objeto da requisição
        CandidateRequest request = new CandidateRequest(textoCompleto.toString());

        try {
            CandidateResponse response = restTemplate.postForObject(url, request, CandidateResponse.class);
            return response != null ? response.resumo_profissional() : candidate.getResumoPerfil();
        } catch (Exception e) {
            e.printStackTrace();
            return candidate.getResumoPerfil();
        }
    }

    // --- DTOs Internos (Para mapear o JSON da sua API) ---
    // Request/Response Vaga
    public record VagaRequest(String cargo, String descricao_original) {}
    public record VagaResponse(String texto_inclusivo, List<String> alteracoes) {}

    // Request/Response Candidato
    public record CandidateRequest(String texto_curriculo) {}
    public record CandidateResponse(String resumo_profissional, List<String> hard_skills, List<String> soft_skills) {}
}