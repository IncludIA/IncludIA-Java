package com.fiap.gs2025.IncludIA_Java.service;

import com.fiap.gs2025.IncludIA_Java.models.Candidate;
import com.fiap.gs2025.IncludIA_Java.models.Skill;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class AiService {

    private final ChatClient chatClient;

    @Autowired
    public AiService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public String gerarDescricaoInclusiva(String descricaoOriginal) {
        String promptText = """
                Você é um especialista em Recrutamento e Seleção focado em diversidade e inclusão.
                Reescreva a seguinte descrição de vaga para ser 100% neutra em termos de gênero,
                idade, raça ou qualquer viés inconsciente.
                
                Remova jargões corporativos (ex: "rockstar", "ninja", "guru").
                Substitua requisitos de "anos de experiência" por "experiência comprovada em...".
                Foque estritamente nas habilidades técnicas e comportamentais necessárias.
                
                Descrição Original:
                {descricao}
                
                Retorne APENAS a nova descrição, sem qualquer introdução.
                """;

        PromptTemplate promptTemplate = new PromptTemplate(promptText);
        promptTemplate.add("descricao", descricaoOriginal);

        return chatClient.prompt(promptTemplate.create()).call().content();
    }

    public String gerarResumoInclusivo(Candidate candidate) {

        String skills = candidate.getSkills().stream()
                .map(Skill::getNome)
                .collect(Collectors.joining(", "));

        String experiencias = candidate.getExperiencias().stream()
                .map(exp -> String.format("- %s (%s)", exp.getTituloCargo(), exp.getDescricao()))
                .collect(Collectors.joining("\n"));

        String formacoes = candidate.getFormacoes().stream()
                .map(edu -> String.format("- %s em %s", edu.getGrau().toString(), edu.getAreaEstudo()))
                .collect(Collectors.joining("\n"));

        String promptText = """
                Você é um assistente de IA focado em recrutamento anónimo e inclusivo.
                Analise o perfil de um candidato e gere um resumo em terceira pessoa,
                focado puramente em competências (soft e hard skills).
                
                NÃO mencione nomes de empresas, universidades, género, ou qualquer
                informação pessoal que possa gerar viés.
                
                Perfil Original do Candidato:
                {resumo}
                
                Competências Listadas:
                {skills}
                
                Experiências (foco na descrição):
                {experiencias}
                
                Formações:
                {formacoes}
                
                Retorne APENAS o resumo anónimo, com 2 a 3 parágrafos.
                Exemplo: "Este profissional possui fortes competências em..."
                """;

        PromptTemplate promptTemplate = new PromptTemplate(promptText);
        promptTemplate.add("resumo", candidate.getResumoPerfil());
        promptTemplate.add("skills", skills);
        promptTemplate.add("experiencias", experiencias);
        promptTemplate.add("formacoes", formacoes);

        return chatClient.prompt(promptTemplate.create()).call().content();
    }
}