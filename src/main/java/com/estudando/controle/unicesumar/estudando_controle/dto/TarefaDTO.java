package com.estudando.controle.unicesumar.estudando_controle.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TarefaDTO {
    private Long id;
    private String nome;
    private String responsavel;
    private String dataCriacao;
    private String dataConclusao;
    private Boolean concluida;

    // Construtores, getters e setters
    public TarefaDTO(Long id, String nome, String responsavel,
            LocalDateTime dataCriacao, LocalDateTime dataConclusao, Boolean concluida) {
        this.id = id;
        this.nome = nome;
        this.responsavel = responsavel;
        this.concluida = concluida;
        // Formatar as datas corretamente para LocalTime
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"); // Corrigido para tempo
        this.dataCriacao = dataCriacao != null ? dataCriacao.format(formatter) : null;
        this.dataConclusao = dataConclusao != null ? dataConclusao.format(formatter) : null;
    }

    // Getters e setters
    public Long getId() {
        return id;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public String getNome() {
        return nome;
    }

    public String getDataCriacao() {
        return dataCriacao;
    }

    public String getDataConclusao() {
        return dataConclusao;
    }

    public Boolean getConcluida() {
        return concluida;
    }
}
